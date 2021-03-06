package drivers;

import org.bson.types.ObjectId;
import org.mongodb.morphia.query.Query;
import org.mongodb.morphia.query.UpdateOperations;

import model.Url;

public class UrlDriver extends MorphiaAbstractModelDriver<Url> {

	private static UrlDriver instance = null;

	public static UrlDriver getInstance() {
		if (instance == null) {
			instance = new UrlDriver();
		}
		return instance;
	}
	
	public UrlDriver() {
		super(Url.class);
	}
	
	public Url getNonProcessed() {
		return createQuery().field("isProcessed").equal(false).get();
	}
	
	public Url getNonQueued() {
		return createQuery().field("isInQueue").equal(false).get();
	}
	
	public boolean setProcessed(ObjectId id) {
		UpdateOperations<Url> ops = createUpdateOperations();
		ops.set("isProcessed", true);
		Query<Url> query = createQuery().field("_id").equal(id);
		return getDatastore().update(query, ops).getUpdatedExisting();
	}
	
	public boolean setIsInQueue(ObjectId id) {
		UpdateOperations<Url> ops = createUpdateOperations();
		ops.set("isInQueue", true);
		Query<Url> query = createQuery().field("_id").equal(id);
		return getDatastore().update(query, ops).getUpdatedExisting();
	}

	public boolean refreshAll() {
		UpdateOperations<Url> ops = createUpdateOperations();
		ops.set("isProcessed", false);
		ops.set("isInQueue", false);
		Query<Url> query = createQuery();
		return getDatastore().update(query, ops).getUpdatedExisting();
	}

	public boolean removeAll() {
		Query<Url> query = createQuery();
		return getDatastore().delete(query).isUpdateOfExisting();
	}
}
