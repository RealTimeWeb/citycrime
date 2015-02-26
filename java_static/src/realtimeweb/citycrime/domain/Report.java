package realtimeweb.citycrime.domain;

import net.minidev.json.JSONObject;

import com.jayway.jsonpath.JsonPath;

import java.lang.reflect.Field;

import realtimeweb.util.RealtimeWebUtil;

public class Report {
	private Integer year;
	private Integer population;
	private Integer assault;
	private Integer murder;
	private Integer rape;
	private Integer robbery;
	
	public Report() {
		
	}
	
	public Report(JSONObject jsonReportObj) {
		year = JsonPath.read(jsonReportObj,"$.year");
		population = JsonPath.read(jsonReportObj,"$.population");
		assault = JsonPath.read(jsonReportObj,"$.totals.violent.assault");
		murder = JsonPath.read(jsonReportObj,"$.totals.violent.murder");
		rape = JsonPath.read(jsonReportObj,"$.totals.violent.rape");
		robbery = JsonPath.read(jsonReportObj,"$.totals.violent.robbery");
		sanitizeFields();
	}
	
	private void sanitizeFields(){
		year = RealtimeWebUtil.nullToInt(year);
		population = RealtimeWebUtil.nullToInt(population);
		assault = RealtimeWebUtil.nullToInt(assault);
		murder = RealtimeWebUtil.nullToInt(murder);
		rape = RealtimeWebUtil.nullToInt(rape);
		robbery = RealtimeWebUtil.nullToInt(robbery);
	}

	public Integer getAssault() {
		return assault;
	}
	public void setAssault(Integer assault) {
		this.assault = assault;
	}
	public Integer getMurder() {
		return murder;
	}
	public void setMurder(Integer murder) {
		this.murder = murder;
	}
	public Integer getRape() {
		return rape;
	}
	public void setRape(Integer rape) {
		this.rape = rape;
	}
	public Integer getRobbery() {
		return robbery;
	}
	public void setRobbery(Integer robbery) {
		this.robbery = robbery;
	}
	public Integer getYear() {
		return year;
	}
	public void setYear(Integer integer) {
		this.year = integer;
	}
	public Integer getPopulation() {
		return population;
	}
	public void setPopulation(Integer integer) {
		this.population = integer;
	}
	@Override
	public String toString() {
		return "Report [year=" + year + ", population=" + population
				+ ", assault=" + assault + ", murder=" + murder + ", rape="
				+ rape + ", robbery=" + robbery + "]";
	}
}