multibranchPipelineJob('example-1') {
    authorization {
      permissionAll("admin")
    }
    properties {}
     // check every minute for github_repo changes as well as new / deleted branches
    triggers { 
       periodic(1)
      }
    branchSources {
      displayName("myexample-1")
      git {
          id('123456789') // IMPORTANT: use a constant and unique identifier
          remote('https://github.com/jenkinsci/job-dsl-plugin.git')
          credentialsId('github-ci')
          includes('JENKINS-*')
        }
      strategy {
          defaultBranchPropertyStrategy {
            props {
              // keep only the last 10 builds
              buildRetentionBranchProperty {
                buildDiscarder {
                  logRotator {
                    daysToKeepStr("-1")
                    numToKeepStr("10")
                    artifactDaysToKeepStr("-1")
                    artifactNumToKeepStr("-1")
                  }
                }
              }
            }
          }
        }
       // discover Branches (workaround due to JENKINS)
      configure {
        def traits = it / sources / data / 'jenkins.branch.BranchSource' / source / traits
        traits << 'com.cloudbees.jenkins.plugins.Github.BranchDiscoveryTrait' {
          strategyId(3) // detect all branches
        }
      }
    }
    factory {

    }
    // don't keep build jobs for deleted branches
    orphanedItemStrategy {
        discardOldItems {
            numToKeep(20)
        }
    }
}