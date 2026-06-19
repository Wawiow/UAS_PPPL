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
Permohonan Unduh Dokumen

Metode pengujian yang digunakan adalah Black Box Testing dengan pendekatan Equivalence Partitioning (EP) dan Boundary Value Analysis (BVA) yang diimplementasikan menggunakan BDD (Behavior Driven Development) dengan framework Cucumber dan Selenium WebDriver.

TEST SUITE

Nama Test Suite : Pengajuan Menjadi Kontributor

Deskripsi: Test suite ini digunakan untuk memverifikasi proses pengajuan perubahan peran pengguna menjadi Contributor melalui form pengajuan contributor.

| ID     | Scenario                       | Expected Result                      |
| ------ | ------------------------------ |--------------------------------------|
| REQ-01 | Pengajuan contributor berhasil | Request berhasil dikirim             |
| REQ-02 | Alasan kosong                  | Tombol Ajukan Permintaan tidak aktif |
| REQ-03 | Alasan 9 karakter              | Tombol Ajukan Permintaan tidak aktif |


Nama Test Suite : Approve Contributor Request

Deskripsi : Test suite ini digunakan untuk memverifikasi proses persetujuan permintaan contributor oleh Administrator.

| ID         | Scenario                                                   | Expected Result                                 |
| ---------- | ---------------------------------------------------------- | ----------------------------------------------- |
| APPROVE-01 | Admin menyetujui permintaan contributor dengan notes valid | Status request berubah menjadi Approved         |


Nama Test Suite : Reject Contributor Request

Deskripsi : Test suite ini digunakan untuk memverifikasi proses penolakan permintaan contributor oleh Administrator.

| ID        | Scenario                                                 | Expected Result                         |
| --------- | -------------------------------------------------------- | --------------------------------------- |
| REJECT-01 | Admin menolak permintaan contributor dengan alasan valid | Status request berubah menjadi Rejected |
| REJECT-02 | Admin menolak permintaan contributor tanpa alasan        | Tombol reject tidak aktif               |


Nama Test Suite : Permohonan Unduh Dokumen

Deskripsi : Test suite ini digunakan untuk memverifikasi proses pengajuan permohonan unduh dokumen oleh pengunjung dari halaman katalog, termasuk persetujuan oleh pemilik dokumen dan notifikasi email ke pemohon.

| ID       | Scenario                                                          | Expected Result                                  |
| -------- | ------------------------------------------------------------------ | ------------------------------------------------- |
| TC-PU-01 | Permohonan unduh dengan data lengkap dan valid                     | Permohonan berhasil dikirim dengan pesan sukses    |
| TC-PU-02 | Permohonan gagal: email/nama/instansi/keperluan tidak valid         | Muncul pesan validasi error                        |
| TC-PU-06 | Permohonan dengan nilai field di batas bawah minimum yang valid     | Sistem menampilkan respons permohonan (diterima)   |
| TC-PU-07 | Permohonan gagal: email tanpa TLD (tepat di bawah batas valid)      | Muncul pesan validasi error                        |
| TC-PU-08 | Permohonan gagal: semua field dikosongkan                          | Muncul pesan validasi error                        |
| TC-PU-09 | Pemilik dokumen menyetujui permohonan melalui email Gmail           | Halaman konfirmasi persetujuan ditampilkan         |
| TC-PU-10 | Pemohon menerima email notifikasi persetujuan dari eLibrary         | Email konfirmasi unduh ditampilkan                 |


pembagian tugas:
Sedayu: membuat test untuk request Contributor
Ahsani: membuat test untuk Request Unduh Dokumen
Farrel
Avirza
