# reactive-spring-webflux
* Spring Webflux
  * flux 常用操作
    * map
      * transforming operator n->n synchronously
    * flatmap
      * asynchronously 1 -> 0/n
    * flatMapSequential
      * asynchronously but keeping the original ordering
    * filter
    * reduce
    * concatMap
      * asynchronously
    * transform
      * receive a function and return a flux object
    * concat
      * 合并两个flux对象
    * concatWith
      * 静态方法合并两个flux
    * merge
    * zip
      * 最大操作8个元素，Tuple8
    * zipWith
  * mono 常用操作
    * flatMap
      * asynchronously operator
    * flatMapMany
      * convert Mono object to Flux object
    * concatWith
      * 将两个对象合并成flux对象
    * fromCallable
      * 调用函数
    * zipWith
    * 
# issues
* when using embed mongodb I got the following erro log

    ```org.springframework.beans.factory.UnsatisfiedDependencyException: Error creating bean with name 'embeddedMongoServer' defined in class path resource 
  [org/springframework/boot/autoconfigure/mongo/embedded/EmbeddedMongoAutoConfiguration.class]: Unsatisfied dependency expressed through method 'embeddedMongoServer' parameter 0; nested exception is org.springframework.beans.factory.BeanCreationException: Error creating bean with name 'embeddedMongoConfiguration' defined in class path resource [org/springframework/boot/autoconfigure/mongo/embedded/EmbeddedMongoAutoConfiguration.class]: Bean instantiation via factory method failed; 
  nested exception is org.springframework.beans.BeanInstantiationException: Failed to instantiate [de.flapdoodle.embed.mongo.config.MongodConfig]: Factory method 'embeddedMongoConfiguration' threw exception; nested exception is java.lang.IllegalStateException: Set the spring.mongodb.embedded.version property or define your own MongodConfig bean to use embedded MongoDB```
* solved by: adding annotation: @ImportAutoConfiguration(exclude = EmbeddedMongoAutoConfiguration.class)
or @DataMongoTest(excludeAutoConfiguration = EmbeddedMongoAutoConfiguration.class)
