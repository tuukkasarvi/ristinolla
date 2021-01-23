# Tic-tac-toe game
A tic-tac-toe game with arbitrary board size and a computer opponent based on rule-based pattern recognition. GUI implemented using Swing which is deprecated now. Decided try to publish this
as a web app for fun.

## Run locally
Run using java

```
java koodi/Ristinolla
``` 
or
``` 
java -cp opt/app/Ristinolla.jar Ristinolla
``` 

## Deploy on web
I was able to deploy this using an AWS EC2 instance, Route 53 (DNS), Apache, webswing and docker. Webswing (https://www.webswing.org/) is a web server that
makes it possible to run old Swing applications from browser.

1. Provision virtual machine and configure Route 53 to the assigned instance IP. Install Apache web server. Install docker and docker-compose. 
Install git and ssh and configure credentials.
2. Clone this repository to EC2 instance. 
3. Set up certificates for your site for HTTPS using Let's Encrypt for example.
4. Update docker-compose.yml with your site information and run ```docker-compose up -d```.
5. Check out the local IP for the container in EC2.
    1. Get container id: ```docker ps -a```.
    2. Get ip address: ```docker inspect <container id> | grep "IPAddress"```.
6. Update httpd.conf (VirtualHost for HTTPS) using your SSL certificate information and local IP for container.
7. Copy httpd.conf to /etc/httpd/conf/ and start Apache ```sudo service httpd start```.
8. Test if the setup works by navigating to your site.

## Problems
It turns out that some of the components of the app do not work reliabliy in browser using webswing. The app crashes often. 
This is likely due to the fact that Swing components used are from way back (15+ years ago). These problems occur also when the app is run from localhost in browser. 
The app works without problems when run using Java.

Deployment setup allows for a single session. If the app were to be published for use, there would be need to allow multiple sessions.
