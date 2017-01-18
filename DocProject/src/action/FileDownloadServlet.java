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

        // 要看超聯結裡面的第一碼是否有在session 裡面
        // 意思是這個Doc 使用者是否有權限
        DocDtl fileAttribute = new DocDtl();
        EsunDocBo bo = null;
        try {
            // 有在session 裡面 才去撈 DB
            // 或是權限是管理者時 管理者隨便你去用
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
			if (bo == null || fileAttribute == null) { // 如果使用者亂敲則把使用者導掉
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
                    "下載檔案失敗：" + StringUtil.getStackTraceASString(e), true);
        } finally {
            try {
                bo.disconnect();
            } catch (Exception e) {
            }
        }
    }
}
