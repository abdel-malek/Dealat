package com.tradinos.dealat2.View;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.tradinos.core.network.SuccessCallback;
import com.tradinos.dealat2.Adapter.BookmarkAdapter;
import com.tradinos.dealat2.Controller.UserController;
import com.tradinos.dealat2.Model.Bookmark;
import com.tradinos.dealat2.Model.Category;
import com.tradinos.dealat2.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by developer on 26.03.18.
 */

public class BookmarksActivity extends MasterActivity {

    private List<Bookmark> bookmarks = new ArrayList<>();
    //views
    private SwipeRefreshLayout refreshLayout;
    private TextView layoutEmpty;
    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_bookmarks);
        super.onCreate(savedInstanceState);
    }

    @Override
    public void getData() {
        if (!refreshLayout.isRefreshing())
            ShowProgressDialog();

        UserController.getInstance(mController).getBookmarks(new SuccessCallback<List<Bookmark>>() {
            @Override
            public void OnSuccess(List<Bookmark> result) {
                HideProgressDialog();
                refreshLayout.setRefreshing(false);

                if (result.isEmpty())
                    layoutEmpty.setVisibility(View.VISIBLE);
                else
                    layoutEmpty.setVisibility(View.GONE);

                bookmarks.clear();
                bookmarks.addAll(result);
                listView.setAdapter(new BookmarkAdapter(mContext, bookmarks));
            }
        });
    }

    @Override
    public void showData() {

    }

    @Override
    public void assignUIReferences() {
        refreshLayout = (SwipeRefreshLayout) findViewById(R.id.refreshLayout);
        layoutEmpty = (TextView) findViewById(R.id.layoutEmpty);
        listView = (ListView) findViewById(R.id.listView);
    }

    @Override
    public void assignActions() {
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshLayout.setRefreshing(true);
                getData();
            }
        });
    }

    @Override
    public void onClick(final View view) {
        final int position = Integer.parseInt(view.getTag().toString());

        switch (view.getId()) {
            case R.id.buttonFalse:

                DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case DialogInterface.BUTTON_POSITIVE:
                                ShowProgressDialog();
                                UserController.getInstance(mController).deleteBookmark(bookmarks.get(position).getId(), new SuccessCallback<String>() {
                                    @Override
                                    public void OnSuccess(String result) {
                                        bookmarks.remove(position);
                                        ((BookmarkAdapter) listView.getAdapter()).notifyDataSetChanged();

                                        if (bookmarks.isEmpty())
                                            layoutEmpty.setVisibility(View.VISIBLE);

                                        HideProgressDialog();
                                        showMessageInToast("deleted");
                                    }
                                });
                        }
                    }
                };

                AlertDialog.Builder builder = new AlertDialog.Builder(mContext, AlertDialog.THEME_HOLO_LIGHT);
                builder.setMessage(R.string.areYouSureDeleteBookmark).setPositiveButton(getString(R.string.yes), dialogClickListener)
                        .setNegativeButton(getString(R.string.no), dialogClickListener).show();
                break;

            case R.id.buttonTrue:

                Intent intent = new Intent(mContext, ViewAdsActivity.class);

                Category mainCategory = Category.getMain(getString(R.string.allCategories));

                intent.putExtra("action", ViewAdsActivity.ACTION_BOOKMARK);
                intent.putExtra("category", mainCategory);
                intent.putExtra("bookmarkId", bookmarks.get(position).getId());

                startActivity(intent);
                break;
        }
    }
}
