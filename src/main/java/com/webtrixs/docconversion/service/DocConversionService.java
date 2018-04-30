package com.webtrixs.docconversion.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.cxf.jaxrs.ext.multipart.Attachment;
import org.springframework.stereotype.Service;

import com.webtrixs.docconversion.model.Hmtl2PDF;

@Path("/v1")
@Service
public interface DocConversionService {
	
	@GET
	@Produces(MediaType.TEXT_PLAIN)
	public boolean isHealthy() ;
	
	
	/*
	@POST
	@Path("/convert")
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	public Response convert(List<Attachment> attachments, @Context HttpServletRequest request) throws Exception ;
*/
	
	@POST
	@Path("/converthtml")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response convert(Hmtl2PDF html2pdfRequ) throws Exception ;

	
}
