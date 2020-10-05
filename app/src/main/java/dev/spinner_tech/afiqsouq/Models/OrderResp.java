package dev.spinner_tech.afiqsouq.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public  class OrderResp {
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("parent_id")
    @Expose
    private Integer parentId;
    @SerializedName("number")
    @Expose
    private String number;
    @SerializedName("order_key")
    @Expose
    private String orderKey;
    @SerializedName("created_via")
    @Expose
    private String createdVia;
    @SerializedName("version")
    @Expose
    private String version;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("currency")
    @Expose
    private String currency;
    @SerializedName("date_created")
    @Expose
    private String dateCreated;
    @SerializedName("date_created_gmt")
    @Expose
    private String dateCreatedGmt;
    @SerializedName("date_modified")
    @Expose
    private String dateModified;
    @SerializedName("date_modified_gmt")
    @Expose
    private String dateModifiedGmt;
    @SerializedName("discount_total")
    @Expose
    private String discountTotal;
    @SerializedName("discount_tax")
    @Expose
    private String discountTax;
    @SerializedName("shipping_total")
    @Expose
    private String shippingTotal;
    @SerializedName("shipping_tax")
    @Expose
    private String shippingTax;
    @SerializedName("cart_tax")
    @Expose
    private String cartTax;
    @SerializedName("total")
    @Expose
    private String total;
    @SerializedName("total_tax")
    @Expose
    private String totalTax;
    @SerializedName("prices_include_tax")
    @Expose
    private Boolean pricesIncludeTax;
    @SerializedName("customer_id")
    @Expose
    private Integer customerId;
    @SerializedName("customer_ip_address")
    @Expose
    private String customerIpAddress;
    @SerializedName("customer_user_agent")
    @Expose
    private String customerUserAgent;
    @SerializedName("customer_note")
    @Expose
    private String customerNote;
    @SerializedName("billing")
    @Expose
    private CreateOrderResp.Billing billing;
    @SerializedName("shipping")
    @Expose
    private CreateOrderResp.Shipping shipping;
    @SerializedName("payment_method")
    @Expose
    private String paymentMethod;
    @SerializedName("payment_method_title")
    @Expose
    private String paymentMethodTitle;
    @SerializedName("transaction_id")
    @Expose
    private String transactionId;
    @SerializedName("date_paid")
    @Expose
    private String datePaid;
    @SerializedName("date_paid_gmt")
    @Expose
    private String datePaidGmt;
    @SerializedName("date_completed")
    @Expose
    private Object dateCompleted;
    @SerializedName("date_completed_gmt")
    @Expose
    private Object dateCompletedGmt;
    @SerializedName("cart_hash")
    @Expose
    private String cartHash;
//    @SerializedName("meta_data")
//    @Expose
//    private List<MetaDatum> metaData = null;
    @SerializedName("line_items")
    @Expose
    private List<CreateOrderResp.LineItem> lineItems = null;
//    @SerializedName("tax_lines")
//    @Expose
//    private List<TaxLine> taxLines = null;
    @SerializedName("shipping_lines")
    @Expose
    private List<CreateOrderResp.ShippingLine> shippingLines = null;
    @SerializedName("fee_lines")
    @Expose
    private List<Object> feeLines = null;
    @SerializedName("coupon_lines")
    @Expose
    private List<Object> couponLines = null;
    @SerializedName("refunds")
    @Expose
    private List<Object> refunds = null;
    @SerializedName("_links")
//    @Expose
//    private Links links;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getOrderKey() {
        return orderKey;
    }

    public void setOrderKey(String orderKey) {
        this.orderKey = orderKey;
    }

    public String getCreatedVia() {
        return createdVia;
    }

    public void setCreatedVia(String createdVia) {
        this.createdVia = createdVia;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(String dateCreated) {
        this.dateCreated = dateCreated;
    }

    public String getDateCreatedGmt() {
        return dateCreatedGmt;
    }

    public void setDateCreatedGmt(String dateCreatedGmt) {
        this.dateCreatedGmt = dateCreatedGmt;
    }

    public String getDateModified() {
        return dateModified;
    }

    public void setDateModified(String dateModified) {
        this.dateModified = dateModified;
    }

    public String getDateModifiedGmt() {
        return dateModifiedGmt;
    }

    public void setDateModifiedGmt(String dateModifiedGmt) {
        this.dateModifiedGmt = dateModifiedGmt;
    }

    public String getDiscountTotal() {
        return discountTotal;
    }

    public void setDiscountTotal(String discountTotal) {
        this.discountTotal = discountTotal;
    }

    public String getDiscountTax() {
        return discountTax;
    }

    public void setDiscountTax(String discountTax) {
        this.discountTax = discountTax;
    }

    public String getShippingTotal() {
        return shippingTotal;
    }

    public void setShippingTotal(String shippingTotal) {
        this.shippingTotal = shippingTotal;
    }

    public String getShippingTax() {
        return shippingTax;
    }

    public void setShippingTax(String shippingTax) {
        this.shippingTax = shippingTax;
    }

    public String getCartTax() {
        return cartTax;
    }

    public void setCartTax(String cartTax) {
        this.cartTax = cartTax;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public String getTotalTax() {
        return totalTax;
    }

    public void setTotalTax(String totalTax) {
        this.totalTax = totalTax;
    }

    public Boolean getPricesIncludeTax() {
        return pricesIncludeTax;
    }

    public void setPricesIncludeTax(Boolean pricesIncludeTax) {
        this.pricesIncludeTax = pricesIncludeTax;
    }

    public Integer getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Integer customerId) {
        this.customerId = customerId;
    }

    public String getCustomerIpAddress() {
        return customerIpAddress;
    }

    public void setCustomerIpAddress(String customerIpAddress) {
        this.customerIpAddress = customerIpAddress;
    }

    public String getCustomerUserAgent() {
        return customerUserAgent;
    }

    public void setCustomerUserAgent(String customerUserAgent) {
        this.customerUserAgent = customerUserAgent;
    }

    public String getCustomerNote() {
        return customerNote;
    }

    public void setCustomerNote(String customerNote) {
        this.customerNote = customerNote;
    }

    public CreateOrderResp.Billing getBilling() {
        return billing;
    }

    public void setBilling(CreateOrderResp.Billing billing) {
        this.billing = billing;
    }

    public CreateOrderResp.Shipping getShipping() {
        return shipping;
    }

    public void setShipping(CreateOrderResp.Shipping shipping) {
        this.shipping = shipping;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public String getPaymentMethodTitle() {
        return paymentMethodTitle;
    }

    public void setPaymentMethodTitle(String paymentMethodTitle) {
        this.paymentMethodTitle = paymentMethodTitle;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public String getDatePaid() {
        return datePaid;
    }

    public void setDatePaid(String datePaid) {
        this.datePaid = datePaid;
    }

    public String getDatePaidGmt() {
        return datePaidGmt;
    }

    public void setDatePaidGmt(String datePaidGmt) {
        this.datePaidGmt = datePaidGmt;
    }

    public Object getDateCompleted() {
        return dateCompleted;
    }

    public void setDateCompleted(Object dateCompleted) {
        this.dateCompleted = dateCompleted;
    }

    public Object getDateCompletedGmt() {
        return dateCompletedGmt;
    }

    public void setDateCompletedGmt(Object dateCompletedGmt) {
        this.dateCompletedGmt = dateCompletedGmt;
    }

    public String getCartHash() {
        return cartHash;
    }

    public void setCartHash(String cartHash) {
        this.cartHash = cartHash;
    }

//    public List<MetaDatum> getMetaData() {
//        return metaData;
//    }
//
//    public void setMetaData(List<MetaDatum> metaData) {
//        this.metaData = metaData;
//    }

    public List<CreateOrderResp.LineItem> getLineItems() {
        return lineItems;
    }

    public void setLineItems(List<CreateOrderResp.LineItem> lineItems) {
        this.lineItems = lineItems;
    }

//    public List<TaxLine> getTaxLines() {
//        return taxLines;
//    }
//
//    public void setTaxLines(List<TaxLine> taxLines) {
//        this.taxLines = taxLines;
//    }

    public List<CreateOrderResp.ShippingLine> getShippingLines() {
        return shippingLines;
    }

    public void setShippingLines(List<CreateOrderResp.ShippingLine> shippingLines) {
        this.shippingLines = shippingLines;
    }

    public List<Object> getFeeLines() {
        return feeLines;
    }

    public void setFeeLines(List<Object> feeLines) {
        this.feeLines = feeLines;
    }

    public List<Object> getCouponLines() {
        return couponLines;
    }

    public void setCouponLines(List<Object> couponLines) {
        this.couponLines = couponLines;
    }

    public List<Object> getRefunds() {
        return refunds;
    }

    public void setRefunds(List<Object> refunds) {
        this.refunds = refunds;
    }

}
