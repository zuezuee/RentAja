package view;

import dao.TransaksiDAO;

import javax.swing.*;
import java.awt.*;
import java.util.List;

/**
 * Menampilkan laporan transaksi.
 * TransaksiDAO.getAllTransaksi() mengembalikan List<String> (baris teks
 * yang sudah diformat dari Orang 2), jadi ditampilkan pakai JList
 * dengan font monospace supaya kalau formatnya rata kolom, tetap rapi.
 */
public class LaporanTransaksiForm extends JFrame {

    private JList<String> listTransaksi;
    private DefaultListModel<String> listModel;
    private TransaksiDAO transaksiDAO;

    public LaporanTransaksiForm() {
        transaksiDAO = new TransaksiDAO();

        setTitle("Laporan Transaksi");
        setSize(600, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        listModel = new DefaultListModel<>();
        listTransaksi = new JList<>(listModel);
        listTransaksi.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 13));

        JScrollPane scrollPane = new JScrollPane(listTransaksi);

        JButton btnRefresh = new JButton("Refresh");
        JButton btnTutup = new JButton("Tutup");

        JPanel panelTombol = new JPanel();
        panelTombol.add(btnRefresh);
        panelTombol.add(btnTutup);

        add(scrollPane, BorderLayout.CENTER);
        add(panelTombol, BorderLayout.SOUTH);

        btnRefresh.addActionListener(e -> muatTransaksi());
        btnTutup.addActionListener(e -> dispose());

        muatTransaksi();
    }

    private void muatTransaksi() {
        listModel.clear();
        List<String> daftar = transaksiDAO.getAllTransaksi();
        if (daftar == null || daftar.isEmpty()) {
            listModel.addElement("Belum ada transaksi.");
            return;
        }
        for (String baris : daftar) {
            listModel.addElement(baris);
        }
    }
}