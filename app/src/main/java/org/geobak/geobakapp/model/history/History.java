
package org.geobak.geobakapp.model.history;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class History {

    @SerializedName("hasil")
    @Expose
    private List<Hasil> hasil = null;

    public List<Hasil> getHasil() {
        return hasil;
    }

    public void setHasil(List<Hasil> hasil) {
        this.hasil = hasil;
    }

}
