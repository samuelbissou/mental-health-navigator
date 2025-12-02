package com.mycompany.mentalhealthnavigator;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import javax.swing.JFrame;
import java.util.ArrayList; 

public class MentalHealthNavigator extends JFrame {
       
    // GUI Components
    private ResourceManager resourceManager;
    private JTable resourceTable;
    private DefaultTableModel tableModel;
    private JTextField searchField;
    private JButton searchButton;
    private JButton crisisButton;
    private JButton clearButton;
    private JButton favoritesButton;
    private JButton viewFavoritesButton;
    private JButton quizButton;
    private JComboBox<String> categoryFilter;
    
    
    public MentalHealthNavigator() {
        // Initialize resource manager
        resourceManager = new ResourceManager();
        
        // Setup window
        setTitle("Mental Health Resource Navigator");
        setSize(900, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        
        
        // Create GUI
        setupGUI();
        
        // Create Event handlers
        setupEventHandlers();
        
        setVisible(true);
    }
    
    private void setupGUI() {
        // TOP PANEL - Search and Crisis
        JPanel topPanel = new JPanel();
        topPanel.setLayout(new FlowLayout());
        topPanel.setBackground(new Color(152, 251, 152));
        
        
        JLabel titleLabel = new JLabel("Mental Health Resources");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        
        
        crisisButton = new JButton("🚨 CRISIS: Call 988");
        crisisButton.setBackground(new Color(220, 53, 69));
        crisisButton.setForeground(Color.WHITE);
        crisisButton.setFont(new Font("Arial", Font.BOLD, 14));
        
        topPanel.add(titleLabel);
        topPanel.add(Box.createHorizontalStrut(50));
        topPanel.add(crisisButton);
        
        // SEARCH PANEL
        JPanel searchPanel = new JPanel();
        searchPanel.setLayout(new FlowLayout());
        searchPanel.setBackground(new Color(152, 251, 152));
        
        searchField = new JTextField(30);
        searchButton = new JButton("Search");
        clearButton = new JButton("Clear");
        favoritesButton = new JButton("⭐ Add to Favorites");
        viewFavoritesButton = new JButton("📋 View Favorites");
        quizButton = new JButton("🧠 Wellness Quiz");
        
        
        searchPanel.add(new JLabel("Search:"));
        searchPanel.add(searchField);
        searchPanel.add(searchButton);
        searchPanel.add(clearButton);
        searchPanel.add(favoritesButton);
        searchPanel.add(viewFavoritesButton);
        searchPanel.add(quizButton);
        
        //Button effects
        searchButton.setBackground(new Color(60, 179, 113)); 
        searchButton.setForeground(Color.WHITE);
        clearButton.setBackground(new Color(60, 179, 113)); 
        clearButton.setForeground(Color.WHITE);
        favoritesButton.setBackground(new Color(255, 215, 0)); 
        favoritesButton.setForeground(Color.BLACK);
        viewFavoritesButton.setBackground(new Color(135, 206, 250)); 
        viewFavoritesButton.setForeground(Color.BLACK);
        quizButton.setBackground(new Color(147, 112, 219)); // Purple
        quizButton.setForeground(Color.WHITE);
        
        String[] categories = {"All", "Crisis", "Information","Online Resource","Support Group","Therapy"};
        categoryFilter = new JComboBox<>(categories);
        searchPanel.add(new JLabel("Category:"));
        searchPanel.add(categoryFilter);
        
        // Combine top panels
        JPanel northPanel = new JPanel();
        northPanel.setLayout(new BorderLayout());
        northPanel.add(topPanel, BorderLayout.NORTH);
        northPanel.add(searchPanel, BorderLayout.CENTER);
        
        add(northPanel, BorderLayout.NORTH);
        
        // CENTER PANEL - Table
        String[] columns = {"Name", "Category", "Phone", "Cost"};
        tableModel = new DefaultTableModel(columns, 0);
        resourceTable = new JTable(tableModel);
        

        resourceTable.setForeground(Color.BLACK);
        resourceTable.setGridColor(Color.BLACK);
        
        JScrollPane scrollPane = new JScrollPane(resourceTable);
        add(scrollPane, BorderLayout.CENTER);
        
        
        // Display all resources initially
        displayResources(resourceManager.getAllResources());
        getContentPane().setBackground(new Color(245, 255, 250));
        
    }
    
    // ==================
    // EVENT HANDLERS
    // ==================
    
    private void setupEventHandlers() {

        // Search button
        searchButton.addActionListener(e -> handleSearch());
        
        // Crisis button
        crisisButton.addActionListener(e -> handleCrisis());
        
        // Clear button
        clearButton.addActionListener(e -> handleClear());
        
        // Favorites button
        favoritesButton.addActionListener(e -> handleAddToFavorites());
        
        // View Favorites button
        viewFavoritesButton.addActionListener(e -> handleViewFavorites());
        
        // Quiz button
        quizButton.addActionListener(e -> handleWellnessQuiz());
        
        // Enter key in search field
        searchField.addActionListener(e -> handleSearch());
        
        // Table selection
        resourceTable.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                handleTableSelection();
            }
        });
        categoryFilter.addActionListener(e -> handleCategoryFilter());
    }

    private void handleTableSelection() {
        int selectedRow = resourceTable.getSelectedRow();

        if (selectedRow != -1) {
            String name = (String) tableModel.getValueAt(selectedRow, 0);

            // Find the full resource
            ArrayList<Resource> all = resourceManager.getAllResources();
            for (Resource r : all) {
                if (r.getName().equals(name)) {
                    showResourceDetails(r);
                    break;
                }
            }
        }
    }

    private void showResourceDetails(Resource r) {
        String details = "Name: " + r.getName() + "\n" +
                        "Category: " + r.getCategory() + "\n" +
                        "Phone: " + r.getPhone() + "\n" +
                        "Website: " + r.getWebsite() + "\n" +
                        "Cost: " + r.getCost() + "\n\n" +
                        "Description:\n" + r.getDescription();

        JOptionPane.showMessageDialog(this,
            details,
            "Resource Details",
            JOptionPane.INFORMATION_MESSAGE);
    }
    
    private void handleSearch() {
    String query = searchField.getText().trim();
    
    // Validation - check if empty
    if (query.isEmpty()) {
        JOptionPane.showMessageDialog(this, 
            "Please enter a search term", 
            "Invalid Search", 
            JOptionPane.WARNING_MESSAGE);
        return;
    }
    
    // Validation - check minimum length
    if (query.length() < 2) {
        JOptionPane.showMessageDialog(this, 
            "Please enter at least 2 characters", 
            "Invalid Search", 
            JOptionPane.WARNING_MESSAGE);
        return;
    }
    
    // Do the search
    ArrayList<Resource> results = resourceManager.searchResources(query);
    displayResources(results);
    
    JOptionPane.showMessageDialog(this, 
        "Found " + results.size() + " resources", 
        "Search Results", 
        JOptionPane.INFORMATION_MESSAGE);
}
    
    private void handleCrisis() {
        JOptionPane.showMessageDialog(this,
            "🚨 CRISIS SUPPORT 🚨\n\n" +
            "National Suicide Prevention Lifeline\n" +
            "Call or Text: 988\n\n" +
            "Crisis Text Line\n" +
            "Text HOME to: 741741\n\n" +
            "Available 24/7 - Free and Confidential",
            "Emergency Crisis Support",
            JOptionPane.WARNING_MESSAGE);
    }
    
    private void handleClear() {
        searchField.setText("");
        displayResources(resourceManager.getAllResources());
    }
    
    private void handleAddToFavorites() {
    int selectedRow = resourceTable.getSelectedRow();
    
    if (selectedRow == -1) {
        JOptionPane.showMessageDialog(this,
            "Please select a resource first",
            "No Selection",
            JOptionPane.WARNING_MESSAGE);
        return;
    }
    
    // Get the selected resource
    String name = (String) tableModel.getValueAt(selectedRow, 0);
    
    ArrayList<Resource> all = resourceManager.getAllResources();
    for (Resource r : all) {
        if (r.getName().equals(name)) {
            resourceManager.saveFavorite(r);
            JOptionPane.showMessageDialog(this,
                "Added to favorites!",
                "Success",
                JOptionPane.INFORMATION_MESSAGE);
            break;
        }
    }
    }
    
    private void handleViewFavorites() {
    ArrayList<Resource> favorites = resourceManager.loadFavorites();
    
    if (favorites.isEmpty()) {
        JOptionPane.showMessageDialog(this,
            "No favorites saved yet",
            "Favorites",
            JOptionPane.INFORMATION_MESSAGE);
    } else {
        displayResources(favorites);
        JOptionPane.showMessageDialog(this,
            "Showing " + favorites.size() + " favorites",
            "Favorites",
            JOptionPane.INFORMATION_MESSAGE);
    }
    }   
    
    private void handleCategoryFilter() {
    String selected = (String) categoryFilter.getSelectedItem();
    
    if (selected.equals("All")) {
        displayResources(resourceManager.getAllResources());
    } else {
        ArrayList<Resource> filtered = resourceManager.filterByCategory(selected);
        displayResources(filtered);
        }
    }
    private void handleWellnessQuiz() {
    int score = 0;
    
    // Question 1
    String[] options1 = {"Never", "Sometimes", "Often", "Always"};
    int answer1 = JOptionPane.showOptionDialog(this,
        "How often do you feel stressed or overwhelmed?",
        "Wellness Quiz - Question 1 of 5",
        JOptionPane.DEFAULT_OPTION,
        JOptionPane.QUESTION_MESSAGE,
        null, options1, options1[0]);
    
    if (answer1 == -1) return; // User cancelled
    score += answer1;
    
    // Question 2
    String[] options2 = {"Very Poor", "Poor", "Fair", "Good", "Excellent"};
    int answer2 = JOptionPane.showOptionDialog(this,
        "How would you rate your sleep quality?",
        "Wellness Quiz - Question 2 of 5",
        JOptionPane.DEFAULT_OPTION,
        JOptionPane.QUESTION_MESSAGE,
        null, options2, options2[2]);
    
    if (answer2 == -1) return;
    score += (4 - answer2); // Reverse scoring
    
    // Question 3
    String[] options3 = {"Never", "Rarely", "Sometimes", "Often"};
    int answer3 = JOptionPane.showOptionDialog(this,
        "How often do you feel sad or anxious?",
        "Wellness Quiz - Question 3 of 5",
        JOptionPane.DEFAULT_OPTION,
        JOptionPane.QUESTION_MESSAGE,
        null, options3, options3[0]);
    
    if (answer3 == -1) return;
    score += answer3;
    
    // Question 4
    String[] options4 = {"Yes, regularly", "Sometimes", "Rarely", "Never"};
    int answer4 = JOptionPane.showOptionDialog(this,
        "Do you engage in activities you enjoy?",
        "Wellness Quiz - Question 4 of 5",
        JOptionPane.DEFAULT_OPTION,
        JOptionPane.QUESTION_MESSAGE,
        null, options4, options4[0]);
    
    if (answer4 == -1) return;
    score += answer4;
    
    // Question 5
    String[] options5 = {"Very comfortable", "Comfortable", "Uncomfortable", "Very uncomfortable"};
    int answer5 = JOptionPane.showOptionDialog(this,
        "How comfortable are you reaching out for support?",
        "Wellness Quiz - Question 5 of 5",
        JOptionPane.DEFAULT_OPTION,
        JOptionPane.QUESTION_MESSAGE,
        null, options5, options5[0]);
    
    if (answer5 == -1) return;
    score += answer5;
    
    // Show results
    showQuizResults(score);
    }
    
    private void showQuizResults(int score) {
    String result = "";
    String recommendation = "";
    
    if (score <= 4) {
        result = "✅ Great Mental Wellness!\n\n";
        recommendation = "You're doing well! Continue with:\n" +
                        "• Maintaining healthy habits\n" +
                        "• Staying connected with others\n" +
                        "• Practicing self-care\n\n" +
                        "Resources: Check out our Information category";
    } else if (score <= 8) {
        result = "⚠️ Moderate Stress Level\n\n";
        recommendation = "Consider exploring:\n" +
                        "• Support groups in your area\n" +
                        "• Online mental health resources\n" +
                        "• Talking to a counselor\n\n" +
                        "Resources: Browse our Support Group and Online Resource categories";
    } else {
        result = "🚨 Higher Stress/Concern Level\n\n";
        recommendation = "It might be helpful to:\n" +
                        "• Talk to a mental health professional\n" +
                        "• Reach out to crisis support if needed\n" +
                        "• Connect with trusted friends/family\n\n" +
                        "Resources: Check our Crisis and Therapy categories\n\n" +
                        "CRISIS SUPPORT: Call or text 988 anytime";
    }
    
    JOptionPane.showMessageDialog(this,
        result + "Your Score: " + score + "/15\n\n" + recommendation,
        "Wellness Quiz Results",
        JOptionPane.INFORMATION_MESSAGE);
    }
    
    // ========================================
    //
    // ========================================
    
    // Helper method 
    private void displayResources(ArrayList<Resource> resources) {
        tableModel.setRowCount(0); // Clear table
        
        for (Resource r : resources) {
            tableModel.addRow(new Object[]{
                r.getName(),
                r.getCategory(),
                r.getPhone(),
                r.getCost()
            });
        }
    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new MentalHealthNavigator();
        });
}
}