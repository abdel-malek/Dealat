package com.tradinos.dealat2.View;

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
import com.tradinos.dealat2.Utils.CustomAlertDialog;

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

                CustomAlertDialog dialog = new CustomAlertDialog(mContext, getString(R.string.areYouSureDeleteSearch));
                dialog.show();

                dialog.getButtonTrue().setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        ShowProgressDialog();
                        UserController.getInstance(mController).deleteBookmark(bookmarks.get(position).getId(), new SuccessCallback<String>() {
                            @Override
                            public void OnSuccess(String result) {
                                HideProgressDialog();

                                bookmarks.remove(position);
                                ((BookmarkAdapter) listView.getAdapter()).notifyDataSetChanged();

                                if (bookmarks.isEmpty())
                                    layoutEmpty.setVisibility(View.VISIBLE);

                                showMessageInToast(R.string.toastUnBookmark);
                            }
                        });
                    }
                });

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
