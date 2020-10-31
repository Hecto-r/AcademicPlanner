package com.seniorproject.payload.response;

public class JwtResponse {
	private String token;
	private String type = "Bearer";
	private int id;
	private String username;
	private String email;
	private String firstName;
	private String lastName;

	public JwtResponse(String accessToken, int id, String username, String email, String firstName, String lastName) {
		this.token = accessToken;
		this.id = id;
		this.username = username;
		this.email = email;
		this.firstName = firstName;
		this.lastName = lastName;
	}

	public String getAccessToken() {
		return token;
	}

	public void setAccessToken(String accessToken) {
		this.token = accessToken;
	}

	public String getTokenType() {
		return type;
	}

	public void setTokenType(String tokenType) {
		this.type = tokenType;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getFirstName(){return firstName;}
	public String getLastName(){return lastName;}

	public void setFirstName(String firstName){this.firstName = firstName;}
	public void setLastName(String lastName){this.firstName = lastName;}

}
