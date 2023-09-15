## 简介
个人的**Java工具库**，无三方依赖，不耦合特定开发框架。自己也用主流工具集，所以也不去造重复的轮子。
<hr/>

## 模块

| 模块              | 说明         |
|-----------------|------------|
| wen-common-bean | 存放注解、实体等定义 |
| wen-common      | 工具包        |
| wen-common-test | 测试         |
               
<hr/>

## 组件

### [FieldFill](wen-common%2Fsrc%2Fmain%2Fjava%2Fio%2Fgithub%2Fvwenx%2Fcommon%2Ffieldfill) 字段填充工具
- 通过在实体字段上注解声明的规则，来处理字段的自动赋值。避免为了额外返回个别字段去写连表、穿插调用服务/转换枚举的代码。
- 常用场景：
  - 关联信息 如:用户的创建人字段存的是创建人的用户id，还需要给前端返回创建人的名称
  - 枚举信息 如:用户的性别、字典code与文本的映射 (虽然没有BFF层的后端不该做这些)
- [FieldFillTest.java 测试代码示例](wen-common-test%2Fsrc%2Fmain%2Fjava%2Fio%2Fgithub%2Fvwenx%2Fcommon%2Ftest%2Ffieldfill%2FFieldFillTest.java)
##### 使用
1. 实现并注册自己需要的填充器
   - 填充器实现相关包`io.github.vwenx.common.fieldfill.stuffer`
       - 填充器需要实现`FieldFillStuffer`接口，包内也提供了一些抽象封装和快捷构建的类
       - 子包`builder`提供了填充器的快捷构建支持，使用Lambda引用构建减少使用者编写class等冗长的结构性代码
       - 子包`impl`提供了一些基础填充器实现(目前有基于枚举的填充器)
   - 实现填充器后，使用`FieldFillStufferRegistry.register`方法注册
2. 在需要处理的字段上使用注解定义规则
    ```java
    class Demo{
        private int sex;
        // 使用枚举类型的填充器 将sex字段的值对应SexEnum的type，取出SexEnum的text填充到sexText字段上 
        @FieldFill(type = FieldFillType.Enum, source = "sex", classArgs = SexEnum.class, args = {"type", "text"})
        private String sexText;
        // 声明扫描此字段对象内部的填充信息
        @ScanFieldFill
        private List<Demo> subList;
    }
    ```
3. 调用字段填充助手来填充数据
   - `FieldFillHelper.fill(demoList);`
   - `FieldFillHelper`将会扫描数据字段的`@FieldFill`注解信息来收集需要填充的任务，根据任务类型分组来调用对应注册的填充器实现。

##### 集成
- 可自由集成到拦截器/AOP，业务开发只需要在字段中定义注解，就能自动完成字段填充，保持主逻辑代码的干净。

##### 提示
- 源码注释还是很清晰的，测试包里的示例也很简洁且完整。
- 填充助手会按类型，将同类任务聚合一次交给填充器，可以使用批量查询来优化时间。(如: 数据库查询、服务调用等RPC场景)
- 如实体继承中重复定义了同名字段，解析器会选择抛出异常。
- 涉及对象嵌套的数据结构，只有在字段上定义了`@ScanFieldFill`注解，才会向下一层去扫描填充信息。

<hr/>