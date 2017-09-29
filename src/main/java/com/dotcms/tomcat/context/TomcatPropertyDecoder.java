package com.dotcms.tomcat.context;

import org.apache.tomcat.util.IntrospectionUtils.PropertySource;

public class TomcatPropertyDecoder implements PropertySource {

  @Override
  public String getProperty(String key) {
   return (
       System.getProperty(key) !=null) 
       ? System.getProperty(key) 
           : System.getenv(key); 

  }

}
