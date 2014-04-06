package com.olympus.rest;

import java.util.ArrayList;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.olympus.rest.data.util.Product;
import com.olympus.rest.sap.SAPProductModel;


@Path("/olympusrest/")
public class OlympusRest {

	@GET
	@Path("getallbooks")
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON, MediaType.TEXT_XML, MediaType.TEXT_HTML })
	public ArrayList<Product> getAllBooks() {
		ArrayList<Product> products = new ArrayList<Product>();
		SAPProductModel spm = SAPProductModel.getInstanceOf();
		try {
			products = spm.getAllProducts();
		} catch (Exception e) {
			e.printStackTrace();
			products = null;
		}
		return products;
	}

}
