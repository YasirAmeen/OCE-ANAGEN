package edu.anagen.cs.ssuet.ocr.activities;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.PopupMenu;
import android.util.Log;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.pixplicity.easyprefs.library.Prefs;

import java.util.ArrayList;
import java.util.List;

import edu.anagen.cs.ssuet.ocr.CaptureActivity;
import edu.anagen.cs.ssuet.ocr.R;
import edu.anagen.cs.ssuet.ocr.Utils;
import edu.anagen.cs.ssuet.ocr.adapter.questionsAdapter;
import edu.anagen.cs.ssuet.ocr.model.Paper;
import io.realm.Realm;
import io.realm.RealmQuery;
import io.realm.RealmResults;


public class QuestionsListActivity extends AppCompatActivity {


    private static final int MY_PERMISSIONS_REQUEST_READ_CONTACTS = 12345;
    private ListView _lv;
    private Realm realm;
    private List<Paper> paperList;
    private questionsAdapter adapter;
    private ImageView _scan_image;
    private LinearLayout _no_question_view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_questions_list);

        realm = Realm.getDefaultInstance();
        paperList = new ArrayList<>();
        _scan_image = (ImageView) findViewById(R.id.scan_image);
        _no_question_view = (LinearLayout) findViewById(R.id.no_question_view);
        _lv = (ListView) findViewById(R.id.questionsListView);
        RealmQuery<Paper> query = realm.where(Paper.class);
        RealmResults<Paper> _paper = query.findAll();

        for(Paper results : _paper) {

            paperList.add(results);
        }
        if(paperList.size() != 0) {

            _no_question_view.setVisibility(View.INVISIBLE);
            _lv.setVisibility(View.VISIBLE);
            Log.d("paper",paperList.toString());
            adapter = new questionsAdapter(this,paperList);
            _lv.setAdapter(adapter);
            _lv.setLongClickable(true);
        }else {
            _no_question_view.setVisibility(View.VISIBLE);
            _lv.setVisibility(View.INVISIBLE);


        }



        _lv.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(final AdapterView<?> adapterView, View view, int i, long l) {

                final Paper __paper = paperList.get(i);

                AlertDialog.Builder builder = new AlertDialog.Builder(QuestionsListActivity.this);
                builder.setMessage("Do you really want to delete this question.");

                //Set When SEND Button Click
                builder.setPositiveButton("DELETE", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                         final RealmResults<Paper> result = realm.where(Paper.class).findAll();
                        realm.executeTransaction(new Realm.Transaction() {
                            @Override
                            public void execute(Realm realm) {
                                Paper paper = result.where().equalTo("id",__paper.getId()).findFirst();


                                for(Paper iter : paperList) {

                                    if(iter.getId() == __paper.getId()) {

                                       paperList.remove(iter);
                                    }
                                }
                                paper.deleteFromRealm();
                                adapter.notifyDataSetChanged();
                                if(paperList.size() != 0) {

                                    _no_question_view.setVisibility(View.INVISIBLE);
                                    _lv.setVisibility(View.VISIBLE);

                                }else {
                                    _no_question_view.setVisibility(View.VISIBLE);
                                    _lv.setVisibility(View.INVISIBLE);


                                }
                                Toast.makeText(QuestionsListActivity.this, "Deleting done..", Toast.LENGTH_SHORT).show();
                            }

                        });
                    }
                });

                //Set When Cancel Button Click
                builder.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        //Dismissing the alertDialog
                        dialogInterface.dismiss();
                    }
                });

                AlertDialog dialog = builder.create();
                dialog.show();

                return true;
            }


        });


        _lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {


                Paper paper = paperList.get(i);
                Intent intent = new Intent(QuestionsListActivity.this,OCRResultActivity.class);
                intent.putExtra("comingFromQuestionList",true);
                intent.putExtra("_id",paper.getId());
                intent.putExtra("_text",paper.getText());
                startActivity(intent);

            }
        });


        _scan_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP) {

                    if (ActivityCompat.checkSelfPermission(QuestionsListActivity.this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                        ActivityCompat.requestPermissions(QuestionsListActivity.this, new String[]{Manifest.permission.CAMERA}, 1002);
                    }else {
                        Intent intent = new Intent(QuestionsListActivity.this,CaptureActivity.class);
                        startActivity(intent);
                    }
                }

            }
        });


        findViewById(R.id.popmenu).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                showPopUpMenu(view);
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(requestCode ==1002 ) {

            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                Intent intent = new Intent(QuestionsListActivity.this,CaptureActivity.class);
                startActivity(intent);

            } else {
                Toast.makeText(this, "Permission denied...", Toast.LENGTH_SHORT).show();
            }
            return;
        }

    }

    private void showPopUpMenu(final View view) {
        final PopupMenu popup = new PopupMenu(QuestionsListActivity.this, view);
        popup.getMenuInflater().inflate(R.menu.popup_menu, popup.getMenu());
        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            public boolean onMenuItemClick(MenuItem item) {
                int i = item.getItemId();
                if (i == R.id.about) {
                    Toast.makeText(QuestionsListActivity.this, "About is coming soon", Toast.LENGTH_SHORT).show();
                    return true;
                }
                else if (i == R.id.logout){

                    new LogOutTask().execute();
                    return true;
                }
                else {
                    return onMenuItemClick(item);
                }
            }
        });

        popup.show();
    }


    @Override
    protected void onResume() {
        super.onResume();
        if(adapter != null) {
            adapter.notifyDataSetChanged();
        }
    }

    private class LogOutTask extends AsyncTask<Void,Void,Void> {

        ProgressDialog pd;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        pd = Utils.MorseProgressDialog(QuestionsListActivity.this,null,"Signing out...");
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
            Prefs.putBoolean("loginSuccess",false);
            Intent intent = new Intent(QuestionsListActivity.this, SplashActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            finish();

        }
    }
}
