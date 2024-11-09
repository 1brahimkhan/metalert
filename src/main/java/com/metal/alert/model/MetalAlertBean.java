package com.metal.alert.model;

import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonProperty;

public class MetalAlertBean {

	@JsonProperty("metal")
	public String metal;

	@JsonProperty("currency")
	public String currency;
	@JsonProperty("price_gram_24k")
	public double pricePerGram24k;

//	public int timestamp;
//	public String exchange;
//	public String symbol;
//	public double prev_close_price;
//	public double open_price;
//	public double low_price;
//	public double high_price;
//	public int open_time;
//	public double price;
//	public double ch;
//	public double chp;
//	public double ask;
//	public double bid;
//	public double price_gram_22k;
//	public double price_gram_21k;
//	public double price_gram_20k;
//	public double price_gram_18k;
//	public double price_gram_16k;
//	public double price_gram_14k;
//	public double price_gram_10k;

	public String getMetal() {
		return metal;
	}

	public void setMetal(String metal) {
		this.metal = metal;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public double getPrice_gram_24k() {
		return pricePerGram24k;
	}

	public void setPrice_gram_24k(double price_gram_24k) {
		this.pricePerGram24k = price_gram_24k;
	}

	@Override
	public String toString() {
		return "MetalAlertBean [metal=" + metal + ", currency=" + currency + ", price_gram_24k=" + pricePerGram24k
				+ "]";
	}

	@Override
	public int hashCode() {
		return Objects.hash(currency, metal, pricePerGram24k);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		MetalAlertBean other = (MetalAlertBean) obj;
		return Objects.equals(currency, other.currency) && Objects.equals(metal, other.metal)
				&& Double.doubleToLongBits(pricePerGram24k) == Double.doubleToLongBits(other.pricePerGram24k);
	}

}
