package edu.nara.training;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;

@RestController
@RefreshScope
@Slf4j
public class ConfigmapReloadController {
	
	@Value("${nara.test.value}")
	private String val;
	
	@GetMapping("/reloadConfigMap")
	public String getMessage() {
		log.info(val);
		
		 try (InputStream input = ConfigmapReloadController.class.getClassLoader().getResourceAsStream("fluentdConfig.properties")) {

	            Properties prop = new Properties();

	            if (input == null) {
	            	log.info(prop.getProperty("Sorry, unable to find fluentdConfig.properties"));
	               return val;
	            }

	            //load a properties file from class path, inside static method
	            prop.load(input);

	            //get the property value and print it out
	           log.info(prop.getProperty("fluentD.host"));
	           log.info(prop.getProperty("fluentD.env"));
	     

	        } catch (IOException ex) {
	            ex.printStackTrace();
	        }
		
		
		return val;
	}

}
