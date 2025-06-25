package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import bean.Teacher;

public class LoginDAO2 extends Dao{
	public Teacher search(String id, String password)
	throws Exception{
		Teacher Login2=null;

		Connection con=getConnection();

		PreparedStatement st;
		st=con.prepareStatement("select * from teacher where id=? and password=?");
		st.setString(1, id);
		st.setString(2, password);
		ResultSet rs=st.executeQuery();

		while (rs.next()){
			Login2=new Teacher();
			Login2.setId(rs.getString("id"));
			Login2.setName(rs.getString("name"));
			Login2.setPassword(rs.getString("password"));
			Login2.setSchool_cd(rs.getString("school_cd"));
		}
		st.close();
		con.close();
		return Login2;
	}
}