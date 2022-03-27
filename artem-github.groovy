import utilities.GithubMultibranch
 
 
def multiPipeline = new GithubMultibranch(
    description: 'Just try make world better',
    name: 'Github-Test',
    displayName: 'Github-Test',
    repositoryOwner: "ram-repo",
    repositoryName: "game-of-life",
    credentials: 'artem-github',
    includeBranches: '*',
    excludeBranches: ''
).build(this)
