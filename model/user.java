package Sai.Expense;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class user {
	String user_id;
	String user_name, user_email, role;
	public String getUser_id() {
		return user_id;
	}
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
	public void setUser_id(String string) {
		this.user_id = string;
	}
	public String getUser_name() {
		return user_name;
	}
	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}
	public String getUser_email() {
		return user_email;
	}
	public void setUser_email(String user_email) {
		this.user_email = user_email;
	}
}