@ui @uploadDocument
Feature: Upload Dokumen oleh Contributor

  Background:
    Given pengguna sudah login menggunakan email "mohammadavirzaradyatanza@mail.ugm.ac.id" dan password "avirza123"
    And pengguna berada di halaman beranda

  @smoke @positive
  Scenario: Berhasil mengunggah dokumen dengan data valid
    When pengguna menekan tombol "Dashboard Kontributor"
    Then pengguna diarahkan ke halaman dashboard contributor

    When pengguna menekan tombol "Unggah Dokumen"
    Then form upload dokumen ditampilkan

    When pengguna mengisi judul dokumen "Pengujian Sistem Informasi"
    And pengguna memilih bahasa "id"
    And pengguna memilih jenis dokumen
    And pengguna mengisi penulis "Revaldo Kuncoro"
    And pengguna mengisi tahun terbit "2025"
    And pengguna memilih subjek
    And pengguna menekan tombol "Lanjut"

    And pengguna menekan tombol "Lanjut" pada ringkasan metadata

    And pengguna mengunggah file PDF "Selenium Cucumber.pdf"
    And pengguna menekan tombol "Lanjut"

    And pengguna menyetujui seluruh pernyataan
    And pengguna menekan tombol "Upload Dokumen"

    Then upload dokumen berhasil
    And status dokumen adalah "Pending Review"

  @negative
  Scenario: Validasi judul kosong
    When pengguna menekan tombol "Dashboard Kontributor"
    And pengguna menekan tombol "Unggah Dokumen"
    And pengguna mengosongkan judul dokumen
    And pengguna menekan tombol "Lanjut"
    Then muncul pesan validasi "Judul dokumen harus diisi"

  @negative
  Scenario: Validasi file belum dipilih
    When pengguna menekan tombol "Dashboard Kontributor"
    And pengguna menekan tombol "Unggah Dokumen"
    And pengguna mengisi metadata dengan data valid
    And pengguna menekan tombol "Lanjut"
    And pengguna menekan tombol "Lanjut" pada ringkasan metadata
    And pengguna menekan tombol "Lanjut"
    Then muncul pesan validasi "File dokumen harus dipilih"

  @negative
  Scenario: Validasi lisensi belum disetujui
    When pengguna menekan tombol "Dashboard Kontributor"
    And pengguna menekan tombol "Unggah Dokumen"
    And pengguna mengisi seluruh data upload dengan benar
    And pengguna tidak mencentang pernyataan persetujuan
    And pengguna menekan tombol "Upload Dokumen"
    Then muncul pesan validasi "Anda harus menyetujui pernyataan ini"