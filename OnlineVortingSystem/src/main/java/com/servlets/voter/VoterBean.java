package com.servlets.voter;

public class VoterBean {
	
	private String voterName;
	private String voterId;
	private String voterMob;
	private String regDateTime;
	private String gender;
	private String email;
	private String password;
	private String passwordCon;
	private String otp;
	
	//Gets/Sets method 
	
	public String getOtp() {
		return otp;
	}
	public void setOtp(String otp) {
		this.otp = otp;
	}
	public String getVoterName() {
		return voterName;
	}
	public void setVoterName(String voterName) {
		this.voterName = voterName;
	}
	public String getVoterId() {
		return voterId;
	}
	public void setVoterId(String voterId) {
		this.voterId = voterId;
	}
	public String getVoterMob() {
		return voterMob;
	}
	public void setVoterMob(String voterMob) {
		this.voterMob = voterMob;
	}
	public String getRegDateTime() {
		return regDateTime;
	}
	public void setRegDateTime(String regDateTime) {
		this.regDateTime = regDateTime;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getPasswordCon() {
		return passwordCon;
	}
	public void setPasswordCon(String passwordCon) {
		this.passwordCon = passwordCon;
	}
	
}
