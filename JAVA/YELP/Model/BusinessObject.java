/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import java.util.ArrayList;

/**
 *
 * @author anand
 */
public class BusinessObject {
    private String businessID;
    private String address;
    private String city;
    private String state;
    private String businessName;
    private String businessType;
    private float latitiude;
    private float longitude;
    private float stars;
    private int reviewCount;
    private boolean open;
    private ArrayList<BusinessHours> bHours;
    private ArrayList<BusinessCategory> bCategory;
    private ArrayList<BusinessSubcategory> bSubcategory;

    /**
     * @return the businessID
     */
    public String getBusinessID() {
        return businessID;
    }

    /**
     * @param businessID the businessID to set
     */
    public void setBusinessID(String businessID) {
        this.businessID = businessID;
    }

    /**
     * @return the address
     */
    public String getAddress() {
        return address;
    }

    /**
     * @param address the address to set
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * @return the city
     */
    public String getCity() {
        return city;
    }

    /**
     * @param city the city to set
     */
    public void setCity(String city) {
        this.city = city;
    }

    /**
     * @return the state
     */
    public String getState() {
        return state;
    }

    /**
     * @param state the state to set
     */
    public void setState(String state) {
        this.state = state;
    }

    /**
     * @return the businessName
     */
    public String getBusinessName() {
        return businessName;
    }

    /**
     * @param businessName the businessName to set
     */
    public void setBusinessName(String businessName) {
        this.businessName = businessName;
    }

    /**
     * @return the businessType
     */
    public String getBusinessType() {
        return businessType;
    }

    /**
     * @param businessType the businessType to set
     */
    public void setBusinessType(String businessType) {
        this.businessType = businessType;
    }

    /**
     * @return the latitiude
     */
    public float getLatitiude() {
        return latitiude;
    }

    /**
     * @param latitiude the latitiude to set
     */
    public void setLatitiude(float latitiude) {
        this.latitiude = latitiude;
    }

    /**
     * @return the longitude
     */
    public float getLongitude() {
        return longitude;
    }

    /**
     * @param longitude the longitude to set
     */
    public void setLongitude(float longitude) {
        this.longitude = longitude;
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
     * @return the reviewCount
     */
    public int getReviewCount() {
        return reviewCount;
    }

    /**
     * @param reviewCount the reviewCount to set
     */
    public void setReviewCount(int reviewCount) {
        this.reviewCount = reviewCount;
    }

    /**
     * @return the open
     */
    public boolean isOpen() {
        return open;
    }

    /**
     * @param open the open to set
     */
    public void setOpen(boolean open) {
        this.open = open;
    }

    /**
     * @return the bHours
     */
    public ArrayList<BusinessHours> getbHours() {
        return bHours;
    }

    /**
     * @param bHours the bHours to set
     */
    public void setbHours(ArrayList<BusinessHours> bHours) {
        this.bHours = bHours;
    }

    /**
     * @return the bCategory
     */
    public ArrayList<BusinessCategory> getbCategory() {
        return bCategory;
    }

    /**
     * @param bCategory the bCategory to set
     */
    public void setbCategory(ArrayList<BusinessCategory> bCategory) {
        this.bCategory = bCategory;
    }

    /**
     * @return the bSubcategory
     */
    public ArrayList<BusinessSubcategory> getbSubcategory() {
        return bSubcategory;
    }

    /**
     * @param bSubcategory the bSubcategory to set
     */
    public void setbSubcategory(ArrayList<BusinessSubcategory> bSubcategory) {
        this.bSubcategory = bSubcategory;
    }
}
