pipeline {
    agent none
    stages {
        stage('maven step') {
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
        stage('node step') {
            agent {
                docker { image 'node:7-alpine' }
            }
            steps {
                sh 'node --version'
            }
        }
        stage('slave step') {
        	agent {
        		label 'ubuntu'
        	}
        	steps {
        		sh 'java -version '
        	}
        }
        stage ('dev step') {
        	agent none
        	steps {
        		input 'Continue?'
        	}
        }
    }
}