package com.yiyao.pojo;

public class Evaluate {
	//评论编号
	private int e_id;
	//用户编号
	private int u_id;
	//用户昵称
	private String u_nickname;
	//用户头像
	private String u_imageurl;
	//商品编号
	private int g_id;
	//评论内容
	private String evaluate_content;
	//评论日期
	private String evaluate_date;
	//评论级别(好评 1；中评 2；差评 3)
	private int evaluate_level=2;
	
	//构造方法
	public Evaluate(){
		
	}
	
	//setget 方法

	public int getE_id() {
		return e_id;
	}

	public String getU_imageurl() {
		return u_imageurl;
	}

	public void setU_imageurl(String u_imageurl) {
		this.u_imageurl = u_imageurl;
	}

	public void setE_id(int e_id) {
		this.e_id = e_id;
	}

	public int getU_id() {
		return u_id;
	}

	public void setU_id(int u_id) {
		this.u_id = u_id;
	}

	public String getU_nickname() {
		return u_nickname;
	}

	public void setU_nickname(String u_nickname) {
		this.u_nickname = u_nickname;
	}

	public int getG_id() {
		return g_id;
	}

	public void setG_id(int g_id) {
		this.g_id = g_id;
	}

	public String getEvaluate_content() {
		return evaluate_content;
	}

	public void setEvaluate_content(String evaluate_content) {
		this.evaluate_content = evaluate_content;
	}

	public String getEvaluate_date() {
		return evaluate_date;
	}

	public void setEvaluate_date(String evaluate_date) {
		this.evaluate_date = evaluate_date;
	}

	public int getEvaluate_level() {
		return evaluate_level;
	}

	public void setEvaluate_level(int evaluate_level) {
		this.evaluate_level = evaluate_level;
	}
	
}
