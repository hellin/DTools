# DTools
Developer tools (SSH, FTP, SNMP simulator, automatization of common tasks, network status, docker status...)

Current stable release: 5.4.1


## Features

- monitor network nodes and services
- auto-discovery
- execute ssh commands
- save common ssh commands
- upload/download files using ftp/sftp
- snmp manager, agent and trap sender/receiver
- docker status
- webhook receiver for alertmanager notifications
- active alerts (from alertmanager)
- sending metrics to Pushgateway



Commons-logging is required by commons-vfs


## Docker image

Build:

```
docker build -t dtools .
docker tag dtools matjaz99/dtools:5.4.2-beta
docker push matjaz99/dtools:5.4.2-beta
```

docker run -d --name dtools -p 7070:8080 matjaz99/dtools:5.4.1



Prometheus metrics:

/api/prometheus/metrics


Webhook:

/api/webhook/*



## Version history

moved to functional specification


Whois implementation in Java:

https://github.com/ethauvin/Whois/blob/master/Whois.java

https://stackoverflow.com/questions/2195730/check-domain-availability-java





