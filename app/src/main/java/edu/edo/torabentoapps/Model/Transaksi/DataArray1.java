
package edu.edo.torabentoapps.Model.Transaksi;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DataArray1 {

    @SerializedName("nama_pembeli")
    @Expose
    private String namaPembeli;
    @SerializedName("nama_makanan")
    @Expose
    private String namaMakanan;
    @SerializedName("qty")
    @Expose
    private String qty;

    public String getNamaPembeli() {
        return namaPembeli;
    }

    public void setNamaPembeli(String namaPembeli) {
        this.namaPembeli = namaPembeli;
    }

    public String getNamaMakanan() {
        return namaMakanan;
    }

    public void setNamaMakanan(String namaMakanan) {
        this.namaMakanan = namaMakanan;
    }

    public String getQty() {
        return qty;
    }

    public void setQty(String qty) {
        this.qty = qty;
    }

}
