package com.shop.cart.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSON;
import com.shop.bean.OmsCartItem;
import com.shop.bean.PmsSkuInfo;
import com.shop.service.CartService;
import com.shop.service.SkuService;
import com.shop.util.CookieUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Controller
public class CartController {

    @Reference
    SkuService skuService;

    @Reference
    CartService cartService;

    @RequestMapping("/success")
    public String index(){
        return "success";
    }

    @RequestMapping("/addToCart")
    public String addToCart(int num, String skuId, HttpServletRequest request, HttpServletResponse response, RedirectAttributes modelMap){
        PmsSkuInfo skuInfo = skuService.getSkuBySkuId(skuId);
        List<OmsCartItem> omsCartItems = new ArrayList<>();
        OmsCartItem omsCartItem = new OmsCartItem();
        omsCartItem.setCreateDate(new Date());
        omsCartItem.setPrice(skuInfo.getPrice());
        omsCartItem.setProductCategoryId(skuInfo.getCatalog3Id());
        omsCartItem.setProductId(skuInfo.getProductId());
        omsCartItem.setProductName(skuInfo.getSkuName());
        omsCartItem.setQuantity(new BigDecimal(num));
        omsCartItem.setProductSkuId(skuId);
        omsCartItem.setProductPic(skuInfo.getSkuDefaultImg());

        Object user = request.getSession().getAttribute("user");
//        user = new Object();
        if (user != null){
            String memberId = "1";
            omsCartItem.setMemberId(memberId);
            OmsCartItem cartItem = cartService.getCartItemByMembetIdAndSkuId(memberId,skuId);
            if (cartItem != null){
                cartItem.setQuantity(cartItem.getQuantity().add(new BigDecimal(num)));
                cartService.updateCartItem(cartItem);
            }else{
                cartService.addCartItem(omsCartItem);
                cartItem = omsCartItem;
                cartItem.setIsChecked("1");
            }
            cartItem.setTotalPrice(cartItem.getPrice().multiply(cartItem.getQuantity()));
            cartService.flushCartCache(cartItem);
        }else{
            boolean flag = true;
            String cartList = CookieUtil.getCookieValue(request, "cartList", true);
            if (StringUtils.isNotBlank(cartList)){
                omsCartItems = JSON.parseArray(cartList,OmsCartItem.class);
                for (OmsCartItem cartItem : omsCartItems) {
                    if (cartItem.getProductSkuId().equals(skuId)){
                        cartItem.setQuantity(cartItem.getQuantity().add(new BigDecimal(num)));
                        cartItem.setTotalPrice(cartItem.getPrice().multiply(cartItem.getQuantity()));
                        flag = false;
                    }
                }
            }
            if (flag){
                omsCartItem.setTotalPrice(omsCartItem.getPrice().multiply(omsCartItem.getQuantity()));
                omsCartItem.setIsChecked("1");
                omsCartItems.add(omsCartItem);
            }
            CookieUtil.setCookie(request,response,"cartList", JSON.toJSONString(omsCartItems),60*60*72,true);
        }
        modelMap.addFlashAttribute("skuInfo",skuInfo);
        modelMap.addFlashAttribute("skuNum",num);
        return "redirect:/success";

    }

    @RequestMapping("/cartList")
    public String cartList(ModelMap modelMap, HttpSession session,HttpServletRequest request){
        Object user = session.getAttribute("user");
        String memberId = "1";
//        user = new Object();
        if (user != null){
            modelMap.put("cartList",cartService.getFormCache(memberId));
        }else{
            String cartList = CookieUtil.getCookieValue(request, "cartList", true);
            if (StringUtils.isNotBlank(cartList)) {
                modelMap.put("cartList",JSON.parseArray(cartList, OmsCartItem.class));
            }
        }
        return "cartList";
    }

    @RequestMapping("/checkCart")
    public String checkCart(String isChecked, String skuId,BigDecimal quantity, ModelMap modelMap, HttpSession session, HttpServletRequest request,HttpServletResponse response){
        Object user = session.getAttribute("user");
        String memberId = "1";
//        user = new Object();
        if(user != null){
            OmsCartItem oneFormCache = cartService.getOneFormCache(memberId, skuId);
            oneFormCache.setIsChecked(isChecked);
            oneFormCache.setQuantity(quantity);
            oneFormCache.setTotalPrice(oneFormCache.getPrice().multiply(oneFormCache.getQuantity()));
            cartService.flushCartCache(oneFormCache);
            modelMap.put("cartList",cartService.getFormCache(memberId));
        }else{
            String cartList = CookieUtil.getCookieValue(request, "cartList", true);
            if (StringUtils.isNotBlank(cartList)) {
                List<OmsCartItem> omsCartItems = JSON.parseArray(cartList, OmsCartItem.class);
                for (OmsCartItem cartItem : omsCartItems) {
                    if (cartItem.getProductSkuId().equals(skuId)){
                        cartItem.setIsChecked(isChecked);
                        cartItem.setQuantity(quantity);
                        cartItem.setTotalPrice(cartItem.getPrice().multiply(cartItem.getQuantity()));
                    }
                }
                CookieUtil.setCookie(request,response,"cartList", JSON.toJSONString(omsCartItems),60*60*72,true);
                modelMap.put("cartList",omsCartItems);
            }
        }
        return "inner";
    }
}
