/*
 * Copyright 2015 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.tyze.b.fun7service;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.EntityNotFoundException;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;

@SuppressWarnings("serial")
@WebServlet(name = "multiplayer", value = "/multiplayer")
public class MultiplayerServlet extends HttpServlet {
	
	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		String timezone = req.getParameter("timezone");
		String userid = req.getParameter("userid");
		String cc = req.getParameter("cc");
		
		String error = "";
		if (userid == null || timezone == null || cc == null) {
			error = "Provide userid, timezone and country code.";
			resp.sendError(HttpServletResponse.SC_BAD_REQUEST, error);
		}
		
		PrintWriter out = resp.getWriter();
		if (error.equals("")) {
			DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
			Key userKey = KeyFactory.createKey("User", userid);
			Entity user = null;
			if (userKey != null) {
				try {
					user = datastore.get(userKey);
				} catch (EntityNotFoundException e) {
					e.printStackTrace();
				}
			}

			if (user == null) {
				user = new Entity("User", userid);
				user.setIndexedProperty("userid", userid);
				user.setProperty("countrycode", cc);
				user.setProperty("timezone", timezone);
				user.setProperty("access", "1");
			} else {
				int access = Integer.parseInt(user.getProperty("access").toString()) + 1;
				user.setProperty("access", Integer.toString(access));
			}

			datastore.put(user);
			
			String service = "{\"multiplayer\":" + (checkMultiplayer(user) ? "\"enabled\"}" : "\"disabled\"}");
			resp.setContentType("application/json");
			resp.setCharacterEncoding("UTF-8");
			out.println(service);
		} else {
			out.println(error);
		}
	}

	private boolean checkMultiplayer(Entity user) {
		if (user == null) {
			return false;
		}
		if (Integer.parseInt(user.getProperty("access").toString()) >= 5
				&& user.getProperty("countrycode").toString().equals("US")) {
			return true;
		}

		return false;
	}
}
