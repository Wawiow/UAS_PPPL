@PermohonanUnduh
Feature: Permohonan Unduh Dokumen
  Sebagai pengunjung yang belum login
  Saya ingin mengajukan permohonan unduh dokumen dari halaman katalog
  Agar saya dapat mengakses dokumen yang dibutuhkan setelah disetujui pemilik

  Background:
    Given user membuka halaman landing page eLibrary

  # -------------------------------------------------------
  # TC-PU-01 | POSITIVE — EP Valid Class: semua field valid
  # -------------------------------------------------------
  @positive @EP @TC-PU-01
  Scenario: User berhasil mengajukan permohonan unduh dokumen dari halaman katalog
    When user klik menu Katalog di navbar
    Then user berada di halaman katalog
    When user klik tombol Download pada dokumen
    Then popup permohonan unduh ditampilkan
    When user mengisi nama "Ahsani Fadhli"
    And user mengisi email "Ahsani.fadhli@gmail.com"
    And user mengisi instansi "Universitas Haluoleo"
    And user mengisi keperluan "Penelitian untuk skripsi saya"
    And user klik tombol Kirim Permohonan
    Then permohonan berhasil dikirim dengan pesan sukses

  # -------------------------------------------------------
  # TC-PU-02 s.d TC-PU-05 | NEGATIVE — EP Invalid Class
  # Partisi tidak valid: satu field kosong atau email salah format
  # -------------------------------------------------------
  @negative @EP @TC-PU-02
  Scenario Outline: User gagal kirim permohonan karena data tidak valid (Equivalence Partitioning)
    When user klik menu Katalog di navbar
    And user klik tombol Download pada dokumen
    Then popup permohonan unduh ditampilkan
    When user mengisi nama "<nama>"
    And user mengisi email "<email>"
    And user mengisi instansi "<instansi>"
    And user mengisi keperluan "<keperluan>"
    And user klik tombol Kirim Permohonan
    Then muncul pesan validasi error

    Examples:
      | nama          | email              | instansi              | keperluan          |
      | Budi Santoso  | emailtidakvalid    | Universitas Haluoleo  | Penelitian skripsi |
      |               | budi@gmail.com     | Universitas Haluoleo  | Penelitian skripsi |
      | Budi Santoso  | budi@gmail.com     |                       | Penelitian skripsi |
      | Budi Santoso  | budi@gmail.com     | Universitas Haluoleo  |                    |

  # -------------------------------------------------------
  # TC-PU-06 | POSITIVE — BVA Batas Bawah Valid
  # Email minimum valid: format "x@x.xx" (6 karakter)
  # Nama minimum: 1 karakter
  # -------------------------------------------------------
  @positive @BVA @TC-PU-06
  Scenario: Permohonan dengan nilai field di batas bawah minimum yang masih valid (BVA)
    When user klik menu Katalog di navbar
    And user klik tombol Download pada dokumen
    Then popup permohonan unduh ditampilkan
    When user mengisi nama "A"
    And user mengisi email "a@b.co"
    And user mengisi instansi "X"
    And user mengisi keperluan "Y"
    And user klik tombol Kirim Permohonan
    Then sistem menampilkan respons permohonan

  # -------------------------------------------------------
  # TC-PU-07 | NEGATIVE — BVA Tepat di Bawah Batas Bawah Valid
  # Email tanpa domain TLD → format tidak valid
  # -------------------------------------------------------
  @negative @BVA @TC-PU-07
  Scenario: Permohonan gagal karena email tidak memiliki TLD (BVA batas bawah-1)
    When user klik menu Katalog di navbar
    And user klik tombol Download pada dokumen
    Then popup permohonan unduh ditampilkan
    When user mengisi nama "Budi"
    And user mengisi email "budi@"
    And user mengisi instansi "Kampus"
    And user mengisi keperluan "Keperluan penelitian"
    And user klik tombol Kirim Permohonan
    Then muncul pesan validasi error

  # -------------------------------------------------------
  # TC-PU-08 | NEGATIVE — BVA Field Kosong Semua
  # Semua field kosong = batas paling bawah (0 input)
  # -------------------------------------------------------
  @negative @BVA @TC-PU-08
  Scenario: Permohonan gagal ketika semua field dikosongkan (BVA nilai minimum 0)
    When user klik menu Katalog di navbar
    And user klik tombol Download pada dokumen
    Then popup permohonan unduh ditampilkan
    When user mengisi nama ""
    And user mengisi email ""
    And user mengisi instansi ""
    And user mengisi keperluan ""
    And user klik tombol Kirim Permohonan
    Then muncul pesan validasi error

  # -------------------------------------------------------
  # TC-PU-09 | POSITIVE — Alur Gmail: pemilik dokumen approve
  # CATATAN: Ganti kredensial sebelum menjalankan test ini.
  # Pastikan akun tidak aktif 2FA, atau gunakan App Password.
  # -------------------------------------------------------
  @positive @gmail @TC-PU-09
  Scenario: Pemilik dokumen menerima email permohonan dan menyetujuinya via Gmail
    Given pemilik dokumen membuka halaman Gmail
    When pemilik dokumen login dengan email "apc008d6y0478@student.devacademy.id" dan password "Ahsani2006"
    And pemilik dokumen membuka email permohonan unduh terbaru
    Then detail permohonan ditampilkan dalam email
    When pemilik dokumen klik tombol Approve dalam email
    Then halaman konfirmasi persetujuan ditampilkan

  # -------------------------------------------------------
  # TC-PU-10 | POSITIVE — Alur Gmail: pemohon terima notifikasi
  # -------------------------------------------------------
  @positive @gmail @TC-PU-10
  Scenario: Pemohon menerima email notifikasi persetujuan dari eLibrary
    Given pemohon membuka halaman Gmail
    When pemohon login dengan email "Ahsani.fadhli@gmail.com" dan password "Ahsani2006"
    And pemohon membuka email notifikasi persetujuan dari eLibrary
    Then email konfirmasi unduh ditampilkan
