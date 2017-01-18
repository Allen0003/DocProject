package esunbank.esundoc.bo;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

import esunbank.esunutil.db.DBUtil;
import esunbank.esundoc.dao.DocCtlDao;
import esunbank.esundoc.dao.DocDtlDao;
import esunbank.esundoc.dao.DocTypeDao;
import esunbank.esundoc.dao.SysUserDao;
import esunbank.esundoc.dao.UserAuthDao;
import esunbank.esundoc.entity.DocCtl;
import esunbank.esundoc.entity.DocDtl;
import esunbank.esundoc.entity.DocIDsAndAuth;
import esunbank.esundoc.entity.DocType;
import esunbank.esundoc.entity.SysUser;
import esunbank.esundoc.entity.UserAuth;
import esunbank.esundoc.util.Const;

public class EsunDocBo {

    public Connection conn;

    DocTypeDao dao_DocType = null;

    UserAuthDao dao_UserAuth = null;

    SysUserDao dao_SysUser = null;

    DocDtlDao dao_DocDtl = null;

    DocCtlDao dao_DocCtl = null;

    public EsunDocBo() throws Exception {
        conn = new DBUtil().getSQLConn(Const.dB_ServerIP, Const.dB_ServerPort,
                Const.dB_DatabaseName, Const.dB_ID, Const.dB_PassWD_IV);
    }

    public ArrayList<UserAuth> getUserAuth() throws SQLException {
        if (dao_UserAuth == null) {
            dao_UserAuth = new UserAuthDao(conn);
        }
        return dao_UserAuth.getUserAuth();
    }

    public void updUserAuthForReadDoc(String[] modifyList, String docID,
            String user_No) throws SQLException {
        if (dao_UserAuth == null) {
            dao_UserAuth = new UserAuthDao(conn);
        }
        try {
            conn.setAutoCommit(false);
            dao_UserAuth.updUserAuthForReadDoc(modifyList, docID, user_No);
            this.conn.commit();
        } catch (Exception e) {
            try {
                this.conn.rollback();
            } catch (SQLException e1) {
            }
        } finally {
            try {
                this.conn.setAutoCommit(true);
            } catch (Exception e) {
            }
        }
    }

    public void updUserAuthForReadDoc(String[] modifyList,
            ArrayList<String> auth, String docID, String user_No)
            throws SQLException {
        if (dao_UserAuth == null) {
            dao_UserAuth = new UserAuthDao(conn);
        }
        try {
            conn.setAutoCommit(false);
            dao_UserAuth.deleteUserAuth(docID, null);
            dao_UserAuth.addUserAuth(modifyList, auth, docID, user_No);
            this.conn.commit();
        } catch (Exception e) {
            try {
                this.conn.rollback();
            } catch (SQLException e1) {
            }
        } finally {
            try {
                this.conn.setAutoCommit(true);
            } catch (Exception e) {
            }
        }
    }

    public ArrayList<String> getUserAuth_NotInDoc(String docID)
            throws SQLException {
        if (dao_UserAuth == null) {
            dao_UserAuth = new UserAuthDao(conn);
        }
        return dao_UserAuth.getUserAuth_NotInDoc(docID);
    }

    public ArrayList<UserAuth> getUserAuth(String docID) throws SQLException {
        if (dao_UserAuth == null) {
            dao_UserAuth = new UserAuthDao(conn);
        }
        return dao_UserAuth.getUserAuth(docID);
    }

    public void updHistoryZone(String docID, String docSN, String userID,
            String inHistoryZone) throws SQLException {
        if (dao_DocDtl == null) {
            dao_DocDtl = new DocDtlDao(conn);
        }
        dao_DocDtl.updHistoryZone(docID, docSN, userID, inHistoryZone);
        dao_DocDtl.updDocCtl(docID, userID);
    }

    public SysUser getUserId(String uid) throws SQLException {
        if (dao_UserAuth == null) {
            dao_UserAuth = new UserAuthDao(conn);
        }
        return dao_UserAuth.getUserId(uid);
    }

    public ArrayList<SysUser> getSysUser_JustUser() throws SQLException {
        if (dao_SysUser == null) {
            dao_SysUser = new SysUserDao(conn);
        }
        return dao_SysUser.getSysUser(Const.User);
    }

    public ArrayList<SysUser> getSysUser() throws SQLException {
        if (dao_SysUser == null) {
            dao_SysUser = new SysUserDao(conn);
        }
        return dao_SysUser.getSysUser(null);
    }

    public ArrayList<String> getSendList(String docID) throws SQLException {
        if (dao_SysUser == null) {
            dao_SysUser = new SysUserDao(conn);
        }
        return dao_SysUser.getSendList(docID);
    }

    public ArrayList<DocCtl> getAllDocCtl() // 管理者走的
            throws SQLException {
        if (dao_DocCtl == null) {
            dao_DocCtl = new DocCtlDao(conn);
        }
        return dao_DocCtl.getAllDocCtl();
    }

    public ArrayList<DocCtl> getDocCtlByID(String[] uid) // 使用者 走的
            throws SQLException {
        if (dao_DocCtl == null) {
            dao_DocCtl = new DocCtlDao(conn);
        }
        return dao_DocCtl.getDocCtlByID(uid);
    }

    public void uptSysUser(SysUser sysUser) throws SQLException {
        if (dao_SysUser == null) {
            dao_SysUser = new SysUserDao(conn);
        }
        dao_SysUser.uptSysUser(sysUser);
    }

    public DocIDsAndAuth getDocIDsAndAuth(String uid) throws SQLException {
        if (dao_UserAuth == null) {
            dao_UserAuth = new UserAuthDao(conn);
        }
        return dao_UserAuth.getUserAuthForDocID(uid);
    }

    public ArrayList<DocCtl> getDocCtlandDocDtl(String key) throws SQLException {
        if (dao_UserAuth == null) {
            dao_UserAuth = new UserAuthDao(conn);
        }
        if (dao_DocCtl == null) {
            dao_DocCtl = new DocCtlDao(conn);
        }
        if (dao_SysUser == null) {
            dao_SysUser = new SysUserDao(conn);
        }
        // 要撈出 全部的 與 user 可以看的
        ArrayList<DocCtl> user_array_all_fila = new ArrayList<DocCtl>();
        // 裡面還有包一個ArrayList
        // 全部的文章數 與 使用者可以看到的文章
        try {
            conn.setAutoCommit(false);
            user_array_all_fila = dao_DocCtl.getAllDocCtl();

            user_array_all_fila.get(0).setDocIDsAndAuth(
                    dao_UserAuth.getUserAuthForDocID(key));
            this.conn.commit();
        } catch (Exception e) {
            try {
                this.conn.rollback();
            } catch (SQLException e1) {
            }
        } finally {
            try {
                this.conn.setAutoCommit(true);
            } catch (Exception e) {
            }
        }
        return user_array_all_fila;
    }

    public void updUserDocID(SysUser sysUser) throws SQLException {

        if (dao_UserAuth == null) {
            dao_UserAuth = new UserAuthDao(conn);
        }
        try {
            this.conn.setAutoCommit(false);
            dao_UserAuth.updSysUser(sysUser);
            dao_UserAuth.deleteUserAuth(null, sysUser.getUid());
            dao_UserAuth.addUserAuth(sysUser);
            this.conn.commit();
        } catch (Exception e) {
            this.conn.rollback();
        } finally {
            this.conn.setAutoCommit(true);
        }
    }

    public ArrayList<DocType> getDocType() throws SQLException {
        if (dao_DocType == null) {
            dao_DocType = new DocTypeDao(conn);
        }
        return dao_DocType.getDocType();
    }

    public void addDocCtl(DocCtl addDocCtl) throws SQLException {
        // DocCtl table
        if (dao_DocCtl == null) {
            dao_DocCtl = new DocCtlDao(conn);
        }
        dao_DocCtl.addDocCtl(addDocCtl);
    }

    public void addDocDtl(DocDtl docDtl) throws SQLException {
        // 把讀入的檔案轉成二進位寫到DB裡
        if (dao_DocDtl == null) {
            dao_DocDtl = new DocDtlDao(conn);
        }
        dao_DocDtl.addDocDtl(docDtl);
        dao_DocDtl.updDocCtl(docDtl.getDocID(), docDtl.getSysid());

    }

    public ArrayList<DocDtl> getDocDtl(String DocID , int hisZone) throws SQLException {
        if (dao_DocDtl == null) {
            dao_DocDtl = new DocDtlDao(conn);
        }
        return dao_DocDtl.getDocDtl(DocID,  hisZone);
    }

    public ArrayList<DocDtl> getDocDtl(String DocID ) throws SQLException {
        if (dao_DocDtl == null) {
            dao_DocDtl = new DocDtlDao(conn);
        }
        return dao_DocDtl.getDocDtl(DocID);
    }
    
    public String getDocName(String DocID) throws SQLException {
        if (dao_DocCtl == null) {
            dao_DocCtl = new DocCtlDao(conn);
        }
        return dao_DocCtl.getDocName(DocID);
    }

    public DocDtl getDocDtl(String DocID, String DocSN)
            throws SQLException {
        if (dao_DocDtl == null) {
            dao_DocDtl = new DocDtlDao(conn);
        }
        return dao_DocDtl.getDocDtl(DocID, DocSN);
    }

    public void deleteDocDtl(String docID, String docSN, String userID)
            throws SQLException {
        if (dao_DocDtl == null) {
            dao_DocDtl = new DocDtlDao(conn);
        }
        try {
            this.conn.setAutoCommit(false);
            // dao_DocDtl.deleteDocDtl(docID, docSN, userID);

            dao_DocDtl.addDocDtlHis(docID, userID,
                    Const.getKey(Const.DocID_Type_Dtl), docSN);
            dao_DocDtl.delectDocDtl(docID, docSN);
            dao_DocDtl.updDocCtl(docID, userID);

            this.conn.commit();
        } catch (Exception e) {
            this.conn.rollback();
        } finally {
            this.conn.setAutoCommit(true);
        }
    }

    public ArrayList<DocCtl> getDocType(String key) throws SQLException {
        if (dao_DocCtl == null) {
            dao_DocCtl = new DocCtlDao(conn);
        }
        return dao_DocCtl.getDocCtlByType(key);
    }

    public ArrayList<DocCtl> getDocType(String key, String uid)
            throws SQLException {
        if (dao_DocCtl == null) {
            dao_DocCtl = new DocCtlDao(conn);
        }
        return dao_DocCtl.getDocCtlByType(key, uid);
    }

    // 改這邊 並且依樣 建立兩個conn
    public void deleteDocCtl(String docID, String userID, String batchNum)
            throws SQLException {
        if (dao_DocDtl == null) {
            dao_DocDtl = new DocDtlDao(conn);
        }
        if (dao_DocCtl == null) {
            dao_DocCtl = new DocCtlDao(conn);
        }
        if (dao_UserAuth == null) {
            dao_UserAuth = new UserAuthDao(conn);
        }

        try {
            this.conn.setAutoCommit(false);

            dao_DocDtl.addDocDtlHis(docID, userID, batchNum, null);// 先刪除dtl在刪除ctl
            dao_DocDtl.delectDocDtls(docID);

            dao_DocCtl.addDocCtlHis(docID, userID, batchNum);
            dao_DocCtl.deleteDocCtl(docID);
            dao_UserAuth.deleteUserAuth(docID, null);

            this.conn.commit();

        } catch (Exception e) {
            this.conn.rollback();
        } finally {
            this.conn.setAutoCommit(true);
        }
    }

    public void addSysUser(SysUser sysUser) throws SQLException {
        if (dao_SysUser == null) {
            dao_SysUser = new SysUserDao(conn);
        }
        dao_SysUser.addSysUser(sysUser);
    }

    public void addDocType(DocType docType) throws SQLException {
        if (dao_DocType == null) {
            dao_DocType = new DocTypeDao(conn);
        }
        dao_DocType.addDocType(docType);
    }

    public void disconnect() throws SQLException {
        if (conn != null) {
            conn.close();
        }
    }

    public void finalize() throws Throwable {
        disconnect();
    }
}
