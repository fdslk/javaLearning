# java spring boot demo project
* restful api
* webflux
  * **_constructor injection to wire up dependencies and @ComponentScan to find beans_**
  * using webflux router to build controller
* junit.jupiter
  * webTestClient using as a test server client
  * clientAndServer mock dependent client
* maven
  * mvn spring-boot:run compile and run my application by mvn
* @Value
  * need to care about the injection sequence.
    * https://stackoverflow.com/questions/25764459/spring-boot-application-properties-value-not-populating
* plantuml
  * Archimate-PlantUML
    * https://github.com/plantuml-stdlib/Archimate-PlantUML