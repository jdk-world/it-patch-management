package com.example.demo;


public class ComplianceRegion {
	private String emp_region;
    private String patch_compliance;
	public ComplianceRegion(String emp_region, String patch_compliance) {
		this.emp_region = emp_region;
		this.patch_compliance = patch_compliance;
	}
	public String getEmp_region() {
		return emp_region;
	}
	public void setEmp_region(String emp_region) {
		this.emp_region = emp_region;
	}
	public String getPatch_compliance() {
		return patch_compliance;
	}
	public void setPatch_compliance(String patch_compliance) {
		this.patch_compliance = patch_compliance;
	}


 
}