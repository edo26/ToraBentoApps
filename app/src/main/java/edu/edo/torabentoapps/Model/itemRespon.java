package edu.edo.torabentoapps.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by hadip on 29/08/2017.
 */

public class itemRespon {
    @SerializedName("nilai")
    @Expose
    private Integer nilai;
    private String pesan;
    @SerializedName("data_array")
    @Expose
    private List<itemModel> liModel;

    public Integer getNilai() {
        return nilai;
    }

    public String getPesan() {
        return pesan;
    }

    public List<itemModel> getLiModel() {
        return liModel;
    }
}