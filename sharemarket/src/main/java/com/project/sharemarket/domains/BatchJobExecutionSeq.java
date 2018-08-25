package com.project.sharemarket.domains;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@Table(name = "batch_job_execution_seq", catalog = "share_market", uniqueConstraints = @UniqueConstraint(columnNames = "UNIQUE_KEY"))
public class BatchJobExecutionSeq implements java.io.Serializable {

	private BatchJobExecutionSeqId id;

	public BatchJobExecutionSeq() {
	}

	public BatchJobExecutionSeq(BatchJobExecutionSeqId id) {
		this.id = id;
	}

	@EmbeddedId

	@AttributeOverrides({ @AttributeOverride(name = "id", column = @Column(name = "ID", nullable = false)),
			@AttributeOverride(name = "uniqueKey", column = @Column(name = "UNIQUE_KEY", unique = true, nullable = false, length = 1)) })
	public BatchJobExecutionSeqId getId() {
		return this.id;
	}

	public void setId(BatchJobExecutionSeqId id) {
		this.id = id;
	}

}
