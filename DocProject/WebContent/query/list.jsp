<%@ page language="java" pageEncoding="UTF-8"%>
<%@ page import="esunbank.esundoc.bo.*"%>
<%@ page import="esunbank.esundoc.entity.*"%>
<%@ page import="java.util.*"%>
<!-- 查詢 文件 DocCtl 介面-->
<html>
<head>
<title><%=Const.title%></title>
<link rel="stylesheet" type="text/css" href="../css/style.css">
<script type="text/javascript" src="../js/normal.js"> </script>
<script type="text/javascript" src="../js/tablesort.js"> </script>
<script language="JavaScript" src="../js/cal.js"></script>
<script language="javascript">
	function select_form() {
		document.getElementById("I_am_Select").submit();		
	}

	function confirmDelete(key) {
		if (confirm("是否刪除")) {
			document.location.href = "DeleteDocCtlServlet?key=" + key;
		} else {
			return;
		}
	}
	
	function confirmSendMail(docID ){
		
		if (confirm("確認寄信")) {
			document.location.href = "SendMailServlet?docID=" + docID;
		} else {
			return;
		}
	}
	
	function addNewGroup( docID){   //要有name and DocID
							
		//放在哪邊

		var left = (screen.width/3.5);
		var top = (screen.height/3.5);
						
		//大小是多少
		var width = (screen.width/2);
		var height = (screen.height/2.5);
		map = window.open("add_user_for_doc.jsp?docID="+docID, "", 'toolbar=no, location=no, directories=no, status=no, menubar=no, scrollbars=yes, resizable=no, copyhistory=no,  height='+height+',  width='+width+',  top='+top+', left='+left);								
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
	-moz-border-radius:;
}
</style>
</head>
<body background="../image/bg1.gif" topMargin="0">
	<%@include file="../common/top.jsp"%>

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
				    EsunDocBo bo = null;
				    ArrayList<DocCtl> docCtl = null;
				    Set<Integer> set_DocCtl = null;
				    Iterator<Integer> it = null;
				    int error_flag = 0 ;
					ArrayList<Integer> docTypeName = null;
				    try {
				        
				        bo = new EsunDocBo();
				        String Auth = (String) session.getAttribute("user_Auth");
				        docTypeName =  new ArrayList<Integer> ();
				        
				        if (Auth.equals(Const.Manager)) {//管理者可以看到全部的文章
				            docCtl = bo.getAllDocCtl();							
				        	for (int i = 0 ; i < docCtl.size() ; i ++){				        	    
				        	    docTypeName.add(docCtl.get(i).getDocType());
				        	}				
				        } 
						else { //使用者則收session 裡面的值						    
				            docCtl = bo.getDocCtlByID(  ((String) session.getAttribute("DocID")).split("\\+"));	 
				            docCtl.get(0).setDocIDsAndAuth( bo.getDocIDsAndAuth(  (String) session.getAttribute("user_No") )) ;				                  
							for (int i = 0 ; i < docCtl.size() ; i ++){				        	    
				        	    docTypeName.add(docCtl.get(i).getDocType());
				        	}							
				        } 				        				        
				         
				        if ( ((String)session.getAttribute("user_Auth")).equals(Const.Manager)  && request.getParameter("docType_select") != null  && request.getParameter("docType_select").equals("*")){
							docCtl = null;
				            docCtl = bo.getAllDocCtl();
				        }
				        //管理者選單個
				        else if( ((String)session.getAttribute("user_Auth")).equals(Const.Manager)  && request.getParameter("docType_select") != null  ){
							docCtl = null;
				            docCtl = bo.getDocType(request.getParameter("docType_select"));				            
				        } 
				        //使用者選全部
				        else if (session.getAttribute("user_Auth").equals(Const.User)  &&   request.getParameter("docType_select") != null   &&  request.getParameter("docType_select").equals("*")) { //user 選全部的狀態				                    				             
							docCtl = null;
				            docCtl = bo.getDocCtlByID( ((String) session.getAttribute("DocID")).split("\\+"));
				            docCtl.get(0).setDocIDsAndAuth( bo.getDocIDsAndAuth(  (String) session.getAttribute("user_No") )) ;
				        }
				        else{   //使用者選不是全部
					        for (int i = 0 ; i < Const.DocType.size() ; i ++){
					            if (Integer.toString(Const.DocType.get(i).getTypeID()).equals(request.getParameter("docType_select"))){
									//找到使用者從畫面輸入的那一筆
									if( ((String)session.getAttribute("DocType")).contains(Const.DocType.get(i).getTypeName())){
										docCtl = null ;
									    docCtl = bo.getDocType(request.getParameter("docType_select") );
									    docCtl.get(0).setDocIDsAndAuth( bo.getDocIDsAndAuth(  (String) session.getAttribute("user_No") )) ;
									}
					            }				            
					        }				            
				        }		
						
				    } 
					catch (Exception e) {
					    e.printStackTrace();
					    error_flag ++ ; 
						
				    } finally {
				        try {
				            bo.disconnect();
				        } catch (Exception e) {
				            e.printStackTrace();
				        }
				    }
				%> <%
				if (error_flag != 0){
				    %>
				<p>
					<font color='red'><b><%="系統異常 "%></b></font>
				</p>    
				    <%
				}
				if (request.getParameter("mes") == null) {

				} else if (request.getParameter("mes").equals("success")) {
				%>
						<p>
							<font color='red'><b><%="更新成功"%></b></font>
						</p> <%
				} else if (request.getParameter("mes").equals("fail")) {
				%>
						<p>
							<font color='red'><b><%="更新失敗"%></b></font>
						</p> <%
				}else if (request.getParameter("mes").equals("nodata")) {
				%>	 
						<p>
							<font color='red'><b><%="規格裡面沒有資料"%></b></font>
						</p> 
				<%
				}
	 
	 
				%> <!--  下拉式選單介面 -->
				<form id=I_am_Select method="Post" action=" #">
					規格類別: <select name="docType_select" onChange="select_form()">
						<option value="" disabled selected style="display: none;">請選擇</option>
						<option value=*>全部</option>
						<%
						
						    set_DocCtl = new HashSet<Integer>();
							if ( docTypeName != null){   
							    for (int i = 0; i < docTypeName.size(); i++) {
							        set_DocCtl.add(docTypeName.get(i));
							    }
						    
							    it = set_DocCtl.iterator();
							    String docType = "";
							   
							    while (it.hasNext()) {
							        int docType_Num = it.next();
							        String docType_Name = "";
							        for (int i = 0 ;  i < Const.DocType.size() ; i++){
							            if (Const.DocType.get(i).getTypeID()  == docType_Num){
							                docType_Name = Const.DocType.get(i).getTypeName();
							            }
							        }
							        out.println("<option value=\"" + docType_Num + "\" >"+  docType_Name + "</option>");
							        docType += docType_Name + "+";
							    }						    
							    session.setAttribute("DocType", docType);
							    //可以觀看文章數的 DocType num
							}
						%>
					</select>
				</form> <%
					if (session.getAttribute("user_Auth").equals(Const.Manager)) {
				%> <!-- 管理者才有的建立 -->
						<form action="../createDocCtl/createDocCtl.jsp">
							<input type="submit" value="建立" class="button">
						</form>
				<%
					}
				%>
				<table class="sample" width=85%>
					<tr>
						<th><b>規格名稱</b></th>
						<th><b>規格類別</b></th>
						<th><b>異動人員</b></th>
						<th><b>人員編號</b></th>
						<th onclick="sortColumn(event)" ><b>異動時間</b></th>
					<%
					    if (session.getAttribute("user_Auth").equals(Const.Manager)) {
					%>
							<th><b>刪除</b> <%
						}//end if
					%>
						<th><b>通知</b> 								
						<th><b>增加人員</b>						
					</tr>
					
					<%
						String bgcolor = "";
						
						String session_DocID = (String) session.getAttribute("DocID");
							
						String[] compare_href_session = session_DocID.split("\\+");
						int color_flag =0 ; 
						int current_color_num  = 0 ;	
					
						for (int i = 0; i < docCtl.size(); i++) {
							for (int j = 0; j < compare_href_session.length; j++) { //要知道使用者可以看哪些文章
								//因為會多選
								if (docCtl.get(i).getDocID().equals(compare_href_session[j]) || ((String)session.getAttribute("user_Auth")).trim().equals(Const.Manager)) {		//當找到session 裡面有的時候								
									if (current_color_num != docCtl.get(i).getDocType()){
										color_flag ++ ;
										current_color_num = docCtl.get(i).getDocType()	;
									}
					%>
								<!-- 使用者  -->
									
									<%
										if(color_flag % 2 == 0 ){
											bgcolor="#FFFFFF";
										}
										else if (color_flag % 2 == 1 ){
											bgcolor="#E7CDFF";
										}
									%> 				
									<tr>										
										<td  align="left" bgcolor = "<%=bgcolor%>" ><a  href=dtl.jsp?DocID=<%=docCtl.get(i).getDocID()%>
											target=_self><%=docCtl.get(i).getDocName()%></a></td>
										<td align="left" bgcolor = "<%=bgcolor%>"  >
									<%							
										for (int k = 0 ; k < Const.DocType.size() ; k ++){
											if (Const.DocType.get(k).getTypeID() == docCtl.get(i).getDocType() ){
												out.println(Const.DocType.get(k).getTypeName());
											}	    
										}														
									%>                                                                                                                       
										</td>							
										<td align="center" bgcolor = "<%=bgcolor%>" ><%=new esunbank.esunutil.CommonUtil().getCommonUser(docCtl.get(i).getSysid()).getEMCNM()%></td>
										<td align="center" bgcolor = "<%=bgcolor%>" ><%=docCtl.get(i).getSysid()%></td>																
										<td align="center" bgcolor = "<%=bgcolor%>" >
											<%=docCtl.get(i).getSysdt().substring(0,10)%>						
											<br><%=docCtl.get(i).getSysdt().substring(10,docCtl.get(i).getSysdt().length())%>
										</td>  <!--這邊在去判斷權限-->
									<% 
										if ( ((String)session.getAttribute("user_Auth")).trim().equals(Const.Manager) ) {
									%>	
											<td align="center" bgcolor = "<%=bgcolor%>" ><img style="cursor: pointer"
												src="../image/cross.png" width="12" height="12" align="middle"
												onclick="confirmDelete('<%=docCtl.get(i).getDocID()%>')" />
											</td>										
											<td align="center" bgcolor = "<%=bgcolor%>" >
												<img style="cursor: pointer" src="../image/mail.jpg" width="16" height="14" align="middle" onclick="confirmSendMail('<%=docCtl.get(i).getDocID()%>')" />
											</td>
											<td align="center" bgcolor = "<%=bgcolor%>" >
												<img style="cursor: pointer" src="../image/group.jpg" width="24" height="18" align="middle" onclick="addNewGroup('<%=docCtl.get(i).getDocID()%>')" />
											</td>										
									<%		
										}
										else {																						
											boolean print = false; 
											for(int k = 0 ;  k < docCtl.get(0).getDocIDsAndAuth().getDocAuth().size() ; k++){
												if (docCtl.get(i).getDocID().equals(docCtl.get(0).getDocIDsAndAuth().getDocIDs().get(k))){
													if (docCtl.get(0).getDocIDsAndAuth().getDocAuth().get(k).equals(Const.Manager)){
									%>
														<td align="center" bgcolor = "<%=bgcolor%>" >
															<img style="cursor: pointer" src="../image/mail.jpg" width="16" height="14" align="middle" onclick="confirmSendMail('<%=docCtl.get(i).getDocID()%>')" />
														</td>
														<td align="center" bgcolor = "<%=bgcolor%>" >
															<img style="cursor: pointer" src="../image/group.jpg" width="24" height="18" align="middle" onclick="addNewGroup('<%=docCtl.get(i).getDocID()%>')" />
														</td>
														<%  print = true ; %>
									<%
													}
												}									        
											}
											if (!print) { //有沒有權限寄信增加人
									%>
												<td align="center" bgcolor = "<%=bgcolor%>" ></td>
												<td align="center" bgcolor = "<%=bgcolor%>" ></td>
									<%
											}
										}
									%>																				
									</tr>
					<%
								}
							}			
						}
					%>					
				</table> <!-- end -->
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
