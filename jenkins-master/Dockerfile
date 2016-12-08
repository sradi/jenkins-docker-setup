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
    && mkdir /usr/share/jenkins-provisioning \
    && chown -R jenkins:jenkins /var/log/jenkins \
    && chown -R jenkins:jenkins /var/cache/jenkins \
    && chown -R jenkins:jenkins /var/keys/jenkins/.ssh \
    && chown -R jenkins:jenkins /usr/share/jenkins-provisioning

COPY config-templates/* /usr/share/jenkins-provisioning/config-templates/
COPY *.yml /usr/share/jenkins-provisioning/
RUN echo "===> Installing sudo to emulate normal OS behavior..."  && \
    apk --update add sudo                                         && \
    \
    \
    echo "===> Adding Python runtime..."  && \
    apk add python py-pip openssl ca-certificates    && \
    apk add libxml2 libxslt py-lxml && \
    apk add --virtual build-dependencies \
                python-dev libffi-dev openssl-dev build-base  && \
    pip install --upgrade pip cffi                            && \
    \
    \
    echo "===> Installing Ansible + modules..."  && \
    pip install ansible                && \
    ansible-galaxy install -p /tmp cmprescott.xml && \
    ln -s /tmp/cmprescott.xml/library /usr/share/jenkins-provisioning/library && \
    \
    \
    echo "===> Adding hosts for convenience..."  && \
    mkdir -p /etc/ansible                        && \
    echo 'localhost' > /etc/ansible/hosts        && \
    \
    \
    echo "===> Configuring Jenkins..." && \
    ansible-playbook /usr/share/jenkins-provisioning/configure-jenkins.yml && \
    \
    \
    echo "===> Removing package list..."  && \
    apk del build-dependencies            && \
    rm -rf /var/cache/apk/*

# Copy in the certs for connection to Docker host
COPY certs-from-docker-host/* /usr/local/etc/jenkins/certs/
RUN chmod +r /usr/local/etc/jenkins/certs/ca.pem \
    && chmod +r /usr/local/etc/jenkins/certs/cert.pem \
    && chown -R jenkins:jenkins /usr/local/etc/jenkins

USER jenkins

# generate key pair for connections to buildagents
RUN ssh-keygen -t rsa -b 2048 -q -N "" -f /var/keys/jenkins/.ssh/id_rsa