package io.spring;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class AuthorizationServerSimpleApplication {

  /**
   * article:https://spring.io/blog/2023/05/24/spring-authorization-server-is-on-spring-initializr
   * <p>
   * http://localhost:8080/.well-known/oauth-authorization-server
   */
  public static void main(String[] args) {
    SpringApplication.run(AuthorizationServerSimpleApplication.class, args);
  }
}
//basic-auth:admin-client：secret
//curl --location 'http://localhost:8080/oauth2/token' \
//--header 'Content-Type: application/x-www-form-urlencoded' \
//--header 'Authorization: Basic YWRtaW4tY2xpZW50OnNlY3JldA==' \
//--data-urlencode 'grant_type=client_credentials' \
//--data-urlencode 'scope=user.read'
/*
{
    "access_token": "eyJraWQiOiIxNGFhYjBhNi1jZTIwLTQwMDUtOWMzOS1hMzhlODhhZWJiMTMiLCJhbGciOiJSUzI1NiJ9.eyJzdWIiOiJhZG1pbi1jbGllbnQiLCJhdWQiOiJhZG1pbi1jbGllbnQiLCJuYmYiOjE3MTg0NDY2NjcsInNjb3BlIjpbInVzZXIucmVhZCJdLCJpc3MiOiJodHRwOi8vbG9jYWxob3N0OjgwODAiLCJleHAiOjE3MTg0NDY5NjcsImlhdCI6MTcxODQ0NjY2NywianRpIjoiN2M0NTAyMGYtNDc2Zi00NTc1LTlmZjgtYjk3ZDAzOGE1ZDgzIn0.AAXxp1BfLgkwMS9Ow13rOTaq2URCk-R3-6XxVlPm8lrGRFZAUc7RFRjETmk8d7n5N20gjKwi-iQUOLVxBKWO9Qrz1pTIJlIAlY429RLK4VM_23NHawPP-9mYYITzFmGOaBcjVUwTC7GqmJfWxQ8VA_TSVUhxB8K6eblqWSew0d3LimUyrJxS0wZ3kBHjVwx285lg65ta5FUsbLaWWH8a52ojRCLk9mLO6armU61tsX8SctxPCsHBGnfLldMEa8Q0C4FqlrTK03bkaCwKyM1-Q5q4txVV3p65b2KyM-yH5RtO4Sg6cFCB5tsFlbVENc5bGV8KG77pBg2fqJc0SG9vXQ",
    "scope": "user.read",
    "token_type": "Bearer",
    "expires_in": 300
}
*/

//curl --location 'http://localhost:8080/oauth2/introspect' \
//--header 'Content-Type: application/x-www-form-urlencoded' \
//--header 'Authorization: ••••••' \
//--header 'Cookie: JSESSIONID=ACEA9A64AC9B7E0801D38B3C628E6C8D' \
//--data-urlencode 'token=xxx'
/*
{
    "active": true,
    "sub": "admin-client",
    "aud": [
        "admin-client"
    ],
    "nbf": 1718447197,
    "scope": "user.read",
    "iss": "http://localhost:8080",
    "exp": 1718447247,
    "iat": 1718447197,
    "jti": "76690932-063a-450c-86a4-dda35836b70b",
    "client_id": "admin-client",
    "token_type": "Bearer"
}
or
{
    "active": false
}
 */

//The JWK-Set Endpoint is available at http://localhost:8080/oauth2/jwks when the application is running.↩
//The OAuth2 Authorization Server Metadata Endpoint is available at http://localhost:8080/.well-known/oauth-authorization-server when the application is running.↩
//The OpenID Connect Provider Configuration Endpoint is available at http://localhost:8080/.well-known/openid-configuration when the application is running.↩
