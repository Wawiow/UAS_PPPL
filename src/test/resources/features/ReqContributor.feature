@request
Feature: Request Contributor
  Scenario: BVA - alasan 9 karakter
    Given pengguna sudah login menggunakan email "newuser3@gmail.com" dan password "user2345"
    When pengguna menekan tombol jadi kontributor
    And pengguna membuka form pengajuan kontributor
    And pengguna mengisi alasan kontributor "123456789"
    Then tombol ajukan permintaan tidak aktif

  Scenario: EP - Pengajuan contributor berhasil
    Given pengguna sudah login menggunakan email "newuser@gmail.com" dan password "user1234"
    When pengguna menekan tombol jadi kontributor
    And pengguna membuka form pengajuan kontributor
    And pengguna mengisi alasan kontributor "Saya ingin menjadi kontributor karena ingin berbagi karya ilmiah"
    And pengguna mengajukan permintaan kontributor
    Then permintaan kontributor berhasil dikirim

