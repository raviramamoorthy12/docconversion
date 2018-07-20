package com.webtrixs.docconversion;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.webtrixs.docconversion.helper.CommonHelper;
import com.webtrixs.docconversion.service.DocConversionService;


@RunWith(SpringRunner.class)
@SpringBootTest
public class DocconversionApplicationTests {
	
	@Autowired
	private DocConversionService doc;
	
	@Autowired
	private CommonHelper com;

	@Test
	public void contextLoads() {
		System.out.println(doc);
		System.out.println(com);
	}

}
