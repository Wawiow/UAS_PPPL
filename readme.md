Sistem Under Test (SUT)

Sistem yang diuji adalah E-Library BRIDA yang dapat diakses melalui:

https://elibrary.sanskuy.space

E-Library BRIDA merupakan aplikasi perpustakaan digital yang menyediakan layanan pencarian, pengelolaan, dan publikasi dokumen ilmiah. Sistem ini memiliki beberapa jenis pengguna, yaitu User, Contributor, dan Administrator.

Pada pengujian ini, fitur yang diuji meliputi:
Login pengguna
Pengajuan Contributor
Persetujuan Contributor oleh Admin
Penolakan Contributor oleh Admin
Upload Dokumen oleh Contributor

Metode pengujian yang digunakan adalah Black Box Testing dengan pendekatan Equivalence Partitioning (EP) dan Boundary Value Analysis (BVA) yang diimplementasikan menggunakan BDD (Behavior Driven Development) dengan framework Cucumber dan Selenium WebDriver.

TEST SUITE

Nama Test Suite : Pengajuan Menjadi Kontributor

Deskripsi: Test suite ini digunakan untuk memverifikasi proses pengajuan perubahan peran pengguna menjadi Contributor melalui form pengajuan contributor.

Nama Test Suite : Approve Contributor Request

Deskripsi : Test suite ini digunakan untuk memverifikasi proses persetujuan permintaan contributor oleh Administrator.

Nama Test Suite : Reject Contributor Request

Deskripsi : Test suite ini digunakan untuk memverifikasi proses penolakan permintaan contributor oleh Administrator.

