package com.olympus.rest;

import java.util.ArrayList;
import java.util.logging.Level;

import javax.servlet.ServletContext;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import com.olympus.rest.data.util.Product;
import com.olympus.rest.data.util.ProductList;
import com.olympus.rest.sap.SAPProductModel;
import com.sun.istack.internal.logging.Logger;


@Path("/olympusrest/")
public class OlympusRest {
	
	@Context
	private ServletContext context;

	@GET
	@Path("getallproducts")
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON, MediaType.TEXT_XML, MediaType.TEXT_HTML })
	public ProductList getAllBooks() {
		ArrayList<Product> products = new ArrayList<Product>();
		ProductList pl = new ProductList();
		try {
			SAPProductModel spm = SAPProductModel.getInstanceOf(context);
			products = spm.getAllProducts();
			pl.setProducts(products);
		} catch (Exception e) {
			e.printStackTrace();
			Logger.getLogger(OlympusRest.class).log(Level.SEVERE, "Problems Connecting to SAP", e);
		}
		return pl;
	}
	
	@GET
	@Path("getproductsbyname/{productname}")
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON, MediaType.TEXT_XML, MediaType.TEXT_HTML })
	public ProductList getProductsByName(@PathParam("productname") String productName) {
		System.out.println("--------->SearchName OlympusRest: " + productName);
		ArrayList<Product> products = new ArrayList<Product>();
		ProductList pl = new ProductList();
		try {
			SAPProductModel spm = SAPProductModel.getInstanceOf(context);
			products = spm.getProductsByName("%" + productName + "%");
			pl.setProducts(products);
		} catch (Exception e) {
			e.printStackTrace();
			Logger.getLogger(OlympusRest.class).log(Level.SEVERE, "Problems Connecting to SAP", e);
		}
		return pl;
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
			Logger.getLogger(OlympusRest.class).log(Level.SEVERE, "Problems Connecting to SAP", e);
		}
		return pl;
	}
	//************************************************INSERT*********************************************
	@POST
	@Path("insertproduct")
	@Consumes({MediaType.TEXT_HTML, MediaType.TEXT_XML})
	public String insertProduct(Product product) {
		String outcome = "Product inserted";
		try {
			SAPProductModel spm = SAPProductModel.getInstanceOf(context);
			spm.insertProduct(product);
		} catch (Exception e) {
			e.printStackTrace();
			outcome = "ERROR600_An error occured: " + e.getMessage();
		}
		return outcome;
	}
	
	@DELETE
	@Path("deleteproduct")
	@Consumes({MediaType.TEXT_HTML, MediaType.TEXT_XML})
	public String deleteProduct(Product product){
		String outcome = "Product " + product.getId() + " " + product.getName() + " deleted";
		SAPProductModel spm = SAPProductModel.getInstanceOf(context);
		try {
			spm.deleteProduct(product);
		} catch (Exception e) {
			e.printStackTrace();
			outcome = "ERROR_600: " + e.getMessage();
		}
		return outcome;
	}
	//************************************************UPDATE********************************************
	@PUT
	@Path("updateqty")
	@Consumes({MediaType.TEXT_HTML, MediaType.TEXT_XML})
	public String updateQty(Product product) {
		String outcome = "There are still products";
		SAPProductModel spm = SAPProductModel.getInstanceOf(context);
		try {
			int remaining = spm.updateQuantity(product);
			System.out.println("Products remaining: " + remaining);
			/*if(remaining < 0) {
				outcome = "ERROR500_No more products remaining";
				Product rollBackProduct = new Product(product.getId(), 0, null, (product.getQuantity()*-1), null, null, null, null, null, null);
				spm.updateQuantity(rollBackProduct);
			}*/
		} catch (Exception e) {
			e.printStackTrace();
			outcome = "ERROR600_An error occured: " + e.getMessage();
		}
		System.out.println("OUTCOME: " + outcome);
		return outcome;
	}

}
