package edu.edo.torabentoapps.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by ASUS on 16/08/2017.
 */

public class itemModel {
    @SerializedName("nama_makanan")
    @Expose
    private String nmMakanan;
    @SerializedName("harga")
    @Expose
    private String harga;
    @SerializedName("status")
    @Expose
    private String ketersediaan;
    @SerializedName("stok")
    @Expose
    private int stok;

    public int getStok() {
        return stok;
    }

    public void setStok(int stok) {
        this.stok = stok;
    }

    @SerializedName("gambar")
    @Expose
    private String gambar;

    public String getNmMakanan() {
        return nmMakanan;
    }

    public void setNmMakanan(String nmMakanan) {
        this.nmMakanan = nmMakanan;
    }

    public String getHarga() {
        return harga;
    }

    public void setHarga(String harga) {
        this.harga = harga;
    }

    public String getKetersediaan() {
        return ketersediaan;
    }

    public void setKetersediaan(String ketersediaan) {
        this.ketersediaan = ketersediaan;
    }

    public String getGambar() {
        return gambar;
    }

    public void setGambar(String gambar) {
        this.gambar = gambar;
    }
}
