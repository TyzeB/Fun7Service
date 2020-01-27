package io.tyze.b.fun7service;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

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
public class CheckServicesServletTest {
	private CheckServicesServlet servletUnderTest;
	@Mock
	private HttpServletRequest mockRequest;
	@Mock
	private HttpServletResponse mockResponse;

	StringWriter stringWriter;
	PrintWriter responseWriter;
    
	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		stringWriter = new StringWriter();
        responseWriter = new PrintWriter(stringWriter);
		servletUnderTest = new CheckServicesServlet();
	}
	
	@Test
	public void doGet_correctQueryParams() throws IOException {
		when(mockRequest.getParameter("userid")).thenReturn("200");
		when(mockRequest.getParameter("timezone")).thenReturn("LJ");
		when(mockRequest.getParameter("cc")).thenReturn("USA");
        when(mockResponse.getWriter()).thenReturn(responseWriter);
        
		servletUnderTest.doGet(mockRequest, mockResponse);
		
		assertTrue(Helper.isValidJSON(stringWriter.getBuffer().toString()));
		responseWriter.flush();
	}

	@Test
	public void doGet_noUserIdSpecified() throws Exception {
		when(mockRequest.getParameter("timezone")).thenReturn("LJ");
		when(mockRequest.getParameter("cc")).thenReturn("USA");
        when(mockResponse.getWriter()).thenReturn(responseWriter);
		
		servletUnderTest.doGet(mockRequest, mockResponse);		
		assertTrue(stringWriter.getBuffer().toString().contains("Provide userid, timezone and country code."));
		
		responseWriter.flush();
	}

	@Test
	public void doGet_noCountryCodeSpecified() throws IOException {
		when(mockRequest.getParameter("userid")).thenReturn("200");
		when(mockRequest.getParameter("timezone")).thenReturn("LJ");
        when(mockResponse.getWriter()).thenReturn(responseWriter);
        
		servletUnderTest.doGet(mockRequest, mockResponse);
		assertTrue(stringWriter.getBuffer().toString().contains("Provide userid, timezone and country code."));

		responseWriter.flush();
	}

	@Test
	public void doGet_noTimezoneSpecified() throws IOException {
		when(mockRequest.getParameter("userid")).thenReturn("200");
		when(mockRequest.getParameter("cc")).thenReturn("USA");
        when(mockResponse.getWriter()).thenReturn(responseWriter);
		
		servletUnderTest.doGet(mockRequest, mockResponse);
		assertTrue(stringWriter.getBuffer().toString().contains("Provide userid, timezone and country code."));
		
		responseWriter.flush();
	}

}
