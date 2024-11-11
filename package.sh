#!/bin/bash

#在项目的根目录下，执行 进行自动化打包
# mvn clean package -Dmaven.test.skip=true
# 命令，编译后端项目，构建出它的 Jar 包
#mvn clean package -Dmaven.test.skip=true

# 预计等1分钟 出现 BUILD SUCCESS  即代表编译成功




# 执行如下命令，构建名字为 yudao-server 的 Docker 镜像。
# shellcheck disable=SC2164
cd yudao-server
docker build -t yudao-server

#scp  -r /Users/allenpandas/datasets/ root@192.168.0.2:/root/autodl-tmp/

 //"registry-mirrors": ["https://docker.1ms.run"]
