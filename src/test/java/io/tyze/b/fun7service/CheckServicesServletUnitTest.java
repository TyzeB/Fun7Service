package io.tyze.b.fun7service;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Unit tests for {@link CheckServicesServlet}.
 */

@RunWith(JUnit4.class)
public class CheckServicesServletUnitTest {
	private CheckServicesServlet servletUnderTest;
	@Mock
	private HttpServletRequest mockRequest;
	@Mock
	private HttpServletResponse mockResponse;
    
	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		servletUnderTest = new CheckServicesServlet();
	}
	
	@Test
	public void doGet_correctQueryParams() throws IOException {
		when(mockRequest.getParameter("userid")).thenReturn("200");
		when(mockRequest.getParameter("timezone")).thenReturn("LJ");
		when(mockRequest.getParameter("cc")).thenReturn("USA");
		
		StringWriter stringWriter = new StringWriter();
        PrintWriter responseWriter = new PrintWriter(stringWriter);
        when(mockResponse.getWriter()).thenReturn(responseWriter);
        
		servletUnderTest.doGet(mockRequest, mockResponse);
		
		assertTrue(isValidJSON(stringWriter.getBuffer().toString()));
		responseWriter.flush();
	}

	@Test
	public void doGet_noUserIdSpecified() throws Exception {
		when(mockRequest.getParameter("timezone")).thenReturn("LJ");
		when(mockRequest.getParameter("cc")).thenReturn("USA");
		
		StringWriter stringWriter = new StringWriter();
        PrintWriter responseWriter = new PrintWriter(stringWriter);
        when(mockResponse.getWriter()).thenReturn(responseWriter);
		
		servletUnderTest.doGet(mockRequest, mockResponse);		
		assertTrue(stringWriter.getBuffer().toString().contains("Provide userid, timezone and country code."));
		
		responseWriter.flush();
	}

	@Test
	public void doGet_noCountryCodeSpecified() throws IOException {
		when(mockRequest.getParameter("userid")).thenReturn("200");
		when(mockRequest.getParameter("timezone")).thenReturn("LJ");
		
		StringWriter stringWriter = new StringWriter();
        PrintWriter responseWriter = new PrintWriter(stringWriter);
        when(mockResponse.getWriter()).thenReturn(responseWriter);
        
		servletUnderTest.doGet(mockRequest, mockResponse);
		assertTrue(stringWriter.getBuffer().toString().contains("Provide userid, timezone and country code."));

		responseWriter.flush();
	}

	@Test
	public void doGet_noTimezoneSpecified() throws IOException {
		when(mockRequest.getParameter("userid")).thenReturn("200");
		when(mockRequest.getParameter("cc")).thenReturn("USA");
		
		StringWriter stringWriter = new StringWriter();
        PrintWriter responseWriter = new PrintWriter(stringWriter);
        when(mockResponse.getWriter()).thenReturn(responseWriter);
		
		servletUnderTest.doGet(mockRequest, mockResponse);
		assertTrue(stringWriter.getBuffer().toString().contains("Provide userid, timezone and country code."));
		
		responseWriter.flush();
	}

	
	public boolean isValidJSON(String test) {
		try {
			new JSONObject(test);
		} catch (JSONException ex) {
			try {
				new JSONArray(test);
			} catch (JSONException ex1) {
				return false;
			}
		}
		return true;
	}
}
