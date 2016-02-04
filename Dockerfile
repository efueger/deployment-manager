FROM benchflow/base-images:dns-envconsul-java8_dev

MAINTAINER Vincenzo FERME <info@vincenzoferme.it>

ENV DOCKER_COMPOSE_VERSION 1.5.2
ENV BENCHFLOW_COMPOSE_VERSION v-dev

#Install docker-compose
RUN apk --update add curl && \
    curl -L https://github.com/docker/compose/releases/download/${DOCKER_COMPOSE_VERSION}/docker-compose-Linux-x86_64 > /usr/local/bin/docker-compose && \
    chmod +x /usr/local/bin/docker-compose && \
	# Get benchflow-compose
    wget -q --no-check-certificate -O /app/benchflow-compose.jar https://github.com/benchflow/compose/releases/download/$BENCHFLOW_COMPOSE_VERSION/benchflow-compose.jar && \
	# Clean up
	apk del --purge curl && \
    rm -rf /var/cache/apk/*

COPY configuration.yml /app/

COPY ./services/300-compose.conf /apps/chaperone.d/300-compose.conf

#TODO: remove, here for testing purposes
RUN touch /app/test.tpl
#TODO: remove, here because of a problem killing the container
RUN rm /apps/chaperone.d/200-envconsul-envcp.conf
 
EXPOSE 8080
