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
  ```docker run -p 9200:9200 \
    -e "discovery.type=single-node" \
    docker.elastic.co/elasticsearch/elasticsearch:7.10.0
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
    * ElasticSearchRestTemplate
* How to mock ES in spring boot integration test
  * using elasticsearch-cluster-runner
  * using embedded elastic-search