package edu.anagen.cs.ssuet.ocr.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import edu.anagen.cs.ssuet.ocr.CaptureActivity;
import edu.anagen.cs.ssuet.ocr.R;
import edu.anagen.cs.ssuet.ocr.Utils;
import edu.anagen.cs.ssuet.ocr.model.User;
import io.realm.Realm;
import io.realm.RealmQuery;
import io.realm.RealmResults;


public class SignInActivitiy extends AppCompatActivity {


    private Button _btn_register;
    private Button _btn_signin;
    private EditText _et_email_s;
    private EditText _et_password_s;
    private Realm realm;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in_activitiy);
        setupComponents();
        setupListener();

    }

    private void setupComponents() {

        realm = Realm.getDefaultInstance();
        _btn_register = (Button) findViewById(R.id.btn_register);
        _btn_signin = (Button) findViewById(R.id.btn_signin);
        _et_email_s = (EditText) findViewById(R.id.et_email_s);
        _et_password_s = (EditText) findViewById(R.id.et_password_s);


    }

    private void setupListener() {

        _btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SignInActivitiy.this,RegisterActivity.class));
            }
        });

        _btn_signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(_et_email_s.getText().toString().isEmpty()) {
                    Toast.makeText(SignInActivitiy.this, "Email is required..", Toast.LENGTH_SHORT).show();
                    return;
                }else if(!Utils.isValidEmail(_et_email_s.getText().toString().trim())) {
                    Toast.makeText(SignInActivitiy.this, "Please enter valid email address", Toast.LENGTH_SHORT).show();
                    return;
                }else if(_et_password_s.getText().toString().isEmpty()) {
                    Toast.makeText(SignInActivitiy.this, "Password is required..", Toast.LENGTH_SHORT).show();
                    return;
                }else {

                    signUp(_et_email_s.getText().toString().toLowerCase().trim(),_et_password_s.getText().toString().trim());
                }


            }
        });
    }

    private void signUp(String email, String password) {

        RealmQuery<User> query = realm.where(User.class);
        User queryResult = query.equalTo("email",email).equalTo("password",password).findFirst();
        if(queryResult != null) {
            LoaderClass task = new LoaderClass();
            task.execute();
            Toast.makeText(this, "Login Success", Toast.LENGTH_SHORT).show();

        }else {
            LoaderClass task = new LoaderClass();
            task.execute();
            Toast.makeText(this, "Login Failed", Toast.LENGTH_SHORT).show();

        }



    }


    private class LoaderClass extends AsyncTask<Void,Void,Void> {

        ProgressDialog pd;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            pd = Utils.MorseProgressDialog(SignInActivitiy.this,null,"Verifying Account...");
            pd.show();

        }

        @Override
        protected Void doInBackground(Void... voids) {


            for(int i = 0; i < 3; i++) {

                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            pd.dismiss();

        }
    }

}
