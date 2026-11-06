#!/usr/bin/env bash
set -e
cd "$(dirname "$0")"
APP_PROFILE=demo MAIL_MODE=demo java -jar caso3-documentos.jar
