package com.project.sharemarket.domains;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

@Entity
@Table(name = "batch_job_execution_context", catalog = "share_market")
public class BatchJobExecutionContext implements java.io.Serializable {

	private long jobExecutionId;
	private BatchJobExecution batchJobExecution;
	private String shortContext;
	private String serializedContext;

	public BatchJobExecutionContext() {
	}

	public BatchJobExecutionContext(BatchJobExecution batchJobExecution, String shortContext) {
		this.batchJobExecution = batchJobExecution;
		this.shortContext = shortContext;
	}

	public BatchJobExecutionContext(BatchJobExecution batchJobExecution, String shortContext,
			String serializedContext) {
		this.batchJobExecution = batchJobExecution;
		this.shortContext = shortContext;
		this.serializedContext = serializedContext;
	}

	@GenericGenerator(name = "BatchJobExecutionContextIdGenerator", strategy = "foreign", parameters = @Parameter(name = "property", value = "batchJobExecution"))
	@Id
	@GeneratedValue(generator = "BatchJobExecutionContextIdGenerator")

	@Column(name = "JOB_EXECUTION_ID", unique = true, nullable = false)
	public long getJobExecutionId() {
		return this.jobExecutionId;
	}

	public void setJobExecutionId(long jobExecutionId) {
		this.jobExecutionId = jobExecutionId;
	}

	@OneToOne(fetch = FetchType.LAZY)
	@PrimaryKeyJoinColumn
	public BatchJobExecution getBatchJobExecution() {
		return this.batchJobExecution;
	}

	public void setBatchJobExecution(BatchJobExecution batchJobExecution) {
		this.batchJobExecution = batchJobExecution;
	}

	@Column(name = "SHORT_CONTEXT", nullable = false, length = 2500)
	public String getShortContext() {
		return this.shortContext;
	}

	public void setShortContext(String shortContext) {
		this.shortContext = shortContext;
	}

	@Column(name = "SERIALIZED_CONTEXT", length = 65535)
	public String getSerializedContext() {
		return this.serializedContext;
	}

	public void setSerializedContext(String serializedContext) {
		this.serializedContext = serializedContext;
	}

}
