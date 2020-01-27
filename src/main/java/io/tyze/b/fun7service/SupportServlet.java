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
import java.time.ZoneId;
import java.time.ZonedDateTime;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@SuppressWarnings("serial")
@WebServlet(name = "support", value = "/support")
public class SupportServlet extends HttpServlet {

	private ZonedDateTime today = ZonedDateTime.now(ZoneId.of("UTC+1"));
	private ZonedDateTime start = today.withHour(8).withMinute(0).withSecond(0);
	private ZonedDateTime end = today.withHour(15).withMinute(0).withSecond(0);

	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		String service = "{\"user-support\":" + (checkAvailability() ? "\"enabled\"}" : "\"disabled\"}");
		resp.setContentType("application/json");
		resp.setCharacterEncoding("UTF-8");
		PrintWriter out = resp.getWriter();
		out.print(service);
	}

	public boolean checkAvailability() {
		if (today.isAfter(start) && today.isBefore(end)) {
			return true;
		}
		return false;
	}
}
