nohup process & java -Xmx512m -Xss256k -Dserver.port=9080 -Dcom.example.gateway.microservice-url1=http://10.242.131.203:9080 -jar netty-reactive/netty-reactive-gateway/target/netty-reactive-gateway-0.0.1.jar 