package realtimeweb.citycrime.domain;

import java.util.ArrayList;
import java.util.List;

import net.minidev.json.JSONArray;
import net.minidev.json.JSONObject;

public class CityCrime {
	String department;
	String state;
	List<Report> report;
	
	public CityCrime(){}

	public CityCrime(JSONObject obj){
		department = (String) obj.get("department");
		state = (String) obj.get("state");
		report = new ArrayList<Report>();
		JSONArray data = (JSONArray) obj.get("data");
		for (Object jsonReportObj:data){
			report.add(new Report((JSONObject) jsonReportObj));
		}
	}
	
	public List<Report> getReports() {
		return report;
	}

	public void setReport(List<Report> report) {
		this.report = report;
	}

	public String getDepartment() {
		return department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	@Override
	public String toString() {
		return "CityCrime [department=" + department + ", state=" + state
				+ ", reports=" + report + "]";
	}

}
