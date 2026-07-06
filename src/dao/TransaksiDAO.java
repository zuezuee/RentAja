package dao;

import model.*;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class TransaksiDAO {

    private MobilDAO mobilDAO = new MobilDAO();
    private CustomerDAO customerDAO = new CustomerDAO();

    // Proses sewa mobil: validasi stok, hitung biaya (polymorphism), simpan transaksi, update status mobil
    public boolean sewaMobil(String idTransaksi, String idMobil, String idCustomer, LocalDate tglSewa, int lamaSewa) {
        // 1. Ambil data mobil & customer dulu
        Mobil mobil = null;
        for (Mobil m : mobilDAO.getAllMobil()) {
            if (m.getId().equals(idMobil)) {
                mobil = m;
                break;
            }
        }
        Customer customer = customerDAO.getCustomerById(idCustomer);

        if (mobil == null || customer == null) {
            System.out.println("Mobil atau customer tidak ditemukan.");
            return false;
        }

        // 2. Validasi stok
        if (!mobil.getStatus().equals("Tersedia")) {
            System.out.println("Mobil sedang tidak tersedia.");
            return false;
        }

        // 3. Hitung biaya pakai polymorphism (hitungBiaya bisa beda-beda tergantung jenis kendaraan)
        double totalBiaya = mobil.hitungBiaya(lamaSewa);

        // 4. Simpan transaksi ke database
        String sql = "INSERT INTO transaksi_sewa (id_transaksi, id_mobil, id_customer, tgl_sewa, lama_sewa, total_biaya) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = Koneksi.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, idTransaksi);
            stmt.setString(2, idMobil);
            stmt.setString(3, idCustomer);
            stmt.setDate(4, Date.valueOf(tglSewa));
            stmt.setInt(5, lamaSewa);
            stmt.setDouble(6, totalBiaya);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }

        // 5. Update status mobil jadi "Disewa"
        mobilDAO.updateStatusMobil(idMobil, "Disewa");

        return true;
    }

    // Ambil semua riwayat transaksi (buat laporan)
    public List<String> getAllTransaksi() {
        List<String> list = new ArrayList<>();
        String sql = "SELECT * FROM transaksi_sewa";
        try (Connection conn = Koneksi.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                String baris = rs.getString("id_transaksi") + " | "
                        + rs.getString("id_mobil") + " | "
                        + rs.getString("id_customer") + " | "
                        + rs.getDate("tgl_sewa") + " | "
                        + rs.getInt("lama_sewa") + " hari | Rp"
                        + rs.getDouble("total_biaya");
                list.add(baris);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
}