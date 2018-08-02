package com.dealat.View;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.dealat.Adapter.BookmarkPagingAdapter;
import com.dealat.Controller.UserController;
import com.dealat.Model.Bookmark;
import com.dealat.Model.Category;
import com.dealat.R;
import com.dealat.Utils.CustomAlertDialog;
import com.dealat.Utils.PaginationScrollListener;
import com.tradinos.core.network.SuccessCallback;

import java.util.List;

/**
 * Created by developer on 26.03.18.
 */

public class BookmarksActivity extends MasterActivity {

    public final int PAGE_SIZE = 20;

    // Indicates if footer ProgressBar is shown (i.e. next page is loading)
    private boolean isLoading = false;

    // If current page is the last page (Pagination will stop after this page load)
    private boolean isLastPage = false;

    // indicates the current page which Pagination is fetching.
    private int currentPage = 1; // first is called in MyProfileActivity

    //private List<Bookmark> bookmarks = new ArrayList<>();
    //views
    private GridLayoutManager gridLayoutManager;
    private BookmarkPagingAdapter pagingAdapter;

    private SwipeRefreshLayout refreshLayout;
    private TextView layoutEmpty;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_bookmarks);
        super.onCreate(savedInstanceState);
    }

    @Override
    public void getData() {
        gridLayoutManager = new GridLayoutManager(mContext, 1);
        pagingAdapter = new BookmarkPagingAdapter(mContext);

        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.setAdapter(pagingAdapter);

        getBookmarks();
    }

    @Override
    public void showData() {

    }

    @Override
    public void assignUIReferences() {
        refreshLayout = findViewById(R.id.refreshLayout);
        layoutEmpty = findViewById(R.id.layoutEmpty);
        recyclerView = findViewById(R.id.recyclerView);
    }

    @Override
    public void assignActions() {
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshLayout.setRefreshing(true);
                pagingAdapter.clear();
                currentPage = 1;
                isLastPage = false;

                getBookmarks();
            }
        });

        recyclerView.addOnScrollListener(new PaginationScrollListener(gridLayoutManager) {
            @Override
            protected void loadMoreItems() {
                isLoading = true;
                currentPage += 1; //Increment page index to load the next one

                getBookmarks();
            }

            @Override
            public boolean isLastPage() {
                return isLastPage;
            }

            @Override
            public boolean isLoading() {
                return isLoading;
            }
        });
    }

    @Override
    public void onClick(final View view) {
        final int position = Integer.parseInt(view.getTag().toString());

        switch (view.getId()) {
            case R.id.buttonFalse:

                final CustomAlertDialog dialog = new CustomAlertDialog(mContext, getString(R.string.areYouSureDeleteSearch));
                dialog.show();

                dialog.getButtonTrue().setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        ShowProgressDialog();
                        UserController.getInstance(mController).deleteBookmark(pagingAdapter.getItem(position).getId(), new SuccessCallback<String>() {
                            @Override
                            public void OnSuccess(String result) {
                                HideProgressDialog();

                                pagingAdapter.removeItem(position);

                                if (pagingAdapter.getItemCount() == 0)
                                    layoutEmpty.setVisibility(View.VISIBLE);

                                showMessageInToast(R.string.toastUnBookmark);
                                dialog.dismiss();
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
                intent.putExtra("bookmarkId", pagingAdapter.getItem(position).getId());

                startActivity(intent);
                break;
        }
    }

    private void getBookmarks() {
        if (currentPage == 1 && !refreshLayout.isRefreshing())
            ShowProgressDialog();

        UserController.getInstance(mController).getBookmarks(currentPage, PAGE_SIZE, new SuccessCallback<List<Bookmark>>() {
            @Override
            public void OnSuccess(List<Bookmark> result) {
                HideProgressDialog();
                refreshLayout.setRefreshing(false);

                pagingAdapter.removeLoadingFooter();
                isLoading = false;

                pagingAdapter.addAll(result);

                if (pagingAdapter.getItemCount() == 0)
                    layoutEmpty.setVisibility(View.VISIBLE);
                else
                    layoutEmpty.setVisibility(View.GONE);

                if (result.size() < PAGE_SIZE)
                    isLastPage = true;
                else
                    pagingAdapter.addLoadingFooter();
            }
        });
    }
}
