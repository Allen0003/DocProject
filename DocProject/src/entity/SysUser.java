package esunbank.esundoc.entity;

public class SysUser {

    private String AUTH;

    private String Uid;

    private String isAction;

    private String Sysid;

    private String UserNo; // �ϥΪ̭��u�s��

    private String UserName; // �ϥΪ̦W��

    private java.util.ArrayList<String> DocIDs; // ���v������󪩥��s��

    private java.util.ArrayList<String> DocAuth; // ����v�� �᭱�� R �� M

    // �� order �L�F ���Ӥ��αƧ�

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
