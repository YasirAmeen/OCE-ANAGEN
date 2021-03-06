package edu.anagen.cs.ssuet.ocr.activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import edu.anagen.cs.ssuet.ocr.R;
import edu.anagen.cs.ssuet.ocr.model.Paper;
import edu.anagen.cs.ssuet.ocr.model.User;
import io.realm.Realm;
import io.realm.RealmResults;


public class OCRResultActivity extends ActionBarActivity {



    private EditText _ocr_result_text;
    private Button btn_edit;
    private Button btn_save;
    private String resultsText;
    private Realm realm;
    private boolean fromQuestionList;
    private int _id;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ocrresult);


        realm = Realm.getDefaultInstance();
        btn_edit = (Button) findViewById(R.id._btn_edit);
        btn_save = (Button) findViewById(R.id._btn_save);

        getSupportActionBar().hide();
       _ocr_result_text = (EditText) findViewById(R.id.ocr_result_text);

        final Intent intent = getIntent();
        fromQuestionList = intent.getBooleanExtra("comingFromQuestionList",false);
        if(fromQuestionList) {
            resultsText = intent.getStringExtra("_text");
            _id = intent.getIntExtra("_id",0);
            _ocr_result_text.setText(resultsText);
            _ocr_result_text.setEnabled(false);
            btn_save.setText("Update");
        }else {
            String resultsText = intent.getStringExtra("ocrText");
           /* resultsText = "It is a long established fact that a reader will be distracted by the readable content of a page when looking at its layout. The point of using Lorem Ipsum is that it has a more-or-less normal distribution of letters,3" +
                    "";*/

            _ocr_result_text.setText(resultsText.trim().replaceAll(" +", " "));
            _ocr_result_text.setEnabled(false);
        }
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);




        btn_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                _ocr_result_text.setEnabled(true);
                _ocr_result_text.setFocusable(true);
            }
        });


        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                /*if(!_ocr_result_text.isEnabled()) {

                    Toast.makeText(OCRResultActivity.this, "Please enable text editing first..", Toast.LENGTH_SHORT).show();
                    return;
                }*/

                AlertDialog.Builder builder = new AlertDialog.Builder(OCRResultActivity.this);
                LayoutInflater inflater = getLayoutInflater();

                View dialog_layout = inflater.inflate(R.layout.ocr_result_dialog,null);
                final EditText _et_com = (EditText) dialog_layout.findViewById(R.id.et_com);
                final EditText _et_wage = (EditText) dialog_layout.findViewById(R.id.et_wage);
               // final EditText _et_name = (EditText) dialog_layout.findViewById(R.id.et_name_exam);
                final TextView _btn_done = dialog_layout.findViewById(R.id.tv_done);
                final TextView _btn_cancel = dialog_layout.findViewById(R.id.tv_cancel);
                builder.setView(dialog_layout);


                final AlertDialog dialog = builder.create();
                _btn_done.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {


                        if(btn_save.getText().toString().equals("Update")) {


                            if(_et_com.getText().toString().isEmpty()) {

                                Toast.makeText(OCRResultActivity.this, "Complexity is required..", Toast.LENGTH_SHORT).show();
                                return;
                            }else if(_et_wage.getText().toString().isEmpty()) {

                                Toast.makeText(OCRResultActivity.this, "Weightage is required..", Toast.LENGTH_SHORT).show();
                                return;
                            }/*else if(_et_name.getText().toString().isEmpty()) {
                            Toast.makeText(OCRResultActivity.this, "Name is required..", Toast.LENGTH_SHORT).show();
                            return;
                        }*/else {


                                RealmResults<Paper> papers = realm.where(Paper.class).findAll();
                                Paper singlePaper = papers.where().equalTo("id",_id).findFirst();

                                // String editedText = _ocr_result_text.getText().toString().trim().replaceAll("\\s{2,}", " ")
                                realm.beginTransaction();
                                singlePaper.setComplexity(Integer.parseInt(_et_com.getText().toString()));
                                singlePaper.setWeightage(Float.parseFloat(_et_wage.getText().toString()));
                                // paper.setName(_et_name.getText().toString());
                                singlePaper.setText(_ocr_result_text.getText().toString());
                                realm.commitTransaction();

                                Toast.makeText(OCRResultActivity.this, "Update Successfully", Toast.LENGTH_SHORT).show();
                                dialog.dismiss();
                                finish();

                            }
                        }else {
                            if(_et_com.getText().toString().isEmpty()) {

                                Toast.makeText(OCRResultActivity.this, "Complexity is required..", Toast.LENGTH_SHORT).show();
                                return;
                            }else if(_et_wage.getText().toString().isEmpty()) {

                                Toast.makeText(OCRResultActivity.this, "Weightage is required..", Toast.LENGTH_SHORT).show();
                                return;
                            }/*else if(_et_name.getText().toString().isEmpty()) {
                            Toast.makeText(OCRResultActivity.this, "Name is required..", Toast.LENGTH_SHORT).show();
                            return;
                        }*/else {

                                int id = -1;
                                RealmResults<Paper> results = realm.where(Paper.class).findAll();
                                if(results.size()  == 0) {

                                    id = 1;

                                } else {

                                    id= results.max("id").intValue() + 1;

                                }
                                // String editedText = _ocr_result_text.getText().toString().trim().replaceAll("\\s{2,}", " ")
                                realm.beginTransaction();
                                Paper paper = realm.createObject(Paper.class);
                                paper.setId(id);
                                paper.setComplexity(Integer.parseInt(_et_com.getText().toString()));
                                paper.setWeightage(Float.parseFloat(_et_wage.getText().toString()));
                                // paper.setName(_et_name.getText().toString());
                                paper.setText(_ocr_result_text.getText().toString());
                                realm.commitTransaction();

                                Toast.makeText(OCRResultActivity.this, "Saved Successfully", Toast.LENGTH_SHORT).show();
                                dialog.dismiss();
                                finish();
                               Intent intent1 = new Intent(OCRResultActivity.this,QuestionsListActivity.class);
                               startActivity(intent1);


                            }
                        }


                    }
                });


                _btn_cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        dialog.dismiss();
                    }
                });


                getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
                dialog.getWindow().setBackgroundDrawableResource(R.drawable.rounded_for_dialo);
                dialog.show();


            }
        });



    }
}
