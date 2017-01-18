<%@ page import="esunbank.esundoc.bo.*"%>
<%@ page import="esunbank.esundoc.entity.*"%>
<%@ page import="java.util.*"%>

<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!-- 新增上傳檔案最主要的地方 -->

<html>
<head>
<script type="text/javascript"
	src="http://code.jquery.com/jquery-latest.js"></script>

<script language="javascript">

	var textNumber = 1;
	var markarray = new Array();
		
		
	function confirmSendMail(docID,docSN){
		if (confirm("確認寄信")) {
			document.location.href = "SendMailServlet?docID=" + docID+"&docSN=" + docSN ;
		} else {
			return;
		}
	}		
		
	function confirmRetore(key,id){
		if (confirm("是否還原")){			
			var temp = key.split("+");
			document.location.href="FileToHistoryZoneServlet?docID="+temp[0]+"&docSN="+temp[1]+"&inHistoryZone=N"; 									
		}
		else { 
			return;			
		}	 	
	}			
	
	function addTextBox(form, afterElement) {
		textNumber++;
		var label = document.createElement("label");
		var textField = document.createElement("input");
		textField.setAttribute("type", "file");
		textField.setAttribute("name", "file" + textNumber);
		textField.setAttribute("id", "file" + textNumber);		
		textField.className  = "button2";

		var textField1 = document.createElement("input");
		textField1.setAttribute("type", "text");
		textField1.setAttribute("name", "memo" + textNumber);
		textField1.setAttribute("size", "51");

		// Add the label's text   
		// Put the textbox inside   

		label.appendChild(textField);
		label.appendChild(document.createTextNode('\u00a0'));
		label.appendChild(document.createTextNode('\u00a0'));
		label.appendChild(document.createTextNode('\u00a0'));
		label.appendChild(textField1);		

		var textField3 = document.createElement("input");   
		textField3.setAttribute("type","button");
		textField3.setAttribute("title","刪除上傳檔案");
		textField3.id = textNumber ;
		//因為IE的原因 所不能直接用onclick 與 style  
		textField3.style.setAttribute("cssText", "height:22px ; width:22px; cursor:pointer;  background-image:url(../image/sub.png); "); 
		textField3.setAttribute("onclick","removeTextBox();");   	
		textField3.onclick = function() {
			removeTextBox(this.id);
		}; 
		label.appendChild(document.createTextNode('\u00a0'));
		label.appendChild(document.createTextNode('\u00a0'));
		label.appendChild(document.createTextNode('\u00a0'));

		label.appendChild(textField3);  
		 
		form.insertBefore(label, afterElement);
		return false;
	}
	function removeTextBox(idNum) {
		if (textNumber > 1) {
			myForm.removeChild(document.getElementById("file"+ idNum).parentNode);
			markarray[idNum] = 1 ;  //是否被刪除的flag
		}
	}
	function submitForms() {
		for (var i = 1; i <= textNumber; i++) {
			if (markarray[i]  !=1 ){
				if (document.getElementById("file" + i).value == "") { 
					alert("檔案不可為空");
					return;
				}	
			}
		}
		if (!confirm("確認檔案是否正確")){
			return ;
		}
		if (document.getElementById("memo1").value == "說明文件") {			 
			document.getElementById("memo1").value = "";
		}
		document.getElementById("disable").disabled=true;   //把button藏起來
		document.getElementById("myForm").submit();
	}
	
	function confirmDelete(key) {		     
		var temp = key.split("+");
		if (confirm("是否刪除")){
			document.location.href="DeleteDocDtlServlet?docID="+temp[0]+"&docSN="+temp[1]  ; 
		}
		else { 
			return;			
		}	 
	}	 
	
	function confirm2HistoryZone(key){
		var temp = key.split("+");
		if (confirm("是否移至備份區")){
			document.location.href="FileToHistoryZoneServlet?docID="+temp[0]+"&docSN="+temp[1]+"&inHistoryZone=Y"; 
		}		
		else { 
			return;			
		}	 	
	}
	
	function toHistoryZone(docID){
		document.location.href="historyzone.jsp?DocID="+docID  ; 	 				
	}
	
	
	$(document).ready(function(){    //Select all anchor tag with rel set to tooltip
		$('a[rel=tooltip]').mouseover(function(e) {
			//Grab the title attribute's value and assign it to a variable
			var tip = $(this).attr('title');   
			//Remove the title attribute's to avoid the native tooltip from the browser
			$(this).attr('title','');
			//Append the tooltip template and its value			
			$(this).append('<div id="tooltip"><div class="tipHeader"></div><div class="tipBody">' + tip + '</div><div class="tipFooter"></div></div>');     			
			//Set the X and Y axis of the tooltip
			$('#tooltip').css('top', e.pageY + 10 );
			$('#tooltip').css('left', e.pageX + 20 );
			//Show the tooltip with faceIn effect
			$('#tooltip').fadeIn('500');
			//$('#tooltip').fadeTo('10',0.8);			
			}).mousemove(function(e) {
			//Keep changing the X and Y axis for the tooltip, thus, the tooltip move along with the mouse			
				$('#tooltip').css('top', e.pageY + 10 );
				$('#tooltip').css('left', e.pageX + 20 );			
			}).mouseout(function() {			
			//Put back the title attribute's value
			$(this).attr('title',$('.tipBody').html());
			//Remove the appended tooltip template
			$(this).children('div#tooltip').remove();
			});
		});
	
</script>

<style type="text/css">

			
label {
	display: block;
	margin: .25em 0em;
}

.div_table-cell {
	width: 85%;
	height: 26px;
	background-color: #00755e;
	display: table-cell;
	text-align: center;
	vertical-align: middle;
	border-width: 2px;
	border-style: solid;
}

.div_table-cell span {
	height: 100%;
	display: inline-block;
}

.div_table-cell * {
	vertical-align: middle;
}

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

a:hover {
	color: #00755e;
}
/* Tooltip */
#tooltip {
	position: absolute;
	z-index: 9999;
	color: #000000;
	font-size: 10px;
	width: 180px;
	

}

#tooltip .tipHeader {
	height: 8px;
}
/* IE hack */
* html 
	#tooltip .tipHeader {
	margin-bottom: -6px;
}

#tooltip .tipBody {
	background-color: #ffffff;
	border-width: 1px;
	border-style: solid;	
	font-size: 13px;
	padding: 5px;
}

#tooltip .tipFooter {
	height: 8px;
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

	<%
	    EsunDocBo bo = null;
	    ArrayList<DocDtl> array_DocDtl = null;
	    int error_flag = 0 ;
		
	    String docName = "";
	    //要去比看session 的值  跟收到的超連結是否一樣 		
	    String DocID = ((String) request.getParameter("DocID")).trim();
	    String session_DocID = (String) session.getAttribute("DocID");
		String history = null;
		if (request.getParameter("his") != null){
			history = ((String) request.getParameter("his")).trim();
		}
		else {
			history = "";
		}				
	    String[] compare_href_session = session_DocID.split("\\+");
		
		String [] docAuth = ((String) session.getAttribute("DocAuth")).split("\\+");
		String thisDocAuth ="";
		
	    int turn_back = 0; // 判斷使用者亂敲 DocID ID 的flag
	    for (int i = 0; i < compare_href_session.length; i++) {
	        //Loop只去撈一次資料庫 for 是為了判斷這個DocID 使用者可不可以看文章
	        if (compare_href_session[i].equals(DocID.trim())   || session.getAttribute("user_Auth").equals(
	                        Const.Manager)) {
				thisDocAuth = docAuth[i];				
	            //如果超聯結的值在session 裡 或是 管理者
	            if (turn_back == 0) { // 確保只會去撈一次資料庫
	                try {
	                    bo = new EsunDocBo();
						if ( history.equals("T") ) {   //代表在歷史區
							array_DocDtl = bo.getDocDtl(DocID,Const.DocID_For_In_Hiszone);
						}  
						else {  //不在歷史區
							array_DocDtl = bo.getDocDtl(DocID,Const.DocID_For_Not_In_Hiszone);
						}
	                    //array_DocDtl = bo.getDocDtl(DocID);
	                    docName = bo.getDocName(DocID);
	                    turn_back++;
	                } catch (Exception e) { 
					    e.printStackTrace();
					    error_flag ++ ; 		 		                    
	                } finally {
	                    try {
	                        bo.disconnect();
	                    } catch (Exception e) {
	                        e.printStackTrace();
	                    }
	                }
	            }
	        }
	    }
	    if (turn_back == 0) { // 導回登入畫面  
	        response.sendRedirect("../common/logout.jsp");
	        return;
	    }
	%>

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
								: <%=session.getAttribute("user_Name")%></font> <br> <font
							style="FONT-SIZE: 13px; LETTER-SPACING: 2px; TEXT-ALIGN: left; color: #006699;">規格名稱
								: <%=docName%>
						</font></td>
					</tr>
				</table>
			</td>
		</tr>
		<tr>
			<td colspan="3" class=default-td align="center">
				<%
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
								}
						%>
				<% if ( !history.equals("T") &&(  thisDocAuth.equals(Const.Manager)  || session.getAttribute("user_Auth").equals(Const.Manager)))    { %>	
				
					<div class="div_table-cell">
						<span></span><b><font color="#FFFFFF" size=3>上傳檔案</font></b>
					</div>


					<div
						style="border-width: 1px; border-style: solid; background-color: #f0ffe3; float: center; width: 85%; position: relative">
						<form id="myForm" method="post" action="AddDocDtlServlet"
							enctype="multipart/form-data">
							<div style="position: relative; float: left; left: 3%; top: 1.2%">
								<input type="hidden" name="count_memo" value=""> 
								<input type="file" class="button2" name="file1" id="file1" /> &nbsp;
								<input size="51" type="text" name="memo1" id = "memo1" value="說明文件"
									onfocus="if (value =='說明文件'){value =''}"
									onblur="if (value ==''){value='說明文件'}" /> <input type="hidden"
									name="DocID" value="<%=request.getParameter("DocID")%>">
								<input type="hidden" name="userName"
									value="<%=(String) session.getAttribute("user_Name")%>">																														 		   
							</div>
							<div style="position: absolute; right: 3%; top: 1.2%">
								<input type="button" title="新增上傳檔案"
									style="cursor: pointer; background-image: url(../image/plus_.png); width: 22px; height: 22 px;"
									onclick="addTextBox(myForm,this.parentNode)" />
							</div>
						</form>
					</div>

				<p>
					<input id=disable type=button class="button" value="送出"
						onclick="submitForms()">
				</p> 
				<%}%>
				<!-- 下載檔案 -->
				
				<%
					boolean print_table =  false  ;
					for (int i = 0 ; i < array_DocDtl.size() ; i ++){   
						if ( !history.equals("T") && array_DocDtl.get(i).getInHistoryZone().equals("N")){   // 裡面只要有一個就印
							print_table = true ;
						}
						else if (history.equals("T") && array_DocDtl.get(i).getInHistoryZone().equals("Y")){
							print_table = true ;
						}
					}
				%> 

				<%
					if ( !history.equals("T") && !print_table && array_DocDtl.size() != 0){
				%>	
					<font color='red'><b>僅
					<a style="border-left-width: 1px; border-bottom-width: 1px; border-right-width: 1px;  border-top-width: 1px;  border-style: outset; text-decoration:underline ; padding: 3px; -moz-border-radius:;  font-size: 13px ; color: red;" href=dtl.jsp?DocID=<%=DocID%>&his=T target="_self">
						備份區
					</a>
					有資料	
					</b></font>
					<br><br><br><br><br><br>																																									
				<%		
					}else if ( history.equals("T") && array_DocDtl.size() != 0 && !print_table ) {
				%>	
					<font color='red'><b>無資料</b></font>
					
					<a style="border-left-width: 1px; border-bottom-width: 1px; border-right-width: 1px;  border-top-width: 1px;  border-style: outset; text-decoration:underline ; padding: 3px; -moz-border-radius:;  font-size: 13px ; color: red;" href=dtl.jsp?DocID=<%=DocID%>  target="_self">
					<b>	
						回檔案目錄
					</b>																																																					
					</a>
					<br><br><br><br><br><br>	
				<%
					}
				%>
				
				<%if (print_table) {%>
					<div>
						<Form method="post" action="DeleteDocDtlServlet" id="delete">
							<table class="sample" width=90%>
								<tr>
									<th><b>上傳檔名</b></th>
									<th><b>異動<br>人員</b></th>
									<th><b>人員編號</b></th>
									<th><b>異動時間</b></th>
									<th><b>檔案<br>大小</b></th>
									<th><b>規格說明</b></th>						
									<% if (!history.equals("T")  && (thisDocAuth.equals(Const.Manager)  || session.getAttribute("user_Auth").equals(Const.Manager)))    { %>
									<th  nowrap><b>										 												
											<a style=" border-left-width: 1px; border-bottom-width: 1px; border-right-width: 1px;  border-top-width: 1px;  border-style: outset; text-decoration:underline ; padding: 3px; -moz-border-radius:;  font-size: 13px ; color: #ffffff;"   onMouseOver="this.style.color='#0F0'" onMouseOut="this.style.color='#ffffff'" href=dtl.jsp?DocID=<%=DocID%>&his=T 	target="_self">
												備份區
											</a></b>
									</th> 
									<%
										}else if ( history.equals("T")  && (thisDocAuth.equals(Const.Manager)  || session.getAttribute("user_Auth").equals(Const.Manager)))    { 
									%>										
										<th><b>										
											<a id="link" style=" border-left-width: 1px; border-bottom-width: 1px; border-right-width: 1px;  border-top-width: 1px;  border-style: outset; text-decoration:underline ; padding: 3px; -moz-border-radius:;  font-size: 13px ; color: #ffffff;" onMouseOver="this.style.color='#0F0'" onMouseOut="this.style.color='#ffffff'"  href=dtl.jsp?DocID=<%=DocID%> 	target="_self">										
												回到規格																																																				
											</a>																						
										</b></th>										
									<%
										}	
									%>									
									<% if ( !history.equals("T") &&( thisDocAuth.equals(Const.Manager)  || session.getAttribute("user_Auth").equals(Const.Manager)))    { %>	
										<th><b>
											刪除
										</b></th>
									<%}%>	
									
									<%
										if (  !history.equals("T") && ( thisDocAuth.equals(Const.Manager) || ((String)session.getAttribute("user_Auth")).trim().equals(Const.Manager))) {
									%>
									<th><b>通知</b> <%
										   }//end if
									 %>										
									
								</tr>
								<%
									for (int i = 0; i < array_DocDtl.size(); i++) {																				
											%>
											<tr>																												
												<td bgcolor="#E7CDFF" align="left">										
														<a style="text-decoration:none" rel="tooltip" title="<%=array_DocDtl.get(i).getFileName()%>" href=FileDownloadServlet?key=<%=array_DocDtl.get(i).getDocID() + "*"+ array_DocDtl.get(i).getDocSN()%>
															target="_self">																				
															<%=array_DocDtl.get(i).getFileName()%>		
														</a>									
												</td>									
												
												<td align="center" nowrap>									
												<%=new esunbank.esunutil.CommonUtil().getCommonUser(
													array_DocDtl.get(i).getSysid()).getEMCNM()%></td>
												<td align="center"><%=array_DocDtl.get(i).getSysid()%></td>
												<td align="center">
												<%						
													out.println(array_DocDtl.get(i).getSysdt().substring(0,10) + "<br>" + array_DocDtl.get(i).getSysdt().substring(10,array_DocDtl.get(i).getSysdt().length()));																														
												%></td>
												
												<%
													if (array_DocDtl.get(i).getFileSize() < 1024) { //不到1K										    
															float temp;
															int temp2 = (int) array_DocDtl.get(i).getFileSize();
															temp = (float) temp2 / (float) 1024.0;
															out.println("<td  align=\"right\"  > "
																	+ String.format("%.2f", temp) + "K</td> ");
															//取四捨五入
														} else { //大於1K
															out.println("<td  align=\"right\"  > "
																	+ array_DocDtl.get(i).getFileSize() / 1024
																	+ "K</td> ");
														}
												%>
													
												<td align="left">
													<a rel="tooltip" title="<%=array_DocDtl.get(i).getMemo()%>"> 
														<img src="../image/file.gif" width="12" height="12" align="left">
															<% 
																if (array_DocDtl.get(i).getMemo().length() > 11) { //memo只顯示前16個字  不這樣判斷會overflow
															%> <%=array_DocDtl.get(i).getMemo().substring(0, 11)%>
															<%
																} else {
															%> <%=array_DocDtl.get(i).getMemo()%>
															<%
																}
															%>
													</a>
												</td>				

												<% if ((thisDocAuth.equals(Const.Manager)  || session.getAttribute("user_Auth").equals(Const.Manager)) &&  !history.equals("T")  )  { %>							 
													<td align="center"><img style="cursor: pointer"
														src="../image/backup.png" width="40" height="35" align="middle"
														onclick="confirm2HistoryZone('<%=array_DocDtl.get(i).getDocID() + "+"+ array_DocDtl.get(i).getDocSN()  %> ')" />
													</td>
																				
													<td align="center"><img style="cursor: pointer"
														src="../image/cross.png" width="12" height="12" align="middle"
														onclick="confirmDelete('<%=array_DocDtl.get(i).getDocID() + "+"+ array_DocDtl.get(i).getDocSN() %>')" />
													</td>
													
													<td align="center">
														<img style="cursor: pointer" src="../image/mail.jpg" width="16" height="14" align="middle" onclick="confirmSendMail('<%=array_DocDtl.get(i).getDocID()%>','<%=array_DocDtl.get(i).getDocSN()%>')" />
													</td>
								
													
												<%
												} else if ((thisDocAuth.equals(Const.Manager)  || session.getAttribute("user_Auth").equals(Const.Manager)) &&  history.equals("T")  )  { 
												%>
													<td align="center"><img id = "jpg<%=i%>"   style="cursor: pointer"
														src="../image/restore.jpg"  width="40" height="35" align="middle"
														onclick="confirmRetore(' <%=array_DocDtl.get(i).getDocID() + "+"+ array_DocDtl.get(i).getDocSN()  %> ' , this.id)" />		
														
													</td>
												
												<%
												}	
												%>	
											</tr>		
											
									<%				
										
									}
									%>
								
							</table>
						</Form>
					</div>
				<%} else if (array_DocDtl.size() == 0) {			
					out.println("<p><font color='red'><b>無資料</b></font></p>");
				}//end if condition %>	
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
