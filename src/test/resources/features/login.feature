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