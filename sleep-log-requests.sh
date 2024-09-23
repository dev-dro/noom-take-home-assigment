#!/bin/bash

# Create a new sleep log
curl -X 'POST' \
  'http://localhost:8080/sleep-log' \
  -H 'accept: */*' \
  -H 'username: danilo' \
  -H 'Content-Type: application/json' \
  -d '{
  "startedSleep": "2024-09-21 23:40",
  "wokeUp": "2024-09-22 06:40",
  "feltWhenWokeUp": "OK"
}'

####
# Get last night sleep log
curl -X 'GET' \
  'http://localhost:8080/sleep-log/last-night' \
  -H 'accept: application/json' \
  -H 'username: danilo'

####
# Get the sleep logs averages from the last 30 days
curl -X 'GET' \
  'http://localhost:8080/sleep-log/30-days-averages' \
  -H 'accept: application/json' \
  -H 'username: danilo'