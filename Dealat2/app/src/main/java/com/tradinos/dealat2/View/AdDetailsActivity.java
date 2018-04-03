package com.tradinos.dealat2.View;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.tradinos.core.network.InternetManager;
import com.tradinos.core.network.SuccessCallback;
import com.tradinos.dealat2.Adapter.AdImagesAdapter;
import com.tradinos.dealat2.Controller.AdController;
import com.tradinos.dealat2.Controller.CurrentAndroidUser;
import com.tradinos.dealat2.Controller.UserController;
import com.tradinos.dealat2.Model.Ad;
import com.tradinos.dealat2.Model.AdElectronic;
import com.tradinos.dealat2.Model.AdFashion;
import com.tradinos.dealat2.Model.AdIndustry;
import com.tradinos.dealat2.Model.AdJob;
import com.tradinos.dealat2.Model.AdKid;
import com.tradinos.dealat2.Model.AdMobile;
import com.tradinos.dealat2.Model.AdProperty;
import com.tradinos.dealat2.Model.AdSport;
import com.tradinos.dealat2.Model.AdVehicle;
import com.tradinos.dealat2.Model.Category;
import com.tradinos.dealat2.Model.Chat;
import com.tradinos.dealat2.MyApplication;
import com.tradinos.dealat2.R;

/**
 * Created by developer on 01.03.18.
 */

public class AdDetailsActivity extends MasterActivity {

    private final int REQUEST_CALL = 6;

    private boolean enabled = false;

    private CurrentAndroidUser user;
    private Ad currentAd;

    // textViewViews
    private ViewPager viewPager;
    private TabLayout tabLayout;

    private TextView textViewId, textViewTitle, textViewTitle2, textViewPrice, textNegotiable, textViewDesc,
            textViewSeller, textViewPhone,
            textViewViews, textViewPublishDate, textViewLocation, textViewCity, textViewExpires,
            textViewEdu, textViewSch, textViewSalary, textViewEx,
            textViewSpace, textViewRooms, textViewFloors, textViewFurn, textViewState,
            textViewBrand, textViewModel, textViewKilo, textViewTransmission, textViewManufactureYear,
            textViewUsage, textViewSize;

    private ImageButton buttonFav;
    private RatingBar ratingBar;

    private LinearLayout containerJob, containerProperty, containerVehicle, containerBrand, containerUsage, containerSize;
    private View line1, line2, line3;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_ad_details);
        super.onCreate(savedInstanceState);
    }

    @Override
    public void getData() {

        user = new CurrentAndroidUser(mContext);
        currentAd = (Ad) getIntent().getSerializableExtra("ad");

        getAd();
    }

    private void getAd() {
        ShowProgressDialog();
        AdController.getInstance(mController).getAdDetails(currentAd.getId(), currentAd.getTemplate(), new SuccessCallback<Ad>() {
            @Override
            public void OnSuccess(Ad result) {
                enabled = true;

                currentAd.setSellerId(result.getSellerId());
                currentAd.setSellerPhone(result.getSellerPhone());
                currentAd.setSellerName(result.getSellerName());
                currentAd.setFavorite(result.isFavorite());
                currentAd.setImagesPaths(result.getImagesPaths());

                viewPager.setAdapter(new AdImagesAdapter(getSupportFragmentManager(), result.getImagesPaths(), currentAd.getTemplate()));

                tabLayout.setupWithViewPager(viewPager);

                LinearLayout tabStrip = (LinearLayout) tabLayout.getChildAt(0);

                for (int i = 0; i < result.getImagesPaths().size(); i++) {
                    View view = LayoutInflater.from(mContext).inflate(R.layout.row_tab_image, null);

                    ImageView imageView = view.findViewById(R.id.imageView);

                    int defaultDrawable = getTemplateDefaultImage(currentAd.getTemplate());

                    if (result.getImagePath(i) != null) {
                        ImageLoader mImageLoader = InternetManager.getInstance(mContext).getImageLoader();
                        mImageLoader.get(MyApplication.getBaseUrlForImages() + result.getImagePath(i), ImageLoader.getImageListener(imageView,
                                defaultDrawable, defaultDrawable));
                    }
                    tabLayout.getTabAt(i).setCustomView(view);

                    View tab = tabStrip.getChildAt(i);
                    tab.setPadding(0, 0, 0, 0);
                    ViewGroup.MarginLayoutParams p = (ViewGroup.MarginLayoutParams) tab.getLayoutParams();
                    p.setMargins(4, 4, 4, 4);
                    tab.requestLayout();
                }

                // filling data
                textViewId.setText(result.getFormattedId());
                textViewTitle.setText(result.getTitle());
                textViewTitle2.setText(result.getTitle());
                textViewPublishDate.setText(formattedDate(result.getPublishDate()));
                textViewPrice.setText(formattedNumber(result.getPrice()) + " " + getString(R.string.sp));
                textViewCity.setText(result.getCityName());

                if (result.getLocationId() != null){
                    textViewLocation.setText(result.getLocationName());
                    findViewById(R.id.line6).setVisibility(View.VISIBLE);
                    findViewById(R.id.containerLocation).setVisibility(View.VISIBLE);
                }

                textViewExpires.setText(getExpiryTime(result.getPublishDate(), result.getShowPeriod()));
                textViewSeller.setText(result.getSellerName());

                // SpannableString content = new SpannableString(result.getSellerPhone());
                // content.setSpan(new UnderlineSpan(), 0, result.getSellerPhone().length(), 0);
                textViewPhone.setText(result.getSellerPhone());

                if (result.isNegotiable())
                    textNegotiable.setText(getString(R.string.yes));
                else
                    textNegotiable.setText(getString(R.string.no));

                textViewDesc.setText(result.getDescription());

                HideProgressDialog();
                fillTemplate(result);
                showTemplate();

                if (user.IsLogged()) {
                    textViewPhone.setVisibility(View.VISIBLE);

                    if (currentAd.getSellerId().equals(user.Get().getId())) { // view info for Seller

                        ((TextView) findViewById(R.id.textViewStatus)).setText(getStringStatus(currentAd.getStatus()));
                        findViewById(R.id.container4).setVisibility(View.VISIBLE);

                        if (currentAd.isRejected()) {
                            ((TextView) findViewById(R.id.textViewNote)).setText(currentAd.getRejectNote());
                            findViewById(R.id.container5).setVisibility(View.VISIBLE);
                        }

                    } else { // user cannot contact themselves
                        if (currentAd.isFavorite())
                            buttonFav.setImageDrawable(ContextCompat.getDrawable(mContext, R.drawable.star));

                        buttonFav.setVisibility(View.VISIBLE);

                        findViewById(R.id.buttonCall).setVisibility(View.VISIBLE);
                        findViewById(R.id.line4).setVisibility(View.VISIBLE);
                        findViewById(R.id.buttonMessage).setVisibility(View.VISIBLE);
                        findViewById(R.id.line5).setVisibility(View.VISIBLE);
                        findViewById(R.id.buttonReport).setVisibility(View.VISIBLE);
                    }
                }
            }
        });
    }

    @Override
    public void showData() {

    }

    @Override
    public void assignUIReferences() {
        viewPager = (ViewPager) findViewById(R.id.viewpager);
        tabLayout = (TabLayout) findViewById(R.id.tab_layout);

        textViewId = (TextView) findViewById(R.id.text2);
        textViewTitle = (TextView) findViewById(R.id.title);
        textViewTitle2 = (TextView) findViewById(R.id.text);
        textViewViews = (TextView) findViewById(R.id.textView);
        textViewPublishDate = (TextView) findViewById(R.id.textViewDate);
        textViewPrice = (TextView) findViewById(R.id.textViewPrice);
        textViewCity = (TextView) findViewById(R.id.textViewCity);
        textViewLocation = (TextView) findViewById(R.id.textLocation);
        textViewExpires = (TextView) findViewById(R.id.textViewExpires);
        textNegotiable = (TextView) findViewById(R.id.textNegotiable);
        textViewDesc = (TextView) findViewById(R.id.textDesc);
        textViewSeller = (TextView) findViewById(R.id.textUser);
        textViewPhone = (TextView) findViewById(R.id.textViewPhone);

        textViewEdu = (TextView) findViewById(R.id.textEdu);
        textViewSch = (TextView) findViewById(R.id.textSch);
        textViewEx = (TextView) findViewById(R.id.textEx);
        textViewSalary = (TextView) findViewById(R.id.textSalary);


        textViewSpace = (TextView) findViewById(R.id.textSpace);
        textViewRooms = (TextView) findViewById(R.id.textRooms);
        textViewFloors = (TextView) findViewById(R.id.textFloor);
        textViewFurn = (TextView) findViewById(R.id.textFurn);
        textViewState = (TextView) findViewById(R.id.textState);


        textViewBrand = (TextView) findViewById(R.id.textBrand);
        textViewModel = (TextView) findViewById(R.id.textModel);
        textViewKilo = (TextView) findViewById(R.id.textKilo);
        textViewManufactureYear = (TextView) findViewById(R.id.textDate);
        textViewTransmission = (TextView) findViewById(R.id.textTransmission);

        textViewSize = (TextView) findViewById(R.id.textSize);

        textViewUsage = (TextView) findViewById(R.id.textUsage);

        containerBrand = (LinearLayout) findViewById(R.id.containerBrand);
        containerUsage = (LinearLayout) findViewById(R.id.containerUsage);
        containerJob = (LinearLayout) findViewById(R.id.containerJob);
        containerProperty = (LinearLayout) findViewById(R.id.containerProperty);
        containerVehicle = (LinearLayout) findViewById(R.id.containerVehicle);
        containerSize = (LinearLayout) findViewById(R.id.containerSize);

        line1 = findViewById(R.id.line1);
        line2 = findViewById(R.id.line2);
        line3 = findViewById(R.id.line3);

        buttonFav = (ImageButton) findViewById(R.id.buttonFav);
        ratingBar = (RatingBar) findViewById(R.id.ratingBar);
    }

    @Override
    public void assignActions() {

    }

    @Override
    public void onClick(View view) {

        if (registered() && user.IsLogged()) {

            switch (view.getId()) {
                case R.id.buttonFav:
                    if (currentAd.isFavorite()) {
                        AdController.getInstance(mController).removeFromFavorite(currentAd.getId(), new SuccessCallback<String>() {
                            @Override
                            public void OnSuccess(String result) {

                                Animation mAnimation = new AlphaAnimation(0.0f, 1.0f);
                                mAnimation.setDuration(800);
                                buttonFav.startAnimation(mAnimation);

                                buttonFav.setImageDrawable(ContextCompat.getDrawable(mContext, R.drawable.star_copy));
                                showMessageInToast(R.string.toastUnfavorite);
                                currentAd.setFavorite(false);
                            }
                        });
                    } else {
                        AdController.getInstance(mController).setAsFavorite(currentAd.getId(), new SuccessCallback<String>() {
                            @Override
                            public void OnSuccess(String result) {

                                Animation mAnimation = new AlphaAnimation(0.0f, 1.0f);
                                mAnimation.setDuration(800);
                                buttonFav.startAnimation(mAnimation);

                                buttonFav.setImageDrawable(ContextCompat.getDrawable(mContext, R.drawable.star));
                                showMessageInToast(R.string.toastFavorite);
                                currentAd.setFavorite(true);
                            }
                        });
                    }

                    break;

                case R.id.buttonCall:

                    if (Build.VERSION.SDK_INT >= 23 && ContextCompat.checkSelfPermission(mContext,
                            Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                        ActivityCompat.requestPermissions(AdDetailsActivity.this,
                                new String[]{Manifest.permission.CALL_PHONE}, REQUEST_CALL);
                    } else {
                        Intent callIntent = new Intent(Intent.ACTION_CALL);
                        callIntent.setData(Uri.parse("tel:" + currentAd.getSellerPhone()));
                        startActivity(callIntent);
                    }

                    break;

                case R.id.buttonMessage:
                    Intent intent = new Intent(mContext, ChatActivity.class);
                    Chat chat = new Chat();
                    chat.setAdId(currentAd.getId());
                    chat.setSellerId(currentAd.getSellerId());
                    chat.setSellerName(currentAd.getSellerName());
                    chat.setUserId(user.Get().getId());
                    chat.setUserName(user.Get().getName());
                    chat.setAdTitle(currentAd.getTitle());

                    intent.putExtra("chat", chat);
                    startActivity(intent);

                    break;

                case R.id.buttonReport:
                    break;

                case R.id.ratingBar:
                    UserController.getInstance(mController).rateSeller(currentAd.getSellerId(), ratingBar.getRating(), new SuccessCallback<String>() {
                        @Override
                        public void OnSuccess(String result) {

                        }
                    });
            }
        }
    }

    public void onImageClicked(View view) {
        Intent intent = new Intent(mContext, ImagesDetailsActivity.class);
        intent.putExtra("ad", currentAd);
        intent.putExtra("page", viewPager.getCurrentItem());
        startActivity(intent);
    }

    @Override
    protected void showSnackBar(String message) {
        Snackbar snackbar = Snackbar
                .make(findViewById(R.id.parentPanel), message, Snackbar.LENGTH_INDEFINITE)
                .setActionTextColor(getResources().getColor(R.color.white))
                .setAction(getResources().getString(R.string.refresh), new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        recreate();
                    }
                });

        if (enabled)
            showMessageInToast(message);
        else
            snackbar.show();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == REQUEST_CALL) {

            Intent callIntent = new Intent(Intent.ACTION_CALL);
            callIntent.setData(Uri.parse("tel:" + currentAd.getSellerPhone()));
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED)
                startActivity(callIntent);
        }
    }


    private void fillTemplate(Ad result) {
        switch (result.getTemplate()) {
            case Category.PROPERTIES:

                textViewSpace.setText(formattedNumber(((AdProperty) result).getSpace()));
                textViewRooms.setText(formattedNumber(((AdProperty) result).getRoomNum()));
                textViewFloors.setText(formattedNumber(((AdProperty) result).getFloorNum()));
                textViewState.setText(((AdProperty) result).getState());

                if (((AdProperty) result).isFurnished())
                    textViewFurn.setText(getString(R.string.yes));
                else
                    textViewFurn.setText(getString(R.string.no));

                break;

            case Category.JOBS:

                textViewEdu.setText(((AdJob) result).getEducationName());
                textViewSch.setText(((AdJob) result).getScheduleName());
                textViewEx.setText(((AdJob) result).getExperience());
                textViewSalary.setText(formattedNumber(((AdJob) result).getSalary()) + " " + getString(R.string.sp));

                break;

            case Category.VEHICLES:
                textViewBrand.setText(((AdVehicle) result).getTypeName());
                textViewModel.setText(((AdVehicle) result).getModelName());
                textViewManufactureYear.setText(((AdVehicle) result).getManufactureYear());
                textViewKilo.setText(formattedNumber(((AdVehicle) result).getKilometer()));

                if (((AdVehicle) result).isAutomatic())
                    textViewTransmission.setText(getString(R.string.labelAutomatic));
                else
                    textViewTransmission.setText(getString(R.string.manual));

                if (((AdVehicle) result).isSecondhand())
                    textViewUsage.setText(getString(R.string.old));
                else
                    textViewUsage.setText(getString(R.string.newU));

                break;

            case Category.ELECTRONICS:
                textViewBrand.setText(((AdElectronic) result).getTypeName());
                textViewSize.setText(formattedNumber(((AdElectronic) result).getSize()));

                if (((AdElectronic) result).isSecondhand())
                    textViewUsage.setText(getString(R.string.old));
                else
                    textViewUsage.setText(getString(R.string.newU));

                break;

            case Category.MOBILES:
                textViewBrand.setText(((AdMobile) result).getTypeName());

                if (((AdMobile) result).isSecondhand())
                    textViewUsage.setText(getString(R.string.old));
                else
                    textViewUsage.setText(getString(R.string.newU));

                break;

            case Category.FASHION:

                if (((AdFashion) result).isSecondhand())
                    textViewUsage.setText(getString(R.string.old));
                else
                    textViewUsage.setText(getString(R.string.newU));

                break;

            case Category.KIDS:

                if (((AdKid) result).isSecondhand())
                    textViewUsage.setText(getString(R.string.old));
                else
                    textViewUsage.setText(getString(R.string.newU));

                break;

            case Category.SPORTS:

                if (((AdSport) result).isSecondhand())
                    textViewUsage.setText(getString(R.string.old));
                else
                    textViewUsage.setText(getString(R.string.newU));

                break;

            case Category.INDUSTRIES:

                if (((AdIndustry) result).isSecondhand())
                    textViewUsage.setText(getString(R.string.old));
                else
                    textViewUsage.setText(getString(R.string.newU));
        }
    }

    private void showTemplate() {

        switch (currentAd.getTemplate()) {
            case Category.PROPERTIES:
                containerProperty.setVisibility(View.VISIBLE);

                break;

            case Category.JOBS:
                containerJob.setVisibility(View.VISIBLE);
                textViewPrice.setVisibility(View.INVISIBLE);

                break;

            case Category.VEHICLES:
                containerVehicle.setVisibility(View.VISIBLE);
                line3.setVisibility(View.VISIBLE);
                containerBrand.setVisibility(View.VISIBLE);
                line1.setVisibility(View.VISIBLE);
                containerUsage.setVisibility(View.VISIBLE);

                break;

            case Category.ELECTRONICS:
                line2.setVisibility(View.VISIBLE);
                containerSize.setVisibility(View.VISIBLE);

            case Category.MOBILES:
                line3.setVisibility(View.VISIBLE);
                containerBrand.setVisibility(View.VISIBLE);

            case Category.FASHION:
            case Category.KIDS:
            case Category.SPORTS:
            case Category.INDUSTRIES:
                line1.setVisibility(View.VISIBLE);
                containerUsage.setVisibility(View.VISIBLE);
        }
    }

    private String getStringStatus(int status) {
        switch (status) {
            case Ad.PENDING:
                return getString(R.string.statusPending);

            case Ad.ACCEPTED:
                return getString(R.string.statusAccepted);

            case Ad.EXPIRED:
                return getString(R.string.statusExpired);

            case Ad.HIDDEN:
                return getString(R.string.statusHidden);

            case Ad.REJECTED:
                return getString(R.string.statusRejected);

            default:
                return "";
        }
    }
}
