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
public class userobject {
            private String user_id;
            private String yelping_since;
            private String name;
            private String type;
            private float avg_stars;
            private int review_count;
            private int fans;

    /**
     * @return the user_id
     */
    public String getUserid() {
        return user_id;
    }

    /**
     * @param userid the user_id to set
     */
    public void setUserid(String userid) {
        this.user_id = userid;
    }

    /**
     * @return the yelping_since
     */
    public String getYelping_since() {
        return yelping_since;
    }

    /**
     * @param yelping_since the yelping_since to set
     */
    public void setYelping_since(String yelping_since) {
        this.yelping_since = yelping_since;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
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
     * @return the avg_stars
     */
    public float getAvg_stars() {
        return avg_stars;
    }

    /**
     * @param avg_stars the avg_stars to set
     */
    public void setAvg_stars(float avg_stars) {
        this.avg_stars = avg_stars;
    }

    /**
     * @return the review_count
     */
    public int getReview_count() {
        return review_count;
    }

    /**
     * @param review_count the review_count to set
     */
    public void setReview_count(int review_count) {
        this.review_count = review_count;
    }

    /**
     * @return the fans
     */
    public int getFans() {
        return fans;
    }

    /**
     * @param fans the fans to set
     */
    public void setFans(int fans) {
        this.fans = fans;
    }
    
}
