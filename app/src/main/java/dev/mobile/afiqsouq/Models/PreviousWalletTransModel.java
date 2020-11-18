package dev.mobile.afiqsouq.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public  class PreviousWalletTransModel implements Serializable {

    @Expose
    @SerializedName("date")
    private String date;
    @Expose
    @SerializedName("deleted")
    private String deleted;
    @Expose
    @SerializedName("created_by")
    private String created_by;
    @Expose
    @SerializedName("details")
    private String details;
    @Expose
    @SerializedName("currency")
    private String currency;
    @Expose
    @SerializedName("balance")
    private String balance;
    @Expose
    @SerializedName("amount")
    private String amount;
    @Expose
    @SerializedName("type")
    private String type;
    @Expose
    @SerializedName("user_id")
    private String user_id;
    @Expose
    @SerializedName("blog_id")
    private String blog_id;
    @Expose
    @SerializedName("transaction_id")
    private String transaction_id;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDeleted() {
        return deleted;
    }

    public void setDeleted(String deleted) {
        this.deleted = deleted;
    }

    public String getCreated_by() {
        return created_by;
    }

    public void setCreated_by(String created_by) {
        this.created_by = created_by;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getBalance() {
        return balance;
    }

    public void setBalance(String balance) {
        this.balance = balance;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getBlog_id() {
        return blog_id;
    }

    public void setBlog_id(String blog_id) {
        this.blog_id = blog_id;
    }

    public String getTransaction_id() {
        return transaction_id;
    }

    public void setTransaction_id(String transaction_id) {
        this.transaction_id = transaction_id;
    }
}
