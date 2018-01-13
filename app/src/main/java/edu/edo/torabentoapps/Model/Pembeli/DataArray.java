
package edu.edo.torabentoapps.Model.Pembeli;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DataArray {

    @SerializedName("id_pembeli")
    @Expose
    private String idPembeli;
    @SerializedName("nama_pembeli")
    @Expose
    private String namaPembeli;
    @SerializedName("nomorhp")
    @Expose
    private String nomorhp;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("alamat")
    @Expose
    private String alamat;

    public String getIdPembeli() {
        return idPembeli;
    }

    public void setIdPembeli(String idPembeli) {
        this.idPembeli = idPembeli;
    }

    public String getNamaPembeli() {
        return namaPembeli;
    }

    public void setNamaPembeli(String namaPembeli) {
        this.namaPembeli = namaPembeli;
    }

    public String getNomorhp() {
        return nomorhp;
    }

    public void setNomorhp(String nomorhp) {
        this.nomorhp = nomorhp;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAlamat() {
        return alamat;
    }

    public void setAlamat(String alamat) {
        this.alamat = alamat;
    }

}
