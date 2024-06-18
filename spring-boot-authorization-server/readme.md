参考：https://github.com/lokeshgupta1981/Spring-Security/tree/master/spring-boot-authorization-server

Basic Auth:admin-client:secret
curl --location 'http://localhost:8080/oauth2/token' \
--header 'Content-Type: application/x-www-form-urlencoded' \
--header 'Authorization: Basic YWRtaW4tY2xpZW50OnNlY3JldA==' \
--header 'Cookie: JSESSIONID=64E84129DDC2019788C05D309BEC94EC' \
--data-urlencode 'grant_type=client_credentials' \
--data-urlencode 'scope=user.read'

Bearer Token:
curl --location 'http://localhost:9000/api/users/me' \
--header 'Authorization: Bearer eyJraWQiOiIyYjYyMDBhZC0xZTg1LTQ0NWItYjllZC0xOTc3NzRjYzQ0MmYiLCJhbGciOiJSUzI1NiJ9.eyJzdWIiOiJhZG1pbi1jbGllbnQiLCJhdWQiOiJhZG1pbi1jbGllbnQiLCJuYmYiOjE3MTg2MDc3NTEsInNjb3BlIjpbInVzZXIucmVhZCJdLCJpc3MiOiJodHRwOi8vbG9jYWxob3N0OjgwODAiLCJleHAiOjE3MTg2MDc4MTEsImlhdCI6MTcxODYwNzc1MSwianRpIjoiYzA5YzJlZWItNzFkYi00MjczLTg0MTktODMyZmMxYzQxZGFiIn0.Pmyw4Snv20BxrLyhF6J4sQMtTiZevpU0N8WoTwBxbt3yiKA7PtWJeD8ODOmKEgGpKeEoBBGkVAnrmyYYjhUm1R3Fh43XPxV6U_4xT5YpBFSrx2fhSNMwRzmfxYtJyCt_8l9enxW3W4sJcINaPaJJ0Yn5Ru392dDKKtHtEYiU1CFSamveQvb7hy0Lz3oho8YmoC9S93R5QgvI-V7wwDkkPDAQBGT80pUpqp02ypc_T-1Vw-gMFypQ-pjH1IWraAzC6TH-ItSpqHgRaGZP_qVpb8XSXQ-G0GwSOHtL5IuU_HTYWuZihEZ53guYc7_Ys73x6J-FuhBGdc722F3L-X04VA' \
--header 'Cookie: JSESSIONID=64E84129DDC2019788C05D309BEC94EC'
