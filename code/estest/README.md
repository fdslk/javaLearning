# Elastic Search Integration test

* What is the Elastic Search Engine
  * Why we need it
  * The using scenarios
  * peer product
  * two-sides
    * advantages
    * shortcoming
* How to run local elastic search
  * run docker cmd 
  ```
    docker run -p 9200:9200 \
    -e "discovery.type=single-node" \
    docker.elastic.co/elasticsearch/elasticsearch:7.8.0
  ```
  * check es healthy status ``` http://localhost:9200 ```
* How to integrate ES in spring boot project
  * add maven dependency
  ```xml
  <dependency>
    <groupId>org.springframework.data</groupId>
    <artifactId>spring-data-elasticsearch</artifactId>
    <version>4.0.0.RELEASE</version>
  </dependency>
  ```
  * query methods
    * repository
      * autoWire ```ElasticsearchRestTemplate```
      * add ```NativeSearchQueryBuilder``` to build query conditions
      * add **ES** configuration class, add a java bean to instantiate an ```ElasticsearchRestTemplate```
    * ElasticSearchRestTemplate
      * add new interface to implement ```ElasticsearchRepository```
      * add new index method to insert data into es, **tip:** search key-word should be included in the searched object
      * add ```Document``` annotation in operating object, which can wrapped the wanted mapping object
    * high-level REST client
      * add pom dependency
        ```
        <dependency>
          <groupId>org.elasticsearch.client</groupId>
          <artifactId>elasticsearch-rest-high-level-client</artifactId>
          <version>7.17.6</version>
        </dependency>
        ```
      * initialize rest high level client
      * use IndexRequest to build saving data
        * types
          * Json string directly, if you define an object at first, you can use `ObjectMapper` to execute mapper operation. 
          * `Map` document definition then framework will convert it to `Json format`
        * tips: If the id is fixed, the newly indexRequest will override the previous one.
      * how to search data
        * using `SearchRequest`, define index name, document type
        * build `SearchSourceBuilder`, set query item. 
        * set the source of searchRequest by SearchSourceBuilder
        * using the following cmd to verify all data
        ```kotlin
        curl -X GET "localhost:9200/<your index>/_search?pretty" -H 'Content-Type: application/json' -d
           '{
             "query": {
                "match_all": {}
              }
            }'
        ```
    * migrate `HLRC` to `co.elastic.clients`
    * Tips:
      * Actually, if you don't set up configurations for connecting es, like port number, ```ElasticsearchRestTemplate``` has provided the DEFAULT value **9200**
* How to mock ES in spring boot integration test
  * using docker to set up a real elastic search
    * if you take the ```ElasticsearchRepository``` as your ES connector, you will start real elastic search in your local machine for test
    * step:
      * step1: set up an es by docker
        ```
        docker run -p 9200:9200 \
        -e "discovery.type=single-node" \
        docker.elastic.co/elasticsearch/elasticsearch:7.8.0
        ```
      * instantiate `RestHighLevelClient` by annotation `@BeforeAll`
      * set up `restTemplate`
      * functional test class extends `BaseTestClass`
      * write test in the specific test class and make assertions
  * using embedded elastic-search,
    * add dependency
    ```xml
    <dependency>
	    <groupId>org.codelibs</groupId>
		   <artifactId>elasticsearch-cluster-runner</artifactId>
		   <version>6.6.0.0</version>
		   <scope>test</scope>
    </dependency>
    ```
    * build default JVM ES cluster by defining a static `ElasticsearchClusterRunner`
    * define default `RestHighLevelClient` and set port number as **9201**
      * tips: If you want update the version of `elasticsearch-cluster-runner`, you must take care of the rest-high-level-client using elasticsearch version.
      ![keep the version elasticsearch version be same](https://user-images.githubusercontent.com/6279298/193168951-9144cee0-f9d5-4c98-bc20-9fd6a6f4328a.png)
  * using stub to mock elastic search server
  * using ESIntegTestCase
    * Not good choice....
* migration `rest-high-level-client` to `java-client`
  * motivation
    * rest-high-level-client is deprecated since version 7.15.0, which means if you would want to use high level ES, the `rhlc` might take some incompatible problems.
    * `rhlc` used http to transport data, while elastic-search-java-api used transport protocol to transport data. 
  * how to
    * add new dependency
    ```xml
     <dependency>
       <groupId>co.elastic.clients</groupId>
       <artifactId>elasticsearch-java</artifactId>
       <version>8.4.3</version>
     </dependency>
     <dependency>
       <groupId>com.fasterxml.jackson.core</groupId>
       <artifactId>jackson-databind</artifactId>
       <version>2.12.3</version>
     </dependency>
    ```
    * tips
      * if this `NoClassDefFoundError: jakarta/json/spi/JsonProvider` happens to application
      you can add the following dependency
      ```xml
      <dependency>
        <groupId>jakarta.json</groupId>
        <artifactId>jakarta.json-api</artifactId>
        <version>2.0.1</version>
      </dependency>
      ```
      * if this `{
        "error" : "Content-Type header [application/x-www-form-urlencoded] is not supported",
        "status" : 406
        }` happens to application
      you can add a default header
      ```java
      RestClient.builder(
            new HttpHost("127.0.0.1", 9201))
            .setDefaultHeaders(new Header[]{
                    new BasicHeader("Content-type", "application/json")
            })
            .build();
      ```
