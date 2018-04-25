package service;

import com.miaosha.model.GoodsVo;
import com.miaosha.model.MiaoshaUser;
import com.miaosha.model.OrderInfo;

import javax.annotation.Resource;

public interface IOrderService {

      public OrderInfo miaosha(MiaoshaUser miaoshaUser, GoodsVo goodsVo);

}
