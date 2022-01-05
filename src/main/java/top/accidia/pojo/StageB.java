
package top.accidia.pojo;

import javax.annotation.Generated;
import com.google.gson.annotations.SerializedName;
import lombok.ToString;

@Generated("net.hexar.json2pojo")
@SuppressWarnings("unused")
@ToString
public class StageB {

    @SerializedName("id")
    private String mId;
    @SerializedName("image")
    private String mImage;
    @SerializedName("name")
    private String mName;

    public String getId() {
        return mId;
    }

    public void setId(String id) {
        mId = id;
    }

    public String getImage() {
        return mImage;
    }

    public void setImage(String image) {
        mImage = image;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

}
