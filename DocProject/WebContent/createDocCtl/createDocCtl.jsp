<%@ page language="java" pageEncoding="UTF-8"%>
<%@ page import="entity.*"%>

<%@ page import="java.util.*"%>

<html>
<head>

<script language="javascript">

	function submitForms() {
		
		if (document.getElementById("docName").value.replace(/(^[\s]*)|([\s]*$)/g, "") == "") {
			alert("請輸入規格名稱");
			return;
		}	
		document.getElementById("form1").submit();
	}
	function addDocType() {
		
		if (document.getElementById("DocType").value == "") {
			alert("請輸入創造規格的名稱");
			return;
		}	
		document.getElementById("form2").submit();
	}
	

</script>



<title><%=Const.title%></title>
<link rel="stylesheet" type="text/css" href="../css/style.css">
<script type="text/javascript" src="../js/normal.js"></script>
<script language="JavaScript" src="../js/cal.js"></script>
</head>
<body background="../image/bg1.gif" topMargin="0">
	<%@include file="../common/top.jsp"%>
	<!-- 把Top include 近來  -->

	<table width="98%" border="0" cellpadding="0" cellspacing="0"
		align="center">
		<!-- 標題 -->
		<tr>
			<td colspan="3" height="10"></td>
		</tr>
		<tr>
			<td width="30"><img src="../image/top01.gif" width="30"
				height="25"></td>
			<td width="98%" valign="middle" background="../image/top02.gif"
				class="default-subtitle">文件查詢作業</td>
			<td width="10"><img src="../image/top03.gif" width="10"
				height="25" alt=""></td>
		</tr>
		<!-- start -->
		

		
		
		<tr>			
			<td colspan="3" class="default-td" align="center" width="100%">
			 	<form action="AddDocTypeServlet"  id="form2" method = "Post"> 	
					<table width="90%">
						<tr>
							<td align="left" width="30%"><font
								style="FONT-SIZE: 13px; LETTER-SPACING: 2px; TEXT-ALIGN: left; color: #006699;">操作人員 
									:  <%=session.getAttribute("user_Name") %> </font>
							</td>
									
									<td align="center" width="30%">
										<%
											if (request.getParameter("mes") == null) {
											} else if (request.getParameter("mes").equals("success")) {
										%>
										<p>
											<font color='red' style="FONT-SIZE: 13px"><b><%="更新成功"%></b></font>
										</p>
										<%
											} else if (request.getParameter("mes").equals("fail")) {
										%>
										<p>
											<font color='red' style="FONT-SIZE: 13px"><b><%="更新失敗"%></b></font>
										</p> 
										<%
											}
										%> 																															
									</td>			
									<!-- 						
									<td align="right" width="30%">
									<input type = "text" name = "DocType">
									<input type = "button" value = "類別" class="button" onclick =  "addDocType()">  	
									</td>
									 -->								  
						</tr>
					</table>
				</form>
			</td> 
		</tr>		 				
		<tr>					
			<td colspan="3" class=default-td align="center">									
			
			  	<form action="AddDocCtlServlet"  id="form1" method = "Post"> 							  
					規格名稱 <input type=text  name=docName  >					 
					<br>
					規格類別  <select name="num" style="width: 155px">
						<%						
								for (int i = 0 ; i < Const.DocType.size() ; i++ ){
									out.println("<option value=\"" + Const.DocType.get(i).getTypeID() + "\" >"+ Const.DocType.get(i).getTypeName() + "</option>");
								}						
						%>							
									</select>																		
					<br>
					規格說明 <input type=text  name=Memo  >   <br>
					<p>
						<input type=button value=送出  class="button" onclick =  "submitForms()" >
					</p>
				</form>
			</td>
		</tr>
		<!-- end -->
		<tr>
			<td width="30"><img src="../image/sub_img03.gif" height="5"></td>
			<td width="660" background="../image/sub_img04.gif"></td>
			<td width="10"><img src="../image/sub_img05.gif" height="5"></td>
		</tr>
	</table>
	<br>

	<%@include file="../common/foot.jsp"%>
</body>

</html>

