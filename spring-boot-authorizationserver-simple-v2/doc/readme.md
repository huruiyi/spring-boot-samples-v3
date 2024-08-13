## 0:well-known
http://localhost:8080/.well-known/openid-configuration
## 1:获取code:
- （1）http://127.0.0.1:8080/oauth2/authorize?client_id=oidc-client&response_type=code&scope=openid&redirect_uri=http://127.0.0.1:8080/login/oauth2/code/oidc-client

- （2）http://127.0.0.1:8080/oauth2/authorize?client_id=oidc-client&response_type=code&redirect_uri=http://127.0.0.1:8080/login/oauth2/code/oidc-client

- （3）http://127.0.0.1:8080/oauth2/authorize?client_id=oidc-client&response_type=code&redirect_uri=http://127.0.0.1:8080/login/oauth2/code/oidc-client&scope=blog.write blog.read openid 
## 获取token:
![01.png](01.png)
```shell
curl --location 'http://localhost:8080/oauth2/token' \
--header 'Cookie: JSESSIONID=6B0421C17FC887E5E1C37112E7716C36' \
--form 'grant_type="authorization_code"' \
--form 'code="2G7JqJmn1Q5Qui5RbPYvy5gC07c6qwCH17Ohijywy6qN7suFC1vOv3jU-8lnjyB6aXcZZSKmiMU_LTkOG23BcD2pcbfk7x4U5Mq4ewWwDfBMomyKY0NQvsM1C5CL1b03"' \
--form 'client_id="oidc-client"' \
--form 'client_secret="secret"' \
--form 'redirect_uri="http://127.0.0.1:8080/login/oauth2/code/oidc-client"'
```




/**
	 * 参考项目：spring-authorization-server\samples\default-authorizationserver
	 * http://localhost:9000/.well-known/oauth-authorization-server
	 *
	 * http://127.0.0.1:9000/oauth2/authorize?client_id=messaging-client&response_type=code&scope=openid&redirect_uri=http://127.0.0.1:8080/authorized
	 *
	 * 通过http://127.0.0.1:9000/oauth2/authorize拿到code,
	 * curl --location 'http://localhost:9000/oauth2/token' \
	 * --header 'Cookie: JSESSIONID=D361BF69A645279B7C1780F0CCA9C0DD' \
	 * --form 'grant_type="authorization_code"' \
	 * --form 'code="dBpqkT1qSR1aw4L0F-tF-YvtT2kQlaXaPHCraETTsqQL-b23N_VCfiQEuLUY0vKXDitvN3gS4YUevHygm0XY0rNaPjNB3P3kbHvbkF8cVgOuBby6BuZjyNL6PsW3-fli"' \
	 * --form 'client_id="messaging-client"' \
	 * --form 'client_secret="secret"' \
	 * --form 'redirect_uri="http://127.0.0.1:8080/authorized"'
	 *
	 *
	 * Basic Auth:client_secret_basic
	 * curl --location 'http://localhost:9000/oauth2/token' \
	 * --header 'Authorization: Basic bWVzc2FnaW5nLWNsaWVudDpzZWNyZXQ=' \
	 * --header 'Cookie: JSESSIONID=D361BF69A645279B7C1780F0CCA9C0DD' \
	 * --form 'grant_type="client_credentials"'
	 *
	 *
	 * No Auth:client_secret_post
	 * curl --location 'http://localhost:9000/oauth2/token' \
	 * --header 'Cookie: JSESSIONID=D361BF69A645279B7C1780F0CCA9C0DD' \
	 * --form 'grant_type="client_credentials"' \
	 * --form 'client_id="messaging-client"' \
	 * --form 'client_secret="secret"'
	 *
	 */
