package esunbank.esundoc.entity;

public class DocCtl {

    private String DocID;

    private String DocName;

    private int DocType;

    private String memo;

    private String Sysid;

    private String Sysdt;

    private String DocAuth; // 為了判斷使用者有沒有權限寄信

    private DocIDsAndAuth DocIDsAndAuth;

    public DocIDsAndAuth getDocIDsAndAuth() {
        return DocIDsAndAuth;
    }

    public void setDocIDsAndAuth(DocIDsAndAuth docIDsAndAuth) {
        DocIDsAndAuth = docIDsAndAuth;
    }

    public String getDocID() {
        return DocID;
    }

    public void setDocID(String docID) {
        DocID = docID;
    }

    public String getDocName() {
        return DocName;
    }

    public void setDocName(String docName) {
        DocName = docName;
    }

    public int getDocType() {
        return DocType;
    }

    public void setDocType(int docType) {
        DocType = docType;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public String getSysid() {
        return Sysid;
    }

    public void setSysid(String sysid) {
        Sysid = sysid;
    }

    public String getSysdt() {
        return Sysdt;
    }

    public void setSysdt(String sysdt) {
        Sysdt = sysdt;
    }

    public String getDocAuth() {
        return DocAuth;
    }

    public void setDocAuth(String docAuth) {
        DocAuth = docAuth;
    }

}
