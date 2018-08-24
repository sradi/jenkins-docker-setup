# jenkins-docker-setup
Experiments on how to automate Jenkins setup with docker, including Docker-based agents, that are created on demand.

## Summary
* Four Dockerfiles:
  * jenkins-master: Docker image, that defines defaults. Uses ONBUILD and is supposed to be used as a parent Dockerfile.
  * a data volume for jenkins_home and the logfiles (the extracted war path is redirected to stay out of the volume container)
  * (not currently in use) a jenkins-buildagent (buildagent containers are started/destroyed on demand by jenkins-master)
  * jenkins-example: a Docker container, that extends jenkins-master. This examples contains configuration for Docker based buildagents, copies certificates for a Docker host and installs additional plugins

## Jenkins master
* executors are set to 0
* jenkins 2 setup wizard is deactivated
* a default user 'admin' (password 'admin') is generated. Values can be changed through environment variables
* some plugins are pre-installed. List of plugins can be customized through environment variable.

## Jenkins example
* jenkins-example contains cert-files from (my local) docker host.
