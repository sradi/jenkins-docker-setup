FROM jenkins:alpine
MAINTAINER Stefan Rademacher <s.rademacher@live.de>

ENV JENKINS_USER admin
ENV JENKINS_PASS admin
ENV JAVA_OPTS -Xmx2g -Djenkins.install.runSetupWizard=false
ENV JENKINS_OPTS --logfile=/var/log/jenkins/jenkins.log --webroot=/var/cache/jenkins/war

COPY init.groovy.d/* /usr/share/jenkins/ref/init.groovy.d/

ENV JENKINS_PLUGINS git docker-plugin workflow-aggregator docker-workflow matrix-auth blueocean
RUN /usr/local/bin/install-plugins.sh ${JENKINS_PLUGINS}

USER root
RUN mkdir /var/log/jenkins \
    && mkdir /var/cache/jenkins \
    && mkdir -p /var/keys/jenkins/.ssh \
    && chown -R  jenkins:jenkins /var/log/jenkins \
    && chown -R  jenkins:jenkins /var/cache/jenkins \
    && chown -R  jenkins:jenkins /var/keys/jenkins/.ssh

# Copy in the certs for connection to Docker host
COPY certs-from-docker-host/* /usr/local/etc/jenkins/certs/
RUN chmod +r /usr/local/etc/jenkins/certs/ca.pem \
    && chmod +r /usr/local/etc/jenkins/certs/cert.pem \
    && chown -R jenkins:jenkins /usr/local/etc/jenkins

USER jenkins

# generate key pair for connections to buildagents
RUN ssh-keygen -t rsa -b 2048 -q -N "" -f /var/keys/jenkins/.ssh/id_rsa