package com.dealat.View;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import com.dealat.Adapter.RadioAdapter;
import com.dealat.Model.Item;
import com.dealat.Model.User;
import com.dealat.MyApplication;
import com.dealat.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Created by developer on 23.04.18.
 */

public class LanguageActivity extends MasterActivity {

    private RadioAdapter adapter;

    // Views
    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_language);
        super.onCreate(savedInstanceState);
    }


    @Override
    public void getData() {
        Item arabic = new Item("ar", getString(R.string.arabic));
        Item english = new Item("en", getString(R.string.english));

        List<Item> languages = new ArrayList<>();
        languages.add(arabic);
        languages.add(english);

        adapter = new RadioAdapter(mContext, languages);
        listView.setAdapter(adapter);
    }

    @Override
    public void showData() {

    }

    @Override
    public void assignUIReferences() {
        listView = findViewById(R.id.listView);
    }

    @Override
    public void assignActions() {

    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.buttonTrue){
            if (adapter.getSelected() == null)
                showMessageInToast(R.string.labelPleaseSelectLang);
            else {
                Intent intent = new Intent(mContext, CityActivity.class);

                Locale locale = new Locale(adapter.getSelected().getId());

                MyApplication.saveUserState(User.Languaged);
                MyApplication.setLocale(locale);

                Configuration conf = getResources().getConfiguration();
                conf.setLocale(locale);

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N)
                    Locale.setDefault(conf.getLocales().get(0));
                else
                    Locale.setDefault(conf.locale);

                mContext.getResources().updateConfiguration(conf,
                        getResources().getDisplayMetrics());

                startActivity(intent);
                finish();
            }
        }
    }
}
