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
  * using embedded elastic-search
  * using stub to mock elastic search server
  * using ESIntegTestCase
