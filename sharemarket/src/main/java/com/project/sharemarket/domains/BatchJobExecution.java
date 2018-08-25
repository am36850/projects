package com.project.sharemarket.domains;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Version;

@Entity
@Table(name = "batch_job_execution", catalog = "share_market")
public class BatchJobExecution implements java.io.Serializable {

	private long jobExecutionId;
	private Long version;
	private BatchJobInstance batchJobInstance;
	private Date createTime;
	private Date startTime;
	private Date endTime;
	private String status;
	private String exitCode;
	private String exitMessage;
	private Date lastUpdated;
	private String jobConfigurationLocation;
	private Set<BatchStepExecution> batchStepExecutions = new HashSet<BatchStepExecution>(0);
	private Set<BatchJobExecutionParams> batchJobExecutionParamses = new HashSet<BatchJobExecutionParams>(0);
	private BatchJobExecutionContext batchJobExecutionContext;

	public BatchJobExecution() {
	}

	public BatchJobExecution(long jobExecutionId, BatchJobInstance batchJobInstance, Date createTime) {
		this.jobExecutionId = jobExecutionId;
		this.batchJobInstance = batchJobInstance;
		this.createTime = createTime;
	}

	public BatchJobExecution(long jobExecutionId, BatchJobInstance batchJobInstance, Date createTime, Date startTime,
			Date endTime, String status, String exitCode, String exitMessage, Date lastUpdated,
			String jobConfigurationLocation, Set<BatchStepExecution> batchStepExecutions,
			Set<BatchJobExecutionParams> batchJobExecutionParamses, BatchJobExecutionContext batchJobExecutionContext) {
		this.jobExecutionId = jobExecutionId;
		this.batchJobInstance = batchJobInstance;
		this.createTime = createTime;
		this.startTime = startTime;
		this.endTime = endTime;
		this.status = status;
		this.exitCode = exitCode;
		this.exitMessage = exitMessage;
		this.lastUpdated = lastUpdated;
		this.jobConfigurationLocation = jobConfigurationLocation;
		this.batchStepExecutions = batchStepExecutions;
		this.batchJobExecutionParamses = batchJobExecutionParamses;
		this.batchJobExecutionContext = batchJobExecutionContext;
	}

	@Id

	@Column(name = "JOB_EXECUTION_ID", unique = true, nullable = false)
	public long getJobExecutionId() {
		return this.jobExecutionId;
	}

	public void setJobExecutionId(long jobExecutionId) {
		this.jobExecutionId = jobExecutionId;
	}

	@Version
	@Column(name = "VERSION")
	public Long getVersion() {
		return this.version;
	}

	public void setVersion(Long version) {
		this.version = version;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "JOB_INSTANCE_ID", nullable = false)
	public BatchJobInstance getBatchJobInstance() {
		return this.batchJobInstance;
	}

	public void setBatchJobInstance(BatchJobInstance batchJobInstance) {
		this.batchJobInstance = batchJobInstance;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "CREATE_TIME", nullable = false, length = 19)
	public Date getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "START_TIME", length = 19)
	public Date getStartTime() {
		return this.startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "END_TIME", length = 19)
	public Date getEndTime() {
		return this.endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	@Column(name = "STATUS", length = 10)
	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Column(name = "EXIT_CODE", length = 2500)
	public String getExitCode() {
		return this.exitCode;
	}

	public void setExitCode(String exitCode) {
		this.exitCode = exitCode;
	}

	@Column(name = "EXIT_MESSAGE", length = 2500)
	public String getExitMessage() {
		return this.exitMessage;
	}

	public void setExitMessage(String exitMessage) {
		this.exitMessage = exitMessage;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "LAST_UPDATED", length = 19)
	public Date getLastUpdated() {
		return this.lastUpdated;
	}

	public void setLastUpdated(Date lastUpdated) {
		this.lastUpdated = lastUpdated;
	}

	@Column(name = "JOB_CONFIGURATION_LOCATION", length = 2500)
	public String getJobConfigurationLocation() {
		return this.jobConfigurationLocation;
	}

	public void setJobConfigurationLocation(String jobConfigurationLocation) {
		this.jobConfigurationLocation = jobConfigurationLocation;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "batchJobExecution")
	public Set<BatchStepExecution> getBatchStepExecutions() {
		return this.batchStepExecutions;
	}

	public void setBatchStepExecutions(Set<BatchStepExecution> batchStepExecutions) {
		this.batchStepExecutions = batchStepExecutions;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "batchJobExecution")
	public Set<BatchJobExecutionParams> getBatchJobExecutionParamses() {
		return this.batchJobExecutionParamses;
	}

	public void setBatchJobExecutionParamses(Set<BatchJobExecutionParams> batchJobExecutionParamses) {
		this.batchJobExecutionParamses = batchJobExecutionParamses;
	}

	@OneToOne(fetch = FetchType.LAZY, mappedBy = "batchJobExecution")
	public BatchJobExecutionContext getBatchJobExecutionContext() {
		return this.batchJobExecutionContext;
	}

	public void setBatchJobExecutionContext(BatchJobExecutionContext batchJobExecutionContext) {
		this.batchJobExecutionContext = batchJobExecutionContext;
	}

}
