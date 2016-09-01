package prictices.qll.com.loginmvptest.loginblock.view;

/**
 * Created by Administrator on 2016/9/1.
 */

public interface LView {
    public void onClearText();
    public void onLoginResult(Boolean res,boolean code);
    public void onSetProgressBarVisibility(int visibility);
}
