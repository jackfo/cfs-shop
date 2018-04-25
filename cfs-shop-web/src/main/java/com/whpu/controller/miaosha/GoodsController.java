package com.whpu.controller.miaosha;


import com.miaosha.mapper.GoodsMapper;
import com.miaosha.model.GoodsVo;
import com.miaosha.model.MiaoshaUser;
import com.whpu.constant.Info;
import com.whpu.service.IGoodsService;
import com.whpu.service.IRedisService;
import org.redisson.api.RMapCache;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.thymeleaf.context.IContext;
import org.thymeleaf.context.WebContext;
import org.thymeleaf.spring5.view.ThymeleafViewResolver;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Controller
@RequestMapping("/goods")
public class GoodsController {

    @Autowired
    IGoodsService goodServiceImpl;

    @Autowired
    ThymeleafViewResolver thymeleafViewResolver;

    @Autowired
    ApplicationContext applicationContext;

    @Autowired
    RedissonClient redissonClient;

    @Resource
    GoodsMapper goodsMapper;

    @RequestMapping(value="/to_list", produces="text/html")
    @ResponseBody
    public String list(HttpServletRequest request, HttpServletResponse response, Model model, MiaoshaUser user){
       model.addAttribute("user", user);
        //从缓存中取出相应的货物界面 get则不用加锁
       RMapCache<String,String> rMapCache = redissonClient.getMapCache(Info.staticTemplate);
       String html = rMapCache.get("goodslist");
       if(html!=null){
           return html;
       }
       System.out.print("你好");
        //查询出所有的货物
        List<GoodsVo> goodsList = goodServiceImpl.listGoodsVo();
        model.addAttribute("goodsList", goodsList);
        /**
         * @param request 请求
         * @param response 响应
         * @param servletContext
         * @param Locale
         * @param Map
         * @param applicationContext  应用上下文
         * */
        IContext iContext =new WebContext(request,response,request.getServletContext(),request.getLocale(),model.asMap());
        //手动渲染
        html = thymeleafViewResolver.getTemplateEngine().process("goods_list",iContext);
        //将其添加到缓存中去
        rMapCache.put("goodslist",html,3,TimeUnit.SECONDS);
        return html;
    }

    @RequestMapping(value = "/goods_detail",produces = "text/html",method=RequestMethod.GET)
    @ResponseBody
    public String detail2(HttpServletRequest request, HttpServletResponse response, Model model,MiaoshaUser user,
                          long goodsId) {

        //添加用户属性
        model.addAttribute("user", user);
        //从缓存中取出相应的货物界面 get则不用加锁
        RMapCache<String,String> rMapCache = redissonClient.getMapCache(Info.staticTemplate);
        String html = rMapCache.get("goods_detail");
        if(html!=null){
            return html;
        }
        //查找出当前货物的详细信息
        GoodsVo goodsVo = goodsMapper.getGoodsVoByGoodsId(goodsId);
        model.addAttribute("goods",goodsVo);

        //获取货物开始时间与结束时间
        long startAt = goodsVo.getStartDate().getTime();
        long endAt = goodsVo.getEndDate().getTime();
        long now = System.currentTimeMillis();
        int miaoshaStatus = 0;
        int remainSeconds = 0;
        if(now < startAt ) {//秒杀还没开始，倒计时
            miaoshaStatus = 0;
            remainSeconds = (int)((startAt - now )/1000);
        }else  if(now > endAt){//秒杀已经结束
            miaoshaStatus = 2;
            remainSeconds = -1;
        }else {//秒杀进行中
            miaoshaStatus = 1;
            remainSeconds = 0;
        }
        model.addAttribute("miaoshaStatus", miaoshaStatus);
        model.addAttribute("remainSeconds", remainSeconds);
        IContext iContext =new WebContext(request,response,request.getServletContext(),request.getLocale(),model.asMap());
        //手动渲染
        html = thymeleafViewResolver.getTemplateEngine().process("goods_detail",iContext);
        //将其添加到缓存中去
        rMapCache.put("goods_detail",html,3,TimeUnit.SECONDS);
        return html;
    }

}
