@PengajuanKontributor
Feature: Pengajuan Kontributor

  @positive
  Scenario: User berhasil mengajukan kontributor
    Given user membuka halaman login
    When user login dengan email "newuser@gmail.com" dan password "user1234"
    And user klik menu jadi kontributor
    And user klik tombol lanjut isi form
    And user input alasan "Saya ingin menjadi kontributor"
    And user submit pengajuan
    Then pengajuan berhasil dikirim

  @positive
  Scenario: Admin menerima permintaan kontributor user
    Given admin membuka halaman login
    When admin login dengan email "admin@brida.com" dan password "admin123"
    And admin klik menu contributor request
    And admin klik tombol approve
    And admin input notes
    And admin klik tombol approve
    Then user berhasil jadi kontributor
