package it.enuwa.sfdc.routing;

import org.restlet.Request;
import org.restlet.Response;
import org.restlet.data.Header;
import org.restlet.util.Series;

public class APICorsFilter extends org.restlet.routing.Filter {

	@Override
	protected int beforeHandle (Request request, Response response) {
		int result = STOP;
		//Add the CORS header to every message passing through the filter:
		try{
				/*
				 * ALLOW CROSS DOMAIN REQUESTS:
				 */
			Series<Header> responseHeaders = (Series<Header>) response.getAttributes().get("org.restlet.http.headers");
			if (responseHeaders == null) {
				responseHeaders = new Series(Header.class);
				response.getAttributes().put("org.restlet.http.headers", responseHeaders);
			}

			responseHeaders.add(new Header("Access-Control-Allow-Credentials", "true"));
			responseHeaders.add(new Header("Access-Control-Allow-Methods", "POST, GET, OPTIONS"));
			responseHeaders.add(new Header("Access-Control-Allow-Headers","Authorization"));
			responseHeaders.add(new Header("Access-Control-Allow-Origin", "*"));


		} catch (Throwable t){
			t.printStackTrace();
		}


		result = CONTINUE;
		return result;
	}
	
}
