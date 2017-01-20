<%@ page language="java" pageEncoding="UTF-8"%>
<%@ page import="bo.*"%>
<%@ page import="entity.*"%>
<%@ page import="java.util.*"%>
<%@ page import="util.*"%>
<%@ page import="esunbank.esunutil.*"%>		

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<link rel="stylesheet" type="text/css" href="../css/style.css">

<style type="text/css">
	table.sample {
		border-width: 0px;
		border-spacing: 0px;
		border-style: solid;
		border-color: #00755e;
		border-collapse: collapse;
		background-color: white;
	}

	table.sample th {
		border-width: 1px;
		padding: 3px;
		border-style: solid;
		border-color: #000000;
		font-size: 13px;
		color: #ffffff;
		background-color: #00755e;
		-moz-border-radius:;
	}

	table.sample td {
		border-width: 1px;
		padding: 3px;
		border-style: solid;
		border-color: #00755e;
		font-size: 13px;
		color: #006699;
		text-align: center;
		background-color: white;
		-moz-border-radius:;
	}
</style>


<meta http-equiv="Content-Type" content="text/html; charset=BIG5">
<title><%=Const.title%></title>
</head>

	<%	
		if(session.getAttribute("user_Auth")==null){
			response.sendRedirect(Const.SSO);
			return;
		}	
	%>

<body>	
		<%	
			String docID = request.getParameter("docID") != null ? request.getParameter("docID").trim() : "";
					
			if (docID.equals("") || (String)session.getAttribute("user_Auth") == null  || (String)session.getAttribute("user_No") == null ) {
				out.println( "<div id='close'></div>" ); 	
			}
			else {		
				ArrayList <String> modufyName = null;
				ArrayList <UserAuth> userAuths = null;
				ArrayList<SysUser> sysUsers = null;
				DocBo bo = null;
				try {

					bo = new DocBo();
					userAuths = new ArrayList<UserAuth>();
					userAuths = bo.getUserAuth(docID);
					sysUsers = new ArrayList<SysUser>();
					sysUsers = bo.getSysUser_JustUser();
				%>					
					<script>
						var size = "<%=sysUsers.size()%>";
					</script>
				<%	
				}catch (Exception e){
					e.printStackTrace();	
				}finally {
			         try {
			             bo.disconnect();
			         } catch (Exception e) {
			             e.printStackTrace();
			         }
			     }			
		%>		
		<form action="UpdUserForReadDocServlet"  method = post  id =updUserForReadDoc>
				<br><br>
				<table class="sample" width=85% align="center" >									
					<tr>
						<th><b>使用帳號</b></th>
						<th><b>使用人員</b></th>
						<th><b>可觀看此文件</b></th>
						<th><b>使用者是否可管理</b></th>
					</tr>
				
					<input type = "hidden"   name = "totalUserNum" value ="<%=sysUsers.size() %>" >
		<%						
					for (int i = 0 ; i < sysUsers.size() ; i ++){					
						boolean  find_flag  = false ;
		%>			
					<tr>
						<td ><%=sysUsers.get(i).getUid()%></td>
						<td><%=new esunbank.esunutil.CommonUtil().getCommonUser(sysUsers.get(i).getUid()).getEMCNM()%></td>						
													
						<%																				
							for(int k = 0 ; k < userAuths.size() ; k ++){								    
								if (userAuths.get(k).getUid().equals(sysUsers.get(i).getUid())){  //代表有找到 
									find_flag = true ; 
						%>	
									<td>
										<input type = "checkbox" id = "<%=sysUsers.get(i).getUid()%>"  name = "modifyList" value = "<%=sysUsers.get(i).getUid()%>" checked  onclick="setVisibility('hide<%=i%>',this,'<%=sysUsers.get(i).getUid()%>')">是
									</td>	
									<td id = "hide<%=i%>">		
										<%
											if (userAuths.get(k).getDocAuth().equals(Const.Manager)){
										%>	
												<input type = "radio" name = "auth<%=i%>" value = "<%=Const.Manager %>" checked >管理
												<input type = "radio" name = "auth<%=i%>"   id = "<%=sysUsers.get(i).getUid()+"radio"%>"  value = "<%=Const.User %>"  >閱讀
										<%		
											}else {
										%>	
												<input type = "radio" name = "auth<%=i%>" value = "<%=Const.Manager %>" >管理
												<input type = "radio" name = "auth<%=i%>" value = "<%=Const.User %>" checked>閱讀																				
										<%
											}																					
										%>				
									</td>
						<%		
								}
							}							
							if ( !find_flag )	{
						%>
								<td>										
									<input type = "checkbox"  name = "modifyList" value = "<%=sysUsers.get(i).getUid()%>" onclick="setVisibility('hide<%=i%>',this)">是	
								</td>
								<td id = "hide<%=i%>">	
								</td>
								<!--勾下去才彈出來-->
						<%		
							}						
						%>	
					</tr>						
		<%		
					}
					
			}//end else condition
		%>
		
				</table>
		<p  align="center">
			
			<input type = "hidden"   name = "docID" value ="<%=docID %>" >  <!--沒錯要把文件ID往後丟-->
			<input type = "button"  value="送出"  class="button" onclick ="updUserForReadDoc()">
			
		</p>
	</form>	
	
	<%
	    if (request.getParameter("mes") == null) {
		}
		else if (request.getParameter("mes").equals("success")) {
	%>
			<div id='success'></div>
	<%
		} else if (request.getParameter("mes").equals("fail") || request.getParameter("mes").equals("seriousFail") ) {
	%>
		<div id='fail'></div>
	<%	
		}			
	%>
</body>

	<script type="text/javascript">		
	
	var temp_rul = window.opener.location.href;	

	if (temp_rul.search("\\?") != -1){  //代表不是第一次來				
			temp_rul = temp_rul.substring(0, temp_rul.search("\\?"));		
	}	
	
	if ( document.getElementById("success") ){			
		window.opener.location.href = temp_rul +"?mes=success";
        window.close();
    }
	else if ( document.getElementById("fail") || document.getElementById("close")){
		window.opener.location.href = temp_rul +"?mes=fail";		
		window.close();
	}
	
	function updUserForReadDoc(){	
		if (confirm("確認無誤")) {
			document.getElementById("updUserForReadDoc").submit();			
		} else {
			return;
		}			
	} 	
 
	var  protect_double_click = new Array(size);
	for (var  i  = 0  ; i < protect_double_click.length ; i++ ){
		protect_double_click[i] = false ; 
	}
	
	var uid = "<%=(String)session.getAttribute("user_No")%>";	
	
	if (document.getElementById(uid+"radio")){
		document.getElementById(uid+"radio").disabled='disabled';
	}
	
	function setVisibility( id , checkbox , idNum){
		
		if (idNum == uid ){  
			var label = document.getElementById(idNum);
			label.checked = true; 
			return;
		}	
		
		if ( protect_double_click[id.replace("hide","")]){//protect double click bug
			del(id , checkbox );
			if (checkbox.checked ){							
				creat( id , checkbox );
			}
			protect_double_click[id.replace("hide","")] = false ;
			return;
		}
		
		if (checkbox.checked ){			
			creat( id , checkbox );
		}		 
		else {		
			del(id , checkbox );
		}		
	} 
			
	function creat( id , checkbox ){
	
		temp_id = id.replace("hide","auth"); 
		protect_double_click[id.replace("hide","")] = true;
		var textField = document.createElement("input");						
			
		textField.setAttribute("type", "radio");
		textField.setAttribute("name", temp_id);
		textField.setAttribute("value", "M" );
		var textField1 = document.createElement("input");
		textField1.setAttribute("type", "radio");
		textField1.setAttribute("name", temp_id);
		textField1.setAttribute("value", "U" );
		textField1.setAttribute("check", "checked" );
		textField1.defaultChecked = true;    
		textField1.checked = true;   

		var label = document.getElementById(id);

		var textnode=document.createTextNode("管理");
		var textnode1=document.createTextNode("閱讀");
			  
		label.appendChild(textField);
		label.appendChild(textnode);
		label.appendChild(textField1);
		label.appendChild(textnode1);			
	}		
	
	function del(id , checkbox ){
		var elem = document.getElementById(id);							
		while (elem.childNodes.length > 1) {
			elem.removeChild(elem.lastChild);
		}					
		if (elem.children.length != 0){
			elem.removeChild(elem.lastChild);
		}	
	}
	
	</script>
</html>