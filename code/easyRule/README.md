# Easy rule demo

## 什么是easy rule
* easy rule是一个rule engine，用来代替复杂的`if` `else` block，在代码中使得业务能够得到更好的分离，从而让代码能够更好的使用策略模式来分离业务
* 与之相同的还有很多其他的rule engine
  * Drool
  * OpenL Tablets
  * RuleBook
## 如何使用easy rule
* 不使用注解的形式(**_OO_**)
  * 添加依赖
  ```xml
  <dependency>
      <groupId>org.jeasy</groupId>
      <artifactId>easy-rules-core</artifactId>
      <version>4.1.0</version>
  </dependency>
  ```
  * 创建抽象类继承 `BasicRule`, 重载`evaluate()` & `execute()`方法，前者为了执行不同的rule匹配规则，后者为了自定义执行满足条件之后的操作，🌰 ：打印某些日志，或者改变原先的facts的值
  * 定义满足条件的抽象函数`hitCondition`，每个rule继承Basic方法的之后，实现`hitCondition`方法
  * 定义RuleEngine，初始化rule parameter参数
    * `setSkipOnFirstAppliedRule`，这个配置决定了是否匹配到第一个rule就直接返回了
    * `isSkipOnFirstNonTriggeredRule`，跳过第一个没有被触发的规则，<strong style="color:yellow">什么是没有触发的规则？</strong>
    * `skipOnFirstFailedRule`，当第一个匹配到的规则失败的时候，将会返回匹配到的下一个规则
  * 定义Rule，并注册rule
  * 最后定义一个通用的方法，使用RuleEngine的`fire`方法，来触发规则匹配
* annotation
* Expression
* fluent programmatic way
* rule yml
* spring boot 项目，使用注解，注入rule
