# How to process nullable value in elegant way

## optional in java
* **create** optional
  * ```of```, set a null will throw nullPointerException
  * ```ofNullable```, set a null will throw no suchElementException
* ```orElse``` will create a default value whatever this.isPresent is true or not
* ```getOrElse``` the opposite of "orElse" operation
* handle exception
  * ```orElseThrow``` works for optional.ofNullable
* ```filter```
  * This is used to check logic with different judge condition. incompatible for list operation
  * If list, we can use stream filter directly
* transforming
  * ```map```
    * convert a optional value to other value
    * also can filter a new list from original list with java stream operation 
  * ```flatMap```
    * for nested optional object, no need to unwrap, which can get it directly.
* serialize
  * with Jackson to treat an empty Optional as null, and to treat a present Optional as a field representing its value.
## option in vavr
* option is used to eliminate nullable check, which can make our code more safe
* you can add the following dependency for using option vavr
```
<dependency>
    <groupId>io.vavr</groupId>
    <artifactId>vavr</artifactId>
    <version>0.9.0</version>
</dependency>
```
* create option
  * ```Option.of```, then we can use getOrElse to get actual value or set a default value for returning
* Tuple
* Try
  * replace try-catch block
  * if it is failure, can use ```getOrElse```
  * if dev wants to throw exception, can use getOrElseThrow
* function Interface
  * Function
  * BiFunction
  * Function[num]
* collections
  * include list, array, set, map, etc.
* validation
  * combine
  * valid
  * invalid
  * ap
* Match Pattern
  ```java
  Match(input).of(
  Case(...
  ))
   ```

## optional VS option

