package model;

import org.bson.types.ObjectId;
import org.mongodb.morphia.annotations.Id;
import org.mongodb.morphia.annotations.Version;

public class AbstractModel {

	@Id
	protected ObjectId id;

	@Version 
	protected long version;
	
	protected AbstractModel() {
	}

	protected AbstractModel(ObjectId id) {
		this.id = id;
	}

	protected AbstractModel(String id) {
		this.id = new ObjectId(id);
	}

	public ObjectId getId() {
		return id;
	}

	public AbstractModel setId(ObjectId id) {
		this.id = id;
		return this;
	}
	
	public AbstractModel setId(String id) {
		this.id = new ObjectId(id);
		return this;
	}

	public int creationTimestamp() {
		return id.getTimestamp();
	}

	public long getVersion() {
		return version;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		AbstractModel other = (AbstractModel) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
}