# 优雅的实现策略

[阅读本文大约10min](#)

## 什么是easy rule
* [easy rule](https://github.com/j-easy/easy-rules) 是一个rule engine工具，用来代替复杂的`if` `else` block，在代码中使得业务能够得到更好的分离，从而让代码能够更好的使用策略模式来分离业务
* `easy rule`除了支持java以外还支持，`Go`，`Groovy`，`C#` 
* 更多关于rule engine的details，给大家推荐一篇老马的文章[RulesEngine](https://martinfowler.com/bliki/RulesEngine.html)
* 与之相同的还有很多其他的java language的rule engine
  * [Drool](https://www.drools.org/)
  * [OpenL Tablets](https://openl-tablets.org/)
  * [RuleBook](https://github.com/deliveredtechnologies/rulebook)
## 如何使用easy rule
* <font size=4>**如何用不同的方式创建rule**</font>
  * <font size=3>**以面向对象的形式(**_OO_**)**</font>
    * 添加依赖，根据项目而定，`maven`和`gradle`都支持，在本文中使用maven
    ```xml
    <dependency>
        <groupId>org.jeasy</groupId>
        <artifactId>easy-rules-core</artifactId>
        <version>4.1.0</version>
    </dependency>
    ```
    * 创建抽象类`BasicTestRule`继承easyRule中的`BasicRule`, 重载`evaluate()` & `execute()`方法，前者为了执行不同的rule匹配规则，后者为了自定义执行满足条件之后的操作，🌰 ：打印某些日志，或者改变原先的facts的值；在以下的例子中，将对传入的facts与规则匹配上的时候在重载的`execute()`方法中执行了`setter`
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
      * 当传入的 facts的type和当前类名一致时，将会被匹配上，并且在`BasicTestRule`中，同样重载了easyRule提供的`execute()`，当rule被匹配之后，将会执行该函数。在父类`BasicTestRule`中，提供了抽象方法`getType()`，在不同的子类型中会返回自己类的全称，然后被父类中`execute()`调用
      ```java
      @Override
      protected String getType() {
          return PersonTestRule.class.toString();
      }
      ```
  * <font size=3>**annotation的形式**</font>
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
  * <font size=3>**Expression的形式定义rule**</font>
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
  * <font size=3>**fluent programmatic的形式定义rule**</font>
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
  * <font size=3>**rule yml的形式定义rule**</font>
    * 定义一个yml文件，同样的，`MVELRuleFactory`也支持`json`格式的文件
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
      * 如果在一个yml文件中定义了多个rule，使用`createRules`，否则使用`createRule`
      ```java
      Rules multipleRules = ruleFactory.createRules(new InputStreamReader(resourceAsStream));
      ```
      * [MVEL语法](https://github.com/imona/tutorial/wiki/MVEL-Guide#foreach)
      * 如果遇到以下的错误 ```[Error: could not access field: org.zqf.easyruledemo.Person.age]```
        * 我的解决方法是将`Person`变成一个公有的方法 <strong style="color:green">原因是我当时第一次写的时候，是将`Person`定义在了当前的测试类中，所以当前class的级别为`default`，使用的范围是在当前包下。但是当easy rule的要使用person类中的方法时，因为没有在同一个包下，所以访问不了。</strong>
    * 第一个🌰 是在一个yml文件中定义了多个规则，但是每一个规则都是一个简单规则，使用`---`分隔
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
    * <font size=2 color=red>**支持组合规则，适用于较为复杂的场景**</font>
      * `UnitRuleGroup`，在yml中定义`compositeRuleType`为`UnitRuleGroup`，被定义的规则需要都被匹配上才能被选择上，如果有一个**子规则**没有匹配上，那么结果仍然不会被匹配，这些规则具有<strong style="color:green">原子性</strong>
        ```yaml
        name: adult check composing rule
        ompositeRuleType: UnitRuleGroup
        riority: 1
        omposingRules:
         name: adult
         description: If the age is more than 18
         priority: 1
         condition: "person.age > 18"
         actions:
         - "person.setAdult(true);"
         name: foo-adult
         description: If the name is 'foo'
         priority: 1
         condition: "person.name == 'foo'"
         actions:
         - "person.setAdult(true);"
        ```
      * `ConditionalRuleGroup`，举个🌰：这种group类似于一种组合拳，有一个规则必须先执行，然后再看有没有其他的规则能够匹配上，就像登录一样，验证通过之后，再去做一些其他的操作
        ```yaml
         name: adult check Condiftional rule Group
         compositeRuleType: ConditionalRuleGroup
         priority: 1
         composingRules:
         - name: adult
           description: If the age is more than 18
           priority: 1
           condition: "person.age > 18"
           actions:
           - "person.setAdult(true);"
         - name: foo-adult
           description: If the name is 'foo'
           priority: 2
           condition: "person.name == 'foo'"
           actions:
           - "person.setAdult(true);"
         
        ```
        * tips，`ConditionalRuleGroup`将会检查`composingRules`中的优先级，其中只允许有一个`priority=1`的**子规则**
      * `ActivationRuleGroup`，只会有一个子规则匹配上，那么就会忽略其他的规则，直接执行这个被选择上的规则
        ```yaml
        name: adult check Condiftional rule Group
         compositeRuleType: ActivationRuleGroup
         priority: 1
         composingRules:
         - name: adult
           description: If the age is more than 18
           priority: 1
           condition: "person.age > 18"
           actions:
           - "person.setAdult(true);"
         - name: foo-adult
           description: If the name is 'foo'
           priority: 2
           condition: "person.name == 'foo'"
           actions:
           - "person.setAdult(true);"
        ```
  * <font size=3>**对于`spring boot`项目，可以使用注解的方式，注入Rules**</font>
    * 定义一个Rule的configuration class，对该类使用注解`@Configuration`，spring boot的bean容器就会在启动的时候，将当前父类`BasicVegetableRule`下所有子类都加载到入参`basicVegetableRules`中，当然如果想要使用java的bean的便利，那么对于需要加载的子类也需要加上注解`@Component`。这样在spring boot项目启动的时候就会将其扫描到这个`Set`中。`Rule`是一个<strong style='color:RED'>interface</strong>，所以所有的实现都可以转成其本身。
    ```java
     @Configuration
     public class RuleBeans {
     @Bean("BasicVegetableRule")
     public Rules BasicVegetableRules(final Set<BasicVegetableRule> basicVegetableRules) {
        return new Rules(basicVegetableRules.stream().map(Rule.class::cast).collect(Collectors.toSet()));
       }
     }
    ```
    ```java
    @Component
    public class PotatoRule extends BasicVegetableRule {
       xxxxx;
    }
    ```
    * 在spring boot项目中，有一些不同的业务场景，可能需要用到大量的`if else`来完成不同的业务场景。将这些Component利用Java bean加载到Set对象中，将其构造到`Rules`，进而可以直接传入ruleEngine中完成规则匹配
* <font size=4>**如何使用ruleEngine**</font>
  * 定义RuleEngine，初始化rule parameter参数
       ```java
       RulesEngineParameters parameters = new RulesEngineParameters()
                                        .skipOnFirstAppliedRule(true)
                                        .skipOnFirstFailedRule(false)
                                        .skipOnFirstNonTriggeredRule(false);
       ``` 
    * `setSkipOnFirstAppliedRule`，这个配置决定了是否匹配到第一个rule就直接返回了
    * `isSkipOnFirstNonTriggeredRule`，当第一个规则没有被匹配成功的时候，如果当前值设置为`true`的时候，将不会执行其他的规则，一般可以设置为`false`，我理解这个是给最高优先级的规则匹配，如果第一个匹配不上，不用关心其他的规则
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

## 优缺点
* rule engine可以将复杂的业务解耦，使每个类的职责更单一，方便维护与后期的扩展
* 对于较为简单的规则，因为rule的优先级不同，以及被分散在每一个类中，这样可能不如使用`if else`直观，需要有足够的上下文，才能将每个rule联系起来

## Q&A
* 如果两个rule的优先级相同，rule在Rules的顺序是什么呢？
  * **answer**：当两个rule的优先级相同时，匹配rule的顺序会使用rule的注册顺序，反之使用优先级(priority)排序

## Demo
* [非spring boot](https://github.com/Fdslk/javaLearning/tree/master/code/easyRule)
* [spring boot](https://github.com/Fdslk/javaLearning/tree/master/code/easyruledemo)