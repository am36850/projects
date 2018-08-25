package com.project.sharemarket.domains;

import static javax.persistence.GenerationType.IDENTITY;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "nse_per_day_data", catalog = "share_market")
public class NsePerDayData implements java.io.Serializable {

	private Integer id;
	private String ticker;
	private Date date;
	private BigDecimal open;
	private BigDecimal high;
	private BigDecimal low;
	private BigDecimal close;
	private Long volume;
	private Integer oi;

	public NsePerDayData() {
	}

	public NsePerDayData(String ticker, Date date) {
		this.ticker = ticker;
		this.date = date;
	}

	public NsePerDayData(String ticker, Date date, BigDecimal open, BigDecimal high, BigDecimal low, BigDecimal close,
			Long volume, Integer oi) {
		this.ticker = ticker;
		this.date = date;
		this.open = open;
		this.high = high;
		this.low = low;
		this.close = close;
		this.volume = volume;
		this.oi = oi;
	}

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Column(name = "ticker", nullable = false, length = 100)
	public String getTicker() {
		return this.ticker;
	}

	public void setTicker(String ticker) {
		this.ticker = ticker;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "date", nullable = false, length = 10)
	public Date getDate() {
		return this.date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	@Column(name = "open", precision = 20, scale = 4)
	public BigDecimal getOpen() {
		return this.open;
	}

	public void setOpen(BigDecimal open) {
		this.open = open;
	}

	@Column(name = "high", precision = 20, scale = 4)
	public BigDecimal getHigh() {
		return this.high;
	}

	public void setHigh(BigDecimal high) {
		this.high = high;
	}

	@Column(name = "low", precision = 20, scale = 4)
	public BigDecimal getLow() {
		return this.low;
	}

	public void setLow(BigDecimal low) {
		this.low = low;
	}

	@Column(name = "close", precision = 20, scale = 4)
	public BigDecimal getClose() {
		return this.close;
	}

	public void setClose(BigDecimal close) {
		this.close = close;
	}

	@Column(name = "volume")
	public Long getVolume() {
		return this.volume;
	}

	public void setVolume(Long volume) {
		this.volume = volume;
	}

	@Column(name = "oi")
	public Integer getOi() {
		return this.oi;
	}

	public void setOi(Integer oi) {
		this.oi = oi;
	}

}
