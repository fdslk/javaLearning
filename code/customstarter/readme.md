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
