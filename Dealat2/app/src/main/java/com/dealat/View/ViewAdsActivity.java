package com.dealat.View;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.dealat.Adapter.AdPagingAdapter;
import com.dealat.Controller.AdController;
import com.dealat.Controller.UserController;
import com.dealat.Model.Ad;
import com.dealat.Model.Category;
import com.dealat.Model.GroupedResponse;
import com.dealat.Model.User;
import com.dealat.MyApplication;
import com.dealat.R;
import com.dealat.Utils.PaginationScrollListener;
import com.tradinos.core.network.SuccessCallback;

import java.util.HashMap;
import java.util.List;

/**
 * Created by developer on 21.02.18.
 */

public class ViewAdsActivity extends DrawerActivity {

    public static final int ACTION_SEARCH = 15, ACTION_VIEW = 16, ACTION_BOOKMARK = 17;
    public final int PAGE_SIZE = 20;

    // Indicates if footer ProgressBar is shown (i.e. next page is loading)
    private boolean isLoading = false;

    // If current page is the last page (Pagination will stop after this page load)
    private boolean isLastPage = false;

    // indicates the current page which Pagination is fetching.
    private int currentPage = 1;

    private int currentView, action;
    private String bookmarkId;

    private Category selectedCategory;
    private int currentTemplate;
    private HashMap<String, String> searchParameters = new HashMap<>();

    private AdPagingAdapter pagingAdapter;

    // views
    private GridLayoutManager gridLayoutManager;
    private RecyclerView recyclerView;
    private ImageButton buttonViews, buttonFav;
    private ImageView imageViewCategory;
    private TextView textViewCategory, textViewCount;
    private SwipeRefreshLayout refreshLayout;
    private SearchView searchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_view_ads);
        super.onCreate(savedInstanceState);
    }

    @Override
    public void getData() {
        // if preference isn't exist, the default view is 1
        currentView = ((MyApplication) getApplication()).getCurrentView();
        gridLayoutManager = new GridLayoutManager(mContext, 1);
        pagingAdapter = new AdPagingAdapter(mContext, getGridCellResource());

        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.setAdapter(pagingAdapter);

        selectedCategory = (Category) getIntent().getSerializableExtra("category");
        currentTemplate = selectedCategory.getTemplateId();

        action = getIntent().getIntExtra("action", 0);

        if (action == ACTION_SEARCH)
            searchParameters = (HashMap<String, String>) getIntent().getSerializableExtra("parameters");

        getAds();
    }

    private void getAds() {

        if (currentPage == 1 && !refreshLayout.isRefreshing())
            ShowProgressDialog();

        if (bookmarkId == null)
            buttonFav.setImageDrawable(ContextCompat.getDrawable(mContext, R.drawable.ic_star_border_white_24dp));

        switch (action) {
            case ACTION_SEARCH:
                AdController.getInstance(mController).search(currentPage, PAGE_SIZE, searchParameters, new SuccessCallback<GroupedResponse>() {
                    @Override
                    public void OnSuccess(GroupedResponse result) {

                        showResult(result.getAds());

                        if (currentPage == 1)  // commercials are sent with first page only
                            getCommercialAds(result.getCommercialAds());
                    }
                });

                break;
            case ACTION_VIEW:

                AdController.getInstance(mController).getCategoryAds(currentPage, PAGE_SIZE, selectedCategory.getId(), new SuccessCallback<GroupedResponse>() {
                    @Override
                    public void OnSuccess(GroupedResponse result) {

                        showResult(result.getAds());

                        if (currentPage == 1)  // commercials are sent with first page only
                            getCommercialAds(result.getCommercialAds());
                    }
                });
                break;

            case ACTION_BOOKMARK:
                UserController.getInstance(mController).getBookmarkAds(currentPage, PAGE_SIZE, getIntent().getStringExtra("bookmarkId"), new SuccessCallback<GroupedResponse>() {
                    @Override
                    public void OnSuccess(GroupedResponse result) {

                        showResult(result.getAds());

                        if (currentPage == 1)  // commercials are sent with first page only
                            getCommercialAds(result.getCommercialAds());
                    }
                });
                break;
        }
    }

    private void showResult(List<Ad> result) {
        HideProgressDialog();
        if (findViewById(R.id.refreshLayout) != null)
            ((SwipeRefreshLayout) findViewById(R.id.refreshLayout)).setRefreshing(false);

        pagingAdapter.removeLoadingFooter();
        isLoading = false;

        pagingAdapter.addAll(result);

        if (pagingAdapter.getItemCount() == 0) {
            findViewById(R.id.layoutEmpty).setVisibility(View.VISIBLE);
            textViewCount.setText("");

        } else {
            findViewById(R.id.layoutEmpty).setVisibility(View.GONE);
            textViewCount.setText(formattedNumber(pagingAdapter.getItemCount()) + " " + getString(R.string.ad));
        }

        if (result.size() < PAGE_SIZE)
            isLastPage = true;
        else
            pagingAdapter.addLoadingFooter();
    }

    @Override
    public void showData() {

        imageViewCategory.setImageDrawable(ContextCompat.getDrawable(mContext,
                getTemplateDefaultImage(selectedCategory.getTemplateId())));

        textViewCategory.setText(selectedCategory.getFullName());

        buttonViews.setImageDrawable(ContextCompat.getDrawable(mContext, getButtonViewsResource()));

        if (MyApplication.getUserState() == User.REGISTERED)
            buttonFav.setVisibility(View.VISIBLE);
    }

    @Override
    public void assignUIReferences() {
        recyclerView = findViewById(R.id.recyclerView);
        buttonViews = findViewById(R.id.buttonViews);
        buttonFav = findViewById(R.id.buttonFav);
        imageViewCategory = findViewById(R.id.imageView);
        textViewCategory = findViewById(R.id.textView);
        textViewCount = findViewById(R.id.textViewCount);
        refreshLayout = findViewById(R.id.refreshLayout);
        searchView = findViewById(R.id.searchView);
    }

    @Override
    public void assignActions() {
        buttonViews.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                currentView++;
                if (currentView > 3)
                    currentView = 1;

                ((MyApplication) getApplication()).setCurrentView(currentView);

                buttonViews.setImageDrawable(ContextCompat.getDrawable(mContext, getButtonViewsResource()));

                pagingAdapter.setResourceLayout(getGridCellResource());
                recyclerView.setAdapter(pagingAdapter); // to enforce re-creating views
            }
        });

        searchView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                searchView.setIconified(false);
            }
        });

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                searchParameters.clear();
                searchParameters.put("query", query);

                if (!selectedCategory.isMain()) {
                    searchParameters.put("category_id", selectedCategory.getId());
                    searchParameters.put(getString(R.string.categoryName), selectedCategory.getFullName());
                }

                bookmarkId = null;
                Animation mAnimation = new AlphaAnimation(0.0f, 1.0f);
                mAnimation.setDuration(800);
                buttonFav.startAnimation(mAnimation);
                buttonFav.setImageDrawable(ContextCompat.getDrawable(mContext, R.drawable.ic_star_border_white_24dp));

                action = ACTION_SEARCH;
                pagingAdapter.clear();
                currentPage = 1;
                isLastPage = false;

                getAds();
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if (newText.isEmpty()) {
                    searchView.setIconified(true);
                    searchParameters.remove("query");
                    bookmarkId = null;
                }

                return false;
            }
        });

        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshLayout.setRefreshing(true);
                pagingAdapter.clear();
                currentPage = 1;
                isLastPage = false;

                getAds();
            }
        });

        buttonFav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (bookmarkId == null) {
                    if (!searchParameters.isEmpty()) {
                        ShowProgressDialog();
                        UserController.getInstance(mController).bookmarkSearch(searchParameters, new SuccessCallback<String>() {
                            @Override
                            public void OnSuccess(String result) {
                                HideProgressDialog();
                                showMessageInToast(getString(R.string.toastBookmark));
                                bookmarkId = result;

                                Animation mAnimation = new AlphaAnimation(0.0f, 1.0f);
                                mAnimation.setDuration(800);
                                buttonFav.startAnimation(mAnimation);
                                buttonFav.setImageDrawable(ContextCompat.getDrawable(mContext, R.drawable.ic_star_white_24dp));
                            }
                        });
                    }
                } else {
                    ShowProgressDialog();
                    UserController.getInstance(mController).deleteBookmark(bookmarkId, new SuccessCallback<String>() {
                        @Override
                        public void OnSuccess(String result) {
                            HideProgressDialog();
                            showMessageInToast(R.string.toastUnBookmark);
                            bookmarkId = null;

                            Animation mAnimation = new AlphaAnimation(0.0f, 1.0f);
                            mAnimation.setDuration(800);
                            buttonFav.startAnimation(mAnimation);
                            buttonFav.setImageDrawable(ContextCompat.getDrawable(mContext, R.drawable.ic_star_border_white_24dp));
                        }
                    });
                }
            }
        });

        recyclerView.addOnScrollListener(new PaginationScrollListener(gridLayoutManager) {
            @Override
            protected void loadMoreItems() {
                isLoading = true;
                currentPage += 1; //Increment page index to load the next one

                getAds();
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
    public void onClick(View view) {
        if (view.getId() == R.id.buttonFilter) { //Filter
            Intent intent = new Intent(mContext, FilterActivity.class);
            intent.putExtra("category", selectedCategory);

            startActivityForResult(intent, ACTION_SEARCH);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK)
            if (requestCode == ACTION_SEARCH) {
                selectedCategory = (Category) data.getSerializableExtra("category");
                searchParameters = (HashMap<String, String>) data.getSerializableExtra("parameters");
                bookmarkId = null;
                buttonFav.setImageDrawable(ContextCompat.getDrawable(mContext, R.drawable.ic_star_border_white_24dp));
                searchView.setQuery("", false);
                searchView.setIconified(true);

                if (currentTemplate != selectedCategory.getTemplateId()) {
                    imageViewCategory.setImageDrawable(ContextCompat.getDrawable(mContext,
                            getTemplateDefaultImage(selectedCategory.getTemplateId())));

                    currentTemplate = selectedCategory.getTemplateId();
                }
                textViewCategory.setText(selectedCategory.getFullName());

                action = ACTION_SEARCH;
                pagingAdapter.clear();
                currentPage = 1;
                isLastPage = false;
                getAds();
            }
    }

    private int getButtonViewsResource() {

        switch (currentView) {
            case 1:
                return R.drawable.views;

            case 2:
                return R.drawable.views_2;

            case 3:
                return R.drawable.views_3;

            default:
                return R.drawable.views;
        }
    }

    private int getGridCellResource() {
        switch (currentView) {
            case 1:
                gridLayoutManager.setSpanCount(1);
                return R.layout.row_view1;

            case 2:
                gridLayoutManager.setSpanCount(1);
                return R.layout.row_view2;

            case 3:
                gridLayoutManager.setSpanCount(2);
                return R.layout.row_view3;

            default:
                gridLayoutManager.setSpanCount(1);
                return R.layout.row_view1;
        }
    }
}
