package dev.spinner_tech.afiqsouq.Models;


public  class PrefUserModel {
    String name , mail , deliveryAddress  , country , state  ;
    String id  , ph  ;

    public PrefUserModel(String name, String mail, String deliveryAddress, String country, String state, String id, String ph) {
        this.name = name;
        this.mail = mail;
        this.deliveryAddress = deliveryAddress;
        this.country = country;
        this.state = state;
        this.id = id;
        this.ph = ph;
    }

    public String getPh() {
        return ph;
    }

    public void setPh(String ph) {
        this.ph = ph;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getDeliveryAddress() {
        return deliveryAddress;
    }

    public void setDeliveryAddress(String deliveryAddress) {
        this.deliveryAddress = deliveryAddress;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
