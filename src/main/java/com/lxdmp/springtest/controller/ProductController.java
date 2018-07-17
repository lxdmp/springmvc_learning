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

import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.validation.BindingResult;
//import org.apache.commons.lang.StringUtils;
import org.springframework.util.StringUtils;

import org.apache.log4j.Logger;

@Controller
public class ProductController
{
	private static final Logger logger = Logger.getLogger(ProductController.class);

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
		// 查询参数指定了"第几页"与"每页多少条"(两者有默认参数),还需指定共有多少条记录.
		paginator.setTotalCount(productService.getAllProductsNum());
		logger.info(paginator.toString());

		// 分页显示Product
		List<Product> products = productService.getProductsByPage(paginator);
		if(products==null || products.isEmpty())
			throw new NoProductsFoundException();
		paginator.setItems(products);
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
	@InitBinder
	public void initializeBinder(WebDataBinder binder)
	{
		/*
		// 设置允许绑定的字段
		binder.setAllowedFields(
			"productId",
			"name",
			"unitPrice",
			"description",
			"manufacturer",
			"category",
			"unitsInStock",
			"condition"
		);
		*/
		// paginator也需要绑定,故只设置不允许绑定的字段.
		binder.setDisallowedFields(
			"unitsInStock", 
			"unitsInOrder", 
			"discontinued"
		);
	}

	@RequestMapping(value="/products/add", method=RequestMethod.GET)
	public String getAddNewProductForm(Model model)
	{
		Product newProduct = new Product();
		model.addAttribute("newProduct", newProduct);
		return "addProduct";
	}

	@RequestMapping(value="/products/add", method=RequestMethod.POST)
	public String processAddNewProductForm(
		@ModelAttribute("newProduct") Product newProduct, 
		BindingResult bindingResult
	)
	{
		String[] suppressedFields = bindingResult.getSuppressedFields();
		if(suppressedFields.length>0)
		{
			throw new RuntimeException(
				"Attempting to bind disallowed fields : " +
				StringUtils.arrayToCommaDelimitedString(suppressedFields)
			);
		}

		productService.addProduct(newProduct);
		//return "redirect:/products";
		String redirect_url = String.format("redirect:/product?id=%s", newProduct.getProductId());
		return redirect_url;
	}
}

