pipeline {
    
    agent none
    
    options {
        timeout(time: 1, unit: 'HOURS') 
    }

    parameters {
		choice(
			// choices are a string of newline separated values
			// https://issues.jenkins-ci.org/browse/JENKINS-41180
			choices: 'greeting\nsilence',
			description: 'Say goodbye at the end',
			name: 'END_ACTION')
    }

    stages {
    	stage ('intro') {
    		agent none
    		steps {
    			timeout (time: 5, unit: 'MINUTES') {

    				input name:'MYCHOICE', message: 'Choose the following options wisely', parameters: [choice(choices: 'Dog\nCat\nTurtle\nMaven', description: 'Choose Maven to run it!', name: 'RUN')], submitter: 'hhtay,admin'

	    			//script {
	    			//	env.RESULT = input message: 'Choose the following options wisely', parameters: [choice(choices: 'Dog\nCat\nTurtle\nMaven', description: 'Choose Maven to run it!', name: 'RUN')], submitter: 'hhtay,admin'
	    			//}

	    			echo "${env.RESULT}"
	    			echo "${MYCHOICE}"
	    		}
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
            	expression {
           			env.RESULT == 'Maven'
           		}
            }
            steps {
            	sh 'env'
                sh 'mvn --version'
            }
        }
        stage ('docker step') {
            agent {
                docker { image 'node:7-alpine' }
            }
            when {
            	beforeAgent true
            	environment name: 'RESULT.DOCKER', value: 'true' 
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
        	agent {
        		label 'ubuntu'
        	}
        	when {
        		expression {
        			params.END_ACTION == 'greeting'
        		}
        	}
        	steps {
        		echo 'GOOD BYE!'
        	}
        }
    }
}