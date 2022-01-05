
package top.accidia.pojo;

import javax.annotation.Generated;
import com.google.gson.annotations.SerializedName;
import lombok.ToString;

@Generated("net.hexar.json2pojo")
@SuppressWarnings("unused")
@ToString
public class Rule {

    @SerializedName("key")
    private String mKey;
    @SerializedName("multiline_name")
    private String mMultilineName;
    @SerializedName("name")
    private String mName;

    public String getKey() {
        return mKey;
    }

    public void setKey(String key) {
        mKey = key;
    }

    public String getMultilineName() {
        return mMultilineName;
    }

    public void setMultilineName(String multilineName) {
        mMultilineName = multilineName;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

}
