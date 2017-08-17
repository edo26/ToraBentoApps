package edu.edo.torabentoapps.Model;

/**
 * Created by ASUS on 16/08/2017.
 */

public class itemModel {
    private String nmMakanan;
    private String harga;
    private String ketersediaan;
    private int thumbnail;

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

    public int getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(int thumbnail) {
        this.thumbnail = thumbnail;
    }
}
