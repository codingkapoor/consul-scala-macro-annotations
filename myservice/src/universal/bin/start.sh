#!/bin/bash

BIN_DIR="$( cd "$( dirname "${BASH_SOURCE[0]}" )" && pwd )"
HOME_DIR="$(dirname "$BIN_DIR")"
CONF_DIR="${HOME_DIR}/conf"
LIB_DIR="${HOME_DIR}/lib"
LOG_DIR="${HOME_DIR}/logs"

if [ ! -d ${CONF_DIR} ] || [ ! -d ${LIB_DIR} ] || [ ! -d ${LOG_DIR} ]; then
  echo "`date`: Mandatory directory check failed."
  exit 0
fi

nohup java -server -Dmyservice.home="${HOME_DIR}" -Dmyservice.conf="${CONF_DIR}" -cp "${LIB_DIR}/*:${CONF_DIR}/*" \
	com.codingkapoor.myservice.MyService > "${LOG_DIR}/stdout.log" 2>&1 &

myservice_pid=$!

echo
echo "MyService started with PID [$myservice_pid] at [`date`]"
