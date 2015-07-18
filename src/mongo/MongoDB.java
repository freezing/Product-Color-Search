package mongo;

import org.mongodb.morphia.Datastore;

import com.mongodb.MongoClient;

public class MongoDB {

    private static MongoDBProvider instance = null;

    public static MongoDBProvider getInstance() {
        if (instance == null) {
                instance = new LocalhostMongoDBProvider();
        }
        return instance;
    }
    
    private MongoDB() {}
    
    public static void setInstance(MongoDBProvider instance) {
		MongoDB.instance = instance;
	}
    
    public static MongoClient getMongoClient() {
        return MongoDB.getInstance().getMongoClient();
    }
    
    public static Datastore getDatastore () {
        return MongoDB.getInstance().getMorphiaDatastore();
    }
}