#!/bin/bash

yum update -y
yum install -y docker

service docker start
usermod -a -G docker ec2-user

systemctl enable docker
systemctl restart docker

docker run -d -p 80:8080 kauanmatheusoliveira/duckmail:latest