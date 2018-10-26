package com.endava.javanight.view;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

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

		// Get URL to call endpoint
		List<String> toConcatenate = Arrays.asList("http://", instanceInfo.getIPAddr(), ":",
				new Integer(instanceInfo.getPort()).toString(), "/hello/?name=", name);

		String url = String.join("", toConcatenate);

		String response = restTemplate.getForObject(url, String.class);

		return String.format("%s, have a nice day!", response);
	}

}
