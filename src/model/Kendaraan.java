package model;

public abstract class Kendaraan {
    protected String id;
    protected String merk;
    protected String tipe;
    protected double hargaSewa;

    public Kendaraan(String id, String merk, String tipe, double hargaSewa) {
        this.id = id;
        this.merk = merk;
        this.tipe = tipe;
        this.hargaSewa = hargaSewa;
    }

    // Encapsulation: getter & setter
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getMerk() { return merk; }
    public void setMerk(String merk) { this.merk = merk; }

    public String getTipe() { return tipe; }
    public void setTipe(String tipe) { this.tipe = tipe; }

    public double getHargaSewa() { return hargaSewa; }
    public void setHargaSewa(double hargaSewa) { this.hargaSewa = hargaSewa; }

    // Abstract method, wajib di-override anak class-nya
    public abstract double hitungBiaya(int lamaSewa);
}