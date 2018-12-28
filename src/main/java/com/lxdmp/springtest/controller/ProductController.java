package com.lxdmp.springtest.controller;

import java.util.List;
import java.math.BigDecimal;
import java.io.File;
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
import com.lxdmp.springtest.entity.Product;
import com.lxdmp.springtest.service.ProductService;

import com.lxdmp.springtest.entity.CustomFormatTestObj;
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

import org.springframework.web.multipart.MultipartFile;
import com.lxdmp.springtest.utils.UploadUtils;

import javax.validation.Valid;
import org.springframework.validation.Validator;
import com.lxdmp.springtest.validator.UnitsInStockValidator;

import org.springframework.security.access.prepost.PreAuthorize;

@Controller
public class ProductController
{ 
	private static final Logger logger = Logger.getLogger(ProductController.class);

	@Autowired
	private ProductService productService;

	@Autowired
	private UnitsInStockValidator unitsInStockValidator;

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
		//logger.info(paginator.toString());

		// 分页显示Product
		List<Product> products = productService.getProductsByPage(
			paginator.getCurrentPage(), 
			paginator.getPageSize()
		);
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

	@PreAuthorize("hasAuthority('CUSTOM_FORMAT')")
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
	@InitBinder("newProduct")
	public void initPostedProductBinder(WebDataBinder binder)
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
		// 设置不允许绑定的字段.
		binder.setDisallowedFields(
			"unitsInStock", 
			"unitsInOrder", 
			"discontinued"
		);

		/*
		 * setValidator会禁用JSR-303的各类Bean Validator
		 */
		//binder.setValidator(unitsInStockValidator);
		binder.addValidators(new Validator[]{
			unitsInStockValidator
		});
	}

	@PreAuthorize("hasAuthority('ADD_PRODUCT')")
	@RequestMapping(value="/products/add", method=RequestMethod.GET)
	public String getAddNewProductForm(Model model)
	{
		Product newProduct = new Product();
		model.addAttribute("newProduct", newProduct);
		return "addProduct";
	}

	@RequestMapping(value="/products/add", method=RequestMethod.POST)
	public String processAddNewProductForm(
		@ModelAttribute("newProduct") @Valid Product newProduct, 
		BindingResult bindingResult, 
		HttpServletRequest request
	)
	{
		// - 绑定的字段是否合法
		if(bindingResult.hasErrors())
		{
			return "addProduct";
		}

		String[] suppressedFields = bindingResult.getSuppressedFields();
		if(suppressedFields.length>0)
		{
			throw new RuntimeException(
				"Attempting to bind disallowed fields : " +
				StringUtils.arrayToCommaDelimitedString(suppressedFields)
			);
		}

		// - 另存提交的图片
		String rootDirectory = request.getSession().getServletContext().getRealPath("/");
		String[] paths = new String[] {
			rootDirectory, "resources", "images", newProduct.getProductId() + ".png"
		};
		String imageSavePath = org.apache.commons.lang.StringUtils.join(paths, File.separator);
		MultipartFile productImage = newProduct.getProductImage();
		UploadUtils.saveProductImage(productImage, imageSavePath);

		// - 重定向到新添加的产品页面
		productService.addProduct(newProduct);
		//return "redirect:/products";
		String redirect_url = String.format("redirect:/product?id=%s", newProduct.getProductId());
		return redirect_url;
	}
}

