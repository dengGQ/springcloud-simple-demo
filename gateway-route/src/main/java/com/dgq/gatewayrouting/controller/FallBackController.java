package com.dgq.gatewayrouting.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/fallback")
@RestController
public class FallBackController {
	
	@RequestMapping("")
	public String fallback() {
		return "嘻嘻";
	}
}
