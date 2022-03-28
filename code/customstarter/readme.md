# 如何构建自定义的spring starter

## 为什么要开发 spring starter

* 为了理解为什么我们需要开发spring starter这样的一个工具，主要原因还要从spring mvc开始说起。因为早期的MVC框架中，如果要引入其他的依赖，需要在项目的xml中配置很多。那如果依赖的的越多，或者项目的越复杂，那么加入的配置也会越多，最终导致我们xml文件也会非常的复杂。

* 因为有了👆🏻的痛点之后，spring加入了**auto-configuration**的技术
  * 什么是auto-configuration?
    * 可以把**classpath**中的jar包自动的导入在JVM中
    * 需要被auto-configuration的依赖都会被列举在**spring.property**文件中
    * 使用注解**AutoConfiguration**或者 **SpringBootApplication**在项目中加上，就可以使用auto-configuration的特性
    * 在pom文件中加入
    * &lt; dependency &gt;
            &lt;groupId&gt;org.springframework.boot&lt;/groupId&gt;
            &lt;artifactId&gt;spring-boot-configuration-processor&lt;/artifactId&gt;
            &lt;optional&gt;true&lt;/optional&gt;
        &lt;/dependency&gt;
      * [Cannot Resolve Spring Boot Configuration Properties Error](https://www.baeldung.com/intellij-resolve-spring-boot-configuration-properties)
      * 加入了上述依赖之后，没有重新构建的时候都会在target/classes/创建'META-INF/'

## 什么是spring starter
* starter可以在spring boot启动的时候把一些开源的资源文件或者一些配置文件提前加载到java bean中，这样就可以避免一些依赖的重复引入
* auto configuration class
  * 在resource/META-INF文件目录下，创建一个文件**spring.factories**，当spring boot启动的时候，加载spring.factories中的包含的对象
    ``` 
    # Auto Configure
    org.springframework.boot.autoconfigure.EnableAutoConfiguration=\
    org.springframework.boot.autoconfigure.amqp.RabbitAutoConfiguration,\
    org.springframework.boot.autoconfigure.cassandra.CassandraAutoConfiguration,\
    org.springframework.boot.autoconfigure.mongo.MongoAutoConfiguration,\
    org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration
    ```
  * 不是加到了这个文件中，就会都能够正常使用，只是spring boot在启动的时候，会将这些类（以及相关的beans）加载到java bean中
  * 这些被引用的类，就可以被开箱即用
* 从application.property中装载自定的属性
  * 使用注解**ConfigurationProperties(prefix = "timeout")**，关联java类中的属性和application中的关系
  * 使用注解**Configuration**定义java bean类型
  * 创建MATE-INF文件夹，并在创建**additional-spring-configuration-metadata.json**文件定义需要自动装载的property的名字
* 如何自定一个starter
  * 定义自动配置的模块
  * 将需要自动化加载的类添加在configuration文件中，并加上注解**ConditionalOnClass**
  * pom文件中添加**spring-boot-autoconfigure**
  * 在srping.factories中加上对应的configuration
  * start命名规则
    * 不能以**spring boot**开头
    * 以**name-spring-boot-starter**的格式来命名

## 如何配置slf4j的日志格式
* 引入**spring-boot-starter-log4j2**的pom依赖
* 排除其他引入了logger的started，运行命令 ```mvn dependency:tree```检查引入logger的dependency
* 如果遇到问题，可能需要删除对应的external library，然后再用reload pom依赖
* 加入log4j2.xml在resource目录下
