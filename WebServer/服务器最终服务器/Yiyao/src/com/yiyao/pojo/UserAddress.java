package com.yiyao.pojo;

public class UserAddress {
	//收货地址id
	private int a_id;
	//属于谁(用户id)的收货地址
	private int u_id;
	//收货人 姓名
	private String a_receive_user_name;
	//收货人 地址
	private String a_receive_user_address;
	//收货人 电话
	private String a_receive_user_phone;
	
	//构造方法	
	public UserAddress(){
	
	}
	
	public UserAddress(int a_id, int u_id, String a_receive_user_name, String a_receive_user_address,
			String a_receive_user_phone) {
		super();
		this.a_id = a_id;
		this.u_id = u_id;
		this.a_receive_user_name = a_receive_user_name;
		this.a_receive_user_address = a_receive_user_address;
		this.a_receive_user_phone = a_receive_user_phone;
	}
	
	//set get 构造方法
	public int getA_id() {
		return a_id;
	}

	public void setA_id(int a_id) {
		this.a_id = a_id;
	}

	public int getU_id() {
		return u_id;
	}

	public void setU_id(int u_id) {
		this.u_id = u_id;
	}

	public String getA_receive_user_name() {
		return a_receive_user_name;
	}

	public void setA_receive_user_name(String a_receive_user_name) {
		this.a_receive_user_name = a_receive_user_name;
	}

	public String getA_receive_user_address() {
		return a_receive_user_address;
	}

	public void setA_receive_user_address(String a_receive_user_address) {
		this.a_receive_user_address = a_receive_user_address;
	}

	public String getA_receive_user_phone() {
		return a_receive_user_phone;
	}

	public void setA_receive_user_phone(String a_receive_user_phone) {
		this.a_receive_user_phone = a_receive_user_phone;
	}
	
	
}
