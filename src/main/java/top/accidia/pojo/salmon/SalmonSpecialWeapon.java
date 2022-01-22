
package top.accidia.pojo.salmon;

import java.io.Serializable;

import javax.annotation.Generated;

import com.google.gson.annotations.SerializedName;

@Generated("net.hexar.json2pojo")
@SuppressWarnings("unused")
public class SalmonSpecialWeapon implements Serializable {

    @SerializedName("id")
    private String mId;
    @SerializedName("image")
    private String mImage;
    @SerializedName("name")
    private String mName;
    @SerializedName("weapon")
    private SalmonSpecialWeapon mWeapon;

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

    public SalmonSpecialWeapon getWeapon() {
        return mWeapon;
    }

    public void setWeapon(SalmonSpecialWeapon weapon) {
        mWeapon = weapon;
    }

}
