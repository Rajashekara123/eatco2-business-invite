/**
 * 
 */
package com.eatco2.businessinvite.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

/**
 * @author Rajashekara
 *
 */
@Configuration
@PropertySource(value = "classpath:eatco2-messages-configuration.properties")
public class AppConfig {

}
