pipeline {
    agent any

    stages {
        stage('Building') {
            agent{
                docker{
                    image 'maven:3.9.8-eclipse-temurin-21'
                    reuseNode true
                }
            }
            steps {
                echo 'Building PRUDWI CEO application'
                sh 'mvn clean package'
            }
        }

        stage('Unit Testing')
        {
            steps{
                sh 'mvn test'
            }
            post{
                always{
               junit '**/target/surefire-reports/*.xml' 
                }
            }
        }
    }
}
