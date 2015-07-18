package mongo;

import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Morphia;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoDatabase;

public abstract class AbstractMongoDBProvider implements MongoDBProvider{
	protected MongoDatabase imageDb;
	protected MongoClient mongoClient;
	protected Morphia morphia;
	protected Datastore morphiaDatastore;
	
	protected AbstractMongoDBProvider (){
		
	}
	protected AbstractMongoDBProvider(MongoDatabase imageDb, MongoClient mongoClient) {
		this.imageDb = imageDb;
		this.mongoClient = mongoClient;
		initMorphia();
	}
	protected void initMorphia(){
		morphia = new Morphia();
		morphia.mapPackage("model");

		morphiaDatastore = morphia.createDatastore(mongoClient, imageDb.getName());
		morphiaDatastore.ensureIndexes();
	}
	@Override
	public MongoDatabase getBucketadoDatabase() {
		return imageDb;
	}
	@Override
	public MongoClient getMongoClient() {
		return mongoClient;
	}
	@Override
	public Morphia getMorphia() {
		return morphia;
	}
	@Override
	public Datastore getMorphiaDatastore() {
		return morphiaDatastore;
	}
}