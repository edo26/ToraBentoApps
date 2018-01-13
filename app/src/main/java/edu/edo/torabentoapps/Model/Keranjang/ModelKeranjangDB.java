
package edu.edo.torabentoapps.Model.Keranjang;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ModelKeranjangDB {

    @SerializedName("nilai")
    @Expose
    private Integer nilai;
    @SerializedName("data_array")
    @Expose
    private List<ModelKeranjang> modelKeranjang = null;

    public Integer getNilai() {
        return nilai;
    }

    public void setNilai(Integer nilai) {
        this.nilai = nilai;
    }

    public List<ModelKeranjang> getModelKeranjang() {
        return modelKeranjang;
    }

    public void setModelKeranjang(List<ModelKeranjang> modelKeranjang) {
        this.modelKeranjang = modelKeranjang;
    }

}
