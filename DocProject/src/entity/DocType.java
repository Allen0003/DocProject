//package esunbank.esundoc.entity;
package entity;

public class DocType {

	private int TypeID;

	private String TypeName;

	private String Sysid;

	public String getSysid() {
		return Sysid;
	}

	public void setSysid(String sysid) {
		Sysid = sysid;
	}

	public int getTypeID() {
		return TypeID;
	}

	public void setTypeID(int typeID) {
		TypeID = typeID;
	}

	public String getTypeName() {
		return TypeName;
	}

	public void setTypeName(String typeName) {
		TypeName = typeName;
	}

}
