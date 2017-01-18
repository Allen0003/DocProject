package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;

import entity.DocDtl;
import util.Const;

public class DocDtlDao {
	Connection conn;

	public DocDtlDao(Connection conn) { // new 起來時送連線過來
		this.conn = conn;
	}

	public void addDocDtl(DocDtl docDtl) throws SQLException {
		String sql = "insert into DocDtl  (DocID, DocSN, memo , [FileName] , [File] , Sysid , Sysdt , FileSize , InHistoryZone ) "
				+ " values (?,?,?,?,?,?,?,?,?)";
		PreparedStatement pstmt = conn.prepareStatement(sql);
		pstmt.setString(1, docDtl.getDocID());
		pstmt.setString(2, Const.getKey(Const.DocID_Type_Dtl));// 取不重複亂數
		pstmt.setString(3, docDtl.getMemo());
		pstmt.setString(4, docDtl.getFileName());
		pstmt.setBytes(5, docDtl.getFile_byte());
		pstmt.setString(6, docDtl.getSysid());
		pstmt.setTimestamp(7, new Timestamp(System.currentTimeMillis()));
		pstmt.setLong(8, docDtl.getFileSize());
		pstmt.setString(9, "N"); // default 設定不在歷史區
		pstmt.executeUpdate();
	}

	// get 一群 DocDtl
	private ArrayList<DocDtl> getDocDtls(PreparedStatement pstmt) throws SQLException {
		ArrayList<DocDtl> docDtls = new ArrayList<DocDtl>();
		ResultSet rs = pstmt.executeQuery();
		DocDtl docDtl = null;
		while (rs.next()) {
			docDtl = new DocDtl();
			docDtl.setDocID(rs.getString("DocID"));
			docDtl.setDocSN(rs.getString("DocSN"));
			docDtl.setMemo(rs.getString("memo"));
			docDtl.setFileName(rs.getString("FileName"));
			docDtl.setSysid(rs.getString("Sysid"));
			docDtl.setSysdt(rs.getString("Sysdt"));
			docDtl.setFileSize(rs.getLong("FileSize"));
			docDtl.setFile_byte(rs.getBytes("File"));
			docDtl.setInHistoryZone(rs.getString("InHistoryZone"));
			docDtls.add(docDtl);
		}
		return docDtls;
	}

	private DocDtl getDocDtl(PreparedStatement pstmt) throws SQLException {
		ResultSet rs = pstmt.executeQuery();
		DocDtl docDtl = null;
		rs.next();
		docDtl = new DocDtl();
		docDtl.setDocID(rs.getString("DocID"));
		docDtl.setDocSN(rs.getString("DocSN"));
		docDtl.setMemo(rs.getString("memo"));
		docDtl.setFileName(rs.getString("FileName"));
		docDtl.setSysid(rs.getString("Sysid"));
		docDtl.setSysdt(rs.getString("Sysdt"));
		docDtl.setFileSize(rs.getLong("FileSize"));
		docDtl.setFile_byte(rs.getBytes("File"));

		return docDtl;
	}

	public ArrayList<DocDtl> getDocDtl(String docID) throws SQLException {
		// getDocDtl(docID, null);
		// PreparedStatement pstmt = conn
		// .prepareStatement("Select * From DocDtl where DocID = ? order by
		// [FileName]desc ,Sysdt desc");
		// pstmt.setString(1, docID);
		return getDocDtl(docID, Const.DocID_For_all);
	}

	// 用在印query/dtl 的畫面上
	public ArrayList<DocDtl> getDocDtl(String docID, int hisZone) throws SQLException {
		String sql = "";
		if (hisZone == Const.DocID_For_all) {
			sql = "Select * From  DocDtl  where DocID = ?   order by  [FileName]desc  ,Sysdt desc";
		} else {
			sql = "Select * From  DocDtl  where DocID = ?  and  InHistoryZone = ?  order by  [FileName]desc  ,Sysdt desc";
		}

		PreparedStatement pstmt = conn.prepareStatement(sql);

		pstmt.setString(1, docID);

		if (hisZone == Const.DocID_For_In_Hiszone) { // 在歷史區
			pstmt.setString(2, "Y");
		} else if (hisZone == Const.DocID_For_Not_In_Hiszone) {
			pstmt.setString(2, "N");
		}

		return getDocDtls(pstmt);
	}

	public DocDtl getDocDtl(String DocID, String DocSN) throws SQLException {
		String sql = "Select * From  DocDtl   Where DocID = ?  and  DocSN = ?  ";
		PreparedStatement pstmt = conn.prepareStatement(sql);
		pstmt.setString(1, DocID);
		pstmt.setString(2, DocSN);
		return getDocDtl(pstmt);
	}

	public void updDocCtl(String docID, String userID) throws SQLException {
		String sql1 = " Update  DocCtl  Set   Sysid = ? , Sysdt = ?  where DocID = ? ";
		PreparedStatement pstmt = conn.prepareStatement(sql1);
		pstmt.setString(1, userID); // Sysid
		pstmt.setTimestamp(2, new Timestamp(System.currentTimeMillis())); // Sysdt
		pstmt.setString(3, docID);
		pstmt.executeUpdate();
	}

	public void addDocDtlHis(String docID, String userID, String batchNum, String docSN) throws SQLException {
		// 先把要刪掉的資訊存取起來
		String sql1 = "Select * from [DocDtl]  Where DocID = ?  ";
		if (docSN != null) {
			sql1 += " and DocSN = ? ";
		}
		PreparedStatement pstmt1 = conn.prepareStatement(sql1);
		pstmt1.setString(1, docID);
		if (docSN != null) {
			pstmt1.setString(2, docSN);
		}

		String sql = "  Insert into DocDtlHis (BatchNum , DocID , DocSN "
				+ " , memo , FileName , [File] , FileSize , SysidOrg , SysdtOrg , Sysid  ,Sysdt) "
				+ "  values (?,?,?,?,?,?,?,?,?,?,?)  ";
		PreparedStatement pstmt = conn.prepareStatement(sql);
		ArrayList<DocDtl> docDtls = new ArrayList<DocDtl>();
		docDtls = getDocDtls(pstmt1);

		for (int i = 0; i < docDtls.size(); i++) {
			pstmt.setString(1, batchNum);
			pstmt.setString(2, docDtls.get(i).getDocID());
			pstmt.setString(3, docDtls.get(i).getDocSN());
			pstmt.setString(4, docDtls.get(i).getMemo());
			pstmt.setString(5, docDtls.get(i).getFileName());
			pstmt.setBytes(6, docDtls.get(i).getFile_byte());
			pstmt.setLong(7, docDtls.get(i).getFileSize());
			pstmt.setString(8, docDtls.get(i).getSysid());
			pstmt.setTimestamp(9, Timestamp.valueOf(docDtls.get(i).getSysdt()));
			pstmt.setString(10, userID);
			pstmt.setTimestamp(11, new Timestamp(System.currentTimeMillis()));
			pstmt.addBatch();
		}
		pstmt.executeBatch();
	}

	public void delectDocDtls(String docID) throws SQLException {
		String sql2 = " Delete From  DocDtl  Where DocID = ?  ";
		PreparedStatement pstmt2 = conn.prepareStatement(sql2);
		pstmt2.setString(1, docID);
		pstmt2.executeUpdate();
	}

	public void delectDocDtl(String docID, String docSN) throws SQLException {
		String sql2 = " Delete From  DocDtl  Where DocID = ? and DocSN =  ? ";
		PreparedStatement pstmt2 = conn.prepareStatement(sql2);
		pstmt2.setString(1, docID);
		pstmt2.setString(2, docSN);
		pstmt2.executeUpdate();
	}

	public void updHistoryZone(String docID, String docSN, String userID, String inHistoryZone) throws SQLException {
		String sql1 = "Update  DocDtl  Set   InHistoryZone = ?  where DocID = ?  and DocSN = ?";
		PreparedStatement pstmt1 = conn.prepareStatement(sql1);
		pstmt1.setString(1, inHistoryZone);
		pstmt1.setString(2, docID);
		pstmt1.setString(3, docSN);
		pstmt1.executeUpdate();
	}
}
