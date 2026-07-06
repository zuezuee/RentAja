package model;

import java.time.LocalDate;

public class Transaksi {
    private String id;
    private Mobil mobil;
    private Customer customer;
    private LocalDate tglSewa;
    private int lamaSewa;
    private double totalBiaya;

    public Transaksi(String id, Mobil mobil, Customer customer, LocalDate tglSewa, int lamaSewa) {
        this.id = id;
        this.mobil = mobil;
        this.customer = customer;
        this.tglSewa = tglSewa;
        this.lamaSewa = lamaSewa;
        this.totalBiaya = mobil.hitungBiaya(lamaSewa); // manfaatin polymorphism
    }

    public String getId() { return id; }
    public Mobil getMobil() { return mobil; }
    public Customer getCustomer() { return customer; }
    public LocalDate getTglSewa() { return tglSewa; }
    public int getLamaSewa() { return lamaSewa; }
    public double getTotalBiaya() { return totalBiaya; }
}