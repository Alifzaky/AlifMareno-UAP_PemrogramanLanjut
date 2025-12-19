package org.example;

public class Siswa {
    private String nisn;
    private String nama;
    private double[] nilaiSemester; // Array index 0-5 untuk sem 1-6
    private String status;

    public Siswa(String nisn, String nama, double[] nilaiSemester) {
        this.nisn = nisn;
        this.nama = nama;
        this.nilaiSemester = nilaiSemester;
        calculateStatus();
    }

    public void calculateStatus() {
        double total = 0;
        for (double n : nilaiSemester) {
            total += n;
        }
        double avg = total / nilaiSemester.length;
        // Jika rata-rata < 40 Tidak Lolos (Sesuai request: kurang dari 40 tidak lolos)
        this.status = (avg >= 40) ? "LOLOS" : "TIDAK LOLOS";
    }

    // Getters and Setters
    public String getNisn() { return nisn; }
    public String getNama() { return nama; }
    public double[] getNilaiSemester() { return nilaiSemester; }
    public String getStatus() { return status; }
    public double getRataRata() {
        double total = 0;
        for(double n : nilaiSemester) total += n;
        return total / nilaiSemester.length;
    }
}