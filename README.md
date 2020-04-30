# Cmd-Pack

Java Maven项目Git打包工具



## 快速开始

```powershell
java -jar cmd-pack.jar
```



## 配置文件

cmd-pack.yaml

```yaml
basic:
  project:
    name: bxkc
    source: "E:\\Programming\\Code\\Company\\bxkc-pc"
    output: "D:\\Test\\bxkc"
  git:
    author: dunhanson
    after: "2020-04-01 00:00:00"
    before: "2020-04-18 00:00:00"
```

说明：

project:

* name 项目名称 必填 pom.xml中配置的artifactId
* source 项目路径 必填 Git Maven项目路径
* output 输出路径 必填 打包后输出的路径，会生成目录和压缩包

git:

* author 作者 可选
* after 开始时间 可选 默认当前时间，但是零时、零分、零秒（XXXX-XX-XX 00:00:00）
* before 结束时间 可选 默认当前时间



## JAR文件

[cmd_pack_jar.zip](http://bxkc.oss-cn-shanghai.aliyuncs.com/software/cmd_pack_jar.zip)

文件清单：

* cmd-pack.bat
* cmd-pack.jar
* cmd-pack.yaml