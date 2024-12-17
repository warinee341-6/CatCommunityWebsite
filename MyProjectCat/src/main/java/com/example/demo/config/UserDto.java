package com.example.demo.config;

public class UserDto {

	private String username;
	private String password;
	private String email;
	private String userPic;

	public UserDto(String username, String password, String email, String userPic) {
		super();
		this.username = username;
		this.password = password;
		this.email = email;
		this.userPic = userPic;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getUserPic() {
		return userPic;
	}

	public void setUserPic(String userPic) {
		this.userPic = userPic;
	}

	@Override
	public String toString() {
		return "UserDto username=" + username + ", password=" + password + ", email=" + email
				+ ", userPic=" + userPic + "]";
	}


}