package com.dgq.users.common;

import java.util.List;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Data;

@ConfigurationProperties(prefix = "user-manager.path")
@Data
@Component
public class RemoteConfigProperties {
	
	private String reportPath;
	
	private List<String> searchPathsList;
}
