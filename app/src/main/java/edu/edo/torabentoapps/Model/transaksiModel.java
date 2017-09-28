package edu.edo.torabentoapps.Model;

/**
 * Created by anggy on 20/08/2017.
 */

public class transaksiModel {

    private String transaksiID,namamakanan,tanggal_transaksi,statuspemesanan,alamat;
    private int quantity,thumbnails;
    private long totalharga;

    public String getTransaksiID() {
        return transaksiID;
    }

    public void setTransaksiID(String transaksiID) {
        this.transaksiID = transaksiID;
    }

    public String getNamamakanan() {
        return namamakanan;
    }

    public void setNamamakanan(String namamakanan) {
        this.namamakanan = namamakanan;
    }

    public String getTanggal_transaksi() {
        return tanggal_transaksi;
    }

    public void setTanggal_transaksi(String tanggal_transaksi) {
        this.tanggal_transaksi = tanggal_transaksi;
    }

//    public String getStatuspemesanan() {
//        return statuspemesanan;
//    }
//
//    public void setStatuspemesanan(String statuspemesanan) {
//        this.statuspemesanan = statuspemesanan;
//    }

    public String getAlamat() {
        return alamat;
    }

    public void setAlamat(String alamat) {
        this.alamat = alamat;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public long getTotalharga() {
        return totalharga;
    }

    public void setTotalharga(long totalharga) {
        this.totalharga = totalharga;
    }

    public int getThumbnails() {
        return thumbnails;
    }

    public void setThumbnails(int thumbnails) {
        this.thumbnails = thumbnails;
    }
}
