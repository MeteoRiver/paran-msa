pipeline {
    agent any

    environment {
        JAVA_HOME = '/usr/lib/jvm/java-17-openjdk-amd64'
        repository = "meteoriver/paran"
        DOCKERHUB_CREDENTIALS = credentials('paran-docker') // Docker Hub credentials
        dockerImage = ''
    }

    stages {

/*         stage('Checkout SCM') {
            steps {
                script {
                    // Git 리포지토리와 서브모듈을 체크아웃
                    checkout([$class: 'GitSCM', branches: [[name: '*//* master']], // Ensure this is the correct branch
                      userRemoteConfigs: [[url: 'git@github.com:MeteoRiver/paran-msa.git', credentialsId: 'paran-ssh']],
                      doGenerateSubmoduleConfigurations: false, extensions: [[$class: 'CleanBeforeCheckout']]])
                }
            }
        } */

        stage('Build') {
            steps {
                script {
                    // gradlew에 실행 권한 부여
                    sh 'chmod +x ./gradlew'

                    sh '''#!/bin/bash
                    set -e
                    export JAVA_HOME="$JAVA_HOME"

                    all_modules=("server:gateway-server" "server:config-server" "server:eureka-server"
                                 "service:user-service" "service:group-service" "service:chat-service"
                                 "service:file-service" "service:room-service" "service:comment-service")

                    echo "Cleaning..."
                    ./gradlew clean

                    for module in "${all_modules[@]}"
                    do
                      echo "Building BootJar for $module"
                      ./gradlew :$module:bootJar || exit 1  # 빌드 실패 시 중지
                    done
                    '''
                }
            }
        }

        stage('Build Docker Images') {
            steps {
                sh 'pwd'  // 현재 작업 디렉토리 확인
                sh 'ls -al'  // 파일 목록 확인
                dir('./path/to/your/docker-compose') {  // docker-compose.yml 파일이 있는 디렉토리로 이동
                    sh 'docker-compose up -d --build'
                    sh 'docker images' // 현재 빌드된 이미지 확인
                }
            }
        }

        stage('Login to Docker Hub') {
            steps {
                sh 'echo $DOCKERHUB_CREDENTIALS_PSW | docker login -u $DOCKERHUB_CREDENTIALS_USR --password-stdin'  // Docker Hub 로그인
            }
        }

        stage('Push to Docker Hub') {
            steps {
                script {
                    def modules = ["config", "eureka", "user", "group", "chat", "file", "room", "comment", "gateway"]

                    for (module in modules) {
                        def imageTag = "meteoriver/paran:${module}-${env.BUILD_ID}"  // 이미지 태그 정의
                        echo "Tagging and pushing ${imageTag}"  // 디버그 메시지
                        sh "docker tag ${repository}:${BUILD_NUMBER} ${imageTag}"  // 이미지 태그
                        sh "docker push ${imageTag}"  // Docker Hub에 푸시
                    }
                }
            }
        }

        stage('Clean Docker Images') {
            steps {
                script {
                    def modules = ["config", "eureka", "user", "group", "chat", "file", "room", "comment", "gateway"]
                    for (module in modules) {
                        def imageTag = "meteoriver/paran:${module}-${env.BUILD_ID}"  // 이미지 태그 정의
                        sh "docker rmi ${imageTag}"  // Docker 이미지 제거
                    }
                }
            }
        }

        stage('Deploy to Kubernetes') {
            steps {
                script {
                    def modules = ["gateway", "config", "eureka", "user", "group", "chat", "file", "room", "comment"]
                    for (module in modules) {
                        def imageTag = "meteoriver/paran:${module}-${env.BUILD_ID}"  // 이미지 태그 정의
                        sh "kubectl set image deployment/${module} ${module}=${imageTag}"  // Kubernetes에 이미지 배포
                    }
                }
            }
        }
    }

    post {
        success {
            echo 'Deployment successful!'
        }
        failure {
            echo 'Deployment failed.'
        }
    }
}