package org.example;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;


public class AdminDashboard extends JFrame {
    private JTable table;
    private DefaultTableModel tableModel;
    private List<Siswa> siswaList;
    private JTextField txtSearch;
    private final String NISN_PREFIX = "1122334";

    public AdminDashboard() {
        setTitle("Admin Dashboard - Data Nilai Siswa");
        setSize(900, 600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // Header
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(StyleTheme.PRIMARY_COLOR);
        headerPanel.setBorder(BorderFactory.createEmptyBorder(15, 20, 15, 20));

        JLabel lblTitle = new JLabel("Manajemen Data Siswa");
        lblTitle.setFont(StyleTheme.FONT_TITLE);
        lblTitle.setForeground(StyleTheme.BLACK);

        JButton btnLogout = new JButton("Logout");
        btnLogout.setBackground(StyleTheme.DANGER_COLOR);
        btnLogout.setForeground(StyleTheme.WHITE);
        btnLogout.setFont(StyleTheme.FONT_BOLD);
        btnLogout.setOpaque(true);
        btnLogout.setContentAreaFilled(true);
        btnLogout.setBorderPainted(false);
        btnLogout.addActionListener(e -> {
            new LoginFrame().setVisible(true);
            this.dispose();
        });

        headerPanel.add(lblTitle, BorderLayout.WEST);
        headerPanel.add(btnLogout, BorderLayout.EAST);

        // Toolbar (Search & Sort)
        JPanel toolbar = new JPanel(new FlowLayout(FlowLayout.LEFT));
        toolbar.setBackground(StyleTheme.BG_COLOR);

        txtSearch = new JTextField(20);
        JButton btnSearch = new JButton("Cari ID");
        JButton btnSortAsc = new JButton("Sort Abjad (A-Z)");
        JButton btnSortDesc = new JButton("Sort Abjad (Z-A)");

        toolbar.add(new JLabel("Cari NISN: "));
        toolbar.add(txtSearch);
        toolbar.add(btnSearch);
        toolbar.add(new JSeparator(JSeparator.VERTICAL));
        toolbar.add(btnSortAsc);
        toolbar.add(btnSortDesc);

        // Table
        String[] columns = {"NISN", "Nama", "Sem 1", "Sem 2", "Sem 3", "Sem 4", "Sem 5", "Sem 6", "Status"};
        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) { return false; }
        };
        table = new JTable(tableModel);
        table.setRowHeight(25);
        table.getTableHeader().setBackground(StyleTheme.ACCENT_COLOR);
        table.getTableHeader().setForeground(StyleTheme.BLACK);
        table.getTableHeader().setFont(StyleTheme.FONT_BOLD);

        JScrollPane scrollPane = new JScrollPane(table);

        // Action Buttons
        JPanel btnPanel = new JPanel();
        btnPanel.setBackground(StyleTheme.BG_COLOR);
        JButton btnCreate = new JButton("Create Data");
        JButton btnEdit = new JButton("Edit Data");
        JButton btnDelete = new JButton("Delete Data");

        btnCreate.setBackground(StyleTheme.SUCCESS_COLOR);
        btnCreate.setForeground(StyleTheme.WHITE);
        btnCreate.setFont(StyleTheme.FONT_BOLD);
        btnCreate.setOpaque(true);
        btnCreate.setContentAreaFilled(true);
        btnCreate.setBorderPainted(false);

        btnEdit.setBackground(StyleTheme.EDIT_COLOR);
        btnEdit.setForeground(StyleTheme.WHITE);
        btnEdit.setFont(StyleTheme.FONT_BOLD);
        btnEdit.setOpaque(true);
        btnEdit.setContentAreaFilled(true);
        btnEdit.setBorderPainted(false);

        btnDelete.setBackground(StyleTheme.DELETE_COLOR);
        btnDelete.setForeground(StyleTheme.WHITE);
        btnDelete.setFont(StyleTheme.FONT_BOLD);
        btnDelete.setOpaque(true);
        btnDelete.setContentAreaFilled(true);
        btnDelete.setBorderPainted(false);

        btnPanel.add(btnCreate);
        btnPanel.add(btnEdit);
        btnPanel.add(btnDelete);

        add(headerPanel, BorderLayout.NORTH);

        JPanel centerPanel = new JPanel(new BorderLayout());
        centerPanel.add(toolbar, BorderLayout.NORTH);
        centerPanel.add(scrollPane, BorderLayout.CENTER);

        add(centerPanel, BorderLayout.CENTER);
        add(btnPanel, BorderLayout.SOUTH);

        // Load Data
        loadData();

        // Listeners
        btnCreate.addActionListener(e -> showInputForm(null));

        btnEdit.addActionListener(e -> {
            int row = table.getSelectedRow();
            if (row != -1) {
                String nisn = (String) table.getValueAt(row, 0);
                Siswa s = siswaList.stream().filter(x -> x.getNisn().equals(nisn)).findFirst().orElse(null);
                if(s != null) showInputForm(s);
            } else {
                JOptionPane.showMessageDialog(this, "Pilih baris yang akan diedit.");
            }
        });

        btnDelete.addActionListener(e -> {
            int row = table.getSelectedRow();
            if (row != -1) {
                if (JOptionPane.showConfirmDialog(this, "Yakin hapus data?", "Konfirmasi", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
                    String nisn = (String) table.getValueAt(row, 0);
                    siswaList.removeIf(s -> s.getNisn().equals(nisn));
                    saveAndRefresh();
                }
            } else {
                JOptionPane.showMessageDialog(this, "Pilih baris yang akan dihapus.");
            }
        });

        btnSearch.addActionListener(e -> filterData(txtSearch.getText()));
        btnSortAsc.addActionListener(e -> sortData(true));
        btnSortDesc.addActionListener(e -> sortData(false));
    }

    private void loadData() {
        siswaList = CsvHandler.readData();
        refreshTable(siswaList);
    }

    private void refreshTable(List<Siswa> data) {
        tableModel.setRowCount(0);
        for (Siswa s : data) {
            Object[] row = {
                    s.getNisn(), s.getNama(),
                    s.getNilaiSemester()[0], s.getNilaiSemester()[1], s.getNilaiSemester()[2],
                    s.getNilaiSemester()[3], s.getNilaiSemester()[4], s.getNilaiSemester()[5],
                    s.getStatus()
            };
            tableModel.addRow(row);
        }
    }

    private void saveAndRefresh() {
        CsvHandler.writeData(siswaList);
        refreshTable(siswaList);
    }

    private void filterData(String keyword) {
        if(keyword.isEmpty()) {
            refreshTable(siswaList);
            return;
        }
        List<Siswa> filtered = siswaList.stream()
                .filter(s -> s.getNisn().contains(keyword))
                .collect(Collectors.toList());
        refreshTable(filtered);
    }

    private void sortData(boolean ascending) {
        Comparator<Siswa> comp = Comparator.comparing(Siswa::getNama);
        if(!ascending) comp = comp.reversed();
        Collections.sort(siswaList, comp);
        refreshTable(siswaList);
    }

    private void showInputForm(Siswa existing) {
        JDialog d = new JDialog(this, existing == null ? "Tambah Siswa" : "Edit Siswa", true);
        d.setSize(400, 500);
        d.setLocationRelativeTo(this);
        d.setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Form Elements
        JTextField tfSuffix = new JTextField(3); // Hanya 3 digit
        JTextField tfNama = new JTextField(20);
        JTextField[] tfNilai = new JTextField[6];
        for(int i=0; i<6; i++) tfNilai[i] = new JTextField(5);

        // NISN Logic
        gbc.gridx=0; gbc.gridy=0; d.add(new JLabel("NISN ("+NISN_PREFIX+"...):"), gbc);
        JPanel nisnPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        nisnPanel.add(new JLabel(NISN_PREFIX));
        nisnPanel.add(tfSuffix);
        gbc.gridx=1; d.add(nisnPanel, gbc);

        gbc.gridx=0; gbc.gridy=1; d.add(new JLabel("Nama Lengkap:"), gbc);
        gbc.gridx=1; d.add(tfNama, gbc);

        for(int i=0; i<6; i++) {
            gbc.gridx=0; gbc.gridy=2+i; d.add(new JLabel("Nilai Sem "+(i+1)+":"), gbc);
            gbc.gridx=1; d.add(tfNilai[i], gbc);
        }

        JButton btnSave = new JButton("Simpan");
        gbc.gridx=1; gbc.gridy=8; d.add(btnSave, gbc);

        // Pre-fill jika Edit
        if(existing != null) {
            String suffix = existing.getNisn().substring(7); // Ambil 3 digit terakhir
            tfSuffix.setText(suffix);
            tfNama.setText(existing.getNama());
            for(int i=0; i<6; i++) tfNilai[i].setText(String.valueOf(existing.getNilaiSemester()[i]));
            // Tidak boleh ubah NISN saat edit (opsional, tapi biasanya Primary Key jangan diubah)
            // tfSuffix.setEditable(false);
        }

        btnSave.addActionListener(ev -> {
            try {
                // Validasi input 3 digit
                if(tfSuffix.getText().length() != 3 || !tfSuffix.getText().matches("\\d+")) {
                    JOptionPane.showMessageDialog(d, "Suffix NISN harus 3 digit angka!");
                    return;
                }

                String fullNisn = NISN_PREFIX + tfSuffix.getText();
                String nama = tfNama.getText();
                double[] nilai = new double[6];

                if(nama.isEmpty()) throw new Exception("Nama kosong");

                for(int i=0; i<6; i++) {
                    nilai[i] = Double.parseDouble(tfNilai[i].getText());
                }

                // Cek Duplicate NISN jika Create New
                if(existing == null && siswaList.stream().anyMatch(s -> s.getNisn().equals(fullNisn))) {
                    JOptionPane.showMessageDialog(d, "NISN sudah ada!");
                    return;
                }

                Siswa newSiswa = new Siswa(fullNisn, nama, nilai);

                if(existing != null) {
                    // Remove old, add new (update)
                    siswaList.remove(existing);
                }
                siswaList.add(newSiswa);
                saveAndRefresh();
                d.dispose();

            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(d, "Nilai harus berupa angka!");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(d, "Lengkapi semua data: " + ex.getMessage());
            }
        });

        d.setVisible(true);
    }
}
