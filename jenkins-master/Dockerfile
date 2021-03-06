FROM jenkins:alpine
MAINTAINER Stefan Rademacher <s.rademacher@live.de>

USER jenkins

ENV DOCKER_HOST_CERTS_PATH /usr/local/etc/jenkins/certs/
ENV JENKINS_USER admin
ENV JENKINS_PASS admin
ENV JAVA_OPTS -Xmx2g -Djenkins.install.runSetupWizard=false
ENV JENKINS_OPTS --logfile=/var/log/jenkins/jenkins.log --webroot=/var/cache/jenkins/war

COPY init.groovy.d/* /usr/share/jenkins/ref/init.groovy.d/
COPY config-templates/* /usr/share/jenkins/ref/
COPY *.yml /usr/share/jenkins-provisioning/

ENV JENKINS_PLUGINS git docker-plugin workflow-aggregator docker-workflow matrix-auth
RUN /usr/local/bin/install-plugins.sh ${JENKINS_PLUGINS}

USER root
RUN mkdir /var/log/jenkins \
    && chown -R jenkins:jenkins /var/log/jenkins \
    && mkdir /var/cache/jenkins \
    && chown -R jenkins:jenkins /var/cache/jenkins \
    && mkdir -p /var/keys/jenkins/.ssh \
    && chown -R jenkins:jenkins /var/keys/jenkins/.ssh \
    && mkdir -p /usr/local/etc/jenkins \
    && chown -R jenkins:jenkins /usr/local/etc/jenkins

# generate key pair for connections to buildagents
ENV SSH_SLAVE_PRIVATE_KEY_FILE /var/keys/jenkins/.ssh/id_rsa
ENV SSH_SLAVE_PUBLIC_KEY_FILE /var/keys/jenkins/.ssh/id_rsa.pub
RUN ssh-keygen -t rsa -b 2048 -q -N "" -f ${SSH_SLAVE_PRIVATE_KEY_FILE}

ONBUILD COPY config-templates/* /usr/share/jenkins/ref/
ONBUILD COPY *.yml /usr/share/jenkins-provisioning/

ONBUILD USER root
ONBUILD RUN echo "===> Installing sudo to emulate normal OS behavior..."  && \
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
    ansible-playbook --connection=local /usr/share/jenkins-provisioning/configure-jenkins.yml && \
    \
    \
    echo "===> Removing package list..."  && \
    apk del build-dependencies            && \
    rm -rf /var/cache/apk/*

ONBUILD USER jenkins
