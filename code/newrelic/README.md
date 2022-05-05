# How to integrate newRelic into spring-boot application

## From the [stackoverflow](https://stackoverflow.com/questions/26901959/new-relic-for-spring-boot), I found three ways to implement newRelic integration.

* Way one locally test by java cmd
  * we can download java agent from the following [url](https://docs.newrelic.com/docs/release-notes/agent-release-notes/java-release-notes/).
  * unzip the zip file
  * modify the license_key and app_name
  * run ```mvn package``` to build your application jar package
  * Enter this command java -javagent: \<path to your new relic jar\>\newrelic.jar -jar \<path to your application jar\>\\<you rapplication jar name>.jar
  * go to newrelic console
    * your application APM 
      ![APM service name](https://user-images.githubusercontent.com/96409339/166885858-57be9335-04e4-4269-b843-13d8b8d1c195.png)
    * other metrics
      ![metrics charts](https://user-images.githubusercontent.com/96409339/166887000-12aa2119-ed36-409c-a4d0-293935025627.png)
    * logs contents
      ![logs contents](https://user-images.githubusercontent.com/96409339/166887340-305a0dd5-4ca7-49f7-bd49-cbdd93c2a594.png)

  