package com.whpu.controller.miaosha;


import com.miaosha.mapper.GoodsMapper;
import com.miaosha.model.GoodsVo;
import com.miaosha.model.MiaoshaMessage;
import com.miaosha.model.MiaoshaOrder;
import com.miaosha.model.MiaoshaUser;
import com.whpu.constant.Info;
import com.whpu.mq.MQSender;
import com.whpu.util.convert.UtilObject;
import com.whpu.util.json.CodeMsg;
import com.whpu.util.json.HttpResponse;
import org.apache.rocketmq.client.exception.MQBrokerException;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.remoting.exception.RemotingException;
import org.redisson.api.RAtomicLong;
import org.redisson.api.RMap;
import org.redisson.api.RedissonClient;
import org.redisson.misc.Hash;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;

@Controller
@RequestMapping("/miaosha")
public class MiaoShaController implements InitializingBean {

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

    @RequestMapping(value="/{path}/do_miaosha", method=RequestMethod.POST)
    @ResponseBody
    public HttpResponse<Integer> miaosha(Model model, MiaoshaUser user, long goodsId,
                                         String path) throws InterruptedException, RemotingException, MQClientException, MQBrokerException {
        model.addAttribute("user",user);
        if (user==null){
            return HttpResponse.error(CodeMsg.SESSION_ERROR);
        }

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
        MiaoshaOrder order = rMap.get(user.getId()+"_"+goodsId);
        //判断是否已经秒杀到了
        if(order != null) {
            return HttpResponse.error(CodeMsg.REPEATE_MIAOSHA);
        }
        MiaoshaMessage mm = new MiaoshaMessage();
        mm.setUser(user);
        mm.setGoodsId(goodsId);
        mqSender.sendMiaoshaMessage(mm);
        return HttpResponse.success(0);//排队中
    }
}
