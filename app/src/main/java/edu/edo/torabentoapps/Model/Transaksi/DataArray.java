
package edu.edo.torabentoapps.Model.Transaksi;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DataArray {

    @SerializedName("id_transaksi")
    @Expose
    private String idTransaksi;
    @SerializedName("id_reseller")
    @Expose
    private String idReseller;
    @SerializedName("idpembeli")
    @Expose
    private String idpembeli;
    @SerializedName("nama_pembeli")
    @Expose
    private String namaPembeli;
    @SerializedName("alamat")
    @Expose
    private String alamat;
    @SerializedName("totalharga")
    @Expose
    private String totalharga;
    @SerializedName("status_transaksi")
    @Expose
    private String statusTransaksi;
    @SerializedName("tanggal_transaksi")
    @Expose
    private String tanggalTransaksi;
    @SerializedName("resipembayaran")
    @Expose
    private String resipembayaran;

    public String getIdTransaksi() {
        return idTransaksi;
    }

    public void setIdTransaksi(String idTransaksi) {
        this.idTransaksi = idTransaksi;
    }

    public String getIdReseller() {
        return idReseller;
    }

    public void setIdReseller(String idReseller) {
        this.idReseller = idReseller;
    }

    public String getIdpembeli() {
        return idpembeli;
    }

    public void setIdpembeli(String idpembeli) {
        this.idpembeli = idpembeli;
    }

    public String getNamaPembeli() {
        return namaPembeli;
    }

    public void setNamaPembeli(String namaPembeli) {
        this.namaPembeli = namaPembeli;
    }

    public String getAlamat() {
        return alamat;
    }

    public void setAlamat(String alamat) {
        this.alamat = alamat;
    }

    public String getTotalharga() {
        return totalharga;
    }

    public void setTotalharga(String totalharga) {
        this.totalharga = totalharga;
    }

    public String getStatusTransaksi() {
        return statusTransaksi;
    }

    public void setStatusTransaksi(String statusTransaksi) {
        this.statusTransaksi = statusTransaksi;
    }

    public String getTanggalTransaksi() {
        return tanggalTransaksi;
    }

    public void setTanggalTransaksi(String tanggalTransaksi) {
        this.tanggalTransaksi = tanggalTransaksi;
    }

    public String getResipembayaran() {
        return resipembayaran;
    }

    public void setResipembayaran(String resipembayaran) {
        this.resipembayaran = resipembayaran;
    }

}
