package pojos;

import java.io.Serializable;

public class User implements Serializable {

	private static final long serialVersionUID = 1L;

	private Integer user_id;
	private String user_name;
	private String password;
	private String email;

	public User() {
		super();
	}

	public User(String user_name, String email, String password) {
		this.user_name = user_name;
		this.password = password;
		this.email = email;
	}

	public String getUserName() {
		return user_name;
	}

	public void setUserName(String user_name) {
		this.user_name = user_name;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Integer getUserId() {
		return user_id;
	}

	public void setUserId(Integer user_id) {
		this.user_id = user_id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((user_id == null) ? 0 : user_id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		User other = (User) obj;
		if (user_id == null) {
			if (other.user_id != null)
				return false;
		} else if (!user_id.equals(other.user_id))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "User [user_id=" + user_id + ", user_name=" + user_name + ", password=" + password + ", email=" + email
				+ "]";
	}

}
