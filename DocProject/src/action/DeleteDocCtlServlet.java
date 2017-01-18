package action;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bo.DocBo;
import util.Const;

public class DeleteDocCtlServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	// �ȨϥΪ̶ÿ�J���} �ҥH�n��
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession(true);

		// �޲z�̤~�����\��
		if (session.getAttribute("user_Auth") == null || !session.getAttribute("user_Auth").equals(Const.Manager)) {
			response.sendRedirect("../common/logout.jsp");
			return;
		}
		String url = "../query/list.jsp?mes=success";
		DocBo bo = null;
		try {
			bo = new DocBo();
			// �o���妸�Ǹ��@�_��i�h

			if (session.getAttribute("user_No") == null || ((String) session.getAttribute("user_No")).trim().equals("")
					|| request.getParameter("key") == null || request.getParameter("key").trim().equals("")) {
				url = "../query/list.jsp?mes=fail";
			} else {

				bo.deleteDocCtl(request.getParameter("key").trim(), (String) session.getAttribute("user_No"),
						Const.getKey(Const.DocID_Type_Ctl));
			}

		} catch (Exception e) {
			url = "../query/list.jsp?mes=fail";
			Const.logUtil.info("�R�����ѡG");
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
