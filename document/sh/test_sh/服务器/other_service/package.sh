#!/bin/bash
#运行错误，不执行后续
set -e

#代码目录
CODE_PATH=/home/lhs/cutepet/code
#仓库目录
REPOSITORY_PATH=/home/repository/com/lhs/cutepet

#服务名称
APP_NAME=cutepet-member
#jar name
JAR_NAME=${CODE_PATH}/${APP_NAME}/${APP_NAME}-biz/target/${APP_NAME}.jar 

JAR_TARGET_PATH=/home/lhs/cutepet/jars/${APP_NAME}
#代码分支
BRANCH=$1

mkdir -p ${JAR_TARGET_PATH}

#如果出现 instal之后 包没有替换的情况，取消注释
# rm -rf ${REPOSITORY_PATH}/${APP_NAME}

sleep 1

cd ${CODE_PATH}/${APP_NAME}
#拉取对应分支代码 覆盖本地代码
git fetch --all &&  git reset --hard origin/${BRANCH} && git pull
git checkout ${BRANCH}
mvn -B clean install -Dmaven.test.skip=true -Dautoconfig.skip

sleep 1
cp ${JAR_NAME} ${JAR_TARGET_PATH}
sleep 1

