import javax.swing.*;
import java.awt.*;
import java.net.URL;
import javax.swing.ImageIcon;

public class MealImageDisplay {

    // Fungsi untuk menampilkan gambar dari URL
    public static void displayImage(String imageUrl) {
        try {
            // Membuat URL dari gambar
            URL url = new URL(imageUrl);
            ImageIcon imageIcon = new ImageIcon(url); // Mengubah URL ke ImageIcon
            JLabel label = new JLabel(imageIcon); // Membuat JLabel untuk menampilkan gambar

            // Membuat JFrame untuk menampilkan gambar
            JFrame frame = new JFrame("Meal Image");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(400, 400); // Ukuran jendela
            frame.setLayout(new FlowLayout());
            frame.add(label); // Menambahkan gambar ke frame
            frame.setVisible(true); // Menampilkan frame

        } catch (Exception e) {
            e.printStackTrace(); // Menangani error jika URL tidak valid
        }
    }

    public static void main(String[] args) {
        // Gambar dengan ukuran kecil (small)
        String imageUrl = "https://www.themealdb.com/images/media/meals/llcbn01574260722.jpg/small";
        displayImage(imageUrl); // Menampilkan gambar dalam ukuran kecil
    }
}
