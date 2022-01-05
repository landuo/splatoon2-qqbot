
package top.accidia.pojo;

import java.util.List;
import javax.annotation.Generated;
import com.google.gson.annotations.SerializedName;
import lombok.ToString;

@Generated("net.hexar.json2pojo")
@SuppressWarnings("unused")
@ToString
public class Schedule {

    @SerializedName("gachi")
    private List<Gachi> mGachi;
    @SerializedName("league")
    private List<League> mLeague;
    @SerializedName("regular")
    private List<Regular> mRegular;

    public List<Gachi> getGachi() {
        return mGachi;
    }

    public void setGachi(List<Gachi> gachi) {
        mGachi = gachi;
    }

    public List<League> getLeague() {
        return mLeague;
    }

    public void setLeague(List<League> league) {
        mLeague = league;
    }

    public List<Regular> getRegular() {
        return mRegular;
    }

    public void setRegular(List<Regular> regular) {
        mRegular = regular;
    }

}
