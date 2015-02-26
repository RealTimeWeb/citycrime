package realtimeweb.citycrime;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import net.minidev.json.JSONArray;
import net.minidev.json.JSONObject;
import net.minidev.json.JSONValue;
import net.minidev.json.parser.ParseException;
import realtimeweb.citycrime.domain.CityCrime;
import realtimeweb.citycrime.domain.Report;

import com.jayway.jsonpath.Configuration;
import com.jayway.jsonpath.JsonPath;
import com.jayway.jsonpath.Option;

public class CityCrimeService {
	JSONArray rawData;
	
    /**
     * Creating a new service to read local data source
     * 
     * @return CityCrimeService
     */
	public CityCrimeService(){
		try {
			FileReader reader= new FileReader("data/crime_small.json");
			rawData = (JSONArray) JSONValue.parseWithException(reader);

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**Get all city crime reports
	 * 
	 * @return List<CityCrime>
	 */
	public List<CityCrime> getAllCityCrime(){
		List<CityCrime> cityCrimeList = new ArrayList<CityCrime>();
		for (int i =0; i < rawData.size(); i++){
			try{
				cityCrimeList.add(new CityCrime((JSONObject)rawData.get(i)));
			} catch (NullPointerException e){
				e.printStackTrace();
			}
		}
		return cityCrimeList;
	}
	
	/**
	 * getCityCrimeByState
	 * @param state
	 * @return List<CityCrime> of CityCrime Objects
	 */
	public List<CityCrime> getCityCrimeByState(String state){
		List<JSONObject> foundObjs = JsonPath.parse(rawData).read("$[?(@.state=="+state+")]");
		List<CityCrime> resultList = new ArrayList<CityCrime>();

		for(JSONObject obj : foundObjs){
			CityCrime cityCrime = new CityCrime(obj);
			resultList.add(cityCrime);
		}
		return resultList;
	}
	
	/**
	 * 
	 * @param queryString is a simple query of 4 types (assault, murder, robbery, rape) e.g. assault>300&&rape<10
	 * @return List<CityCrime> of CityCrime objects
	 */
	public List<CityCrime> filterByViolentType(String queryString){
		Configuration config = Configuration.builder().options(Option.AS_PATH_LIST).build();
		String formatted = formatQueryString("totals.violent",queryString);
		List<String> foundPaths = JsonPath.using(config).parse(rawData).read("$[*].data[?("+formatted+")]");
		List<CityCrime> resultList = new ArrayList<CityCrime>();

		// Regex:::create matcher object to get the parent
		for(String path : foundPaths){
			String pattern = "\\$\\[\\d+\\]";	//root

			// Create a Pattern object
			Pattern r = Pattern.compile(pattern);
			CityCrime cityCrime = new CityCrime();

			Matcher m = r.matcher(path);
			if (m.find()) {
				JSONObject rawObj = JsonPath.read(rawData,m.group(0));
				cityCrime.setDepartment((String) rawObj.get("department"));
				cityCrime.setState((String) rawObj.get("state"));


				JSONObject reportObj = JsonPath.read(rawData,path);
				List<Report> reports = new ArrayList<Report>();
				reports.add(new Report(reportObj));
				cityCrime.setReport(reports);
			}
			resultList.add(cityCrime);
		}
		return resultList;
	}
	
	public static String formatQueryString(String path, String query){
		String result="";
		String[] params = query.split("((?<=&&)|(?=&&)|(?<=\\|\\|)|(?=\\|\\|))");
		String operator = "";
		for(int i = 0; i < params.length ;i++){
			if(params[i].trim().equals("&&")||params[i].trim().equals("||")){
				operator = params[i].trim();
			}else{
				String value = operator+"@."+path+"."+params[i].trim();
				result += value;
			}
		}
		return result;
	}
	

	public static void main(String[] args) {

	}


}
