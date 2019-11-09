package com.springmvc.pojo;

public class Demo {

	private People peo;

	public People getPeo() {
		return peo;
	}

	public void setPeo(People peo) {
		this.peo = peo;
	}

	@Override
	public String toString() {
		return "Demo [peo=" + peo + "]";
	}
	
	
}
