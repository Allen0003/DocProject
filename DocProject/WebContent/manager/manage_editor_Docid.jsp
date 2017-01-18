<%@ page language="java" pageEncoding="UTF-8"%>
<%@ page import="esunbank.esundoc.bo.*"%>
<%@ page import="esunbank.esundoc.entity.*"%>
<%@ page import="java.util.*"%>

<!-- 管理者 文章與權限  介面-->
<html>
<head>
<link type="text/css" rel="stylesheet"
	href="../css/jquery.checktree.css" />
<script type="text/javascript" src="../js/jquery-1.3.2.min.js"></script>
<script type="text/javascript" src="../js/jquery.checktree_yctin.min.js"></script>

<script language="javascript">
	var auth = "<%=request.getParameter("auth")%>" ;
	function submitForms() {	 
			var check = document.getElementsByName("ids[]");
			for (var k = 0 ; k < check.length ; k++){
				if (check[k].checked){  //至少選一個則送出
					document.getElementById("form").submit();
					return ;
				} 						 
			}
		if (auth == "<%=Const.Manager%>") {
			document.getElementById("form").submit();
			return;
		}
		alert("請選擇文件");
		return; 
	}
	
	function change_state_disable(){ 
		auth = "<%=Const.Manager%>" ;			
	}
	
	function change_state_enable(){
		auth = "<%=	Const.User%>" ;	
	}
	
	var $checktree;
	$(function() {
		$checktree = $("ul.tree").checkTree(); //每一個ul label 裡面class 叫tree 的抓出來
		
	});
	function clearAll() {
		$checktree.clear();
		$checktree.update();
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
	padding: 0;
	border-style: solid;
	border-color: #00755e;
	font-size: 13px;
	color: #006699;
	text-align: left;
	vertical-align: left;
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
		<%
		    EsunDocBo bo = null;
		    String[] userDocId = null;
		    Set<Integer> set_DocCtl = null;
		    Iterator<Integer> it = null;
		    int error_flag = 0 ;
		    userDocId = request.getParameter("key").split("\\*");
		    String key = userDocId[0];
		    String auth = request.getParameter("auth");
		    //接受權限
		    ArrayList<DocCtl> user_array_all_file = null ; 
		    //全部的文章數
		    ArrayList<String> user_array_for_user = null; 		    
		    //使用者所可以看到的文章數
		    ArrayList<String> user_array_for_user_auth = null; 		  
			//文章對使用者的權限
			
		    try {
		        bo = new EsunDocBo();
		        //key  是員工編號            
		        user_array_all_file = bo.getDocCtlandDocDtl(key); 
		        //全部的文章
		        //裡面有DocType                             
		        user_array_for_user = user_array_all_file.get(0).getDocIDsAndAuth().getDocIDs(); 
		        //可以看得的文章
				user_array_for_user_auth =  user_array_all_file.get(0).getDocIDsAndAuth().getDocAuth();
		        
				//文章對使用者的權限
		    } catch (Exception e) {
			    e.printStackTrace();
			    error_flag++ ;			
		    } finally {
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
				}
%>

		<tr>
			<td colspan="3" class="default-td" align="center" width="100%">
				<table width="90%">
					<tr>
						<td align="left" width="50%"><font
							style="FONT-SIZE: 13px; LETTER-SPACING: 2px; TEXT-ALIGN: left; color: #006699;">操作人員
								: <%=session.getAttribute("user_Name")%></font> <br> <font
							style="FONT-SIZE: 13px; LETTER-SPACING: 2px; TEXT-ALIGN: left; color: #006699;">變更使用者名
								: <%=new esunbank.esunutil.CommonUtil().getCommonUser(userDocId[0]).getEMCNM()%></font>
						</td>
					</tr>
				</table>
			</td>
		</tr>
		<tr>
			<td colspan="3" class=default-td align="center">
				<form action=UpdUserAuthServlet id=form method=post
					onsubmit="submitForms('DocID')">
					<%
						String managerCheck ; 
						String userCheck ; 
					    int selected[];
					    //先找看看裡面有哪些文件是使用者有的
						int docAuth [];
					    selected = new int[user_array_all_file.size()];
					    docAuth = new int[user_array_all_file.size()];
					    if (user_array_for_user.size() != 0) {
					        for (int i = 0; i < user_array_all_file.size(); i++) {
					            selected[i] = 0;
					        }
					        for (int i = 0; i < user_array_all_file.size(); i++) {
					            for (int k = 0; k < user_array_for_user.size(); k++) {
					                if (user_array_all_file.get(i).getDocID().equals( user_array_for_user.get(k))) {
					                    selected[i] = 1;
					                    if (user_array_for_user_auth.get(k).equals( Const.Manager)){  // 找到一樣的 在找一下權限
					                        docAuth[i]  = 1;   //代表有管理權限
					                    }
					                }
					            }
					        }
					    }
					    set_DocCtl = new HashSet<Integer>();
					    for (int i = 0; i < user_array_all_file.size(); i++) {
					        set_DocCtl.add(user_array_all_file.get(i).getDocType());
					    }
					    it = set_DocCtl.iterator();
					    int parent = 0;
					%>
					<%
					    if (auth.equals(Const.Manager)) { //進來情形是管理者的話
					%>
					<input type="radio" name="AUTH" value=<%=Const.User%> onclick = "change_state_enable()" > 使用者
					&nbsp; <input type="radio" name="AUTH" value=<%=Const.Manager%>
						checked   onclick = "change_state_disable()"> 管理者
					<%
  					   } else { //使用者的情形
 					%>
					<input type="radio" name="AUTH" value=<%=Const.User%> checked   onclick = "change_state_enable()">
					使用者 &nbsp;
					<input type="radio" name="AUTH" value=<%=Const.Manager%>   onclick = "change_state_disable()"> 
					管理者
					<%
					     }
					 %>
					<table class="sample" width=90%>
						<tr>
							<th align="left"> &nbsp;&nbsp;&nbsp; 修改可看文件</th>
						</tr>
						<ul class="tree">
							<%
							    it = set_DocCtl.iterator();
								//取出不重複的數字 
							    while (it.hasNext()) {
							        parent++; //十位數														
							        int docType_Num = it.next();   //抓出數字									
									String  DocType_Name = "";
									for (int i = 0 ; i < Const.DocType.size() ; i ++){
										if (Const.DocType.get(i).getTypeID() == docType_Num){
											DocType_Name = Const.DocType.get(i).getTypeName();
										}
									}
							%>
							<tr>
								<td align="left">
									<li>
										<%
										    if (auth.equals(Const.Manager)) {
										%> <input type=checkbox name='ids[]' id='p_<%=parent%>' disabled="disabled" /> <%
  										   } else { //如果權限不是管理者的話
 										%> <input type=checkbox name='ids[]' 	id='p_<%=parent%>' /> <%
     										}
 											%> <b><font color="#000000"	size=3><%=DocType_Name%> <!-- DocType Name --> </font></b>
										<ul>
											<%											    
											        int child = 0;													
											        for (int i = 0; i < user_array_all_file.size(); i++) { //總共有幾筆文件
															if (user_array_all_file.get(i).getDocType() == docType_Num ){
															//找出批號相同  的相同類別
																if (user_array_for_user.size() == 0  ) { //沒有文件可以看 的情形
																%>
																<li><input type='checkbox' name='ids[]' id='p_<%=parent%><%=child%>'	value='<%=user_array_all_file.get(i).getDocID()%>'>
																	<%=user_array_all_file.get(i).getDocName()%>
																	&nbsp;&nbsp;&nbsp;控管人員
																	<input type = "radio"   name="DocAuth<%=user_array_all_file.get(i).getDocID() %>"   value="M"   >是
																	<input type = "radio"   name="DocAuth<%=user_array_all_file.get(i).getDocID() %>"   value="R"  checked>否
																</li>
																<%			
																	} 																								
																	else if (selected[i] == 1) {
																					//找到有權限觀看的文章 default 打勾
																%>
																<li><input type='checkbox' name='ids[]'
																	id='p_<%=parent%><%=child%>'	value='<%=user_array_all_file.get(i).getDocID()%>' checked> 
																	<%=user_array_all_file.get(i).getDocName()%>
																	&nbsp;&nbsp;&nbsp;控管人員																	
																	<%
																																				
																		if (docAuth[i] ==1){  //代表可以修改
																			managerCheck = "checked";
																			userCheck = "";
																		}
																		else{
																			managerCheck = "";
																			userCheck = "checked";
																		}
																	%>																			    
																	    <input type = "radio"  name="DocAuth<%=user_array_all_file.get(i).getDocID() %>"  value="M"  <%=managerCheck%>  >是
																		<input type = "radio"  name="DocAuth<%=user_array_all_file.get(i).getDocID() %>"  value="R"   <%=userCheck%>>否
																</li>
																<%
																	} 
																	else { //沒找到 default 空白
																%>
																<li>
																	<input type='checkbox' name='ids[]' 	id='p_<%=parent%><%=child%>' value='<%=user_array_all_file.get(i).getDocID()%>'>
																	<%=user_array_all_file.get(i).getDocName()%> 
																	&nbsp;&nbsp;&nbsp;控管人員
																	<input type = "radio"   name="DocAuth<%=user_array_all_file.get(i).getDocID() %>"   value="M"   >是
																	<input type = "radio"   name="DocAuth<%=user_array_all_file.get(i).getDocID() %>"   value="R"  checked>否
																</li>
																<%
																	}															
															}
											        }
											        out.println("</ul>  </li></tr></td>"); //一次while 收一次
											    }
											    out.println("<input type=hidden name=Uid" + "  value ="
											            + userDocId[0] + ">");
											%>
											<br style="clear: both" />
					</table>
					<p>
						<input type=hidden name="userName"
							value=<%=session.getAttribute("user_Name")%>> <input
							type=button class="button" value=修改 onclick="submitForms()">
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

