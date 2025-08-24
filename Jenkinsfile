pipeline {
    agent any

    stages {
        stage('Building') {
            agent{
                docker{
                    image 'maven:3.9.8-eclipse-temurin-21'
                }
            }
            steps {
                echo 'Building PRUDWI CEO application'
                sh 'mvn clean package'
            }
        }
    }
}
