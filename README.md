# jenkins-docker-setup
Experiments on how to automate Jenkins setup with docker, including Docker-based agents, that are created on demand.

## Summary
* Three Dockerfiles:
  * jenkins-master
  * a data volume for jenkins_home and the logfiles (the extracted war path is redirected to stay out of the volume container)
  * a jenkins-buildagent (buildagent containers are started/destroyed on demand by jenkins-master)
* jenkins-master executors are set to 0
* jenkins 2 setup wizard is deactivated
* a default user 'admin' (password 'admin') is generated. Values can be changed through environment variables
* some plugins are pre-installed. List of plugins can be customized thour environment variable.
  * This uses `/usr/local/bin/install-plugins.sh` instead of deprecated `/usr/local/bin/plugins.sh`, as the new script supports transitive plugin dependencies!
