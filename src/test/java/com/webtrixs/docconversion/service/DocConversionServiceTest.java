package com.webtrixs.docconversion.service;

import static org.junit.Assert.*;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.InputStream;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.StreamingOutput;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;

import com.google.gson.Gson;
import com.webtrixs.docconversion.helper.CommonHelper;
import com.webtrixs.docconversion.model.Hmtl2PDF;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DocConversionServiceTest {
	
	@TestConfiguration
    static class EmployeeServiceImplTestContextConfiguration {
  
        @Bean
        public DocConversionService docConversionService() {
            return new DocConversionServiceImpl();
        }
    }

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}
	
	@Autowired
	private DocConversionService  docConversionService;
	
	@Autowired
	private CommonHelper  commonHelper;
	
	
	private Gson gson = new Gson();

	@Test
	public void testIsHealthy() {
		assertTrue(docConversionService.isHealthy());
		
	}

	@Ignore
	@Test
	public void testConvertListOfAttachmentHttpServletRequest() {
		fail("Not yet implemented");
	}

	@Test
	public void testConvertHmtl2PDF() throws Exception {
		Hmtl2PDF html2pdfRequ = gson.fromJson(commonHelper.readRequestFileAsStr("html2pdf.json"), Hmtl2PDF.class);
		Response response = docConversionService.convert(html2pdfRequ);
		StreamingOutput entity=(StreamingOutput)response.getEntity();
		ByteArrayOutputStream baos=new ByteArrayOutputStream();
		entity.write(baos);
		File temp = File.createTempFile("Mail_"+System.currentTimeMillis(), ".pdf"); 
		FileUtils.writeByteArrayToFile(temp, baos.toByteArray());
		assertEquals(200, response.getStatus());
		temp.deleteOnExit();
		
	}

}
