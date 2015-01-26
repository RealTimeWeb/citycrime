package realtimeweb.citycrime.tests;

import static org.junit.Assert.assertNotNull;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.ArrayList;

import org.junit.Test;

import realtimeweb.citycrime.CityCrime;
import realtimeweb.citycrime.domain.AnnualReport;
import realtimeweb.citycrime.domain.CityCrimeReport;
import realtimeweb.stickyweb.EditableCache;
import realtimeweb.stickyweb.exceptions.StickyWebDataSourceNotFoundException;
import realtimeweb.stickyweb.exceptions.StickyWebDataSourceParseException;
import realtimeweb.stickyweb.exceptions.StickyWebInternetException;
import realtimeweb.stickyweb.exceptions.StickyWebInvalidPostArguments;
import realtimeweb.stickyweb.exceptions.StickyWebInvalidQueryString;
import realtimeweb.stickyweb.exceptions.StickyWebLoadDataSourceException;
import realtimeweb.stickyweb.exceptions.StickyWebNotInCacheException;

public class CityCrimeServiceTest {
	CityCrime citycrime = new CityCrime();
	
	@Test
	public void testCityCrimeServiceOnline() {
		
		ArrayList<CityCrimeReport> reports = citycrime.getReports();
		for(CityCrimeReport cityReport : reports){
			assertFieldsNotNull(cityReport);	
		}
	}

	@Test
	public void testCityCrimeServiceCache() {
		EditableCache recording = new EditableCache();
		ArrayList<CityCrimeReport> reports = citycrime.getReports();
		//recording
		try {
			recording.addData(citycrime.getReportsRequest());
		} catch (StickyWebNotInCacheException | StickyWebInternetException
				| StickyWebInvalidQueryString
				| StickyWebInvalidPostArguments e) {
			e.printStackTrace();
		}
		//saving
		try {
			recording.saveToStream(new FileOutputStream("test-cache.json"));
		} catch (StickyWebDataSourceNotFoundException
				| StickyWebDataSourceParseException
				| StickyWebLoadDataSourceException | FileNotFoundException e) {
			e.printStackTrace();
		}
		
		//test retrieving from cache
		CityCrime citycrime = new CityCrime("test-cache.json");
		reports = citycrime.getReports();
		for(CityCrimeReport cityReport : reports){
			assertFieldsNotNull(cityReport);	
		}
	}

	private void assertFieldsNotNull(CityCrimeReport cityReport) {
		assertNotNull(cityReport.getDepartment());
		assertNotNull(cityReport.getState());
		for(AnnualReport annualReport : cityReport.getAnnualReports()){
			assertNotNull(annualReport.getYear());
			//other fields can be null
		}
	}
}
