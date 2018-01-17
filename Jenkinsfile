pipeline {
    
    agent none
    
    // pipeline must complete in 1 hour
    options {
        timeout(time: 1, unit: 'HOURS') 
    }

    // can only appear once
    // under the pipeline tag
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

    		// if use input make sure agent is none
    		// else u'll hold up the executor
    		agent none

    		input {
				message "Should we continue?"
				ok "Yes, we should."
				submitter "admin,hhtay"

				// each parameter is separated
				// by new line
				parameters {
					string(name: 'PERSON', defaultValue: 'Mr Jenkins', description: 'Who should I say hello to?')
					choice(choices: 'Dog\nCat\nTurtle\nMaven', description: 'Choose Maven to run it!', name: 'RUN')
				}
			}

    		steps {
    			timeout (time: 5, unit: 'MINUTES') {
    			
    				// and this is how u access the values
	    			echo "${PERSON} ${RUN}"
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