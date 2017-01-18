package esunbank.esundoc.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;

import esunbank.esundoc.entity.DocType;
import esunbank.esundoc.util.Const;

public class DocTypeDao {
    Connection conn;

    public DocTypeDao(Connection conn) { // new 起來時送連線過來
        this.conn = conn;
    }

    public ArrayList<DocType> getDocType() throws SQLException {
        String sql = "select *  from EsunDoc.dbo.DocType  ";
        PreparedStatement pstmt = conn.prepareStatement(sql);
        ResultSet rs = pstmt.executeQuery();
        DocType class_DocType = null;
        ArrayList<DocType> array_DocType = new ArrayList<DocType>();
        while (rs.next()) {
            class_DocType = new DocType();
            class_DocType.setTypeName(rs.getString("TypeName"));
            class_DocType.setTypeID(rs.getInt("TypeID"));
            array_DocType.add(class_DocType);
        }
        return array_DocType;
    }
    
    public void addDocType(DocType  docType) throws SQLException {
        String sql = "insert into DocType  ( TypeName , Sysid  ,Sysdt )"
                + " values (?,?,?)";       
        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setString(1, docType.getTypeName());
        pstmt.setString(2, docType.getSysid());
        pstmt.setTimestamp(3, new Timestamp(System.currentTimeMillis()));
        pstmt.executeUpdate();
        Const.DocType = getDocType();  //把Const  裡面更新 
    }    
}
