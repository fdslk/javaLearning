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
  * how to generate yaml format open-api file
    * add the following plugin in your project
    ```yaml
    <plugin>
		<groupId>org.springdoc</groupId>
		<artifactId>springdoc-openapi-maven-plugin</artifactId>
		<version>1.0</version>
		<executions>
			<execution>
				<id>integration-test</id>
				<goals>
					<goal>generate</goal>
				</goals>
			</execution>
		</executions>
		<configuration>
			<apiDocsUrl>http://localhost:8080/v3/api-docs.yaml
			</apiDocsUrl>
			<outputFileName>openapi.yaml</outputFileName>
		</configuration>
	</plugin>
    ```
    * run ```mvn verify -Dspring.application.admin.enabled=true -Pintegration -Dspringdoc.writer-with-default-pretty-printer=true```,
    then the openapi.yaml will be created in your ***./target*** directory
    
## open-api validation for controller contract
* swagger-request-validator-restassured to create integration test
* add dependency
  ```
  <dependency>
        <groupId>com.atlassian.oai</groupId>
        <artifactId>swagger-request-validator-restassured</artifactId>
        <version>${swagger-request-validator.version}</version>
        <scope>test</scope>
  </dependency>
  ```
  * using the above command to generate yaml file
* actually, ```OpenApiValidationFilter``` also reuse ```OpenApiInteractionValidator``` capability, which includes a filter interface for validate the 
whole response in the request.
* If you want to do more things, like logger, whiteRules filter, you can create a customFilter extending the OpenApiValidationFilter.
And override method ```filter``` as follows:
* ```java
  public static class CustomValidationFilter extends OpenApiValidationFilter {

        public CustomValidationFilter(String specUrlOrDefinition) {
            super(specUrlOrDefinition);
        }

        @Override
        public Response filter(FilterableRequestSpecification requestSpec, FilterableResponseSpecification responseSpec, FilterContext ctx) {
            log.info(format("[{0}]request from: {1}",requestSpec.getMethod(), requestSpec.getBaseUri() + requestSpec.getDerivedPath()));
            return super.filter(requestSpec, responseSpec, ctx);
        }
    }
  ```

## How to run test
* run application by ```./mvnw spring-boot::run ```
* run test by ```mvn clean test```
