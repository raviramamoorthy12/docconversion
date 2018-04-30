package com.webtrixs.docconversion;

import java.io.File;
import java.net.URI;
import java.util.Arrays;
import java.util.List;

import org.apache.cxf.Bus;
import org.apache.cxf.endpoint.Server;
import org.apache.cxf.interceptor.Interceptor;
import org.apache.cxf.interceptor.LoggingInInterceptor;
import org.apache.cxf.interceptor.LoggingOutInterceptor;
import org.apache.cxf.jaxrs.JAXRSServerFactoryBean;
import org.apache.cxf.message.Message;
import org.codehaus.jackson.jaxrs.JacksonJsonProvider;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationHome;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.webtrixs.docconversion.service.DocConversionService;
import ch.tocco.wkhtmltopdf.binary.WkHtmlToPdfBinary;

@SpringBootApplication
public class DocconversionApplication {
	
	@Autowired
	public Bus bus;
	
	 @Autowired
     private BeanFactory beanFactory;
	
	public static void main(String[] args) {
		SpringApplication.run(DocconversionApplication.class, args);
		System.out.println("App Started");
		
		
	}
	
	
	

	@Bean
	public Server rsServer() {
		JAXRSServerFactoryBean endPoint = new JAXRSServerFactoryBean();
		endPoint.setBus(bus);
		DocConversionService docConversionService = (DocConversionService) beanFactory.getBean("docConversionService");
		endPoint.setServiceBeans(Arrays.<Object>asList(docConversionService));
		endPoint.setAddress("/docconversion");
		endPoint.setProvider(new JacksonJsonProvider());
		//endPoint.setFeatures(Arrays.asList(createOpenApiFeature(), new LoggingFeature()));
		endPoint.setInInterceptors(Arrays.<Interceptor<? extends Message>>asList( new LoggingInInterceptor()));
		endPoint.setOutInterceptors(Arrays.<Interceptor<? extends Message>>asList( new LoggingOutInterceptor()));

		return endPoint.create();
		
		
	}
	
	
	/*public OpenApiFeature createOpenApiFeature() {
        OpenApiFeature openApiFeature = new OpenApiFeature();
        openApiFeature.setPrettyPrint(true);
        OpenApiCustomizer customizer = new OpenApiCustomizer();
        customizer.setDynamicBasePath(true);
        openApiFeature.setCustomizer(customizer);
        openApiFeature.setTitle("Spring Boot CXF REST Application");
        openApiFeature.setContactName("The Apache CXF team");
        openApiFeature.setDescription("This sample project demonstrates how to use CXF JAX-RS services"
                + " with Spring Boot. This demo has two JAX-RS class resources being"
                + " deployed in a single JAX-RS endpoint.");
        openApiFeature.setVersion("1.0.0");
        return openApiFeature;
    }*/
	 
}
