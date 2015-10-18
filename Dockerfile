FROM benchflow/envconsul:v0.6.0_serverjre-8

MAINTAINER Vincenzo FERME <info@vincenzoferme.it>

#TODO: Update to 1.5 when released
ENV DOCKER_COMPOSE_VERSION 1.4.2
ENV BENCHFLOW_COMPOSE_VERSION 0.0.1

RUN apk --update add curl && \
    curl -L https://github.com/docker/compose/releases/download/${DOCKER_COMPOSE_VERSION}/docker-compose-Linux-x86_64 > /usr/local/bin/docker-compose && \
    chmod +x /usr/local/bin/docker-compose && \
    apk del --purge curl && \
    rm -rf /var/cache/apk/* /var/tmp/* *.gz
    
#TODO: complete it by adding the actual service (both using local folder and the git repository)

#Using local folder
COPY configuration.yml /opt/dropwizard/
COPY target/benchflow-compose-${BENCHFLOW_COMPOSE_VERSION}.jar /opt/dropwizard/benchflow-compose.jar

WORKDIR /opt/dropwizard

EXPOSE 8080

CMD ["java", "-jar", "-Done-jar.silent=true", "benchflow-compose.jar", "server", "configuration.yml"]