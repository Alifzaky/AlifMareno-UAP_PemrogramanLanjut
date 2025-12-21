package org.example;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.List;

public class LoginFrame extends JFrame {
    private JTextField tfId;
    private JPasswordField tfPass;
    private JRadioButton rbAdmin, rbSiswa;
    private ButtonGroup bgRole;

    public LoginFrame() {
        setTitle("Aplikasi Kelulusan - Login");
        setSize(400, 350);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // Panel Utama
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBackground(StyleTheme.BG_COLOR);
        mainPanel.setBorder(BorderFactory.createEmptyBorder(30, 40, 30, 40));

        // Judul
        JLabel lblTitle = new JLabel("Selamat Datang");
        lblTitle.setFont(StyleTheme.FONT_TITLE);
        lblTitle.setForeground(StyleTheme.PRIMARY_COLOR);
        lblTitle.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Form Input
        tfId = new JTextField();
        tfPass = new JPasswordField();
        styleField(tfId);
        styleField(tfPass);

        // Radio Button Role
        rbAdmin = new JRadioButton("Admin");
        rbSiswa = new JRadioButton("Siswa");
        rbAdmin.setBackground(StyleTheme.BG_COLOR);
        rbSiswa.setBackground(StyleTheme.BG_COLOR);
        rbAdmin.setFont(StyleTheme.FONT_REGULAR);
        rbSiswa.setFont(StyleTheme.FONT_REGULAR);

        bgRole = new ButtonGroup();
        bgRole.add(rbAdmin);
        bgRole.add(rbSiswa);
        rbSiswa.setSelected(true);

        JPanel rolePanel = new JPanel();
        rolePanel.setBackground(StyleTheme.BG_COLOR);
        rolePanel.add(rbAdmin);
        rolePanel.add(rbSiswa);

        // Tombol Login
        JButton btnLogin = new JButton("LOGIN");
        btnLogin.setFont(StyleTheme.FONT_BOLD);
        btnLogin.setBackground(StyleTheme.PRIMARY_COLOR);
        btnLogin.setForeground(StyleTheme.WHITE);
        btnLogin.setOpaque(true);
        btnLogin.setContentAreaFilled(true);
        btnLogin.setBorderPainted(false);
        btnLogin.setFocusPainted(false);
        btnLogin.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnLogin.setMaximumSize(new Dimension(320, 40));

        // Add Components
        mainPanel.add(lblTitle);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        mainPanel.add(createLabel("ID Pendidikan / NISN:"));
        mainPanel.add(tfId);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        mainPanel.add(createLabel("Password:"));
        mainPanel.add(tfPass);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        mainPanel.add(rolePanel);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        mainPanel.add(btnLogin);

        add(mainPanel);

        // Logic Login
        Runnable loginAction = () -> handleLogin();

        btnLogin.addActionListener(e -> loginAction.run());

        // Enter key listener
        KeyAdapter enterListener = new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) loginAction.run();
            }
        };
        tfId.addKeyListener(enterListener);
        tfPass.addKeyListener(enterListener);
        btnLogin.addKeyListener(enterListener);
    }

    private JLabel createLabel(String text) {
        JLabel lbl = new JLabel(text);
        lbl.setFont(StyleTheme.FONT_REGULAR);
        lbl.setAlignmentX(Component.CENTER_ALIGNMENT);
        return lbl;
    }

    private void styleField(JComponent field) {
        field.setFont(StyleTheme.FONT_REGULAR);
        field.setMaximumSize(new Dimension(320, 30));
    }

    private void handleLogin() {
        String id = tfId.getText();
        String pass = new String(tfPass.getPassword());

        if (id.isEmpty() || pass.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Harap lengkapi semua data!", "Peringatan", JOptionPane.WARNING_MESSAGE);
            return;
        }

        if (rbAdmin.isSelected()) {
            if (id.equals("admin") && pass.equals("admin123")) {
                showSuccess("Login Admin Berhasil");
                new AdminDashboard().setVisible(true);
                this.dispose();
            } else {
                showFail();
            }
        } else {
            if (!id.equals(pass)) {
                JOptionPane.showMessageDialog(this, "Untuk siswa, Password harus sama dengan NISN.", "Login Gagal", JOptionPane.ERROR_MESSAGE);
                return;
            }

            List<Siswa> data = CsvHandler.readData();
            Siswa found = data.stream().filter(s -> s.getNisn().equals(id)).findFirst().orElse(null);

            if (found != null) {
                showSuccess("Login Siswa Berhasil");
                new StudentDashboard(found).setVisible(true);
                this.dispose();
            } else {
                JOptionPane.showMessageDialog(this, "NISN tidak ditemukan di database.", "Login Gagal", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void showSuccess(String msg) {
        UIManager.put("OptionPane.background", StyleTheme.BG_COLOR);
        UIManager.put("Panel.background", StyleTheme.BG_COLOR);
        JOptionPane.showMessageDialog(this, msg, "Sukses", JOptionPane.INFORMATION_MESSAGE);
    }

    private void showFail() {
        JOptionPane.showMessageDialog(this, "Akses Admin Ditolak", "Gagal", JOptionPane.ERROR_MESSAGE);
    }
}
