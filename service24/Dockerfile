## ----------------------------------------------------------
## Dockerfile for unibo.basicrobot22
## ----------------------------------------------------------

FROM openjdk:12.0.2
EXPOSE 8011
ARG distrFile=./build/distributions/unibo.servicemath-boot-1.0.tar
## ADD extracts the tar
ADD ${distrFile} ./
WORKDIR /unibo.servicemath-boot-1.0/bin
COPY ./*.pl ./
COPY ./*.json ./
COPY ./*.txt ./
COPY ./*.py ./

## RUN apt-get update -y
## RUN apt-get install -y wiringpi
## RUN sudo apt-get install -y python

CMD ["bash", "./unibo.servicemath"]

## PRIMA ATTIVAZIONE
## gradlew build
## docker build -t servicemath:1.0 .                      CREA IMMAGINE REPOSTORY lowercase
## docker run -it --rm --name servicemath -p8011:8011 -p8020:8020 --privileged servicemath:1.0  /bin/bash

