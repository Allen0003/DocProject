package esunbank.esundoc.action;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import esunbank.esundoc.bo.EsunDocBo;
import esunbank.esundoc.entity.DocDtl;
import esunbank.esundoc.util.Const;
import esunbank.esunutil.StringUtil;

import java.net.URLEncoder;

public class FileDownloadServlet extends HttpServlet {

    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(true);
        if (session.getAttribute("user_Auth") == null
                || session.getAttribute("DocID") == null) {
            response.sendRedirect("../common/logout.jsp");
            return;
        }

        String[] temp = null;
        String[] compare = null;

        String url = "../common/logout.jsp";
        temp = ((String) session.getAttribute("DocID")).split("\\+");

        String key = request.getParameter("key") != null ? request
                .getParameter("key").replace("*", "+") : "";
        compare = key.split("\\+");

        // �n�ݶW�p���̭����Ĥ@�X�O�_���bsession �̭�
        // �N��O�o��Doc �ϥΪ̬O�_���v��
        DocDtl fileAttribute = new DocDtl();
        EsunDocBo bo = null;
        try {
            // ���bsession �̭� �~�h�� DB
            // �άO�v���O�޲z�̮� �޲z���H�K�A�h��
            if (temp != null && compare != null) {
                for (int i = 0; i < temp.length; i++) {
                    if ((temp[i].equals(compare[0]) || session.getAttribute(
                            "user_Auth").equals(Const.Manager))
                            && !key.equals("")) {
                        bo = new EsunDocBo();
                        fileAttribute = bo.getDocDtl(compare[0],
                                compare[1]);
                        break;
                    }
                }
            }
			if (bo == null || fileAttribute == null) { // �p�G�ϥΪ̶úV�h��ϥΪ̾ɱ�
				response.sendRedirect(url);
				return;
			}
			
			response.setContentType("application/octet-stream; charset=UTF-8");
			response.setHeader("Content-Disposition", "attachment; filename=\""
					+ URLEncoder.encode(fileAttribute.getFileName(), "UTF-8")
					+ "\"");
			response.getOutputStream().write(fileAttribute.getFile_byte(), 0,
					fileAttribute.getFile_byte().length);
			
			response.setStatus(HttpServletResponse.SC_OK);
			response.flushBuffer();
			
        } catch (Exception e) {
            Const.logUtil.Error(
                    "�U���ɮץ��ѡG" + StringUtil.getStackTraceASString(e), true);
        } finally {
            try {
                bo.disconnect();
            } catch (Exception e) {
            }
        }
    }
}
