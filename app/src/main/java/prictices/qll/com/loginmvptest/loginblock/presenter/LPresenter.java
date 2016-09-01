package prictices.qll.com.loginmvptest.loginblock.presenter;

/**
 * Created by Administrator on 2016/9/1.
 */

public interface LPresenter {
    void clear();
    void login(String name,String psd);
    void setProgressBarVisibility(int visible);
}
