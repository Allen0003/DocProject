package esunbank.esundoc.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;

import esunbank.esundoc.entity.DocIDsAndAuth;
import esunbank.esundoc.entity.SysUser;
import esunbank.esundoc.entity.UserAuth;

public class UserAuthDao {
    Connection conn;

    public UserAuthDao(Connection conn) { // new 起來時送連線過來
        this.conn = conn;
    }

    public void updUserAuthForReadDoc(String[] modifyList, String docID,
            String user_No) throws SQLException {
        String sql = "INSERT INTO UserAuth ([Uid],DocID,Sysid ,Sysdt,DocAuth )  Values (?,?,?,?,?)";
        PreparedStatement pstmt = conn.prepareStatement(sql);
        for (int i = 0; i < modifyList.length; i++) {
            pstmt.setString(1, modifyList[i]);
            pstmt.setString(2, docID);
            pstmt.setString(3, user_No);
            pstmt.setTimestamp(4, new Timestamp(System.currentTimeMillis()));
            pstmt.setString(5, "R");
            pstmt.addBatch();
        }
        pstmt.executeBatch();
        conn.commit();
    }

    public void addUserAuth(String[] modifyList, ArrayList<String> auth,
            String docID, String user_No) throws SQLException {
        String sql = " insert into UserAuth ([Uid],DocID,Sysid ,Sysdt,DocAuth )  Values (?,?,?,?,?)";
        PreparedStatement pstmt = conn.prepareStatement(sql);
        for (int i = 0; i < modifyList.length; i++) {
            pstmt.setString(1, modifyList[i]);
            pstmt.setString(2, docID);
            pstmt.setString(3, user_No);
            pstmt.setTimestamp(4, new Timestamp(System.currentTimeMillis()));
            pstmt.setString(5, auth.get(i));
            pstmt.addBatch();
        }
        pstmt.executeBatch();
        conn.commit();
    }

    public ArrayList<UserAuth> getUserAuth() throws SQLException {
        String sql = "select * from UserAuth  order by DocID";
        PreparedStatement pstmt = conn.prepareStatement(sql);
        return getUserAuths(pstmt);
    }

    private ArrayList<UserAuth> getUserAuths(PreparedStatement pstmt)
            throws SQLException {
        ResultSet rs = pstmt.executeQuery();
        UserAuth userAuth = null;
        ArrayList<UserAuth> userAuths = new ArrayList<UserAuth>();
        while (rs.next()) {
            userAuth = new UserAuth();
            userAuth.setUid(rs.getString("Uid"));
            userAuth.setDocAuth(rs.getString("DocAuth"));
            userAuth.setDocID(rs.getString("DocID"));
            userAuths.add(userAuth);
        }
        return userAuths;
    }

    public ArrayList<UserAuth> getUserAuth(String docID) throws SQLException {
        String sql = "select * " + " from EsunDoc.dbo.UserAuth "
                + " where DocID = ? ";
        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setString(1, docID);
        return getUserAuths(pstmt);
    }

    public ArrayList<String> getUserAuth_NotInDoc(String docID)
            throws SQLException {
        String sql = "select  [Uid]  from SysUser"
                + " where AUTH = 'U' and isAction = 'Y' "
                + " except select [Uid] from UserAuth " + " where DocID = ? ";

        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setString(1, docID);
        ResultSet rs = pstmt.executeQuery();
        ArrayList<String> uid = new ArrayList<String>();
        while (rs.next()) {
            uid.add(rs.getString("Uid"));
        }
        return uid;
    }

    public SysUser getUserId(String uid) throws SQLException {
        // 比較user 的id 如果存在則回傳 user 的資料
        // 並且傳回 可以觀看的文章數
        String sql = "Select * from SysUser  Left join UserAuth on  "
                + "[SysUser].[Uid] = UserAuth.[Uid] " + " Left join DocCtl on "
                + " UserAuth.DocID = DocCtl.DocID "
                + " Where [SysUser].[Uid] = ?"
                + " order by DocType, DocCtl.Sysdt DESC";

        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setString(1, uid);
        ResultSet rs = pstmt.executeQuery();
        ArrayList<String> docIDString = new ArrayList<String>();
        ArrayList<String> docAuthString = new ArrayList<String>();
        SysUser sysUser = null;
        sysUser = new SysUser();
        while (rs.next()) {
            sysUser.setAUTH(rs.getString("AUTH"));
            sysUser.setIsAction(rs.getString("IsAction"));
            sysUser.setUserNo(rs.getString("Uid"));
            docIDString.add(rs.getString("DocID"));
            docAuthString.add(rs.getString("DocAuth"));
        }
        sysUser.setDocIDs(docIDString);
        sysUser.setDocAuth(docAuthString);
        return sysUser;
    }

    public void addUserAuth(SysUser sysUser) throws SQLException {
        String sql = "INSERT INTO UserAuth ([Uid],DocID,Sysid ,Sysdt,DocAuth )  Values (?,?,?,?,?)";
        PreparedStatement pstmt = conn.prepareStatement(sql);
        for (int i = 0; i < sysUser.getDocIDs().size(); i++) {
            pstmt.setString(1, sysUser.getUid());
            pstmt.setString(2, sysUser.getDocIDs().get(i));
            pstmt.setString(3, sysUser.getUserName());
            pstmt.setTimestamp(4, new Timestamp(System.currentTimeMillis()));
            pstmt.setString(5, sysUser.getDocAuth().get(i));
            pstmt.addBatch(); // 用批次執行
        }
        pstmt.executeBatch();
        conn.commit();
    }

    public void updSysUser(SysUser sysUser) throws SQLException {
        String sql = "Update SysUser Set AUTH = ?  Where [Uid] = ? ";
        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setString(1, sysUser.getAUTH());
        pstmt.setString(2, sysUser.getUid());
        pstmt.executeUpdate();
    }

    public DocIDsAndAuth getUserAuthForDocID(String uid) throws SQLException {
        // 回傳一個在DocCtl 裡面的class
        String sql1 = "Select *  from  UserAuth where [Uid] = ?  order by DocID  ";
        PreparedStatement pstmt;
        ArrayList<String> docIDs = new ArrayList<String>();
        ArrayList<String> docAuth = new ArrayList<String>();

        pstmt = conn.prepareStatement(sql1);
        pstmt.setString(1, uid);
        ResultSet rs1 = pstmt.executeQuery();
        while (rs1.next()) {
            docIDs.add(rs1.getString("DocID"));
            docAuth.add(rs1.getString("DocAuth"));
        }
        DocIDsAndAuth docIDsAndAuth = new DocIDsAndAuth();
        docIDsAndAuth.setDocIDs(docIDs);
        docIDsAndAuth.setDocAuth(docAuth);
        return docIDsAndAuth;
    }

    public void deleteUserAuth(String docID, String uid) throws SQLException {
        String sql = "  Delete From  UserAuth where ";
        if (docID != null) {
            sql += "  DocID = ? ";
        } else if (uid != null) {
            sql += "   [Uid] = ? ";
        }

        PreparedStatement pstmt = conn.prepareStatement(sql);
        if (docID != null) {
            pstmt.setString(1, docID);
        } else if (uid != null) {
            pstmt.setString(1, uid);
        }
        pstmt.executeUpdate();
    }

}