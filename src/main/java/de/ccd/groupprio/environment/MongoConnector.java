package de.ccd.groupprio.environment;

import com.mongodb.DB;
import com.mongodb.MongoClient;
import com.mongodb.ServerAddress;
import lombok.SneakyThrows;

import java.net.UnknownHostException;

public class MongoConnector {
    public static DB connectMongoDb() throws UnknownHostException {
        var mongoHost = System.getenv().getOrDefault("MONGO_HOST", "127.0.0.1");

        System.out.println("Connecting to mongoDB at " + mongoHost);
        ServerAddress addr = new ServerAddress(mongoHost);
        var mongoClient = new MongoClient(addr);

        return mongoClient.getDB("groupprio");
    }
}
