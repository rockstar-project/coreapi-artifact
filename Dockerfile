FROM ibmcom/ibmjava
MAINTAINER Rockstar Team

VOLUME /tmp
ADD target/coreapi-artifact-1.0.0-SNAPSHOT.jar coreapi-artifact.jar
RUN bash -c 'touch /coreapi-artifact.jar'
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/coreapi-artifact.jar"]
