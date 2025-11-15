public class Resep {
    private String namaResep;
    private String gambar;
    private String deskripsi;
    private String bahan;

    public Resep(String namaResep, String gambar, String deskripsi, String bahan) {
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
