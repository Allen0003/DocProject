package esunbank.esundoc.action;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import esunbank.esundoc.bo.EsunDocBo;
import esunbank.esundoc.entity.DocDtl;
import esunbank.esundoc.util.Const;
import esunbank.esunutil.CommonUser;
import esunbank.esunutil.CommonUtil;
import esunbank.esunutil.StringUtil;
import esunbank.esunutil.info.MailUtil;

public class SendMailServlet extends HttpServlet {

    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession(true);
        boolean getout = true;

        if (session.getAttribute("user_Auth") == null
                || session.getAttribute("DocAuth") == null) {
            getout = true;
        }

        String docID = request.getParameter("docID") != null ? request
                .getParameter("docID").trim() : "";

        String[] identifyDocAuth = ((String) session.getAttribute("DocAuth"))
                .split("\\+");

        String[] identifydocID = ((String) session.getAttribute("DocID"))
                .split("\\+");

        if (session.getAttribute("user_Auth").equals(Const.Manager)) {
            getout = false;
        } else if (docID != null) {
            for (int i = 0; i < identifydocID.length; i++) {
                if (identifydocID[i].equals(docID)) { // 找到傳進來的那一筆
                    if (identifyDocAuth[i].equals(Const.Manager)) {
                        getout = false;
                    }
                }
            }
        }

        if (getout) {
            response.sendRedirect("../common/logout.jsp");
            return;
        }

        String url = "../query/list.jsp?mes=success";
        EsunDocBo bo = null;
        ArrayList<DocDtl> docDtls = null;
        ArrayList<String> sendList = null;
        String docName = null;
        try {

            String docSN = request.getParameter("docSN") != null ? request
                    .getParameter("docSN").trim() : "";
            if (docID.equals("")) {
                url = "../query/list.jsp?mes=fail";
            } else {
                bo = new EsunDocBo();
                docDtls = new ArrayList<DocDtl>();
                sendList = new ArrayList<String>();
                sendList = bo.getSendList(docID);
                docName = bo.getDocName(docID);
                if (docSN.equals("")) { // 要撈DocCtl
                    docDtls = bo.getDocDtl(docID);
                } else if (!docSN.equals("")) { // 只撈一個
                    docDtls.add(bo.getDocDtl(docID, docSN));
                    url = "../query/dtl.jsp?mes=success&DocID=" + docID;
                }
            }

            String subject = "";
            String content = "";
            if (docDtls.size() == 0) {
                url = "../query/list.jsp?mes=nodata";
                response.sendRedirect(url);
                return;
            } else if (docDtls.size() != 0) { // 真的要寄信了
                subject = "EsunDoc文件更新通知~" + docName;
                content += "<table  width=\"900\" bgcolor=\"#00755e\" "
                        + "cellpadding=\"3\" border = \"1\"  bordercolor =\"#00755e\" "
                        + "cellspacing=\"1\" ALIGN=\"center\" VALIGN=\"center\">";

                content += "<tr><td  bgcolor = \"#00755e\" colspan = \"9\" align  = \"center\" valign = \"center\"> ";

                content += "<font color = \"ffffff\">" + docName
                        + "</font></td></tr>";

                content += "<tr><td colspan = \"3\"  bgcolor = \"#f0ffe3\">"
                        + "檔案" + "</td>";
                content += "<td colspan = \"3\"  bgcolor = \"#f0ffe3\">"
                        + "異動人員" + "</td>";
                content += "<td colspan = \"3\"  bgcolor = \"#f0ffe3\">"
                        + "異動時間 " + "</td></tr>";

                for (int i = 0; i < docDtls.size(); i++) {
                    content += "<tr><td colspan = \"3\"  bgcolor = \"#ffffff\">"
                            + docDtls.get(i).getFileName() + "</td>";
                    content += "<td colspan = \"3\"  bgcolor = \"#ffffff\">"
                            + new esunbank.esunutil.CommonUtil().getCommonUser(
                                    docDtls.get(i).getSysid()).getEMCNM()
                            + "</td>";
                    content += "<td colspan = \"3\"  bgcolor = \"#ffffff\">"
                            + docDtls.get(i).getSysdt() + "</td></tr>";

                }
            }

            content += "</table>";
            String combine = "";
            CommonUser commonUser = null;

            for (int i = 0; i < sendList.size(); i++) { // 找一下寄信的人

                commonUser = new CommonUtil().getCommonUser(sendList.get(i));
                if (commonUser.getEMAIL() != null
                        && commonUser.getEMAIL().contains("@")) {
                    combine += commonUser.getEMAIL() + ",";
                }
            }

            if (!combine.equals("")) {
                combine = combine.substring(0, combine.length() - 1);

                System.out.println("combine = " + combine);
                combine = "apss-14105@email.esunbank.com.tw";
                new MailUtil().isSendMail(combine.split(","), null, null, null,
                        subject, content, null);
            }

        } catch (Exception e) {
            url = "../query/list.jsp?mes=fail";
            Const.logUtil.Error(
                    "發送郵件錯誤：" + StringUtil.getStackTraceASString(e), true);
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