package edu.edo.torabentoapps.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by ASUS on 16/08/2017.
 */

public class itemModel {
    @SerializedName("id_makanan")
    @Expose
    private String idMakanan;
    @SerializedName("id_reseller")
    @Expose
    private String idReseller;
    @SerializedName("nama_makanan")
    @Expose
    private String namaMakanan;
    @SerializedName("stok")
    @Expose
    private String stok;
    @SerializedName("harga")
    @Expose
    private String harga;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("gambar")
    @Expose
    private String gambar;

    public String getIdMakanan() {
        return idMakanan;
    }

    public void setIdMakanan(String idMakanan) {
        this.idMakanan = idMakanan;
    }

    public String getIdReseller() {
        return idReseller;
    }

    public void setIdReseller(String idReseller) {
        this.idReseller = idReseller;
    }

    public String getNmMakanan() {
        return namaMakanan;
    }

    public void setNamaMakanan(String namaMakanan) {
        this.namaMakanan = namaMakanan;
    }

    public String getStok() {
        return stok;
    }

    public void setStok(String stok) {
        this.stok = stok;
    }

    public String getHarga() {
        return harga;
    }

    public void setHarga(String harga) {
        this.harga = harga;
    }

    public String getKetersediaan() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getGambar() {
        return gambar;
    }

    public void setGambar(String gambar) {
        this.gambar = gambar;
    }

}
