package me.oxince.db;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.MongoIterable;
import me.oxince.lobbysystem.LobbySystem;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.bukkit.Bukkit;

import java.util.ArrayList;

public class DatabaseController {
    private final MongoClient mongoClient;
    private final MongoDatabase database;

    public DatabaseController(String mongoUrl, String databaseName, String[] collections) {
        MongoClientURI clientUrl = new MongoClientURI(mongoUrl);

        mongoClient = new MongoClient(clientUrl);
        database = mongoClient.getDatabase(databaseName);

        MongoIterable<String> collectionNames = database.listCollectionNames();

        if (collections != null && collections.length != 0) {
            for (String collection : collections) {
                if (!collectionNames.into(new ArrayList<String>()).contains(collection)) {
                    database.createCollection(collection);
                }
            }
        }

        Bukkit.getConsoleSender().sendMessage(LobbySystem.formatMessage("§aConnected to MongoDB Server on %s", true, clientUrl.getHosts().get(0)));
    }

    public Document findFirst(String collection, Bson bson) {
        return (Document) this.getCollection(collection).find(bson).first();
    }

    public MongoCollection<Document> getCollection(String collection) {
        return database.getCollection(collection);
    }

    public MongoDatabase getDatabase() {
        return database;
    }

    public MongoClient getMongoClient() {
        return mongoClient;
    }
}