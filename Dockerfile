FROM ubuntu:18.04
RUN apt update && apt install -y git mc openjdk-11-jdk maven
RUN apt install -y git
RUN  git clone https://github.com/rubanovmaxim/bookstore.git -b homework_7 && \
cd bookstore && \
mvn clean package
CMD ["java", "-jar", "/bookstore/target/bookstore-1.0-SNAPSHOT.jar"]