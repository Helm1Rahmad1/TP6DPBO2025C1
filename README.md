# TP6DPBO2025C1

## Janji 
Saya Muhammad Helmi Rahmadi dengan NIM 2311574 mengerjakan Tugas Praktikum 6 dalam mata kuliah Desain dan Pemrograman Berorientasi Objek untuk keberkahanNya maka saya tidak melakukan kecurangan seperti yang telah dispesifikasikan. Aamiin.

---

## Deskripsi Program

Program ini adalah implementasi game **Flappy Bird** sederhana menggunakan **Java Swing**, yang dibangun dengan menerapkan prinsip **Object-Oriented Programming (OOP)** secara konsisten.

Game ini menampilkan antarmuka grafis yang interaktif dan responsif, serta dilengkapi dengan berbagai fitur seperti:

- **Sistem skor dinamis**
- **Deteksi tabrakan (collision detection)**
- **Mekanisme restart setelah Game Over**

Pemain dapat mengendalikan burung dengan menekan tombol **Spasi** untuk melompat dan berusaha menghindari rintangan berupa pipa yang bergerak dari kanan ke kiri. Setiap kali pemain berhasil melewati sepasang pipa, skor akan bertambah.

Permainan akan berakhir apabila burung menabrak pipa atau jatuh ke bawah layar. Dengan desain sederhana namun menantang, game ini memberikan pengalaman bermain yang menyenangkan dan cocok sebagai latihan pemrograman berorientasi objek dengan antarmuka grafis.


---

## Struktur Kelas

| Kelas         | Deskripsi                                                                 |
|---------------|--------------------------------------------------------------------------|
| `MainMenu`    | Ini tampilan awal pas game dijalankan. Ada judul, animasi burung lucu, dan tombol "Start Game" sama "Exit". Dari sini user bisa mulai main. |
| `App`         | Versi alternatif buat langsung masuk ke game tanpa lewat menu. Buat testing atau langsung main. |
| `FlappyBird`  | Ini inti dari gamenya. Ngatur gameplay, gambar-gambar, logika tabrakan, dan kontrol si burung. |
| `Player`      | Karakter utama alias si burung yang dikendaliin pemain. Bisa lompat, dan kena gravitasi juga. |
| `Pipe`        | Rintangan yang harus dihindari si burung. Gerak dari kanan ke kiri terus makin lama makin cepet. |


---

## Alur Permainan

1. Program dimulai dengan menampilkan **Main Menu**.
2. Saat tombol "Start Game" ditekan, game dimulai.
3. Pemain menekan tombol **Space** untuk membuat burung melompat.
4. Pemain harus menghindari pipa yang terus bergerak.
5. Skor bertambah setiap kali melewati pipa.
6. Game berakhir jika burung menabrak pipa atau jatuh ke bawah.
7. Tekan tombol **R** untuk me-restart game setelah Game Over.

---

## Fitur

- **Main Menu** dengan tampilan dan animasi menarik
- **Deteksi tabrakan** antara burung dan pipa
- **Sistem skor**
- **Restart game** menggunakan tombol `R`
- **Visualisasi menarik** menggunakan gambar

---

## Kontrol Game

| Tombol | Fungsi                         |
|--------|--------------------------------|
| Space  | Burung melompat                |
| R      | Restart game setelah Game Over |

---

## Screenshot

### Main Menu
<img width="1728" alt="Screenshot 2025-04-10 at 21 32 42" src="https://github.com/user-attachments/assets/c256bf04-873c-4c1d-b079-d8e1a9bc5ef0" />

### Gameplay
<img width="1728" alt="Screenshot 2025-04-10 at 21 20 34" src="https://github.com/user-attachments/assets/13873438-e1f1-474f-b5c2-e9f7cf7b238c" />

### Game Over
<img width="1728" alt="Screenshot 2025-04-10 at 21 20 44" src="https://github.com/user-attachments/assets/df5fe7be-8d91-41ee-be56-86a4fe85f398" />


---





