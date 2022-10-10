#!/bin/bash

REPOSITORY=/home/ubuntu/app/step2
PROJECT_NAME=Inssagram

echo "> Build 파일 복사"

cp $REPOSITORY/zip/*.jar $REPOSITORY/

echo "> 현재 구동중인 애플리케이션 pid 확인"

# 현재 수행중인 스프링 부트 어플리케이션의 프로세스 ID를 찾아서 실행중이면 종료.
# 스프링부트 어플리케이션(backend_server)으로 된 다른 프로그램이 있을 수 있으므로 backend_server로 된 jar 프로세스를 찾고 ID를 반환해 변수에 넣음.
CURRENT_PID=$(pgrep -fl ${PROJECT_NAME} | grep jar | awk '{print $1}')

echo "현재 구동중인 어플리케이션 pid: $CURRENT_PID"

if [ -z "$CURRENT_PID" ]; then
    echo "> 현재 구동중인 애플리케이션이 없으므로 종료하지 않습니다."
else
    echo "> kill -15 $CURRENT_PID"
    kill -15 $CURRENT_PID
    sleep 5
fi

echo "> 새 어플리케이션 배포"

JAR_NAME=$(ls -tr $REPOSITORY/*.jar | tail -n 1)

echo "> JAR Name: $JAR_NAME"

echo "> $JAR_NAME 에 실행권한 추가"

chmod +x $JAR_NAME # JAR 파일에 대한 권한이 없으므로 부여함.

echo "> $JAR_NAME 실행"

nohup java -jar $JAR_NAME > $REPOSITORY/nohup.out 2>&1 & # nobup 실행하면 CodeDeploy가 무한 대기하므로 nobup.out을 표준 입출력용으로 별도로 사용함.
    # 이렇게 하지 않으면 nohup.out 파일이 생성되지 않고, CodeDeploy 로그에 표준 입출력이 출력됨.
    # nohup이 끝나기 전까지 CodeDeploy도 끝나지 않으니 꼭 이렇게 해야만 함.