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
        // don't make the build fail in case of test failures...
        sh "'${mvnHome}/bin/mvn' -Dmaven.test.failure.ignore=true -fae clean verify"
      }
   }
   stage('Results') {
      // ... JUnit archiver will set the build as UNSTABLE in case of test failures
      junit '**/target/surefire-reports/TEST-*.xml'
      archiveArtifacts '**/target/repository/'
   }
}
