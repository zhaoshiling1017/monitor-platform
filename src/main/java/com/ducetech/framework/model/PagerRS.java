package com.ducetech.framework.model;

import com.alibaba.fastjson.JSON;

import java.util.List;

public class PagerRS<T> {
	
	private long count;
	
	private List<T> results;
	
	private int pages;
	
	public PagerRS(List<T> results, long l, int p) {
		this.results = results;
		this.count = l;
		this.pages = p;
	}

	public long getCount() {
		return count;
	}


	public void setCount(int count) {
		this.count = count;
	}
	
	public int getPages() {
		return pages;
	}

	public void setPages(int pages) {
		this.pages = pages;
	}


	public List<T> getResults() {
		return results;
	}

	public void setResults(List<T> results) {
		this.results = results;
	}
	
	public String toJsonStr(){
		return "{\"total\":\"" + this.count + "\",\"pages\":\"" + this.pages + "\",\"rows\":"+JSON.toJSONString(this.results)+"}";
	}
}
