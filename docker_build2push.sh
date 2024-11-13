#!/bin/bash


# 配置参数
REPO_URL="8.152.217.4:5000"        # 私有仓库地址
REPO_URL_LOCAL="localhost:5000"        # 私有仓库本地地址 解决下载仓库报错 https 不支持的问题
IMAGE_NAME="admin-java-server"                # 镜像名称
CONTAINER_NAME="admin-container"                # 容器名称
IMAGE_TAG="v_0.0.7"                 # 镜像标签 latest
USERNAME="admin"                   # Docker 仓库用户名
PASSWORD="admin123"            # Docker 仓库密码
DOCKERFILE_PATH="./Dockerfile"     # Dockerfile 文件路径（默认当前目录）
#

#步骤 1：清理缓存 准备重新构建
echo "步骤 1：清理缓存 准备重新构建"
#mvn clean
#mvn compile
#首先开始打包
mvn clean package -Dmaven.test.skip=true



# 步骤 2：本地构建镜像
echo "步骤 2：本地构建镜像 Building Docker image..."
cd yudao-server
pwd
docker build --platform linux/amd64 -t "$REPO_URL/$IMAGE_NAME:$IMAGE_TAG" -f "$DOCKERFILE_PATH" .
if [ $? -ne 0 ]; then
    echo "Docker image build failed"
    exit 1
fi
 echo "Docker image build Success-------------->>>>>>>>>"

## 打标签
#echo "Tagging Docker image..."
#docker tag "$IMAGE_NAME:$IMAGE_TAG" "$REPO_URL/$IMAGE_NAME:$IMAGE_TAG"

 #步骤 2.1：登录到私有仓库
echo "Logging into Docker registry at $REPO_URL..."
echo "$PASSWORD" | docker login "$REPO_URL" -u "$USERNAME" --password-stdin
if [ $? -ne 0 ]; then
    echo "Login failed"
    exit 1
fi

# 步骤 2.2：标记本地镜像为目标仓库格式
# 假设本地镜像是 "your-image-name:latest"，你需要将它标记为仓库的正确格式
#echo "Tagging local image $IMAGE_NAME:$IMAGE_TAG as $REPO_URL/$IMAGE_NAME:$IMAGE_TAG..."
#docker tag "$IMAGE_NAME:$IMAGE_TAG" "$REPO_URL/$IMAGE_NAME:$IMAGE_TAG"
## 检查镜像是否标记成功
#if [ $? -ne 0 ]; then
#    echo "Docker image tagging failed"
#    exit 1
#fi


# 步骤 3：推送到私有仓库
echo "步骤 3：推送到私有仓库---> $REPO_URL..."
docker push "$REPO_URL/$IMAGE_NAME:$IMAGE_TAG"
if [ $? -ne 0 ]; then
    echo "Docker image push failed"
    exit 1
fi

echo "Docker image pushed successfully to $REPO_URL/$IMAGE_NAME:$IMAGE_TAG"

# 步骤 4：登录阿里云，使用阿里云docker ，下载上传的镜像
echo "步骤 4：登录阿里云，从 $REPO_URL 下载镜像."
# 服务器信息
SERVER_IP="8.152.217.4"
USERNAME="root"
PASSWORD="v#b*UDkyjkqcwF5"
# 执行远程命令

sshpass -p "$PASSWORD" ssh -t -o StrictHostKeyChecking=no "$USERNAME@$SERVER_IP" \
  "echo '登录阿里云 successfully--> Remote command executed successfully'; \
   echo '阿里云 docker list ⬆⬇️'; \
   docker images; \
   echo '阿里云 docker list ⬆️'; \
   echo '下载镜像 docker pull $REPO_URL/$IMAGE_NAME:$IMAGE_TAG'; \
   docker pull '$REPO_URL_LOCAL/$IMAGE_NAME:$IMAGE_TAG'; \
   echo '停止-----> $CONTAINER_NAME 容器'; \
   docker stop $CONTAINER_NAME || true; \
   docker rm $CONTAINER_NAME || true; \
   echo '删除------> $CONTAINER_NAME 容器'; \
   echo '开始启动 $CONTAINER_NAME 容器'; \
   docker run -d --name $CONTAINER_NAME -p 48080:48080 -e SPRING_PROFILES_ACTIVE=dev -v /work/projects/yudao-server:/root/logs/ $REPO_URL_LOCAL/$IMAGE_NAME:$IMAGE_TAG; \
   echo '正在启动 yudao-server 容器中，需要等待 60 秒左右'"

if [ $? -ne 0 ]; then
    echo "<<<<<<<-----ALL TASK   ERROR------>>>>>"
    exit 1
fi
echo  "<<<<<<<-----ALL TASK   DONE------>>>>>"