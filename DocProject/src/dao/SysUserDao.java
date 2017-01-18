package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;

import entity.SysUser;
import util.Const;

public class SysUserDao {
	Connection conn;

	public SysUserDao(Connection conn) { // new 起來時送連線過來
		this.conn = conn;
	}

	public ArrayList<SysUser> getSysUser(String auth) throws SQLException {
		String sql = "Select * From  SysUser ";
		if (auth != null) {
			sql += "where AUTH = ? ";
		}
		PreparedStatement pstmt;
		pstmt = conn.prepareStatement(sql);
		if (auth != null) {
			pstmt.setString(1, auth);
		}
		return getSysUsers(pstmt);
	}

	private ArrayList<SysUser> getSysUsers(PreparedStatement pstmt) throws SQLException {
		SysUser sysUser = null;
		ArrayList<SysUser> sysUsers = new ArrayList<SysUser>();
		ResultSet rs = pstmt.executeQuery();
		while (rs.next()) {
			sysUser = new SysUser();
			sysUser.setAUTH(rs.getString("AUTH"));
			sysUser.setIsAction(rs.getString("isAction"));
			sysUser.setUid(rs.getString("Uid"));
			sysUsers.add(sysUser);
		}
		return sysUsers;
	}

	public ArrayList<String> getSendList(String docID) throws SQLException {
		String sql = "select SysUser.[Uid] As SecdList , *   from SysUser  left join  UserAuth on SysUser.[Uid] = UserAuth.[Uid] where (SysUser.AUTH = ?  or UserAuth.DocID = ? )  and isAction = ?  ";
		PreparedStatement pstmt = conn.prepareStatement(sql);
		pstmt.setString(1, Const.Manager);
		pstmt.setString(2, docID);
		pstmt.setString(3, "Y");
		ArrayList<String> sendList = new ArrayList<String>();
		ResultSet rs = pstmt.executeQuery();
		while (rs.next()) {
			sendList.add(rs.getString("SecdList"));
		}
		return sendList;
	}

	public void uptSysUser(SysUser sysUser) throws SQLException {
		// 更新使用者的是否啟用 狀態
		String sql = "Update  SysUser" + "  Set   isAction = ? , Sysid = ? , Sysdt = ?  where  [Uid] = ?  ";
		PreparedStatement pstmt = conn.prepareStatement(sql);
		pstmt.setString(1, sysUser.getIsAction()); // isAction
		pstmt.setString(2, sysUser.getUserName()); // Sysid
		pstmt.setTimestamp(3, new Timestamp(System.currentTimeMillis())); // Sysdt
		pstmt.setString(4, sysUser.getUid()); // Uid
		pstmt.executeUpdate();
	}

	public void addSysUser(SysUser sysUser) throws SQLException {
		// 增加使用者
		String sql = "insert into SysUser  ( [Uid], [AUTH] , [isAction] , Sysid  ,Sysdt )" + " values (?,?,?,?,?)";
		PreparedStatement pstmt = conn.prepareStatement(sql);
		pstmt.setString(1, sysUser.getUid());
		pstmt.setString(2, sysUser.getAUTH());
		pstmt.setString(3, sysUser.getIsAction());
		pstmt.setString(4, sysUser.getSysid());
		pstmt.setTimestamp(5, new Timestamp(System.currentTimeMillis()));
		pstmt.executeUpdate();
	}
}