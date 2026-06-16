Feature: Approve Contributor

  @approve
  Scenario: Admin menyetujui permintaan contributor
    Given pengguna sudah login menggunakan email "admin@brida.com" dan password "admin123"
    When admin membuka halaman contributor request
    And admin memilih approve
    And admin mengisi notes "Approved by automation"
    And admin mengkonfirmasi approve
    Then request contributor berhasil disetujui