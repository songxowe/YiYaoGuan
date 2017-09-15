package com.example.yiyao.pojo;

import java.io.Serializable;

public class Orderform implements Serializable{
	//1   订单编号
	private int o_id;
	//2   用户编号
	private int u_id;
	//3  商品id和
	private String g_ids;
	//4  订单日期
	private String o_date;
	//5  订单状态(待支付 1，待收货 2，待评价 3，完成 4)
	private int o_state=1;
	//6  商品金额
	private float o_goods_value=0f;
	//7  运费金额
	private float o_transport_value=0f;
	//8  总金额
	private float o_total_value=0f;
	//9  收货人
	private String o_receive_people;
	//10 收货地址
	private String o_receive_address;
	//11  收货人电话
	private String o_receive_phone;
	//12 列表中显示的第一个商品的显示图片
	private String show_image_url;
	//13 订单中 商品json字符串
	private String orderfrom_good;
	
	//构造方法
	public Orderform() {
		super();
	}
	
	//set、get方法
	public int getO_id() {
		return o_id;
	}
	public String getOrderfrom_good() {
		return orderfrom_good;
	}

	public void setOrderfrom_good(String orderfrom_good) {
		this.orderfrom_good = orderfrom_good;
	}

	public String getShow_image_url() {
		return show_image_url;
	}

	public void setShow_image_url(String show_image_url) {
		this.show_image_url = show_image_url;
	}

	public void setO_id(int o_id) {
		this.o_id = o_id;
	}
	public int getU_id() {
		return u_id;
	}
	public void setU_id(int u_id) {
		this.u_id = u_id;
	}
	public String getG_ids() {
		return g_ids;
	}
	public void setG_ids(String g_ids) {
		this.g_ids = g_ids;
	}
	public String getO_date() {
		return o_date;
	}
	public void setO_date(String o_date) {
		this.o_date = o_date;
	}
	public int getO_state() {
		return o_state;
	}
	public void setO_state(int o_state) {
		this.o_state = o_state;
	}
	public float getO_goods_value() {
		return o_goods_value;
	}
	public void setO_goods_value(float o_goods_value) {
		this.o_goods_value = o_goods_value;
	}
	public float getO_transport_value() {
		return o_transport_value;
	}
	public void setO_transport_value(float o_transport_value) {
		this.o_transport_value = o_transport_value;
	}
	public float getO_total_value() {
		return o_total_value;
	}
	public void setO_total_value(float o_total_value) {
		this.o_total_value = o_total_value;
	}
	public String getO_receive_people() {
		return o_receive_people;
	}
	public void setO_receive_people(String o_receive_people) {
		this.o_receive_people = o_receive_people;
	}
	public String getO_receive_address() {
		return o_receive_address;
	}
	public void setO_receive_address(String o_receive_address) {
		this.o_receive_address = o_receive_address;
	}
	public String getO_receive_phone() {
		return o_receive_phone;
	}
	public void setO_receive_phone(String o_receive_phone) {
		this.o_receive_phone = o_receive_phone;
	}
}
