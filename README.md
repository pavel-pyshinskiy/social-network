#Social network app

## Requirements
- Java 11
- Docker
- 8080 port is free

## launch instructions
- cd ${project_location}/docker
- docker compose up -d
- cd ..
- .\gradlew.bat build
- java -jar ./build/libs/social-network-0.0.1-SNAPSHOT.jar

## api testing
run "social_network_postman_collection.json" postman collection located in project root directory