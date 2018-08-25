package com.project.sharemarket.domains;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "batch_job_execution_params", catalog = "share_market")
public class BatchJobExecutionParams implements java.io.Serializable {

	private BatchJobExecutionParamsId id;
	private BatchJobExecution batchJobExecution;

	public BatchJobExecutionParams() {
	}

	public BatchJobExecutionParams(BatchJobExecutionParamsId id, BatchJobExecution batchJobExecution) {
		this.id = id;
		this.batchJobExecution = batchJobExecution;
	}

	@EmbeddedId

	@AttributeOverrides({
			@AttributeOverride(name = "jobExecutionId", column = @Column(name = "JOB_EXECUTION_ID", nullable = false)),
			@AttributeOverride(name = "typeCd", column = @Column(name = "TYPE_CD", nullable = false, length = 6)),
			@AttributeOverride(name = "keyName", column = @Column(name = "KEY_NAME", nullable = false, length = 100)),
			@AttributeOverride(name = "stringVal", column = @Column(name = "STRING_VAL", length = 250)),
			@AttributeOverride(name = "dateVal", column = @Column(name = "DATE_VAL", length = 19)),
			@AttributeOverride(name = "longVal", column = @Column(name = "LONG_VAL")),
			@AttributeOverride(name = "doubleVal", column = @Column(name = "DOUBLE_VAL", precision = 22, scale = 0)),
			@AttributeOverride(name = "identifying", column = @Column(name = "IDENTIFYING", nullable = false, length = 1)) })
	public BatchJobExecutionParamsId getId() {
		return this.id;
	}

	public void setId(BatchJobExecutionParamsId id) {
		this.id = id;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "JOB_EXECUTION_ID", nullable = false, insertable = false, updatable = false)
	public BatchJobExecution getBatchJobExecution() {
		return this.batchJobExecution;
	}

	public void setBatchJobExecution(BatchJobExecution batchJobExecution) {
		this.batchJobExecution = batchJobExecution;
	}

}
