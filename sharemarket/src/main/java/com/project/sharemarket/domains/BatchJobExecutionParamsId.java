package com.project.sharemarket.domains;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class BatchJobExecutionParamsId implements java.io.Serializable {

	private long jobExecutionId;
	private String typeCd;
	private String keyName;
	private String stringVal;
	private Date dateVal;
	private Long longVal;
	private Double doubleVal;
	private char identifying;

	public BatchJobExecutionParamsId() {
	}

	public BatchJobExecutionParamsId(long jobExecutionId, String typeCd, String keyName, char identifying) {
		this.jobExecutionId = jobExecutionId;
		this.typeCd = typeCd;
		this.keyName = keyName;
		this.identifying = identifying;
	}

	public BatchJobExecutionParamsId(long jobExecutionId, String typeCd, String keyName, String stringVal, Date dateVal,
			Long longVal, Double doubleVal, char identifying) {
		this.jobExecutionId = jobExecutionId;
		this.typeCd = typeCd;
		this.keyName = keyName;
		this.stringVal = stringVal;
		this.dateVal = dateVal;
		this.longVal = longVal;
		this.doubleVal = doubleVal;
		this.identifying = identifying;
	}

	@Column(name = "JOB_EXECUTION_ID", nullable = false)
	public long getJobExecutionId() {
		return this.jobExecutionId;
	}

	public void setJobExecutionId(long jobExecutionId) {
		this.jobExecutionId = jobExecutionId;
	}

	@Column(name = "TYPE_CD", nullable = false, length = 6)
	public String getTypeCd() {
		return this.typeCd;
	}

	public void setTypeCd(String typeCd) {
		this.typeCd = typeCd;
	}

	@Column(name = "KEY_NAME", nullable = false, length = 100)
	public String getKeyName() {
		return this.keyName;
	}

	public void setKeyName(String keyName) {
		this.keyName = keyName;
	}

	@Column(name = "STRING_VAL", length = 250)
	public String getStringVal() {
		return this.stringVal;
	}

	public void setStringVal(String stringVal) {
		this.stringVal = stringVal;
	}

	@Column(name = "DATE_VAL", length = 19)
	public Date getDateVal() {
		return this.dateVal;
	}

	public void setDateVal(Date dateVal) {
		this.dateVal = dateVal;
	}

	@Column(name = "LONG_VAL")
	public Long getLongVal() {
		return this.longVal;
	}

	public void setLongVal(Long longVal) {
		this.longVal = longVal;
	}

	@Column(name = "DOUBLE_VAL", precision = 22, scale = 0)
	public Double getDoubleVal() {
		return this.doubleVal;
	}

	public void setDoubleVal(Double doubleVal) {
		this.doubleVal = doubleVal;
	}

	@Column(name = "IDENTIFYING", nullable = false, length = 1)
	public char getIdentifying() {
		return this.identifying;
	}

	public void setIdentifying(char identifying) {
		this.identifying = identifying;
	}

	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof BatchJobExecutionParamsId))
			return false;
		BatchJobExecutionParamsId castOther = (BatchJobExecutionParamsId) other;

		return (this.getJobExecutionId() == castOther.getJobExecutionId())
				&& ((this.getTypeCd() == castOther.getTypeCd()) || (this.getTypeCd() != null
						&& castOther.getTypeCd() != null && this.getTypeCd().equals(castOther.getTypeCd())))
				&& ((this.getKeyName() == castOther.getKeyName()) || (this.getKeyName() != null
						&& castOther.getKeyName() != null && this.getKeyName().equals(castOther.getKeyName())))
				&& ((this.getStringVal() == castOther.getStringVal()) || (this.getStringVal() != null
						&& castOther.getStringVal() != null && this.getStringVal().equals(castOther.getStringVal())))
				&& ((this.getDateVal() == castOther.getDateVal()) || (this.getDateVal() != null
						&& castOther.getDateVal() != null && this.getDateVal().equals(castOther.getDateVal())))
				&& ((this.getLongVal() == castOther.getLongVal()) || (this.getLongVal() != null
						&& castOther.getLongVal() != null && this.getLongVal().equals(castOther.getLongVal())))
				&& ((this.getDoubleVal() == castOther.getDoubleVal()) || (this.getDoubleVal() != null
						&& castOther.getDoubleVal() != null && this.getDoubleVal().equals(castOther.getDoubleVal())))
				&& (this.getIdentifying() == castOther.getIdentifying());
	}

	public int hashCode() {
		int result = 17;

		result = 37 * result + (int) this.getJobExecutionId();
		result = 37 * result + (getTypeCd() == null ? 0 : this.getTypeCd().hashCode());
		result = 37 * result + (getKeyName() == null ? 0 : this.getKeyName().hashCode());
		result = 37 * result + (getStringVal() == null ? 0 : this.getStringVal().hashCode());
		result = 37 * result + (getDateVal() == null ? 0 : this.getDateVal().hashCode());
		result = 37 * result + (getLongVal() == null ? 0 : this.getLongVal().hashCode());
		result = 37 * result + (getDoubleVal() == null ? 0 : this.getDoubleVal().hashCode());
		result = 37 * result + this.getIdentifying();
		return result;
	}

}
