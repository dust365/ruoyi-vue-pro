#!/bin/bash

#首先开始打包
#mvn clean package -Dmaven.test.skip=true

# 配置参数
REPO_URL="8.152.217.4:5000"        # 私有仓库地址
REPO_URL_LOCAL="localhost:5000"        # 私有仓库本地地址

IMAGE_NAME="admin-java-server"                # 镜像名称
CONTAINER_NAME="admin-container"                # 容器名称

IMAGE_TAG="latest"                 # 镜像标签
USERNAME="admin"                   # Docker 仓库用户名
PASSWORD="admin123"            # Docker 仓库密码
DOCKERFILE_PATH="./Dockerfile"     # Dockerfile 文件路径（默认当前目录）
#
# 步骤 1：登录到私有仓库
echo "Logging into Docker registry at $REPO_URL..."
echo "$PASSWORD" | docker login "$REPO_URL" -u "$USERNAME" --password-stdin
if [ $? -ne 0 ]; then
    echo "Login failed"
    exit 1
fi

# 步骤 2：构建镜像
echo "Building Docker image..."
cd yudao-server
pwd
docker build -t "$IMAGE_NAME:$IMAGE_TAG" -f "$DOCKERFILE_PATH" .
if [ $? -ne 0 ]; then
    echo "Docker image build failed"
    exit 1
fi

# 步骤 3：打标签
echo "Tagging Docker image..."
docker tag "$IMAGE_NAME:$IMAGE_TAG" "$REPO_URL/$IMAGE_NAME:$IMAGE_TAG"

# 步骤 4：推送到私有仓库
echo "Pushing Docker image to $REPO_URL..."
docker push "$REPO_URL/$IMAGE_NAME:$IMAGE_TAG"
if [ $? -ne 0 ]; then
    echo "Docker image push failed"
    exit 1
fi

echo "Docker image pushed successfully to $REPO_URL/$IMAGE_NAME:$IMAGE_TAG"

# 步骤 5：登录阿里云，使用阿里云docker ，下载上传的镜像
# 服务器信息
SERVER_IP="8.152.217.4"
USERNAME="root"
PASSWORD="v#b*UDkyjkqcwF5"
# 执行远程命令
sshpass -p "$PASSWORD" ssh -o StrictHostKeyChecking=no "$USERNAME@$SERVER_IP" << EOF
# 1.在远程服务器上执行的命令
echo "登录阿里云 successfully--> Remote command executed successfully"
echo " 阿里云 docker list ⬆⬇️"
docker images
echo " 阿里云 docker list ⬆️"


# 步骤 7 ：下载上传的镜像
echo " 下载镜像 "
docker pull "$REPO_URL_LOCAL$IMAGE_NAME:$IMAGE_TAG"

# 步骤 8 ：：删除可能启动的老 yudao-server 容器
echo "停止-----> $CONTAINER_NAME 容器"
docker stop $CONTAINER_NAME || true

docker rm $CONTAINER_NAME || true
echo "删除------> $CONTAINER_NAME 容器"

# 步骤 9 ：启动新的 yudao-server 容器
echo "开始启动 $CONTAINER_NAME 容器"
docker run -d --name $CONTAINER_NAME \
  -p 48080:48080 \
  -e SPRING_PROFILES_ACTIVE=dev \
  -v /work/projects/yudao-server:/root/logs/ \
  $REPO_URL_LOCAL/$IMAGE_NAME
echo "正在启动 yudao-server 容器中，需要等待 60 秒左右"


EOF
