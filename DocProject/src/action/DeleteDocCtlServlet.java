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

public class DeleteDocCtlServlet extends HttpServlet {

    // �ȨϥΪ̶ÿ�J���} �ҥH�n��
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession(true);

        // �޲z�̤~�����\��
        if (session.getAttribute("user_Auth") == null
                || !session.getAttribute("user_Auth").equals(Const.Manager)) {
            response.sendRedirect("../common/logout.jsp");
            return;
        }
        String url = "../query/list.jsp?mes=success";
        EsunDocBo bo = null;
        try {
            bo = new EsunDocBo();
            // �o���妸�Ǹ��@�_��i�h

            if (session.getAttribute("user_No") == null
                    || ((String) session.getAttribute("user_No")).trim()
                            .equals("") || request.getParameter("key") == null
                    || request.getParameter("key").trim().equals("")) {
                url = "../query/list.jsp?mes=fail";
            } else {

                bo.deleteDocCtl(request.getParameter("key").trim(),
                        (String) session.getAttribute("user_No"),
                        Const.getKey(Const.DocID_Type_Ctl));
            }

        } catch (Exception e) {
            url = "../query/list.jsp?mes=fail";
            Const.logUtil.Error("�R�����ѡG" + StringUtil.getStackTraceASString(e),
                    true);
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
