package com.websarva.wings.android.projectx;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;

public class Activate_MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activate_activity_main);

        //テスト、カードID変更
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString("user_id", "");	//	キー、値
        editor.commit();

        //カードID読み込み
        String userId = sp.getString("user_id", "");

        if(userId.isEmpty() == true){
            Intent intent = new Intent(Activate_MainActivity.this, Activate_MainActivity.class); //あとで変更
            startActivity(intent);
            return;
        }else{
            Intent intent = new Intent(Activate_MainActivity.this, Test1.class); //あとで変更
            startActivity(intent);
            return;
        }

    }
}