pipeline {

    agent{
            docker{
                        image 'maven:3.9.8-eclipse-temurin-21'
                        reuseNode true
                    }
                }

    stages {
        stage('Building') {
            
            steps {
                echo 'Building PRUDWI CEO application'
                sh 'mvn clean package -DskipTests'
            }
        }

        stage('Unit Testing')
        {
            
            steps{
                sh 'mvn test'
            }
            post{
                always{
               junit 'test-results/junit.xml' 
                }
            }
        }
        stage('Integration Testing')
        {
           
            steps{
                echo 'Integration Testing'
                sh 'mvn verify -DskipUnitTests'
            }
            post{
                always{
                    junit 'jest-results/junit.xml'
                }
            }
        }
    }
}
