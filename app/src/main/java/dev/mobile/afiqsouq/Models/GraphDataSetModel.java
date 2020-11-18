package dev.mobile.afiqsouq.Models;

import java.io.Serializable;

public  class GraphDataSetModel implements Serializable {
    Float price , numberOfMonth , year ;

    public GraphDataSetModel(Float price, Float numberOfMonth, Float year) {
        this.price = price;
        this.numberOfMonth = numberOfMonth;
        this.year = year;
    }

    public Float getPrice() {
        return price;
    }

    public void setPrice(Float price) {
        this.price = price;
    }

    public Float getNumberOfMonth() {
        return numberOfMonth;
    }

    public void setNumberOfMonth(Float numberOfMonth) {
        this.numberOfMonth = numberOfMonth;
    }

    public Float getYear() {
        return year;
    }

    public void setYear(Float year) {
        this.year = year;
    }
}
