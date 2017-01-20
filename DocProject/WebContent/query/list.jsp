<%@ page language="java" pageEncoding="UTF-8"%>
<%@ page import="bo.*"%>
<%@ page import="entity.*"%>
<%@ page import="java.util.*"%>
<!-- 查詢 文件 DocCtl 介面-->
<html>
<head>
<%@include file="../common/head.jsp"%>



<link href="../css/tablesaw.css" rel="stylesheet">
<script src="../js/tablesaw.js"></script>
<script src="../js/tablesaw-init.js"></script>
<link href="../css/AllenTable.css" rel="stylesheet">

<script type="text/javascript" src="../js/tablesort.js">
	
</script>
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

	function confirmSendMail(docID) {

		if (confirm("確認寄信")) {
			document.location.href = "SendMailServlet?docID=" + docID;
		} else {
			return;
		}
	}

	function addNewGroup(docID) { //要有name and DocID

		//放在哪邊

		var left = (screen.width / 3.5);
		var top = (screen.height / 3.5);

		//大小是多少
		var width = (screen.width / 2);
		var height = (screen.height / 2.5);
		map = window
				.open(
						"add_user_for_doc.jsp?docID=" + docID,
						"",
						'toolbar=no, location=no, directories=no, status=no, menubar=no, scrollbars=yes, resizable=no, copyhistory=no,  height='
								+ height
								+ ',  width='
								+ width
								+ ',  top='
								+ top + ', left=' + left);
	}
</script>

</head>
<body role="document">
	<%@include file="../common/navbar.jsp"%>

	<div class="container theme-showcase" role="main">
		<div class="row">
			<div class="col-md-11">
				<h2>Swipe Table</h2>
				<table class="tablesaw col-xs-12 allenTable blueTable"
					data-tablesaw-mode="swipe" data-minimap>
					<thead>
						<tr>
							<th scope="col" data-tablesaw-priority="persist">Doc Name</th>
							<th scope="col">Doc Class</th>
							<th scope="col">Staff Name</th>
							<th scope="col">Staff No.</th>
							<th scope="col">Trans. Time</th>
							<th scope="col">Delete</th>
							<th scope="col">Infor</th>
							<th scope="col">Add Staff</th>
						</tr>
					</thead>
					<tbody>
						<tr>
							<td class="ranked-name"><a href="#"> Amanda </a></td>
							<td class="current-ranking">1</td>
							<td>19.45</td>
							<td>18.72</td>
							<td>5</td>
							<td><img style="cursor: pointer" src="../image/cross.png"
								width="12" height="12" align="middle" /></td>
							<td><img style="cursor: pointer" src="../image/mail.jpg"
								width="16" height="14" align="middle" /></td>
							<td><img style="cursor: pointer" src="../image/group.jpg"
								width="24" height="18" align="middle" /></td>

						</tr>
						<tr class="even">
							<td class="ranked-name"><a href="#"> Dave </a></td>
							<td>2</td>
							<td>36.32</td>
							<td>20.52</td>
							<td>4</td>
							<td><img style="cursor: pointer" src="../image/cross.png"
								width="12" height="12" align="middle" /></td>
							<td><img style="cursor: pointer" src="../image/mail.jpg"
								width="16" height="14" align="middle" /></td>
							<td><img style="cursor: pointer" src="../image/group.jpg"
								width="24" height="18" align="middle" /></td>
						</tr>
						<tr>
							<td class="ranked-name"><a href="#"> Kristen </a></td>
							<td>3</td>
							<td>35.23</td>
							<td>21.36</td>
							<td>2</td>
							<td><img style="cursor: pointer" src="../image/cross.png"
								width="12" height="12" align="middle" /></td>
							<td><img style="cursor: pointer" src="../image/mail.jpg"
								width="16" height="14" align="middle" /></td>
							<td><img style="cursor: pointer" src="../image/group.jpg"
								width="24" height="18" align="middle" /></td>
						</tr>
						<tr class="even">
							<td class="ranked-name"><a href="#"> Kenny </a></td>
							<td>4</td>
							<td>34.65</td>
							<td>27.15</td>
							<td>4</td>
							<td><img style="cursor: pointer" src="../image/cross.png"
								width="12" height="12" align="middle" /></td>
							<td><img style="cursor: pointer" src="../image/mail.jpg"
								width="16" height="14" align="middle" /></td>
							<td><img style="cursor: pointer" src="../image/group.jpg"
								width="24" height="18" align="middle" /></td>
						</tr>
					</tbody>
				</table>
			</div>
		</div>
	</div>

	<%@include file="../common/commonJs.jsp"%>

</body>
</html>
