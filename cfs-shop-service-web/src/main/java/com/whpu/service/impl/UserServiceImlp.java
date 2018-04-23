package com.whpu.service.impl;

import com.miaosha.mapper.MiaoshaUserMapper;
import com.miaosha.model.LoginVo;
import com.miaosha.model.MiaoshaUser;
import com.whpu.constant.Info;
import com.whpu.service.IUserService;
import com.whpu.util.UUIDUtil;
import com.whpu.util.cache.BloomFileter;
import com.whpu.util.exception.MsgException;
import com.whpu.util.json.CodeMsg;
import com.whpu.util.redisson.RedissonUtil;
import org.redisson.Redisson;
import org.redisson.api.RBloomFilter;
import org.redisson.api.RLock;
import org.redisson.api.RMapCache;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.NotNull;
import java.util.List;

@Service
public class UserServiceImlp implements IUserService,InitializingBean {

    private volatile  RBloomFilter<Long> bloomFileter;

    @Autowired
    private volatile  RedissonClient redissonClient;

    private volatile static RMapCache<Long,MiaoshaUser> rMapCache;

    private volatile static RLock rLock;

    public static final String COOKI_NAME_TOKEN = "token";

    @Autowired
    private  MiaoshaUserMapper miaoshaUserMapper;
    //疑问:既然布隆过滤器和redis都是在同一个服务端,为什么还需要布隆过滤器
    //答:如果本人有大量书籍,能够快速判断自己是否具有该书籍查找是否更好一些
    @Override
    public void afterPropertiesSet() throws Exception {
        bloomFileter   = redissonClient.getBloomFilter("BloomFilter");
        bloomFileter.tryInit(10000000,0.1);
        List<Long> idList = miaoshaUserMapper.getMiaoShaoUserList();
        //foreach循环处理链表具有更好的效率 在数组上相对慢一点但是不会慢很多
        // 查询出所有数据,添加到布隆过滤器
        for (long temp:idList){
            bloomFileter.add(temp);
        }
        rMapCache = redissonClient.getMapCache("user");
        //获取用户表操作的锁
        rLock = redissonClient.getLock("user");
    }


    /**
     * 通过id来进行获取,首先看这个id是否存在于布隆过滤器
     *
     * 如何保证多线程安全
     *    先从缓存中获取,如果获取不成功,加锁再从缓存中获取
     *    由于这个缓存是缓存在redis中,所以采用分布式锁进行获取
     * */
    public MiaoshaUser getById(@NotNull long id) {
        if(bloomFileter.contains(id)){
            //如果数据存在布隆过滤则进行查缓存,查数据库
            MiaoshaUser miaoshaUser = rMapCache.get(id);
            if (miaoshaUser==null){
                try{
                    rLock.lock();
                    //加锁是为了防止缓存击穿,防止同时多个用户查询,在这里更好的办法是控制连接数量
                    miaoshaUser = rMapCache.get(id);
                    if (miaoshaUser==null){
                        //查询数据库
                        miaoshaUser = miaoshaUserMapper.getById(id);
                        rMapCache.put(id,miaoshaUser);
                    }
                }finally {
                    rLock.unlock();
                }
            }
        }
        //如果数据不存在缓存中,则直接返回
        return null;
    }
    @Override
    public String login(HttpServletResponse response, LoginVo loginVo) {
        if (loginVo==null){
            throw new MsgException(CodeMsg.SERVER_ERROR);
        }
        String mobile = loginVo.getMobile();
        String password = loginVo.getPassword();

        if (password==null||mobile==null){
            throw new MsgException(CodeMsg.SERVER_ERROR);
        }
        //根据手机号获取用户
        MiaoshaUser user = getById(Long.parseLong(mobile));
        if(user == null) {
            throw new MsgException(CodeMsg.MOBILE_NOT_EXIST);
        }

        //验证密码
        String dbPass = user.getPassword();
        if(!password.equals(dbPass)) {
            throw new MsgException(CodeMsg.PASSWORD_ERROR);
        }

        //生成cookie
        String token = UUIDUtil.uuid();
        addCookie(response, token, user);
        return null;
    }

    /**
     * 登陆成功则将用户信息添加到cookie
     * */
    private void addCookie(HttpServletResponse response, String token, MiaoshaUser user) {
        RedissonUtil.set(Info.userToken,token,user);
        Cookie cookie = new Cookie(COOKI_NAME_TOKEN, token);
        cookie.setMaxAge( 3600*24 * 2);
        cookie.setPath("/");
        response.addCookie(cookie);
    }


}
