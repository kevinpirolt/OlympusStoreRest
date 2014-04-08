package com.olympus.rest;

import java.util.ArrayList;

import javax.servlet.ServletContext;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import com.olympus.rest.data.util.Product;
import com.olympus.rest.data.util.ProductList;
import com.olympus.rest.sap.SAPProductModel;


@Path("/olympusrest/")
public class OlympusRest {
	
	@Context
	private ServletContext context;

	@GET
	@Path("getallproducts")
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON, MediaType.TEXT_XML, MediaType.TEXT_HTML })
	public ArrayList<Product> getAllBooks() {
		ArrayList<Product> products = new ArrayList<Product>();
		try {
			SAPProductModel spm = SAPProductModel.getInstanceOf(context);
			products = spm.getAllProducts();
		} catch (Exception e) {
			e.printStackTrace();
			products = null;
		}
		return products;
	}
	
	@GET
	@Path("getproductsbyname/{productname}")
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON, MediaType.TEXT_XML, MediaType.TEXT_HTML })
	public ArrayList<Product> getProductsByName(@PathParam("productname") String productName) {
		ArrayList<Product> products = new ArrayList<Product>();
		try {
			SAPProductModel spm = SAPProductModel.getInstanceOf(context);
			products = spm.getProductsByName(productName);
		} catch (Exception e) {
			e.printStackTrace();
			products = null;
		}
		return products;
	}
	
	@GET
	@Path("getlatestproducts/{producttype}")
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON, MediaType.TEXT_XML, MediaType.TEXT_HTML })
	public ProductList getLatestProducts(@PathParam("producttype") String productType) {
		System.out.println("In getLatestProducts");
		ArrayList<Product> products = new ArrayList<Product>();
		ProductList pl = new ProductList();
		try {
			SAPProductModel spm = SAPProductModel.getInstanceOf(context);
			products = spm.getLatestProductsPerType(productType);
			pl.setProducts(products);
		} catch (Exception e) {
			e.printStackTrace();
			products = null;
			pl = null;
		}
		return pl;
	}
	
	@POST
	@Consumes({MediaType.TEXT_HTML, MediaType.TEXT_XML})
	public String insertProduct(Product product) {
		String outcome = "Product inserted";
		try {
			SAPProductModel spm = SAPProductModel.getInstanceOf(context);
			spm.insertProduct(product);
		} catch (Exception e) {
			e.printStackTrace();
			outcome = "An error occured: " + e.getMessage();
		}
		return outcome;
	}

}
