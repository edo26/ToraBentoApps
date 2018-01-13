
package edu.edo.torabentoapps.Model.Keranjang;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ModelKeranjang {

    @SerializedName("id_keranjang")
    @Expose
    private String idKeranjang;
    @SerializedName("id_makanan")
    @Expose
    private String idMakanan;
    @SerializedName("id_pembeli")
    @Expose
    private String idPembeli;
    @SerializedName("qty")
    @Expose
    private String qty;

    public String getIdKeranjang() {
        return idKeranjang;
    }

    public void setIdKeranjang(String idKeranjang) {
        this.idKeranjang = idKeranjang;
    }

    public String getIdMakanan() {
        return idMakanan;
    }

    public void setIdMakanan(String idMakanan) {
        this.idMakanan = idMakanan;
    }

    public String getIdPembeli() {
        return idPembeli;
    }

    public void setIdPembeli(String idPembeli) {
        this.idPembeli = idPembeli;
    }

    public String getQty() {
        return qty;
    }

    public void setQty(String qty) {
        this.qty = qty;
    }

}
