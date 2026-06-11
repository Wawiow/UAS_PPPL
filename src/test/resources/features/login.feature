Feature: Login pengguna

  Background:
	Given pengguna sudah memiliki akun dengan email "mohammadavirzaradyatanza@mail.ugm.ac.id" dan password "avirza123"

  Scenario: Login berhasil dengan kredensial valid
	Given pengguna berada di halaman login
	When pengguna memasukkan email "mohammadavirzaradyatanza@mail.ugm.ac.id" ke dalam field email
	And pengguna memasukkan password "avirza123" ke dalam field password
	And pengguna menekan tombol "Login"
	Then pengguna diarahkan ke halaman dashboard


  # login gagal
  Scenario: Login gagal dengan kredensial tidak valid
	Given pengguna berada di halaman login
	When pengguna memasukkan email "mohammadavirzaradyatanza@mail.ugm.ac.id" ke dalam field email
	And pengguna memasukkan password "avirza 1234" ke dalam field password
	And pengguna menekan tombol "Login"
	Then pengguna tetap berada di halaman login
	And muncul pesan kesalahan "401 - Invalid credentials"


