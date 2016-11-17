FROM alpine:3.4
MAINTAINER Stefan Rademacher <s.rademacher@live.de>

RUN addgroup -g 1000 jenkins \
&& adduser -h /var/jenkins_home -u 1000 -G jenkins -s /bin/bash -D jenkins

RUN mkdir -p /var/log/jenkins
RUN chown -R jenkins:jenkins /var/log/jenkins

VOLUME /var/log/jenkins

USER jenkins

CMD ["echo", "Data Volume for Jenkins"]