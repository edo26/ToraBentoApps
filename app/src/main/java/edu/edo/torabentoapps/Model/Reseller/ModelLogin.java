
package edu.edo.torabentoapps.Model.Reseller;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ModelLogin {

    @SerializedName("nilai")
    @Expose
    private Integer nilai;
    @SerializedName("data_array")
    @Expose
    private List<DataArray> dataArray = null;

    public Integer getNilai() {
        return nilai;
    }

    public void setNilai(Integer nilai) {
        this.nilai = nilai;
    }

    public List<DataArray> getDataArray() {
        return dataArray;
    }

    public void setDataArray(List<DataArray> dataArray) {
        this.dataArray = dataArray;
    }

}
