package com.newsclan.crud;

import java.io.IOException;
import java.io.InputStream;

import jakarta.servlet.http.HttpServletRequest;

import org.apache.commons.fileupload.RequestContext;

public  class RC implements RequestContext{         
    HttpServletRequest request = null;
	private javax.servlet.http.HttpServletRequest request2;
    public RC(HttpServletRequest request) {
        this.request=request;
    }
    public RC(javax.servlet.http.HttpServletRequest request) {
        this.request2=request;
    }

    @Override
    public String getCharacterEncoding() {
    	if(request!=null)
    		return request.getCharacterEncoding();
    	else 
    		return request2.getCharacterEncoding();
    }

    @Override
    public String getContentType() {
     	if(request!=null)
        return request.getContentType();
     	else 
    		return request2.getContentType();
    }

    @Override
    public int getContentLength() {
     	if(request!=null)
        return request.getContentLength();
     	else 
    		return request2.getContentLength();
    }

    @Override
    public InputStream getInputStream() throws IOException {
     	if(request!=null)
        return request.getInputStream();
     	else 
    		return request2.getInputStream();
    }
}