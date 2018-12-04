package com.adobe.target.sample;

import com.adobe.experiencecloud.target.client.MarketingCloudClient;
import com.adobe.experiencecloud.target.client.impl.DefaultMarketingCloudClient;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class ClientSampleApplication {

	public static void main(String[] args) {
		SpringApplication.run(ClientSampleApplication.class, args);
	}

	@Bean MarketingCloudClient marketingCloudClient() {
	    return DefaultMarketingCloudClient.builder()
	          .clientId("adobetargetmobile" )
	          .organizationId("2274402E5A99659A0A495EDC@AdobeOrg")
	          .secure( false )
	          .timeout( 10000 )
	          .build();
	  }
}
