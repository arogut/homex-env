try {
    timeout(time: 20, unit: 'MINUTES') {
        node("maven") {
            stage("Checkout") {
                checkout scm
            }
            stage("Build") {
                sh "mvn clean verify"
                sh "cp shas-device-registry/target/ShasDeviceRegistry-${appVersion}-exec.jar build/shas-device-registry/app.jar"
            }
            stage("Build Image") {
                def appVersion = version()
//              build device registry TODO: change it
                sh "oc start-build shas-device-registry-docker --from-file=build/shas-device-registry/Dockerfile -n shas-int"
            }
        }
    }
} catch (err) {
    echo "in catch block"
    echo "Caught: ${err}"
    currentBuild.result = 'FAILURE'
    throw err
}

def version() {
    def matcher = readFile('pom.xml') =~ '<version>(.+?)</version>'
    matcher ? matcher[0][1] : null
}