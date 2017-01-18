package action;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bo.DocBo;
import entity.SysUser;
import util.Const;

public class UpdUserAuthServlet extends HttpServlet {

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
		DocBo bo = null;

		String url = "../manager/list.jsp?mes=success";
		try {
			SysUser sysUser = null;
			sysUser = new SysUser();
			sysUser.setUid(request.getParameter("Uid") != null ? request.getParameter("Uid").trim() : "");

			sysUser.setAUTH(request.getParameter("AUTH") != null ? request.getParameter("AUTH").trim() : "");

			sysUser.setUserName(
					session.getAttribute("user_No") != null ? ((String) session.getAttribute("user_No")).trim() : "");

			ArrayList<String> docIDString = new ArrayList<String>();
			if (!sysUser.getAUTH().equals(Const.Manager) && request.getParameterValues("ids[]") != null) {
				// 防止一開始狀態是管理者然後亂點文件
				String[] temp = request.getParameterValues("ids[]");
				if (temp != null && temp.length > 0) {
					for (int k = 0; k < temp.length; k++) {
						if (!temp[k].equals("on")) {
							docIDString.add(temp[k]);
						}
					}
				}
			}
			sysUser.setDocIDs(docIDString);
			ArrayList<String> docAuth = new ArrayList<String>();
			for (int i = 0; i < sysUser.getDocIDs().size(); i++) {
				docAuth.add(request.getParameter("DocAuth" + sysUser.getDocIDs().get(i)) != null
						? request.getParameter("DocAuth" + sysUser.getDocIDs().get(i)).trim() : "");
			}
			sysUser.setDocAuth(docAuth);

			if (sysUser.getUid().equals("") || sysUser.getAUTH().equals("") || sysUser.getUserName().equals("")) {
				url = "../manager/list.jsp?mes=fail";
			} else {
				bo = new DocBo();
				bo.updUserDocID(sysUser);
			}
		} catch (Exception e) {
			url = "../manager/list.jsp?mes=fail";
			Const.logUtil.info("UpdUserAuthServlet 新增資料錯誤");
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
