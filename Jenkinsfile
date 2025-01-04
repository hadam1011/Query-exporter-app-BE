def FAILED_STAGE

pipeline {
    agent any
    
    environment {
        DOCKERHUB_CREDENTIALS = credentials('dockerhub_login')
        GITHUB_CREDENTIALS = credentials('github_login')
        GITHUB_TOKEN = credentials('github_token')
        DOCKERHUB_REPO = 'mad1011/query-exporter-app'
    }

    stages {
        // stage('SonarCloud analysis') {
        //     environment {
        //         scannerHome = tool 'Sonarqube scanner'
        //     }

        //     steps {
        //         powershell 'Copy-Item -Path "pom-analysis.xml" -Destination "pom.xml" -Force'

        //         withSonarQubeEnv(credentialsId: 'sonarcloud_token', installationName: 'SonarCloud') {
        //             withMaven {
        //                 bat 'mvn verify org.sonarsource.scanner.maven:sonar-maven-plugin:sonar -Dsonar.projectKey=hadam1011_Query-exporter-app-BE'
        //             }
        //         }
        //     }
        // }

        stage ('Build') {
            steps {
                // Build image
                script {
                    FAILED_STAGE = env.STAGE_NAME
                }
                bat "docker build -t ${DOCKERHUB_REPO}:backend-${BUILD_NUMBER} ."
            }
        }

        stage ('Push image to Docker Hub') {
            steps {
                // Push image to Docker Hub
                script {
                    FAILED_STAGE = env.STAGE_NAME
                }
                bat """
                    docker login -u ${DOCKERHUB_CREDENTIALS_USR} -p ${DOCKERHUB_CREDENTIALS_PSW}
                    docker push ${DOCKERHUB_REPO}:backend-${BUILD_NUMBER}
                """
            }
        }

        stage ('Deploy') {
            steps {
                script {
                    FAILED_STAGE = env.STAGE_NAME
                }
                bat """
                    git config user.email "hadam8910@gmail.com"
                    git config user.name "hadam1011"
                    powershell -Command "(Get-Content deployments/backend-deployment.yaml) -replace 'imageVersion', ${BUILD_NUMBER} | Out-File -encoding ASCII deployments/backend-deployment.yaml"
                    
                    git clone https://github.com/hadam1011/manifests.git
                    copy deployments\\backend-deployment.yaml manifests\\query-exporter-app\\backend-deployment.yaml

                    cd manifests
                    git config user.email "hadam8910@gmail.com"
                    git config user.name "hadam1011"
                    git add .
                    git commit -m "Update backend deployment image to version ${BUILD_NUMBER}"
                    git push https://${GITHUB_TOKEN}@github.com/hadam1011/manifests

                    cd ..
                    rmdir /s /q manifests
                """
            }
        }
    }

    post{
        success{
            bat """
                echo ${FAILED_STAGE}
                curl -s -X POST https://api.telegram.org/bot7932959424:AAEfe8M7DCJ9G0-r5nx9ze8sEQvcIGwtUp0/sendMessage -d chat_id="-4657156617" -d text="[SUCCESSED] Query-exporter-app BE pipeline run successfully!" 
            """
        }
        failure{
            script {
                def STAGE = FAILED_STAGE
                bat """ 
                    set "STAGE=${STAGE}"
                    curl -s -X POST https://api.telegram.org/bot7932959424:AAEfe8M7DCJ9G0-r5nx9ze8sEQvcIGwtUp0/sendMessage -d chat_id="-4657156617" -d text="[FAILED] Query-exporter-app BE pipeline has failed at stage %STAGE%!" 
                """
            }
        }
    }
}
