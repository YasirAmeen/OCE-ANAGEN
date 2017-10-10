package edu.anagen.cs.ssuet.ocr.activities;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import edu.anagen.cs.ssuet.ocr.R;
import edu.anagen.cs.ssuet.ocr.Utils;
import edu.anagen.cs.ssuet.ocr.model.User;
import io.realm.Realm;
import io.realm.RealmResults;


public class RegisterActivity extends ActionBarActivity {

    private EditText _et_fullname;
    private EditText _et_email;
    private EditText _et_password;
    private EditText _et_mobile;
    private Button _btn_createAccount;

    private Realm realm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        setupComponents();
        setupListener();
    }


    private void setupComponents() {

         realm = Realm.getDefaultInstance();
        _et_fullname = (EditText) findViewById(R.id.et_fullname);
        _et_email = (EditText) findViewById(R.id.et_email);
        _et_password = (EditText) findViewById(R.id.et_password);
        _et_mobile = (EditText) findViewById(R.id.et_mobile);
        _btn_createAccount = (Button) findViewById(R.id.btn_createAccount);

    }

    private void setupListener() {

        _btn_createAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {



                if(_et_fullname.getText().toString().isEmpty()) {

                    Toast.makeText(RegisterActivity.this, "Fullname is required..", Toast.LENGTH_SHORT).show();
                    return;

                }else if(_et_email.getText().toString().isEmpty()) {
                    Toast.makeText(RegisterActivity.this, "Email is required..", Toast.LENGTH_SHORT).show();
                    return;
                }else if(!Utils.isValidEmail(_et_email.getText().toString())) {
                    Toast.makeText(RegisterActivity.this, "Please correct email address", Toast.LENGTH_SHORT).show();
                    return;
                }else if(_et_password.getText().toString().isEmpty()) {
                    Toast.makeText(RegisterActivity.this, "Password is required", Toast.LENGTH_SHORT).show();
                    return;
                }else if(_et_mobile.getText().toString().isEmpty()) {
                    Toast.makeText(RegisterActivity.this, "Phone number is required", Toast.LENGTH_SHORT).show();
                    return;
                }else {

                    registerUser(_et_fullname.getText().toString().toLowerCase().trim(),_et_email.getText().toString().trim(),_et_password.getText().toString().trim(),_et_mobile.getText().toString().trim());
                }
            }
        });
    }

    private void registerUser(String fullname, String email, String password, String mobile) {

        int id = -1;
        RealmResults<User> results = realm.where(User.class).findAll();
        if(results.size()  == 0) {

            id = 1;

        } else {

            id= results.max("id").intValue() + 1;

        }
        realm.beginTransaction();
        User user = realm.createObject(User.class);
        user.setId(id);
        user.setFullname(fullname);
        user.setEmail(email);
        user.setPassword(password);
        user.setMobile(mobile);
        realm.commitTransaction();
        LoaderClass task = new LoaderClass();
        task.execute();

    }


    private class LoaderClass extends AsyncTask<Void,Void,Void> {

        ProgressDialog pd;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            pd = Utils.MorseProgressDialog(RegisterActivity.this,null,"Creating account...");
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
            Toast.makeText(RegisterActivity.this, "User Created Successfully", Toast.LENGTH_SHORT).show();
            finish();

        }
    }
}
