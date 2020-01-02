FROM gradle:6.0.1-jdk11  as builder
USER root
COPY ./src /app/src
COPY ./build.gradle settings.gradle /app/
RUN gradle -p /app clean build -x test

FROM adoptopenjdk/openjdk11:alpine-jre
COPY --from=builder /app/build/libs/*.jar /app/app.jar

RUN apk add --no-cache --update \
    openssl \
    curl \
    bash \
    tini \
    wget \
    tzdata \
    postgresql-client \
    py-pip \
    && pip install s3cmd

RUN wget -O /app/dd-java-agent.jar 'https://search.maven.org/classic/remote_content?g=com.datadoghq&a=dd-java-agent&v=LATEST'
ARG flyway_version="5.2.4"
RUN curl -fsSL https://repo1.maven.org/maven2/org/flywaydb/flyway-commandline/${flyway_version}/flyway-commandline-${flyway_version}-linux-x64.tar.gz \
    | tar xvz -C / && ln -s /flyway-${flyway_version}/flyway /usr/local/bin

ENV TZ=Europe/London
RUN cp /usr/share/zoneinfo/Europe/London /etc/localtime

COPY docker_entrypoint.sh /docker_entrypoint.sh
WORKDIR /app

ENTRYPOINT [ "/sbin/tini", "--", "/docker_entrypoint.sh"]
CMD ["java", "-jar", "/app/app.jar"]



