#!/bin/bash


echo "docker 运行本地包"
#在项目的根目录下，执行 进行自动化打包
# mvn clean package -Dmaven.test.skip=true
# 命令，编译后端项目，构建出它的 Jar 包
#mvn clean package -Dmaven.test.skip=true

# 预计等1分钟 出现 BUILD SUCCESS  即代表编译成功
IMAGE_NAME="admin-java-server"                # 镜像名称
CONTAINER_NAME="admin-container"                # 容器名称
IMAGE_TAG="v_0.0.3"                 # 镜像标签
DOCKERFILE_PATH="./Dockerfile"     # Dockerfile 文件路径（默认当前目录）

# 步骤 2：构建镜像
echo "Building Docker image..."
cd yudao-server
pwd
docker build -t "$IMAGE_NAME:$IMAGE_TAG" -f "$DOCKERFILE_PATH" .
if [ $? -ne 0 ]; then
    echo "Docker image build failed"
    exit 1
fi
echo "images 构建完成---Docker image build SUCCESS "

# 步骤 3：运行到本地
echo "Building Docker image..."

#docker run -d --name $CONTAINER_NAME
#  -p 48080:48080
#  -e SPRING_PROFILES_ACTIVE=dev
##  -v /yudao-server:/root/logs/ \
#  $IMAGE_NAME
#echo "正在启动 yudao-server 容器中，需要等待 60 秒左右"
#

docker run -d --name $CONTAINER_NAME \
  -p 48080:48080 \
  -e SPRING_PROFILES_ACTIVE=dev \
  $IMAGE_NAME:$IMAGE_TAG
echo "正在启动 yudao-server 容器中，需要等待 60 秒左右"
