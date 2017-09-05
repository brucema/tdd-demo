package com.ymm.info.tdd.demo;

import java.util.List;
import java.util.Map;
import java.util.Set;

public class A {

	private B b;
	
	private DAO dao;

	public A() {
	}

	public void setB(B b) {
		this.b = b;
	}
	
	public void setDao(DAO dao) {
		this.dao = dao;
	}

	public String getString(int score) {
		if (score > 10) {
			return "A";
		}
		return b.getString(score);
	}

	public int getInt() {
		return getDefaultResult() * 1;
	}

	public double getDouble() {
		return getDefaultDouble() / 2;
	}
	
	private double getDefaultDouble() {
		return 1.0;
	}

	public static int getDefaultResult() {
		return 1;
	}

	public List<Map<Integer, String>> getList(Set<Integer> set) {
		if (set == null || set.isEmpty()) {
			return null;
		}
		return dao.getList(set);
	}

}
