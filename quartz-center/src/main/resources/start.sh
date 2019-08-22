#!/bin/sh
rm -f tpid

nohup java -jar timer-center.jar --spring.profiles.active=test > /dev/null 2>&1 &
 
echo $! > tpid

sleep 10

PID=`ps -ef|grep timer-center|grep -v grep|awk '{print $2}'`
if [ ${PID} ]; then
	echo 'App is runing...'
else
	echo 'App is NOT runing...'
fi

tail -f ./log/info.timer-center.log
