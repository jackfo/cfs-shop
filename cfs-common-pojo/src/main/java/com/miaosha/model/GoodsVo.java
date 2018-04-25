package com.miaosha.model;

import javax.persistence.Column;
import javax.persistence.Table;
import java.util.Date;

@Table(name = "goods_vo")
public class GoodsVo extends Goods{

    @Column(name = "miaosha_price")
	private Double miaoshaPrice;
    @Column(name = "stock_count")
	private Integer stockCount;
    @Column(name = "start_date")
	private Date startDate;
    @Column(name = "end_date")
	private Date endDate;
	public Integer getStockCount() {
		return stockCount;
	}
	public void setStockCount(Integer stockCount) {
		this.stockCount = stockCount;
	}
	public Date getStartDate() {
		return startDate;
	}
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	public Date getEndDate() {
		return endDate;
	}
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	public Double getMiaoshaPrice() {
		return miaoshaPrice;
	}
	public void setMiaoshaPrice(Double miaoshaPrice) {
		this.miaoshaPrice = miaoshaPrice;
	}
}
