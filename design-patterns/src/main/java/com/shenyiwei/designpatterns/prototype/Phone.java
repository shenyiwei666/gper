package com.shenyiwei.designpatterns.prototype;

import java.math.BigDecimal;

/**
 * Created by shenyiwei on 2019-5-1 001.
 */
public class Phone implements Cloneable {
    /**
     * 品牌
     */
    private String brand;
    /**
     * 型号
     */
    private String type;
    /**
     * 价格
     */
    private BigDecimal price;
    /**
     * 颜色
     */
    private String color;
    /**
     * 屏幕尺寸
     */
    private Double size;
    /**
     * 机身重量
     */
    private Double weight;
    /**
     * 处理器
     */
    private String processor;
    /**
     * 操作系统
     */
    private String operatingSystem;
    /**
     * 存储容量
     */
    private Double storeCapacity;
    /**
     * 电池容量
     */
    private Double electricCapacity;
    /**
     * 唯一标识码
     */
    private String mac;

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public Double getSize() {
        return size;
    }

    public void setSize(Double size) {
        this.size = size;
    }

    public Double getWeight() {
        return weight;
    }

    public void setWeight(Double weight) {
        this.weight = weight;
    }

    public String getProcessor() {
        return processor;
    }

    public void setProcessor(String processor) {
        this.processor = processor;
    }

    public String getOperatingSystem() {
        return operatingSystem;
    }

    public void setOperatingSystem(String operatingSystem) {
        this.operatingSystem = operatingSystem;
    }

    public Double getStoreCapacity() {
        return storeCapacity;
    }

    public void setStoreCapacity(Double storeCapacity) {
        this.storeCapacity = storeCapacity;
    }

    public Double getElectricCapacity() {
        return electricCapacity;
    }

    public void setElectricCapacity(Double electricCapacity) {
        this.electricCapacity = electricCapacity;
    }

    public String getMac() {
        return mac;
    }

    public void setMac(String mac) {
        this.mac = mac;
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}
