package com.lxdmp.springtest.controller;

import java.math.BigDecimal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.beans.factory.annotation.Autowired;
import com.lxdmp.springtest.domain.Product;
import com.lxdmp.springtest.service.ProductService;

import com.lxdmp.springtest.domain.CustomFormatTestObj;
import com.lxdmp.springtest.formatter.CustomFormatTestFormatter;

@Controller
public class ProductController
{
	@Autowired
	private ProductService productService;

	@RequestMapping("/products")
	public String list(Model model)
	{
		model.addAttribute("products", productService.getAllProducts());
		return "products";
	}

	@RequestMapping("/products/{category}")
	public String listByCategory(
		Model model, 
		@PathVariable("category") String category
	)
	{
		model.addAttribute("products", productService.getProductsByCategory(category));
		return "products";
	}

	@RequestMapping("/product")
	public String listById(
		Model model, 
		@RequestParam("id") String productId
	)
	{
		model. addAttribute("product", productService.getProductById(productId));
		return "product";
	}

	@RequestMapping("/format")
	@ResponseBody
	public String testFormat(
		@RequestParam(value="data", required=false, defaultValue=CustomFormatTestFormatter.dummy) CustomFormatTestObj param
	)
	{
		// @RequestParam注解的defaultValue属性不能为空,可设定为一默认值在Formatter中再进一步处理.
		return param.getId();
	}

	@RequestMapping("/update/stock")
	public String updateStock(Model model)
	{
		productService.updateAllStock();
		return "redirect:/products";
	}

	// 添加product
	@RequestMapping(value="/products/add", method=RequestMethod.GET)
	public String getAddNewProductForm(Model model)
	{
		Product newProduct = new Product();
		model.addAttribute("newProduct", newProduct);
		return "addProduct";
	}

	@RequestMapping(value="/products/add", method=RequestMethod.POST)
	public String processAddNewProductForm(
		@ModelAttribute("newProduct") Product newProduct)
	{
		productService.addProduct(newProduct);
		//return "redirect:/products";
		String redirect_url = String.format("redirect:/product?id=%s", newProduct.getProductId());
		return redirect_url;
	}
}

