import javax.swing.*;
import java.awt.*;
import java.awt.print.*;
import java.awt.image.BufferedImage;

public class RecipeDetailDialog extends JDialog {

    // Constructor untuk menerima objek Meal
    public RecipeDetailDialog(JFrame parent, AplikasiResepMakanan.Meal meal) {
        super(parent, "Detail Resep", true); // true indicates that the dialog is modal

        setSize(400, 300);
        setLocationRelativeTo(parent);  // Center the dialog relative to the parent frame

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS)); // Use vertical BoxLayout

        // Label untuk nama resep
        JLabel labelNamaResep = new JLabel("Nama: " + meal.strMeal);

        // Menambahkan gambar resep
        ImageIcon imageIcon = new ImageIcon(meal.strMealThumb); // Load image from URL
        JLabel labelImage = new JLabel(imageIcon);

        // Menambahkan bahan dan deskripsi ke dalam JTextArea
        JTextArea textAreaBahan = new JTextArea("Bahan: " + meal.getIngredients(), 5, 20);
        textAreaBahan.setWrapStyleWord(true);
        textAreaBahan.setLineWrap(true);
        textAreaBahan.setCaretPosition(0);
        textAreaBahan.setEditable(false);
        JScrollPane scrollBahan = new JScrollPane(textAreaBahan);

        JTextArea textAreaDeskripsi = new JTextArea("Deskripsi: " + meal.strInstructions, 5, 20);
        textAreaDeskripsi.setWrapStyleWord(true);
        textAreaDeskripsi.setLineWrap(true);
        textAreaDeskripsi.setCaretPosition(0);
        textAreaDeskripsi.setEditable(false);
        JScrollPane scrollDeskripsi = new JScrollPane(textAreaDeskripsi);

        JButton btnFavorit = new JButton("Tambah ke Favorit");

        // Menambahkan komponen ke panel
        panel.add(labelNamaResep);
        panel.add(labelImage);  // Menambahkan gambar resep
        panel.add(scrollBahan); // Menambahkan bahan
        panel.add(scrollDeskripsi); // Menambahkan deskripsi
        panel.add(btnFavorit);

        // Aksi ketika tombol "Tambah ke Favorit" diklik
        btnFavorit.addActionListener(e -> {
            // Logika untuk menambah ke favorit bisa diimplementasikan di sini
            JOptionPane.showMessageDialog(this, "Resep ditambahkan ke favorit.");
        });
        
        JPanel panelButton = new JPanel(new FlowLayout(FlowLayout.RIGHT));

        JButton btnCetak = new JButton("Cetak Resep");
        JButton btnTutup = new JButton("Tutup");

        // Aksi tombol cetak
        btnCetak.addActionListener(e -> cetakResep(meal));

        btnTutup.addActionListener(e -> dispose());

        panelButton.add(btnCetak);
        panelButton.add(btnTutup);
        panel.add(panelButton);

        add(panel);
        setVisible(true);
    }

    // Fungsi untuk mencetak resep
    private void cetakResep(AplikasiResepMakanan.Meal meal) {
        // Gabungkan semua detail resep dalam satu JTextArea untuk pencetakan
        JTextArea taDetail = new JTextArea();
        taDetail.setText("Nama Resep: " + meal.strMeal + "\n\n" +
                "Bahan: " + meal.getIngredients() + "\n\n" +
                "Deskripsi: " + meal.strInstructions);
        taDetail.setEditable(false);  // Set to non-editable

        PrinterJob job = PrinterJob.getPrinterJob();
        if (job.printDialog()) {  // Open printer dialog
            try {
                job.setPrintable((graphics, pageFormat, pageIndex) -> {
                    if (pageIndex >= 1) {
                        return Printable.NO_SUCH_PAGE;  // No more pages
                    }

                    // Set margin for printing
                    int x = 100;
                    int y = 100;
                    int lineHeight = 15;  // Adjust the line height to prevent text from overlapping

                    // Set font for title and body text
                    graphics.setFont(new Font("Serif", Font.BOLD, 16));  // Set bold font for title
                    graphics.drawString("Nama Resep: " + meal.strMeal, x, y);
                    y += lineHeight * 2; // Move down for the next line

                    graphics.setFont(new Font("Serif", Font.PLAIN, 12));  // Set regular font for body text
                    String bahanText = "Bahan: " + meal.getIngredients();
                    String deskripsiText = "Deskripsi: " + meal.strInstructions;

                    // Print Bahan (Ingredients)
                    y = printWrappedText(graphics, bahanText, x, y, pageFormat);

                    // Print Deskripsi (Instructions)
                    y = printWrappedText(graphics, deskripsiText, x, y, pageFormat);

                    // Print image if available
                    if (meal.strMealThumb != null && !meal.strMealThumb.isEmpty()) {
                        try {
                            ImageIcon imageIcon = new ImageIcon(meal.strMealThumb);
                            BufferedImage image = (BufferedImage) imageIcon.getImage();
                            graphics.drawImage(image, x, y, 100, 100, null);
                            y += 110;  // Move down after drawing the image
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                    return Printable.PAGE_EXISTS;
                });

                job.print();  // Print the content
            } catch (PrinterException e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(this,
                        "Terjadi kesalahan saat mencetak: " + e.getMessage(),
                        "Error Cetak",
                        JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    // Helper function to handle word wrapping and moving to the next line
    private int printWrappedText(Graphics g, String text, int x, int y, PageFormat pageFormat) {
        FontMetrics metrics = g.getFontMetrics();
        int lineHeight = metrics.getHeight();
        int maxWidth = (int) pageFormat.getImageableWidth() - 2 * x; // Max width considering margins
        String[] words = text.split(" ");
        StringBuilder currentLine = new StringBuilder();

        for (String word : words) {
            if (metrics.stringWidth(currentLine.toString() + word) > maxWidth) {
                // If the current line is too long, print it and start a new line
                g.drawString(currentLine.toString(), x, y);
                y += lineHeight;
                currentLine = new StringBuilder(word + " ");  // Start a new line with the current word
            } else {
                currentLine.append(word).append(" ");  // Add word to the current line
            }
        }

        // Print any remaining words in currentLine
        if (currentLine.length() > 0) {
            g.drawString(currentLine.toString(), x, y);
            y += lineHeight;
        }

        return y;  // Return the new y position after printing
    }
}
