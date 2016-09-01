package prictices.qll.com.loginmvptest.loginblock.presenter;

import android.os.Handler;
import android.os.Looper;

import prictices.qll.com.loginmvptest.loginblock.model.User;
import prictices.qll.com.loginmvptest.loginblock.view.LView;

/**
 * Created by Administrator on 2016/9/1.
 */

public class LoginPresenter implements LPresenter{
    LView ilview;
    User user;
    Handler handler;

    public LoginPresenter(LView ilview){
        this.ilview = ilview;
        user = new User("haha","123");
        handler = new Handler(Looper.getMainLooper());
    }

    @Override
    public void clear() {
        ilview.onClearText();
    }

    @Override
    public void login(String name, String psd) {
        Boolean hasLogin = user.checkUserValidity(name,psd);
        final boolean code = user.checkUserValidity(name,psd);
        if(code){
            hasLogin = true;
        }
        final boolean res = hasLogin;
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                ilview.onLoginResult(res,code);
            }
        },3000);
    }

    @Override
    public void setProgressBarVisibility(int visible) {
        ilview.onSetProgressBarVisibility(visible);
    }
}
