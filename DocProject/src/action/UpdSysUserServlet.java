package action;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bo.DocBo;
import entity.SysUser;
import util.Const;

public class UpdSysUserServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession(true);

		if (session.getAttribute("user_Auth") == null || !session.getAttribute("user_Auth").equals(Const.Manager)) {
			response.sendRedirect("../common/logout.jsp");
			return;
		}
		String url = "../manager/list.jsp?mes=success";
		DocBo bo = null;
		try {
			SysUser sysUser = null;
			sysUser = new SysUser();

			sysUser.setUid(request.getParameter("Uid") != null ? request.getParameter("Uid").trim() : "");

			sysUser.setIsAction(
					request.getParameter("IsAction") != null ? request.getParameter("IsAction").trim() : "");

			sysUser.setUserName(request.getParameter("user_No") != null ? request.getParameter("user_No").trim() : "");

			if (sysUser.getUid().equals("") || sysUser.getIsAction().equals("") || sysUser.getUserName().equals("")) {
				url = "../manager/list.jsp?mes=fail";
			} else {
				bo = new DocBo();
				bo.uptSysUser(sysUser); // 去更新使用者table
			}
		} catch (Exception e) {
			url = "../manager/list.jsp?mes=fail";
			Const.logUtil.info("更新SysUserTable錯誤：");
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
