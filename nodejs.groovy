// define the Github project + repos we want to build
def Github_project = 'ram-repo'
def Github_repos = ['https://github.com/ram-repo/game-of-life.git', 'https://github.com/ram-repo/Python_Basics.git']

// create a pipeline job for each of the repos and for each feature branch.
for (Github_repo in Github_repos)
{
  multibranchPipelineJob("${Github_repo}-ci") {
    // configure the branch / PR sources
    branchSources {
      branchSource {
        source {
          Github {
            credentialsId("git")
            repoOwner("${Github_project.toUpperCase()}")
            repository("${Github_repo}")
            serverUrl("https://github.com/")
            traits {
              headWildcardFilter {
                includes("master release/* feature/* bugfix/*")
                excludes("")
              }
            }
          }
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
      }
    }
    // discover Branches (workaround due to JENKINS-46202)
    configure {
      def traits = it / sources / data / 'jenkins.branch.BranchSource' / source / traits
      traits << 'com.cloudbees.jenkins.plugins.Github.BranchDiscoveryTrait' {
        strategyId(3) // detect all branches
      }
    }

    // check every minute for scm changes as well as new / deleted branches
    triggers {
      periodic(1)
    }
    // don't keep build jobs for deleted branches
    orphanedItemStrategy {
      discardOldItems {
        numToKeep(-1)
      }
    }
  }
}