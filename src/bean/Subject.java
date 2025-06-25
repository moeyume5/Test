//Subject.java
package bean;

import java.io.Serializable;

public class Subject implements Serializable{
	private String school_cd;
	private String cd;
	private String name;

	public String getSchool_CD(){
		return school_cd;
	}
	public void setSchool_CD(String school_cd){
		this.school_cd = school_cd;
	}

	public String getCd(){
		return cd;
	}
	public void setCd(String cd){
		this.cd = cd;
	}

	public String getName(){
		return name;
	}
	public void setName(String name){
		this.name = name;
	}
}
