import jenkins.model.Jenkins
import hudson.model.Node.Mode

Jenkins.instance.setNumExecutors(0)
Jenkins.instance.setMode(Mode.EXCLUSIVE)
