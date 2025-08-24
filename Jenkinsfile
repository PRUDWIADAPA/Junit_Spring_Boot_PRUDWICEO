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
        stage('Testing'){
            parallel{
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
        stage('Integration Testing')
        {

            steps{
                echo 'Integration Testing'
                sh 'mvn verify -DskipUnitTests'
            }
            post{
                always{
                     junit '**/target/failsafe-reports/*.xml'
                }
            }
        }
            }

        }

        stage('Approval')
        {
            steps{
                    timeout(3) {
                         input 'Do you want to deploy '
                    }
               
            }
        }


        stage('Deploy')
        {
            steps{
                echo 'deploying'
            }
        }
    }
}
