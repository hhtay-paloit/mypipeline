pipeline {
    agent none
    stages {
        stage('Back-end') {
            agent {
                docker { 
                	image 'maven:3-alpine' 
                	args '-v /var/jenkins_home:/var/jenkins_home'
                }
            }
            steps {
                sh 'mvn --version'
            }
        }
        stage('Front-end') {
            agent {
                docker { image 'node:7-alpine' }
            }
            steps {
                sh 'node --version'
            }
        }
    }
}