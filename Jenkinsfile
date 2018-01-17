pipeline {
    agent none
    stages {
        stage('Back-end') {
            agent {
                docker { 
                	image 'maven:3-alpine' 
                	args '-u root'
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
        stage('Test node') {
        	agent ubuntu
        	steps {
        		sh 'java -version'
        	}
        }
    }
}