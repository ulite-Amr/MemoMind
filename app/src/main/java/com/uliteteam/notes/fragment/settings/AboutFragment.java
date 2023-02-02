package com.uliteteam.notes.fragment.settings;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.DrawableRes;
import androidx.annotation.Nullable;
import androidx.core.content.res.ResourcesCompat;

import com.danielstone.materialaboutlibrary.ConvenienceBuilder;
import com.danielstone.materialaboutlibrary.MaterialAboutFragment;
import com.danielstone.materialaboutlibrary.items.MaterialAboutActionItem;
import com.danielstone.materialaboutlibrary.model.MaterialAboutCard;
import com.danielstone.materialaboutlibrary.model.MaterialAboutList;
import com.danielstone.materialaboutlibrary.util.OpenSourceLicense;
import com.google.android.material.transition.MaterialSharedAxis;
import com.uliteteam.notes.R;
import com.uliteteam.notes.fragment.settings.BasePreferenceFragment;

public class AboutFragment extends MaterialAboutFragment {

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setEnterTransition(new MaterialSharedAxis(MaterialSharedAxis.X, false));
        setExitTransition(new MaterialSharedAxis(MaterialSharedAxis.X, true));
    }

    @Override
    protected MaterialAboutList getMaterialAboutList(Context context) {
        MaterialAboutCard appCard = new MaterialAboutCard.Builder()
                 .addItem(ConvenienceBuilder.createAppTitleItem(context))
                .addItem(new MaterialAboutActionItem.Builder()
                        .subText(R.string.app_des)
                        .build())
                .addItem(ConvenienceBuilder.createVersionActionItem(context,
                        getDrawable(R.drawable.info ),
                        getString(R.string.app_version),
                        true))
                .addItem(ConvenienceBuilder.createEmailItem(context,
                        getDrawable(R.drawable.mail),
                        getString(R.string.settings_about_us_title),
                        false,
                        "test@test.com",
                        ""))
                .build();
        
        MaterialAboutCard communityCard = new MaterialAboutCard.Builder()
                .title("Community")
                .addItem(ConvenienceBuilder.createWebsiteActionItem(context,
                        getDrawable(R.drawable.ic_discord),
                        "Discord",
                        false,
                        Uri.parse("https://test.com")))
                .addItem(ConvenienceBuilder.createWebsiteActionItem(context,
                        getDrawable(R.drawable.ic_instagram),
                        "Instagram",
                        false,
                        Uri.parse("https://instagram.com/amrayman.official")))
                .addItem(ConvenienceBuilder.createWebsiteActionItem(context,
                        getDrawable(R.drawable.ic_youtube),
                        "Youtube",
                        false,
                        Uri.parse("https://youtube.com/@AmrAymanofficial")))
                .addItem(ConvenienceBuilder.createWebsiteActionItem(context,
                        getDrawable(R.drawable.ic_facebook),
                        "Facebook",
                        false,
                        Uri.parse("https://www.facebook.com/amrayman.officiall")))
                .build();
        
                
        MaterialAboutCard licenseCard = ConvenienceBuilder.createLicenseCard(context,
                getDrawable(R.drawable.ic_book),
                getString(R.string.app_name),
                "2023",
                "AmrAyman",
                OpenSourceLicense.GNU_GPL_3);

        

        return new MaterialAboutList.Builder()
                .addCard(appCard)
                .addCard(communityCard)
                .addCard(licenseCard)
                .build();
    }

    private Drawable getDrawable(@DrawableRes int drawable) {
        return ResourcesCompat.getDrawable(requireContext().getResources(),
                drawable,
                requireContext().getTheme());
    }
}