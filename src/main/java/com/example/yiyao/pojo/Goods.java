package com.example.yiyao.pojo;

import java.io.Serializable;

public class Goods  implements Serializable {
	//1商品id
	private int g_id;
	//2显示图片url
	private String g_show_image_url;
	//3商品名
	private String g_name;
	//4商品价格
	private float g_price=0;
	//5商品折扣比例
	private float g_discount=1f;
	//6商品图文信息描述url
	private String g_imagetxt_url;
	//7对应症状(感冒等)
	private String g_cure_symptom;
	//8分类一(大类别)
	private String g_classify_one;
	//9分类二(小类别)
	private String g_classify_two;
	//10适用人群
	private String g_suit_people;
	//11品牌
	private String g_trademark;
	//12生产厂家
	private String g_product_company;
	//13每日促销
	private String g_special_sell_day;
	//14好评数
	private int g_good_evaluate;
	//15中评数
	private int g_normal_evaluate;
	//16差评数
	private int g_bad_evaluate;
	//17 按关键字查询的关键字
	private String condition;
	//18 商品的imageurl详细
	private String g_image_url;
	
	
	//构造方法
	public Goods() {
		super();
	}
	
	//set get方法
	
	public int getG_id() {
		return g_id;
	}
	public String getG_image_url() {
		return g_image_url;
	}

	public void setG_image_url(String g_image_url) {
		this.g_image_url = g_image_url;
	}

	public String getCondition() {
		return condition;
	}


	public void setCondition(String condition) {
		this.condition = condition;
	}


	public void setG_id(int g_id) {
		this.g_id = g_id;
	}
	public String getG_show_image_url() {
		return g_show_image_url;
	}
	public void setG_show_image_url(String g_show_image_url) {
		this.g_show_image_url = g_show_image_url;
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
	public String getG_imagetxt_url() {
		return g_imagetxt_url;
	}
	public void setG_imagetxt_url(String g_imagetxt_url) {
		this.g_imagetxt_url = g_imagetxt_url;
	}
	public String getG_cure_symptom() {
		return g_cure_symptom;
	}
	public void setG_cure_symptom(String g_cure_symptom) {
		this.g_cure_symptom = g_cure_symptom;
	}
	public String getG_classify_one() {
		return g_classify_one;
	}
	public void setG_classify_one(String g_classify_one) {
		this.g_classify_one = g_classify_one;
	}
	public String getG_classify_two() {
		return g_classify_two;
	}
	public void setG_classify_two(String g_classify_two) {
		this.g_classify_two = g_classify_two;
	}
	public String getG_suit_people() {
		return g_suit_people;
	}
	public void setG_suit_people(String g_suit_people) {
		this.g_suit_people = g_suit_people;
	}
	public String getG_trademark() {
		return g_trademark;
	}
	public void setG_trademark(String g_trademark) {
		this.g_trademark = g_trademark;
	}
	public String getG_product_company() {
		return g_product_company;
	}
	public void setG_product_company(String g_product_company) {
		this.g_product_company = g_product_company;
	}
	public String getG_special_sell_day() {
		return g_special_sell_day;
	}
	public void setG_special_sell_day(String g_special_sell_day) {
		this.g_special_sell_day = g_special_sell_day;
	}
	public int getG_good_evaluate() {
		return g_good_evaluate;
	}
	public void setG_good_evaluate(int g_good_evaluate) {
		this.g_good_evaluate = g_good_evaluate;
	}
	public int getG_normal_evaluate() {
		return g_normal_evaluate;
	}
	public void setG_normal_evaluate(int g_normal_evaluate) {
		this.g_normal_evaluate = g_normal_evaluate;
	}
	public int getG_bad_evaluate() {
		return g_bad_evaluate;
	}
	public void setG_bad_evaluate(int g_bad_evaluate) {
		this.g_bad_evaluate = g_bad_evaluate;
	}
}
