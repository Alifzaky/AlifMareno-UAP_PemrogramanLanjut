
---

# Student Graduation Management System

*Student Graduation Management System* adalah aplikasi desktop berbasis Java Swing yang dirancang untuk mengelola data nilai siswa dan menentukan status kelulusan secara otomatis. Aplikasi ini menggunakan sistem penyimpanan berbasis file CSV sehingga tidak memerlukan konfigurasi database yang kompleks.

## 1. Deskripsi Aplikasi

Aplikasi ini merupakan solusi digital untuk lembaga pendidikan dalam mengelola nilai siswa dari Semester 1 hingga Semester 6. Sistem secara otomatis menghitung rata-rata nilai dan menentukan apakah seorang siswa *LOLOS* atau *TIDAK LOLOS* berdasarkan ambang batas nilai yang telah ditentukan (Rata-rata  40). Aplikasi mendukung dua level akses: *Admin* untuk manajemen data dan *Siswa* untuk pengecekan hasil akhir.

## 2. Penjelasan Fitur Per Halaman

### A. Halaman Login (LoginFrame)

* *Role Selection:* Memilih akses sebagai Admin atau Siswa.
* *Authentication:* * *Admin:* Menggunakan kredensial statis (username/password).
* *Siswa:* Menggunakan NISN sebagai ID dan Password.


* *Validation:* Memastikan input tidak kosong sebelum memproses login.

### B. Dashboard Admin (AdminDashboard)

* *Manajemen Data (CRUD):* Tambah (Create), Lihat (Read), Ubah (Update), dan Hapus (Delete) data siswa.
* *Pencarian:* Mencari data siswa secara spesifik berdasarkan NISN.
* *Pengurutan (Sorting):* Mengurutkan daftar nama siswa secara alfabetis (A-Z atau Z-A).
* *Otomatisasi Status:* Sistem otomatis menghitung kelulusan setiap kali data ditambah atau diubah.
* *Logout:* Kembali ke halaman login dengan aman.

### C. Dashboard Siswa (StudentDashboard)

* *Personalisasi Data:* Menampilkan Nama dan NISN siswa yang login.
* *Transkrip Nilai:* Menampilkan rincian nilai dari Semester 1 sampai Semester 6 dalam bentuk grid yang rapi.
* *Indikator Kelulusan:* Menampilkan pengumuman besar status "LOLOS" (Hijau) atau "TIDAK LOLOS" (Merah).

### D. Pengolah Data (CsvHandler)

* *Persistence:* Menyimpan seluruh data ke dalam file database.csv.
* *Auto-Sync:* Data otomatis diperbarui setiap kali Admin melakukan perubahan.

## 3. Target Penggunaan

1. *Staf Administrasi Sekolah:* Untuk menginput dan mengelola database nilai siswa secara efisien tanpa perlu keahlian SQL.
2. *Guru/Wali Kelas:* Untuk meninjau performa akademik siswa dan status kelulusan.
3. *Siswa:* Untuk melihat hasil studi dan status kelulusan secara mandiri dan transparan.

## 4. Cara Penggunaan Aplikasi

### Persiapan:

1. Pastikan Anda telah menginstal *Java Development Kit (JDK)* versi 8 atau yang terbaru.
2. Siapkan IDE (seperti IntelliJ IDEA, Eclipse, atau NetBeans) atau jalankan melalui Terminal.

### Langkah-langkah:

1. *Menjalankan Aplikasi:* Jalankan file Main.java sebagai entry point utama.
2. *Akses Admin:*
* Pilih Role *Admin*.
* Masukkan ID: admin dan Password: admin123.
* Gunakan tombol *Create Data* untuk menambah siswa baru (NISN menggunakan prefix 1122334 + 3 digit unik).


3. *Akses Siswa:*
* Pilih Role *Siswa*.
* Masukkan *NISN* Anda pada kolom ID dan Password (Password default siswa adalah NISN mereka sendiri).
* Lihat hasil pengumuman pada layar.


4. *Penyimpanan Data:*
* Data akan tersimpan secara otomatis di folder proyek dalam file bernama database.csv. Jangan menghapus file ini agar data tidak hilang.



---

### Teknologi yang Digunakan:

* *Bahasa:* Java
* *Framework GUI:* Java Swing & AWT
* *Penyimpanan:* CSV (Comma Separated Values)
* *Paradigma:* Object-Oriented Programming (OOP)

---

Dibuat untuk memenuhi standar tugas Manajemen Data Siswa berbasis Desktop.