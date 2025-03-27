
# 普通镜像构建，随系统版本构建 amd/arm
# docker build -t jerryhotton/big-market-app:1.0-SNAPSHOT -f ./Dockerfile .

# amd构建镜像
docker buildx build --platform linux/amd64 -t jerryhotton/group-buy-market-app:1.0-SNAPSHOT -f ./Dockerfile .