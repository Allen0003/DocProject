<%@ page language="java" contentType="text/html; charset=UTF-8" 
import="util.*" %>	
	<%
	
		if(session.getAttribute("user_Auth")==null){
			response.sendRedirect(Const.SSO);
			return;
		}
	
	%>
<style type="text/css">

body {
	margin-top: 0px;
} 

</style>
<table align="center" width="750" border="0" cellpadding="0" cellspacing="0" class="default-tableborder">
	<tr><td bgcolor="#ffffff">
		<table cellspacing="0" cellpadding="0" width="100%" border="0">
			<tr>
			 	<td colspan="2"><img alt="E.SUN BANK" src="../image/banner.jpg" border="0"></td> 
			</tr>
			<tr>
				<td colspan="2" bgcolor="#18a36c" height="0"></td>
			</tr>
			<tr>
				<td valign="center" background="../image/banner_bg.jpg" ><img src="../image/title.jpg"></td>
				<td valign="center" align="right" background="../image/banner_bg.jpg">
					<table cellspacing="0" cellpadding="0">
						<tr>
							<td><img id="loc1" src="../image/top_line.gif"></td>
							<!-- 權限管理 -->
							
													
							<% 
									session.getAttribute("user_Auth");	 //管理者才看得到																						
							
									if (session.getAttribute("user_Auth").equals("M")){    %>
									<!--<td style="cursor:pointer" onclick="document.location='../manager/doclist.jsp'"><img src="../image/b5.jpg"></td>	
									<td><img id="loc1" src="../image/top_line.gif"></td>		
									-->
									 <td style="cursor:pointer" onclick="document.location='../manager/list.jsp'"><img src="../image/b1.jpg"></td>									
									<td><img id="loc1" src="../image/top_line.gif"></td>		<%}  //  end if condition 	%>			    							

							<!-- 文件查詢 -->
							<td style="cursor:pointer" onclick="document.location='../query/list.jsp'"><img src="../image/b2.jpg"></td>
							<td><img src="../image/top_line.gif"></td>
							<td style="cursor:pointer" onclick="document.location='../common/logout.jsp'"><img src="../image/b4.jpg"></td>
						</tr>
					</table>
				</td> 
			</tr>
			<tr>
				<td colspan="2" bgcolor="#18a36c" height="0"></td>
			</tr>
			<tr><td colspan="2">
				<table align="center" cellpadding="0" cellspacing="0" width="100%">
					<tr><td valign="top" align="center" >
					