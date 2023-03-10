pipeline {
	agent any
    tools {
         maven 'maven-3.9.0'
         jdk 'jdk-17'
    }
    stages {
		stage('Pull from git'){
	    	steps {
	           	git  url: 'https://github.com/simone-cicerello/running-stats.git',
	           	branch: 'master',
	           	credentialsId: 	'github-credentials'
	        }
	    }
		stage('Build with maven'){
	    	steps {
	           	sh "mvn clean install -DskipTests=true"
	    	}
	    }
/* 	    stage('Kill existing process'){
	    	steps {
	   	        sshagent(credentials: ['tomcat-server-credentials']) {
	           		sh 'ssh -o StrictHostKeyChecking=no ec2-user@13.53.132.177 uptime'
	           		sh 'pkill -f "running-stats"'
	        	}
	    	}
	    } */
	    stage('Push jar and application.yml') {
	    	steps {
	    	    //rsync -> funzione copia
	    	    //-a -> Significa archive. Questa modalità corrisponde a scrivere -rlptgoD e riporta tutte le condizioni
	    	    //      originali del file dalla destinazione alla sorgente come timestamp, link simbolici, permessi,
	    	    //      proprietario e gruppo
	    	    //-v -> Significa verbose. Avremo più informazioni a schermo
	    	    //-z -> Dice a rsync di comprimere i dati trasmessi
	    	    //-e ssh -> Opzione usata per specificare che si tratta di una connessione SSH
	    	   	sshagent(credentials: ['tomcat-server-credentials']) {
                    sh 'ssh -o StrictHostKeyChecking=no ec2-user@13.53.132.177 uptime'
	            	sh 'rsync -avz -e ssh target/running-stats-0.0.1-SNAPSHOT.jar running-stats-0.0.1-SNAPSHOT.jar'
	            	sh 'rsync -avz -e ssh src/main/resources/application.yml application.yml'
	        	}
	    	}
	    }
	    stage('Giving permissions to files') {
            steps {
                sshagent(credentials: ['tomcat-server-credentials']) {
                    sh 'ssh -o StrictHostKeyChecking=no ec2-user@13.53.132.177 uptime'
                    sh 'chmod +x running-stats-0.0.1-SNAPSHOT.jar'
                    sh 'chmod +x application.yml'
                }
            }
        }
         stage('Start application') {
            steps {
                sshagent(credentials: ['tomcat-server-credentials']) {
                    sh 'ssh -o StrictHostKeyChecking=no ec2-user@13.53.132.177 uptime'
                    sh 'java -jar /home/ec2-user/running-stats-0.0.1-SNAPSHOT.jar --spring.config.location=file:///home/ec2-user/application.yml'
                }
            }
        }
	}
}