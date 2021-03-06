package com.taotao.portal.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.taotao.common.pojo.TaotaoResult;
import com.taotao.portal.pojo.Item;
import com.taotao.portal.service.CartService;

/**
 * 购物车管理（以JSON格式存储在Cookie中，登录后同步到Redis中）
 * 1.添加商品到购物车
 * 2.显示购物车列表
 * 3.更新商品数量
 * 4.删除购物车商品
 */
@Controller
@RequestMapping("/cart")
public class CartController {

	@Autowired
	private CartService cartService;
	
	/**
	 * 添加购物车商品
	 */
	@RequestMapping("/add/{itemId}")
	public String addCartItem(@PathVariable Long itemId, Integer itemNum,
			HttpServletRequest request, HttpServletResponse response) {
		TaotaoResult result = cartService.addCartItem(itemId, itemNum, request, response);
		if (result.getStatus() == 200) {
			//跳转到加入购物车成功界面
//			return "cart-success";
			return "redirect:/cart/cart.html";
		}
		return "error/exception";
	}
	
	/**
	 * 展示购物车商品
	 */
	@RequestMapping("/cart")
	public String showCart(Model model, HttpServletRequest request) {
		List<Item> list = cartService.getCatItemList(request);
		model.addAttribute("cartList", list);
		return "cart";
	}

	/**
	 * 修改购物车商品数量
	 * @param itemId 商品的id
	 * @param itemNum 商品的数量
	 */
	@RequestMapping("/update/num/{itemId}/{itemNum}")
	@ResponseBody
	public TaotaoResult updateItemNum(@PathVariable Long itemId, @PathVariable Integer itemNum,
			HttpServletRequest request, HttpServletResponse response) {
		TaotaoResult result = cartService.updateItemNum(itemId, itemNum, request, response);
		return result;
	}

	/**
	 * 删除购物车中指定商品
	 * @param itemId 商品的id
	 */
	@RequestMapping("/delete/{itemId}")
	public String deleteCartItem(@PathVariable Long itemId,
								 HttpServletResponse response, HttpServletRequest request) {
		cartService.deleteCartItem(itemId, request, response);
		return "redirect:/cart/cart.html";
	}
}
