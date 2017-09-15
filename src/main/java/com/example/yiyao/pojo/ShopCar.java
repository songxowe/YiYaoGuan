package com.example.yiyao.pojo;

import java.io.Serializable;

public class ShopCar  implements Serializable {
	//购物车编号id
	private int s_id;
	//用户id
	private int u_id;
	//商品id
	private int g_id;
	//商品显示的Url
	private String g_image_url;
	//商品的名称
	private String g_name;
	//商品的价格
	private float g_price=0f;
	//商品折扣
	private float g_discount=1f;
	//商品数量
	private int shopNumber;
	//商店名称
	private String shopName;

	//构造方法
	public ShopCar(){

	}

	public ShopCar(int s_id, int u_id, int g_id, String g_image_url, String g_name, float g_price, float g_discount, int shopNumber, String shopName) {
		this.s_id = s_id;
		this.u_id = u_id;
		this.g_id = g_id;
		this.g_image_url = g_image_url;
		this.g_name = g_name;
		this.g_price = g_price;
		this.g_discount = g_discount;
		this.shopNumber = shopNumber;
		this.shopName = shopName;
	}

	//set,get方法
	public int getS_id() {
		return s_id;
	}
	public void setS_id(int s_id) {
		this.s_id = s_id;
	}
	public int getU_id() {
		return u_id;
	}
	public void setU_id(int u_id) {
		this.u_id = u_id;
	}
	public int getG_id() {
		return g_id;
	}
	public void setG_id(int g_id) {
		this.g_id = g_id;
	}
	public String getG_image_url() {
		return g_image_url;
	}
	public void setG_image_url(String g_image_url) {
		this.g_image_url = g_image_url;
	}
	public String getG_name() {
		return g_name;
	}
	public void setG_name(String g_name) {
		this.g_name = g_name;
	}
	public float getG_price() {
		return g_price;
	}
	public void setG_price(float g_price) {
		this.g_price = g_price;
	}
	public float getG_discount() {
		return g_discount;
	}
	public void setG_discount(float g_discount) {
		this.g_discount = g_discount;
	}

	public int getShopNumber() {
		return shopNumber;
	}

	public void setShopNumber(int shopNumber) {
		this.shopNumber = shopNumber;
	}

	public String getShopName() {
		return shopName;
	}

	public void setShopName(String shopName) {
		this.shopName = shopName;
	}

	@Override
	public String toString() {
		return "ShopCar{" +
				"s_id=" + s_id +
				", u_id=" + u_id +
				", g_id=" + g_id +
				", g_image_url='" + g_image_url + '\'' +
				", g_name='" + g_name + '\'' +
				", g_price=" + g_price +
				", g_discount=" + g_discount +
				'}';
	}
}
