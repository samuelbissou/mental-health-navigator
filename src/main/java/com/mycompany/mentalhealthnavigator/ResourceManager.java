package com.mycompany.mentalhealthnavigator;

import java.util.ArrayList;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class ResourceManager {
    private ArrayList<Resource> resources;
    
    public ResourceManager() {
        resources = new ArrayList<>();
        // Start with a few hardcoded resources for testing
        loadSampleData();
    }
    
    // Temporary method for testing
    private void loadSampleData() {
        // Try to load from CSV first
        try {
            loadResourcesFromFile("data/resources.csv");
            System.out.println("Loaded " + resources.size() + " resources from CSV");
        } catch (Exception e) {
            System.out.println("Could not load CSV, using hardcoded data");
            // Fallback to hardcoded resources
            resources.add(new Resource(
                "National Suicide Prevention Lifeline",
                "Crisis",
                "988",
                "suicidepreventionlifeline.org",
                "24/7 free and confidential support",
                "Free"
            ));
        }
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
        resources.clear(); // Clear existing resources

        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line = br.readLine(); // Skip header line

            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");

                if (parts.length == 6) {
                    Resource r = new Resource(
                        parts[0], // name
                        parts[1], // category
                        parts[2], // phone
                        parts[3], // website
                        parts[4], // description
                        parts[5]  // cost
                    );
                    resources.add(r);
                }
            }
        } catch (IOException e) {
            throw new RuntimeException("Error reading file: " + e.getMessage());
        }
    }
}