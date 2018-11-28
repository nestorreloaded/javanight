package com.endava.javanight.view;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import com.netflix.appinfo.InstanceInfo;
import com.netflix.discovery.EurekaClient;
import com.netflix.discovery.shared.Application;

@RestController
public class WelcomeController {

	@Autowired
	private RestTemplate restTemplate;

	@Autowired
	private EurekaClient eurekaClient;

	@RequestMapping(value = "/welcome", method = RequestMethod.GET)
	public String sayHello(@RequestParam(value = "name", defaultValue = "Dr.") String name) {

		// Query eureka client in order to get Hello Micro Service
		Application usersApp = eurekaClient.getApplication("HELLO");

		InstanceInfo instanceInfo = usersApp.getInstances().get(0);

		UriComponents uri = UriComponentsBuilder.fromHttpUrl("http://{host}:{port}/hello/?name={name}")
				.buildAndExpand(instanceInfo.getIPAddr(), instanceInfo.getPort(), name);

		String response = restTemplate.getForObject(uri.toUriString(), String.class);

		return String.format("%s, Java nice day!", response);
	}

}
