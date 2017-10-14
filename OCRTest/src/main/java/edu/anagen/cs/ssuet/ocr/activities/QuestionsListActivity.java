package edu.anagen.cs.ssuet.ocr.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import edu.anagen.cs.ssuet.ocr.R;
import edu.anagen.cs.ssuet.ocr.adapter.questionsAdapter;
import edu.anagen.cs.ssuet.ocr.model.Paper;
import io.realm.Realm;
import io.realm.RealmQuery;
import io.realm.RealmResults;


public class QuestionsListActivity extends AppCompatActivity {


    private ListView _lv;
    private Realm realm;
    private List<Paper> paperList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_questions_list);

        realm = Realm.getDefaultInstance();
        paperList = new ArrayList<>();


        RealmQuery<Paper> query = realm.where(Paper.class);
        RealmResults<Paper> _paper = query.findAll();

        for(Paper results : _paper) {

            paperList.add(results);
        }

        Log.d("paper",paperList.toString());
        _lv = (ListView) findViewById(R.id.questionsListView);
        _lv.setAdapter(new questionsAdapter(this,paperList));

      /*  RealmQuery<Paper> query = realm.where(Paper.class);
        RealmResults<Paper> realmResults = query.findAll();

        for(Paper _paper : realmResults) {

            paper.add(_paper);
        }*/

        _lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {



            }
        });
    }
}
