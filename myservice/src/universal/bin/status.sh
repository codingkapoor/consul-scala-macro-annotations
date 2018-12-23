#!/bin/bash

CHECK="com.codingkapoor.myservice.MyService"
PID=`jps | grep MyService | awk '{print $1}'`
STATUS=$(ps aux | grep -v grep | grep ${CHECK})

if [ "${#STATUS}" -gt 0 ] && [ -n ${PID} ]; then
    echo "`date`: MyService is running"
else
    echo "`date`: MyService is not running"
    exit 1
fi
