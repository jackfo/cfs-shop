package com.whpu.controller.miaosha;


import com.miaosha.mapper.GoodsMapper;
import com.miaosha.model.GoodsVo;
import com.miaosha.model.MiaoshaMessage;
import com.miaosha.model.MiaoshaOrder;
import com.miaosha.model.MiaoshaUser;
import com.whpu.constant.Info;
import com.whpu.constant.UserInformation;
import com.whpu.mq.MQSender;
import com.whpu.util.json.CodeMsg;
import com.whpu.util.json.HttpResponse;
import com.whpu.web.controller.BaseController;
import com.whpu.web.pojo.SessionUser;
import org.apache.rocketmq.client.exception.MQBrokerException;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.remoting.exception.RemotingException;
import org.redisson.api.RAtomicLong;
import org.redisson.api.RMap;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;

@Controller
@RequestMapping("/miaosha")
public class MiaoShaController extends BaseController implements InitializingBean {

    @Resource
    GoodsMapper goodsMapper;

    @Autowired
    RedissonClient redissonClient;

    @Autowired
    MQSender mqSender;

    /**
     * 初始化相关商品的数量
     * */
    @Override
    public void afterPropertiesSet() throws Exception {
        List<GoodsVo> goodsList = goodsMapper.listGoodsVo();
        if(goodsList == null) {
            return;
        }
        RMap rMap = redissonClient.getMap(Info.miaoshaGoodsStock);
        RAtomicLong stockRAtomicLong;
        for(GoodsVo goods : goodsList) {
            stockRAtomicLong = redissonClient.getAtomicLong(Info.miaoshaGoodsStock+"goodsId");
            stockRAtomicLong.set(goods.getStockCount());
            localOverMap.put(goods.getId(), false);
        }
    }

    private HashMap<Long,Boolean> localOverMap = new HashMap<Long, Boolean>();

    /**
     * sessionUser做统一管理
     * MiaoShaUser是针对秒杀模块,在秒杀成功通过userId添加到redis服务器
     * */
    @RequestMapping(value="/do_miaosha", method=RequestMethod.POST)
    @ResponseBody
    public HttpResponse<Integer> miaosha(HttpServletRequest request,Model model, long goodsId, String token) throws InterruptedException, RemotingException, MQClientException, MQBrokerException {
        SessionUser sessionUser = sessionUser();
        model.addAttribute("user",sessionUser);
        if (sessionUser==null||sessionUser.getUserId()==null){
            return HttpResponse.error(CodeMsg.SESSION_ERROR);
        }
        RMap<String,MiaoshaUser> miaoshaUserRMap=redissonClient.getMap(UserInformation.miaoShaUser);
        MiaoshaUser miaoshaUser =miaoshaUserRMap.get(sessionUser.getUserId());
        //预减库存
        RAtomicLong stockRAtomicLong = redissonClient.getAtomicLong(Info.miaoshaGoodsStock+"goodsId");
        long stock = stockRAtomicLong.decrementAndGet();
        if(stock < 0) {
            localOverMap.put(goodsId, true);
            return HttpResponse.error(CodeMsg.MIAO_SHA_OVER);
        }

        //内存标记，减少redis访问
        boolean over = localOverMap.get(goodsId);
        if(over) {
            return HttpResponse.error(CodeMsg.MIAO_SHA_OVER);
        }
        //判断是否已经秒杀到了
        RMap<String,MiaoshaOrder> rMap = redissonClient.getMap(MiaoshaOrder.class.getName());
        MiaoshaOrder order = rMap.get(sessionUser.getUserId()+"_"+goodsId);
        //判断是否已经秒杀到了
        if(order != null) {
            return HttpResponse.error(CodeMsg.REPEATE_MIAOSHA);
        }
        MiaoshaMessage mm = new MiaoshaMessage();
        mm.setUser(miaoshaUser);
        mm.setGoodsId(goodsId);
        mqSender.sendMiaoshaMessage(mm);
        return HttpResponse.success(0);//排队中
    }
}
