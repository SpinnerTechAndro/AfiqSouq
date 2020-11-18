package dev.mobile.afiqsouq.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public  class CreateReviewRep {
    @SerializedName("product_id")
    @Expose
    private Integer productId;
    @SerializedName("review")
    @Expose
    private String review;
    @SerializedName("reviewer")
    @Expose
    private String reviewer;
    @SerializedName("reviewer_email")
    @Expose
    private String reviewerEmail;
    @SerializedName("rating")
    @Expose
    private Integer rating;

    public CreateReviewRep(Integer productId, String review, String reviewer, String reviewerEmail, Integer rating) {
        this.productId = productId;
        this.review = review;
        this.reviewer = reviewer;
        this.reviewerEmail = reviewerEmail;
        this.rating = rating;
    }

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public String getReview() {
        return review;
    }

    public void setReview(String review) {
        this.review = review;
    }

    public String getReviewer() {
        return reviewer;
    }

    public void setReviewer(String reviewer) {
        this.reviewer = reviewer;
    }

    public String getReviewerEmail() {
        return reviewerEmail;
    }

    public void setReviewerEmail(String reviewerEmail) {
        this.reviewerEmail = reviewerEmail;
    }

    public Integer getRating() {
        return rating;
    }

    public void setRating(Integer rating) {
        this.rating = rating;
    }

}
