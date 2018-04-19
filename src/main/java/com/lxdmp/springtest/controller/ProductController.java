package com.lxdmp.springtest.controller;

import java.math.BigDecimal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.beans.factory.annotation.Autowired;
import com.lxdmp.springtest.domain.Product;
import com.lxdmp.springtest.service.ProductService;

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

	@RequestMapping("/update/stock")
	public String updateStock(Model model)
	{
		productService.updateAllStock();
		return "redirect:/products";
	}
}

