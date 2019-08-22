#!/bin/sh
 
TPID=`ps -ef|grep timer-center|grep -v grep|awk '{print $2}'`
echo 'Kill Process! tpid=' $TPID
if [ ${TPID} ]; then
	echo 'stop Process...'
	kill -15 $TPID
fi

sleep 5

TPID=`ps -ef|grep timer-center|grep -v grep|awk '{print $2}'`
if [ ${TPID} ]; then
	echo 'Kill Process!'
	kill -9 $TPID
else
	echo 'Stop Success'
fi
