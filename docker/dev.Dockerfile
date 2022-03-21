FROM openjdk:17-oracle as builder
WORKDIR /usr/surepay

COPY target/fraud-0.0.1-SNAPSHOT.jar fraud-0.0.1-SNAPSHOT.jar
ARG JAR_FILE=target/fraud-0.0.1-SNAPSHOT.jar
COPY ${JAR_FILE} fraud-0.0.1-SNAPSHOT.jar
RUN java -Djarmode=layertools -jar fraud-0.0.1-SNAPSHOT.jar extract

RUN groupadd surepay && useradd -g surepay surepay
USER surepay

RUN mkdir -p /var/log

COPY --from=builder /usr/surepay/dependencies/ ./
COPY --from=builder /usr/surepay/spring-boot-loader/ ./
COPY --from=builder /usr/surepay/snapshot-dependencies/ ./
COPY --from=builder /usr/surepay/application/ ./

ENTRYPOINT ["java","-noverify", "org.springframework.boot.loader.JarLauncher"]