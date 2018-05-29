node {
   def mvnHome
   stage('Preparation') { // for display purposes
      checkout scm
      // Get the Maven tool.
      // ** NOTE: This 'M3' Maven tool must be configured
      // **       in the global configuration.           
      mvnHome = tool 'M3'
   }
   stage('Build') {
      wrap([$class: 'Xvfb', autoDisplayName: true, debug: false]) {
        // Run the maven build
        // returnStatus: true here will ensure the build stays yellow
        // when test cases are failing
        sh (script:
          "'${mvnHome}/bin/mvn' -fae clean verify",
          returnStatus: true
        )
      }
   }
   stage('Results') {
      junit '**/target/surefire-reports/TEST-*.xml'
      archive '**/target/repository/'
   }
}
