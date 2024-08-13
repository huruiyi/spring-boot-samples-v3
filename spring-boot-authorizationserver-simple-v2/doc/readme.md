## 0:well-known
	http://localhost:8080/.well-known/openid-configuration
    http://localhost:9000/.well-known/oauth-authorization-server
## 1:获取code:
- http://127.0.0.1:8080/oauth2/authorize?client_id=oidc-client&response_type=code&scope=openid&redirect_uri=http://127.0.0.1:8080/login/oauth2/code/oidc-client

- http://127.0.0.1:8080/oauth2/authorize?client_id=oidc-client&response_type=code&redirect_uri=http://127.0.0.1:8080/login/oauth2/code/oidc-client

- http://127.0.0.1:8080/oauth2/authorize?client_id=oidc-client&response_type=code&redirect_uri=http://127.0.0.1:8080/login/oauth2/code/oidc-client&scope=blog.write blog.read openid 
## 2：获取token:
### 2-1:grant_type：authorization_code
#### 2-1-1:client_secret_post
![authorization_code-post.png](authorization_code-post.png)
```shell
curl --location 'http://localhost:8080/oauth2/token' \
--form 'grant_type="authorization_code"' \
--form 'code="2G7JqJmn1Q5Qui5RbPYvy5gC07c6qwCH17Ohijywy6qN7suFC1vOv3jU-8lnjyB6aXcZZSKmiMU_LTkOG23BcD2pcbfk7x4U5Mq4ewWwDfBMomyKY0NQvsM1C5CL1b03"' \
--form 'client_id="oidc-client"' \
--form 'client_secret="secret"' \
--form 'redirect_uri="http://127.0.0.1:8080/login/oauth2/code/oidc-client"'
```

#### 2-1-2:Basic Auth:client_secret_basic
![authorization_code-basic-1.png](authorization_code-basic-1.png)
![authorization_code-basic-0.png](authorization_code-basic-0.png)
```shell
curl --location 'http://localhost:8080/oauth2/token' \
--header 'Authorization: ••••••' \
--header 'Cookie: JSESSIONID=7C9A004DB0A2148CAAC7FD58D9107483' \
--form 'grant_type="authorization_code"' \
--form 'code="PRCUc8ca5VORAyIHRg1hf6HGvXhrQWw24xA-q5LlLM-jlwHDIhJve0IEmhFsJ8zGGv00OH7Ej32vjq30PcGah1YOwCeYSXSu8WbwVgud8A1_uV0llNNfBbjzjMqPk0jU"' \
--form 'redirect_uri="http://127.0.0.1:8080/login/oauth2/code/oidc-client"'
```



### 2-2:grant_type：client_credentials
#### 2-2-1:client_secret_post
![02.png](02.png)
```shell
curl --location 'http://localhost:8080/oauth2/token' \
--header 'Content-Type: application/x-www-form-urlencoded' \
--data-urlencode 'grant_type=client_credentials' \
--data-urlencode 'scope=openid' \
--data-urlencode 'client_id=oidc-client' \
--data-urlencode 'client_secret=secret'
```
#### 2-2-2:Basic Auth:client_secret_basic
![03-0.png](03-0.png)
![03-1.png](03-1.png)
```shell
curl --location 'http://localhost:8080/oauth2/token' \
--header 'Content-Type: application/x-www-form-urlencoded' \
--header 'Authorization: Basic b2lkYy1jbGllbnQ6c2VjcmV0' \
--header 'Cookie: JSESSIONID=7C9A004DB0A2148CAAC7FD58D9107483' \
--data-urlencode 'grant_type=client_credentials' \
--data-urlencode 'scope=openid'
```



- 参考项目：spring-authorization-server\samples\default-authorizationserver
