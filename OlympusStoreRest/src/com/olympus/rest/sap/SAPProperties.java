package com.olympus.rest.sap;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

public class SAPProperties {
    public final static String filePath = "./WebContent/META-INF/sap.properties";
    
    private static SAPProperties instance = null;
    
    private SAPProperties() {}
    
    public static synchronized SAPProperties getInstanceOf() {
        if(instance == null)
            instance = new SAPProperties();
        return instance;
    }
    
    public String getUser() throws FileNotFoundException, IOException {
        Properties prop = SAPProperties.createProperties();
        return prop.getProperty("user");
    }
    
    public String getPassword() throws FileNotFoundException, IOException {
        Properties prop = SAPProperties.createProperties();
        return prop.getProperty("password");
    }
    
    public String getIP() throws FileNotFoundException, IOException {
        Properties prop = SAPProperties.createProperties();
        return prop.getProperty("ip");
    }
    
    public String getMandant() throws FileNotFoundException, IOException {
        Properties prop = SAPProperties.createProperties();
        return prop.getProperty("mandant");
    }
    
    public String getLanguage() throws FileNotFoundException, IOException {
        Properties prop = SAPProperties.createProperties();
        return prop.getProperty("language");
    }
    
    public String getSystemNumber() throws FileNotFoundException, IOException {
        Properties prop = SAPProperties.createProperties();
        return prop.getProperty("systemNumber");
    }
    
    private static Properties createProperties() throws FileNotFoundException, IOException {
        Properties prop = new Properties();
        FileInputStream fis = new FileInputStream(SAPProperties.filePath);
        prop.load(fis);
        fis.close();
        return prop;
    }
}
