
package top.accidia.pojo.salmon;

import java.io.Serializable;
import java.util.List;
import javax.annotation.Generated;
import com.google.gson.annotations.SerializedName;

@Generated("net.hexar.json2pojo")
@SuppressWarnings("unused")
public class Salmon implements Serializable {

    @SerializedName("details")
    private List<SalmonDetail> mDetails;
    @SerializedName("schedules")
    private List<SalmonSchedule> mSchedules;

    public List<SalmonDetail> getDetails() {
        return mDetails;
    }

    public void setDetails(List<SalmonDetail> details) {
        mDetails = details;
    }

    public List<SalmonSchedule> getSchedules() {
        return mSchedules;
    }

    public void setSchedules(List<SalmonSchedule> schedules) {
        mSchedules = schedules;
    }

}
