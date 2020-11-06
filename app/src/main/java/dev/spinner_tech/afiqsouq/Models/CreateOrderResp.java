package dev.spinner_tech.afiqsouq.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class CreateOrderResp implements Serializable {

    @SerializedName("payment_method")
    @Expose
    private String paymentMethod;
    @SerializedName("payment_method_title")
    @Expose
    private String paymentMethodTitle;
    @SerializedName("set_paid")
    @Expose
    private Boolean setPaid;
    @SerializedName("billing")
    @Expose
    private Billing billing;
    @SerializedName("shipping")
    @Expose
    private Shipping shipping;
    @SerializedName("line_items")
    @Expose
    private List<LineItem> lineItems = null;
    @SerializedName("shipping_lines")
    @Expose
    private List<ShippingLine> shippingLines = null;
    @SerializedName("coupon_lines")
    @Expose
    private List<coupon_lines> coupon_lines = null;
    @SerializedName("customer_id")
    @Expose
    private String customer_id = null;


    public CreateOrderResp(String paymentMethod, String paymentMethodTitle, Boolean setPaid, Billing billing, Shipping shipping, List<LineItem> lineItems, List<ShippingLine> shippingLines, List<CreateOrderResp.coupon_lines> coupon_lines, String customer_id) {
        this.paymentMethod = paymentMethod;
        this.paymentMethodTitle = paymentMethodTitle;
        this.setPaid = setPaid;
        this.billing = billing;
        this.shipping = shipping;
        this.lineItems = lineItems;
        this.shippingLines = shippingLines;
        this.coupon_lines = coupon_lines;
        this.customer_id = customer_id;
    }

    public List<CreateOrderResp.coupon_lines> getCoupon_lines() {
        return coupon_lines;
    }

    public void setCoupon_lines(List<CreateOrderResp.coupon_lines> coupon_lines) {
        this.coupon_lines = coupon_lines;
    }

    public String getCustomer_id() {
        return customer_id;
    }

    public void setCustomer_id(String customer_id) {
        this.customer_id = customer_id;
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

    public Boolean getSetPaid() {
        return setPaid;
    }

    public void setSetPaid(Boolean setPaid) {
        this.setPaid = setPaid;
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

    public List<LineItem> getLineItems() {
        return lineItems;
    }

    public void setLineItems(List<LineItem> lineItems) {
        this.lineItems = lineItems;
    }

    public List<ShippingLine> getShippingLines() {
        return shippingLines;
    }

    public void setShippingLines(List<ShippingLine> shippingLines) {
        this.shippingLines = shippingLines;
    }

    public static class ShippingLine implements Serializable {

        @SerializedName("method_id")
        @Expose
        private String methodId; // GLYSUP 4 ta
        @SerializedName("method_title")
        @Expose
        private String methodTitle;
        @SerializedName("total")
        @Expose
        private String total;

        public ShippingLine(String methodId, String methodTitle, String total) {
            this.methodId = methodId;
            this.methodTitle = methodTitle;
            this.total = total;
        }

        public String getMethodId() {
            return methodId;
        }

        public void setMethodId(String methodId) {
            this.methodId = methodId;
        }

        public String getMethodTitle() {
            return methodTitle;
        }

        public void setMethodTitle(String methodTitle) {
            this.methodTitle = methodTitle;
        }

        public String getTotal() {
            return total;
        }

        public void setTotal(String total) {
            this.total = total;
        }

    }
    public static class Shipping implements Serializable {

        @SerializedName("first_name")
        @Expose
        private String firstName;
        @SerializedName("last_name")
        @Expose
        private String lastName;
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

        public Shipping(String firstName, String lastName, String address1, String address2, String city, String state, String postcode, String country) {
            this.firstName = firstName;
            this.lastName = lastName;
            this.address1 = address1;
            this.address2 = address2;
            this.city = city;
            this.state = state;
            this.postcode = postcode;
            this.country = country;
        }

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
    public  static  class  coupon_lines implements  Serializable{
        @SerializedName("code")
        @Expose
        private String code;

        public coupon_lines(String code) {
            this.code = code;
        }

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }
    }
    public static class LineItem implements Serializable {

        @SerializedName("product_id")
        @Expose
        private Integer productId;
        @SerializedName("quantity")
        @Expose
        private Integer quantity;
        @SerializedName("variation_id")
        @Expose
        private Integer variationId;

        public LineItem(Integer productId, Integer quantity, Integer variationId) {
            this.productId = productId;
            this.quantity = quantity;
            this.variationId = variationId;
        }
        public LineItem(Integer productId, Integer quantity) {
            this.productId = productId;
            this.quantity = quantity;

        }

        public Integer getProductId() {
            return productId;
        }

        public void setProductId(Integer productId) {
            this.productId = productId;
        }

        public Integer getQuantity() {
            return quantity;
        }

        public void setQuantity(Integer quantity) {
            this.quantity = quantity;
        }

        public Integer getVariationId() {
            return variationId;
        }

        public void setVariationId(Integer variationId) {
            this.variationId = variationId;
        }

    }
    public static class Billing implements Serializable {

        @SerializedName("first_name")
        @Expose
        private String firstName;
        @SerializedName("last_name")
        @Expose
        private String lastName;
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

        public Billing(String firstName, String lastName, String address1, String address2, String city, String state, String postcode, String country, String email, String phone) {
            this.firstName = firstName;
            this.lastName = lastName;
            this.address1 = address1;
            this.address2 = address2;
            this.city = city;
            this.state = state;
            this.postcode = postcode;
            this.country = country;
            this.email = email;
            this.phone = phone;
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

}
