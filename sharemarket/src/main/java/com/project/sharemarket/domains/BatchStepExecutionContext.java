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
@Table(name = "batch_step_execution_context", catalog = "share_market")
public class BatchStepExecutionContext implements java.io.Serializable {

	private long stepExecutionId;
	private BatchStepExecution batchStepExecution;
	private String shortContext;
	private String serializedContext;

	public BatchStepExecutionContext() {
	}

	public BatchStepExecutionContext(BatchStepExecution batchStepExecution, String shortContext) {
		this.batchStepExecution = batchStepExecution;
		this.shortContext = shortContext;
	}

	public BatchStepExecutionContext(BatchStepExecution batchStepExecution, String shortContext,
			String serializedContext) {
		this.batchStepExecution = batchStepExecution;
		this.shortContext = shortContext;
		this.serializedContext = serializedContext;
	}

	@GenericGenerator(name = "BatchStepExecutionContextIdGenerator", strategy = "foreign", parameters = @Parameter(name = "property", value = "batchStepExecution"))
	@Id
	@GeneratedValue(generator = "BatchStepExecutionContextIdGenerator")

	@Column(name = "STEP_EXECUTION_ID", unique = true, nullable = false)
	public long getStepExecutionId() {
		return this.stepExecutionId;
	}

	public void setStepExecutionId(long stepExecutionId) {
		this.stepExecutionId = stepExecutionId;
	}

	@OneToOne(fetch = FetchType.LAZY)
	@PrimaryKeyJoinColumn
	public BatchStepExecution getBatchStepExecution() {
		return this.batchStepExecution;
	}

	public void setBatchStepExecution(BatchStepExecution batchStepExecution) {
		this.batchStepExecution = batchStepExecution;
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
