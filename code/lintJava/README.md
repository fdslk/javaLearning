* feature works
  * write batect script to execute this check
  * run it in docker container
  * add allure report generation (add this following into the reporting plugins)
  ```<systemProperties>
    <property>
    <name>allure.results.directory</name>
    <value>${project.build.directory}/allure-results</value>
    </property>
    </systemProperties>``