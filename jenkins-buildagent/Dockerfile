FROM openjdk:8-jdk-alpine
MAINTAINER Stefan Rademacher <s.rademacher@live.de>

#RUN adduser -h /home/jenkins -D -s /bin/bash jenkins
RUN addgroup -g 1000 jenkins \
&& adduser -h /home/jenkins -u 1000 -G jenkins -s /bin/bash -D jenkins

# Add the jenkins user to sudoers
RUN echo "jenkins  ALL=(ALL)  ALL" >> etc/sudoers

RUN apk add --no-cache bash git git-subtree openssh \
	&& mkdir /var/run/sshd \
	&& sed -i 's/#PasswordAuthentication yes/PasswordAuthentication no/' /etc/ssh/sshd_config


# setup host keys !?! (https://bbs.archlinux.org/viewtopic.php?id=165382)
RUN /usr/bin/ssh-keygen -A

COPY setup-sshd /usr/local/bin/setup-sshd
RUN chown jenkins /usr/local/bin/setup-sshd \
	&& chmod 754 /usr/local/bin/setup-sshd

# Expose SSH port and run SSHD
EXPOSE 22
CMD ["/usr/local/bin/setup-sshd"]
