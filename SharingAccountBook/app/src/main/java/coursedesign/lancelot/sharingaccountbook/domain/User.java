package coursedesign.lancelot.sharingaccountbook.domain;
import java.io.Serializable;
import java.util.Date;
/**
 * Created by LENOVO on 2018/5/1.
 */

public class User implements Serializable {
    //与远端数据库相比缺少pwd和registerDate字段
    private Integer userId;
    private String userName;
    private String phoneNumber;
    private String nickName;
    private String identity;
    private String picPath;

    public User(){}

    public String toString(){
        return "id:"+userId+"\nuserName:"+userName+"\nnickName:"+nickName+"\n";
    }
    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }



    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }



    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getIdentity() {
        return identity;
    }

    public void setIdentity(String identity) {
        this.identity = identity;
    }

    public String getPicPath() {
        return picPath;
    }

    public void setPicPath(String picPath) {
        this.picPath = picPath;
    }
}
