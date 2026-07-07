package view;

import javax.swing.*;
import java.awt.*;

/**
 * Dashboard utama setelah login.
 * Berisi tombol navigasi ke form-form lain.
 */
public class MainDashboard extends JFrame {

    public MainDashboard() {
        setTitle("Dashboard - Rental Mobil App");
        setSize(400, 300);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(5, 1, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(30, 50, 30, 50));

        JButton btnDataMobil = new JButton("Data Mobil");
        JButton btnSewaMobil = new JButton("Sewa Mobil");
        JButton btnLaporan = new JButton("Laporan Transaksi");
        JButton btnLogout = new JButton("Logout");

        panel.add(new JLabel("Selamat datang di Rental Mobil App", SwingConstants.CENTER));
        panel.add(btnDataMobil);
        panel.add(btnSewaMobil);
        panel.add(btnLaporan);
        panel.add(btnLogout);

        add(panel);

        btnDataMobil.addActionListener(e -> {
            new DataMobilForm().setVisible(true);
        });

        btnSewaMobil.addActionListener(e -> {
            new FormSewaMobil().setVisible(true);
        });

        btnLaporan.addActionListener(e -> {
            new LaporanTransaksiForm().setVisible(true);
        });

        btnLogout.addActionListener(e -> {
            this.dispose();
            new LoginForm().setVisible(true);
        });
    }
}