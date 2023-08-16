package com.websarva.wings.android.projectx;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class CardRegister_MainActivity extends AppCompatActivity {

    // private static final String DEBUG_TAG = "AsyncSample";
    //URL、あとで記述
    String apiURL = "https://XXX.YY-ZZZZ.com/WWW/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cardregister_activity_main);

        Button btClick = findViewById(R.id.btRegist);
        RegistListener listener = new RegistListener();
        btClick.setOnClickListener(listener);
    }

    private class RegistListener implements View.OnClickListener{
        @Override
        public void onClick(View view) {
            EditText input_mail = findViewById(R.id.etMail);
            EditText input_name = findViewById(R.id.etName);
            EditText input_belonging = findViewById(R.id.etBelonging);

            if (input_mail.getText() == null || input_name.getText() == null
                    || input_belonging.getText() == null) {
                //エラーダイアログ、あとで記述
//                Bundle extra =
//                ~~~DialogFragment dialogFragment = new ~~~DialogFragment();
//                dialogFragment.show(getSupportFragmentManager(), "~~~DialogFragment");
                return;
            }
//
//            //POST?json?なんかでデータ登録、通信エラーでダイアログ表示
            URL url = null;
            HttpURLConnection conn = null;

            JSONObject json = new JSONObject();
            try {
                json.put("mail", input_mail.getText());
                json.put("name", input_name.getText());
                json.put("belonging", input_belonging.getText());
            } catch (JSONException e) {
                e.printStackTrace();
            }

//            String postData = "mail=" + input_mail.getText() + "&name=" + input_name.getText() + "&belonging=" + input_belonging.getText();

            String res = "";
            try {
                url = new URL(apiURL);
                conn = (HttpURLConnection) url.openConnection();

                conn.setRequestMethod("POST");
                conn.setDoInput(true);
                conn.setDoOutput(true);
                conn.setRequestProperty("Content-Type", "application/json; charset = utf-8");
                conn.connect();
//                     Print使う方
                PrintStream ps = new PrintStream(conn.getOutputStream());
                ps.print(json);
                ps.close();

////                     Output使う方
//                OutputStream os = conn.getOutputStream();
//                os.write(postData.getBytes(StandardCharsets.UTF_8));
//                os.close();
                if (conn.getResponseCode() != 200) {
                    //エラーダイアログ表示、あとで記述
                    return;
                }

// ネットで見た方のインプット
                BufferedReader br = new BufferedReader(
                        new InputStreamReader(conn.getInputStream(), "UTF-8"));
                StringBuilder sb = new StringBuilder();
                String line = null;
                while ((line = br.readLine()) != null) {
                    sb.append(line);
                }

                // 戻り値のJSONを文字列に変換
                res = sb.toString();

                System.out.println(line);
                br.close();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (conn != null) {
                    conn.disconnect();
                }
            }

            String resPOST = "";
            int cardId = 0;
            try {
                JSONObject resJSON = new JSONObject(res);
                resPOST = resJSON.getString("result");
                cardId = resJSON.getInt("card_id");
            } catch (JSONException e) {
                e.printStackTrace();
            }

            if (resPOST.equals("NG")) {
                // 通信？エラーファイアログを表示
                return;
            }

            SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(CardRegister_MainActivity.this);
            SharedPreferences.Editor editor = sp.edit();
            editor.putInt("user_id", cardId);    //	キー、値
            editor.commit();

            Intent intent = new Intent(CardRegister_MainActivity.this, Test1.class); //あとで変更
            startActivity(intent);
            return;
        }

    }
}