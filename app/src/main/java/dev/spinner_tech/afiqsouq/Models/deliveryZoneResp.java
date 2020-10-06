package dev.spinner_tech.afiqsouq.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public  class deliveryZoneResp {
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("instance_id")
    @Expose
    private Integer instanceId;
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("order")
    @Expose
    private Integer order;
    @SerializedName("enabled")
    @Expose
    private Boolean enabled;
    @SerializedName("method_id")
    @Expose
    private String methodId;
    @SerializedName("method_title")
    @Expose
    private String methodTitle;
    @SerializedName("method_description")
    @Expose
    private String methodDescription;
    @SerializedName("settings")
    @Expose
    private Settings settings;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getInstanceId() {
        return instanceId;
    }

    public void setInstanceId(Integer instanceId) {
        this.instanceId = instanceId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getOrder() {
        return order;
    }

    public void setOrder(Integer order) {
        this.order = order;
    }

    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
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

    public String getMethodDescription() {
        return methodDescription;
    }

    public void setMethodDescription(String methodDescription) {
        this.methodDescription = methodDescription;
    }

    public Settings getSettings() {
        return settings;
    }

    public void setSettings(Settings settings) {
        this.settings = settings;
    }

    public class Cost {

        @SerializedName("id")
        @Expose
        private String id;
        @SerializedName("label")
        @Expose
        private String label;
        @SerializedName("description")
        @Expose
        private String description;
        @SerializedName("type")
        @Expose
        private String type;
        @SerializedName("value")
        @Expose
        private String value;
        @SerializedName("default")
        @Expose
        private String _default;
        @SerializedName("tip")
        @Expose
        private String tip;
        @SerializedName("placeholder")
        @Expose
        private String placeholder;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getLabel() {
            return label;
        }

        public void setLabel(String label) {
            this.label = label;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }

        public String getDefault() {
            return _default;
        }

        public void setDefault(String _default) {
            this._default = _default;
        }

        public String getTip() {
            return tip;
        }

        public void setTip(String tip) {
            this.tip = tip;
        }

        public String getPlaceholder() {
            return placeholder;
        }

        public void setPlaceholder(String placeholder) {
            this.placeholder = placeholder;
        }

    }

    public class Settings {

        @SerializedName("title")
        @Expose
        private Title title;
        @SerializedName("tax_status")
        @Expose
        private TaxStatus taxStatus;
        @SerializedName("cost")
        @Expose
        private Cost cost;

        public Title getTitle() {
            return title;
        }

        public void setTitle(Title title) {
            this.title = title;
        }

        public TaxStatus getTaxStatus() {
            return taxStatus;
        }

        public void setTaxStatus(TaxStatus taxStatus) {
            this.taxStatus = taxStatus;
        }

        public Cost getCost() {
            return cost;
        }

        public void setCost(Cost cost) {
            this.cost = cost;
        }

    }

    public class TaxStatus {

        @SerializedName("id")
        @Expose
        private String id;
        @SerializedName("label")
        @Expose
        private String label;
        @SerializedName("description")
        @Expose
        private String description;
        @SerializedName("type")
        @Expose
        private String type;
        @SerializedName("value")
        @Expose
        private String value;
        @SerializedName("default")
        @Expose
        private String _default;
        @SerializedName("tip")
        @Expose
        private String tip;
        @SerializedName("placeholder")
        @Expose
        private String placeholder;


        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getLabel() {
            return label;
        }

        public void setLabel(String label) {
            this.label = label;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }

        public String getDefault() {
            return _default;
        }

        public void setDefault(String _default) {
            this._default = _default;
        }

        public String getTip() {
            return tip;
        }

        public void setTip(String tip) {
            this.tip = tip;
        }

        public String getPlaceholder() {
            return placeholder;
        }

        public void setPlaceholder(String placeholder) {
            this.placeholder = placeholder;
        }



    }
    public class Title {

        @SerializedName("id")
        @Expose
        private String id;
        @SerializedName("label")
        @Expose
        private String label;
        @SerializedName("description")
        @Expose
        private String description;
        @SerializedName("type")
        @Expose
        private String type;
        @SerializedName("value")
        @Expose
        private String value;
        @SerializedName("default")
        @Expose
        private String _default;
        @SerializedName("tip")
        @Expose
        private String tip;
        @SerializedName("placeholder")
        @Expose
        private String placeholder;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getLabel() {
            return label;
        }

        public void setLabel(String label) {
            this.label = label;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }

        public String getDefault() {
            return _default;
        }

        public void setDefault(String _default) {
            this._default = _default;
        }

        public String getTip() {
            return tip;
        }

        public void setTip(String tip) {
            this.tip = tip;
        }

        public String getPlaceholder() {
            return placeholder;
        }

        public void setPlaceholder(String placeholder) {
            this.placeholder = placeholder;
        }

    }

}
