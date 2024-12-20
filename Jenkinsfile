pipeline {
    agent any

    environment {
        JAVA_HOME = '/usr/lib/jvm/java-17-openjdk-amd64'
        repository = "meteoriver/paran"
        DOCKERHUB_CREDENTIALS = credentials('paran-docker') // Docker Hub credentials
        dockerImage = ''
    }

    stages {
        stage('Clean Workspace') {
            steps {
                script {
                    sh 'rm -rf server/config-server'
                }
            }
        }
        stage('Checkout SCM') {
            steps {
                checkout([$class: 'GitSCM',
                    branches: [[name: '*/main']],
                    userRemoteConfigs: [[url: "git@github.com:MeteoRiver/paran-msa.git", credentialsId:"paran-git"]],
                    extensions: [[$class: 'SubmoduleOption', recursiveSubmodules: true, parentCredentials: true]]
                ])
                sh 'git submodule update --init --recursive'
            }
        }

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
                sh 'echo "BUILD_NUMBER=${BUILD_NUMBER}"'

                sh 'docker-compose up -d --build'
                sh 'docker images' // 현재 빌드된 이미지 확인

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
                        def imageTag = "meteoriver/paran:${module}-${env.BUILD_ID}"
                        def sourceImageTag = "meteoriver/paran:${module}-${env.BUILD_NUMBER}"

                        sh "docker images"

                        echo "Tagging and pushing ${imageTag}"  // 디버그 메시지
                        sh "docker tag ${sourceImageTag} ${imageTag}"  // 이미지 태그
                        sh "docker push ${imageTag}"  // Docker Hub에 푸시

                    }
                }
            }
        }
        stage('Clean Docker Images') {
            steps {
                script {
                    def modules = ["config", "eureka", "user", "group", "chat", "file", "room", "comment", "gateway"]
                    def previousBuildNumber = "${BUILD_NUMBER}".toInteger() - 1  // 이전 빌드 번호 계산

                    for (module in modules) {
                        // 특정 조건을 평가하여 이미지 제거
                        def imageTag = "meteoriver/paran:${module}-${previousBuildNumber}"
                        if (sh(script: "docker images -q ${imageTag}", returnStdout: true).trim()) {
                            sh "docker rmi ${imageTag}"
                        }
                        sh "curl -X DELETE -u '${DOCKERHUB_CREDENTIALS_USR}:${DOCKERHUB_CREDENTIALS_PSW}' https://hub.docker.com/v2/repositories/meteoriver/paran/tags/${module}-${previousBuildNumber}/"
                    }
                }
            }
        }
        stage('Deploy') {
            steps {
                script {
                    withEnv(["KUBECONFIG=/var/lib/jenkins/kubeconfig.yaml"]) {
                        // Secrets 확인
                        sh 'kubectl -n default get secrets'

                        try {
                           // ${BUILD_NUMBER}를 실제 값으로 변경
                           def deployConfig = sh(script: "sed 's/\\\${BUILD_NUMBER}/${BUILD_NUMBER}/g' ./config-deploy.yaml", returnStdout: true).trim()
                           echo "Deploying config-deploy.yaml with the following config: \n${deployConfig}"
                           sh "echo '${deployConfig}' | kubectl apply -f -"

                           // nks-deploy.yaml 배포
                           def nksDeployConfig = sh(script: "sed 's/\\\${BUILD_NUMBER}/${BUILD_NUMBER}/g' ./nks-deploy.yaml", returnStdout: true).trim()
                           echo "Deploying nks-deploy.yaml with the following config: \n${nksDeployConfig}"
                           sh "echo '${nksDeployConfig}' | kubectl apply -f -"

                           // 배포 후 Pods 상태 확인
                           sh 'kubectl get pods -n default'
                       } catch (Exception e) {
                           echo "Deployment failed: ${e.getMessage()}"
                           currentBuild.result = 'FAILURE'
                           error("Aborting the build due to deployment failure.")
                       }
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