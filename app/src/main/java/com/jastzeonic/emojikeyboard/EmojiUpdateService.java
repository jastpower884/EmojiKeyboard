package com.jastzeonic.emojikeyboard;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.jastzeonic.emojikeyboard.database.EmojiItemController;
import com.jastzeonic.emojikeyboard.database.EmojiTypeController;
import com.jastzeonic.emojikeyboard.item.EmojiItem;
import com.jastzeonic.emojikeyboard.item.EmojiTypeItem;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class EmojiUpdateService extends Service {

    public final static String TAG = "EmojiUpdateService";


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference typeReference = database.getReference("types");
        typeReference.addValueEventListener(typeEventListener);
        DatabaseReference emojiReference = database.getReference("content");
        emojiReference.addValueEventListener(emojiContentEventListener);


    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);
    }

    private ValueEventListener emojiContentEventListener = new ValueEventListener() {
        @Override
        public void onDataChange(DataSnapshot dataSnapshot) {
            // This method is called once with the initial value and again
            // whenever data at this location is updated.
            Log.d(TAG, "Child Count: " + dataSnapshot.getChildrenCount());

            Iterable<DataSnapshot> datas = dataSnapshot.getChildren();

            List<EmojiItem> results = new ArrayList<>();
            for (datas.iterator(); datas.iterator().hasNext(); ) {
                DataSnapshot content = datas.iterator().next();


                try {
                    EmojiItem result = new EmojiItem();
                    JSONObject jsonObject = new JSONObject(content.getValue().toString());
                    result.setId(jsonObject.getLong("id"));
                    result.setContent(jsonObject.getString("content"));
                    result.setType(jsonObject.getString("type"));
                    results.add(result);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                Log.v(TAG, "" + content.getValue().toString());
            }

            EmojiItemController emojiItemController = new EmojiItemController();
            emojiItemController.getEmojiItemDao().insertInTx(results);

            String value = dataSnapshot.getKey();
            Log.d(TAG, "Value is: " + value);
        }

        @Override
        public void onCancelled(DatabaseError error) {
            // Failed to read value
            Log.w(TAG, "Failed to read value.", error.toException());
        }
    };

    private ValueEventListener typeEventListener = new ValueEventListener() {
        @Override
        public void onDataChange(DataSnapshot dataSnapshot) {
            // This method is called once with the initial value and again
            // whenever data at this location is updated.
            Log.d(TAG, "Child Count: " + dataSnapshot.getChildrenCount());

            Iterable<DataSnapshot> datas = dataSnapshot.getChildren();
            List<EmojiTypeItem> results = new ArrayList<>();
            long index = 0;
            for (datas.iterator(); datas.iterator().hasNext(); index++) {
                DataSnapshot content = datas.iterator().next();
                EmojiTypeItem result = new EmojiTypeItem();
                result.setId(index);
                result.setContent(content.getValue().toString());
                results.add(result);

            }

            EmojiTypeController emojiItemController = new EmojiTypeController();
            emojiItemController.getDao().insertInTx(results);
            String value = dataSnapshot.getKey();
            Log.d(TAG, "Value is: " + value);
        }

        @Override
        public void onCancelled(DatabaseError error) {
            // Failed to read value
            Log.w(TAG, "Failed to read value.", error.toException());
        }
    };
}
