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
* 
