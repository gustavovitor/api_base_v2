# Base OAuth2
Base API using Spring Boot with OAuth2 Security.

# Resources:
<strong>OAuth2 login:</strong> localhost:8080/oauth/token<br/>
To get an token, send a request with a Basic Auth: <br/> <br/>
<strong>Login:</strong> oauth2-client-api <br/>
<strong>Pass:</strong> *Y*%bXQ#<5,p~[Vk9bb&&X9rsw7V~J`_ <br/> <br/> 
With followed header <strong>Content-Type: application/x-www-form-urlencoded</strong> and set-up the followed body: <br/><br/> 
<strong>username:</strong> admin@admin.com <br/>
<strong>password:</strong> 1 <br/>
<strong>grant_type:</strong> password <br/>
<br/><strong>Token Revoke:</strong> localhost:8080/token/revoke

# Service Maker and Resource Maker
This API can made an service and a resource using ResourceMaker and ServiceMaker.

Pageable example params:
?pageable&page=0&size=5&sort=id,desc