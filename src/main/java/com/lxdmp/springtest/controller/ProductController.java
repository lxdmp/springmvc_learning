package com.lxdmp.springtest.controller;

import java.util.List;
import java.math.BigDecimal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.servlet.ModelAndView;
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

import com.lxdmp.springtest.utils.Paginator;

import com.lxdmp.springtest.exception.NoProductsFoundException;
import com.lxdmp.springtest.exception.ProductNotFoundException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import javax.servlet.http.HttpServletRequest;

@Controller
public class ProductController
{
	@Autowired
	private ProductService productService;

	@RequestMapping("/products")
	public String list(Model model)
	{
		List<Product> products = productService.getAllProducts();
		if(products==null || products.isEmpty())
			throw new NoProductsFoundException();
		model.addAttribute("products", products);
		return "products";
	}

	@RequestMapping("/products/list")
	public String productInList(
		Model model, 
		@ModelAttribute("paginator") Paginator<Product> paginator
	)
	{
		// 分页显示Product
		List<Product> products = productService.getProductsByPage(paginator);
		if(products==null || products.isEmpty())
			throw new NoProductsFoundException();
		model.addAttribute("products", products);
		return "productsInList";
	}

	@RequestMapping("/products/{category}")
	public String listByCategory(
		Model model, 
		@PathVariable("category") String category
	)
	{
		List<Product> products = productService.getProductsByCategory(category);
		if(products==null || products.isEmpty())
			throw new NoProductsFoundException();
		model.addAttribute("products", products);
		return "products";
	}

	@RequestMapping("/product")
	public String listById(
		Model model, 
		@RequestParam("id") String productId
	)
	{
		Product product = productService.getProductById(productId);
		if(product==null)
			throw new ProductNotFoundException(productId);
		model.addAttribute("product", product);
		return "product";
	}

	@ExceptionHandler(ProductNotFoundException.class)
	public ModelAndView handleProductNotFoundError(
		HttpServletRequest req, ProductNotFoundException exception)
	{
		ModelAndView model = new ModelAndView();
		model.addObject("invalidProductId", exception.getProductId());
		model.addObject("exception", exception);
		model.addObject("url", req.getRequestURL()+"?"+req.getQueryString());
		model.addObject("jumpDelay", 5);
		model.setViewName("productNotFound");
		return model;
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

