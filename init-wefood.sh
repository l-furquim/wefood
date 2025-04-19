#!/bin/bash

sudo docker start wefood-postgres
sudo docker start wefood-zookeeper
sudo docker start wefood-redis
sudo docker start wefood-kafka

sudo docker start wefood-discovery
sudo docker start wefood-profile
sudo docker start wefood-notification
sudo docker start wefood-mailsender
sudo docker start wefood-gateway
sudo docker start wefood-payment
sudo docker start wefood-pedido

