create sequence seq_y_user_id 
       minvalue 1
       maxvalue 99999
       start with 1 
       increment by 1
       nocycle
       nocache
       order;

create table y_user(
       u_id  number(8) primary key,
       u_imageurl  varchar2(200),
       u_backgroundimageurl varchar2(200),
       u_nickname varchar2(30),
       u_password varchar2(200) not null,
       u_username varchar2(30),
       u_sex varchar2(10),
       u_age number(5) default 0,
       u_address varchar2(500),
       u_mobile varchar2(20) not null);




create sequence seq_y_user_address_id 
       minvalue 1
       maxvalue 99999
       start with 1 
       increment by 1
       nocycle
       nocache
       order;    

create table y_user_address(
       a_id  number(8) primary key,
       u_id  number(8) not null,
       a_receive_user_name  varchar2(30) not null,
       a_receive_user_address  varchar2(500) not null,
       a_receive_user_phone  varchar2(20) not null);
       




create sequence seq_y_shopcar_id 
       minvalue 1
       maxvalue 99999
       start with 1 
       increment by 1
       nocycle
       nocache
       order;    

create table y_shopcar(
       s_id  number(8) primary key,
       u_id  number(8) not null,
       g_id  number(8) not null);





create sequence seq_y_collect_id 
       minvalue 1
       maxvalue 99999
       start with 1 
       increment by 1
       nocycle
       nocache
       order;    

create table y_collect(
       c_id  number(8) primary key,
       u_id  number(8) not null,
       g_id  number(8) not null);





create sequence seq_y_goods_id 
       minvalue 1
       maxvalue 99999
       start with 1 
       increment by 1
       nocycle
       nocache
       order;    

create table y_goods(
       g_id  number(8) primary key,
       g_show_image_url  varchar2(200),
       g_name  varchar2(200),
       g_price number(6,2) default 0,
       g_discount number(4,2) default 1,
       g_imagetxt_url varchar2(200),
       g_cure_symptom varchar2(4000),
       g_classify_one varchar2(100),
       g_classify_two varchar2(100),
       g_suit_people varchar2(200),
       g_trademark varchar2(500),
       g_product_company varchar2(500),
       g_special_sell_day varchar2(100),
       g_good_evaluate number(8) default 0,
       g_normal_evaluate number(8) default 0,
       g_bad_evaluate number(8) default 0 );



create table y_goods_image_url(
       g_id  number(8) not null,
       g_image_url varchar2(200));
       
       




create sequence seq_y_evaluate_id 
       minvalue 1
       maxvalue 99999
       start with 1 
       increment by 1
       nocycle
       nocache
       order;    

create table y_evaluate(
       e_id  number(8) primary key,
       g_id  number(8) not null,
       u_id  number(8) not null,
       evaluate_content varchar2(4000),
       evaluate_date  varchar2(200),
       evaluate_level number(2) default 2);
       

create sequence seq_y_order_form_id 
       minvalue 1
       maxvalue 99999
       start with 1 
       increment by 1
       nocycle
       nocache
       order;
       
create table y_order_form(
       o_id  number(8) primary key,
       u_id  number(8) not null,
       g_ids  varchar2(200) not null,
       o_date  varchar2(200) not null,
       o_state  number(3) not null,
       o_goods_value  number(10,2) not null,
       o_transport_value  number(6,2),
       o_total_value  number(10,2) not null,
       o_receive_people  varchar2(30),
       o_receive_address  varchar2(500),
       o_receive_phone  varchar2(20));       
       
       
