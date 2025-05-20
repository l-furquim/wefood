pipeline {
    agent any

    environment {
        DOCKERHUB_CREDENTIALS = credentials('dockerhub-creds') // ID configurado no Jenkins
    }

    triggers {
        // Executa no push para main ou em PR se você configurar o GitHub Webhook corretamente
        pollSCM('* * * * *') // ou remova se não quiser polling
    }

    stages {
        stage('Checkout') {
            steps {
                checkout scm
            }
        }

        stage('Login to Docker Hub') {
            steps {
                script {
                    sh "echo ${DOCKERHUB_CREDENTIALS_PSW} | docker login -u ${DOCKERHUB_CREDENTIALS_USR} --password-stdin"
                }
            }
        }

        stage('Build and Push Images') {
            parallel {
                stage('discovery-ms') {
                    steps {
                        sh '''
                        docker build -t furqas/wefood-discovery:latest ./discovery-ms
                        docker push furqas/wefood-discovery:latest
                        '''
                    }
                }
                stage('gateway-ms') {
                    steps {
                        sh '''
                        docker build -t furqas/wefood-gateway:latest ./gateway-ms
                        docker push furqas/wefood-gateway:latest
                        '''
                    }
                }
                stage('mailsender-ms') {
                    steps {
                        sh '''
                        docker build -t furqas/wefood-mailsender:latest ./mailsender-ms
                        docker push furqas/wefood-mailsender:latest
                        '''
                    }
                }
                stage('notification-ms') {
                    steps {
                        sh '''
                        docker build -t furqas/wefood-notification:latest ./notification-ms
                        docker push furqas/wefood-notification:latest
                        '''
                    }
                }
                stage('payment-ms') {
                    steps {
                        sh '''
                        docker build -t furqas/wefood-payment:latest ./payment-ms
                        docker push furqas/wefood-payment:latest
                        '''
                    }
                }
                stage('pedido-ms') {
                    steps {
                        sh '''
                        docker build -t furqas/wefood-pedido:latest ./pedido-ms
                        docker push furqas/wefood-pedido:latest
                        '''
                    }
                }
                stage('profile-ms') {
                    steps {
                        sh '''
                        docker build -t furqas/wefood-profile:latest ./profile-ms
                        docker push furqas/wefood-profile:latest
                        '''
                    }
                }
            }
        }
    }

    post {
        failure {
            echo 'O build falhou!'
        }
        success {
            echo 'Build e push realizados com sucesso!'
        }
    }
}
