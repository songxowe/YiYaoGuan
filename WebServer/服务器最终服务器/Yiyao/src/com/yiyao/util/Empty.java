package com.yiyao.util;

public class Empty {
	public static String emptyToString(String s){
		if(s==null){
			s="";
		}
		return s;
	}
	
	public static boolean isEmpty(String s){
		if(s!=null&&!s.equals("")){
			return false;
		}
		return true;
	}
}
