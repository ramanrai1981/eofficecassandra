#!/usr/bin/env groovy

node {
    stage('checkout') {
        checkout scm
    }

    gitlabCommitStatus('build') {
        docker.image('openjdk:8').inside('-u root -e MAVEN_OPTS="-Duser.home=./"') {
            stage('check java') {
                sh "java -version"
            }

            stage('clean') {
                sh "chmod +x mvnw"
                sh "./mvnw clean"
            }

            stage('install tools') {
                sh "./mvnw com.github.eirslett:frontend-maven-plugin:install-node-and-yarn -DnodeVersion=v6.11.0 -DyarnVersion=v0.24.6"
            }

            stage('yarn install') {
                sh "./mvnw com.github.eirslett:frontend-maven-plugin:yarn"
            }

            stage('backend tests') {
                try {
                    sh "./mvnw test"
                } catch(err) {
                    throw err
                } finally {
                    junit '**/target/surefire-reports/TEST-*.xml'
                }
            }

            stage('frontend tests') {
                try {
                    sh "./mvnw com.github.eirslett:frontend-maven-plugin:gulp -Dfrontend.gulp.arguments=test"
                } catch(err) {
                    throw err
                } finally {
                    junit '**/target/test-results/karma/TESTS-*.xml'
                }
            }

            stage('package and deploy') {
                sh "./mvnw package -DskipTests=true -Pprod docker:build="
                archiveArtifacts artifacts: '**/target/*.war', fingerprint: true
            }

        }

        def dockerImage
        stage('build docker') {
            sh "cp -R src/main/docker target/"
            sh "cp target/*.war target/docker/"
            dockerImage = docker.build('eoffice', 'target/docker')
        }

        stage('publish docker') {
            docker.withRegistry('https://hub.docker.com/r/ramanrai1981/eofficecassandra/', '6d5fb97e-2255-41c6-92e9-7576b6580cde') {
                dockerImage.push 'latest'
            }
        }
    }
}
