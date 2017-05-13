package it.enuwa.sfdc.routing;

import it.enuwa.sfdc.resources.BlockchainResource;
import it.enuwa.sfdc.resources.SaveOrderResource;
import it.enuwa.sfdc.resources.UpgradeResource;
import it.enuwa.sfdc.resources.VerifyResource;
import org.restlet.Application;
import org.restlet.Restlet;
import org.restlet.data.ChallengeScheme;
import org.restlet.routing.Router;
import org.restlet.security.ChallengeAuthenticator;
import org.restlet.security.MapVerifier;


public class APIServerApplication extends Application {

	@Override
	public Restlet createInboundRoot(){

		Router router = new Router(getContext());


		MapVerifier verifier = new MapVerifier();
		verifier.getLocalSecrets().put("enuwa", "3nUw@".toCharArray());

		// Create a guard
		ChallengeAuthenticator guard = new ChallengeAuthenticator(
				getContext(), ChallengeScheme.HTTP_BASIC, "Tutorial");
		guard.setVerifier(verifier);

		APICorsFilter corsFilter = new APICorsFilter();	

		router.attach("/api/0.1/save/",SaveOrderResource.class);
		router.attach("/api/0.1/timestamp/",BlockchainResource.class);
		router.attach("/api/0.1/upgrade/",UpgradeResource.class);
		router.attach("/api/0.1/verify/",VerifyResource.class);
		//router.attach("/api/0.1/env/{varname}", EnvironmentResource.class);
		
		corsFilter.setNext(router);
		guard.setNext(router);

		return corsFilter;

	}

	public APIServerApplication(){
		setName("Acea API server application");
		setDescription("Acea API server application");
		setOwner("Enuwa");
		setAuthor("With <3 by Enuwa Team");
	}


}