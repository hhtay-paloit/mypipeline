pipeline {
	agent none

	stages {
		stage('Info') {
			
			agent {
				docker { 
					image 'openjdk:latest' 
					args '-v /var/jenkins_home:/var/jenkins_home'
				}
			}
			steps {
				sh 'java -version'
				sh 'pwd'
			}
		}	
	}


}
