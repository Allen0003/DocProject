<%@ page language="java" contentType="text/html; charset=UTF-8" import="esunbank.esundoc.util.*, java.util.* " pageEncoding="UTF-8"%>
<%

	Enumeration e = session.getAttributeNames();
	while(e.hasMoreElements()){ 
		session.removeAttribute((String)e.nextElement());		
	}
	response.sendRedirect(Const.SSO);	
	return; 
%>