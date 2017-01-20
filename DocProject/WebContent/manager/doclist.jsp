<%@ page language="java" pageEncoding="UTF-8"%>
<%@ page import="bo.*"%>
<%@ page import="entity.*"%>
<%@ page import="java.util.*"%>

<!-- 管理者 文章與權限  介面-->
<html>
<head>

<link type="text/css" rel="stylesheet" href="../css/jquery.checktree.css" />
<script type="text/javascript" src="../js/jquery-1.3.2.min.js"></script>
<script type="text/javascript" src="../js/jquery.checktree_yctin.min.js"></script>

<script language="javascript">


function addNewGroup(uid , docID){   //要有name and DocID
							
	//放在哪邊

	var left = (screen.width/3.5);
	var top = (screen.height/3.5);
					
	//大小是多少
	var width = (screen.width/2);
	var height = (screen.height/2.5);
	
	map = window.open("../query/add_user_for_doc.jsp?uid="+uid+"&docID="+docID, "", 'toolbar=no, location=no, directories=no, status=no, menubar=no, scrollbars=yes, resizable=no, copyhistory=no,  height='+height+',  width='+width+',  top='+top+', left='+left);								
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

ul.tree li .checkbox { 

    width: 0px !important;
    height: 0px !important;
    
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
		    DocBo bo = null;
			//這邊要撈資料庫
			ArrayList <DocCtl> docCtl =new ArrayList <DocCtl>();
			ArrayList <SysUser> sysUser = new ArrayList <SysUser>();	
			ArrayList <UserAuth> userAuth = new ArrayList <UserAuth>();
			
			try{
				bo = new DocBo();
			    docCtl = bo.getAllDocCtl();			    
				sysUser = bo.getSysUser();				
				userAuth = bo.getUserAuth();
								
			}			
			catch (Exception e){
				e.printStackTrace();	
			}finally {
		         try {
		             bo.disconnect();
		         } catch (Exception e) {
		             e.printStackTrace();
		         }
		     }
			

			Set<Integer> set_DocCtl = new HashSet<Integer>();
		    Iterator<Integer> it = null;
			for (int i = 0; i < docCtl.size(); i++) {
				set_DocCtl.add(docCtl.get(i).getDocType());
			}
			it = set_DocCtl.iterator();
			int parent = 0;									
			
		%>

		<tr>
			<td colspan="3" class="default-td" align="center" width="100%">
				<table width="90%">				
					<tr>
						<td align="left" width="50%"><font
							style="FONT-SIZE: 13px; LETTER-SPACING: 2px; TEXT-ALIGN: left; color: #006699;">操作人員
								: <%=session.getAttribute("user_Name")%></font> 
						</td>
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
				<form action="" id=form method=post onsubmit="submitForms('DocID')">
					<p>
						<table class="sample" width=90%>
							<tr>
								<th align="left"> &nbsp;&nbsp;&nbsp; 修改使用者可觀看文件</th>
							</tr>
							<ul class="tree">
								<!--迴圈寫在這邊-->
								<%
									it = set_DocCtl.iterator();
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
											<b><font color="#000000" size=3><%=DocType_Name%> <!-- DocType Name --> </font></b>
												
											<ul>
												<%											    
													int child = 0;	
													int userid = 0 ;
													for (int i = 0; i < docCtl.size(); i++) {   //全部的文章
														if (docCtl.get(i).getDocType() == docType_Num ){
														%>
															<li>																
																<div id = submit style="cursor: pointer"  onclick="addNewGroup('<%=session.getAttribute("user_No")%>','<%=docCtl.get(i).getDocID()%>')">											
																	<%=docCtl.get(i).getDocName()%>
																</div>
															</li>	
																	
																<%
																	for(int j = 0 ; j <sysUser.size(); j ++){
																%>																				
																		<%	
																			if(sysUser.get(j).getAUTH().equals(Const.User)){   //使用者  
																				
																		%> 																					
																				<input type='checkbox' name='ids[]' id='p_<%=parent%><%=child%><%=userid%>'	value='<%=sysUser.get(j).getUid()%>'>																				   
																		<%
																			}
																		%>																																									
																<%
																	}//end for loop
																%>																																								
															
														<%	
														}
													}	
												%>																																						
											</ul>	
										</li>
									</td>
								</tr>	
								<%

									//	out.println(""); //一次while 收一次
									}//end while loop
								%>
							
							<br style="clear: both" />
						</table>										
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

