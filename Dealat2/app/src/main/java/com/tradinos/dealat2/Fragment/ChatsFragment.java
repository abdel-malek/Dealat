package com.tradinos.dealat2.Fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.tradinos.dealat2.R;

/**
 * Created by developer on 14.03.18.
 */

public class ChatsFragment extends Fragment {

    public static ChatsFragment newInstance(){
        ChatsFragment fragment = new ChatsFragment();

        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.content_building, null);


        return rootView;
    }
}
