# Open-api

## swagger

* How to?
  * add open-api dependency, different framework needs import different maven dependency,
  for our demo, we should add **webflux related openapi dependency**
  ```<dependency>
    <groupId>org.springdoc</groupId>
    <artifactId>springdoc-openapi-webflux-ui</artifactId>
    <version>1.6.4</version>
    </dependency>
  ```
  * how to verify it
    * access to swagger ui ```http://localhost:8080/context-path/swagger-ui/index.html```
      * if context-path isn't set in application.properties, the default one is the root "/"
    * access to open-api description ```http://server:port/context-path/v3/api-docs```
  * path customization
    * add ```springdoc.api-docs.path``` in application.properties
    * add ```springdoc.swagger-ui.path``` in application.properties 

## open-api validation for controller contract