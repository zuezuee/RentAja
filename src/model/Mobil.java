package model;

public class Mobil extends Kendaraan {
    private int tahun;
    private String status; // "Tersedia" atau "Disewa"

    public Mobil(String id, String merk, String tipe, int tahun, double hargaSewa, String status) {
        super(id, merk, tipe, hargaSewa);
        this.tahun = tahun;
        this.status = status;
    }

    public int getTahun() { return tahun; }
    public void setTahun(int tahun) { this.tahun = tahun; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    // Alias getter biar sinkron sama pemanggilan di view (DataMobilForm, FormSewaMobil)
    public String getIdMobil() { return id; }

    // Polymorphism: override method dari parent
    @Override
    public double hitungBiaya(int lamaSewa) {
        return hargaSewa * lamaSewa;
    }

    @Override
    public String toString() {
        return id + " - " + merk + " " + tipe + " (" + status + ")";
    }
}