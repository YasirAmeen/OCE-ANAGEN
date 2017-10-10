package edu.anagen.cs.ssuet.ocr;

import io.realm.Realm;
import io.realm.RealmConfiguration;

/**
 * Created by hp on 10/8/2017.
 */

public class Application extends android.app.Application {


    @Override
    public void onCreate() {
        super.onCreate();

        RealmConfiguration realmConfiguration = new RealmConfiguration.Builder(this)
                .name("AndroidDB.realm")
                .schemaVersion(0)
                .deleteRealmIfMigrationNeeded()
                .build();
        Realm.setDefaultConfiguration(realmConfiguration);
    }
}
