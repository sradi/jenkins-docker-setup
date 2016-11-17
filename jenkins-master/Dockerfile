FROM jenkins:alpine
MAINTAINER Stefan Rademacher <s.rademacher@live.de>

ENV JENKINS_USER admin
ENV JENKINS_PASS admin
ENV JAVA_OPTS -Xmx2g -Djenkins.install.runSetupWizard=false
ENV JENKINS_OPTS --logfile=/var/log/jenkins/jenkins.log

COPY init.groovy.d/* /usr/share/jenkins/ref/init.groovy.d/
#COPY custom.groovy /usr/share/jenkins/ref/init.groovy.d/custom.groovy

ENV JENKINS_PLUGINS git docker-plugin workflow-aggregator docker-workflow matrix-auth blueocean
RUN /usr/local/bin/install-plugins.sh ${JENKINS_PLUGINS}

USER root
RUN mkdir /var/log/jenkins
RUN chown -R  jenkins:jenkins /var/log/jenkins
USER jenkins