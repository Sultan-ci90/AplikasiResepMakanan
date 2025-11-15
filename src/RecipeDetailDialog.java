import javax.swing.*;
import java.awt.*;



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

        add(panel);
        setVisible(true);
    }
}
