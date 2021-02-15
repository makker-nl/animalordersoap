package nl.virtualsciences.fuse.soap.animalorder.processors;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LogHeadersProcessor implements Processor {
	private final Logger log = LoggerFactory.getLogger(LogHeadersProcessor.class);

	@Override
	public void process(Exchange exchange) throws Exception {

		Object bodyObject = exchange.getIn().getBody();
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