package it.enuwa.sfdc.routing;

import it.enuwa.sfdc.sfdc.Salesforce;
import org.restlet.Component;
import org.restlet.routing.VirtualHost;
import org.restlet.service.CorsService;

import java.util.Arrays;
import java.util.HashSet;


public class APIServerComponent extends Component {

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public APIServerComponent(){
		setName("Maverick API server Application");
		setDescription("Maverick API server application");
		setOwner("Youmove.me srl");
		setAuthor("With <3 by Waynaut Team");
		
		//Server server = getServers().add(Protocol.HTTP, 8080); //80 production, 8085 localH
		//server.getContext().getParameters().set("tracing", "false");
		VirtualHost host = getDefaultHost();

		APIServerApplication jsonApplication = new APIServerApplication();
		CorsService corsService = new CorsService();
        corsService.setAllowedOrigins( new HashSet(Arrays.asList("*")));
        corsService.setAllowedCredentials(true);

        jsonApplication.getServices().add(corsService); 
		
		host.attachDefault(jsonApplication);

		//setup SFDC OAuth2 flow
		//Salesforce.boot();
		
	}
}