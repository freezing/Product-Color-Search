package mongo;

import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Morphia;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoDatabase;

public interface MongoDBProvider {
	MongoClient getMongoClient();
	MongoDatabase getBucketadoDatabase();
	Datastore getMorphiaDatastore();
	Morphia getMorphia();
	boolean isTestInstance();
}