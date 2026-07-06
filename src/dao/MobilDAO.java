package dao;

import model.Mobil;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MobilDAO {

    public List<Mobil> getAllMobil() {
        List<Mobil> list = new ArrayList<>();
        String sql = "SELECT * FROM mobil";
        try (Connection conn = Koneksi.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                Mobil m = new Mobil(
                        rs.getString("id_mobil"),
                        rs.getString("merk"),
                        rs.getString("tipe"),
                        rs.getInt("tahun"),
                        rs.getDouble("harga_sewa"),
                        rs.getString("status")
                );
                list.add(m);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public List<Mobil> getMobilTersedia() {
        List<Mobil> list = new ArrayList<>();
        String sql = "SELECT * FROM mobil WHERE status = 'Tersedia'";
        try (Connection conn = Koneksi.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                Mobil m = new Mobil(
                        rs.getString("id_mobil"),
                        rs.getString("merk"),
                        rs.getString("tipe"),
                        rs.getInt("tahun"),
                        rs.getDouble("harga_sewa"),
                        rs.getString("status")
                );
                list.add(m);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public boolean tambahMobil(Mobil m) {
        String sql = "INSERT INTO mobil (id_mobil, merk, tipe, tahun, harga_sewa, status) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = Koneksi.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, m.getId());
            stmt.setString(2, m.getMerk());
            stmt.setString(3, m.getTipe());
            stmt.setInt(4, m.getTahun());
            stmt.setDouble(5, m.getHargaSewa());
            stmt.setString(6, m.getStatus());
            stmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean updateStatusMobil(String idMobil, String statusBaru) {
        String sql = "UPDATE mobil SET status = ? WHERE id_mobil = ?";
        try (Connection conn = Koneksi.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, statusBaru);
            stmt.setString(2, idMobil);
            stmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean hapusMobil(String idMobil) {
        String sql = "DELETE FROM mobil WHERE id_mobil = ?";
        try (Connection conn = Koneksi.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, idMobil);
            stmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}