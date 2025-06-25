package bean;

import java.io.Serializable;

public class Class_num implements Serializable {
	private String school_cd;
	private String class_num;

	public String getSchool_Cd() {
		return school_cd;
	}
	public void setSchool_Cd(String school_cd) {
		this.school_cd = school_cd;
	}
	public String getClass_Num() {
		return class_num;
	}
	public void setClass_Num(String class_num) {
		this.class_num = class_num;
	}
}