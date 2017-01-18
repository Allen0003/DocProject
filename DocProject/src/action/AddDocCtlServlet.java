package action;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bo.DocBo;
import entity.DocCtl;
import util.Const;

public class AddDocCtlServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		HttpSession session = request.getSession(true);

		// �޲z�̤~�����\��
		if (session.getAttribute("user_Auth") == null || !session.getAttribute("user_Auth").equals(Const.Manager)) {
			response.sendRedirect("../common/logout.jsp");
			return;
		}

		DocBo bo = null;
		String url = "../query/list.jsp?mes=success";
		try {
			// aaaa
			DocCtl docCtl = new DocCtl();
			docCtl.setDocID(Const.getKey(Const.DocID_Type_Ctl));

			docCtl.setDocType(
					Integer.parseInt(request.getParameter("num") != null ? request.getParameter("num").trim() : ""));

			docCtl.setDocName(request.getParameter("docName") != null ? request.getParameter("docName").trim() : "");

			docCtl.setMemo(request.getParameter("memo")); // �i�H����

			docCtl.setSysid(
					session.getAttribute("user_No") != null ? ((String) session.getAttribute("user_No")).trim() : "");

			// int ����P�_ �� blank ���L �L�|�ܦ� 0
			if (docCtl.getDocName().equals("") || docCtl.getSysid().equals("")) {
				url = "../query/list.jsp?mes=fail";
			} else {
				bo = new DocBo();
				bo.addDocCtl(docCtl);
			}

		} catch (Exception e) {
			url = "../query/list.jsp?mes=fail";
			Const.logUtil.info("AddDocCtlServlet ���~�G");
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
