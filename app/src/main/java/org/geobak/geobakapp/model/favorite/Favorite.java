
package org.geobak.geobakapp.model;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Favorite {

    @SerializedName("result")
    @Expose
    private List<org.geobak.geobakapp.model.Result> result = null;

    public List<org.geobak.geobakapp.model.Result> getResult() {
        return result;
    }

    public void setResult(List<org.geobak.geobakapp.model.Result> result) {
        this.result = result;
    }

}
