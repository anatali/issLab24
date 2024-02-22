## ----------------------------------------------------------
## Dockerfile for serivemath24Asynch
## ----------------------------------------------------------

FROM openjdk:12.0.2
EXPOSE 8011
ARG distrFile=./build/distributions/servicemath24synch-1.0.tar
## ADD extracts the tar
ADD ${distrFile} ./
WORKDIR /servicemath24synch-1.0/bin
COPY ./*.pl ./
COPY ./*.json ./
COPY ./*.txt ./
COPY ./*.py ./

## RUN apt-get update -y
## RUN apt-get install -y wiringpi
## RUN sudo apt-get install -y python

CMD ["bash", "./unibo.servicemath24synch"]

## PRIMA ATTIVAZIONE
## gradlew distTar
## docker build -t servicemathsynch:1.0 .                      CREA IMMAGINE REPOSTORY lowercase
## docker run -it --rm --name servicemathsynch -p8011:8011  --privileged servicemathsynch:1.0  /bin/bash
## docker-compose -f servicemathsynch.yaml up

## docker tag servicemathsynch:1.0 natbodocker/servicemathsynch:1.0
## type docker_password.txt | docker login --username natbodocker --password-stdin
## docker login --username natbodocker --password
## docker push natbodocker/servicemathsynch:1.0