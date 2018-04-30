package com.webtrixs.docconversion.model;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Hmtl2PDF {
	private String html;
	public String getHtml() {
		return html;
	}
	public void setHtml(String html) {
		this.html = html;
	}
	public String getPdfFileName() {
		return pdfFileName;
	}
	public void setPdfFileName(String pdfFileName) {
		this.pdfFileName = pdfFileName;
	}
	private String pdfFileName;

}
