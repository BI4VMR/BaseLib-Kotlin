# 简介

Kotlin基础工具库，包含文本解析、加解密、编解码等常用工具。

# 使用方法

当我们进行测试时，可以在项目中依赖 `ALL` 模块，同时引用所有子模块。

```xml
<!-- 添加"ALL"模块的依赖声明 -->
<dependency>
    <groupId>net.bi4vmr.tool.kotlin</groupId>
    <artifactId>all</artifactId>
    <version>1.0.0</version>
</dependency>
```

<!-- Hide
尽可能按照功能划分模块，避免引入目标功能的同时引入其他无用依赖的问题。
合理复用，对于简单的功能（字符串判空等），每个模块自行实现，不要复用其他模块，避免引入过多依赖。
-->
