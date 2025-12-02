package com.mycompany.mentalhealthnavigator;

public class Resource {
    private String name;
    private String category;
    private String phone;
    private String website;
    private String description;
    private String cost;
    
    // Constructors
    public Resource(String name, String category, String phone, 
                    String website, String description, String cost) {
        this.name = name;
        this.category = category;
        this.phone = phone;
        this.website = website;
        this.description = description;
        this.cost = cost;
    }
    
    // Getters
    public String getName() { return name; }
    public String getCategory() { return category; }
    public String getPhone() { return phone; }
    public String getWebsite() { return website; }
    public String getDescription() { return description; }
    public String getCost() { return cost; }
}