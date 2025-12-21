package org.example;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class CsvHandler {
    private static final String FILE_NAME = "database.csv";
    private static final String SEPARATOR = ",";

    public static List<Siswa> readData() {
        List<Siswa> listSiswa = new ArrayList<>();
        File file = new File(FILE_NAME);
        if (!file.exists()) return listSiswa;

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] data = line.split(SEPARATOR);
                if (data.length >= 8) { // NISN, Nama, 6 Nilai
                    String nisn = data[0];
                    String nama = data[1];
                    double[] nilai = new double[6];
                    for (int i = 0; i < 6; i++) {
                        nilai[i] = Double.parseDouble(data[i + 2]);
                    }
                    listSiswa.add(new Siswa(nisn, nama, nilai));
                }
            }
        } catch (IOException | NumberFormatException e) {
            e.printStackTrace();
        }
        return listSiswa;
    }

    public static void writeData(List<Siswa> listSiswa) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(FILE_NAME))) {
            for (Siswa s : listSiswa) {
                StringBuilder sb = new StringBuilder();
                sb.append(s.getNisn()).append(SEPARATOR);
                sb.append(s.getNama()).append(SEPARATOR);
                for (double n : s.getNilaiSemester()) {
                    sb.append(n).append(SEPARATOR);
                }
                sb.append(s.getStatus());
                bw.write(sb.toString());
                bw.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}