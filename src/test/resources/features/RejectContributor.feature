@reject
Feature: Reject Contributor

  @rejectPositive
  Scenario: Admin menolak permintaan contributor
    Given pengguna sudah login menggunakan email "admin@brida.com" dan password "admin123"
    When admin membuka halaman contributor request
    And admin memilih reject
    And admin mengisi alasan reject "Persyaratan belum lengkap"
    And admin mengkonfirmasi reject
    Then request contributor berhasil ditolak

  @rejectNegative
  Scenario: Admin reject tanpa mengisi alasan
    Given pengguna sudah login menggunakan email "admin@brida.com" dan password "admin123"
    When admin membuka halaman contributor request
    And admin memilih reject
    And admin mengisi alasan reject ""
    Then tombol reject tidak aktif