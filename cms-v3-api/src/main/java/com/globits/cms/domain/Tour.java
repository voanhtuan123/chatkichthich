package com.globits.cms.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

import com.globits.core.domain.BaseObject;
@Entity
@Table(name = "tbl_tour")
@XmlRootElement
public class Tour extends BaseObject {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Column(name="name")
	private String name;
	@Column(name="code")
	private String code;
	@Column(name="price")
	private Double price;
	
	@Column(name="child_price")
	private Double childPrice;//Giá vé giành cho trẻ em
	
	@Column(name="sale_price")
	private Double salePrice;
	
	@Column(name="sale_child_price")
	private Double saleChildPrice;//Giá khuyến mại
	
	@Column(name="start_date")
	private Date startDate;//Ngày khởi hành
	@Column(name="number_of_day")
	private Integer numberOfDays;//Số ngày
	
	@Column(name="number_of_night")
	private Integer numberOfNight;//Số đêm
	
	@Column(name="start_date_info")
	private String startDateInfo;//Mô tả ngày bắt đầu (ví dụ : Thứ 6 hàng tuần)
	@Column(name="duration_info")
	private String durationInfo;
	@Column(name="transport")
	private String transport;
	@Column(name="program")
	private String program;//Chương trình tour
	@Column(name="included_service")
	private String includedService;//Dịch vụ bao gồm
	@Column(name="exclude_service")
	private String excludeService;//Dịch vụ không bao gồm trong giá
	
	@Column(name="content")
	private String content;
	
	@Column(name="is_sale_tour")
	private Boolean isSaleTour;
	
	@Column(name="website")
	private Website website;
	
	@ManyToOne
	@JoinColumn(name="tour_type_id")
	private TourType tourType;
	
	@ManyToOne
	@JoinColumn(name="transport_type_id")
	private TransportType transportType;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public Double getPrice() {
		return price;
	}
	public void setPrice(Double price) {
		this.price = price;
	}
	public Date getStartDate() {
		return startDate;
	}
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	public String getStartDateInfo() {
		return startDateInfo;
	}
	public void setStartDateInfo(String startDateInfo) {
		this.startDateInfo = startDateInfo;
	}
	public String getDurationInfo() {
		return durationInfo;
	}
	public void setDurationInfo(String durationInfo) {
		this.durationInfo = durationInfo;
	}
	public String getTransport() {
		return transport;
	}
	public void setTransport(String transport) {
		this.transport = transport;
	}
	
	
	public Double getSalePrice() {
		return salePrice;
	}
	public void setSalePrice(Double salePrice) {
		this.salePrice = salePrice;
	}
	public Double getSaleChildPrice() {
		return saleChildPrice;
	}
	public void setSaleChildPrice(Double saleChildPrice) {
		this.saleChildPrice = saleChildPrice;
	}
	public Double getChildPrice() {
		return childPrice;
	}
	public void setChildPrice(Double childPrice) {
		this.childPrice = childPrice;
	}
	public String getProgram() {
		return program;
	}
	public void setProgram(String program) {
		this.program = program;
	}
	public String getIncludedService() {
		return includedService;
	}
	public void setIncludedService(String includedService) {
		this.includedService = includedService;
	}
	public String getExcludeService() {
		return excludeService;
	}
	public void setExcludeService(String excludeService) {
		this.excludeService = excludeService;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	
	public Integer getNumberOfDays() {
		return numberOfDays;
	}
	public void setNumberOfDays(Integer numberOfDays) {
		this.numberOfDays = numberOfDays;
	}
	public Integer getNumberOfNight() {
		return numberOfNight;
	}
	public void setNumberOfNight(Integer numberOfNight) {
		this.numberOfNight = numberOfNight;
	}
	
	public Website getWebsite() {
		return website;
	}
	public void setWebsite(Website website) {
		this.website = website;
	}
	public TourType getTourType() {
		return tourType;
	}
	public void setTourType(TourType tourType) {
		this.tourType = tourType;
	}
	public TransportType getTransportType() {
		return transportType;
	}
	public void setTransportType(TransportType transportType) {
		this.transportType = transportType;
	}
	public Boolean getIsSaleTour() {
		return isSaleTour;
	}
	public void setIsSaleTour(Boolean isSaleTour) {
		this.isSaleTour = isSaleTour;
	}

	
}
