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
      * receive a function and return a flux objec
  * mono 常用操作
    * flatMap
      * asynchronously operator
    * flatMapMany
      * convert Mono object to Flux object
