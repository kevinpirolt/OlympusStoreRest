package com.olympus.rest.sap;

import java.util.ArrayList;
import java.util.Date;

import com.olympus.rest.data.util.Product;
import com.sap.mw.jco.JCO;

public class SAPProductModel {
	/**
	 * nach Typ: latest 3
	 * nach Suchstrig: name
	 * alle daten von einem
	 * 
	 */
	
	private static SAPProductModel instance = null;
	
	private JCO.Client mConnection = null;
	private JCO.Repository mRepository = null;
	
	String userName = null;
	String password = null;
	String ipAddress = null;

	private SAPFunctionPreperator sfp = null;
	
	private SAPProductModel() {sfp = new SAPFunctionPreperator();}
	
	private SAPProductModel(String userName, String password, String ipAddress) {
		this.userName = userName;
		this.password = password;
		this.ipAddress = ipAddress;
		sfp = new SAPFunctionPreperator();
	}
	
	public static SAPProductModel getInstanceOf() {
		if(instance == null)
			instance = new SAPProductModel("BCUSER".toUpperCase(), "MINISAP".toUpperCase(), "");
		return instance;
	}
	
	public void setInformation(String userName, String password, String ipAddress){
		this.userName = userName;
		this.password = password;
		this.ipAddress = ipAddress;
	}
	
	/*private void connect() {
		AnnotationConfiguration configuration= new AnnotationConfiguration("A12"); 
		SessionManager sessionManager = configuration.buildSessionManager();
		
	}*/
	
	public void createConnection() throws Exception {
		String mandant = "000";
		String language = "EN";
		String systemNumber = "00";
				
		mConnection = JCO.createClient(mandant, userName, password, language, ipAddress, systemNumber);
		mConnection.connect();
	}
	
	public void disconnect() {
		if(mConnection != null) {
			mConnection.disconnect();
			mConnection = null;
		}
	}
	
	public void insertProduct(String pname, float price, int initialQuantity, Date reldate,
			String interpret, String type, String genre, String description, String img) throws Exception {
		this.createConnection();
		JCO.Function insert = sfp.getFunction("ZBAPI_OLYMPUS_INSERTPRODUCT", this.mConnection);
		JCO.Function commit = sfp.getCommitFunction(mConnection);
		ArrayList<Object> values = sfp.prepareArrayList(pname, price, initialQuantity, reldate,
				interpret, type, genre, description, img);
		ArrayList<String> names = sfp.prepareStringArrayList("INSNAME", "INSPRICE", "INSQTY", "INSTRELDATE",
				"INSTINTERPRET", "INSTYPE", "INSGENRE", "INSDESCRIPTION", "INSIMG");
		sfp.setInsertParameter(insert, values, names);
		sfp.executeFunction(insert, mConnection);
		sfp.executeFunction(commit, mConnection);
		this.disconnect();
	}
	
	public ArrayList<Product> getAllProducts() throws Exception {
		ArrayList<Product> products = null;
		products = new ArrayList<Product>();
		products.add(new Product(0, 0.20f, "Relapse", 100, new Date(), "Eminem", "Rap", "is Em", "Nope"));
		products.add(new Product(1, 0.30f, "Encore", 100, new Date(), "Eminem", "Rap", "is Em", "Nope"));
		/*this.createConnection();
		
		JCO.Function get = sfp.getFunction("ZBAPI_OLYMPUS_GETPRODUCT", this.mConnection);
		JCO.Function commit = sfp.getCommitFunction(mConnection);
		sfp.executeFunction(get, this.mConnection);
		sfp.executeFunction(commit, this.mConnection);
		products = sfp.getProducts(get);
		
		this.disconnect()*/;
		return products;
	}

	public ArrayList<Product> getProductsByName(String name) throws Exception {
		ArrayList<Product> products = null;
		
		this.createConnection();
		
		JCO.Function get = sfp.getFunction("ZBAPI_OLYMPUS_GETPRODUCTBYNAME", this.mConnection);
		JCO.Function commit = sfp.getCommitFunction(mConnection);
		
		ArrayList<Object> values = sfp.prepareArrayList(name);
		ArrayList<String> names = sfp.prepareStringArrayList("INSNAME");
		sfp.setInsertParameter(get, values, names);
		
		sfp.executeFunction(get, this.mConnection);
		sfp.executeFunction(commit, this.mConnection);
		products = sfp.getProducts(get);
		
		this.disconnect();
		return products;
	}
	
	public ArrayList<Product> getLatestProductsPerType(String type) throws Exception {
		ArrayList<Product> products = null;
		
		this.createConnection();
		
		JCO.Function get = sfp.getFunction("ZBAPI_OLYMPUS_GETPRODUCTLATEST", this.mConnection);
		JCO.Function commit = sfp.getCommitFunction(mConnection);
		
		ArrayList<Object> values = sfp.prepareArrayList(type);
		ArrayList<String> names = sfp.prepareStringArrayList("INSTTYPE");
		sfp.setInsertParameter(get, values, names);
		
		sfp.executeFunction(get, this.mConnection);
		sfp.executeFunction(commit, this.mConnection);
		products = sfp.getProducts(get);
		
		this.disconnect();
		return products;
	}

	/*public void insertProduct(String newPMotivation, String newPName,
			String newPDate, String newDNo) throws Exception {
		JCO.Function insert = this.getFunction("ZBAPI_DEPARTMENT_INSERTPUPIL");
		JCO.Function commit = this.getFunction("BAPI_TRANSACTION_COMMIT");
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		//Date d = sdf.parse(newPDate);
		//SAPConnection.setInsertPupilParameter(insert, newPName, newPMotivation, newPDate, newDNo);
		this.executeFunction(insert);
		this.executeFunction(commit);
		System.out.println("X");
		this.disconnect();
	}*/
}
