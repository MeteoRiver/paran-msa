pipeline {
    agent any

    environment {
        JAVA_HOME = '/opt/java/openjdk'
        repository = "meteoriver/paran"  //docker hub id와 repository 이름
        DOCKERHUB_CREDENTIALS = credentials('paran-docker') // jenkins에 등록해 놓은 docker hub credentials 이름
        dockerImage = ''
    }

    stages {

        stage('Build') {
            steps {
                script {
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
                      ./gradlew :$module:bootJar
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
        stage('Login'){
            steps{
                sh 'echo $DOCKERHUB_CREDENTIALS_PSW | docker login -u $DOCKERHUB_CREDENTIALS_USR --password-stdin' // docker hub 로그인
            }
        }
        stage('Push to Docker Hub') {
            steps {
                script {
                    /*withDockerRegistry([url:'https://registry.hub.docker.com', credentialsId:'paran-docker']) {
                        sh "docker push meteoriver/paran:config-${env.BUILD_ID}"
                        sh "docker push meteoriver/paran:eureka-${env.BUILD_ID}"
                        sh "docker push meteoriver/paran:user-${env.BUILD_ID}"
                        sh "docker push meteoriver/paran:group-${env.BUILD_ID}"
                        sh "docker push meteoriver/paran:chat-${env.BUILD_ID}"
                        sh "docker push meteoriver/paran:file-${env.BUILD_ID}"
                        sh "docker push meteoriver/paran:room-${env.BUILD_ID}"
                        sh "docker push meteoriver/paran:comment-${env.BUILD_ID}"
                        sh "docker push meteoriver/paran:gateway-${env.BUILD_ID}" */

                         def modules = ["config", "eureka", "user", "group", "chat", "file", "room", "comment", "gateway"]

                        for (module in modules) {
                            def imageTag = "meteoriver/paran:${module}-${env.BUILD_ID}"  // 저장소에 푸시할 이미지 태그
                            echo "Tagging and pushing ${imageTag}"  // 디버그 메시지 추가
                            sh "docker push ${imageTag}"  // 이미지를 Docker Hub에 푸시
                        }
                    //}
                }
            }
        }
        stage('Cleaning up') {
            steps {
                sh "docker rmi $repository:$BUILD_NUMBER" // docker image 제거
            }
        }

        stage('Deploy to Kubernetes') {
            steps {
                script {
                    def modules = ["gateway", "config", "eureka", "user", "group", "chat", "file", "room", "comment"]

                    for (module in modules) {
                        sh "kubectl set image deployment/${module} ${module}=meteoriver/paran:${module}-${env.BUILD_ID}"  // paran 저장소에서 이미지 배포
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