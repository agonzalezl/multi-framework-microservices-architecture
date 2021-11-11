FROM openjdk:15

ADD target/inventory-bootable.jar /app.jar

CMD java -jar -Djboss.bind.address=0.0.0.0 -Djboss.http.port=8080 /app.jar