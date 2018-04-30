package com.webtrixs.docconversion.pdf;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component("pdfGenerationService")
@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class PDFGenerationService {

	  @Autowired
      private BeanFactory beanFactory;
     
     
      public PDFGenerationService() {
                     
      }
     
     
      public byte[] generatePdf(final String request) throws Exception {
                      WkHtml2PdfGenerator wkHtml2PdfGenerationService  = (WkHtml2PdfGenerator) beanFactory.getBean("wkHtml2PdfGenerationService");
                      //return wkHtml2PdfGenerationService.generatePdf(request);
                      return wkHtml2PdfGenerationService.getPdf(request);
        
        
          

}
}
