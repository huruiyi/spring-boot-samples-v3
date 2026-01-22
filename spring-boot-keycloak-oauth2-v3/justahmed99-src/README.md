* 获取code:
* https://keycloak.fairy.vip/realms/howtodoinjava/protocol/openid-connect/auth?response_type=code&client_id=employee-management-api&scope=openid&redirect_uri=http://localhost:9090/login/oauth2/code/employee-management-api

* 获取token
```shell
curl --location 'https://keycloak.fairy.vip/realms/howtodoinjava/protocol/openid-connect/token' \
--header 'Content-Type: application/x-www-form-urlencoded' \
--data-urlencode 'grant_type=authorization_code' \
--data-urlencode 'code=e16fa5ee-04d9-4090-a611-c926a4c24ec5.5813ffb5-bca0-43de-8997-7e291a96f93a.e0045dcd-91cf-4e3f-bfe5-93abdaf679ec' \
--data-urlencode 'client_id=employee-management-api' \
--data-urlencode 'client_secret=*********************' \
--data-urlencode 'redirect_uri=http://localhost:9090/login/oauth2/code/employee-management-api'
```
