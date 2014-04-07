package com.olympus.rest.sap;

import java.io.FileNotFoundException;
import java.io.IOException;

import com.sap.mw.jco.JCO;

public class JCOClientCreator {

	private static JCOClientCreator instance = null;
	private static SAPProperties spp = null;
	
	private JCOClientCreator() {
		spp = SAPProperties.getInstanceOf();
	}
	
	public static synchronized JCOClientCreator getInstanceOf() {
		if(instance == null)
			instance = new JCOClientCreator();
		return instance;
	}
	
	public JCO.Client createJCOClient() throws FileNotFoundException, IOException {
		String user = spp.getUser();
		String password = spp.getPassword();
		String ip = spp.getIP();
		String mandant = spp.getMandant();
		String language = spp.getLanguage();
		String systemNumber = spp.getSystemNumber();
		
		return JCO.createClient(mandant, user, password, language, ip, systemNumber);
	}

}
