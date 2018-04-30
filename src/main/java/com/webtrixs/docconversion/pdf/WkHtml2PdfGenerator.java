package com.webtrixs.docconversion.pdf;

import java.io.File;
import java.net.URI;
import java.net.URL;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.github.jhonnymertz.wkhtmltopdf.wrapper.Pdf;
import com.github.jhonnymertz.wkhtmltopdf.wrapper.configurations.WrapperConfig;

import ch.tocco.wkhtmltopdf.binary.WkHtmlToPdfBinary;

@Component("wkHtml2PdfGenerationService")
@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class WkHtml2PdfGenerator {
	
	
	 private static final Logger logger = LoggerFactory.getLogger(WkHtml2PdfGenerator.class);
     private static String WKHTML2PDF = "wkhtmltopdf";
     private static String OS_WINDOWS = "windows";
     private static String OS_UNIX = "unix";
     private static String OS_MAC= "mac";
     private static String HYPEN ="-";
     private static String EXE_FILE_EXTENSION = ".exe";
     private String wkhtml2PdfFileName = null;
     private String wkhtml2PdfFilePath = null;
    
     public WkHtml2PdfGenerator() {
                     wkhtml2PdfFilePath =   getWkhtml2PdfFileBasedOnOS();
                    
     }
     
     public String getWkhtml2PdfFileBasedOnOS() {
         String OS = System.getProperty("os.name").toLowerCase();
         if (OS.indexOf("win") >= 0) {
                         wkhtml2PdfFileName = WKHTML2PDF+HYPEN+OS_WINDOWS+EXE_FILE_EXTENSION;
         } else if (OS.indexOf("mac") >= 0) {
                         wkhtml2PdfFileName = WKHTML2PDF+HYPEN+OS_MAC;
         } else if (OS.indexOf("nix") >= 0 || OS.indexOf("nux") >= 0 || OS.indexOf("aix") > 0 ) {
                         wkhtml2PdfFileName = WKHTML2PDF+HYPEN+OS_UNIX;
         } else if (OS.indexOf("sunos") >= 0) {
                         wkhtml2PdfFileName = WKHTML2PDF+HYPEN+OS_UNIX;
         } else {
                         logger.info("Your OS is not support!!");
         }
         ClassLoader classLoader = this.getClass().getClassLoader();
         URL url = classLoader.getResource(wkhtml2PdfFileName);
         String path  = url.getPath();
         File file = new File(path);
         boolean isExecuteable= file.canExecute();
         logger.debug("Is {} executeable {} ",wkhtml2PdfFileName,isExecuteable);
         if(!isExecuteable) {
                         file.setExecutable(true, false);
                 file.setReadable(true, false);
                 file.setWritable(true, false);
         }
         boolean isExecuteableAfter= file.canExecute();
         logger.debug("Is {} executeable after setting permission {}",wkhtml2PdfFileName,isExecuteableAfter);
        
         String absolutePath = file.getAbsolutePath();
         logger.debug("File {} Absolute Path {}",wkhtml2PdfFileName,absolutePath);
         return absolutePath;
        
}
public byte[] getPdf(String html) throws Exception {
                          byte[] results = null;
                          URI uri = WkHtmlToPdfBinary.getInstance().getExe();
                          WrapperConfig wc = new WrapperConfig(uri.getPath());
/*                                           XvfbConfig xc = new XvfbConfig();
                         xc.addParams(new Param("--auto-servernum"), new Param("--server-num=1"));
                         xc.addParams(new Param(wkhtml2PdfFilePath));
                         wc.setXvfbConfig(xc);*/
                         Pdf pdf = new Pdf(wc);
                         pdf.addPageFromString(html); 
                         results = pdf.getPDF();
                         //FileUtils.writeByteArrayToFile(new File("C:\\Users\\a501478\\Desktop\\savefiles\\mail"+System.currentTimeMillis()+".pdf"), bytes);
                         logger.info("Byte Array length {}",results.length);
           return results;

}

}
