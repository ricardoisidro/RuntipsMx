package com.runtips.ricardo.runtipsmx.app;

import android.app.Application;

import com.runtips.ricardo.runtipsmx.models.Test;

import java.util.concurrent.atomic.AtomicInteger;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmObject;
import io.realm.RealmResults;

public class RuntipsMXApp extends Application {

    public static AtomicInteger testId = new AtomicInteger();


    @Override
    public void onCreate() {
        super.onCreate();
        setupRealmConfig();
        Realm realm = Realm.getDefaultInstance();
        testId = getIdByTable(realm, Test.class);
        realm.close();

    }

    private void setupRealmConfig() {
        Realm.init(getApplicationContext());
        RealmConfiguration defaultconfig = new RealmConfiguration
                .Builder()
                .name("runtipsdb.realm")
                .schemaVersion(0)
                .deleteRealmIfMigrationNeeded()
                .build();
        Realm.setDefaultConfiguration(defaultconfig);
    }

    private <T extends RealmObject> AtomicInteger getIdByTable(Realm realm, Class<T> anyClass) {
        RealmResults<T> results = realm.where(anyClass).findAll();
        return (results.size() > 0) ? new AtomicInteger(results.max("id").intValue()) : new AtomicInteger();

    }
}
