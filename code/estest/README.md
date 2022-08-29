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
      * add ```NativeSearchQueryBuilder```
      * 
    * ElasticSearchRestTemplate
      * add new interface to implement ```ElasticsearchRepository```
      * add new index method to insert data into es, **tip:** search key-word should be included in the searched object
      * add **ES** configuration class, add a java bean to instantiate an ```ElasticsearchRestTemplate```
      * add ```Document``` annotation in operating object, which can wrapped the wanted mapping object
* How to mock ES in spring boot integration test
  * using elasticsearch-cluster-runner
  * using embedded elastic-search