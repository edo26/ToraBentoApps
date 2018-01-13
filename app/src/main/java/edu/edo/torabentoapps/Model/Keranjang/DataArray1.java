package edu.edo.torabentoapps.Model.Keranjang;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Edo on 1/1/2018.
 */

public class DataArray1 {
    @SerializedName("namaMakanan")
    @Expose
    private String namaMakanan;
    @SerializedName("qty")
    @Expose
    private int qty;
    @SerializedName("harga")
    @Expose
    private int harga;
    @SerializedName("linkgambar")
    @Expose
    private String linkgambar;

    public DataArray1(String namaMakanan, int qty, int harga, String linkgambar) {
        this.namaMakanan = namaMakanan;
        this.qty = qty;
        this.harga = harga;
        this.linkgambar = linkgambar;
    }

    public String getNamaMakanan() {
        return namaMakanan;
    }

    public void setNamaMakanan(String namaMakanan) {
        this.namaMakanan = namaMakanan;
    }

    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }

    public int getHarga() {
        return harga;
    }

    public void setHarga(int harga) {
        this.harga = harga;
    }

    public String getLinkgambar() {
        return linkgambar;
    }

    public void setLinkgambar(String linkgambar) {
        this.linkgambar = linkgambar;
    }
}
