package io.tyze.b.fun7service;

import java.io.IOException;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import okhttp3.Authenticator;
import okhttp3.Credentials;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.Route;

import java.io.PrintWriter;

@SuppressWarnings("serial")
@WebServlet(name = "checkservices", value = "/")
public class CheckServicesServlet extends HttpServlet {

	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		boolean multiplayer = false;
		boolean customerservice = false;
		boolean ads = false;

		String timezone = req.getParameter("timezone");
		String userid = req.getParameter("userid");
		String cc = req.getParameter("cc");

		String error = "";
		if (userid == null || timezone == null || cc == null) {
			error = "Provide userid, timezone and country code.";
			resp.sendError(HttpServletResponse.SC_BAD_REQUEST, error);
		}

		try {
			customerservice = checkCustomerService(req).equals("{\"customerservice\":\"enabled\"}");
		} catch (Exception e) {
			e.printStackTrace();
		}

		try {
			ads = sendGetAds(cc).equals("{\"ads\": \"sure, why not!\"}");
		} catch (Exception e1) {
			e1.printStackTrace();
		}

		try {
			multiplayer = checkMultiplayerService(req).equals("{\"multiplayer\":\"enabled\"}");
		} catch (Exception e) {
			e.printStackTrace();
		}

		PrintWriter out = resp.getWriter();
		if (error.isEmpty()) {
			String services = "{\"multiplayer\":" + (multiplayer ? "\"enabled\"," : "\"disabled\",");
			services += "\"customerservice\":" + (customerservice ? "\"enabled\"," : "\"disabled\",");
			services += "\"ads\":" + (ads ? "\"enabled\"}" : "\"disabled\"}");
			resp.setContentType("application/json");
			resp.setCharacterEncoding("UTF-8");
			out.println(services);
		} else {
			out.println(error);
		}

	}

	private static OkHttpClient createAuthenticatedClient(final String username, final String password) {
		OkHttpClient client = new OkHttpClient.Builder().authenticator(getBasicAuth(username, password)).build();
		return client;
	}

	private static Authenticator getBasicAuth(final String username, final String password) {
		return new Authenticator() {

			@Override
			public Request authenticate(Route route, Response response) throws IOException {

				String credential = Credentials.basic(username, password);
				return response.request().newBuilder().header("Authorization", credential).build();
			}
		};
	}

	private static Response doRequest(OkHttpClient client, String url) throws Exception {
		Request req = new Request.Builder().url(url).build();
		Response response = client.newCall(req).execute();
		if (!response.isSuccessful()) {
			throw new IOException("Unexpected code " + response);
		}

		return response;
	}

	private String sendGetAds(String cc) throws Exception {
		OkHttpClient client = createAuthenticatedClient("fun7user", "fun7pass");
		String url = "https://us-central1-o7tools.cloudfunctions.net/fun7-ad-partner?countryCode=" + cc;

		Response response = doRequest(client, url);

		return response.body().string();

	}

	private String checkCustomerService(HttpServletRequest request) throws Exception {
		OkHttpClient client = new OkHttpClient.Builder().build();
		String url = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
				+ "/support?userid=" + request.getParameter("userid") + "&cc=" + request.getParameter("cc")
				+ "&timezone=" + request.getParameter("timezone");
		Response response = doRequest(client, url);

		return response.body().string().replaceAll("\\r\\n", "");
	}

	private String checkMultiplayerService(HttpServletRequest request) throws Exception {
		OkHttpClient client = new OkHttpClient.Builder().build();
		String url = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
				+ "/multiplayer?userid=" + request.getParameter("userid") + "&cc=" + request.getParameter("cc")
				+ "&timezone=" + request.getParameter("timezone");
		Response response = doRequest(client, url);

		return response.body().string().replaceAll("\\r\\n", "");
	}
}