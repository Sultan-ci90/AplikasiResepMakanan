import com.google.gson.Gson;
import javax.swing.*;
import java.awt.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;


public class AplikasiResepMakanan extends javax.swing.JFrame {
     private ArrayList<Resep> daftarResep = new ArrayList<>();
     private List<Meal> favoriteRecipes = new ArrayList<>();
    private JPanel panelContent;
    /**
     * Creates new form AplikasiResepMakanan
     */
    public AplikasiResepMakanan() {
        initComponents();
            setExtendedState(JFrame.MAXIMIZED_BOTH);
    setLocationRelativeTo(null);
        panelContent = new JPanel(new GridLayout(2, 5, 10, 10));  // 2 rows, 5 columns, space between items
        jPanel2.setLayout(new BorderLayout());
        jPanel2.add(panelContent, BorderLayout.CENTER);
        btnHome.addActionListener(e -> showHomePage());
        btnFav.addActionListener(e -> showFavoritePage());
        btnCari.addActionListener(e -> searchResep());
        loadRandomRecipes();
    }
    
    private void loadRandomRecipes() {
        panelContent.removeAll();
        for (int i = 0; i < 10; i++) {
            String apiUrl = "https://www.themealdb.com/api/json/v1/1/random.php";
            String jsonResponse = getJsonResponse(apiUrl);

            if (jsonResponse != null && !jsonResponse.isEmpty()) {
                Gson gson = new Gson();
                MealResponse mealResponse = gson.fromJson(jsonResponse, MealResponse.class);

                if (mealResponse != null && mealResponse.meals != null) {
                    Meal meal = mealResponse.meals.get(0);  // Get the first meal from the response

                    // Set the recipe name and image to corresponding label
                    setRecipeToLabel(i + 2, meal);  // Start from label jLabel2 (i + 2 because jLabel starts at 2)
                    }
                }
            }
            panelContent.revalidate();
            panelContent.repaint();
        }
         private void setRecipeToLabel(int labelIndex, Meal meal) {
        // Get the correct label based on the index
        JLabel labelName = getLabelByIndex(labelIndex);
        JLabel labelImage = getImageLabelByIndex(labelIndex);

        // Set the text (recipe name)
        labelName.setText(meal.strMeal);

        // Set the image (recipe image)
        try {
            // Set the image (recipe image)
        ImageIcon imageIcon = new ImageIcon(new URL(meal.strMealThumb));
        Image image = imageIcon.getImage(); // Get the Image object
        Image scaledImage = image.getScaledInstance(100, 100, Image.SCALE_SMOOTH); // Resize image
        labelImage.setIcon(new ImageIcon(scaledImage));
        } catch (Exception e) {
            e.printStackTrace();
            labelImage.setText("Image not available");
        }
        
        String ingredients = meal.getIngredients();
        Resep resep = new Resep(meal.strMeal, meal.strMealThumb, meal.strInstructions, "Bahan: TBD");

        // Create a clickable JPanel for the recipe
        JPanel recipePanel = new JPanel(new BorderLayout());
        recipePanel.add(labelImage, BorderLayout.CENTER);
        recipePanel.add(labelName, BorderLayout.SOUTH);

        // Add MouseListener to open the RecipeDetailDialog when the panel is clicked
        recipePanel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                // Open RecipeDetailDialog with the selected recipe
                new RecipeDetailDialog(AplikasiResepMakanan.this, meal);  // Pass the main frame and recipe object
                favoriteRecipes.add(meal);
            }
        });

    // Add the panel to the content panel
        panelContent.add(recipePanel);
        }
        
            private JLabel getLabelByIndex(int index) {
        switch (index) {
            case 2: return jLabel2;
            case 3: return jLabel3;
            case 4: return jLabel4;
            case 5: return jLabel5;
            case 6: return jLabel6;
            case 7: return jLabel7;
            case 8: return jLabel8;
            case 9: return jLabel9;
            case 10: return jLabel10;
            case 11: return jLabel11;
            default: return null;
        }
    }

    // Get image label by index (jLabel2 to jLabel11)
    private JLabel getImageLabelByIndex(int index) {
        switch (index) {
            case 2: return jLabel2;  // Adjust this based on your actual JLabel names for images
            case 3: return jLabel3;
            case 4: return jLabel4;
            case 5: return jLabel5;
            case 6: return jLabel6;
            case 7: return jLabel7;
            case 8: return jLabel8;
            case 9: return jLabel9;
            case 10: return jLabel10;
            case 11: return jLabel11;
            default: return null;
        }
    }
    
    private void loadSampleResep() {
        daftarResep.add(new Resep("Nasi Goreng", "FotoNasiGoreng.jpg", "Nasi Goreng Spesial", "Bahan: Nasi, Telur, Bumbu"));
        daftarResep.add(new Resep("Mie Goreng", "FotoMieGoreng.jpg", "Mie Goreng Pedas", "Bahan: Mie, Bumbu"));
    }

    // Function to search recipes from TheMealDB API
    private void searchResep() {

        String query = tfCari.getText();
        String apiUrl = "https://www.themealdb.com/api/json/v1/1/search.php?s=" + query;
        String jsonResponse = getJsonResponse(apiUrl);
        if (query.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter a recipe name.");
            return;
        }

        if (jsonResponse != null && !jsonResponse.isEmpty()) {
            Gson gson = new Gson();
            MealResponse mealResponse = gson.fromJson(jsonResponse, MealResponse.class);

            // Clear the previous content
            panelContent.removeAll();

            // Display recipe results from API
            if (mealResponse != null && mealResponse.meals != null) {
                for (Meal meal : mealResponse.meals) {
                    JPanel panelResep = new JPanel();
                    panelResep.setLayout(new BorderLayout());

                    JLabel labelGambar = new JLabel(new ImageIcon(meal.strMealThumb));
                                    try {
                    // Menampilkan gambar resep
                    ImageIcon imageIcon = new ImageIcon(new URL(meal.strMealThumb));
                    Image image = imageIcon.getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH);
                    labelGambar.setIcon(new ImageIcon(image)); // Set gambar yang sudah diubah ukurannya
                } catch (Exception e) {
                    e.printStackTrace();
                    labelGambar.setText("Image not available");
                }

                    JLabel labelCaption = new JLabel(meal.strMeal);
                    panelResep.add(labelGambar, BorderLayout.CENTER);
                    panelResep.add(labelCaption, BorderLayout.SOUTH);

                panelResep.addMouseListener(new java.awt.event.MouseAdapter() {
                    public void mouseClicked(java.awt.event.MouseEvent evt) {
                        // Open RecipeDetailDialog with the selected recipe
                        new RecipeDetailDialog(AplikasiResepMakanan.this, meal);  // Pass the main frame and recipe object
                    }
                });                    
                    
                    panelContent.add(panelResep);
                }
            }

            panelContent.revalidate();
            panelContent.repaint();
        }
    }

    // Function to show Home Page (you can add more functionality here)
    private void showHomePage() {
        JOptionPane.showMessageDialog(this, "Home Page");
        loadRandomRecipes();
        tfCari.setText("");
    }

    // Function to show Favorite Page (you can add more functionality here)
    private void showFavoritePage() {
    panelContent.removeAll(); // Clear the content of the current panel
    if (favoriteRecipes.isEmpty()) {
        JLabel label = new JLabel("No favorite recipes yet.");
        panelContent.add(label);
    } else {
        // Display each favorite recipe
        for (Meal meal : favoriteRecipes) {  // Use 'Meal' instead of 'Resep'
            JPanel panelResep = new JPanel(new BorderLayout());
            JLabel labelGambar = new JLabel(new ImageIcon(meal.strMealThumb));

            // Resize and display the image
            try {
                ImageIcon imageIcon = new ImageIcon(new URL(meal.strMealThumb));
                Image image = imageIcon.getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH);
                labelGambar.setIcon(new ImageIcon(image)); // Set resized image
            } catch (Exception e) {
                e.printStackTrace();
                labelGambar.setText("Image not available");
            }

            JLabel labelCaption = new JLabel(meal.strMeal);  // Display meal name
            panelResep.add(labelGambar, BorderLayout.CENTER);
            panelResep.add(labelCaption, BorderLayout.SOUTH);

            // Create the 'Edit' button
            JButton btnEdit = new JButton("Edit");
            btnEdit.addActionListener(e -> {
                // Open a dialog to edit the recipe
                openEditRecipeDialog(meal); // Open the edit dialog with the selected meal
            });

            // Add the 'Edit' button to the panel
            JPanel buttonPanel = new JPanel();
            buttonPanel.add(btnEdit);
            panelResep.add(buttonPanel, BorderLayout.EAST);
            
            JButton btnHapus = new JButton("Hapus");
            btnHapus.addActionListener(e -> {
                // Remove the recipe from the favorites list
            favoriteRecipes.remove(meal);
                showFavoritePage();  // Refresh the favorite page after removal
            });

            // Add the 'Hapus' button to the panel
           
            buttonPanel.add(btnHapus);
            panelResep.add(buttonPanel, BorderLayout.EAST);
            btnHapus.addActionListener(e -> {
            int response = JOptionPane.showConfirmDialog(null, "Are you sure you want to remove this recipe from favorites?", "Confirm", JOptionPane.YES_NO_OPTION);
            if (response == JOptionPane.YES_OPTION) {
                favoriteRecipes.remove(meal);  // Remove the recipe from the favorites list
                showFavoritePage();  // Refresh the favorite page after removal
            }
            });

            // Add MouseListener for opening recipe details
            panelResep.addMouseListener(new java.awt.event.MouseAdapter() {
                public void mouseClicked(java.awt.event.MouseEvent evt) {
                    new RecipeDetailDialog(AplikasiResepMakanan.this, meal); // Show recipe details
                }
            });

            panelContent.add(panelResep);
        }
    }
    panelContent.revalidate();
    panelContent.repaint(); // Refresh the layout
    
    }

    // Get JSON response from API
    private String getJsonResponse(String apiUrl) {
        StringBuilder response = new StringBuilder();
        try {
            URL url = new URL(apiUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return response.toString();
    }

   

    // Response class for meal data from API
        class MealResponse {
            List<Meal> meals;
        }

    // Meal class for the API data
    class Meal {
        String strMeal;
        String strMealThumb;
        String strInstructions;
        String strIngredient1, strIngredient2, strIngredient3, strIngredient4, strIngredient5, strIngredient6, strIngredient7, strIngredient8, strIngredient9, strIngredient10;
        public String getIngredients() {
        StringBuilder ingredients = new StringBuilder();
        if (strIngredient1 != null && !strIngredient1.isEmpty()) ingredients.append(strIngredient1).append(", ");
        if (strIngredient2 != null && !strIngredient2.isEmpty()) ingredients.append(strIngredient2).append(", ");
        if (strIngredient3 != null && !strIngredient3.isEmpty()) ingredients.append(strIngredient3).append(", ");
        if (strIngredient4 != null && !strIngredient4.isEmpty()) ingredients.append(strIngredient4).append(", ");
        if (strIngredient5 != null && !strIngredient5.isEmpty()) ingredients.append(strIngredient5).append(", ");
        if (strIngredient6 != null && !strIngredient6.isEmpty()) ingredients.append(strIngredient6).append(", ");
        if (strIngredient7 != null && !strIngredient7.isEmpty()) ingredients.append(strIngredient7).append(", ");
        if (strIngredient8 != null && !strIngredient8.isEmpty()) ingredients.append(strIngredient8).append(", ");
        if (strIngredient9 != null && !strIngredient9.isEmpty()) ingredients.append(strIngredient9).append(", ");
        if (strIngredient10 != null && !strIngredient10.isEmpty()) ingredients.append(strIngredient10).append(", ");
        
        // Remove last comma and space if any
        if (ingredients.length() > 0) ingredients.setLength(ingredients.length() - 2);
        return ingredients.toString();
    }
    }

    // Local recipe class
    class LocalResep {
        private String namaResep;
        private String gambar;
        private String deskripsi;
        private String bahan;

        public LocalResep(String namaResep, String gambar, String deskripsi, String bahan) {
            this.namaResep = namaResep;
            this.gambar = gambar;
            this.deskripsi = deskripsi;
            this.bahan = bahan;
        }

        public String getNamaResep() {
            return namaResep;
        }

        public String getGambar() {
            return gambar;
        }

        public String getDeskripsi() {
            return deskripsi;
        }

        public String getBahan() {
            return bahan;
        }
    }
    
    private void openEditRecipeDialog(Meal meal) {
     // Create a new dialog
    JDialog editDialog = new JDialog(this, "Edit Recipe", true);
    editDialog.setLayout(new GridLayout(6, 2, 10, 10));  // Grid layout for better organization

    // Add labels and text fields for editing
    JTextField tfName = new JTextField(meal.strMeal);  // Recipe Name
    JTextField tfImage = new JTextField(meal.strMealThumb);  // Image URL (optional)
    JTextArea taIngredients = new JTextArea(meal.getIngredients());  // Ingredients
    taIngredients.setRows(4);
    JScrollPane scrollPane = new JScrollPane(taIngredients);

    // Add components to the dialog
    editDialog.add(new JLabel("Recipe Name:"));
    editDialog.add(tfName);
    editDialog.add(new JLabel("Image URL:"));
    editDialog.add(tfImage);
    editDialog.add(new JLabel("Ingredients:"));
    editDialog.add(scrollPane);

    // Add 'Save' and 'Cancel' buttons
    JButton btnSave = new JButton("Save");
    JButton btnCancel = new JButton("Cancel");

    // Save button functionality
    btnSave.addActionListener(e -> {
        meal.strMeal = tfName.getText();  // Update recipe name
        meal.strMealThumb = tfImage.getText();  // Update image URL

        // Parse the ingredients from the JTextArea
        String[] ingredients = taIngredients.getText().split(",");  // Split ingredients by commas

        // Update the Meal's ingredients fields
        for (int i = 0; i < ingredients.length && i < 10; i++) {  // Assume max 10 ingredients
            switch (i) {
                case 0: meal.strIngredient1 = ingredients[i]; break;
                case 1: meal.strIngredient2 = ingredients[i]; break;
                case 2: meal.strIngredient3 = ingredients[i]; break;
                case 3: meal.strIngredient4 = ingredients[i]; break;
                case 4: meal.strIngredient5 = ingredients[i]; break;
                case 5: meal.strIngredient6 = ingredients[i]; break;
                case 6: meal.strIngredient7 = ingredients[i]; break;
                case 7: meal.strIngredient8 = ingredients[i]; break;
                case 8: meal.strIngredient9 = ingredients[i]; break;
                case 9: meal.strIngredient10 = ingredients[i]; break;
            }
        }

        // Refresh the favorite page to reflect changes
        showFavoritePage();
        editDialog.dispose();  // Close the dialog
    });

    // Cancel button functionality
    btnCancel.addActionListener(e -> editDialog.dispose());  // Close the dialog without saving

    // Add buttons to the dialog
    editDialog.add(btnSave);
    editDialog.add(btnCancel);

    // Set dialog properties and display it
    editDialog.setSize(400, 300);
    editDialog.setLocationRelativeTo(this);
    editDialog.setVisible(true);
}
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        btnHome = new javax.swing.JButton();
        btnFav = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        tfCari = new javax.swing.JTextField();
        btnCari = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Aplikasi Resep Makanan");

        jPanel1.setBackground(new java.awt.Color(204, 204, 204));
        java.awt.FlowLayout flowLayout1 = new java.awt.FlowLayout(java.awt.FlowLayout.CENTER, 20, 5);
        flowLayout1.setAlignOnBaseline(true);
        jPanel1.setLayout(flowLayout1);

        btnHome.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnHome.setText("Home");
        jPanel1.add(btnHome);

        btnFav.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnFav.setText("Favorite");
        jPanel1.add(btnFav);

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel1.setText("Resep Makanan");
        jPanel1.add(jLabel1);

        tfCari.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        tfCari.setPreferredSize(new java.awt.Dimension(200, 22));
        tfCari.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                tfCariKeyTyped(evt);
            }
        });
        jPanel1.add(tfCari);

        btnCari.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnCari.setText("Cari");
        jPanel1.add(btnCari);

        getContentPane().add(jPanel1, java.awt.BorderLayout.NORTH);

        jPanel2.setLayout(new java.awt.GridLayout(2, 0));

        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setText("jLabel2");
        jPanel2.add(jLabel2);

        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel3.setText("jLabel3");
        jPanel2.add(jLabel3);

        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel4.setText("jLabel4");
        jPanel2.add(jLabel4);

        jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel5.setText("jLabel5");
        jPanel2.add(jLabel5);

        jLabel6.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel6.setText("jLabel6");
        jPanel2.add(jLabel6);

        jLabel7.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel7.setText("jLabel7");
        jPanel2.add(jLabel7);

        jLabel8.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel8.setText("jLabel7");
        jPanel2.add(jLabel8);

        jLabel9.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel9.setText("jLabel7");
        jPanel2.add(jLabel9);

        jLabel10.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel10.setText("jLabel7");
        jPanel2.add(jLabel10);

        jLabel11.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel11.setText("jLabel7");
        jPanel2.add(jLabel11);

        getContentPane().add(jPanel2, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void tfCariKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tfCariKeyTyped
       
    }//GEN-LAST:event_tfCariKeyTyped

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
       //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(AplikasiResepMakanan.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(AplikasiResepMakanan.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(AplikasiResepMakanan.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(AplikasiResepMakanan.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        new AplikasiResepMakanan();
        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new AplikasiResepMakanan().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCari;
    private javax.swing.JButton btnFav;
    private javax.swing.JButton btnHome;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JTextField tfCari;
    // End of variables declaration//GEN-END:variables
}
