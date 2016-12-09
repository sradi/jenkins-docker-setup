FROM sradi/jenkins-master

MAINTAINER Stefan Rademacher <s.rademacher@live.de>

USER jenkins

# Copy in the certs for connection to Docker host
COPY certs-from-docker-host/* ${DOCKER_HOST_CERTS_PATH}
RUN chmod +r ${DOCKER_HOST_CERTS_PATH}ca.pem \
    && chmod +r ${DOCKER_HOST_CERTS_PATH}cert.pem

RUN /usr/local/bin/install-plugins.sh blueocean
