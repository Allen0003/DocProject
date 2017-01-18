package entity;

public class DocDtl {

	private String DocID;

	private String DocSN;

	private String memo;

	private String FileName;

	private byte[] File_byte;

	private long FileSize;

	private String Sysid;

	private String Sysdt;

	private String docName;

	private String InHistoryZone;

	public String getInHistoryZone() {
		return InHistoryZone;
	}

	public void setInHistoryZone(String inHistoryZone) {
		InHistoryZone = inHistoryZone;
	}

	public String getDocName() {
		return docName;
	}

	public void setDocName(String docName) {
		this.docName = docName;
	}

	public String getDocID() {
		return DocID;
	}

	public void setDocID(String docID) {
		DocID = docID;
	}

	public String getDocSN() {
		return DocSN;
	}

	public void setDocSN(String docSN) {
		DocSN = docSN;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	public String getFileName() {
		return FileName;
	}

	public void setFileName(String fileName) {
		FileName = fileName;
	}

	public byte[] getFile_byte() {
		return File_byte;
	}

	public void setFile_byte(byte[] file_byte) {
		File_byte = file_byte;
	}

	public long getFileSize() {
		return FileSize;
	}

	public void setFileSize(long fileSize) {
		FileSize = fileSize;
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

}
