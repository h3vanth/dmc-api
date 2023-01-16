package io.bbw.dmc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DmcApplication {

	public static void main(String[] args) {
		String properties = System.getProperty("properties");
		if (properties != null) {
			for (String property : properties.split(",")) {
				String[] kv = property.split("=");
				System.setProperty(kv[0].trim(), kv[1].trim());
			}
		}
		SpringApplication.run(DmcApplication.class, args);
	}
}
