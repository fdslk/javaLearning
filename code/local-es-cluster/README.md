# Elastic Search 基础知识

## 基本概念
### es是什么
* es是一个分布式文件存储系统，意味着es可以拥有多个节点 **<span style="color:yellow">节点间如何同步数据？</span>**
* 各种复杂的数据都会以json的格式进行序列化，然后进行存储
* 使用反向索引的方式来存储数据，这个索引中存储了整个文档中所有的去重之后的词条  **<span style="color:yellow">什么是inverted-index？</span>**
* 在es中，不同类型数据会使用不同的存储结构，比如说，文本会用`inverted-index`方式存储，而数字则会使用`KDB tree`来储存，这样不同的索引方式使得es能够很快的查询到数据
### es如何查询和分析数据
#### 查询
* es并不是一个凭空而产生的的一个搜索引擎，在他的底层使用是Apache的Lucene搜索引擎 **<span style="color:yellow">什么是Apache Lucene？</span>**
* 对于一些简单的请求，ES提供了RESTFUL-api，可以使用command line模式或者kibana的console来submit `index` 和 `search`的请求；对于更多的复杂场景，es还开发基于各种编程语言的客户端
* es支持的查询方式
  * 结构化查询，将查询的语句SQL化 🌰：
  * 全文本查询，es将会通过输入的词条，查询全部的document，然后再根据relevance排序将数据返回 🌰：
#### 分析
* 所谓的分析数据，其实就是在聚合数据，🌰：在某个人群中的女性占比多少，某个人群中的某个年龄段的女性占比多少，某个人群中的某个职业的女性占比多少
### es的高可用（扩展&弹性收缩）
* es本身就是一个分布式的文档储存工具，随着数量的增加，我们可以通过增加节点数的方式来扩展每个es cluster的性能
* es的索引其实是一个或者多个的物理分片的集合，在储存的过程中会把一个文档的索引储存在多个分片上，而这些分片又会被储存在不同的节点上。而每个分片都有一个主从，当为主的储存片区出现问题时，就使用从片区。
* 使用`CCR`来保证主从数据备份 **<span style="color:yellow">什么是CCR(Cross-cluster replication)？</span>**

## 如何使用
### 支持的系统
* linux & macOS: 使用tar.gz
* windows: 使用zip
* Debian, Ubuntu, and other Debian-based systems: deb
* rpm
* docker
### 配置
* 配置文件：elasticsearch.yml, jvm.options, log4j2.properties
* 设置的种类: persistent, transient, elasticsearch.yml, 默认值 <span style="color:red">上述顺序也是更新顺序</span>
* 具体的配置类型
  * 最主要的配置类型
    * Path settings 
    * Cluster name setting
    * Node name setting
    * Network host settings
    * Discovery settings
    * Heap size settings
    * JVM heap dump path setting
    * GC logging settings
    * Temporary directory settings
    * JVM fatal error log setting
    * Cluster backups
  * ES配置中敏感数据：elasticsearch-keystore
  * 审核配置：根据我的理解，这个应该是配置，通过API访问es时是否需要提供用户名和密码
  * 断路器配置：防止内存溢出
  * 基于分片的分配以及路由设置
  * 跨集群复制配置
  * 发现&集群信息设置
  * 字段&数据缓存设置
  * health-check设置
  * index生命周期设置
  * index management settings(索引管理配置)
  * 机器学习配置
  * 节点设置，（多个节点构成了一个cluster，cluster中的节点可以通过http或者transport traffic协议）
  * 网络设置，用户访问es使用http协议，节点之间使用transport traffic的协议
  * 节点查询缓设置，存放每个节点查询 **<span style="color:yellow">不确定是缓存查询条件还是查询条件查询到的内容？</span>**
  * 查询设置，🌰：查询最大数量
  * 查询通知设置 (*Watcher settings*)
### 升级
* 老版本的indices的兼容性考虑，🌰：6.x版本的es的indices和8.x版本的升级，可能先要将原有的indices删除掉，在使用新的
  * 当es的版本升级之后，需要检查在新版本中是否有即将`archived`的功能，
    * ```GET _cluster/settings?flat_settings=true&filter_path=persistent.archived*```, 如果返回结果是空的，表示没有`archived`节点设置
    * `GET */_settings?flat_settings=true&filter_path=**.settings.archived*`，检查是否有`archived`的index设置
* REST-API的迁移，是一个break changing, <span style="color:yellow"><strong>两种版本的restful接口有什么区别?</strong></span>
* JVM版本的升级，高版本的es需要java 17及以上的版本
* es提供了`archive functionality`，该功能可以把老版本的indices导入到新版本的es中 <strong><span style="color:yellow">How to?</strong>
### index(索引)模块，每一个小节都可以被当做是一个`module`
* 分为静态和动态设置，<strong style="color:yellow">这两种设置之间的区别是什么？</strong>
* 分析，在index module中该模块将用来转换一个字符串为一个独立的词条，
  * 而这些词条将会被加入到inverted-index中，这样是为了这个文档可以被搜索 <strong style="color:yellow">How to?</strong>
  * 被用来进行high-level-queries，使用`match` query来产生搜索词条
* 索引片区分配模块
  * 分配片区所属的node，`Shard allocation filtering`
    * conditions包括，require，exclude，include
    * 可以用被用来做筛选的字段是：`_name, _host_ip, _publish_ip, _ip, _host, _id, _tier and _tier_preference`
  * 节点延迟分配设置，`Delay allocation`，当集群上的某个节点离群之后，分配给这个节点的片区资源将会被重新分配，但是如果shard的`delay`时间，那么这些shard就可能在当前离线node在超时前恢复，重新分配给这个node，减少了一定的操作开销
   ```
   PUT _all/_settings
   {
     "settings": {
      "index.unassigned.node_left.delayed_timeout": "5m"
      }
   }
   ```
  * 每个node总共分配的片区，`Total shards per node`
  * 数据层配置，`Data tier allocation` <strong style="color:yellow">what is the `data tiers` ?</strong>
* 索引块，可以用来控制es index的可被使用的操作，读，写，metadata操作 <strong style="color:yellow">应用场景是啥?</strong>
* 分片合并，在es中所有的shard都是一个Lucene索引，那么这些个碎片在一定时间内都是可以被合并成一个大的碎片，进而被继续使用 <strong style="color:yellow">这里感觉有点儿像储存解空间的碎片回收?</strong>
* 相似性模块，每个field都有一个相似性评分，该评分是用来评价查询回来的document的匹配度，通过es提供的接口，我们可以设置不同的相似性计算算法，
* 慢查询日志，可以设置查询时间超过多少秒就是一个不同级别的日志，🌰 ：`index.search.slowlog.threshold.query.warn: 10s`，某个查询的执行时间超过了10s, 就会被记录成一个warning级别的日志，该配置使用`log4j2.properties`
* 储存方式 `index.store.type:`，该配置可以使用config/elasticserch.yml，也可以使用es提供给的settings api更新
* transaction log简称为`tranlog`，这些日志只要在一个Lucene的一次commit时才会记录日志， <strong style="color:yellow">什么是一次Lucene commit?</strong>
* history retention
* index sort 一个新建的index，Lucene的不会创建任何的排序规则，需要es提供的restful api改变sort规则
* indexing pressure <strong style="color:yellow">分散集群的压力的？</strong>

### mapping
* 每一个数据被es储存的时候，field按照不同的类型储存，而field具有不同的property，比如说`_source`，可以修改这个属性的储存格式，使得es的search更加的快
* 类别
  * <strong style="color:yellow">dynamic?</strong> 
    * 可以自定义一个mapping，以下例子的含义是，在data index中，定义一个mapping type `_doc`，字段名称为`count`，数据类型为`long`
    ```
     PUT data/_doc/1 
     { "count": 5 }****
    ```
    * es中有一个参数`dynamic`来控制是否会自动的创建一个类型mapping，简而言之，dynamic mapping就是会给新加入的数据自动匹配到一种类型来存储在es中，<strong style="color:yellow">如果没有找到对应的数据类型，是否会使用默认类型？貌似默认类型就是`text`</strong>
    * 除了es自己提供的一些mapping template之外，es还提供了接口让我们来自定义mapping template
      * 类型
        * 匹配的类型
        * 匹配和未匹配到的过滤条件
        * 某个字段的路径匹配规则
  * explicit
    * 定义mapping的定义，🌰： 比如说哪个字符串字段应该被当做是一个全文本字段。。。，静态mapping以我现在的理解，有点像是一个字段的对应的关系
    * 以以下的命令为一个例子，该命令的作用是在创建一个index的同时，定义一个mapping关系，只有当你储存一个数据，该数据的field是`age`，该field的值将会以`integer`的类型来储存
    ```
     PUT /my-index-000001
     {
       "mappings": {
           "properties": {
           "age":    { "type": "integer" },  
           "email":  { "type": "keyword"  },
           "name":   { "type": "text"  }     
           }
        }
     }
    ```
  * 区别，`dynamic`不需要指定具体的field名称，`explicit`需要指定具体的字段名称，再根据字段的名称来存储字段内容的类型
* runtime field, 运行时字段，在添加时，不会添加新的索引，不用定义任何的数据匹配关系
  * 好处，可以在不改变原有值的情况下，定义runtime的mapping关系，然后再通过新定义的mapping的字段的condition过滤出符合条件的values
  * 需要均衡的地方
  * runtime依旧是mapping的一种，但是这种方式需要定义一个`runtime`的`mapping`，然后再在这个mapping中定义一个`plainless script`来操作对应字段数据的值
  * runtime的mapping的优先级是高于index mapping，所以如果你在你的index中定义一个runtime的mapping，在查询的时候会覆盖之前的存入值的类型，<strong style="color:yellow">不确定是否会改变之前已存入的数据的值，还是只是在查询时的临时的改变？</strong>
  * 复写字段值在查询的时候，但是不会改变原有值，只会对匹配上的值做一些**计算**或者**类型转换**诸如此类的操作
* field数据类型
  * 基础类型，binary, boolean, Keywords, Numbers, Dates, alias
  * 对象&关系类型，object，flatten，nested，join
  * 结构体数据类型，Range，ip，version，murmur3
  * 聚合数据类型，aggregate_metric_double，histogram
  * 文本搜索类型，text fields, annotated-text, completion, search_as_you_type, token_count
  * 文档排序类型，dense_type, rank_feature, rank_features
  * 空间数据类型，geo_type, geo_shape, point, shape
* Metadata fields
* 提供参数，这些参数主要作用于值类型的数据类型，但是这是参数，并不是适应于每个数据类型，举个🌰 ：对于`analyer`参数，只适应于`text`类型的数据
* mapping限制设置
  * index对应的所有的字段的最大数量
  * index对应的mapping的最大深度
* 8.0以上的es不在支持mapping type，
## text analysis（文字分析）
* 这个feature是用来处理非结构化数据，将非结构化数据处理成结构化的数据（这样的结构化数据是被优化的，利于search的结构）<font color="yellow" size=3>**什么样的数据时非结构化的数据，将源数据做了处理，什么样的数据会被当做非结构化的数据**</font>
* 对于这个功能，只要在用户搜索`text`类型的数据时，才会被触发
* 在text analysis的过程中，es做了两件事 
  * tokenization，将一个存入的string index分解成多个词，这样，用不同的组合搜索都能够搜索到匹配的词条
  * normalization，有些词的大小写不一样，或者存入的词是单数，但是实际上查询的是负数，还有就是同义词的匹配
    * 那么在存储的时候就会做一些统一化的处理
      * 词语都做lowercase的处理，来保持一致
      * 有单数，也有负数的时候，统一将index设置为单数来处理
      * 对于同义词，用一个有语义相似的index进行统一处理
  * 以上两种操作都是es的内置操作，如果想使用一些个性化的操作，可以通过配置`build-in-analyzer`来实现
* 基本概念
  * 对于所有的analyzer都是由是哪个较低level的内置模块组成
    * character filters（optional），将非通用的字符转换成具有同等含义的通用字符，比如将`Hindu-Arabic`转换成`Arabic-Latin`，还可以过滤一些有其他意义的特殊字符
    * tokenizers（required），将一个phrase拆分成一个个的单词
    * token filters （optional），将词条进行lowercase处理，remove stop word，etc
## Index templates
* 用来定义数据存储的格式
* index模板分为，index templates&component templates
  * index template: 就和通俗易懂，该template使用的的范围主要是当前的index，，index中可以使用多个component template，添加优先级，这个功能主要用于如果匹配到几个相同的template时，可以根据优先级来定使用哪个
  * component template：类似于组件，并不属于哪一个特定的template
## data streams
* 