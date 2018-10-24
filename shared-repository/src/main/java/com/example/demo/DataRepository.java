package com.example.demo;

public interface DataRepository<O, A> {

	public O findOne(long delay);

	public A findAll(long delay);

}
