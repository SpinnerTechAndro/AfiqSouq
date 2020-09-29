package dev.spinner_tech.afiqsouq.Models;

import java.io.Serializable;

public class CartModel implements Serializable {
    String title;
    Double unit_price;
    Double sub_total;
    Integer qty;
    Integer product_id;
    Integer shop_id;
    Integer customer_id;
    Integer order_id;
    String product_image;
    String invoice_no;
    String color;
    String size;


    public CartModel() {
    }

    public CartModel(String title, Double unit_price, Double sub_total, Integer qty, Integer product_id, Integer shop_id, Integer customer_id, Integer order_id, String product_image, String invoice_no, String color, String size) {
        this.title = title;
        this.unit_price = unit_price;
        this.sub_total = sub_total;
        this.qty = qty;
        this.product_id = product_id;
        this.shop_id = shop_id;
        this.customer_id = customer_id;
        this.order_id = order_id;
        this.product_image = product_image;
        this.invoice_no = invoice_no;
        this.color = color;
        this.size = size;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Double getUnit_price() {
        return unit_price;
    }

    public void setUnit_price(Double unit_price) {
        this.unit_price = unit_price;
    }

    public Double getSub_total() {
        return sub_total;
    }

    public void setSub_total(Double sub_total) {
        this.sub_total = sub_total;
    }

    public Integer getQty() {
        return qty;
    }

    public void setQty(Integer qty) {
        this.qty = qty;
    }

    public Integer getProduct_id() {
        return product_id;
    }

    public void setProduct_id(Integer product_id) {
        this.product_id = product_id;
    }

    public Integer getShop_id() {
        return shop_id;
    }

    public void setShop_id(Integer shop_id) {
        this.shop_id = shop_id;
    }

    public Integer getCustomer_id() {
        return customer_id;
    }

    public void setCustomer_id(Integer customer_id) {
        this.customer_id = customer_id;
    }

    public Integer getOrder_id() {
        return order_id;
    }

    public void setOrder_id(Integer order_id) {
        this.order_id = order_id;
    }

    public String getProduct_image() {
        return product_image;
    }

    public void setProduct_image(String product_image) {
        this.product_image = product_image;
    }

    public String getInvoice_no() {
        return invoice_no;
    }

    public void setInvoice_no(String invoice_no) {
        this.invoice_no = invoice_no;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }
}
