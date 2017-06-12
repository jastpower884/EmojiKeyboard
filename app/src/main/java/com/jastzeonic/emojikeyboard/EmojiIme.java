package com.jastzeonic.emojikeyboard;

import android.inputmethodservice.InputMethodService;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

/**
 * The Emoji IME controller
 * <p>
 * Created by Jast Lai on 2017/03/07.
 */

public class EmojiIme extends InputMethodService {


    protected RecyclerView mRecyclerView;

    protected RecyclerViewAdapter adapter;

    protected String[] items;
    protected RecyclerView recyclerViewEmojiClass;
    protected RecyclerView recyclerViewEmojiContent;
    protected ImageView imageViewBackSpace;

    @Override
    public View onCreateInputView() {

        View view = getLayoutInflater().inflate(R.layout.layout_input_method_editor, null);
        initView(view);
        mRecyclerView.setLayoutManager(new GridLayoutManager(this, 3));

        items = getResources().getStringArray(R.array.happy);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("message");


        // Read from the database
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                String value = dataSnapshot.getValue(String.class);
                Log.d("EmojiIme", "Value is: " + value);
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w("EmojiIme", "Failed to read value.", error.toException());
            }
        });


        adapter = new RecyclerViewAdapter(this, items, mRecyclerView);
        adapter.setOnItemClickListener(onItemClickListener);

        mRecyclerView.setAdapter(adapter);

        return view;
    }


    protected RecyclerViewAdapter.OnItemClickListener onItemClickListener = new RecyclerViewAdapter.OnItemClickListener() {
        @Override
        public boolean onItemClick(int position, View view) {

            getCurrentInputConnection().commitText(items[position], 1);

            return false;
        }
    };

    private void initView(View rootView) {
        recyclerViewEmojiClass = (RecyclerView) rootView.findViewById(R.id.recycler_view_emoji_class);
        recyclerViewEmojiContent = (RecyclerView) rootView.findViewById(R.id.recycler_view_emoji_content);
        imageViewBackSpace = (ImageView) rootView.findViewById(R.id.image_view_back_space);
    }
}
