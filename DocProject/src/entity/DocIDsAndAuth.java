package esunbank.esundoc.entity;

public class DocIDsAndAuth {
    private java.util.ArrayList<String> DocIDs;

    private java.util.ArrayList<String> DocAuth;

    public java.util.ArrayList<String> getDocAuth() {
        return DocAuth;
    }

    public void setDocAuth(java.util.ArrayList<String> docAuth) {
        DocAuth = docAuth;
    }

    public java.util.ArrayList<String> getDocIDs() {
        return DocIDs;
    }

    public void setDocIDs(java.util.ArrayList<String> docIDs) {
        DocIDs = docIDs;
    }
}