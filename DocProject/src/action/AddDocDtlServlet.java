package action;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.tomcat.util.http.fileupload.FileItem;
import org.apache.tomcat.util.http.fileupload.RequestContext;
import org.apache.tomcat.util.http.fileupload.disk.DiskFileItemFactory;
import org.apache.tomcat.util.http.fileupload.servlet.ServletFileUpload;

import bo.DocBo;
import entity.DocDtl;
import util.Const;

public class AddDocDtlServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		HttpSession session = request.getSession(true);
		if (session.getAttribute("user_Auth") == null) {
			response.sendRedirect("../common/logout.jsp");
			return;
		}

		String FileName = "";

		DocBo bo = null;

		DiskFileItemFactory factory = new DiskFileItemFactory();
		factory.setSizeThreshold(Const.MEMORY_THRESHOLD);
		factory.setRepository(new File(System.getProperty("java.io.tmpdir")));
		ServletFileUpload upload = new ServletFileUpload(factory);
		upload.setFileSizeMax(Const.MAX_FILE_SIZE);
		upload.setSizeMax(Const.MAX_REQUEST_SIZE);
		DocDtl docDtl = new DocDtl();

		String url = ".../query/dtl.jsp?mes=fail";

		// 設定
		try {
			bo = new DocBo();
			// 處理中文檔名問題
			upload.setHeaderEncoding("utf-8");
			request.setCharacterEncoding("utf-8");
			// 設定總上傳大小限制

			List<FileItem> items = upload.parseRequest((RequestContext) request);
			Iterator<FileItem> iter = items.iterator();
			int count_while = 0;
			// 類似flag
			while (iter.hasNext()) {
				count_while++;
				FileItem item = (FileItem) iter.next();
				if (item.isFormField()) {
					// 一般欄位
					if (item.getFieldName().substring(0, 4).equals("memo")) {
						// get memo
						docDtl.setMemo(item.getString("UTF-8")); // 可以為空白
					} else if (item.getFieldName().equalsIgnoreCase("DocID")) {

						docDtl.setDocID(item.getString("UTF-8") != null ? item.getString("UTF-8").trim() : "");

					} /*
						 * else if
						 * (item.getFieldName().equalsIgnoreCase("DocName")) {
						 * // 規格名稱
						 * 
						 * docName = item.getString("UTF-8") != null ? item
						 * .getString("UTF-8").trim() : "";
						 * 
						 * }
						 */else if (item.getFieldName().equalsIgnoreCase("userName")) {

						docDtl.setSysid(session.getAttribute("user_No") != null
								? ((String) session.getAttribute("user_No")).trim() : "");
					}
				} else {

					// 檔案欄位 否則取得檔案資訊
					FileName = item.getName();
					// 因為不同的瀏覽器會造成傳遞 path + filename, 有些則只有 filename
					// for wintel platform
					FileName = FileName.substring(FileName.lastIndexOf("\\") + 1);
					// for unix-like platform
					FileName = FileName.substring(FileName.lastIndexOf("/") + 1);
					docDtl.setFileSize(item.getSize());
					docDtl.setFileName(FileName != null ? FileName.trim() : "");
					docDtl.setFile_byte(item.get());// 檔案大小單位byte

				}
				if (((count_while > 5 && count_while % 2 == 0) || count_while == 5)) {
					if (docDtl.getDocID().equals("") || docDtl.getSysid().equals("")
							|| docDtl.getFileName().equals("")) {
						url = "../query/dtl.jsp?mes=fail&DocID=" + docDtl.getDocID();
					} else {
						bo.addDocDtl(docDtl);
						url = "../query/dtl.jsp?mes=success&DocID=" + docDtl.getDocID();
					}
				}
			}

		} catch (Exception e) {
			url = "../query/dtl.jsp?mes=fail&DocID=" + docDtl.getDocID();
			Const.logUtil.info("AddDocDtlServlet 錯誤：");
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
