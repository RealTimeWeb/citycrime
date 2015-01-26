package realtimeweb.citycrime.domain;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;




import realtimeweb.citycrime.domain.AnnualReport;

/**
 * A report on crime for a city.
 */
public class CityCrimeReport {
	
    private String department;
    private String state;
    private ArrayList<AnnualReport> reports;
    
    
    /*
     * @return The name of the police department for that city
     */
    public String getDepartment() {
        return this.department;
    }
    
    /*
     * @param The name of the police department for that city
     * @return String
     */
    public void setDepartment(String department) {
        this.department = department;
    }
    
    /*
     * @return Name of the U.S. state where the police department is located.
     */
    public String getState() {
        return this.state;
    }
    
    /*
     * @param Name of the U.S. state where the police department is located.
     * @return String
     */
    public void setState(String state) {
        this.state = state;
    }
    
    /*
     * @return All annual reports for that city
     */
    public ArrayList<AnnualReport> getAnnualReports() {
        return this.reports;
    }
    
    /*
     * @param All annual reports for that city
     * @return ArrayList<Annualreport>
     */
    public void setReports(ArrayList<AnnualReport> reports) {
        this.reports = reports;
    }
    
	
	/**
	 * Creates a string based representation of this Citycrimereport.
	
	 * @return String
	 */
	public String toString() {
		return "Citycrimereport[" +department+", "+state+", "+reports+"]";
	}
	
	/**
	 * Internal constructor to create a Citycrimereport from a json representation.
	 * @param map The raw json data that will be parsed.
	 * @return 
	 */
    public CityCrimeReport(Map<String, Object> raw) {
        // TODO: Check that the data has the correct schema.
        // NOTE: It's much safer to check the Map for fields than to catch a runtime exception.
        try {
            this.department = raw.get("department").toString();
            this.state = raw.get("state").toString();
            this.reports = new ArrayList<AnnualReport>();
            Iterator<Object> reportsIter = ((List<Object>)raw.get("data")).iterator();
            while (reportsIter.hasNext()) {
                this.reports.add(new AnnualReport((Map<String, Object>)reportsIter.next()));
            }
        } catch (NullPointerException e) {
    		System.err.println("Could not convert the response to a Citycrimereport; a field was missing.");
    		e.printStackTrace();
    	} catch (ClassCastException e) {
    		System.err.println("Could not convert the response to a Citycrimereport; a field had the wrong structure.");
    		e.printStackTrace();
        }
    
	}	
}