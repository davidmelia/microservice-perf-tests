package com.example.demo;

import java.util.Collection;

import lombok.Value;

@Value
public class DataComposite {
	private Data data;
	private Collection<Data> datas;
	
	public DataComposite() {
		this.data = null;
		this.datas=null;
	}
	
	public DataComposite(Data data, Collection<Data> datas) {
		super();
		this.data = data;
		this.datas = datas;
	}
	
	
}
