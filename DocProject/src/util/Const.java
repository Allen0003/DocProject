package esunbank.esundoc.util;

import esunbank.esundoc.entity.*;
import java.util.ArrayList;

import esunbank.esunutil.io.LogUtil;

public class Const {

    public static final String title = "玉山銀行";

    // 系統名稱
    public static String SSOSystemName;

    // SSO首頁
    public static String SSO;

    // 執行環境
    public static String ENVIRONMENT;

    // 資料庫登入帳號
    public static String dB_ID;

    // 資料庫登入密碼
    public static String dB_PassWD;

    public static String dB_PassWD_IV = "";

    // 資料庫位址
    public static String dB_ServerIP;

    // 資料庫Port 1433
    public static String dB_ServerPort;

    // 資料庫名稱
    public static String dB_DatabaseName;

    // public static LogUtil log = null;

    public static String systemID;

    public static LogUtil logUtil;

    public static final String Manager = "M";

    public static final String User = "U";

    public static final long serialVersionUID = 1L;

    public static final int MEMORY_THRESHOLD = 1024 * 1024 * 3;

    public static final int MAX_FILE_SIZE = 1024 * 1024 * 40;

    public static final int MAX_REQUEST_SIZE = 1024 * 1024 * 50;

    public static ArrayList<DocType> DocType;

    private static long docCtlID = System.currentTimeMillis();

    private static long docDtlID = System.currentTimeMillis();

    public static final int DocID_Type_Ctl = 1;

    public static final int DocID_Type_Dtl = 2;

    public static final int DocID_For_all = 0;

    public static final int DocID_For_In_Hiszone = 1;

    public static final int DocID_For_Not_In_Hiszone = 2;

    public static synchronized String getKey(int type) {

        if (type == DocID_Type_Ctl) {
            docCtlID += 1;
            return Long.toString(docCtlID);
        } else {
            docDtlID += 1;
            return Long.toString(docDtlID);
        }
    }

}
