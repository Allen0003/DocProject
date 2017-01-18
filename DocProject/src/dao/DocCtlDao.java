package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;

import entity.DocCtl;

public class DocCtlDao {
	Connection conn;

	Calendar calendar = null;

	public DocCtlDao(Connection conn) { // new 起來時送連線過來
		this.conn = conn;
	}

	public String getDocName(String docID) throws SQLException {
		String sql1 = "Select *  from DocCtl  Where DocID = ?  ";
		PreparedStatement pstmt;
		pstmt = conn.prepareStatement(sql1);
		pstmt.setString(1, docID);
		ResultSet rs1 = pstmt.executeQuery();
		rs1.next();
		return rs1.getString("DocName");
	}

	public ArrayList<DocCtl> getAllDocCtl() throws SQLException {
		PreparedStatement pstmt = conn.prepareStatement("Select * from DocCtl order by DocType, Sysdt DESC");
		return getDocCtls(pstmt);
	}

	public ArrayList<DocCtl> getDocCtlByID(String[] docID) throws SQLException {
		String sql1 = "Select * from DocCtl Where DocID IN (%s) order by DocType, Sysdt DESC ";
		String sql = String.format(sql1, preparePlaceHolders(docID.length));
		PreparedStatement pstmt = conn.prepareStatement(sql);
		setValues(pstmt, docID); // 一次設定多筆
		return getDocCtls(pstmt);
	}

	public ArrayList<DocCtl> getDocCtlByType(String type) throws SQLException {

		PreparedStatement pstmt = conn.prepareStatement("Select * from DocCtl Where DocType = ? order by Sysdt DESC");
		pstmt.setString(1, type);
		return getDocCtls(pstmt);
	}

	public ArrayList<DocCtl> getDocCtlByType(String type, String uid) throws SQLException {

		PreparedStatement pstmt = conn
				.prepareStatement("Select * from DocCtl" + " left join   UserAuth  on DocCtl.DocID = UserAuth.DocID"
						+ "  Where DocType = ? and UserAuth.[Uid] = ? order by Sysdt DESC");
		pstmt.setString(1, type);
		pstmt.setString(2, uid);
		return getDocCtls(pstmt);
	}

	// 這樣子寫把SQL 分開 然後 再去呼叫上面的就好了
	// 取得DocCtl Key 為DocID 並且 order by DocType
	private ArrayList<DocCtl> getDocCtls(PreparedStatement pstmt) throws SQLException {
		ArrayList<DocCtl> docCtls = new ArrayList<DocCtl>();
		ResultSet rs = pstmt.executeQuery();
		DocCtl docCtl = null;
		while (rs.next()) {
			docCtl = new DocCtl();
			docCtl.setDocType(rs.getInt("DocType"));
			docCtl.setDocName(rs.getString("DocName"));
			docCtl.setMemo(rs.getString("memo"));
			docCtl.setSysdt(rs.getString("Sysdt"));
			docCtl.setSysid(rs.getString("Sysid"));
			docCtl.setDocID(rs.getString("DocID"));

			docCtls.add(docCtl);
		}
		return docCtls;
	}

	public void addDocCtl(DocCtl docCtl) throws SQLException {
		calendar = new GregorianCalendar();
		PreparedStatement pstmt = conn.prepareStatement(
				"insert into DocCtl  (DocID,DocName,DocType,memo ,Sysid ,Sysdt )" + " values (?,?,?,?,?,?)");
		pstmt.setString(1, docCtl.getDocID());
		pstmt.setString(2, docCtl.getDocName());
		pstmt.setInt(3, docCtl.getDocType());
		pstmt.setString(4, docCtl.getMemo());
		pstmt.setString(5, docCtl.getSysid());
		pstmt.setTimestamp(6, new Timestamp(System.currentTimeMillis()));
		pstmt.executeUpdate();
	}

	public void deleteDocCtl(String docID) throws SQLException {
		String sql = " Delete  from DocCtl  where DocID = ?  ";
		PreparedStatement pstmt1 = conn.prepareStatement(sql);
		pstmt1.setString(1, docID);
		pstmt1.executeUpdate();
	}

	public void addDocCtlHis(String docID, String userID, String batchNum) throws SQLException {

		// for DocCtlHis table
		String sql1 = "Select * From DocCtl where DocID = ? ";
		PreparedStatement pstmt1 = conn.prepareStatement(sql1);
		pstmt1.setString(1, docID);
		DocCtl docCtl = new DocCtl();
		docCtl = getDocCtls(pstmt1).get(0);

		String sql = "Insert into  DocCtlHis values (?,?,?,?,?,?,?,?,?) ";
		PreparedStatement pstmt = conn.prepareStatement(sql);
		// insert DocCtlHis
		pstmt.setString(1, batchNum);
		pstmt.setString(2, docCtl.getDocID());
		pstmt.setString(3, docCtl.getDocName());
		pstmt.setInt(4, docCtl.getDocType());
		pstmt.setString(5, docCtl.getMemo());
		pstmt.setString(6, docCtl.getSysid());
		pstmt.setTimestamp(7, Timestamp.valueOf(docCtl.getSysdt()));
		pstmt.setString(8, userID);
		pstmt.setTimestamp(9, new Timestamp(System.currentTimeMillis()));
		pstmt.executeUpdate();
	}

	private String preparePlaceHolders(int length) {// 黏問號在後面
		StringBuilder builder = new StringBuilder();
		for (int i = 0; i < length;) {
			builder.append("?");
			if (++i < length) {
				builder.append(",");
			}
		}
		return builder.toString();
	}

	private void setValues(PreparedStatement preparedStatement, String[] values) throws SQLException {
		for (int i = 0; i < values.length; i++) {
			preparedStatement.setObject(i + 1, values[i]);
		}
	}

}
