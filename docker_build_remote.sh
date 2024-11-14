#!/bin/bash

# 配置参数
REPO_URL="8.152.217.4:5000"        # 私有仓库地址
REPO_URL_LOCAL="localhost:5000"        # 私有仓库本地地址 解决下载仓库报错 https 不支持的问题
IMAGE_NAME="admin-java-server"                # 镜像名称
CONTAINER_NAME="admin-container"                # 容器名称
IMAGE_TAG="v_0.0.8"                 # 镜像标签 latest
DOCKERFILE_PATH="./Dockerfile"     # Dockerfile 文件路径（默认当前目录）
SERVER_IP="8.152.217.4"       #服务器信息
USERNAME="root"                #服务器账号
PASSWORD="v#b*UDkyjkqcwF5"     #服务器密码
DEPLOY_DIR="/work/projects/yudao-server" #项目部署路径


echo "步骤 1：清理缓存 准备重新构建"
mvn clean
#mvn compile
#首先开始打包
mvn clean package -Dmaven.test.skip=true


echo "步骤 #2 rsync 同步 jar 和 Dockerfile 到线上服务器"
sshpass -p "$PASSWORD" rsync -avz --progress  --delete yudao-server/target/yudao-server.jar  $USERNAME@$SERVER_IP:$DEPLOY_DIR/target
sshpass -p "$PASSWORD" rsync -avz --delete yudao-server/Dockerfile  $USERNAME@$SERVER_IP:$DEPLOY_DIR
if [ $? -eq 0 ]; then
  echo "同步完成，文件已上传到服务器"
else
  echo "同步失败，请检查错误信息"
  exit 1
fi

echo "步骤 #3 线上服务器 执行 docker 相关命令 进行构建"
 #使用 sshpass 连接到远程服务器并执行命令
sshpass -p "$PASSWORD" ssh -t -o StrictHostKeyChecking=no "$USERNAME@$SERVER_IP" \
  "echo '登录阿里云 successfully--> Remote command executed successfully'; \
   echo '阿里云 docker list ⬆⬇️'; \
   docker images; \
   echo '阿里云 docker list ⬆️'; \
   echo '构建镜像 docker build  $IMAGE_NAME:$IMAGE_TAG'; \
   docker build --platform linux/amd64 -t '$IMAGE_NAME:$IMAGE_TAG' '$DEPLOY_DIR'; \
   if [ $? -ne 0 ]; then \
     echo '构建镜像失败'; \
     exit 1; \
   fi; \
   echo '停止-----> $CONTAINER_NAME 容器'; \
   docker stop $CONTAINER_NAME || true; \
   if [ $? -ne 0 ] && [ $? -ne 127 ]; then \
     echo '停止容器失败'; \
     exit 1; \
   fi; \
   echo '删除------> $CONTAINER_NAME 容器'; \
   docker rm $CONTAINER_NAME || true; \
   if [ $? -ne 0 ] && [ $? -ne 127 ]; then \
     echo '删除容器失败'; \
     exit 1; \
   fi; \
   echo '开始启动 $CONTAINER_NAME 容器'; \
   docker run -d --name $CONTAINER_NAME -p 48080:48080 -e SPRING_PROFILES_ACTIVE=dev -v /work/projects/yudao-server:/root/logs/  $IMAGE_NAME:$IMAGE_TAG; \
   if [ $? -ne 0 ]; then \
     echo '启动容器失败'; \
     exit 1; \
   fi; \
   echo '正在启动 yudao-server 容器中，需要等待 60 秒左右'; \
   sleep 60; \
   echo '容器已启动'"