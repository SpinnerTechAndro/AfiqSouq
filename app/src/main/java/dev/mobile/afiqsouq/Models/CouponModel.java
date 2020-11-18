package dev.mobile.afiqsouq.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CouponModel {
    @Expose
    @SerializedName("excluded_product_ids")
    private java.util.List<String> excluded_product_ids;
    @Expose
    @SerializedName("product_ids")
    private java.util.List<String> product_ids;
    @Expose
    @SerializedName("individual_use")
    private boolean individual_use;
    @Expose
    @SerializedName("usage_count")
    private int usage_count;
    @Expose
    @SerializedName("date_expires_gmt")
    private String date_expires_gmt;
    @Expose
    @SerializedName("date_expires")
    private String date_expires;
    @Expose
    @SerializedName("description")
    private String description;
    @Expose
    @SerializedName("discount_type")
    private String discount_type;
    @Expose
    @SerializedName("date_modified_gmt")
    private String date_modified_gmt;
    @Expose
    @SerializedName("date_modified")
    private String date_modified;
    @Expose
    @SerializedName("date_created_gmt")
    private String date_created_gmt;
    @Expose
    @SerializedName("date_created")
    private String date_created;
    @Expose
    @SerializedName("amount")
    private String amount;
    @Expose
    @SerializedName("code")
    private String code;
    @Expose
    @SerializedName("id")
    private int id;

    public CouponModel(List<String> excluded_product_ids, List<String> product_ids, boolean individual_use, int usage_count, String date_expires_gmt, String date_expires, String description, String discount_type, String date_modified_gmt, String date_modified, String date_created_gmt, String date_created, String amount, String code, int id) {
        this.excluded_product_ids = excluded_product_ids;
        this.product_ids = product_ids;
        this.individual_use = individual_use;
        this.usage_count = usage_count;
        this.date_expires_gmt = date_expires_gmt;
        this.date_expires = date_expires;
        this.description = description;
        this.discount_type = discount_type;
        this.date_modified_gmt = date_modified_gmt;
        this.date_modified = date_modified;
        this.date_created_gmt = date_created_gmt;
        this.date_created = date_created;
        this.amount = amount;
        this.code = code;
        this.id = id;
    }

    public List<String> getExcluded_product_ids() {
        return excluded_product_ids;
    }

    public void setExcluded_product_ids(List<String> excluded_product_ids) {
        this.excluded_product_ids = excluded_product_ids;
    }

    public List<String> getProduct_ids() {
        return product_ids;
    }

    public void setProduct_ids(List<String> product_ids) {
        this.product_ids = product_ids;
    }

    public boolean isIndividual_use() {
        return individual_use;
    }

    public void setIndividual_use(boolean individual_use) {
        this.individual_use = individual_use;
    }

    public int getUsage_count() {
        return usage_count;
    }

    public void setUsage_count(int usage_count) {
        this.usage_count = usage_count;
    }

    public String getDate_expires_gmt() {
        return date_expires_gmt;
    }

    public void setDate_expires_gmt(String date_expires_gmt) {
        this.date_expires_gmt = date_expires_gmt;
    }

    public String getDate_expires() {
        return date_expires;
    }

    public void setDate_expires(String date_expires) {
        this.date_expires = date_expires;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDiscount_type() {
        return discount_type;
    }

    public void setDiscount_type(String discount_type) {
        this.discount_type = discount_type;
    }

    public String getDate_modified_gmt() {
        return date_modified_gmt;
    }

    public void setDate_modified_gmt(String date_modified_gmt) {
        this.date_modified_gmt = date_modified_gmt;
    }

    public String getDate_modified() {
        return date_modified;
    }

    public void setDate_modified(String date_modified) {
        this.date_modified = date_modified;
    }

    public String getDate_created_gmt() {
        return date_created_gmt;
    }

    public void setDate_created_gmt(String date_created_gmt) {
        this.date_created_gmt = date_created_gmt;
    }

    public String getDate_created() {
        return date_created;
    }

    public void setDate_created(String date_created) {
        this.date_created = date_created;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
