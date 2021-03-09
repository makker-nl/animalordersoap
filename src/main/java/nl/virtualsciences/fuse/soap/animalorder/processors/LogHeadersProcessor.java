package nl.virtualsciences.fuse.soap.animalorder.processors;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;

public class LogHeadersProcessor implements Processor {
	private final Logger log = LoggerFactory.getLogger(LogHeadersProcessor.class);

	@Value("${aOSvc.endpoint}")
	private String animalOrderSvcEndpoint;

	@Value("${aOSvc.endpoint.message}")
	private String animalOrderSvcMessage;

	public void process(Exchange exchange) throws Exception {

		Object bodyObject = exchange.getIn().getBody();

		String svcEndpointPrefix = System.getenv().getOrDefault("SERVICE_ENDPOINT_PREFIX", "AapNootMiesService: ");
		

		log.info( String.format(animalOrderSvcMessage, svcEndpointPrefix, animalOrderSvcEndpoint));
		log.info("Endpoint of AnimalOrderService: " + animalOrderSvcEndpoint);
		log.info("CanonicalClassName of Body: " + bodyObject.getClass().getCanonicalName());

		if (ArrayList.class.isAssignableFrom(bodyObject.getClass())) {
			Object bodyChildObject = exchange.getIn().getBody(List.class).get(0);
			log.info("CanonicalClassName of Body First Child Object: " + bodyChildObject.getClass().getCanonicalName());
		} else {
			log.info("Body is not a list!");
		}

		log.info("Headers:");
		Map<String, Object> headers = exchange.getIn().getHeaders();
		for (String key : headers.keySet()) {
			log.info("Key: " + key + " | Value: " + headers.get(key));
		}
	}
}