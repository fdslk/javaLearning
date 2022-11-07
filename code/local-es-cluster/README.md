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
### 升级
* 老版本的indices的兼容性考虑，🌰：6.x版本的es的indices和8.x版本的升级，可能先要将原有的indices删除掉，在使用新的
  * 当es的版本升级之后，需要检查在新版本中是否有即将`archived`的功能，
    * ```GET _cluster/settings?flat_settings=true&filter_path=persistent.archived*```, 如果返回结果是空的，表示没有`archived`节点设置
    * `GET */_settings?flat_settings=true&filter_path=**.settings.archived*`，检查是否有`archived`的index设置
* REST-API的迁移，是一个break changing, <span style="color:yellow"><strong>两种版本的restful接口有什么区别?</strong></span>
* JVM版本的升级，高版本的es需要java 17及以上的版本
* es提供了`archive functionality`，该功能可以把老版本的indices导入到新版本的es中 <strong><span style="color:yellow">How to?</strong>
