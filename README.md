# stub (Client) code
## 이 프로젝트는 클라이언트 소스 입니다.
- proto buf를 만들었고 클라이언트 소스를 만들어 연동 합니다.
- 해당 소스는 SpringBoot기반 MVC 패턴 -> gRPC stub 호출 -> gRPC server 호출 등의 flow로 진행 됩니다.

## build.gradle
implementation 'com.kakao.www:grpc-test:0.0.1-SNAPSHOT'로 proto buf를 디펜던시를 가져오는데
이것은  mavenLocal()에서 가져 옵니다.

```
plugins {
    id 'java'
    id 'org.springframework.boot' version '3.2.0'
    id 'io.spring.dependency-management' version '1.1.4'
}
 
group = 'com.kakao.www'
version = '0.0.1-SNAPSHOT'
 
java {
    sourceCompatibility = '21'
}
 
configurations {
    compileOnly {
        extendsFrom annotationProcessor
    }
}
 
repositories {
    mavenLocal()
    mavenCentral()
}
 
dependencies {
    implementation 'com.kakao.www:grpc-test:0.0.1-SNAPSHOT'
    implementation 'org.springframework.boot:spring-boot-starter-web'
    compileOnly 'org.projectlombok:lombok'
    developmentOnly 'org.springframework.boot:spring-boot-devtools'
    annotationProcessor 'org.projectlombok:lombok'
    testImplementation 'org.springframework.boot:spring-boot-starter-test'
 
    // grpc 관련 셋팅
    //https://mvnrepository.com/artifact/io.grpc/grpc-stub
    implementation 'io.grpc:grpc-stub:1.60.0'
 
    // https://mvnrepository.com/artifact/com.google.protobuf/protobuf-java
    implementation 'com.google.protobuf:protobuf-java:3.25.1'
 
}
 
tasks.named('test') {
    useJUnitPlatform()
}
```

