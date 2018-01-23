
package edu.edo.torabentoapps.Model.Makanan;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DataArray1 {

    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("totalstok")
    @Expose
    private String totalstok;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTotalstok() {
        return totalstok;
    }

    public void setTotalstok(String totalstok) {
        this.totalstok = totalstok;
    }

}
