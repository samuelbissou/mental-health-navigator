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
        
        // YOUR CODE - Add event handlers
        setupEventHandlers();
        
        setVisible(true);
    }
    
    private void setupGUI() {
        // TOP PANEL - Search and Crisis
        JPanel topPanel = new JPanel();
        topPanel.setLayout(new FlowLayout());
        
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
        
        searchField = new JTextField(30);
        searchButton = new JButton("Search");
        clearButton = new JButton("Clear");
        
        searchPanel.add(new JLabel("Search:"));
        searchPanel.add(searchField);
        searchPanel.add(searchButton);
        searchPanel.add(clearButton);
        
        String[] categories = {"All", "Crisis", "Information"};
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
        
        JScrollPane scrollPane = new JScrollPane(resourceTable);
        add(scrollPane, BorderLayout.CENTER);
        
        // Display all resources initially
        displayResources(resourceManager.getAllResources());
    }
    
    // ========================================
    // YOUR SECTION STARTS HERE - EVENT HANDLERS
    // ========================================
    
    private void setupEventHandlers() {
        // TODO: YOU implement these!
        
        // Search button
        searchButton.addActionListener(e -> handleSearch());
        
        // Crisis button
        crisisButton.addActionListener(e -> handleCrisis());
        
        // Clear button
        clearButton.addActionListener(e -> handleClear());
        
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
        // TODO: YOU implement this!
        String query = searchField.getText();
        
        if (query == null || query.trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, 
                "Please enter a search term", 
                "Search", 
                JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        
        ArrayList<Resource> results = resourceManager.searchResources(query);
        displayResources(results);
        
        JOptionPane.showMessageDialog(this, 
            "Found " + results.size() + " resources", 
            "Search Results", 
            JOptionPane.INFORMATION_MESSAGE);
    }
    
    private void handleCrisis() {
        // TODO: YOU implement this!
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
        // TODO: YOU implement this!
        searchField.setText("");
        displayResources(resourceManager.getAllResources());
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
    // ========================================
    // YOUR SECTION ENDS HERE
    // ========================================
    
    // Helper method - GUI Designer might modify this
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