package realtimeweb.citycrime.domain;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;




/**
 * Report for a particular year for that city
 */
public class AnnualReport {
	
    private Double assualtRate;
    private Double burglaryRate;
    private Double motorRate;
    private Double murderRate;
    private Double larcenyRate;
    private Integer year;
    private Double rapeRate;
    private Double robberyRate;
    private Integer population;
    
    
    /*
     * @return The assault rate
     */
    public Double getAssualtRate() {
        return this.assualtRate;
    }
    
    /*
     * @param The assault rate
     * @return Double
     */
    public void setAssualtRate(Double assualt_Rate) {
        this.assualtRate = assualt_Rate;
    }
    
    /*
     * @return The burglary rate
     */
    public Double getBurglaryRate() {
        return this.burglaryRate;
    }
    
    /*
     * @param The burglary rate
     * @return Double
     */
    public void setBurglaryRate(Double burglary_Rate) {
        this.burglaryRate = burglary_Rate;
    }
    
    /*
     * @return The motor rate
     */
    public Double getMotorRate() {
        return this.motorRate;
    }
    
    /*
     * @param The motor rate
     * @return Double
     */
    public void setMotorRate(Double motor_Rate) {
        this.motorRate = motor_Rate;
    }
    
    /*
     * @return The murder rate
     */
    public Double getMurderRate() {
        return this.murderRate;
    }
    
    /*
     * @param The murder rate
     * @return Double
     */
    public void setMurderRate(Double murder_Rate) {
        this.murderRate = murder_Rate;
    }
    
    /*
     * @return The larceny rate
     */
    public Double getLarcenyRate() {
        return this.larcenyRate;
    }
    
    /*
     * @param The larceny rate
     * @return Double
     */
    public void setLarcenyRate(Double larceny_Rate) {
        this.larcenyRate = larceny_Rate;
    }
    
    /*
     * @return The year
     */
    public Integer getYear() {
        return this.year;
    }
    
    /*
     * @param The year
     * @return Integer
     */
    public void setYear(Integer year) {
        this.year = year;
    }
    
    /*
     * @return The rape rate
     */
    public Double getRapeRate() {
        return this.rapeRate;
    }
    
    /*
     * @param The rape rate
     * @return Double
     */
    public void setRapeRate(Double rape_Rate) {
        this.rapeRate = rape_Rate;
    }
    
    /*
     * @return The robbery rate
     */
    public Double getRobberyRate() {
        return this.robberyRate;
    }
    
    /*
     * @param The robbery rate
     * @return Double
     */
    public void setRobberyRate(Double robbery_Rate) {
        this.robberyRate = robbery_Rate;
    }
    
    /*
     * @return The population
     */
    public Integer getPopulation() {
        return this.population;
    }
    
    /*
     * @param The population
     * @return Integer
     */
    public void setPopulation(Integer population) {
        this.population = population;
    }
    
	
	/**
	 * Creates a string based representation of this Annualreport.
	
	 * @return String
	 */
	public String toString() {
		return "Annualreport[" +assualtRate+", "+burglaryRate+", "+motorRate+", "+murderRate+", "+larcenyRate+", "+year+", "+rapeRate+", "+robberyRate+", "+population+"]";
	}
	
	/**
	 * Internal constructor to create a Annualreport from a json representation.
	 * @param map The raw json data that will be parsed.
	 * @return 
	 */
    public AnnualReport(Map<String, Object> raw) {
        // TODO: Check that the data has the correct schema.
        // NOTE: It's much safer to check the Map for fields than to catch a runtime exception.
        try {
        	if(((Map<String, Object>) ((Map<String, Object>) raw.get("rates")).get("violent")).get("assault")!=null){
              this.assualtRate = Double.parseDouble(((Map<String, Object>) ((Map<String, Object>) raw.get("rates")).get("violent")).get("assault").toString());
        	}
        	if(((Map<String, Object>) ((Map<String, Object>) raw.get("rates")).get("violent")).get("murder")!=null){
        		this.murderRate = Double.parseDouble(((Map<String, Object>) ((Map<String, Object>) raw.get("rates")).get("violent")).get("murder").toString());
        	}
        	if(((Map<String, Object>) ((Map<String, Object>) raw.get("rates")).get("violent")).get("rape")!=null){
        		this.rapeRate = Double.parseDouble(((Map<String, Object>) ((Map<String, Object>) raw.get("rates")).get("violent")).get("rape").toString());
        	}
        	if(((Map<String, Object>) ((Map<String, Object>) raw.get("rates")).get("violent")).get("robbery")!=null){
        		this.robberyRate = Double.parseDouble(((Map<String, Object>) ((Map<String, Object>) raw.get("rates")).get("violent")).get("robbery").toString());
        	}
        	if(((Map<String, Object>) ((Map<String, Object>) raw.get("rates")).get("property")).get("burglary")!=null){
        		this.burglaryRate = Double.parseDouble(((Map<String, Object>) ((Map<String, Object>) raw.get("rates")).get("property")).get("burglary").toString());	
        	}
        	if(((Map<String, Object>) ((Map<String, Object>) raw.get("rates")).get("property")).get("motor")!=null){
        		this.motorRate = Double.parseDouble(((Map<String, Object>) ((Map<String, Object>) raw.get("rates")).get("property")).get("motor").toString());
        	}
        	if(((Map<String, Object>) ((Map<String, Object>) raw.get("rates")).get("property")).get("larceny")!=null){
        		this.larcenyRate = Double.parseDouble(((Map<String, Object>) ((Map<String, Object>) raw.get("rates")).get("property")).get("larceny").toString());
        	}
            this.year = Integer.parseInt(raw.get("year").toString());   
            if(raw.get("population")!=null){
            	this.population = Integer.parseInt(raw.get("population").toString());
            }
            
        } catch (NullPointerException e) {
    		System.err.println("Could not convert the response to a Annualreport; a field was missing.");
    		e.printStackTrace();
    	} catch (ClassCastException e) {
    		System.err.println("Could not convert the response to a Annualreport; a field had the wrong structure.");
    		e.printStackTrace();
        }
    
	}	
}