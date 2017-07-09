# The purpose is to package the api service image.
FROM openjdk:8-alpine
MAINTAINER "Live platform Team" <hw-live@thoughtworks.com>

# Prepare work directory and copy all files
ENV APP_DIR /app

WORKDIR $APP_DIR
COPY build.gradle .
COPY gradle gradle
COPY gradlew .
RUN ./gradlew clean
COPY src src

# Build jar
RUN ./gradlew build bootRepackage --info

COPY startup.sh .
RUN chmod u+x startup.sh \
    && mv $APP_DIR/build/libs/*.jar .

# Remove all source code
RUN rm -rf src \
    && rm -rf gradle \
    && rm -rf build.gradle \
    && rm -rf build \
    && rm -rf gradlew

EXPOSE 8080

HEALTHCHECK CMD wget -qO- http://localhost:8080/health || exit 1

CMD ["./startup.sh"]