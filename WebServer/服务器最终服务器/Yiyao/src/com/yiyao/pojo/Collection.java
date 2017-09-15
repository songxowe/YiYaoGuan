package com.yiyao.pojo;

public class Collection {
		//购物车编号id
		private int c_id;
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
		
		//构造方法
		public Collection(){
			
		}
		public Collection(int c_id, int u_id, int g_id, String g_image_url, String g_name, float g_price, float g_discount) {
			super();
			this.c_id = c_id;
			this.u_id = u_id;
			this.g_id = g_id;
			this.g_image_url = g_image_url;
			this.g_name = g_name;
			this.g_price = g_price;
			this.g_discount = g_discount;
		}
		
		//set,get方法
		public int getC_id() {
			return c_id;
		}
		public void setC_id(int c_id) {
			this.c_id = c_id;
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
		
}
