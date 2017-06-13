package com.jastzeonic.emojikeyboard;

import android.content.Context;
import android.inputmethodservice.InputMethodService;
import android.os.IBinder;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * The Emoji IME controller
 * <p>
 * Created by Jast Lai on 2017/03/07.
 */

public class EmojiIme extends InputMethodService {

    protected RecyclerViewAdapter adapter;
    protected String[] items;
    protected RecyclerView recyclerViewEmojiClass;
    protected RecyclerView recyclerViewEmojiContent;
    protected ImageView imageViewBackSpace;
    protected ImageView imageViewClearLine;
    protected ImageView imageViewSpaceBar;
    protected ImageView imageViewGlobal;
    protected View rootView;
    protected TextView textViewComma;

    @Override
    public View onCreateInputView() {

        View view = getLayoutInflater().inflate(R.layout.layout_input_method_editor, null);
        initView(view);
        recyclerViewEmojiContent.setLayoutManager(new GridLayoutManager(this, 3));

        items = getResources().getStringArray(R.array.happy);
//        FirebaseApp.initializeApp(this);
//        FirebaseDatabase database = FirebaseDatabase.getInstance();
//        DatabaseReference myRef = database.getReference("message");
//
//
//        // Read from the database
//        myRef.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//                // This method is called once with the initial value and again
//                // whenever data at this location is updated.
//                String value = dataSnapshot.getValue(String.class);
//                Log.d("EmojiIme", "Value is: " + value);
//            }
//
//            @Override
//            public void onCancelled(DatabaseError error) {
//                // Failed to read value
//                Log.w("EmojiIme", "Failed to read value.", error.toException());
//            }
//        });
//

        adapter = new RecyclerViewAdapter(this, items, recyclerViewEmojiContent);
        adapter.setOnItemClickListener(onItemClickListener);

        recyclerViewEmojiContent.setAdapter(adapter);

        return view;
    }


    View.OnClickListener buttonOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.image_view_back_space:
                    getCurrentInputConnection().deleteSurroundingText(1, 1);
                    break;
                case R.id.image_view_clear_line:
                    getCurrentInputConnection().deleteSurroundingText(getMaxWidth(), 0);
                    break;
                case R.id.image_view_space_bar:
                    getCurrentInputConnection().commitText(" ", 1);
                    break;
                case R.id.image_view_global:
                    InputMethodManager imm = (InputMethodManager) EmojiIme.this.getSystemService(Context.INPUT_METHOD_SERVICE);
                    try {
                        final IBinder token = EmojiIme.this.getWindow().getWindow().getAttributes().token;
                        imm.switchToLastInputMethod(token);
                    } catch (NullPointerException e) {
                        e.printStackTrace();
                        imm.showInputMethodPicker();
                    }

                    break;
                case R.id.text_view_comma:
                    getCurrentInputConnection().commitText(getString(R.string.comma), 1);
                    break;
            }

        }
    };


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
        imageViewClearLine = (ImageView) rootView.findViewById(R.id.image_view_clear_line);
        imageViewSpaceBar = (ImageView) rootView.findViewById(R.id.image_view_space_bar);
        imageViewGlobal = (ImageView) rootView.findViewById(R.id.image_view_global);
        textViewComma = (TextView) rootView.findViewById(R.id.text_view_comma);


        imageViewBackSpace.setOnClickListener(buttonOnClickListener);
        imageViewClearLine.setOnClickListener(buttonOnClickListener);
        imageViewSpaceBar.setOnClickListener(buttonOnClickListener);
        imageViewGlobal.setOnClickListener(buttonOnClickListener);
        textViewComma.setOnClickListener(buttonOnClickListener);

    }
}
