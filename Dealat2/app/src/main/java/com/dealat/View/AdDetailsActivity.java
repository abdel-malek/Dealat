package com.dealat.View;

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
import com.dealat.Adapter.AdImagesAdapter;
import com.dealat.Controller.CurrentAndroidUser;
import com.tradinos.core.network.InternetManager;
import com.tradinos.core.network.SuccessCallback;
import com.dealat.Controller.AdController;
import com.dealat.Model.Ad;
import com.dealat.Model.AdElectronic;
import com.dealat.Model.AdFashion;
import com.dealat.Model.AdIndustry;
import com.dealat.Model.AdJob;
import com.dealat.Model.AdKid;
import com.dealat.Model.AdMobile;
import com.dealat.Model.AdProperty;
import com.dealat.Model.AdSport;
import com.dealat.Model.AdVehicle;
import com.dealat.Model.Category;
import com.dealat.Model.Chat;
import com.dealat.MyApplication;
import com.dealat.R;
import com.dealat.Utils.CustomAlertDialog;

/**
 * Created by developer on 01.03.18.
 */

public class AdDetailsActivity extends MasterActivity {

    private final int REQUEST_CALL = 6, REQUEST_EDIT = 7;

    private boolean enabled = false;

    private CurrentAndroidUser user;
    private Ad currentAd;
    private Category currentCategory;

    // textViewViews
    private ViewPager viewPager;
    private TabLayout tabLayout;

    private TextView textViewId, textViewTitle, textViewTitle2, textViewPrice, textNegotiable, textViewDesc,
            textViewSeller, textViewPhone,
            textViewViews, textViewPublishDate, textViewLocation, textViewCity, textViewExpires,
            textViewEdu, textCertificate, textViewSch, textViewSalary, textViewEx, textGender,
            textViewSpace, textViewRooms, textViewFloors, textViewNumberFloors, textViewFurn, textViewState,
            textViewBrand, textViewModel, textViewKilo, textViewTransmission, textViewManufactureYear, textCapacity,
            textViewUsage, textViewSize;

    private ImageButton buttonFav, buttonReport;
    private RatingBar ratingBar;

    private LinearLayout containerEdu, containerCertificate, containerSchedule, containerEx, containerSalary, containerGender,
            containerSpace, containerRooms, containerFloors, containerNumberFloors, containerPropertyState, containerFurn,
            containerBrand, containerModel, containerKilometer, containerYear, containerCapacity, containerTransmission,
            containerUsage, containerSize;
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
        currentCategory = MyApplication.getCategoryById(currentAd.getCategoryId());

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
                currentAd.setWhatsAppNumber(result.getWhatsAppNumber());
                currentAd.setFavorite(result.isFavorite());
                currentAd.setImagesPaths(result.getImagesPaths());
                currentAd.setMainVideoUrl(result.getMainVideoUrl());
                currentAd.setVisiblePhone(result.isVisiblePhone());
                currentAd.setAdminSeller(result.isAdminSeller());

                if (result.getMainVideoUrl() != null)// just a gap thing for video // because count of fragments must increase +1
                    result.getImagesPaths().add(0, "");

                viewPager.setAdapter(new AdImagesAdapter(getSupportFragmentManager(), result.getImagesPaths(), currentAd.getTemplate()));

                tabLayout.setupWithViewPager(viewPager);

                LinearLayout tabStrip = (LinearLayout) tabLayout.getChildAt(0);

                for (int i = 0; i < result.getImagesPaths().size(); i++) {
                    View view = LayoutInflater.from(mContext).inflate(R.layout.row_tab_image, null);

                    ImageView imageView = view.findViewById(R.id.imageView);
                    String path = result.getImagePath(i);

                    int defaultDrawable = getTemplateDefaultImage(currentAd.getTemplate());

                    if (path == null)
                        imageView.setImageDrawable(ContextCompat.getDrawable(mContext, defaultDrawable));
                    else if (path.equals("")) { // a Video is added to ad.getImagesPaths as empty string ""
                        imageView.setImageDrawable(ContextCompat.getDrawable(mContext, R.drawable.ic_play_arrow_gray));
                        imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
                    } else {
                        ImageLoader mImageLoader = InternetManager.getInstance(mContext).getImageLoader();
                        mImageLoader.get(MyApplication.getBaseUrl() + path, ImageLoader.getImageListener(imageView,
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
                ((TextView) findViewById(R.id.textViewCat)).setText(currentCategory.getFullName());

                if (result.getPublishDate() != null)  // unaccepted ads their publish dates are null
                    textViewPublishDate.setText(getString(R.string.published) + " " + formattedDate(result.getPublishDate()));

                textViewPrice.setText(formattedNumber(result.getPrice()) + " " + getString(R.string.sp));
                textViewViews.setText(formattedNumber(result.getViews()) + " " + getString(R.string.view));
                textViewCity.setText(result.getCityName());

                if (result.getLocationId() != null) {
                    textViewLocation.setText(result.getLocationName());
                    findViewById(R.id.line6).setVisibility(View.VISIBLE);
                    findViewById(R.id.containerLocation).setVisibility(View.VISIBLE);
                }

                if (result.isNegotiable())
                    textNegotiable.setText(getString(R.string.yes));
                else
                    textNegotiable.setText(getString(R.string.no));

                textViewDesc.setText(result.getDescription());

                HideProgressDialog();
                fillTemplate(result);
                showTemplate();

                if (user.IsLogged()) {
                    if (currentAd.isVisiblePhone()) {
                        textViewPhone.setText(getPhoneNumber(result.getSellerPhone()));
                        textViewPhone.setVisibility(View.VISIBLE);
                    } else { //contact only using Dealat chat
                        findViewById(R.id.buttonCall).setVisibility(View.GONE);
                        findViewById(R.id.line20).setVisibility(View.GONE);
                    }

                    if (currentAd.isAdminSeller()) {
                        findViewById(R.id.buttonMessage).setVisibility(View.GONE);
                        findViewById(R.id.line20).setVisibility(View.GONE);
                    } else {
                        textViewSeller.setText(result.getSellerName());
                        textViewSeller.setVisibility(View.VISIBLE);
                    }

                    if (currentAd.getSellerId().equals(user.Get().getId())) { // view info for Seller

                        ((TextView) findViewById(R.id.textViewStatus)).setText(getStringStatus(currentAd.getStatus()));
                        findViewById(R.id.container4).setVisibility(View.VISIBLE);

                        ((TextView) findViewById(R.id.textViewCreated)).setText(formattedDate(currentAd.getCreationDate()));
                        findViewById(R.id.container6).setVisibility(View.VISIBLE);

                        if (result.getPublishDate() != null) {
                            textViewExpires.setText(getExpiryTime(result.getPublishDate(), result.getDays()));

                            findViewById(R.id.container7).setVisibility(View.VISIBLE);
                            findViewById(R.id.line19).setVisibility(View.VISIBLE);
                        }

                        if (currentAd.isRejected()) {
                            ((TextView) findViewById(R.id.textViewNote)).setText(currentAd.getRejectNote());
                            findViewById(R.id.container5).setVisibility(View.VISIBLE);
                        }

                        // so seller can edit or delete their ad
                        findViewById(R.id.containerSetting).setVisibility(View.VISIBLE);

                    } else { // seller cannot contact themselves
                        if (currentAd.isFavorite())
                            buttonFav.setImageDrawable(ContextCompat.getDrawable(mContext, R.drawable.star));

                        buttonFav.setVisibility(View.VISIBLE);

                        findViewById(R.id.containerContact).setVisibility(View.VISIBLE);

                        buttonReport.setVisibility(View.VISIBLE); // seller cannot report their ad
                    }
                } else
                    buttonReport.setVisibility(View.VISIBLE); // even unregistered users can report
            }
        });
    }

    @Override
    public void showData() {

    }

    @Override
    public void assignUIReferences() {
        viewPager = findViewById(R.id.viewpager);
        tabLayout = findViewById(R.id.tab_layout);

        textViewId = findViewById(R.id.text2);
        textViewTitle = findViewById(R.id.title);
        textViewTitle2 = findViewById(R.id.text);
        textViewViews = findViewById(R.id.textView);
        textViewPublishDate = findViewById(R.id.textViewDate);
        textViewPrice = findViewById(R.id.textViewPrice);
        textViewCity = findViewById(R.id.textViewCity);
        textViewLocation = findViewById(R.id.textLocation);
        textViewExpires = findViewById(R.id.textViewExpires);
        textNegotiable = findViewById(R.id.textNegotiable);
        textViewDesc = findViewById(R.id.textDesc);
        textViewSeller = findViewById(R.id.textUser);
        textViewPhone = findViewById(R.id.textViewPhone);

        textViewEdu = findViewById(R.id.textEdu);
        textCertificate = findViewById(R.id.textCertificate);
        textViewSch = findViewById(R.id.textSch);
        textViewEx = findViewById(R.id.textEx);
        textViewSalary = findViewById(R.id.textSalary);
        textGender = findViewById(R.id.textGender);

        textViewSpace = findViewById(R.id.textSpace);
        textViewRooms = findViewById(R.id.textRooms);
        textViewFloors = findViewById(R.id.textFloor);
        textViewNumberFloors = findViewById(R.id.textNumberFloors);
        textViewFurn = findViewById(R.id.textFurn);
        textViewState = findViewById(R.id.textState);


        textViewBrand = findViewById(R.id.textBrand);
        textViewModel = findViewById(R.id.textModel);
        textViewKilo = findViewById(R.id.textKilo);
        textViewManufactureYear = findViewById(R.id.textDate);
        textCapacity = findViewById(R.id.textCapacity);
        textViewTransmission = findViewById(R.id.textTransmission);

        textViewSize = findViewById(R.id.textSize);

        textViewUsage = findViewById(R.id.textUsage);

        containerBrand = findViewById(R.id.containerBrand);
        containerModel = findViewById(R.id.containerModel);
        containerKilometer = findViewById(R.id.containerKilometer);
        containerYear = findViewById(R.id.containerYear);
        containerTransmission = findViewById(R.id.containerTransmission);
        containerCapacity = findViewById(R.id.containerCapacity);

        containerUsage = findViewById(R.id.containerUsage);

        containerEdu = findViewById(R.id.containerEdu);
        containerCertificate = findViewById(R.id.containerCertificate);
        containerSchedule = findViewById(R.id.containerSchedule);
        containerEx = findViewById(R.id.containerEx);
        containerSalary = findViewById(R.id.containerSalary);
        containerGender = findViewById(R.id.containerGender);

        containerSpace = findViewById(R.id.containerSpace);
        containerRooms = findViewById(R.id.containerRooms);
        containerFloors = findViewById(R.id.containerFloors);
        containerNumberFloors = findViewById(R.id.containerNumberFloors);
        containerPropertyState = findViewById(R.id.containerPropertyState);
        containerFurn = findViewById(R.id.containerFurn);

        containerSize = findViewById(R.id.containerSize);

        line1 = findViewById(R.id.line1);
        line2 = findViewById(R.id.line2);
        line3 = findViewById(R.id.line3);

        buttonFav = findViewById(R.id.buttonFav);
        buttonReport = findViewById(R.id.buttonReport);
        ratingBar = findViewById(R.id.ratingBar);
    }

    @Override
    public void assignActions() {
        buttonReport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(mContext, ReportActivity.class);
                intent.putExtra("ad", currentAd);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onClick(View view) {

        if (registered() && user.IsLogged()) {

            Intent intent;
            switch (view.getId()) {
                case R.id.buttonFav:
                    if (currentAd.isFavorite()) {
                        AdController.getInstance(mController).removeFromFavorite(currentAd.getId(), new SuccessCallback<String>() {
                            @Override
                            public void OnSuccess(String result) {

                                Animation mAnimation = new AlphaAnimation(0.0f, 1.0f);
                                mAnimation.setDuration(800);
                                buttonFav.startAnimation(mAnimation);

                                buttonFav.setImageDrawable(ContextCompat.getDrawable(mContext, R.drawable.ic_star_white_24dp));
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

                        callIntent.setData(Uri.parse("tel:" + getPhoneNumber(currentAd.getSellerPhone())));
                        startActivity(callIntent);
                    }

                    break;

                case R.id.buttonMessage:
                    intent = new Intent(mContext, ChatActivity.class);
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

                case R.id.buttonEdit:
                    intent = new Intent(mContext, EditAdActivity.class);
                    intent.putExtra("ad", currentAd);
                    startActivityForResult(intent, REQUEST_EDIT);
                    finish();

                    break;

                case R.id.buttonDelete:

                    CustomAlertDialog dialog = new CustomAlertDialog(mContext, getString(R.string.areYouSureDeleteAd));
                    dialog.show();

                    dialog.getButtonTrue().setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            ShowProgressDialog();
                            AdController.getInstance(mController).changeStatus(currentAd.getId(), Ad.DELETED,
                                    new SuccessCallback<String>() {
                                        @Override
                                        public void OnSuccess(String result) {

                                            HideProgressDialog();
                                            showMessageInToast(R.string.toastAdDeleted);
                                            finish();
                                        }
                                    });
                        }
                    });

                    break;
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
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK && requestCode == REQUEST_EDIT)
            finish();
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
            callIntent.setData(Uri.parse("tel:" + getPhoneNumber(currentAd.getSellerPhone())));
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
                textViewNumberFloors.setText(formattedNumber(((AdProperty) result).getFloorsCount()));
                textViewState.setText(((AdProperty) result).getStateName());

                if (((AdProperty) result).isFurnished())
                    textViewFurn.setText(getString(R.string.yes));
                else
                    textViewFurn.setText(getString(R.string.no));

                break;

            case Category.JOBS:

                textViewEdu.setText(((AdJob) result).getEducationName());
                textCertificate.setText(((AdJob) result).getCertificateName());
                textViewSch.setText(((AdJob) result).getScheduleName());
                textViewEx.setText(((AdJob) result).getExperience());
                textViewSalary.setText(formattedNumber(((AdJob) result).getSalary()) + " " + getString(R.string.sp));

                if (((AdJob) result).getGender() == 1)
                    textGender.setText(getString(R.string.male));
                else if (((AdJob) result).getGender() == 2)
                    textGender.setText(getString(R.string.female));

                break;

            case Category.VEHICLES:
                textViewBrand.setText(((AdVehicle) result).getTypeName());
                textViewModel.setText(((AdVehicle) result).getModelName());
                textViewManufactureYear.setText(((AdVehicle) result).getManufactureYear());
                textCapacity.setText(((AdVehicle) result).getEngineCapacity());
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
                findViewById(R.id.line17).setVisibility(View.VISIBLE);

                if (!currentCategory.shouldHideTag(getString(R.string.hideSpace))) {
                    findViewById(R.id.line9).setVisibility(View.VISIBLE);
                    containerSpace.setVisibility(View.VISIBLE);
                }

                if (!currentCategory.shouldHideTag(getString(R.string.hideRoom))) {
                    findViewById(R.id.line10).setVisibility(View.VISIBLE);
                    containerRooms.setVisibility(View.VISIBLE);
                }

                if (!currentCategory.shouldHideTag(getString(R.string.hideFloor))) {
                    containerFloors.setVisibility(View.VISIBLE);
                    findViewById(R.id.line11).setVisibility(View.VISIBLE);
                }

                if (!currentCategory.shouldHideTag(getString(R.string.hideNumberFloors))) {
                    containerNumberFloors.setVisibility(View.VISIBLE);
                    findViewById(R.id.line21).setVisibility(View.VISIBLE);
                }

                if (!currentCategory.shouldHideTag(getString(R.string.hidePropertyState))) {
                    findViewById(R.id.line12).setVisibility(View.VISIBLE);
                    containerPropertyState.setVisibility(View.VISIBLE);
                }

                if (!currentCategory.shouldHideTag(getString(R.string.hideFurn))) {
                    containerFurn.setVisibility(View.VISIBLE);
                }

                break;

            case Category.JOBS:

                textViewPrice.setVisibility(View.INVISIBLE);
                findViewById(R.id.line16).setVisibility(View.VISIBLE);

                if (!currentCategory.shouldHideTag(getString(R.string.hideEducation))) {
                    findViewById(R.id.line5).setVisibility(View.VISIBLE);
                    containerEdu.setVisibility(View.VISIBLE);
                }

                if (!currentCategory.shouldHideTag(getString(R.string.hideCertificate))) {
                    containerCertificate.setVisibility(View.VISIBLE);
                    findViewById(R.id.line24).setVisibility(View.VISIBLE);
                }

                if (!currentCategory.shouldHideTag(getString(R.string.hideSchedule))) {
                    containerSchedule.setVisibility(View.VISIBLE);
                    findViewById(R.id.line7).setVisibility(View.VISIBLE);
                }

                if (!currentCategory.shouldHideTag(getString(R.string.hideGender))) {
                    containerGender.setVisibility(View.VISIBLE);
                    findViewById(R.id.line23).setVisibility(View.VISIBLE);
                }

                if (!currentCategory.shouldHideTag(getString(R.string.hideEx))) {
                    findViewById(R.id.line8).setVisibility(View.VISIBLE);
                    containerEx.setVisibility(View.VISIBLE);
                }

                if (!currentCategory.shouldHideTag(getString(R.string.hideSalary)))
                    containerSalary.setVisibility(View.VISIBLE);

                break;

            case Category.VEHICLES:

                findViewById(R.id.line18).setVisibility(View.VISIBLE);

                if (!currentCategory.shouldHideTag(getString(R.string.hideBrand))) {
                    line3.setVisibility(View.VISIBLE);
                    containerBrand.setVisibility(View.VISIBLE);
                }

                if (!currentCategory.shouldHideTag(getString(R.string.hideModel))) {
                    findViewById(R.id.line13).setVisibility(View.VISIBLE);
                    containerModel.setVisibility(View.VISIBLE);
                }

                if (!currentCategory.shouldHideTag(getString(R.string.hideKilo))) {
                    findViewById(R.id.line14).setVisibility(View.VISIBLE);
                    containerKilometer.setVisibility(View.VISIBLE);
                }

                if (!currentCategory.shouldHideTag(getString(R.string.hideYear))) {
                    findViewById(R.id.line15).setVisibility(View.VISIBLE);
                    containerYear.setVisibility(View.VISIBLE);
                }

                if (!currentCategory.shouldHideTag(getString(R.string.hideCapacity))) {
                    findViewById(R.id.line22).setVisibility(View.VISIBLE);
                    containerCapacity.setVisibility(View.VISIBLE);
                }

                if (!currentCategory.shouldHideTag(getString(R.string.hideAutomatic))) {
                    containerTransmission.setVisibility(View.VISIBLE);
                }

                if (!currentCategory.shouldHideTag(getString(R.string.hideSecondhand))) {
                    line1.setVisibility(View.VISIBLE);
                    containerUsage.setVisibility(View.VISIBLE);
                }

                break;

            case Category.ELECTRONICS:
                if (!currentCategory.shouldHideTag(getString(R.string.hideSize))) {
                    line2.setVisibility(View.VISIBLE);
                    containerSize.setVisibility(View.VISIBLE);
                }

            case Category.MOBILES:
                if (!currentCategory.shouldHideTag(getString(R.string.hideBrand))) {
                    line3.setVisibility(View.VISIBLE);
                    containerBrand.setVisibility(View.VISIBLE);
                }

            case Category.FASHION:
            case Category.KIDS:
            case Category.SPORTS:
            case Category.INDUSTRIES:
                if (!currentCategory.shouldHideTag(getString(R.string.hideSecondhand))) {
                    line1.setVisibility(View.VISIBLE);
                    containerUsage.setVisibility(View.VISIBLE);
                }
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
