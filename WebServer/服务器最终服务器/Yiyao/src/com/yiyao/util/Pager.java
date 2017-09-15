package com.yiyao.util;

import java.util.List;

@SuppressWarnings("unchecked")
public class Pager {
  // ����ÿҳ��ʾ
  private static final int size = 8;
  // ��ҳ��
  private int totalpage;
  // ��ҳ��ݼ���
  private List list;

  public static int getSize() {
    return size;
  }

  public int getTotalpage() {
    return totalpage;
  }

  public void setTotalpage(int totalpage) {
    this.totalpage = totalpage;
  }

  public List getList() {
    return list;
  }

  public void setList(List list) {
    this.list = list;
  }
}
