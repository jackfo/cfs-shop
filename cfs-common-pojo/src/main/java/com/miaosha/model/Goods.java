package com.miaosha.model;

import javax.persistence.Column;
import javax.persistence.Table;

@Table(name="goods")
public class Goods {

	private Long id;
	@Column(name="goods_name")
	private String goodsName;
    @Column(name="goods_title")
	private String goodsTitle;
    @Column(name="goods_img")
	private String goodsImg;
    @Column(name="goods_detail")
	private String goodsDetail;
    @Column(name="goods_price")
	private Double goodsPrice;
    @Column(name="goods_stock")
	private Integer goodsStock;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getGoodsName() {
		return goodsName;
	}
	public void setGoodsName(String goodsName) {
		this.goodsName = goodsName;
	}
	public String getGoodsTitle() {
		return goodsTitle;
	}
	public void setGoodsTitle(String goodsTitle) {
		this.goodsTitle = goodsTitle;
	}
	public String getGoodsImg() {
		return goodsImg;
	}
	public void setGoodsImg(String goodsImg) {
		this.goodsImg = goodsImg;
	}
	public String getGoodsDetail() {
		return goodsDetail;
	}
	public void setGoodsDetail(String goodsDetail) {
		this.goodsDetail = goodsDetail;
	}
	public Double getGoodsPrice() {
		return goodsPrice;
	}
	public void setGoodsPrice(Double goodsPrice) {
		this.goodsPrice = goodsPrice;
	}
	public Integer getGoodsStock() {
		return goodsStock;
	}
	public void setGoodsStock(Integer goodsStock) {
		this.goodsStock = goodsStock;
	}
}
