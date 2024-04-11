FROM openjdk:12.0.2
EXPOSE 8033
## ADD extracts the tar
ADD ./build/distributions/smath24asynchfacade-1.0.tar /
WORKDIR /smath24asynchfacade-1.0/bin
COPY ./*.pl ./
COPY ./*.json ./

CMD ["bash", "smath24asynchfacade"]
 
##  gradlew distTar
##  docker build -t smath24:1.0 .
##  docker run -it --rm --name smath24 -p8033:8033/tcp -p8088:8088/tcp -p8088:8088/udp --privileged smath24:1.0  /bin/bash