package com.example;

import static org.assertj.core.api.Assertions.assertThat;

import com.example.service.HelloWorldService;
import com.example.web.SampleController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.context.PropertyPlaceholderAutoConfiguration;
import org.springframework.boot.autoconfigure.http.HttpMessageConvertersAutoConfiguration;
import org.springframework.boot.autoconfigure.web.servlet.DispatcherServletAutoConfiguration;
import org.springframework.boot.autoconfigure.web.servlet.ServletWebServerFactoryAutoConfiguration;
import org.springframework.boot.autoconfigure.web.servlet.WebMvcAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

/**
 * Basic integration tests for demo application.
 *
 * @author Dave Syer
 */
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
class NonAutoConfigurationSampleTomcatApplicationTests {

	@Autowired
	private TestRestTemplate restTemplate;

	@Test
	void testHome() {
		ResponseEntity<String> entity = this.restTemplate.getForEntity("/", String.class);
		assertThat(entity.getStatusCode()).isEqualTo(HttpStatus.OK);
		assertThat(entity.getBody()).isEqualTo("Hello 中国");
	}

	@Configuration(proxyBeanMethods = false)
	@Import({
			ServletWebServerFactoryAutoConfiguration.class,
			DispatcherServletAutoConfiguration.class,
			WebMvcAutoConfiguration.class,
			HttpMessageConvertersAutoConfiguration.class,
			PropertyPlaceholderAutoConfiguration.class
	})
	@ComponentScan(basePackageClasses = {
			SampleController.class, HelloWorldService.class
	})
	public static class NonAutoConfigurationSampleTomcatApplication {

		public static void main(String[] args) {
			SpringApplication.run(TomcatApplication.class, args);
		}

	}

}
