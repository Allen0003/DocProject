package esunbank.esundoc.action;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import esunbank.esundoc.bo.EsunDocBo;
import esunbank.esundoc.entity.SysUser;
import esunbank.esundoc.util.Const;
import esunbank.esunutil.StringUtil;

public class AddSysUserServlet extends HttpServlet {

    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(true);
        // 管理者才有此功能
        if (session.getAttribute("user_Auth") == null
                || !session.getAttribute("user_Auth").equals(Const.Manager)) {
            response.sendRedirect("../common/logout.jsp");
            return;
        }
        EsunDocBo bo = null;
        String url = "../manager/list.jsp?mes=success";
        try {
            SysUser sysUser = new SysUser();

            sysUser.setAUTH(Const.User);
            sysUser.setIsAction("Y");

            sysUser.setUid(request.getParameter("qryUser") != null ? request
                    .getParameter("qryUser").trim() : "");

            sysUser.setSysid(session.getAttribute("user_No") != null ? ((String) session
                    .getAttribute("user_No")).trim() : "");

            if (sysUser.getUid().equals("") || sysUser.getSysid().equals("")) {
                url = "../manager/list.jsp?mes=fail";
            } else {
                bo = new EsunDocBo();
                bo.addSysUser(sysUser);
            }

        } catch (Exception e) {
            url = "../manager/list.jsp?mes=fail";
            Const.logUtil.Error(
                    "AddSysUserServlet 錯誤："
                            + StringUtil.getStackTraceASString(e), true);
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
