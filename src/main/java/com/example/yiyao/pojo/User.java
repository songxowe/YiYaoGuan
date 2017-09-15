package com.example.yiyao.pojo;

import java.io.Serializable;

public class User implements Serializable {
	//用户编号
	private int u_id;
	//用户头像url
	private String u_imageurl;
	//用户背景图片url
	private String u_backgroundimageurl;
	//用户昵称
	private String u_nickname;
	//登录密码
	private String u_password;
	//用户姓名(一般真名)
	private String u_username;
	//用户性别
	private String u_sex;
	//用户年龄
	private int u_age = 0;
	//用户地址
	private String u_address;
	//用户手机号码
	private String u_mobile;
	
	//构造方法
	public User(int u_id, String u_imageurl, String u_backgroundimageurl, String u_nickname, String u_password,
			String u_username, String u_sex, int u_age, String u_address, String u_mobile) {
		super();
		this.u_id = u_id;
		this.u_imageurl = u_imageurl;
		this.u_backgroundimageurl = u_backgroundimageurl;
		this.u_nickname = u_nickname;
		this.u_password = u_password;
		this.u_username = u_username;
		this.u_sex = u_sex;
		this.u_age = u_age;
		this.u_address = u_address;
		this.u_mobile = u_mobile;
	}

	public User() {
		super();
	}
	
	//set、get方法
	public int getU_id() {
		return u_id;
	}

	public void setU_id(int u_id) {
		this.u_id = u_id;
	}

	public String getU_imageurl() {
		return u_imageurl;
	}

	public void setU_imageurl(String u_imageurl) {
		this.u_imageurl = u_imageurl;
	}

	public String getU_backgroundimageurl() {
		return u_backgroundimageurl;
	}

	public void setU_backgroundimageurl(String u_backgroundimageurl) {
		this.u_backgroundimageurl = u_backgroundimageurl;
	}

	public String getU_nickname() {
		return u_nickname;
	}

	public void setU_nickname(String u_nickname) {
		this.u_nickname = u_nickname;
	}

	public String getU_password() {
		return u_password;
	}

	public void setU_password(String u_password) {
		this.u_password = u_password;
	}

	public String getU_username() {
		return u_username;
	}

	public void setU_username(String u_username) {
		this.u_username = u_username;
	}

	public String getU_sex() {
		return u_sex;
	}

	public void setU_sex(String u_sex) {
		this.u_sex = u_sex;
	}

	public int getU_age() {
		return u_age;
	}

	public void setU_age(int u_age) {
		this.u_age = u_age;
	}

	public String getU_address() {
		return u_address;
	}

	public void setU_address(String u_address) {
		this.u_address = u_address;
	}

	public String getU_mobile() {
		return u_mobile;
	}

	public void setU_mobile(String u_mobile) {
		this.u_mobile = u_mobile;
	}	
}
