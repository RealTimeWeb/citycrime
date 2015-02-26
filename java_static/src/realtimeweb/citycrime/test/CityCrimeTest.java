package realtimeweb.citycrime.test;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;

import realtimeweb.citycrime.CityCrimeService;
import realtimeweb.citycrime.Dataset;
import realtimeweb.citycrime.domain.CityCrime;
import realtimeweb.citycrime.domain.Report;

public class CityCrimeTest {
	CityCrimeService crimeService = new CityCrimeService(Dataset.SMALL_DATASET);
	@Test
	public void testGetCityCrime() {
		List<CityCrime> allCityCrime = crimeService.getAllCityCrime();
		for(CityCrime c: allCityCrime){
			assertNotNull(c.getDepartment());
			assertNotNull(c.getState());
			List<Report> reports = c.getReports();
			for(Report r : reports){
				assertNotNull(r.getYear());
				assertNotNull(r.getPopulation());
				assertNotNull(r.getAssault());
				assertNotNull(r.getRape());
				assertNotNull(r.getRobbery());
			}
		}
	}
	
	@Test
	public void testGetCityCrimeByState() {
		List<CityCrime> alabamaCrime = crimeService.getCityCrimeByState("Alabama");
		for(CityCrime c: alabamaCrime){
			assertNotNull(c.getDepartment());
			assertNotNull(c.getState());
			List<Report> reports = c.getReports();
			for(Report r : reports){
				assertNotNull(r.getYear());
				assertNotNull(r.getPopulation());
				assertNotNull(r.getAssault());
				assertNotNull(r.getRape());
				assertNotNull(r.getRobbery());
			}
		}
	}

	@Test
	public void testQuery(){
		List<CityCrime> result = crimeService.filterByViolentType("robbery>300&&rape<10");
		for(CityCrime c: result){
			assertNotNull(c.getDepartment());
			assertNotNull(c.getState());
			List<Report> reports = c.getReports();
			for(Report r : reports){
				assertNotNull(r.getYear());
				assertNotNull(r.getPopulation());
				assertNotNull(r.getAssault());
				assertNotNull(r.getRape());
				assertNotNull(r.getRobbery());
			}
		}
	}

}
