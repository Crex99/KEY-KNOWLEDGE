package com.example.keyknowledge;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import com.example.keyknowledge.control.*;
import com.example.keyknowledge.model.*;

public class Login extends Activity {
    SharedPreferences pref;
    EditText us,pass;
    LoginControl control=new LoginControl(this);
    ViewDialog dialog;
    public static final String BroadcastStringForAction = "checkinternet";
    private IntentFilter mIntentFilter;


    public Login()  {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        pref=getSharedPreferences("profile",MODE_PRIVATE);
        us=findViewById(R.id.user);
        pass=findViewById(R.id.pass);
        dialog = new ViewDialog(this, R.layout.alert_connection_layout, null);
        mIntentFilter = new IntentFilter();
        mIntentFilter.addAction(BroadcastStringForAction);
        Intent serviceIntent = new Intent(this, MyService.class);
        startService(serviceIntent);
        if(!isOnline(getApplicationContext())){
            if(dialog != null && !dialog.isShowen()){
                dialog.showAlertDialog();
            }
        }else{
            if(dialog.isShowen()){
                dialog.dismiss();
            }
        }
    }

    public BroadcastReceiver MyReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {

            if(intent.getAction().equals(BroadcastStringForAction)){
                if(!intent.getStringExtra("online_status").equals("true")){
                    if(dialog != null && !dialog.isShowen()){
                        dialog.showAlertDialog();
                    }
                }else{
                    if(dialog.isShowen()){
                        dialog.dismiss();
                    }
                }
            }
        }
    };

    public boolean isOnline(Context c){

        ConnectivityManager cm = (ConnectivityManager) c.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo ni = cm.getActiveNetworkInfo();
        if(ni != null && ni.isConnectedOrConnecting()){
            return true;
        }else{
            return false;
        }
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        registerReceiver(MyReceiver, mIntentFilter);
    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(MyReceiver);
    }

    @Override
    protected void onResume() {
        super.onResume();
        registerReceiver(MyReceiver, mIntentFilter);
    }

    public void message(String x){
        Toast.makeText(this,x, Toast.LENGTH_LONG).show();
    }

    public void access(View view) {
        control.access(us.getText().toString(),pass.getText().toString());
    }

    public void register(View view) {
        Toast.makeText(this,"funzionalità ancora non disponibile", Toast.LENGTH_LONG).show();
    }

    public void saveUser(User user) {
        SharedPreferences.Editor editor=pref.edit();
        editor.putString("id",user.getNickname());
        editor.commit();
    }
}
