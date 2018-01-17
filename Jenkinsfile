pipeline {
    agent none
    stages {
    	stage ('run docker') {
    		agent none
    		steps {
    			script {
    				env.RESULT = input message: 'Run docker containers?', parameters: [booleanParam(defaultValue: false, description: '', name: 'Run docker')]
    			}
    			echo "${env.RESULT}"
    		}
    	}
        stage ('maven step') {
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
        stage ('node step') {
            agent {
                docker { image 'node:7-alpine' }
            }
            steps {
                sh 'node --version'
            }
        }
        stage ('slave step') {
        	agent {
        		label 'ubuntu'
        	}
        	steps {
        		sh 'java -version '
        	}
        }
        stage ('dev step') {
        	agent none
        	when {
        		branch 'master'
        	}
        	steps {
        		input message: 'Are you going to choose?', ok: 'Okay', parameters: [choice(choices: 'dog\ncat\nturtle', description: '', name: 'which animal?')], submitter: 'hhtay\nfreakerhh\njenkins', submitterParameter: 'approver'
        	}
        }
    }
}