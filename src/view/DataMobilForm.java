package view;

import dao.MobilDAO;
import model.Mobil;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

/**
 * Form untuk menampilkan daftar mobil.
 * Ada tombol untuk lihat "Semua Mobil" atau "Mobil Tersedia" saja.
 *
 * CATATAN: nama getter (getIdMobil, getMerk, getModel, getTahun,
 * getHargaSewa, getStatus) mengikuti pola umum. Kalau nama getter
 * di class Mobil punya Orang 1 berbeda, sesuaikan di method
 * refreshTable() di bawah.
 */
public class DataMobilForm extends JFrame {

    private JTable table;
    private DefaultTableModel tableModel;
    private MobilDAO mobilDAO;

    private final String[] kolom = {
            "ID Mobil", "Merk", "Tipe", "Tahun", "Harga Sewa/Hari", "Status"
    };

    public DataMobilForm() {
        mobilDAO = new MobilDAO();

        setTitle("Data Mobil");
        setSize(650, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        tableModel = new DefaultTableModel(kolom, 0) {
            @Override
            public boolean isCellEditable(int row, int col) {
                return false; // tabel read-only
            }
        };
        table = new JTable(tableModel);

        JScrollPane scrollPane = new JScrollPane(table);

        JButton btnSemua = new JButton("Tampilkan Semua Mobil");
        JButton btnTersedia = new JButton("Tampilkan Mobil Tersedia");
        JButton btnTutup = new JButton("Tutup");

        JPanel panelTombol = new JPanel();
        panelTombol.add(btnSemua);
        panelTombol.add(btnTersedia);
        panelTombol.add(btnTutup);

        add(scrollPane, BorderLayout.CENTER);
        add(panelTombol, BorderLayout.SOUTH);

        btnSemua.addActionListener(e -> refreshTable(mobilDAO.getAllMobil()));
        btnTersedia.addActionListener(e -> refreshTable(mobilDAO.getMobilTersedia()));
        btnTutup.addActionListener(e -> dispose());

        // Tampilkan semua mobil saat form pertama kali dibuka
        refreshTable(mobilDAO.getAllMobil());
    }

    private void refreshTable(List<Mobil> daftarMobil) {
        tableModel.setRowCount(0); // kosongkan tabel dulu

        if (daftarMobil == null) return;

        for (Mobil m : daftarMobil) {
            tableModel.addRow(new Object[]{
                    m.getIdMobil(),
                    m.getMerk(),
                    m.getTipe(),
                    m.getTahun(),
                    m.getHargaSewa(),
                    m.getStatus()
            });
        }
    }
}