package com.example.demo;

import java.io.Serializable;

import lombok.ToString;

@ToString
public class Data implements Serializable {

	private static final long serialVersionUID = 2969254517362220792L;

	private String value;

	public Data() {
		this.value = null;
	}

	public Data(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

}
