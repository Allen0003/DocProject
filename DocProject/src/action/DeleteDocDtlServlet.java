package esunbank.esundoc.action;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import esunbank.esundoc.bo.EsunDocBo;
import esunbank.esundoc.util.Const;
import esunbank.esunutil.StringUtil;

public class DeleteDocDtlServlet extends HttpServlet {

    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(true);
        if (session.getAttribute("user_Auth") == null) { 
            response.sendRedirect("../common/logout.jsp");
            return;
        }
        EsunDocBo bo = null;
        String url = "../query/dtl.jsp?mes=success&DocID="
                + request.getParameter("docID");
        try {
            bo = new EsunDocBo();

            if (session.getAttribute("user_No") == null
                    || ((String) session.getAttribute("user_No")).trim()
                            .equals("")
                    || request.getParameter("docID") == null
                    || request.getParameter("docID").trim().equals("")
                    || request.getParameter("docSN") == null
                    || request.getParameter("docSN").trim().equals("")) {
                url = "../query/dtl.jsp?mes=fail&DocID="
                        + request.getParameter("docID");
            } else {

                bo.deleteDocDtl(request.getParameter("docID").trim(), request
                        .getParameter("docSN").trim(), ((String) session
                        .getAttribute("user_No")).trim());
            }

        } catch (Exception e) {
            url = "../query/dtl.jsp?mes=fail&DocID="
                    + request.getParameter("docID");
            Const.logUtil.Error(
                    "§R°£DocDtl¥¢±Ñ¡G" + StringUtil.getStackTraceASString(e), true);
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
