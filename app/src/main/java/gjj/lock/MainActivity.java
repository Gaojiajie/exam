package gjj.lock;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    Handler handler;
    EditText etName;
    EditText etPassword;
    Button btnLogin;
    Button btnReset;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnLogin = (Button)findViewById(R.id.btnLogin);
        btnReset = (Button)findViewById(R.id.btnReset);
        handler = new Handler(){
          @Override
          public void handleMessage(Message msg){
              if(msg.what == 0x123){
                  Toast.makeText(MainActivity.this,"登录成功",Toast.LENGTH_LONG).show();
                  Intent intent = new Intent(MainActivity.this,MainPage.class);
                  startActivity(intent);

              }
              else if(msg.what == 0x124){
                  Toast.makeText(MainActivity.this,"用户名或密码错误",Toast.LENGTH_LONG).show();
              }

          }
        };

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread(){
                  @Override
                  public void run(){
                      String url = "http://192.168.1.102:8080/2014124068/doLogin";
                      etName = (EditText)findViewById(R.id.etName);
                      etPassword = (EditText)findViewById(R.id.etPassword);
                      url += "?etName="+etName.getText().toString()+"&etPassword="+etPassword.getText().toString();
                      HttpUtils.httpconnect(url);
                      String response =HttpUtils.post(null);
                      Log.d("test-app", "response: " + response);
                      if(response.trim().equalsIgnoreCase("true")){
                          handler.sendEmptyMessage(0x123);
                      }else {
                          handler.sendEmptyMessage(0x124);
                      }
                  }
                }.start();
            }
        });
        btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                etName.setText(null);
                etPassword.setText(null);
            }
        });
    }
}
