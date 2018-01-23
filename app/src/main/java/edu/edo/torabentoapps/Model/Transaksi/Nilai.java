
package edu.edo.torabentoapps.Model.Transaksi;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Nilai {

    @SerializedName("nilai")
    @Expose
    private Integer nilai;

    public Integer getNilai() {
        return nilai;
    }
}
