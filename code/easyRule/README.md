# Easy rule demo

## 什么是easy rule
* easy rule是一个rule engine，用来代替复杂的`if` `else` block，在代码中使得业务能够得到更好的分离，从而让代码能够更好的使用策略模式来分离业务
* 与之相同的还有很多其他的rule engine
  * Drool
  * OpenL Tablets
  * RuleBook
## 如何使用easy rule
* 如何用不同的方式创建rule
  * 不使用注解的形式(**_OO_**)
    * 添加依赖
    ```xml
    <dependency>
        <groupId>org.jeasy</groupId>
        <artifactId>easy-rules-core</artifactId>
        <version>4.1.0</version>
    </dependency>
    ```
    * 创建抽象类`BasicTestRule`继承easyRule中的`BasicRule`, 重载`evaluate()` & `execute()`方法，前者为了执行不同的rule匹配规则，后者为了自定义执行满足条件之后的操作，🌰 ：打印某些日志，或者改变原先的facts的值；在以下的例子中，是将传入的facts执行了`setter`
      ```java
      public abstract class BasicTestRule extends BasicRule {
    
         public BasicTestRule(final String name, final int priority) {
             super(name, DEFAULT_DESCRIPTION, priority);
         }
    
         protected abstract boolean hitCondition(final RuleResult creatureFacts);
         protected abstract String getType();
   
         @Override
         public final boolean evaluate(final Facts facts) {
             return hitCondition(facts.get(FACTS));
         }
   
         @Override
         public final void execute(final Facts facts) {
             RuleResult ruleResult = facts.get(FACTS);
             ruleResult.setResultType(String.format("The class is %s", getType()));
         }
      }
      ```
      * 在以上的例子中，`evaluate`函数是放置了一个**Condition**函数`hitCondition()`，一旦条件符合就会返回`TRUE`，表明该rule被匹配
    * 定义满足条件的抽象函数`hitCondition`，每个rule继承Basic方法的之后，实现`hitCondition`方法，以demo中的`PersonTestRule`中被重载的函数为例：
       ```java
       @Override
       protected boolean hitCondition(RuleResult creatureFacts) {
         return Objects.equals(creatureFacts.getClassType(), this.getClass().getSimpleName());
       }
       ```
      * 当传入的 facts的type和当前类名一致时，将会被被匹配上，并且在`BasicTestRule`，同样重载了easyRule提供的`execute()`，当rule被匹配之后，将会执行该函数。在`BasicTestRule`，提供了抽象方法`getType()`，在不同的子类型中会返回自己类的全称，然后被父类中`execute()`调用
      ```java
      @Override
      protected String getType() {
          return PersonTestRule.class.toString();
      }
      ```
  * annotation的形式
    * 给类定义Rule名称，该注解将会把该类标记成一个rule，标记之后可以用上述步骤在Rules中注册
    ```@Rule(name = "AnnotationTestRule", description = "using annotation to match rule")```
    * 定义一个`Condition`注解的方法，这里定义满足条件的标准
    ```java
    @Condition
     public boolean hitCondition(@Fact("facts") RuleResult ruleResult) {
      return Objects.equals(ruleResult.getClassType(), this.getClass().getSimpleName());
    }
    ```
    * 定义一个`Action`注解的方法，这里定义当条件满足时的要做的业务
    ```java
     @Action
     public void getType() {
      System.out.println(AnnotationTestRule.class);
    }
    ```
  * Expression的形式定义rule
    ```java
    Rule testRule = new MVELRule()
                .name("Test Rule")
                .description("if class Type is `test`")
                .when("ruleResult.classType == 'test'")
                .then("ruleResult.setResultType('test');");
    ```
    * 初始化`MVELRule`，定义rule的`name`，`description`，触发条件`when`，action `then`
    * tips，定义Facts时需要保持key value和表达式中操作对象的名称一致
      * ``` facts.put("ruleResult", ruleResult);```
  * fluent programmatic的形式定义rule
    ```java
    Rule rule = new RuleBuilder()
                  .description("test")
                  .name("checkAdult")
                  .when(condition -> {
                      RuleResult facts = condition.get("facts");
                      return facts.getClassType().equals("test");
                  })
                  .then(facts -> {
                      RuleResult ruleResult = facts.get("facts");
                      System.out.println("--------matches-------");
                      ruleResult.setResultType("test");
                  })
                  .build();
    ```
    * 创建`RuleBuilder`，以builder的形式定义了Rule的描述`description`，名称`name`，条件`when`以及满足条件之后需要执行的行为`then`
  * rule yml的形式定义rule
    * 定义一个yml文件，同样的，`MVELRuleFactory`也支持`json`格式的文件
    ```yaml
    ---
    name: non adult rule
    description: when age is less than or equal 18, then mark as non-adult
    priority: 1
    condition: "person.age <= 18"
    actions:
    - "person.setAdult(false);"
    ---
    name: adult rule
    description: when age is greater than 18, then mark as adult
    priority: 2
    condition: "person.age > 18"
    actions:
    - "person.setAdult(true);"
    ```
    * 加入依赖
    ```xml
     <dependency>
        <groupId>org.jeasy</groupId>
        <artifactId>easy-rules-mvel</artifactId>
        <version>4.1.0</version>
     </dependency>
    ```
    * 使用fileReader读取文件，构造到`MVELRuleFactory`中
      * 首先先定义一个MVELRule的Factory，入参是用来接受rule文件的解析格式，easyRule支持`yml`&`json`两种
      ```java
      private MVELRuleFactory ruleFactory = new MVELRuleFactory(new YamlRuleDefinitionReader());
      ```  
      * 如果是多条件的匹配，使用`createRules`，否则使用`createRule`
      ```java
      Rules multipleRules = ruleFactory.createRules(new InputStreamReader(resourceAsStream));
      ```
      * [MVEL语法](https://github.com/imona/tutorial/wiki/MVEL-Guide#foreach)
      * 如果遇到以下的错误 ```[Error: could not access field: org.zqf.easyruledemo.Person.age]```
        * 我的解决方法是将`Person`变成一个公有的方法 <strong style="color:yellow">如果不是公有的方法不能构造吗？</strong>
  * spring boot 项目，使用注解，注入rule
* 如何使用ruleEngine
  * 定义RuleEngine，初始化rule parameter参数
       ```java
       RulesEngineParameters parameters = new RulesEngineParameters()
                                        .skipOnFirstAppliedRule(true)
                                        .skipOnFirstFailedRule(false)
                                        .skipOnFirstNonTriggeredRule(false);
       ``` 
  * `setSkipOnFirstAppliedRule`，这个配置决定了是否匹配到第一个rule就直接返回了
  * `isSkipOnFirstNonTriggeredRule`，跳过第一个没有被触发的规则，<strong style="color:yellow">什么是没有触发的规则？</strong>
  * `skipOnFirstFailedRule`，当第一个匹配到的规则失败的时候，将会返回匹配到的下一个规则
  * 创建ruleEngine `DefaultRulesEngine rulesEngine = new DefaultRulesEngine(parameters);`
  * 定义Rule，并注册rule
    ```java
    Rules rules = new Rules();
    rules.register(new PersonTestRule());
    ```
  * 定义Facts
    ```java
    Facts facts = new Facts();
    RuleResult ruleResult = new RuleResult();
    ruleResult.setClassType(classType);
    facts.put(FACTS, ruleResult);
    ```
  * 最后定义一个通用的方法，使用RuleEngine的`fire`方法，来触发规则匹配
    `rulesEngine.fire(rules, facts);`
