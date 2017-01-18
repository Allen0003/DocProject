package action;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bo.DocBo;
import util.Const;

public class UpdUserForReadDocServlet extends HttpServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		HttpSession session = request.getSession(true);
		String url = "../query/add_user_for_doc.jsp?mes=success";
		if (session.getAttribute("user_Auth") == null) {
			url = "../query/add_user_for_doc.jsp?mes=seriousFail";
			response.sendRedirect(url);
			return;
		}

		String user_No = (String) session.getAttribute("user_No") != null
				? ((String) session.getAttribute("user_No")).trim() : "";

		DocBo bo = null;
		try {

			String docID = (request.getParameter("docID") != null ? request.getParameter("docID").trim() : "");

			String totalUserNum = (request.getParameter("totalUserNum") != null
					? request.getParameter("totalUserNum").trim() : "");

			if (docID.equals("") || user_No.equals("")) {
				url = "../query/add_user_for_doc.jsp?mes=fail";
			} else {
				if (request.getParameterValues("modifyList") == null) {

				} else {
					bo = new DocBo();
					ArrayList<String> auth = new ArrayList<String>();

					for (int i = 0; i < Integer.parseInt(totalUserNum); i++) {
						if (request.getParameter("auth" + i) != null) {
							auth.add(request.getParameter("auth" + i));
						}
					}
					bo.updUserAuthForReadDoc(request.getParameterValues("modifyList"), auth, docID, user_No);
				}
			}
		} catch (Exception e) {
			url = "../query/add_user_for_doc.jsp?mes=fail";
			Const.logUtil.info("UpdUserForReadDocServlet 新增可觀看文件使用者錯誤 ");
		} finally {
			try {
				bo.disconnect();
			} catch (Exception e) {
			}
		}
		response.sendRedirect(url);
		return;
	}
}
