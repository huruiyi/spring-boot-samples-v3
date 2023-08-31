/*
 * Copyright 2012-2021 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.boot.web.servlet.server.ConfigurableServletWebServerFactory;

@SpringBootApplication
public class SessionJdbcApplication22000 implements WebServerFactoryCustomizer<ConfigurableServletWebServerFactory> {

	/**
	 upstream mySite {
		 server 127.0.0.1:22000 weight=1;
		 server 127.0.0.1:23000 weight=1;
	 }

	 server {
		 listen       80;
		 server_name  localhost;

		 location / {
			 root   html;
			 index  index.html index.htm;
			 proxy_pass http://mySite;
		 }

		 error_page   500 502 503 504  /50x.html;
		 location = /50x.html {
			 root   html;
		 }
	 }
	 */
	public static void main(String[] args) {
		SpringApplication.run(SessionJdbcApplication22000.class);
	}

	@Override
	public void customize(ConfigurableServletWebServerFactory factory) {
		factory.setPort(22000);
	}
}
