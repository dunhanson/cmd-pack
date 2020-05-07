# Cmd-Pack

Java Maven项目Git打包工具

Jenkins部署太慢？需要快速打一个增量包，没错就用它吧！

工作原理：读取Git日志获取文件路径，匹配项目路径下对应的编译后的文件，进行复制和打包。



## 快速开始

```powershell
java -jar cmd-pack.jar
```



## 配置文件

cmd-pack.yaml

```yaml
basic:
  projects:
    - name: bxkc
      start: false
      source: D:\Document\Company\code\bxkc-pc
      output: D:\Test\bxkc\bxkc-pc
    - name: bxkc
      start: true
      source: D:\Document\Company\code\bxkc-api
      output: D:\Test\bxkc\bxkc-api
  git:
    author: dunhanson
    after: '2020-05-01 00:00:00'
    before: '2020-05-08 00:00:00'
```

说明：

projects:

* name 项目名称 必填 pom.xml中配置的build->finalName
* start 是否启用
* source 项目路径 必填 Git Maven项目路径
* output 输出路径 必填 打包后输出的路径，会生成目录和压缩包

git:

* author 作者 可选 默认所有
* after 开始时间 可选 默认当前时间，但是零时、零分、零秒（XXXX-XX-XX 00:00:00）
* before 结束时间 可选 默认当前时间



## 待完善功能

* 多项目支持，并添加start:true/false字段 ``已完成``



## 问题说明

禁止路径中包含空格



## JAR文件

[cmd_pack_jar.zip](http://bxkc.oss-cn-shanghai.aliyuncs.com/software/cmd_pack_jar.zip)

文件清单：

* cmd-pack.bat
* cmd-pack.jar
* cmd-pack.yaml