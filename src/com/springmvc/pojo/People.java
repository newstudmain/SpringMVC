package com.springmvc.pojo;

public class People {

	private String username;
	private int age;
	
	public People() {}

	public People(String name, int age) {
		super();
		this.username = name;
		this.age = age;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String name) {
		this.username = name;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	@Override
	public String toString() {
		return "People [name=" + username + ", age=" + age + "]";
	}
	
	
}
