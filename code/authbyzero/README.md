# Auth0 In Spring-Boot

## What is auth0?

* Auth0 is a open-source service for providing authentication and authorization.
* You can create a auth0 application on the auth0 server, then it will generate clientId and client secret to you.
* Then you don't need to create any authentication or authorization business in your machine
which can cut the unnecessary cost.

## How to use auth0 in spring-boot?
* Firstly, you are supposed to create your auth0 application. 
  * applications->applications->choose the category -> create application
  ![create new application](https://user-images.githubusercontent.com/6279298/164350380-e1c2fbb2-7e41-42c3-8ea4-0158c1b97f4b.png)
* Secondly, you are supposed to create an application api to let your resource application can be protected
  ![create new  api](https://user-images.githubusercontent.com/6279298/164351057-82118c2e-4e93-4287-8575-bd05b28e5d8d.png)
* Thirdly, you can test the newly api for obtaining a token by curl cmd.
  ```
  curl --request POST \
  --url ‘<DOMAIN>/oauth/token' \
  --header 'content-type: application/x-www-form-urlencoded' \
  --data grant_type=client_credentials \
  --data 'client_id=YOUR_CLIENT_ID' \
  --data client_secret=YOUR_CLIENT_SECRET \
  --data audience=YOUR_API_IDENTIFIER
  ```
* Adding spring-boot dependency in your application
  * add Oauth2 maven dependency
  * setup OAuth2 configuration
    * set up the audience
    * set up the issuer-url
  * add validator implements the OAuth2TokenValidator
  * configure api permission

## Tips:
* If this occurs to you:
```json
{"error":"access_denied","error_description":"Client is not authorized to access \"https://quickstarts/api\". You need to create a \"client-grant\" associated to this API. See: https://auth0.com/docs/api/v2#!/Client_Grants/post_client_grants"}%
```
* solution
  * api should authorize the newly app.
  ![](https://user-images.githubusercontent.com/6279298/164351811-db721670-2d43-4051-9b5f-429a3fc1be51.png)
  

[more details about how to use auth0 in spring-boot](https://github.com/auth0-samples/auth0-spring-security5-api-sample/tree/master/01-Authorization-MVC/src)