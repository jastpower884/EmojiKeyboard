package com.jastzeonic.emojikeyboard;

import android.inputmethodservice.InputMethodService;
import android.os.IBinder;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.inputmethod.ExtractedTextRequest;
import android.view.inputmethod.InputConnection;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;

import com.jastzeonic.emojikeyboard.item.TypeItem;

import java.util.ArrayList;
import java.util.List;

/**
 * The Emoji IME controller
 * <p>
 * Created by Jast Lai on 2017/03/07.
 */

public class EmojiIME extends InputMethodService implements View.OnClickListener {


    protected RecyclerView mRecyclerViewType;
    protected RecyclerView mRecyclerViewContent;

    protected ImageView mImageViewDelete;
    protected ImageView mImageViewGlobal;
    protected ImageView mImageViewSpaceBar;
    protected ImageView mImageViewImageViewClearLine;

    protected RecyclerViewTypeAdapter mRecyclerViewTypeAdapter;
    protected RecyclerViewAdapter mRecyclerViewContentAdapter;


    protected String[][] items;
    protected List<TypeItem> emojiTypeList;

    protected int focusPosition;

    @Override
    public View onCreateInputView() {

        View view = getLayoutInflater().inflate(R.layout.layout_input_method_editor, null);
        mRecyclerViewType = (RecyclerView) view.findViewById(R.id.recycler_view_type);
        mRecyclerViewContent = (RecyclerView) view.findViewById(R.id.recycler_view_content);
        mImageViewDelete = (ImageView) view.findViewById(R.id.image_view_delete);
        mImageViewGlobal = (ImageView) view.findViewById(R.id.image_view_global);
        mImageViewSpaceBar = (ImageView) view.findViewById(R.id.image_view_space_bar);
        mImageViewImageViewClearLine = (ImageView) view.findViewById(R.id.image_view_clear_line);

        mRecyclerViewContent.setLayoutManager(new GridLayoutManager(this, 3));
        mRecyclerViewType.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));

        String[] emojiType = getResources().getStringArray(R.array.emoji_type);

        emojiTypeList = new ArrayList<>();

        for (String content : emojiType) {

            TypeItem item = new TypeItem();
            item.setContent(content);
            item.setForcus(false);
            emojiTypeList.add(item);

        }
        emojiTypeList.get(0).setForcus(true);


        mRecyclerViewTypeAdapter = new RecyclerViewTypeAdapter(this, emojiTypeList, mRecyclerViewContent);
        mRecyclerViewTypeAdapter.setOnItemClickListener(onTypeItemClickListener);

        items = new String[emojiType.length][];
        items[0] = getResources().getStringArray(R.array.happy);
        items[1] = getResources().getStringArray(R.array.layzy);
        items[2] = getResources().getStringArray(R.array.shock);


        mRecyclerViewContentAdapter = new RecyclerViewAdapter(this, items[0], mRecyclerViewContent);
        mRecyclerViewContentAdapter.setOnItemClickListener(onItemClickListener);


        mRecyclerViewContent.setAdapter(mRecyclerViewContentAdapter);
        mRecyclerViewType.setAdapter(mRecyclerViewTypeAdapter);

        mImageViewDelete.setOnClickListener(this);
        mImageViewGlobal.setOnClickListener(this);
        mImageViewSpaceBar.setOnClickListener(this);
        mImageViewImageViewClearLine.setOnClickListener(this);

        return view;
    }


    protected RecyclerViewAdapter.OnItemClickListener onItemClickListener = new RecyclerViewAdapter.OnItemClickListener() {
        @Override
        public boolean onItemClick(int position, View view) {

            getCurrentInputConnection().commitText(items[focusPosition][position], 1);

            return false;
        }
    };


    protected RecyclerViewTypeAdapter.OnItemClickListener onTypeItemClickListener = new RecyclerViewTypeAdapter.OnItemClickListener() {
        @Override
        public boolean onItemClick(int position, View view) {

            emojiTypeList.get(focusPosition).setForcus(false);
            mRecyclerViewTypeAdapter.notifyItemChanged(focusPosition);
            emojiTypeList.get(position).setForcus(true);
            mRecyclerViewTypeAdapter.notifyItemChanged(position);
//            getCurrentInputConnection().commitText(items[position], 1);
            focusPosition = position;
            mRecyclerViewContentAdapter.setItems(items[position]);
            mRecyclerViewContentAdapter.notifyDataSetChanged();

            return false;
        }
    };

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.image_view_delete:
                getCurrentInputConnection().deleteSurroundingText(1, 0);
                break;
            case R.id.image_view_global:
                InputMethodManager imeManager = (InputMethodManager) getApplicationContext().getSystemService(INPUT_METHOD_SERVICE);
//                get

//                imeManager.switchToNextInputMethod(getWindow().getOwnerActivity().getApplicationWindowToken(), false);
//                imeManager.switchToNextInputMethod(kv.getWindowToken(), false /* onlyCurrentIme */);

//                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                IBinder token = this.getWindow().getWindow().getAttributes().token;
//                    imeManager.switchToNextInputMethod(token, false);
                imeManager.switchToLastInputMethod(token);
//                }
//                imeManager.showInputMethodPicker();
                break;
            case R.id.image_view_space_bar:

                getCurrentInputConnection().commitText(" ", 1);
                break;
            case R.id.image_view_clear_line:
                InputConnection inputConnection = getCurrentInputConnection();
                CharSequence currentText = inputConnection.getExtractedText(new ExtractedTextRequest(), 0).text;
                CharSequence beforCursorText = inputConnection.getTextBeforeCursor(currentText.length(), 0);
                CharSequence afterCursorText = inputConnection.getTextAfterCursor(currentText.length(), 0);
                inputConnection.deleteSurroundingText(beforCursorText.length(), afterCursorText.length());
                break;

        }
    }
}
