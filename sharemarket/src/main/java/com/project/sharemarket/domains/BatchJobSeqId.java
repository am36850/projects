package com.project.sharemarket.domains;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class BatchJobSeqId implements java.io.Serializable {

	private long id;
	private char uniqueKey;

	public BatchJobSeqId() {
	}

	public BatchJobSeqId(long id, char uniqueKey) {
		this.id = id;
		this.uniqueKey = uniqueKey;
	}

	@Column(name = "ID", nullable = false)
	public long getId() {
		return this.id;
	}

	public void setId(long id) {
		this.id = id;
	}

	@Column(name = "UNIQUE_KEY", unique = true, nullable = false, length = 1)
	public char getUniqueKey() {
		return this.uniqueKey;
	}

	public void setUniqueKey(char uniqueKey) {
		this.uniqueKey = uniqueKey;
	}

	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof BatchJobSeqId))
			return false;
		BatchJobSeqId castOther = (BatchJobSeqId) other;

		return (this.getId() == castOther.getId()) && (this.getUniqueKey() == castOther.getUniqueKey());
	}

	public int hashCode() {
		int result = 17;

		result = 37 * result + (int) this.getId();
		result = 37 * result + this.getUniqueKey();
		return result;
	}

}
