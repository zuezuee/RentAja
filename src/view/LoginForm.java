package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

/**
 * Form Login sederhana.
 * Untuk sekarang login masih hardcode (username: admin, password: admin123)
 * karena belum ada tabel user/admin di DB. Kalau nanti Orang 1/2 bikin
 * tabel user, tinggal ganti bagian validasi di actionLogin().
 */
public class LoginForm extends JFrame {

    private JTextField txtUsername;
    private JPasswordField txtPassword;
    private JButton btnLogin;
    private JButton btnBatal;

    public LoginForm() {
        setTitle("Login - Rental Mobil App");
        setSize(350, 200);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);

        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        gbc.gridx = 0; gbc.gridy = 0;
        panel.add(new JLabel("Username:"), gbc);

        txtUsername = new JTextField(15);
        gbc.gridx = 1; gbc.gridy = 0;
        panel.add(txtUsername, gbc);

        gbc.gridx = 0; gbc.gridy = 1;
        panel.add(new JLabel("Password:"), gbc);

        txtPassword = new JPasswordField(15);
        gbc.gridx = 1; gbc.gridy = 1;
        panel.add(txtPassword, gbc);

        btnLogin = new JButton("Login");
        btnBatal = new JButton("Batal");

        JPanel panelTombol = new JPanel();
        panelTombol.add(btnLogin);
        panelTombol.add(btnBatal);

        gbc.gridx = 0; gbc.gridy = 2; gbc.gridwidth = 2;
        panel.add(panelTombol, gbc);

        add(panel);

        btnLogin.addActionListener(this::actionLogin);
        btnBatal.addActionListener((ActionEvent e) -> System.exit(0));
    }

    private void actionLogin(ActionEvent e) {
        String user = txtUsername.getText().trim();
        String pass = new String(txtPassword.getPassword());

        // TODO: ganti dengan validasi ke DB kalau sudah ada tabel user
        if (user.equals("admin") && pass.equals("admin123")) {
            JOptionPane.showMessageDialog(this, "Login berhasil!");
            new MainDashboard().setVisible(true);
            this.dispose();
        } else {
            JOptionPane.showMessageDialog(this,
                    "Username atau password salah!",
                    "Login Gagal",
                    JOptionPane.ERROR_MESSAGE);
        }
    }
}