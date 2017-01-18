package esunbank.esundoc.entity;

public class SysUser {

    private String AUTH;

    private String Uid;

    private String isAction;

    private String Sysid;

    private String UserNo; // 使用者員工編號

    private String UserName; // 使用者名稱

    private java.util.ArrayList<String> DocIDs; // 有權限的文件版本編號

    private java.util.ArrayList<String> DocAuth; // 文件的權限 後面為 R 或 M

    // 都 order 過了 應該不用排序

    public java.util.ArrayList<String> getDocIDs() {
        return DocIDs;
    }

    public java.util.ArrayList<String> getDocAuth() {
        return DocAuth;
    }

    public void setDocAuth(java.util.ArrayList<String> docAuth) {
        DocAuth = docAuth;
    }

    public void setDocIDs(java.util.ArrayList<String> docIDs) {
        DocIDs = docIDs;
    }

    public String getUserName() {
        return UserName;
    }

    public void setUserName(String userName) {
        UserName = userName;
    }

    public String getAUTH() {
        return AUTH;
    }

    public void setAUTH(String aUTH) {
        AUTH = aUTH;
    }

    public String getUid() {
        return Uid;
    }

    public void setUid(String uid) {
        Uid = uid;
    }

    public String getIsAction() {
        return isAction;
    }

    public void setIsAction(String isAction) {
        this.isAction = isAction;
    }

    public String getUserNo() {
        return UserNo;
    }

    public void setUserNo(String userNo) {
        UserNo = userNo;
    }

    public String getSysid() {
        return Sysid;
    }

    public void setSysid(String sysid) {
        Sysid = sysid;
    }
}
