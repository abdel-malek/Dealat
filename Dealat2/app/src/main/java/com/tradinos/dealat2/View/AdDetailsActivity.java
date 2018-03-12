package com.tradinos.dealat2.View;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.tradinos.core.network.InternetManager;
import com.tradinos.core.network.SuccessCallback;
import com.tradinos.dealat2.Adapter.AdImagesAdapter;
import com.tradinos.dealat2.Controller.AdController;
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
import com.tradinos.dealat2.MyApplication;
import com.tradinos.dealat2.R;

/**
 * Created by developer on 01.03.18.
 */

public class AdDetailsActivity extends MasterActivity {

    private Ad currentAd;

    // textViewViews
    private ViewPager viewPager;
    private TabLayout tabLayout;

    private TextView textViewId, textViewTitle, textViewTitle2, textViewPrice, textNegotiable, textViewDesc, textViewSeller,
            textViewViews, textViewPublishDate, textViewLocation,
            textViewEdu, textViewSch, textViewSalary, textViewEx,
            textViewSpace, textViewRooms, textViewFloors, textViewFurn, textViewState,
            textViewBrand, textViewModel, textViewKilo, textViewTransmission, textViewManufactureYear,
            textViewUsage, textViewSize;

    private ImageButton buttonFav;

    private LinearLayout containerJob, containerProperty, containerVehicle, containerBrand, containerUsage, containerSize;
    private View line1, line2, line3;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_ad_details);
        super.onCreate(savedInstanceState);
    }

    @Override
    public void getData() {

        currentAd = (Ad) getIntent().getSerializableExtra("ad");

        getAd();
    }

    private void getAd() {
        ShowProgressDialog();
        AdController.getInstance(mController).getAdDetails(currentAd.getId(), currentAd.getTemplate(), new SuccessCallback<Ad>() {
            @Override
            public void OnSuccess(Ad result) {

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
                textViewLocation.setText(result.getLocationName());
                textViewSeller.setText(result.getSellerName());

                if (result.isNegotiable())
                    textNegotiable.setText(getString(R.string.yes));
                else
                    textNegotiable.setText(getString(R.string.no));

                textViewDesc.setText(result.getDescription());

                HideProgressDialog();
                fillTemplate(result);
                showTemplate();
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
        textViewLocation = (TextView) findViewById(R.id.textLocation);
        textNegotiable = (TextView) findViewById(R.id.textNegotiable);
        textViewDesc = (TextView) findViewById(R.id.textDesc);
        textViewSeller = (TextView) findViewById(R.id.textUser);

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
    }

    @Override
    public void assignActions() {

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.buttonCall:
                break;

            case R.id.buttonMessage:
                break;

            case R.id.buttonReport:
                break;
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
                textViewSalary.setText(formattedNumber(((AdJob) result).getSalary()));
                textViewPrice.setText(formattedNumber(((AdJob) result).getSalary()));

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
}
