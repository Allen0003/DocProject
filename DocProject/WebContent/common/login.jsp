<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="esunbank.esundoc.bo.*"%>
<%@ page import="esunbank.esundoc.entity.*"%>
<%@ page import="java.util.*"%>
<%@ page import="esunbank.esunutil.*"%>
<%@ page import="esunbank.esundoc.util.*"%>
<%@ page import="esunbank.esunutil.StringUtil"%>




<html>
<head>
<title></title>
<link rel="stylesheet" type="text/css" href="../css/style.css">
</head>
<body>
	<form action="login.jsp" method="post">
		<input type="text" name="User_id"> <input type="submit">
	</form>
	<!-- 在下面撈資料庫    -->
	<%
		
	    if (Const.ENVIRONMENT.equals("T")) {
	        //test
	        if (request.getParameter("User_id") != null) { //有輸入
	            setSession(request.getParameter("User_id"), session); //把UID丟到 session 裡面
	            if (session.getAttribute("user_Name") != null
	                    && session.getAttribute("user_Auth") != null
	                    && !session.getAttribute("user_IsAction").equals(
	                            "N")) {
	                //有撈到
	                if (session.getAttribute("user_Auth").toString()
	                        .contains(Const.Manager)) { //如果是管理者 順便去撈 DocType 

	                    response.sendRedirect("../manager/list.jsp");
	                } else if (session.getAttribute("user_Auth").toString()
	                        .contains(Const.User)) {
	                    response.sendRedirect("../query/list.jsp");
	                } else {
	                    response.sendRedirect("logout.jsp");
	                }
	            } else { //資料庫中沒有資料
	                response.sendRedirect(Const.SSO);
	            }
	        }
	    } 
		else if (request.getParameter("User_id") != null) {
		
			session.setAttribute("User_id", null);
			String User_id = DesEncrypter.decrypt(request.getParameter("User_id"), "abcdefghijklmnopqrstuvwx").trim();
			setSession(request.getParameter("User_id"), session); //把UID丟到 session 裡面
			
            if (session.getAttribute("user_Name") != null
				&&	(new SSOUtil().isSSOLogin(User_id, "FileView", null))
                    && session.getAttribute("user_Auth") != null
                   	&& session.getAttribute("user_IsAction") != null
                    && !session.getAttribute("user_IsAction").equals(
                            "N")) {
                //有撈到
                if (session.getAttribute("user_Auth").toString()
                        .contains(Const.Manager)) { //如果是管理者 順便去撈 DocType 
	                    response.sendRedirect("../manager/list.jsp");
                } else if (session.getAttribute("user_Auth").toString()
                        .contains(Const.User)) {
                    response.sendRedirect("../query/list.jsp");
                } else {
                    response.sendRedirect("logout.jsp");
                }
            } else { //資料庫中沒有資料
                response.sendRedirect(Const.SSO);
            }
        
	    }
	%>


	<!-- </form>   -->
</body>
</html>
<%!void setSession(String user_id, HttpSession session) throws Exception {
        //用這邊撈資料庫	
        EsunDocBo bo = null;
        SysUser user_data = null;
        String temp_DocID;
        String temp_DocAuth;   
        try { 
            bo = new EsunDocBo();
            user_data = bo.getUserId(user_id);
            session.setAttribute("user_Auth", user_data.getAUTH());
            session.setAttribute("user_Name", 
                    new esunbank.esunutil.CommonUtil().getCommonUser(user_id)
                            .getEMCNM()); 
            session.setAttribute("user_IsAction", user_data
                    .getIsAction());
            session.setAttribute("user_No", user_data.getUserNo());

            temp_DocID = ""; 
            temp_DocAuth = ""; 
            for (int i = 0; i < user_data.getDocIDs().size() ; i++) {
                temp_DocID += user_data.getDocIDs().get(i) + "+";
                temp_DocAuth += user_data.getDocAuth().get(i) + "+";
            }
            session.setAttribute("DocID", temp_DocID); //文章數
            session.setAttribute("DocAuth", temp_DocAuth); //文章權限
        } catch (Exception e) { //代表資料庫中撈不道
            session.setAttribute("user_Auth", null);
            session.setAttribute("user_Name", null);
            session.setAttribute("user_IsAction", null);
            session.setAttribute("user_No", null);
            session.setAttribute("DocAuth", null);            
        } finally {
            try {
                bo.disconnect();
            } catch (Exception e) {
                session.setAttribute("user_Auth", null);
                session.setAttribute("user_Name", null);
                session.setAttribute("user_IsAction", null);
                session.setAttribute("user_No", null);
                session.setAttribute("DocAuth", null);
            }
        }

    }%>