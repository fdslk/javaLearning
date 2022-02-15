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
  * mono 常用操作
    * flatMap
      * asynchronously operator
    * flatMapMany
      * convert Mono object to Flux object
    * concatWith
      * 将两个对象合并成flux对象
