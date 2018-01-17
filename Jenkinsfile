
pipeline {
    
    agent none
    
    environment {
        DISABLE_AUTH = 'true'
        DB_ENGINE    = 'sqlite'
    }

    // pipeline must complete in 1 hour
    options {
        timeout(time: 30, unit: 'MINUTES') 
    }

    // can only appear once
    // under the pipeline tag
    parameters {

		choice(
			// choices are a string of newline 
			// separated values
			choices: 'greeting\nsilence',
			description: 'Say goodbye at the end',
			name: 'END_ACTION')

		// say hi at the beginning
		booleanParam (defaultValue: false, description: 'Say hi at the beginning?', name: 'START_ACTION')
    }

    stages {

    	stage ('intro') {

    		// if use input make sure agent is none
    		// else u'll hold up the executor
    		agent none

    		// this is stage level input
    		// this will block for input 
			input {
				message "Should we continue?"
				ok "Yes, we should."
				submitter "admin,hhtay"

				// each parameter is separated
				// by new line
				parameters {
					string(name: 'PERSON', defaultValue: 'Mr Jenkins', description: 'Who should I say hello to?')
					choice(choices: 'Dog\nCat\nTurtle\nMaven', description: 'Choose Maven to run it!', name: 'MAVEN')
					booleanParam (defaultValue: false, description: 'Run docker container?', name: 'DOCKER_INPUT')
				}
			}

    		steps {

    			timeout (time: 5, unit: 'MINUTES') {

    				// and this is how u access the input values
    				// but they are only available in this stage
	    			echo "${PERSON} ${MAVEN} ${DOCKER_INPUT}" 
	    		}

	    		script {

	    			env.CHEATING = DOCKER_INPUT

	    			// you can set environment variables
	  				// using input during steps phase

	    			env.RUN_MAVEN = input message: 'Run Maven?', parameters: [choice(choices: 'Dog\nCat\nTurtle\nMaven', description: 'Choose Maven to run it!', name: 'RUN_MAVEN')], submitter: 'hhtay,admin'

	    			env.RUN_DOCKER = input message: 'Run Docker?', parameters: [booleanParam(defaultValue: false, description: 'Run docker container?', name: 'RUN_DOCKER')], submitter: 'hhtay,admin'
	    		}

	    		echo 'Cheat code ${DOCKER_INPUT} ${env.CHEATING}'
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
            		env.RUN_MAVEN == 'Maven'
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
            	environment name: 'RUN_DOCKER', value: 'true' 
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

        			// this is how you access
        			// params from the above
        			params.END_ACTION == 'greeting'
        		}
        	}
        	steps {
        		echo 'GOOD BYE!'

        		echo "${params.START_ACTION} ${params.END_ACTION}"
        	}
        }
    }
}