# 拉钩 java 高薪 第一阶段 第一模块 作业

## 编程题
> 本程序无需再安装任何其他数据库软件就可以直接运行

验证方式：运行 IPersistenceTest 的 T001.test2()

为了避免程序对 MySql 的依赖，特将数据库切换为 Sqlite
- 在 IPersistenceTest 的 pom.xml 中加入了 sqlite-jdbc 依赖
- 修改了 IPersistenceTest 的 sqlMapConfig.xml 中的 datasource 配置
- 修改了 IPersistence 的 XMLConfigBuilder 中关于数据源的配置
- 删掉了 IPersistence 的 pom.xml 中对 mysql 的依赖

作业讲解见 
[作业讲解.md](https://github.com/yanghonghao/task1-1/blob/master/%E4%BD%9C%E4%B8%9A%E8%AE%B2%E8%A7%A3.md)

## 简答题
### 1、Mybatis动态sql是做什么的？都有哪些动态sql？简述一下动态sql的执行原理？
答1：在进行 sql 操作时，动态的将属性拼接到 sql 语句中

答2：if where foreach include set choose when otherwise trim

答3：
- 初始化时：将解析到的 sql 语句封装为 boundSql 对象
- 执行时：sqlSession 执行的 sql 操作最终会交给 StatementHandler 来执行；
在 StatementHandler 执行 sql 前会根据 boundSql 创建 preparedStatement 对象，
并使用 boundSql 对象对 preparedStatement 对象进行参数化赋值；
最后 StatementHandler 使用 preparedStatement 对象执行真正的 sql 操作。

### 2、Mybatis是否支持延迟加载？如果支持，它的实现原理是什么？
答1：支持；association、collection具备延迟加载功能
答2：创建目标对象的代理对象，当调用目标方法时进行拦截，在检测到缺少关联对象后，在拦截方法中将事先保存好的关联对象赋值给目标对象，随后继续执行

### 3、Mybatis都有哪些Executor执行器？它们之间的区别是什么？
SimpleExecutor : 每次执行 sql 操作，都会开启新的 statement 对象，用完后会立刻关闭
ReuseExecutor : 以 sql 作为 key 存储 statement 对象，每次执行 sql 操作，会先判断该 sql 对应的 statement 是否已存在，
存在就使用，不存在就创建，用完后不关闭 statement 对象，而是存起来。以便重复使用
BatchExecutor : 执行 update 操作时，操作先存起来，等待批处理
CachingExecutor : 执行 query 操作时，先在二级缓存中查找；执行 update 操作时，清空二级缓存；
另外CachingExecutor 只是为了支持二级缓存功能的一个装饰器，实际的 sql 操作可以交给其他三个执行器执行。

### 4、简述下Mybatis的一级、二级缓存（分别从存储结构、范围、失效场景。三个方面来作答）？
一级缓存
- 存储结构：HashMap
- 作用范围：sqlSession
- 失效场景：sqlSession不同；查询条件不同；sqlSession 对象执行了 update 操作；sqlSession 对象手动清理了一级缓存

二级缓存
- 存储结构：HashMap
- 作用范围：mapper
- 失效场景：mapper不同；查询条件不同；mapper 对象执行了 update 操作；mapper 对象手动清理了一级缓存


### 5、简述Mybatis的插件运行原理，以及如何编写一个插件？
答1：Mybatis采用责任链模式，通过动态代理织入多个插件（拦截器），通过这些插件可以改变Mybatis的默认行为
答2：实现Mybatis的Interceptor接口并复写intercept()方法，然后在给插件编写注解，指定要拦截哪一个接口的哪些方法，最后在配置文件中配置编写好的插件。

