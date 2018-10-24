package com.example.demo;

import java.util.Arrays;
import java.util.List;

class HardcodedData {

	static final List<Data> DATAS = Arrays.asList(new Data[] { new Data("Data 1"), //
			new Data("Data 2"), //
			new Data("Data 3") });

	static final Data DATA = DATAS.get(0);
}
