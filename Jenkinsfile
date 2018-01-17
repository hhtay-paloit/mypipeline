pipeline {
    agent none
    stages {
    	stage ('run docker') {
    		agent none
    		steps {
    			script {
    				env.RESULT = input message: 'Choose the following options wisely', parameters: [choice(choices: 'Dog\nCat\nTurtle', description: '', name: 'Animal'), booleanParam(defaultValue: false, description: '', name: 'Docker')], submitter: 'hhtay,admin'

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
            	expression  { 
            		environment name: 'RESULT.Animal', value: 'Cat' 
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