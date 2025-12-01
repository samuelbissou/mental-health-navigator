package com.mycompany.mentalhealthnavigator;

import java.util.ArrayList;

public class ResourceManager {
    private ArrayList<Resource> resources;
    
    public ResourceManager() {
        resources = new ArrayList<>();
        // Start with a few hardcoded resources for testing
        loadSampleData();
    }
    
    // Temporary method for testing
    private void loadSampleData() {
        resources.add(new Resource(
            "National Suicide Prevention Lifeline",
            "Crisis",
            "988",
            "suicidepreventionlifeline.org",
            "24/7 free and confidential support",
            "Free"
        ));
        
        resources.add(new Resource(
            "Crisis Text Line",
            "Crisis",
            "741741",
            "crisistextline.org",
            "Text HOME to 741741",
            "Free"
        ));
        
        resources.add(new Resource(
            "NAMI Helpline",
            "Information",
            "1-800-950-6264",
            "nami.org",
            "Mental health information and support",
            "Free"
        ));
    }
    
    public ArrayList<Resource> getAllResources() {
        return resources;
    }
    
    public ArrayList<Resource> searchResources(String query) {
        ArrayList<Resource> results = new ArrayList<>();
        String searchTerm = query.toLowerCase();
        
        for (Resource r : resources) {
            if (r.getName().toLowerCase().contains(searchTerm) ||
                r.getDescription().toLowerCase().contains(searchTerm) ||
                r.getCategory().toLowerCase().contains(searchTerm)) {
                results.add(r);
            }
        }
        return results;
    }
    
    public ArrayList<Resource> filterByCategory(String category) {
        ArrayList<Resource> results = new ArrayList<>();
        
        for (Resource r : resources) {
            if (r.getCategory().equalsIgnoreCase(category)) {
                results.add(r);
            }
        }
        return results;
    }
    
    public ArrayList<Resource> filterByCost(String cost) {
        ArrayList<Resource> results = new ArrayList<>();
        
        for (Resource r : resources) {
            if (r.getCost().equalsIgnoreCase(cost)) {
                results.add(r);
            }
        }
        return results;
    }
    
    public void loadResourcesFromFile(String filename) {
        // Data Developer will implement CSV reading here
    }
}