package edu.edo.torabentoapps.Model.Makanan;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by hadip on 29/08/2017.
 */

public class ModelMakanan {
    @SerializedName("nilai")
    @Expose
    private Integer nilai;
    private String pesan;
    @SerializedName("data_array")
    @Expose
    private List<itemRespon> liModel;

    public Integer getNilai() {
        return nilai;
    }

    public String getPesan() {
        return pesan;
    }

    public List<itemRespon> getLiModel() {
        return liModel;
    }
}