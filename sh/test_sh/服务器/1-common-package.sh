#!/bin/bash
set -e

#代码目录
CODE_PATH=/home/lhs/cutepet/code
#仓库目录
REPOSITORY_PATH=/home/repository/com/lhs/cutepet

#服务名称
APP_NAME=cutepet-common
#代码分支
BRANCH=$1
echo '将切换到分支:'${BRANCH}
#如果出现 instal之后 包没有替换的情况，取消注释
# rm -rf ${REPOSITORY_PATH}/${APP_NAME}

sleep 1

cd ${CODE_PATH}/${APP_NAME}
#拉取对应分支代码 覆盖本地代码
git fetch --all &&  git reset --hard origin/${BRANCH} && git pull
git checkout ${BRANCH}
mvn -B clean install -Dmaven.test.skip=true -Dautoconfig.skip
sleep 1

#打包其他服务的api
cd ${CODE_PATH}/cutepet-member
git fetch --all &&  git reset --hard origin/${BRANCH} && git pull
git checkout ${BRANCH}
cd ${CODE_PATH}/cutepet-member/cutepet-auth-api
mvn -B clean install -Dmaven.test.skip=true -Dautoconfig.skip
cd ${CODE_PATH}/cutepet-member/cutepet-member-api
mvn -B clean install -Dmaven.test.skip=true -Dautoconfig.skip
sleep 1

cd ${CODE_PATH}/cutepet-order/cutepet-order-api
git fetch --all &&  git reset --hard origin/${BRANCH} && git pull
git checkout ${BRANCH}
mvn -B clean install -Dmaven.test.skip=true -Dautoconfig.skip
sleep 1

#cd ${CODE_PATH}/cutepet-product/cutepet-product-api
#git fetch --all &&  git reset --hard origin/${BRANCH} && git pull
#git checkout ${BRANCH}
#mvn -B clean install -Dmaven.test.skip=true -Dautoconfig.skip
#sleep 1

cd ${CODE_PATH}/cutepet-system/cutepet-system-api
git fetch --all &&  git reset --hard origin/${BRANCH} && git pull
git checkout ${BRANCH}
mvn -B clean install -Dmaven.test.skip=true -Dautoconfig.skip
sleep 1

