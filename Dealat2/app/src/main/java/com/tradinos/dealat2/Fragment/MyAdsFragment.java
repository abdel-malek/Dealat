package com.tradinos.dealat2.Fragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.tradinos.core.network.SuccessCallback;
import com.tradinos.dealat2.Adapter.MyAdAdapter;
import com.tradinos.dealat2.Controller.AdController;
import com.tradinos.dealat2.Controller.UserController;
import com.tradinos.dealat2.Model.Ad;
import com.tradinos.dealat2.R;
import com.tradinos.dealat2.View.AdDetailsActivity;
import com.tradinos.dealat2.View.EditAdActivity;
import com.tradinos.dealat2.View.MasterActivity;
import com.tradinos.dealat2.View.MyProfileActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by developer on 14.03.18.
 */

public class MyAdsFragment extends Fragment {

    private List<Ad> ads = new ArrayList<>();

    SwipeRefreshLayout refreshLayout;
    TextView layoutEmpty;
    ListView listView;


    public static MyAdsFragment newInstance(List<Ad> ads) {
        MyAdsFragment fragment = new MyAdsFragment();

        fragment.setAds(ads);

        return fragment;
    }

    public void setAds(List<Ad> ads) {
        this.ads.clear();
        this.ads.addAll(ads);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_my_profile, null);

        refreshLayout = rootView.findViewById(R.id.refreshLayout);

        layoutEmpty = rootView.findViewById(R.id.layoutEmpty);
        listView = rootView.findViewById(R.id.listView);

        if (ads.isEmpty())
            layoutEmpty.setVisibility(View.VISIBLE);
        else
            listView.setAdapter(new MyAdAdapter(getContext(), ads));

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, final int i, long l) {
                final Dialog adSettingDialog = new Dialog(getContext());
                adSettingDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                adSettingDialog.setContentView(R.layout.popup_ad_settings);

                Button buttonView = adSettingDialog.findViewById(R.id.buttonTrue),
                        buttonEdit = adSettingDialog.findViewById(R.id.buttonEdit),
                        buttonDelete = adSettingDialog.findViewById(R.id.buttonFalse);


                buttonView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(getContext(), AdDetailsActivity.class);
                        intent.putExtra("ad", ads.get(i));
                        getContext().startActivity(intent);
                        adSettingDialog.dismiss();
                    }
                });


                buttonEdit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(getContext(), EditAdActivity.class);
                        intent.putExtra("ad", ads.get(i));
                        getContext().startActivity(intent);
                        adSettingDialog.dismiss();
                    }
                });

                buttonDelete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                switch (which) {
                                    case DialogInterface.BUTTON_POSITIVE:
                                        final MyProfileActivity activity = (MyProfileActivity) getActivity();
                                        activity.ShowProgressDialog();
                                        AdController.getInstance(activity.getController()).changeStatus(ads.get(i).getId(), Ad.DELETED,
                                                new SuccessCallback<String>() {
                                                    @Override
                                                    public void OnSuccess(String result) {

                                                        ads.remove(i);
                                                        ((MyAdAdapter) listView.getAdapter()).notifyDataSetChanged();

                                                        activity.HideProgressDialog();
                                                        activity.showMessageInToast(R.string.toastAdDeleted);
                                                        adSettingDialog.dismiss();
                                                    }
                                                });

                                        break;
                                }
                            }
                        };

                        AlertDialog.Builder builder = new AlertDialog.Builder(getContext(), AlertDialog.THEME_HOLO_LIGHT);
                        builder.setMessage(R.string.areYouSureDeleteAd).setPositiveButton(getResources().getString(R.string.yes), dialogClickListener)
                                .setNegativeButton(getResources().getString(R.string.no), dialogClickListener).show();
                    }
                });

                adSettingDialog.show();
            }
        });

        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshLayout.setRefreshing(true);

                MasterActivity activity = (MasterActivity) getActivity();

                UserController.getInstance(activity.getController()).getMyAds(new SuccessCallback<List<Ad>>() {
                    @Override
                    public void OnSuccess(List<Ad> result) {
                        refreshLayout.setRefreshing(false);

                        if (result.isEmpty())
                            layoutEmpty.setVisibility(View.VISIBLE);
                        else
                            layoutEmpty.setVisibility(View.GONE);

                        setAds(result);
                        listView.setAdapter(new MyAdAdapter(getContext(), ads));
                    }
                });
            }
        });

        return rootView;
    }
}
