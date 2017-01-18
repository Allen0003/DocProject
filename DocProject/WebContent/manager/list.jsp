<%@ page language="java" pageEncoding="UTF-8"%>
<%@ page import="esunbank.esundoc.bo.*"%>
<%@ page import="esunbank.esundoc.entity.*"%>
<%@ page import="java.util.*"%>
<%@ page import="esunbank.esunutil.*"%>		
<!-- 管理者  是否啟用  介面-->
<html>
<head>

<script language="javascript">
	function changeImage(changerID) {
		alert("更改完成");
		var path = document.getElementById(changerID).src;
		var image_name = path.substring(path.lastIndexOf("/") + 1, path.length);

		if (image_name == "active.gif") {
			//原本有啟用 
			document.getElementById(changerID).src = "../image/nactive.gif";
			var elem = document.getElementById("IsAction");
			var uid = document.getElementById("Uid");
			elem.value = "N";
			uid.value = changerID;
			document.getElementById("Manage_form").submit();
		} else {
			document.getElementById(changerID).src = "../image/active.gif";
			var elem1 = document.getElementById("IsAction");
			var uid1 = document.getElementById("Uid");
			uid1.value = changerID;
			elem1.value = "Y";
			document.getElementById("Manage_form").submit();
		}
	}
	
	function addUserCompany() {
		if(document.getElementById("qryUser").value ==""  || 
				document.getElementById("qryUserName").value ==""  ){
			alert("請輸入使用者帳號");			
			return ;
		} 
		document.getElementById("addUser").submit();
	}	
	
</script>

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
				<table width="90%">
					<tr>
						<td align="left" width="50%"><font
							style="FONT-SIZE: 13px; LETTER-SPACING: 2px; TEXT-ALIGN: left; color: #006699;">操作人員
								: <%=session.getAttribute("user_Name")%></font></td>
					</tr>
				</table>
			</td>
		</tr>
		<tr>
			<td colspan="3" class=default-td align="center">
				<%
				    if (request.getParameter("mes") == null) {
				    }
					else if (request.getParameter("mes").equals("success")) {
				%>
				<p>
					<font color='red'><b><%="更新成功"%></b></font>
				</p>
				<%
					} else if (request.getParameter("mes").equals("fail")) {
				%>
				<p>
					<font color='red'><b><%="更新失敗"%></b></font>
				</p>
				<%
					}
				%>
				<%
					EsunDocBo bo = null;
					ArrayList<SysUser> userAttributeArray = null;
					int error_flag = 0 ;
					try {
						bo = new EsunDocBo();
						userAttributeArray = bo.getSysUser();
					} catch (Exception e) {
							e.printStackTrace();
							error_flag ++  ;			         
					} 
					finally {
						try {
							bo.disconnect();
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				%>


<%
				if (error_flag != 0){
				    %>
				<p>
					<font color='red'><b><%="系統異常 "%></b></font>
				</p>    
				    <%
				}%>
			
				<%String  qryUser=""  ,  qryUserName = "";%>
			<form action ="AddSysUserServlet"  method = post  id=addUser  >				
				<input type="text" id="qryUser" name="qryUser" size="18" value="<%=qryUser == null ? "" : qryUser%>">
				<input type="text" id="qryUserName" name="qryUserName" size="18" 	value="<%=qryUserName == null ? "" : qryUserName%>">
				<%= new esunbank.esunutil.ad.ADUtil().getADMenu("qryUser","qryUserName") %>
				&nbsp; &nbsp; 
				<input type = "button"  id  = "addUser"   value="建立"  class="button" onclick ="addUserCompany()">
			</form>
			
				<form action=UpdSysUserServlet method=post Id=Manage_form>
					<table class="sample" width="50%">

						<tr>
							<th><b>使用帳號</b></th>
							<th><b>使用人員</b></th>
							<th><b>是否啟用</b></th>
							<th><b>身分類別</b></th>
							<th><b>權限設定</b></th>
						</tr>
						<%
						    for (int i = 0; i < userAttributeArray.size(); i++) {
						%> 
						<tr>
							<td><%=userAttributeArray.get(i).getUid()%></td>
						
							<td><%=new esunbank.esunutil.CommonUtil().getCommonUser(
                        userAttributeArray.get(i).getUid()).getEMCNM()%></td>
							<%
							    //是否啟用
							        if (userAttributeArray.get(i).getIsAction().equals("Y")) {
							            out.println(" <td ><input type=\"hidden\" name=IsAction"
							                    + " value=\"Y\">");
							            out.println(" <input type=\"hidden\" name=Uid"
							                    + " value=\"\">");
							            out.println("<img style=\"cursor:pointer\" src=\"../image/active.gif\"  id="
							                    + userAttributeArray.get(i).getUid()
							                    + "    \"   width=\"12\" height=\"12\"   align=\"middle\"      onclick=\"changeImage(this.id)\" /></a>");
							        } else {
							            out.println("<td > <img  style=\"cursor:pointer\"  src=\"../image/nactive.gif\"  id="
							                    + userAttributeArray.get(i).getUid()
							                    + "    \"   width=\"12\" height=\"12\"     align=\"middle\"    onclick=\"changeImage(this.id)\" /></a>");
							            out.println(" <input type=\"hidden\" name=Uid"
							                    + " value=\"\">");
							            out.println("<input type=\"hidden\" name=IsAction"
							                    + " value=\"N\"> </td> ");
							        }
							        if (userAttributeArray.get(i).getAUTH().equals(Const.Manager)) {
							            out.println("<td > 管理者</td>");
							        } else if (userAttributeArray.get(i).getAUTH()
							                .equals(Const.User)) {
							            out.println("<td >使用者</td>");
							        }
							        //文件種類
							        if (userAttributeArray.get(i).getAUTH().equals(Const.Manager)) { //管理者
							            out.println("<td ><a href=manage_editor_Docid.jsp?auth=M&key="
							                    + userAttributeArray.get(i).getUid()
							                    + " target=_self  >  變更 </tr>");
							        } else { //如果使用者沒有文件可以看
							                 //使用者
							            out.println("<td ><a href=manage_editor_Docid.jsp?auth=U&key="
							                    + userAttributeArray.get(i).getUid()
							                    + " target=_self  >  變更 </tr>");
							        }
							    }//end for loop
							%>
						
					</table>
					<input type="hidden" name="user_No"
						value="<%=session.getAttribute("user_No")%> "> <br>
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

