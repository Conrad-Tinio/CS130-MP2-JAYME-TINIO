FROM eclipse-temurin:11-jdk AS build
WORKDIR /app
COPY src/ /app/src/
RUN mkdir -p target/classes && \
    javac -d target/classes src/*.java && \
    echo "Main-Class: FlipFlopSimulator" > manifest.txt && \
    jar cvfm cs130mp2jaymetinio.jar manifest.txt -C target/classes .

FROM eclipse-temurin:11-jre
COPY --from=build /app/cs130mp2jaymetinio.jar cs130mp2jaymetinio.jar
ENTRYPOINT ["java", "-jar", "cs130mp2jaymetinio.jar"]
