package mongo;

import com.mongodb.MongoClient;
import com.mongodb.ServerAddress;

public class LocalhostMongoDBProvider extends AbstractMongoDBProvider{
	
	 private static String DB_NAME = "diplomskidb";
	 private static String HOST_NAME = "localhost";
	 private static int PORT_NUMBER = 27017;
	 
	 public LocalhostMongoDBProvider() {
		 ServerAddress server = new ServerAddress(HOST_NAME, PORT_NUMBER);
	     mongoClient = new MongoClient(server);
	     imageDb = mongoClient.getDatabase(DB_NAME);
	     initMorphia();
	 }
	@Override
	public boolean isTestInstance() {
		return false;
	}
}