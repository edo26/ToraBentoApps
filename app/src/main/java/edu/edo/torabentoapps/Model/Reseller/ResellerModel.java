package edu.edo.torabentoapps.Model.Reseller;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ResellerModel {

    @SerializedName("nilai")
    @Expose
    private Integer nilai;
    @SerializedName("data_array")
    @Expose
    private List<ModelReseller> modelReseller = null;

    public Integer getNilai() {
        return nilai;
    }

    public void setNilai(Integer nilai) {
        this.nilai = nilai;
    }

    public List<ModelReseller> getModelReseller() {
        return modelReseller;
    }

    public void setModelReseller(List<ModelReseller> modelReseller) {
        this.modelReseller = modelReseller;
    }

}