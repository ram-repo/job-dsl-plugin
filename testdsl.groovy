multibranchPipelineJob("mysample") {
    branchSources {
        description(String multibranchPipelineJob)
        displayName(String job_name)
        github {
            scanCredentialsId("github")
            repoOwner("ram-repo")
            repository("job-dsl-plugin")
            includes("master feature/* bugfix/* hotfix/* release/*")
            excludes("donotbuild/*")
            caseSensitive(false)
        }
    }
    factory {
        workflowBranchProjectFactory {
            scriptPath("jenkinsFile.groovy")
        }
    }
    triggers {
        periodicFolderTrigger {
            interval("2m")
        }
    }
    orphanedItemStrategy {
        discardOldItems {
            numToKeep(10)
        }
    }

}
