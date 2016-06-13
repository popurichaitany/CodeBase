/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

/**
 *
 * @author anand
 */
public class reviewobject {
    private String user_id;
    private String review_id;
    private String date;
    private String text;
    private String business_id;
    private String type;
    private float stars;
    private int usefulVotes;
// ========string votes======(doubt)
    /**
     * @return the user_id
     */
    public String getUser_id() {
        return user_id;
    }

    /**
     * @param user_id the user_id to set
     */
    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    /**
     * @return the review_id
     */
    public String getReview_id() {
        return review_id;
    }

    /**
     * @param review_id the review_id to set
     */
    public void setReview_id(String review_id) {
        this.review_id = review_id;
    }

    /**
     * @return the date
     */
    public String getDate() {
        return date;
    }

    /**
     * @param date the date to set
     */
    public void setDate(String date) {
        this.date = date;
    }

    /**
     * @return the text
     */
    public String getText() {
        return text;
    }

    /**
     * @param text the text to set
     */
    public void setText(String text) {
        this.text = text;
    }

    /**
     * @return the businessID
     */
    public String getbusiness_id() {
        return business_id;
    }

    /**
     * @param business_id
     */
    public void setbusiness_id(String business_id) {
        this.business_id = business_id;
    }

    /**
     * @return the type
     */
    public String getType() {
        return type;
    }

    /**
     * @param type the type to set
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * @return the stars
     */
    public float getStars() {
        return stars;
    }

    /**
     * @param stars the stars to set
     */
    public void setStars(float stars) {
        this.stars = stars;
    }

    /**
     * @return the usefulVotes
     */
    public int getUsefulVotes() {
        return usefulVotes;
    }

    /**
     * @param usefulVotes the usefulVotes to set
     */
    public void setUsefulVotes(int usefulVotes) {
        this.usefulVotes = usefulVotes;
    }

    
}
