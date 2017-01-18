package util;

import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

import javax.servlet.http.HttpServlet;

import bo.DocBo;
import esunbank.esunutil.StringUtil;
import test.TestLog;

@SuppressWarnings("serial")
public class InitService extends HttpServlet {

	public void init() {

		Const.ENVIRONMENT = getInitParameter("ENVIRONMENT");
		Const.SSO = getInitParameter("SSO"); // 連線失敗導入的地方
		Const.SSOSystemName = getInitParameter("SSOSystemName");
		Const.dB_ServerIP = getInitParameter("dB_ServerIP");
		Const.dB_ServerPort = getInitParameter("dB_ServerPort");
		Const.dB_ID = getInitParameter("dB_ID");
		Const.dB_PassWD_IV = getInitParameter("dB_PassWD_IV");
		Const.systemID = getInitParameter("systemID");
		Const.dB_DatabaseName = getInitParameter("dB_DatabaseName");

		DocBo bo = null;

		try {
			Const.logUtil = Logger.getLogger(TestLog.class.getName());
			FileHandler fh = new FileHandler("C:/var/Doc/MyLogFile.log");
			Const.logUtil.addHandler(fh);
			SimpleFormatter formatter = new SimpleFormatter();
			fh.setFormatter(formatter);
		} catch (Exception e) {

		}

		// try {
		// Hashtable<String, String> pwd = null;
		// ArrayList<String[]> pwdList = new ArrayList<String[]>();
		// SSLClient sslc = new SSLClient();
		// String[] db_Password_pkIV = { "dB_PassWD", Const.dB_PassWD_IV };
		// pwdList.add(db_Password_pkIV);
		// pwd = sslc.getParamEncrypt(Const.systemID, pwdList);
		// if (sslc.isGetParamSuccess(pwd)) {
		// Const.dB_PassWD = pwd.get("dB_PassWD");
		// } else {
		// throw new Exception("密碼取得失敗：" + pwd.get(SSLClient.queryRSCode));
		// }
		// } catch (Exception e) {
		// e.printStackTrace();
		// }
		try {
			// bo = new DocBo();
			// Const.DocType = bo.getDocType();
		} catch (Exception e) {
			Const.logUtil.info("InitService 錯誤：" + StringUtil.getStackTraceASString(e));
		} finally {
			try {
				bo.disconnect();
			} catch (Exception e) {
			}
		}
	}
}
