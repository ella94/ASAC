### build stage ###
FROM openjdk:17-jdk-alpine as build

# set arg
ARG WORKSPACE=.
ARG BUILD_TARGET=${WORKSPACE}/build/libs
WORKDIR ${WORKSPACE}

# copy code & build
COPY . .
RUN ./gradlew clean bootJar

# unpack jar
WORKDIR ${BUILD_TARGET}
RUN jar -xf *.jar

### create image stage ###
FROM openjdk:17-jdk-alpine

ARG BUILD_TARGET=./build/libs
ARG DEPLOY_PATH=./deploy

# copy from build stage
COPY --from=build ${BUILD_TARGET}/org ${DEPLOY_PATH}/org
COPY --from=build ${BUILD_TARGET}/BOOT-INF/lib ${DEPLOY_PATH}/BOOT-INF/lib
COPY --from=build ${BUILD_TARGET}/META-INF ${DEPLOY_PATH}/META-INF
COPY --from=build ${BUILD_TARGET}/BOOT-INF/classes ${DEPLOY_PATH}/BOOT-INF/classes

WORKDIR ${DEPLOY_PATH}

EXPOSE 8080
ENTRYPOINT ["java","org.springframework.boot.loader.JarLauncher"]