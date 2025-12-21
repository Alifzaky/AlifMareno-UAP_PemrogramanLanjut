# 🎓 Aplikasi Sistem Informasi Kelulusan Siswa

Aplikasi desktop berbasis **Java Swing** yang dirancang untuk mengelola data nilai siswa dan mengumumkan status kelulusan. Aplikasi ini menggunakan sistem penyimpanan data berbasis file CSV sederhana (`database.csv`), sehingga tidak memerlukan konfigurasi database eksternal (seperti MySQL).

Aplikasi ini memiliki dua hak akses (Role): **Admin** dan **Siswa**.

## ✨ Fitur Utama

### 👨‍💻 Administrator (Admin)
* **Login Keamanan:** Autentikasi khusus admin.
* **CRUD Data Siswa:**
    * **Create:** Menambah data siswa baru (NISN, Nama, Nilai Semester 1-6).
    * **Read:** Melihat daftar seluruh siswa dalam tabel.
    * **Update:** Mengedit data siswa dan nilai.
    * **Delete:** Menghapus data siswa.
* **Pencarian & Penyortiran:**
    * Mencari siswa berdasarkan NISN.
    * Mengurutkan data siswa berdasarkan Nama (A-Z atau Z-A).
* **Validasi Input:** Validasi format NISN (Prefix `1122334` + 3 digit suffix) dan rentang nilai (0-100).

### 👨‍🎓 Siswa
* **Login Personal:** Masuk menggunakan NISN.
* **Dashboard Hasil Studi:**
    * Melihat biodata (Nama & NISN).
    * Melihat rincian nilai dari Semester 1 hingga Semester 6.
* **Status Kelulusan:** Penentuan status **LOLOS** atau **TIDAK LOLOS** secara otomatis dengan visualisasi warna (Hijau/Merah).

## 🛠️ Spesifikasi Teknis

* **Bahasa Pemrograman:** Java (JDK 8 ke atas).
* **GUI Library:** Java Swing & AWT.
* **Database:** CSV (Comma Separated Values).
* **Arsitektur:** Mengadopsi pola sederhana mirip MVC (Model-View-Controller).
    * **Model:** `Siswa.java`
    * **View:** `LoginFrame`, `AdminDashboard`, `StudentDashboard`
    * **Utility:** `CsvHandler`, `StyleTheme`

## 📂 Struktur Proyek

Berikut adalah penjelasan fungsi dari setiap file dalam paket `org.example`:

| Nama File | Deskripsi |
| :--- | :--- |
| **`Main.java`** | *Entry point* aplikasi. Mengatur `LookAndFeel` dan membuka `LoginFrame`. |
| **`StyleTheme.java`** | Menyimpan konstanta desain (Warna, Font) agar tampilan aplikasi konsisten. |
| **`Siswa.java`** | Kelas Model (POJO) yang merepresentasikan objek Siswa, termasuk logika perhitungan rata-rata dan status kelulusan. |
| **`CsvHandler.java`** | Menangani operasi *File I/O*. Membaca dan menulis data ke `database.csv`. |
| **`LoginFrame.java`** | Antarmuka untuk login. Memisahkan logika login antara Admin dan Siswa. |
| **`AdminDashboard.java`** | Antarmuka utama untuk Admin. Berisi tabel data dan tombol manajemen (CRUD). |
| **`StudentDashboard.java`** | Antarmuka untuk Siswa. Menampilkan "Raport" digital dan status akhir. |

## 🚀 Cara Menjalankan

1.  Pastikan **Java Development Kit (JDK)** sudah terinstal.
2.  Compile dan jalankan file `Main.java`.
3.  Aplikasi akan membuat file `database.csv` secara otomatis di root folder proyek saat Admin pertama kali menyimpan data.

## 🔑 Akun & Kredensial

Untuk masuk ke dalam aplikasi, gunakan kredensial berikut:

### 1. Login Admin
* **ID Pendidikan / NISN:** `admin`
* **Password:** `admin123`

### 2. Login Siswa
Siswa login menggunakan NISN yang telah didaftarkan oleh Admin.
* **ID Pendidikan / NISN:** `[Nomor NISN Siswa]`
* **Password:** `[Nomor NISN Siswa]` (Password sama dengan NISN)

> **Catatan:** Admin harus membuat data siswa terlebih dahulu agar siswa bisa login.

## 📝 Logika Bisnis & Validasi

### Format NISN
Aplikasi ini menggunakan format NISN tetap (Hardcoded) pada `AdminDashboard`:
* **Prefix:** `1122334`
* **Suffix:** 3 digit angka inputan user.
* **Contoh:** Jika input `001`, maka NISN menjadi `1122334001`.

### Penentuan Kelulusan
Logika kelulusan terdapat pada class `Siswa.java`:
* Status dihitung dari rata-rata nilai semester 1 s.d 6.
* Rumus: `(Jumlah Nilai Semester) / 6`
* **Syarat Lolos:** Rata-rata `>= 40.0`.

## 📸 Pratinjau Tampilan (Deskripsi)

1.  **Login:** Desain minimalis dengan pilihan Radio Button untuk peran (Admin/Siswa).
2.  **Admin Dashboard:** Tabel data berwarna aksen biru, dengan fitur pencarian di bagian atas dan tombol aksi (Create/Edit/Delete) di bagian bawah.
3.  **Student Dashboard:** Tampilan bersih berwarna putih. Menampilkan status "LOLOS" dengan font besar berwarna hijau, atau "TIDAK LOLOS" berwarna merah.

---
*Dibuat untuk memenuhi tugas Pemrograman Berorientasi Objek.*