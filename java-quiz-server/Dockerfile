# Use amazoncorretto's openjdk 17
FROM docker.io/library/amazoncorretto:17

# Limit the work directory to /app
WORKDIR /app

# Copy source to container
COPY src/ .
COPY jar/ .

# Extract jar
RUN jar xf json-simple-1.1.1.jar
RUN jar xf postgresql-42.6.0.jar

# Compile source
RUN javac businesslogic/*.java dataaccess/*.java domain/*.java
RUN javac Program.java

# Entry: run compiled app 
CMD java Program
