sudo docker rm -f wefood-discovery
sudo docker rm -f wefood-profile
sudo docker rm -f wefood-notification
sudo docker rm -f wefood-mailsender
sudo docker rm -f wefood-gateway

sudo docker rmi build-discovery-ms
sudo docker rmi build-profile-ms
sudo docker rmi build-notification-ms
sudo docker rmi build-mailsender-ms
sudo docker rmi build-gateway-ms