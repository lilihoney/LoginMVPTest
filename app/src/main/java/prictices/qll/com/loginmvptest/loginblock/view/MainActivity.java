package prictices.qll.com.loginmvptest.loginblock.view;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import prictices.qll.com.loginmvptest.R;
import prictices.qll.com.loginmvptest.loginblock.presenter.LoginPresenter;
import qll.com.block.Views.TeachingActivity;

public class MainActivity extends AppCompatActivity implements LView, View.OnClickListener{
    private EditText etName,etPsd;
    private Button btnLogin,btnClear;
    private LoginPresenter lpresenter;
    private ProgressBar progressBar;
    private SurfaceView

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initViews();
        initPresenter();
    }

    private void initViews(){
        etName = (EditText)findViewById(R.id.et_name);
        etPsd = (EditText)findViewById(R.id.et_psd);
        btnClear = (Button)findViewById(R.id.btn_clear);
        btnLogin = (Button)findViewById(R.id.btn_login);
        progressBar = (ProgressBar)findViewById(R.id.progressBar);

        btnLogin.setOnClickListener(this);
        btnClear.setOnClickListener(this);
    }

    private void initPresenter(){
        lpresenter = new LoginPresenter(this);
        lpresenter.setProgressBarVisibility(View.INVISIBLE);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_clear:
                lpresenter.clear();
                break;
            case R.id.btn_login:
                lpresenter.setProgressBarVisibility(View.VISIBLE);
                btnLogin.setEnabled(false);
                btnClear.setEnabled(false);
                lpresenter.login(etName.getText().toString(),etPsd.getText().toString());
                break;
            default:
                break;
        }
    }

    @Override
    public void onClearText() {
        etPsd.setText("");
        etName.setText("");
    }

    @Override
    public void onLoginResult(Boolean res, boolean code) {
        lpresenter.setProgressBarVisibility(View.VISIBLE);
        btnLogin.setEnabled(true);
        btnClear.setEnabled(true);
        if(res){
//            Toast.makeText(getApplicationContext(),"login success",Toast.LENGTH_LONG).show();
            progressBar.setVisibility(View.GONE);
            startActivity(new Intent(MainActivity.this, TeachingActivity.class));
        }else{
            Toast.makeText(getApplicationContext(),"Login fail,code = "+ code,Toast.LENGTH_LONG).show();
            progressBar.setVisibility(View.GONE);
        }
    }

    @Override
    public void onSetProgressBarVisibility(int visibility) {
        progressBar.setVisibility(visibility);
    }
}
