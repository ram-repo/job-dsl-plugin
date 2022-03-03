/*
Creating DSL job for multibranch Pipline
*/

def Job_Name = sample_multibranch_pipline

multibranchPipelineJob($Job_Name){
    /*authorization {
        permission(String permission, String user)
        permissionAll(String user)
        permissions(String user, Iterable<String> permissionsList)
    }*/
    configure(Closure configureBlock)
    description(String descriptionString)
    displayName(String displayName)
    branchSources {
        buildStrategies {}
        source(github){
            // IMPORTANT: use a constant and unique identifier
            id('123456789') 
            //apiUri(String value)
            //repositoryUrl(String value)
            remote('https://github.com/jenkinsci/job-dsl-plugin.git')
            //configuredByUrl(boolean value)
           // credentialsId(String value)
           credentialsId('github-ci')
            //id(String value)
            //includes(String value)
            includes('JENKINS-*')
            //traits {}   multiple option available
        }
        strategy {
            /*allBranchesSame {
                props {
                    buildRetention {
                        buildDiscarder {
                            logRotator {
                                artifactDaysToKeepStr(String value)
                                artifactNumToKeepStr(String value)
                                daysToKeepStr(String value)
                                numToKeepStr(String value)
                            }

                        }
                    }
                }
            } */
            namedBranchesDifferent {
                // 
                defaultProperties {
                    buildRetention {
                        buildDiscarder {
                            logRotator {
                                artifactDaysToKeepStr(String value)
                                artifactNumToKeepStr(String value)
                                daysToKeepStr(String value)
                                numToKeepStr(String value)
                            }
                        }
                    }
                }
                // 
                namedExceptions {
                    name(String value)
                    props {
                        buildRetention {
                            buildDiscarder {
                                logRotator {
                                    artifactDaysToKeepStr(String value)
                                    artifactNumToKeepStr(String value)
                                    daysToKeepStr(String value)
                                    numToKeepStr(String value)
                                }
                            }
                        }
                        durabilityHint {
                            hint(String value)
                            /* Possible values for value:
                            'PERFORMANCE_OPTIMIZED'
                            'SURVIVABLE_NONATOMIC'
                            'MAX_SURVIVABILITY' */
                        }
                        rateLimit {
                            count(int value)
                            durationName(String value)
                            userBoost(boolean value)
                        }
                        suppressAutomaticTriggering()
                        untrusted {
                            publisherWhitelist(Iterable<String> value)
                        }
                    }

                }
            }

        }
    factory {
        workflowBranchProjectFactory {
            scriptPath(String value)
        }
    }
    orphanedItemStrategy {
        defaultOrphanedItemStrategy {
            abortBuilds(boolean value)
            daysToKeepStr(String value)
            numToKeepStr(String value)
        }
        discardOldItems {
            numToKeep(20)
          //daysToKeep(int daysToKeep)
          //inherit()

        }
    }
    primaryView(String primaryViewArg)
    properties {}
    triggers {
        cron {}
       //dos(String cronString) {}
        dos('@daily') {
            triggerScript('set CAUSE=Build successfully triggered by dostrigger.')
        }
        githubPush()
        periodicFolderTrigger {}
        pollSCM {}
        upstream {}
        urlTrigger {
            cron('* 0 * 0 *')
            restrictToLabel('foo')

            // simple configuration
            url('http://www.example.com/foo/') {
                proxy(true)
                status(404)
                timeout(4000)
                check('status')
                check('etag')
                check('lastModified')
            }

            // Content inspection (MD5 hash)
            url('http://www.example.com/bar/') {
                inspection('change')
            }

            // content inspection for JSON content with detailed checking using JSONPath
            url('http://www.example.com/baz/') {
                inspection('json') {
                    path('$.store.book[0].title')
                }
            }

            // content inspection for text content with detailed checking using regular expressions
            url('http://www.example.com/fubar/') {
                inspection('text') {
                    regexp(/_(foo|bar).+/)
                }
            }
        }
    }
}

}
    

