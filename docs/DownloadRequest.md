# Dokumentasi Test Case: Fitur Permohonan Unduh Dokumen

## Deskripsi Fitur

Fitur ini menguji alur permohonan unduh dokumen pada aplikasi **eLibrary** (`https://elibrary.sanskuy.space/`), mulai dari pengajuan permohonan oleh pengunjung, persetujuan oleh pemilik dokumen, hingga penerimaan notifikasi email oleh pemohon.

---

## Teknologi yang Digunakan

| Komponen | Detail |
|---|---|
| Bahasa | Java |
| Framework Test | Selenium WebDriver 4.43.0 + Cucumber BDD + JUnit 5 |
| Build Tool | Maven |
| Pattern | Page Object Model (POM) |
| Browser | Google Chrome |

---

## Struktur File

```
src/
├── main/java/pages/
│   ├── LandingPage.java              — Halaman utama eLibrary
│   ├── CatalogPage.java              — Halaman katalog dokumen
│   ├── DownloadRequestPopupPage.java — Popup form permohonan unduh
│   ├── GmailLoginPage.java           — Login Gmail (owner & pemohon)
│   ├── GmailInboxPage.java           — Inbox Gmail
│   └── GmailEmailDetailPage.java     — Detail email & tombol Approve
├── test/java/
│   ├── runner/TestRunner.java        — Konfigurasi runner Cucumber
│   ├── steps/DownloadRequestSteps.java — Step definitions
│   └── hooks/Hooks.java              — Setup & teardown per scenario
└── test/resources/features/
    └── DownloadRequest.feature       — Skenario Cucumber (Gherkin)
```

---

## Daftar Test Case

### EP (Equivalence Partitioning)

| ID | Tipe | Deskripsi | Data Input |
|---|---|---|---|
| TC-PU-01 | Positif | User berhasil mengajukan permohonan unduh dokumen dari halaman katalog | Nama: Ahsani Fadhli, Email: Ahsani.fadhli@gmail.com, Instansi: Universitas Haluoleo |
| TC-PU-02a | Negatif | Gagal kirim — email tidak valid | email: `emailtidakvalid` |
| TC-PU-02b | Negatif | Gagal kirim — nama kosong | nama: `""` |
| TC-PU-02c | Negatif | Gagal kirim — instansi kosong | instansi: `""` |
| TC-PU-02d | Negatif | Gagal kirim — keperluan kosong | keperluan: `""` |

### BVA (Boundary Value Analysis)

| ID | Tipe | Deskripsi | Data Input |
|---|---|---|---|
| TC-PU-06 | Positif | Permohonan dengan nilai field di batas bawah minimum valid | Nama: `A`, Email: `a@b.co`, Instansi: `X`, Keperluan: `Y` |
| TC-PU-07 | Negatif | Email tanpa TLD — tepat di bawah batas bawah valid | Email: `budi@` |
| TC-PU-08 | Negatif | Semua field dikosongkan (nilai minimum 0) | Semua field: `""` |

### Alur Gmail (End-to-End)

| ID | Tipe | Deskripsi | Akun yang Digunakan |
|---|---|---|---|
| TC-PU-09 | Positif | Pemilik dokumen menerima email permohonan dan menyetujuinya via Gmail | Owner: `apc008d6y0478@student.devacademy.id` / `Ahsani2006` |
| TC-PU-10 | Positif | Pemohon menerima email notifikasi persetujuan dari eLibrary | Pemohon: `Ahsani.fadhli@gmail.com` / `Ahsani2006` |

---

## Alur Lengkap (End-to-End Flow)

```
TC-PU-01: Pengunjung submit form permohonan unduh
    ↓  (email notifikasi dikirim ke owner)
TC-PU-09: Owner login Gmail → buka email → klik Approve
    ↓  (sistem kirim email persetujuan ke pemohon)
TC-PU-10: Pemohon login Gmail → buka email → verifikasi notifikasi diterima
```

---

## Cara Menjalankan Test

### Prasyarat

1. Java 17+ terinstall
2. Google Chrome versi terbaru
3. Maven terinstall (atau gunakan Maven bawaan IntelliJ)
4. Koneksi internet aktif

### Perintah (jalankan di terminal dari folder project)

#### Jalankan TC-PU-01 s.d TC-PU-10 (default)

```powershell
& "C:\Program Files\JetBrains\IntelliJ IDEA Community Edition 2024.3.4\plugins\maven\lib\maven3\bin\mvn.cmd" verify "-Dmaven.test.failure.ignore=true"
```

#### Jalankan satu test case tertentu

```powershell
# Contoh: hanya TC-PU-01
& "C:\...\mvn.cmd" verify "-Dmaven.test.failure.ignore=true" "-Dcucumber.filter.tags=@TC-PU-01"

# Contoh: hanya TC-PU-09 dan TC-PU-10
& "C:\...\mvn.cmd" verify "-Dmaven.test.failure.ignore=true" "-Dcucumber.filter.tags=@TC-PU-09 or @TC-PU-10"

# Hanya test positif
& "C:\...\mvn.cmd" verify "-Dmaven.test.failure.ignore=true" "-Dcucumber.filter.tags=@positive"

# Hanya test negatif
& "C:\...\mvn.cmd" verify "-Dmaven.test.failure.ignore=true" "-Dcucumber.filter.tags=@negative"
```

#### Lihat laporan HTML setelah test selesai

Buka file berikut di browser:

```
target/cucumber-html-reports/overview-features.html
```

---

## Catatan Penting

- **TC-PU-09 dan TC-PU-10** memerlukan koneksi Gmail aktif. Google terkadang menampilkan halaman `challenge/pwd` (verifikasi ulang password) — sudah ditangani otomatis.
- Akun `apc008d6y0478@student.devacademy.id` (owner) dan `Ahsani.fadhli@gmail.com` (pemohon) harus **tidak mengaktifkan 2FA** agar login otomatis bisa berjalan.
- Setiap test run TC-PU-01 membuat satu request baru di sistem. TC-PU-09 akan selalu mengambil approve link untuk `Ahsani.fadhli@gmail.com` dari thread email terbaru.
- Delay antar step: **1500ms** (dikonfigurasi di `Hooks.java` via `@AfterStep`).
