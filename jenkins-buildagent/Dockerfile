FROM openjdk:8-jdk-alpine
MAINTAINER Stefan Rademacher <s.rademacher@live.de>

RUN set -x \
	&& apk add --no-cache openssh

RUN adduser -m -s /bin/bash jenkins

# Add public key for Jenkins login
# TODO

# Add the jenkins user to sudoers
RUN echo "jenkins  ALL=(ALL)  ALL" >> etc/sudoers

# Expose SSH port and run SSHD
EXPOSE 22
CMD ["/usr/sbin/sshd", "-D"]
