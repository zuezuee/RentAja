package view;

import dao.CustomerDAO;
import dao.MobilDAO;
import dao.TransaksiDAO;
import model.Customer;
import model.Mobil;

import javax.swing.*;
import java.awt.*;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.List;

/**
 * Form untuk transaksi sewa mobil baru.
 * Memanggil TransaksiDAO.sewaMobil(idTransaksi, idMobil, idCustomer, tglSewa, lamaSewa)
 *
 * CATATAN:
 * - Format tanggal input: yyyy-MM-dd (contoh: 2026-07-07)
 * - idTransaksi digenerate otomatis pakai timestamp, silakan sesuaikan
 *   kalau Orang 2 punya format ID transaksi sendiri (misal "TRX001").
 * - Combo box mobil & customer memakai custom renderer supaya tidak
 *   bergantung pada toString() di class Mobil/Customer.
 */
public class FormSewaMobil extends JFrame {

    private JComboBox<Mobil> comboMobil;
    private JComboBox<Customer> comboCustomer;
    private JTextField txtTanggalSewa;
    private JSpinner spinnerLamaSewa;
    private JButton btnSewa;
    private JButton btnBatal;

    private MobilDAO mobilDAO;
    private CustomerDAO customerDAO;
    private TransaksiDAO transaksiDAO;

    public FormSewaMobil() {
        mobilDAO = new MobilDAO();
        customerDAO = new CustomerDAO();
        transaksiDAO = new TransaksiDAO();

        setTitle("Form Sewa Mobil");
        setSize(420, 300);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.WEST;

        gbc.gridx = 0; gbc.gridy = 0;
        panel.add(new JLabel("Pilih Mobil:"), gbc);

        comboMobil = new JComboBox<>();
        loadMobilTersedia();
        comboMobil.setRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value,
                                                          int index, boolean isSelected, boolean cellHasFocus) {
                super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                if (value instanceof Mobil) {
                    Mobil m = (Mobil) value;
                    setText(m.getIdMobil() + " - " + m.getMerk() + " " + m.getModel());
                }
                return this;
            }
        });
        gbc.gridx = 1; gbc.gridy = 0;
        panel.add(comboMobil, gbc);

        gbc.gridx = 0; gbc.gridy = 1;
        panel.add(new JLabel("Pilih Customer:"), gbc);

        comboCustomer = new JComboBox<>();
        loadCustomer();
        comboCustomer.setRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value,
                                                          int index, boolean isSelected, boolean cellHasFocus) {
                super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                if (value instanceof Customer) {
                    Customer c = (Customer) value;
                    setText(c.getIdCustomer() + " - " + c.getNama());
                }
                return this;
            }
        });
        gbc.gridx = 1; gbc.gridy = 1;
        panel.add(comboCustomer, gbc);

        gbc.gridx = 0; gbc.gridy = 2;
        panel.add(new JLabel("Tanggal Sewa (yyyy-MM-dd):"), gbc);

        txtTanggalSewa = new JTextField(new SimpleDateFormat("yyyy-MM-dd").format(new java.util.Date()), 12);
        gbc.gridx = 1; gbc.gridy = 2;
        panel.add(txtTanggalSewa, gbc);

        gbc.gridx = 0; gbc.gridy = 3;
        panel.add(new JLabel("Lama Sewa (hari):"), gbc);

        spinnerLamaSewa = new JSpinner(new SpinnerNumberModel(1, 1, 365, 1));
        gbc.gridx = 1; gbc.gridy = 3;
        panel.add(spinnerLamaSewa, gbc);

        btnSewa = new JButton("Proses Sewa");
        btnBatal = new JButton("Batal");

        JPanel panelTombol = new JPanel();
        panelTombol.add(btnSewa);
        panelTombol.add(btnBatal);

        gbc.gridx = 0; gbc.gridy = 4; gbc.gridwidth = 2;
        panel.add(panelTombol, gbc);

        add(panel);

        btnSewa.addActionListener(e -> prosesSewa());
        btnBatal.addActionListener(e -> dispose());
    }

    private void loadMobilTersedia() {
        comboMobil.removeAllItems();
        List<Mobil> daftar = mobilDAO.getMobilTersedia();
        if (daftar != null) {
            for (Mobil m : daftar) {
                comboMobil.addItem(m);
            }
        }
    }

    private void loadCustomer() {
        comboCustomer.removeAllItems();
        List<Customer> daftar = customerDAO.getAllCustomer();
        if (daftar != null) {
            for (Customer c : daftar) {
                comboCustomer.addItem(c);
            }
        }
    }

    private void prosesSewa() {
        Mobil mobilTerpilih = (Mobil) comboMobil.getSelectedItem();
        Customer customerTerpilih = (Customer) comboCustomer.getSelectedItem();

        if (mobilTerpilih == null || customerTerpilih == null) {
            JOptionPane.showMessageDialog(this,
                    "Mobil atau customer belum tersedia / belum dipilih.",
                    "Gagal", JOptionPane.WARNING_MESSAGE);
            return;
        }

        try {
            Date tglSewa = Date.valueOf(txtTanggalSewa.getText().trim()); // format yyyy-MM-dd
            int lamaSewa = (Integer) spinnerLamaSewa.getValue();
            String idTransaksi = "TRX" + System.currentTimeMillis();

            boolean berhasil = transaksiDAO.sewaMobil(
                    idTransaksi,
                    mobilTerpilih.getIdMobil(),
                    customerTerpilih.getIdCustomer(),
                    tglSewa,
                    lamaSewa
            );

            if (berhasil) {
                JOptionPane.showMessageDialog(this, "Transaksi sewa berhasil dibuat!\nID Transaksi: " + idTransaksi);
                loadMobilTersedia(); // refresh, karena mobil yg baru disewa harusnya hilang dari daftar tersedia
                dispose();
            } else {
                JOptionPane.showMessageDialog(this,
                        "Gagal memproses sewa. Cek stok mobil atau data yang dimasukkan.",
                        "Gagal", JOptionPane.ERROR_MESSAGE);
            }

        } catch (IllegalArgumentException ex) {
            JOptionPane.showMessageDialog(this,
                    "Format tanggal salah. Gunakan format yyyy-MM-dd (contoh: 2026-07-07).",
                    "Input Salah", JOptionPane.ERROR_MESSAGE);
        }
    }
}