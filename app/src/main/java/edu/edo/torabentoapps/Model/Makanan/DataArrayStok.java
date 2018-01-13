
package edu.edo.torabentoapps.Model.Makanan;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DataArrayStok {

    @SerializedName("id_makanan")
    @Expose
    private String idMakanan;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("ekkado_stok")
    @Expose
    private String ekkadoStok;
    @SerializedName("chickenkatsu_stok")
    @Expose
    private String chickenkatsuStok;
    @SerializedName("ebifurai_stok")
    @Expose
    private String ebifuraiStok;
    @SerializedName("eggchickenroll_stok")
    @Expose
    private String eggchickenrollStok;
    @SerializedName("kakinaga_stok")
    @Expose
    private String kakinagaStok;
    @SerializedName("kaniroll_stok")
    @Expose
    private String kanirollStok;
    @SerializedName("shrimproll_stok")
    @Expose
    private String shrimprollStok;
    @SerializedName("spicychicken_stok")
    @Expose
    private String spicychickenStok;

    public String getIdMakanan() {
        return idMakanan;
    }

    public void setIdMakanan(String idMakanan) {
        this.idMakanan = idMakanan;
    }

    public String getIdReseller() {
        return email;
    }

    public void setIdReseller(String idReseller) {
        this.email = idReseller;
    }

    public String getEkkadoStok() {
        return ekkadoStok;
    }

    public void setEkkadoStok(String ekkadoStok) {
        this.ekkadoStok = ekkadoStok;
    }

    public String getChickenkatsuStok() {
        return chickenkatsuStok;
    }

    public void setChickenkatsuStok(String chickenkatsuStok) {
        this.chickenkatsuStok = chickenkatsuStok;
    }

    public String getEbifuraiStok() {
        return ebifuraiStok;
    }

    public void setEbifuraiStok(String ebifuraiStok) {
        this.ebifuraiStok = ebifuraiStok;
    }

    public String getEggchickenrollStok() {
        return eggchickenrollStok;
    }

    public void setEggchickenrollStok(String eggchickenrollStok) {
        this.eggchickenrollStok = eggchickenrollStok;
    }

    public String getKakinagaStok() {
        return kakinagaStok;
    }

    public void setKakinagaStok(String kakinagaStok) {
        this.kakinagaStok = kakinagaStok;
    }

    public String getKanirollStok() {
        return kanirollStok;
    }

    public void setKanirollStok(String kanirollStok) {
        this.kanirollStok = kanirollStok;
    }

    public String getShrimprollStok() {
        return shrimprollStok;
    }

    public void setShrimprollStok(String shrimprollStok) {
        this.shrimprollStok = shrimprollStok;
    }

    public String getSpicychickenStok() {
        return spicychickenStok;
    }

    public void setSpicychickenStok(String spicychickenStok) {
        this.spicychickenStok = spicychickenStok;
    }

}
