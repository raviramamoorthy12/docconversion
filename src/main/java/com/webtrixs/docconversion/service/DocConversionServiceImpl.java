package com.webtrixs.docconversion.service;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.List;

import javax.activation.DataHandler;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.StreamingOutput;

import org.apache.commons.io.IOUtils;
import org.apache.cxf.jaxrs.ext.multipart.Attachment;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.webtrixs.docconversion.model.Hmtl2PDF;
import com.webtrixs.docconversion.pdf.PDFGenerationService;

@Service("docConversionService")
public class DocConversionServiceImpl implements DocConversionService {
	  @Autowired
      private BeanFactory beanFactory;
	  
Logger logger = LoggerFactory.getLogger(DocConversionServiceImpl.class);
	
	public boolean isHealthy() {
		
		return true;
	}

	
	public Response convert(List<Attachment> attachments, HttpServletRequest request) throws Exception {
		Response res = null; 
		InputStream stream = null;
		 for (Attachment attachment : attachments) {
	         DataHandler handler = attachment.getDataHandler();
	         try {
	        	 
	        	  stream = handler.getInputStream();
	            MultivaluedMap<String, String> map = attachment.getHeaders();
	            System.out.println("fileName Here" + getFileName(map));
	            String requestHtml = IOUtils.toString(stream, StandardCharsets.UTF_8.name());
	            
	            res =   doConversion(requestHtml,"convertedpdf");
	              
	         }
	           
	        
	         finally {
	        	 if(stream != null) {
	        		 try {
						stream.close();
					} catch (IOException e) {
						
					}
	        	 }
	        	 
	         }
	      }

		 
		
		 
	      return res;
	}

	
	
	
	
	private Response doConversion(String html,String fileName ) {
		
        String escapedhtml = null;
      //  String fileName = "convertedFile";
        if(null != html) {
                
               escapedhtml =  org.apache.commons.lang.StringEscapeUtils.unescapeHtml(html);
        }
        byte [] bytes = null;
        
        try {
               
      	  PDFGenerationService  pdfGenerationService =   (PDFGenerationService) beanFactory.getBean("pdfGenerationService");
      	  bytes = pdfGenerationService.generatePdf(escapedhtml);
               //FileUtils.writeByteArrayToFile(new File("/vc2coma2121674n/app/wkhtml2pdf/mail_"+System.currentTimeMillis()+".pdf"), bytes);
        } catch (Exception e) {
               logger.error("Error while generating pdf",e.getMessage(),e);
               throw new RuntimeException("Error while generating pdf",e);
        }
        final byte [] pdfBytes =bytes;
        
        StreamingOutput fileStream =  new StreamingOutput()
        {
            @Override
            public void write(java.io.OutputStream output) throws IOException, WebApplicationException
            {
                try
                {
                   
                    output.write(pdfBytes);
                    output.flush();
                }
                catch (Exception e)
                {
                    throw new WebApplicationException();
                }
            }
        };
        return Response.ok(fileStream, "application/pdf")
                      .header("content-disposition","attachment; filename="+fileName+".pdf")
                      .build();
		
	}
	
	
	private String getFileName(MultivaluedMap<String, String> header) {
	      String[] contentDisposition = header.getFirst("Content-Disposition").split(";");
	      for (String filename : contentDisposition) {
	         if ((filename.trim().startsWith("filename"))) {
	            String[] name = filename.split("=");
	            String exactFileName = name[1].trim().replaceAll("\"", "");
	            return exactFileName;
	         }
	      }
	      return "unknown";
	   }

	@Override
	public Response convert(Hmtl2PDF html2Pdf) throws Exception {
		// TODO Auto-generated method stub
		return doConversion(html2Pdf.getHtml(),html2Pdf.getPdfFileName());
	}

	


	
}
