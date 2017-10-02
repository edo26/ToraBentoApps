package edu.edo.torabentoapps.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DataArray {

    @SerializedName("id_reseller")
    @Expose
    private String idReseller;
    @SerializedName("nama_reseller")
    @Expose
    private String namaReseller;
    @SerializedName("alamat")
    @Expose
    private String alamat;
    @SerializedName("nomorhp")
    @Expose
    private String nomorhp;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("username")
    @Expose
    private String username;
    @SerializedName("password")
    @Expose
    private String password;

    public String getIdReseller() {
        return idReseller;
    }

    public void setIdReseller(String idReseller) {
        this.idReseller = idReseller;
    }

    public String getNamaReseller() {
        return namaReseller;
    }

    public void setNamaReseller(String namaReseller) {
        this.namaReseller = namaReseller;
    }

    public String getAlamat() {
        return alamat;
    }

    public void setAlamat(String alamat) {
        this.alamat = alamat;
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

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}