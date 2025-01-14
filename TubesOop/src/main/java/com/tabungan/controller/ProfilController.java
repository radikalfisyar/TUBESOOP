/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tabungan.controller;

/**
 *
 * @author M.R.FIRDAUS
 */
import com.tabungan.model.Profil;
import com.tabungan.utils.ConnectionManager;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ProfilController {

    // Metode untuk menambah profil pengguna
    public boolean tambahProfil(Profil profil) {
        String query = "INSERT INTO profil (username, nama, email, gender) VALUES (?, ?, ?, ?)";
        try (Connection con = ConnectionManager.getConnection(); 
             PreparedStatement pstmt = con.prepareStatement(query)) {

            pstmt.setString(1, profil.getUsername());
            pstmt.setString(2, profil.getNama());
            pstmt.setString(3, profil.getEmail());
            pstmt.setString(4, profil.getGender());

            int rowsAffected = pstmt.executeUpdate(); // Eksekusi pernyataan insert
            return rowsAffected > 0; // Mengembalikan true jika berhasil ditambahkan

        } catch (SQLException ex) {
            ex.printStackTrace();
            return false; // Mengembalikan false jika gagal
        }
    }

    // Metode untuk mengambil profil pengguna berdasarkan username
      public Profil ambilProfil(String username) {
        // Query SQL untuk mengambil data dari tabel profil
        String query = "SELECT * FROM profil WHERE username = ?";

        // Gunakan try-with-resources agar connection, statement, dan resultSet otomatis ditutup
        try (Connection connection = ConnectionManager.getConnection();
             PreparedStatement ps = connection.prepareStatement(query)) {

            // Set parameter query (username)
            ps.setString(1, username);

            // Eksekusi query dan simpan hasilnya dalam ResultSet
            ResultSet rs = ps.executeQuery();

            // Jika data ditemukan, buat dan kembalikan objek Profil
            if (rs.next()) {
                String nama = rs.getString("nama");
                String email = rs.getString("email");
                String gender = rs.getString("gender");
                return new Profil(username, nama, email, gender);
            }

        } catch (SQLException e) {
            // Tampilkan pesan error jika terjadi masalah dengan database
            e.printStackTrace();
        }

        // Kembalikan null jika data tidak ditemukan
        return null;
    }

    // Metode untuk mengupdate informasi profil pengguna
    public boolean updateProfil(Profil profil) {
        String query = "UPDATE profil SET nama = ?, email = ?, gender = ? WHERE username = ?";
        try (Connection con = ConnectionManager.getConnection(); 
             PreparedStatement pstmt = con.prepareStatement(query)) {

            pstmt.setString(1, profil.getNama());
            pstmt.setString(2, profil.getEmail());
            pstmt.setString(3, profil.getGender());
            pstmt.setString(4, profil.getUsername());

            int rowsAffected = pstmt.executeUpdate(); // Eksekusi pernyataan update
            return rowsAffected > 0; // Mengembalikan true jika berhasil diupdate

        } catch (SQLException ex) {
            ex.printStackTrace();
            return false; // Mengembalikan false jika gagal
        }
    }

    // Metode untuk menghapus profil pengguna berdasarkan username
    public boolean hapusProfil(String username) {
        String query = "DELETE FROM profil WHERE username = ?";
        try (Connection con = ConnectionManager.getConnection(); 
             PreparedStatement pstmt = con.prepareStatement(query)) {

            pstmt.setString(1, username);
            int rowsAffected = pstmt.executeUpdate(); // Eksekusi pernyataan delete
            return rowsAffected > 0; // Mengembalikan true jika berhasil dihapus

        } catch (SQLException ex) {
            ex.printStackTrace();
            return false; // Mengembalikan false jika gagal
        }
    }
}