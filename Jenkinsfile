pipeline {
	agent none

	stages {
		stage('Info') {
			
			agent {
				docker 'openjdk:latest'
			}
			steps {
				sh 'java -version'
				sh 'pwd'
			}
		}	
	}


}
