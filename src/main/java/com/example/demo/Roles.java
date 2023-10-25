package com.example.demo;


public class Roles implements Comparable<Roles> {
    private int role_id;
    private String role_name;
	public Roles(String region_name, int region_id) {
		this.role_name = region_name;
		this.role_id = region_id;
	}
	public int getRole_id() {
		return role_id;
	}
	public void setRole_id(int role_id) {
		this.role_id = role_id;
	}
	public String getRole_name() {
		return role_name;
	}
	public void setRole_name(String role_name) {
		this.role_name = role_name;
	}

	  @Override
	    public int compareTo(Roles otherRole) {
	        return Integer.compare(this.role_id, otherRole.role_id);
	    }

}