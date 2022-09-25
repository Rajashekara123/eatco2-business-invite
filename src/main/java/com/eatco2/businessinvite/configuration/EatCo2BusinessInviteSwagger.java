/**
 * 
 */
package com.eatco2.businessinvite.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @author Rajashekara
 *
 */
@Configuration
@EnableSwagger2
public class EatCo2BusinessInviteSwagger {

	@Bean
	public Docket productApi() {
		return new Docket(DocumentationType.SWAGGER_2).select()
				.apis(RequestHandlerSelectors.basePackage("com.eatco2.businessinvite")).paths(PathSelectors.any())
				.build();
	}

}
