## ----------------------------------------------------------
## Dockerfile for unibo.basicrobot22
## ----------------------------------------------------------

FROM openjdk:12.0.2
EXPOSE 8020
## ADD extracts the tar
ADD ./build/distributions/basicrobot24-1.0.tar /
WORKDIR /basicrobot24-1.0/bin
COPY ./*.pl ./
COPY ./*.json ./
COPY ./*.bin ./
COPY ./*.txt ./

## RUN apt-get update -y
## RUN apt-get install -y wiringpi
## RUN sudo apt-get install -y python

CMD ["bash", "basicrobot24"]

## 1) gradlew distTar
## 2) docker build -t basicrobot24:1.0 .
## OCCORRE APRIRE UDP PER PERMETTERE COAP
## docker run -it --rm --name basicrobot24 -p8020:8020/tcp -p8020:8020/udp -p8085:8085/tcp --privileged basicrobot24:1.0  /bin/bash
## MODIFICA DEL FILE DI CONFIGURAZIONE
# docker cp basicrobotConfig.json 89193dba02a2:/basicrobot24-1.0/bin/basicrobotConfig.json
# docker cp stepTimeConfig.json cab3aa29f8eb:/basicrobot24-1.0/bin/stepTimeConfig.json
# docker tag basicrobot24:1.0 natbodocker/basicrobot24:1.0

#type docker_password.txt | docker login --username natbodocker --password-stdin
#docker login --username natbodocker --password xyz
#docker push natbodocker/basicrobot24:1.0


# docker-compose -f webbasicrobot24.yaml  up