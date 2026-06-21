# ZonaCerdas - Backend Application

ZonaCerdas adalah platform edukasi interaktif berbasis web (Game Market & Learning Hub) yang dirancang untuk membantu pengalaman belajar siswa. Proyek ini merupakan repositori backend yang dibangun menggunakan Spring Boot 3 dengan arsitektur Modular Monolith yang scalable, aman, dan siap pakai.

## Tech Stack & Libraries

Backend dikembangkan menggunakan teknologi standar industri:
* Java 21 & Spring Boot 3.x
* Spring Security & JSON Web Token (JWT)
* Spring Data JPA & Hibernate
* PostgreSQL
* Lombok
* Jackson

## Architectural Pattern: Package by Domain

Proyek ini menerapkan konsep Modular Monolith dengan pendekatan Package by Domain. Setiap fitur utama diisolasi ke dalam paketnya sendiri untuk memastikan skalabilitas dan kemudahan pemeliharaan kode.

```text
com.demisnack.Eduplay.Application
├── auth          (Manajemen registrasi dan proses login)
├── catalog       (Manajemen Game/Konten & Transaksi Pembelian)
├── contributor   (Manajemen fitur khusus kreator konten)
├── global        (Exception Handling, JWT Filter, & Response Wrapper global)
├── library       (Koleksi konten/game yang dimiliki user setelah purchase)
├── roles         (Manajemen hak akses role base)
├── security      (Konfigurasi Security & Autentikasi Utama)
└── user          (Manajemen profil dan data pengguna)

```

## Core Security Features

* Global CORS Configured at Security Level: Mengizinkan integrasi frontend dengan lancar tanpa terblokir kebijakan CORS melalui penanganan preflight request (OPTIONS).
* Stateless Authentication: Menggunakan JWT Bearer Token untuk menjaga performa dan keamanan sesi server.
* Resource Constraints: Dioptimalkan dengan batas memori JVM (-Xmx256m) agar aplikasi tetap stabil dan mencegah Out of Memory saat berjalan di infrastruktur cloud dengan resource terbatas.
* Environment Variable Protection: Menggunakan sistem fallback configuration untuk melindungi kredensial database dan JWT secret dari kebocoran (Credential Leak) di repositori publik.

## API Endpoints Spesifikasi

Semua respon API dibungkus menggunakan format standar `GlobalResponse<T>`.

### Authentication & Users

| Method | Endpoint | Akses | Deskripsi |
| --- | --- | --- | --- |
| `POST` | `/api/auth/register/user` | Public | Registrasi akun Siswa/User umum |
| `POST` | `/api/auth/register/contributor` | Public | Registrasi akun Kreator Konten |
| `POST` | `/api/auth/login` | Public | Login untuk mendapatkan JWT Token |

### Content Catalog

| Method | Endpoint | Akses | Deskripsi |
| --- | --- | --- | --- |
| `GET` | `/api/catalog` | Public | Menarik semua daftar konten/game aktif |
| `GET` | `/api/catalog/{id}` | Public | Detail spesifik dari satu konten game |
| `PUT` | `/api/contents/{id}` | Contributor | Mengubah data konten (Title, Price, Thumbnail, dll) |
| `POST` | `/api/catalog/{id}/purchase` | Authenticated | Melakukan transaksi pembelian konten game |

### User Library

| Method | Endpoint | Akses | Deskripsi |
| --- | --- | --- | --- |
| `GET` | `/api/library` | Authenticated | Mengambil koleksi game yang sudah dibeli oleh user |
| `GET` | `/api/library/{id}/play` | Authenticated | Mengambil file URL game untuk dimainkan |
| `GET` | `/api/library/{id}/download` | Authenticated | Mengambil file URL download game |

## Menjalankan Aplikasi

Aplikasi ini mendukung konsep "Plug and Play" untuk development lokal, sekaligus aman untuk production cloud.

### Prasyarat

* Java Development Kit (JDK) 21 atau versi yang lebih baru
* PostgreSQL Server aktif

### 1. Lingkungan Lokal (Development)

Sistem sudah dikonfigurasi menggunakan nilai cadangan (fallback values) sehingga Anda tidak perlu mengatur Environment Variables secara manual.

1. Clone Repositori:

```bash
git clone [https://github.com/Firmanann/ZonaCerdas-Application.git](https://github.com/Firmanann/ZonaCerdas-Application.git)
cd ZonaCerdas-Application

```

2. Siapkan Database:
   Buat database di PostgreSQL lokal Anda dengan nama `zonacerdas`. Aplikasi akan otomatis menggunakan kredensial default (`username=postgres`, `password=manman`).
3. Build dan Jalankan:

```bash
./mvnw spring-boot:run

```

Aplikasi akan aktif dan dapat diakses di `http://localhost:8080`.

### 2. Lingkungan Cloud (Production)

Untuk deployment ke server produksi (Railway, AWS), atur Environment Variables berikut pada panel kontrol server Anda. Nilai ini akan otomatis menimpa konfigurasi lokal:

* `DB_URL`
* `DB_USERNAME`
* `DB_PASSWORD`
* `JWT_SECRET` 
