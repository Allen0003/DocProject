package action;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bo.DocBo;
import util.Const;

public class FileToHistoryZoneServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		HttpSession session = request.getSession(true);
		if (session.getAttribute("user_Auth") == null) { // 沒有login就想亂刪東西
			response.sendRedirect("../common/logout.jsp");
			return;
		}
		DocBo bo = null;
		String url = "../query/dtl.jsp?mes=success&DocID=" + request.getParameter("docID");
		try {
			bo = new DocBo();

			if (session.getAttribute("user_No") == null || ((String) session.getAttribute("user_No")).trim().equals("")
					|| request.getParameter("docID") == null || request.getParameter("docID").trim().equals("")
					|| request.getParameter("inHistoryZone").trim().equals("")
					|| request.getParameter("inHistoryZone") == null || request.getParameter("docSN") == null
					|| request.getParameter("docSN").trim().equals("")) {
				url = "../query/dtl.jsp?mes=fail&DocID=" + request.getParameter("docID");
			} else {

				bo.updHistoryZone(request.getParameter("docID").trim(), request.getParameter("docSN").trim(),
						((String) session.getAttribute("user_No")).trim(),
						request.getParameter("inHistoryZone").trim());

				if (request.getParameter("inHistoryZone").trim().equals("N")) { // 要轉回去的
					url = "../query/dtl.jsp?mes=success&DocID=" + request.getParameter("docID");
				}
			}

		} catch (Exception e) {
			url = "../query/dtl.jsp?mes=fail&DocID=" + request.getParameter("docID");
			Const.logUtil.info("刪除DocDtl失敗：");
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
