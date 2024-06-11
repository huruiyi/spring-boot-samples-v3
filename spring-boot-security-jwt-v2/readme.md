## 第一步，获取token

```shell
curl --location 'http://localhost:8888/login' \
--header 'Cookie: JSESSIONID=626764E4C384EE4136D05487A56B8A4C' \
--form 'username="fox"' \
--form 'password="123456"'
```

## 第二步，获取用户信息

```shell
curl --location 'http://localhost:8888/user/getCurrentUser' \
--header 'Authorization: bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJleHBpcmVzSW4iOjE2OTk0MzMwODEwMTUsImF1dGhvcml0aWVzIjpbeyJhdXRob3JpdHkiOiJST0xFX2FkbWluIn0seyJhdXRob3JpdHkiOiJST0xFX3VzZXIifV0sImVuYWJsZWQiOnRydWUsInVzZXJuYW1lIjoiZm94In0.CtjgM3Vyvaua5_btTKY7Se-SEoFH3qspX9l72d5uhnQ' \
--header 'Cookie: JSESSIONID=5981F6C1F43DA022E5F65DF5239BE38F'
```
