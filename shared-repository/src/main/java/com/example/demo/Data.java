package com.example.demo;

import java.io.Serializable;

import lombok.ToString;
import lombok.Value;


@Value
public class Data implements Serializable {

	private static final long serialVersionUID = 2969254517362220792L;

	private final String value;

	public Data() {
		this.value = null;
	}

	public Data(String value) {
		this.value = value;
	}

}
