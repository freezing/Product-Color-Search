package drivers;

import java.util.Iterator;
import java.util.List;

import org.bson.types.ObjectId;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Key;
import org.mongodb.morphia.query.Query;
import org.mongodb.morphia.query.UpdateOperations;

import model.AbstractModel;
import mongo.MongoDB;

public abstract class MorphiaAbstractModelDriver<T extends AbstractModel> {

	protected Class<T> forClass;

	protected MorphiaAbstractModelDriver(Class<T> forClass) {
		this.forClass = forClass;
	}

	protected Class<T> getForClass() {
		return forClass;
	}

	protected Datastore getDatastore() {
		return MongoDB.getDatastore();
	}

	protected Query<T> createQuery() {
		return getDatastore().createQuery(forClass);
	}

	protected UpdateOperations<T> createUpdateOperations() {
		return getDatastore().createUpdateOperations(forClass);
	}

	public T get (T entity){
		return getDatastore().get(entity);
	}
	public T get(ObjectId id) {
		return getDatastore().get(forClass, id);
	}

	public T get(String id) {
		return get(new ObjectId(id));
	}

	public List<T> getAsList(int limit, int offset) {
		return getDatastore().find(forClass).limit(limit).offset(offset).asList();
	}
	
	public ObjectId save(T toSave) {
		return (ObjectId) getDatastore().save(toSave).getId();
	}
	public Iterable<Key<T>> save(List<T> list){
		return getDatastore().save(list);
	}

	public boolean remove(ObjectId id) {
		return getDatastore().delete(forClass, id).isUpdateOfExisting();
	}

	public boolean remove(String id) {
		return remove(new ObjectId(id));
	}
	public boolean remove(T toRemove){
		return getDatastore().delete(toRemove).isUpdateOfExisting();
	}
	
	public Iterator<T> getIterator() {
		return getDatastore().find(forClass).iterator();
	}
}