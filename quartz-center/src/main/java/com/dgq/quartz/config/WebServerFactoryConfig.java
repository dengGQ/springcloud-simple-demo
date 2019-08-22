package com.dgq.quartz.config;

import org.springframework.boot.web.embedded.undertow.UndertowBuilderCustomizer;
import org.springframework.boot.web.embedded.undertow.UndertowServletWebServerFactory;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.context.annotation.Configuration;

@Configuration
public class WebServerFactoryConfig implements WebServerFactoryCustomizer<UndertowServletWebServerFactory>{

	@Override
	public void customize(UndertowServletWebServerFactory factory) {
		factory.addBuilderCustomizers((UndertowBuilderCustomizer)builder->{
			builder.addHttpListener(8080, "0.0.0.0");
		});
	}

}
