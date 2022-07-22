set -e
export branch=$(echo $branch | base64 -d)
APP_NAME=cutepet-gateway

sh /home/lhs/cutepet/script/${APP_NAME}/package.sh ${branch}
sleep 1
sh /home/lhs/cutepet/script/${APP_NAME}/deploy.sh restart
