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

## optional VS option

