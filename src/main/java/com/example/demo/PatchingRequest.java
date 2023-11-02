package com.example.demo;

class PatchingRequest {
    private String patchName;
    private String hostName;
    private String empId;
    private String preferredTime;
	public String getPatchName() {
		return patchName;
	}
	public void setPatchName(String patchName) {
		this.patchName = patchName;
	}
	public String getHostName() {
		return hostName;
	}
	public void setHostName(String hostName) {
		this.hostName = hostName;
	}
	public String getEmpId() {
		return empId;
	}
	public void setEmpId(String empId) {
		this.empId = empId;
	}
	public PatchingRequest(String patchName, String hostName, String empId, String preferredTime) {
		super();
		this.patchName = patchName;
		this.hostName = hostName;
		this.empId = empId;
		this.preferredTime = preferredTime;
	}
	public String getPreferredTime() {
		return preferredTime;
	}
	public void setPreferredTime(String preferredTime) {
		this.preferredTime = preferredTime;
	}
  
    


    // Constructors, getters, and setters
}