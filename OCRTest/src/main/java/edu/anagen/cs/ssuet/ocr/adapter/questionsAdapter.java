package edu.anagen.cs.ssuet.ocr.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.PopupMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.pixplicity.easyprefs.library.Prefs;

import java.util.ArrayList;
import java.util.List;

import edu.anagen.cs.ssuet.ocr.R;
import edu.anagen.cs.ssuet.ocr.activities.SplashActivity;
import edu.anagen.cs.ssuet.ocr.model.Paper;
import io.realm.Realm;
import io.realm.RealmQuery;
import io.realm.RealmResults;

/**
 * Created by hp on 10/14/2017.
 */

public class questionsAdapter extends BaseAdapter {

    private Context context; //context
    private LayoutInflater inflater = null;
    private Realm realm;
    private List<Paper> paperList;


    public questionsAdapter(Context context, List<Paper> _paper) {

        this.context = context;
        this.paperList = _paper;
        inflater = LayoutInflater.from(context);





    }
    @Override
    public int getCount() {
        return paperList.size();
    }

    @Override
    public Object getItem(int i) {
        return paperList.size();
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View convertview, ViewGroup viewGroup) {


        Paper singlePaper = paperList.get(i);

        ViewHolder holder;
        if(convertview == null) {

            convertview =inflater.inflate(R.layout.questions_list_item,null);
            holder = new ViewHolder();

            holder._scanText = convertview.findViewById(R.id.scan_text);
            holder._complexity = convertview.findViewById(R.id.tv_complexity);
            holder._weightage = convertview.findViewById(R.id.tv_weightage);


            convertview.setTag(holder);

        } else {
            holder = (ViewHolder) convertview.getTag();
        }


        holder._scanText.setText(singlePaper.getText().toString());
        holder._complexity.setText("Complexity " +String.valueOf(singlePaper.getComplexity()));
        holder._weightage.setText("Weightage " +String.valueOf(singlePaper.getWeightage()));


        return convertview;
    }



    public class ViewHolder {


        TextView _scanText;
        TextView _complexity;
        TextView _weightage;
    }
}
