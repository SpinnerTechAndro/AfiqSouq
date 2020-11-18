package dev.mobile.afiqsouq.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class oldOrderModel implements Serializable {
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
    private Billing billing;
    @SerializedName("shipping")
    @Expose
    private Shipping shipping;
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
    private Object datePaid;
    @SerializedName("date_paid_gmt")
    @Expose
    private Object datePaidGmt;
    @SerializedName("date_completed")
    @Expose
    private Object dateCompleted;
    @SerializedName("date_completed_gmt")
    @Expose
    private Object dateCompletedGmt;
    @SerializedName("cart_hash")
    @Expose
    private String cartHash;
    @SerializedName("meta_data")
    @Expose
    private List<Object> metaData = null;
    @SerializedName("line_items")
    @Expose
    private List<LineItem> lineItems = null;
    @SerializedName("tax_lines")
    @Expose
    private List<TaxLine> taxLines = null;
    @SerializedName("shipping_lines")
    @Expose
    private List<ShippingLine> shippingLines = null;
    @SerializedName("fee_lines")
    @Expose
    private List<Object> feeLines = null;
    @SerializedName("coupon_lines")
    @Expose
    private List<Object> couponLines = null;
    @SerializedName("refunds")
    @Expose
    private List<Object> refunds = null;
    @SerializedName("currency_symbol")
    @Expose
    private String currencySymbol;



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

    public Billing getBilling() {
        return billing;
    }

    public void setBilling(Billing billing) {
        this.billing = billing;
    }

    public Shipping getShipping() {
        return shipping;
    }

    public void setShipping(Shipping shipping) {
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

    public Object getDatePaid() {
        return datePaid;
    }

    public void setDatePaid(Object datePaid) {
        this.datePaid = datePaid;
    }

    public Object getDatePaidGmt() {
        return datePaidGmt;
    }

    public void setDatePaidGmt(Object datePaidGmt) {
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

    public List<Object> getMetaData() {
        return metaData;
    }

    public void setMetaData(List<Object> metaData) {
        this.metaData = metaData;
    }

    public List<LineItem> getLineItems() {
        return lineItems;
    }

    public void setLineItems(List<LineItem> lineItems) {
        this.lineItems = lineItems;
    }

    public List<TaxLine> getTaxLines() {
        return taxLines;
    }

    public void setTaxLines(List<TaxLine> taxLines) {
        this.taxLines = taxLines;
    }

    public List<ShippingLine> getShippingLines() {
        return shippingLines;
    }

    public void setShippingLines(List<ShippingLine> shippingLines) {
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

    public String getCurrencySymbol() {
        return currencySymbol;
    }

    public void setCurrencySymbol(String currencySymbol) {
        this.currencySymbol = currencySymbol;
    }


    public static class LineItem implements Serializable {

        @SerializedName("id")
        @Expose
        private Integer id;
        @SerializedName("name")
        @Expose
        private String name;
        @SerializedName("product_id")
        @Expose
        private Integer productId;
        @SerializedName("variation_id")
        @Expose
        private Integer variationId;
        @SerializedName("quantity")
        @Expose
        private Integer quantity;
        @SerializedName("tax_class")
        @Expose
        private String taxClass;
        @SerializedName("subtotal")
        @Expose
        private String subtotal;
        @SerializedName("subtotal_tax")
        @Expose
        private String subtotalTax;
        @SerializedName("total")
        @Expose
        private String total;
        @SerializedName("total_tax")
        @Expose
        private String totalTax;
        @SerializedName("taxes")
        @Expose
        private List<Tax> taxes = null;
        @SerializedName("meta_data")
        @Expose
        private List<Object> metaData = null;
        @SerializedName("sku")
        @Expose
        private Object sku;
        @SerializedName("price")
        @Expose
        private Integer price;

        @SerializedName("date")
        @Expose
        private String date;

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public Integer getProductId() {
            return productId;
        }

        public void setProductId(Integer productId) {
            this.productId = productId;
        }

        public Integer getVariationId() {
            return variationId;
        }

        public void setVariationId(Integer variationId) {
            this.variationId = variationId;
        }

        public Integer getQuantity() {
            return quantity;
        }

        public void setQuantity(Integer quantity) {
            this.quantity = quantity;
        }

        public String getTaxClass() {
            return taxClass;
        }

        public void setTaxClass(String taxClass) {
            this.taxClass = taxClass;
        }

        public String getSubtotal() {
            return subtotal;
        }

        public void setSubtotal(String subtotal) {
            this.subtotal = subtotal;
        }

        public String getSubtotalTax() {
            return subtotalTax;
        }

        public void setSubtotalTax(String subtotalTax) {
            this.subtotalTax = subtotalTax;
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

        public List<Tax> getTaxes() {
            return taxes;
        }

        public void setTaxes(List<Tax> taxes) {
            this.taxes = taxes;
        }

        public List<Object> getMetaData() {
            return metaData;
        }

        public void setMetaData(List<Object> metaData) {
            this.metaData = metaData;
        }

        public Object getSku() {
            return sku;
        }

        public void setSku(Object sku) {
            this.sku = sku;
        }

        public Integer getPrice() {
            return price;
        }

        public void setPrice(Integer price) {
            this.price = price;
        }

    }

    public static class Tax implements Serializable {

        @SerializedName("id")
        @Expose
        private Integer id;
        @SerializedName("total")
        @Expose
        private String total;
        @SerializedName("subtotal")
        @Expose
        private String subtotal;

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public String getTotal() {
            return total;
        }

        public void setTotal(String total) {
            this.total = total;
        }

        public String getSubtotal() {
            return subtotal;
        }

        public void setSubtotal(String subtotal) {
            this.subtotal = subtotal;
        }

    }

    public static class TaxLine implements Serializable {

        @SerializedName("id")
        @Expose
        private Integer id;
        @SerializedName("rate_code")
        @Expose
        private String rateCode;
        @SerializedName("rate_id")
        @Expose
        private Integer rateId;
        @SerializedName("label")
        @Expose
        private String label;
        @SerializedName("compound")
        @Expose
        private Boolean compound;
        @SerializedName("tax_total")
        @Expose
        private String taxTotal;
        @SerializedName("shipping_tax_total")
        @Expose
        private String shippingTaxTotal;
        @SerializedName("rate_percent")
        @Expose
        private Integer ratePercent;
        @SerializedName("meta_data")
        @Expose
        private List<Object> metaData = null;

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public String getRateCode() {
            return rateCode;
        }

        public void setRateCode(String rateCode) {
            this.rateCode = rateCode;
        }

        public Integer getRateId() {
            return rateId;
        }

        public void setRateId(Integer rateId) {
            this.rateId = rateId;
        }

        public String getLabel() {
            return label;
        }

        public void setLabel(String label) {
            this.label = label;
        }

        public Boolean getCompound() {
            return compound;
        }

        public void setCompound(Boolean compound) {
            this.compound = compound;
        }

        public String getTaxTotal() {
            return taxTotal;
        }

        public void setTaxTotal(String taxTotal) {
            this.taxTotal = taxTotal;
        }

        public String getShippingTaxTotal() {
            return shippingTaxTotal;
        }

        public void setShippingTaxTotal(String shippingTaxTotal) {
            this.shippingTaxTotal = shippingTaxTotal;
        }

        public Integer getRatePercent() {
            return ratePercent;
        }

        public void setRatePercent(Integer ratePercent) {
            this.ratePercent = ratePercent;
        }

        public List<Object> getMetaData() {
            return metaData;
        }

        public void setMetaData(List<Object> metaData) {
            this.metaData = metaData;
        }

    }

    public static class Tax_ implements Serializable {

        @SerializedName("id")
        @Expose
        private Integer id;
        @SerializedName("total")
        @Expose
        private String total;
        @SerializedName("subtotal")
        @Expose
        private String subtotal;

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public String getTotal() {
            return total;
        }

        public void setTotal(String total) {
            this.total = total;
        }

        public String getSubtotal() {
            return subtotal;
        }

        public void setSubtotal(String subtotal) {
            this.subtotal = subtotal;
        }

    }

    public static class Shipping implements Serializable {

        @SerializedName("first_name")
        @Expose
        private String firstName;
        @SerializedName("last_name")
        @Expose
        private String lastName;
        @SerializedName("company")
        @Expose
        private String company;
        @SerializedName("address_1")
        @Expose
        private String address1;
        @SerializedName("address_2")
        @Expose
        private String address2;
        @SerializedName("city")
        @Expose
        private String city;
        @SerializedName("state")
        @Expose
        private String state;
        @SerializedName("postcode")
        @Expose
        private String postcode;
        @SerializedName("country")
        @Expose
        private String country;

        public String getFirstName() {
            return firstName;
        }

        public void setFirstName(String firstName) {
            this.firstName = firstName;
        }

        public String getLastName() {
            return lastName;
        }

        public void setLastName(String lastName) {
            this.lastName = lastName;
        }

        public String getCompany() {
            return company;
        }

        public void setCompany(String company) {
            this.company = company;
        }

        public String getAddress1() {
            return address1;
        }

        public void setAddress1(String address1) {
            this.address1 = address1;
        }

        public String getAddress2() {
            return address2;
        }

        public void setAddress2(String address2) {
            this.address2 = address2;
        }

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public String getState() {
            return state;
        }

        public void setState(String state) {
            this.state = state;
        }

        public String getPostcode() {
            return postcode;
        }

        public void setPostcode(String postcode) {
            this.postcode = postcode;
        }

        public String getCountry() {
            return country;
        }

        public void setCountry(String country) {
            this.country = country;
        }

    }

    public static class Billing implements Serializable {

        @SerializedName("first_name")
        @Expose
        private String firstName;
        @SerializedName("last_name")
        @Expose
        private String lastName;
        @SerializedName("company")
        @Expose
        private String company;
        @SerializedName("address_1")
        @Expose
        private String address1;
        @SerializedName("address_2")
        @Expose
        private String address2;
        @SerializedName("city")
        @Expose
        private String city;
        @SerializedName("state")
        @Expose
        private String state;
        @SerializedName("postcode")
        @Expose
        private String postcode;
        @SerializedName("country")
        @Expose
        private String country;
        @SerializedName("email")
        @Expose
        private String email;
        @SerializedName("phone")
        @Expose
        private String phone;

        public String getFirstName() {
            return firstName;
        }

        public void setFirstName(String firstName) {
            this.firstName = firstName;
        }

        public String getLastName() {
            return lastName;
        }

        public void setLastName(String lastName) {
            this.lastName = lastName;
        }

        public String getCompany() {
            return company;
        }

        public void setCompany(String company) {
            this.company = company;
        }

        public String getAddress1() {
            return address1;
        }

        public void setAddress1(String address1) {
            this.address1 = address1;
        }

        public String getAddress2() {
            return address2;
        }

        public void setAddress2(String address2) {
            this.address2 = address2;
        }

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public String getState() {
            return state;
        }

        public void setState(String state) {
            this.state = state;
        }

        public String getPostcode() {
            return postcode;
        }

        public void setPostcode(String postcode) {
            this.postcode = postcode;
        }

        public String getCountry() {
            return country;
        }

        public void setCountry(String country) {
            this.country = country;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

    }

    public static class ShippingLine implements Serializable {

        @SerializedName("id")
        @Expose
        private Integer id;
        @SerializedName("method_title")
        @Expose
        private String methodTitle;
        @SerializedName("method_id")
        @Expose
        private String methodId;
        @SerializedName("instance_id")
        @Expose
        private String instanceId;
        @SerializedName("total")
        @Expose
        private String total;
        @SerializedName("total_tax")
        @Expose
        private String totalTax;
        @SerializedName("taxes")
        @Expose
        private List<Tax_> taxes = null;
        @SerializedName("meta_data")
        @Expose
        private List<Object> metaData = null;

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public String getMethodTitle() {
            return methodTitle;
        }

        public void setMethodTitle(String methodTitle) {
            this.methodTitle = methodTitle;
        }

        public String getMethodId() {
            return methodId;
        }

        public void setMethodId(String methodId) {
            this.methodId = methodId;
        }

        public String getInstanceId() {
            return instanceId;
        }

        public void setInstanceId(String instanceId) {
            this.instanceId = instanceId;
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

        public List<Tax_> getTaxes() {
            return taxes;
        }

        public void setTaxes(List<Tax_> taxes) {
            this.taxes = taxes;
        }

        public List<Object> getMetaData() {
            return metaData;
        }

        public void setMetaData(List<Object> metaData) {
            this.metaData = metaData;
        }

    }
}
