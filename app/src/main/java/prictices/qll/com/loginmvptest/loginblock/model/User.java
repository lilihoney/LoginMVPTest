package prictices.qll.com.loginmvptest.loginblock.model;

/**
 * Created by Administrator on 2016/9/1.
 */

public class User {
    private String user_name;
    private String psd;

    public User(String name,String psd){
        this.user_name = name;
        this.psd = psd;
    }

    public String getUser_name(){
        return user_name;
    }
    public String getPsd(){
        return psd;
    }

    public void setUser_name(String name){
        this.user_name = name;
    }
    public void setPsd(String psd){
        this.psd = psd;
    }

    public boolean checkUserValidity(String name,String psd){
        if(this.user_name.equals(name) && this.psd.equals(psd)){
            return true;
        }
        return false;
    }
}
