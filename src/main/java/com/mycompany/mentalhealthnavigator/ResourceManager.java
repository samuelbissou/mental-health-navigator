package com.mycompany.mentalhealthnavigator;

import java.util.ArrayList;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.FileWriter;
import java.io.BufferedWriter;
import java.io.PrintWriter;

public class ResourceManager {
    private ArrayList<Resource> resources;
    
    public ResourceManager() {
        resources = new ArrayList<>();
        loadSampleData();
    }
    
    // Temporary method for testing
    private void loadSampleData() {
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
    
    public void saveFavorite(Resource r) {
    try (FileWriter fw = new FileWriter("data/favorites.txt", true);
         BufferedWriter bw = new BufferedWriter(fw);
         PrintWriter out = new PrintWriter(bw)) {
        
        out.println(r.getName() + "," + 
                    r.getCategory() + "," + 
                    r.getPhone() + "," + 
                    r.getWebsite() + "," + 
                    r.getDescription() + "," + 
                    r.getCost());
        
    } catch (IOException e) {
        System.err.println("Error saving favorite: " + e.getMessage());
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
    
    
    
    public ArrayList<Resource> loadFavorites() {
    ArrayList<Resource> favorites = new ArrayList<>();
    
    try (BufferedReader br = new BufferedReader(new FileReader("data/favorites.txt"))) {
        String line;
        
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
                favorites.add(r);
            }
        }
    } catch (IOException e) {
        System.out.println("No favorites file found or error reading: " + e.getMessage());
    }
    
    return favorites;
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