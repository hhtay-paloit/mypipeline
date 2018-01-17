pipeline {
    agent none
    parameters {
		choice(
			// choices are a string of newline separated values
			// https://issues.jenkins-ci.org/browse/JENKINS-41180
			choices: 'greeting\nsilence',
			description: 'Say good at the end',
			name: 'END_ACTION')
    }

    stages {
    	stage ('run docker') {
    		agent none
    		steps {
    			script {
    				env.RESULT = input message: 'Choose the following options wisely', parameters: [choice(choices: 'Dog\nCat\nTurtle\nMaven', description: '', name: 'Animal'), booleanParam(defaultValue: false, description: '', name: 'Docker')], submitter: 'hhtay,admin'

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
            when {
            	beforeAgent true
            	environment name: 'RESULT.Animal', value: 'Maven' 
            }
            steps {
                sh 'mvn --version'
            }
        }
        stage ('node step') {
            agent {
                docker { image 'node:7-alpine' }
            }
            when {
            	beforeAgent true
            	environment name: 'RESULT.Docker', value: 'true' 
            }
            steps {
                sh 'node --version'
            }
        }
        stage ('slave step') {
        	agent any
        	steps {
        		sh 'java -version '
        	}
        }
        stage ('dev step') {
        	agent none       
        	when {
        		expression {
        			params.END_ACTION == 'greeting'
        		}
        	}
        	steps {
        		input message: 'Are you going to choose?', ok: 'Okay', parameters: [choice(choices: 'dog\ncat\nturtle', description: '', name: 'which animal?')], submitter: 'hhtay\nfreakerhh\njenkins', submitterParameter: 'approver'
        	}
        }
    }
}