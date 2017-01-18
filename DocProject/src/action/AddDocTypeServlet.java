package esunbank.esundoc.action;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import esunbank.esundoc.bo.EsunDocBo;
import esunbank.esundoc.entity.DocType;
import esunbank.esundoc.util.Const;
import esunbank.esunutil.StringUtil;

public class AddDocTypeServlet extends HttpServlet {

    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(true);
        // 管理者才有此功能
        if (session.getAttribute("user_Auth") == null
                || !session.getAttribute("user_Auth").equals(Const.Manager)) {
            response.sendRedirect("../common/logout.jsp");
            return;
        }
        String url = "../createDocCtl/createDocCtl.jsp?mes=success";
        EsunDocBo bo = null;
        try {
            DocType docType = new DocType();

            docType.setTypeName(request.getParameter("DocType") != null ? request
                    .getParameter("DocType").trim() : "");

            docType.setSysid(session.getAttribute("user_No") != null ? ((String) session
                    .getAttribute("user_No")).trim() : "");

            if (docType.getTypeName().equals("")
                    || docType.getSysid().equals("")) {
                url = "../manager/list.jsp?mes=fail";
            } else {
                bo = new EsunDocBo();
                bo.addDocType(docType);
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
