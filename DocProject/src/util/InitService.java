package esunbank.esundoc.util;

import java.util.ArrayList;
import java.util.Hashtable;

import javax.servlet.http.HttpServlet;

import esunbank.esunutil.io.LogUtil;
import esunbank.esunutil.io.ssl.SSLClient;
import esunbank.esundoc.bo.EsunDocBo;
import esunbank.esundoc.util.Const;
import esunbank.esunutil.StringUtil;

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
        Const.logUtil = new LogUtil(getInitParameter("logFile"));
        EsunDocBo bo = null;
        try {
            Hashtable<String, String> pwd = null;
            ArrayList<String[]> pwdList = new ArrayList<String[]>();
            SSLClient sslc = new SSLClient();
            String[] db_Password_pkIV = { "dB_PassWD", Const.dB_PassWD_IV };
            pwdList.add(db_Password_pkIV);
            pwd = sslc.getParamEncrypt(Const.systemID, pwdList);
            if (sslc.isGetParamSuccess(pwd)) {
                Const.dB_PassWD = pwd.get("dB_PassWD");
            } else {
                throw new Exception("密碼取得失敗：" + pwd.get(SSLClient.queryRSCode));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            bo = new EsunDocBo();
            Const.DocType = bo.getDocType();
        } catch (Exception e) {
            Const.logUtil.Error(
                    "InitService 錯誤：" + StringUtil.getStackTraceASString(e),
                    true);
        } finally {
            try {
                bo.disconnect();
            } catch (Exception e) {
            }
        }
    }
}
