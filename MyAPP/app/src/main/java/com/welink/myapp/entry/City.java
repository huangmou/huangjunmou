package com.welink.myapp.entry;

import java.io.Serializable;

public class City implements Serializable {

	public String name;
	public String pinyi;

	public City(String name, String pinyi) {
		super();
		this.name = name;
		this.pinyi = pinyi;
	}

	public City() {
		super();
		// TODO Auto-generated constructor stub
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPinyi() {
		return pinyi;
	}

	public void setPinyi(String pinyi) {
		this.pinyi = pinyi;
	}

	@Override
	public String toString() {
		return "City{" +
				"name='" + name + '\'' +
				", pinyi='" + pinyi + '\'' +
				'}';
	}
}
