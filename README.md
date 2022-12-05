### 创建自定义Maven插件 

[官方文档](https://maven.apache.org/guides/plugin/guide-java-plugin-development.html)

* 插件的命名方式
  * name-maven-plugin **一般是非Maven官方插件**
  * maven-name-plugin **一般是Maven官方插件**

* 添加依赖

```xml
<dependencies>
    <dependency>
        <groupId>org.apache.maven</groupId>
        <artifactId>maven-plugin-api</artifactId>
        <version>3.8.5</version>
    </dependency>
    <dependency>
        <groupId>org.apache.maven.plugin-tools</groupId>
        <artifactId>maven-plugin-annotations</artifactId>
        <version>3.6.4</version>
    </dependency>
</dependencies>
```

* 插件的目录结构

```shell
xyz.lglg.maven.plugin

# maven.plugin是属于约定成俗的
```

* 使用注解

```java
@Mojo(name = "annotation")
public class AnnotationMojo extends CustomAbstractMojo {
    @Override
    public void execute() throws MojoExecutionException, MojoFailureException {
        getLog().info("注解类型的插件");
    }
}
```

* 使用注释类型

```java
/**
 * @author jay
 * @goal document
 */
public class DocumentMojo extends CustomAbstractMojo {
    @Override
    public void execute() throws MojoExecutionException, MojoFailureException {
        getLog().info("文档注释类型的插件");
    }
}
```

* 定义自定义插件都需要继承抽象类`org.apache.maven.plugin.AbstractMojo`，并且实现它的`execute`方法

* 实现`execute`方法的类就算是一个插件的具体执行逻辑

* 运行`mvn install` 安装之后，就可以在本地测试使用

* 本地调用方式
  * 在本地的`seeting.xml`中注册 
  * 不注册全名称调用 

```shell
mvn groupId:artifactId:version:goal
mvn xyz.lglg.plugin:custom-maven-plugin:1.0.0:help

# 注册到setting.xml
<pluginGroups>
    <pluginGroup>xyz.lglg.plugin</pluginGroup>
</pluginGroups>
mvn mvn custom:help
```

* 在项目中使用

```xml
<build>
      <plugins>
          <plugin>
              <groupId>xyz.lglg.plugin</groupId>
              <artifactId>custom-maven-plugin</artifactId>
              <version>1.0.0</version>
              <executions>
                  <execution>
                      <id>test-jay</id>
                      <phase>clean</phase>
                      <goals>
                          <goal>annotation</goal>
                          <goal>document</goal>
                      </goals>
                  </execution>
                  <execution>
                      <id>test-query</id>
                      <phase>validate</phase>
                      <goals>
                          <goal>query</goal>
                      </goals>
                      <configuration>
                          <host>${host}</host>
                          <port>${port}</port>
                          <username>${username}</username>
                          <password>${password}</password>
                          <database>${database}</database>
                      </configuration>
                  </execution>

              </executions>
          </plugin>
      </plugins>
</build>
```



