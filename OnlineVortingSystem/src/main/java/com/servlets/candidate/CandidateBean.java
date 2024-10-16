package com.servlets.candidate;

import java.sql.PreparedStatement;

import jakarta.servlet.annotation.MultipartConfig;
@MultipartConfig
public class CandidateBean {
	
	private String name;
	private String id;
	private String mobNo;
	private String regDateTime;
	private String gender;
	private String email;
	private String password;
	private String passwordCon;
	private String otp;
	private String imagePath;
	private PreparedStatement preparedImage;	
	
	public PreparedStatement getPreparedImage() {
		return preparedImage;
	}
	public void setPreparedImage(PreparedStatement preparedImage) {
		this.preparedImage = preparedImage;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getMobNo() {
		return mobNo;
	}
	public void setMobNo(String mobNo) {
		this.mobNo = mobNo;
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
	public String getOtp() {
		return otp;
	}
	public void setOtp(String otp) {
		this.otp = otp;
	}
	public String getImagePath() {
		return imagePath;
	}
	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
	}
}
