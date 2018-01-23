
package edu.edo.torabentoapps.Model.Transaksi;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ModelDetailPesanan {

    @SerializedName("nilai")
    @Expose
    private Integer nilai;

    @SerializedName("error")
    @Expose
    private String error;

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    @SerializedName("data_array")
    @Expose
    private List<DataArray1> dataArray1 = null;

    public Integer getNilai() {
        return nilai;
    }

    public void setNilai(Integer nilai) {
        this.nilai = nilai;
    }

    public List<DataArray1> getDataArray1() {
        return dataArray1;
    }

    public void setDataArray1(List<DataArray1> dataArray1) {
        this.dataArray1 = dataArray1;
    }

}
