#!/bin/bash
set -e

BRANCH=$1
CODE_PATH=/home/lhs/cutepet/code/cutepet-front-web
HTML_PATH=/home/lhs/cutepet/front_html/cutepet-front-web
echo '将切换到分支:'${BRANCH}

cd ${CODE_PATH}

git fetch --all &&  git reset --hard origin/${BRANCH} && git pull
git checkout ${BRANCH}

/home/nodejs/node-v14.16.1-linux-x64/lib/node_modules/yarn/bin/yarn install
npm run build:dev

sleep 1

cd ${HTML_PATH}
rm -rf *
cp -r ${CODE_PATH}/dist/* ${HTML_PATH}

sleep 1

echo 'success'
