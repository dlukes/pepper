package org.corpus_tools.pepper.gui.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;

import org.corpus_tools.pepper.service.adapters.PepperJobMarshallable;
import org.corpus_tools.pepper.service.adapters.PepperModuleCollectionMarshallable;
import org.corpus_tools.pepper.service.adapters.PepperModuleDescMarshallable;
import org.corpus_tools.pepper.service.adapters.StepDescMarshallable;
import org.corpus_tools.pepper.service.interfaces.PepperServiceImplConstants;
import org.corpus_tools.pepper.service.util.PepperSerializer;
import org.corpus_tools.pepper.service.util.PepperServiceURLDictionary;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ServiceConnector implements PepperServiceURLDictionary{
	private String serviceUrl;
	public Client client = null;
	public WebTarget baseTarget = null;	
	
	private final PepperSerializer serializer;
	
	private static final Logger logger = LoggerFactory.getLogger(ServiceConnector.class);
	private static final String ERR_404 = "Requested resource not available.";
	private static final String ERR_REQUEST = "An error occured performing the request.";
	
	public ServiceConnector(String serviceURL){
		this.serviceUrl = serviceURL;
		this.client = ClientBuilder.newClient();
		this.baseTarget = client.target(serviceUrl);		
		serializer = PepperSerializer.getInstance(PepperServiceImplConstants.DATA_FORMAT);
		
		logger.info("ServiceConnector initialized with base target" + baseTarget + " and Serializer " + serializer);		
	}

	public Collection<PepperModuleDescMarshallable> getAllModules() {
		ArrayList<PepperModuleDescMarshallable> moduleList = new ArrayList<PepperModuleDescMarshallable>(); 
		try{				
			HttpURLConnection connection = (HttpURLConnection) (new URL("http://localhost:8080/pepper-rest/resource/modules")).openConnection();
			connection.setRequestMethod("GET");
			connection.setRequestProperty("Accept", PepperServiceImplConstants.DATA_FORMAT);
			InputStreamReader reader = new InputStreamReader(connection.getInputStream());
			BufferedReader br = new BufferedReader(reader);			
			StringBuilder xml = new StringBuilder();
			String line = br.readLine();
			while (line != null){
				xml.append(line);
				line = br.readLine();
			}
			Collection<PepperModuleDescMarshallable> rawList = ((PepperModuleCollectionMarshallable)serializer.unmarshal(xml.toString(), PepperModuleCollectionMarshallable.class)).getModuleList();			
			for (PepperModuleDescMarshallable pmdm : rawList){
				moduleList.add(pmdm);
			}
		} catch (IOException e){
			logger.error(ERR_REQUEST);
		}
	
		return moduleList;
	}

	/**
	 * 
	 * @param configs
	 * @return 
	 * 		Job id
	 */
	public String createJob(List<StepDescMarshallable> configs) {
		PepperJobMarshallable jdm = new PepperJobMarshallable();
		jdm.setBasedirURI("."); // TODO what to do with this
		jdm.getSteps().addAll(configs);
		
		String data = serializer.marshal(jdm);
		
		logger.info(data); //TODO shift to debug later
		
		try{				
			HttpURLConnection connection = (HttpURLConnection) (new URL("http://localhost:8080/pepper-rest/resource/job")).openConnection();
			connection.setRequestMethod("POST");
			connection.setDoOutput(true);
			connection.setDoInput(true);			
			connection.setRequestProperty("Content-Type", PepperServiceImplConstants.DATA_FORMAT);	
			connection.setRequestProperty("Content-Length", Integer.toString(data.length()));
			connection.connect();
			/*SEND*/
			OutputStreamWriter writer = new OutputStreamWriter(connection.getOutputStream());
			writer.write(data);
			writer.flush();
			/*RECEIVE*/
			if (connection.getResponseCode() == 200){		
				InputStreamReader reader = new InputStreamReader(connection.getInputStream());
				StringBuilder response = new StringBuilder();
				int c = reader.read();
				while (c != -1){
					response.append((char)c);
					c = reader.read();
				}			
				return response.toString();
			} else {
				logger.error(ERR_REQUEST+" "+connection.getResponseCode());
			}
		} catch (IOException e){
			logger.error(ERR_REQUEST);
			//DEBUG:
			e.printStackTrace();
		}
		
		return null;
	}
}
