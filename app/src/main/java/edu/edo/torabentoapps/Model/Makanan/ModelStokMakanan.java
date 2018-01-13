
package edu.edo.torabentoapps.Model.Makanan;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ModelStokMakanan {

    @SerializedName("nilai")
    @Expose
    private Integer nilai;
    @SerializedName("data_array")
    @Expose
    private List<DataArrayStok> dataArray1 = null;

    public Integer getNilai() {
        return nilai;
    }

    public void setNilai(Integer nilai) {
        this.nilai = nilai;
    }

    public List<DataArrayStok> getDataArray1() {
        return dataArray1;
    }

    public void setDataArray1(List<DataArrayStok> dataArray1) {
        this.dataArray1 = dataArray1;
    }

}
