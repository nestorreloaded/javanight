package com.endava.javanight.view;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

	@RequestMapping(value = "/hello", method = RequestMethod.GET)
	public String sayHello(@RequestParam(value = "name", defaultValue = "Dr.") String name) {
		return String.format("Hello %s", name);
	}

}
