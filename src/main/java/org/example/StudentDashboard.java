package org.example;

import javax.swing.*;
import java.awt.*;

public class StudentDashboard extends JFrame {

    public StudentDashboard(Siswa siswa) {
        setTitle("Pengumuman Kelulusan");
        setSize(600, 500);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // Background Putih Bersih
        getContentPane().setBackground(Color.WHITE);

        // Konten Tengah
        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
        contentPanel.setBackground(Color.WHITE);
        contentPanel.setBorder(BorderFactory.createEmptyBorder(40, 50, 40, 50));

        JLabel lblHeader = new JLabel("HASIL AKHIR STUDI");
        lblHeader.setFont(StyleTheme.FONT_TITLE);
        lblHeader.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel lblNisn = new JLabel("NISN: " + siswa.getNisn());
        lblNisn.setFont(StyleTheme.FONT_SUBTITLE);
        lblNisn.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel lblNama = new JLabel("Nama: " + siswa.getNama());
        lblNama.setFont(StyleTheme.FONT_SUBTITLE);
        lblNama.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Panel Nilai (Grid Layout kecil)
        JPanel nilaiPanel = new JPanel(new GridLayout(2, 3, 10, 10));
        nilaiPanel.setBackground(new Color(240, 240, 240));
        nilaiPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        nilaiPanel.setMaximumSize(new Dimension(500, 80));

        double[] n = siswa.getNilaiSemester();
        for(int i=0; i<6; i++) {
            JLabel lbl = new JLabel("Sem " + (i+1) + ": " + n[i]);
            lbl.setHorizontalAlignment(SwingConstants.CENTER);
            nilaiPanel.add(lbl);
        }

        // Status Besar
        JLabel lblStatusTitle = new JLabel("DINYATAKAN:");
        lblStatusTitle.setFont(StyleTheme.FONT_REGULAR);
        lblStatusTitle.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel lblStatus = new JLabel(siswa.getStatus());
        lblStatus.setFont(new Font("Segoe UI", Font.BOLD, 48));
        lblStatus.setAlignmentX(Component.CENTER_ALIGNMENT);

        if (siswa.getStatus().equals("LOLOS")) {
            lblStatus.setForeground(StyleTheme.SUCCESS_COLOR);
        } else {
            lblStatus.setForeground(StyleTheme.DANGER_COLOR);
        }

        // Susun Layout
        contentPanel.add(lblHeader);
        contentPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        contentPanel.add(new JSeparator());
        contentPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        contentPanel.add(lblNama);
        contentPanel.add(lblNisn);
        contentPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        contentPanel.add(nilaiPanel);
        contentPanel.add(Box.createRigidArea(new Dimension(0, 30)));
        contentPanel.add(lblStatusTitle);
        contentPanel.add(lblStatus);

        // Tombol Logout
        JButton btnLogout = new JButton("Logout");
        btnLogout.setBackground(StyleTheme.DANGER_COLOR );
        btnLogout.setForeground(Color.WHITE);
        btnLogout.setFont(StyleTheme.FONT_BOLD);
        btnLogout.setOpaque(true);
        btnLogout.setContentAreaFilled(true);
        btnLogout.setBorderPainted(false);
        btnLogout.addActionListener(e -> {
            new LoginFrame().setVisible(true);
            this.dispose();
        });

        JPanel bottomPanel = new JPanel();
        bottomPanel.setBackground(Color.WHITE);
        bottomPanel.add(btnLogout);

        add(contentPanel, BorderLayout.CENTER);
        add(bottomPanel, BorderLayout.SOUTH);
    }
}