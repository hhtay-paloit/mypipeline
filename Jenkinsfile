
pipeline {
    
    agent none
    
    environment {
        DISABLE_AUTH = 'true'
        DB_ENGINE    = 'sqlite'
        SCANNER_HOME = 'c:/tayhh/Software/sonar-scanner-3.0.3.778-windows/bin'
    }

    // pipeline must complete in 1 hour
    options {
        timeout(time: 10, unit: 'MINUTES') 
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
		booleanParam (defaultValue: false, description: 'Run on slave?', name: 'SLAVE_ON')
    }

    stages {
		stage('SonarQube analysis') {
			
			agent any

			steps {
			
				withSonarQubeEnv('sonarserver') {
					def SCANNER_HOME = tool 'sonarscanner'	
					sh "${SCANNER_HOME}/sonar-scanner"
				}	
				
				sh 'env'
			}
		}

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
					choice(choices: 'Dog\nCat\nTurtle\nMaven', description: 'Choose Maven to run it!', name: 'STAGE_INPUT_MAVEN')
					booleanParam (defaultValue: false, description: 'Run docker container?', name: 'STAGE_INPUT_DOCKER')
				}
			}

    		steps {

    			timeout (time: 5, unit: 'MINUTES') {

    				// and this is how u access the input values
    				// but they are only available in this stage
	    			echo "${PERSON} ${STAGE_INPUT_MAVEN} ${STAGE_INPUT_DOCKER}" 
	    		}

	    		script {

	    			// turn stage-level input into 
	    			// environment variables to be   
	    			// used in other subsequent stages
	    			env.DOCKER = STAGE_INPUT_DOCKER

	    			// this is rare, but you can also get input 
	    			// and set them to environment variables
	  				// in the steps phase
	  				// this is best used with ONLY ONE option, else 
	  				// it will return a mapped variable which
	  				// the values are not easily parsable

	    			env.RUN_MAVEN = input (message: 'Run Maven?', parameters: [choice(choices: 'Dog\nCat\nTurtle\nMaven', description: 'Choose Maven to run it!', name: 'NO_DIRECT_ACCESS_RUN_MAVEN')], submitter: 'hhtay,admin')

	    			env.RUN_DOCKER = input message: 'Run Docker?', parameters: [booleanParam(defaultValue: false, description: 'Run docker container?', name: 'NO_DIRECT_ACCESS_DOCKER')], submitter: 'hhtay,admin'
	    		}

	    		echo "STAGE_INPUT_DOCKER: ${STAGE_INPUT_DOCKER} == env.DOCKER ${env.DOCKER}"
	    		echo "env.RUN_MAVEN ${env.RUN_MAVEN}, env.RUN_DOCKER ${env.RUN_DOCKER}"
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
            	sh 'set'
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

        stage ('any step') {
        	agent any
        	steps {
        		sh 'java -version '
        	}
        }
        stage ('slave step') {
        	agent {
        		label 'ubuntu'
        	}
        	when {
        		expression {
        				params.END_ACTION == 'greeting' && 
        				params.SLAVE_ON == true
        		}
        	}
        	steps {
        		echo 'GOOD BYE!'
        		echo "params.START_ACTION: ${params.START_ACTION}, params.END_ACTION: ${params.END_ACTION} updated"
        	}
        }
    }
}