pipeline {
    agent any

    tools {
        maven 'Maven'
        jdk 'JDK21'
    }

    stages {
        stage('Checkout') {
            steps {
                checkout scm
            }
        }

        stage('Verify Parabank is running') {
            steps {
                sh 'curl -f http://localhost:8080/parabank/index.htm >/dev/null'
            }
        }

        stage('Run Automation Suite') {
            steps {
                sh 'mvn clean test -Dheadless=true -Dbrowser=chrome'
            }
        }
    }

    post {
        always {
            junit allowEmptyResults: true, testResults: 'target/cucumber.xml,target/surefire-reports/**/*.xml'
            archiveArtifacts allowEmptyArchive: true, artifacts: 'target/cucumber-reports.html,target/cucumber.json,logs/**/*.log'
        }
    }
}
