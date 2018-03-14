package com.udacity.gradle.builditbigger;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;

import eu.dkaratzas.tellMeAJoke.TellMeAJokeActivity;

public class MainActivityFragment extends Fragment {
    private ProgressBar mProgressBar;

    public MainActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_main, container, false);
        mProgressBar = root.findViewById(R.id.progressBar);

        AdView mAdView = root.findViewById(R.id.adView);
        // Create an ad request. Check logcat output for the hashed device ID to
        // get test ads on a physical device. e.g.
        // "Use AdRequest.Builder.addTestDevice("ABCDEF012345") to get test ads on this device."
        AdRequest adRequest = new AdRequest.Builder()
                .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                .build();
        mAdView.loadAd(adRequest);

        root.findViewById(R.id.buttonTellJoke).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mProgressBar.setVisibility(View.VISIBLE);
                new JokeAsyncTask(new JokeAsyncTask.JokeAsyncTaskCallback() {
                    @Override
                    public void onResult(String result) {
                        mProgressBar.setVisibility(View.GONE);
                        if (result != null) {
                            showInterstitialAd(result);
                        } else {
                            Toast.makeText(getActivity(), R.string.failed_load_joke, Toast.LENGTH_SHORT).show();
                        }
                    }
                }).execute();
            }
        });
        return root;
    }

    private void showInterstitialAd(final String jokeToShow) {
        final InterstitialAd mInterstitialAd = new InterstitialAd(getActivity());
        mInterstitialAd.setAdUnitId(getContext().getString(R.string.interstitial_ad_unit_id));
        mInterstitialAd.setAdListener(new AdListener() {
            @Override
            public void onAdLoaded() {
                super.onAdLoaded();
                mInterstitialAd.show();
            }

            @Override
            public void onAdClosed() {
                super.onAdClosed();
                showJokeActivity(jokeToShow);
            }
        });
        AdRequest adRequest = new AdRequest.Builder()
                .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                .build();
        mInterstitialAd.loadAd(adRequest);
    }

    private void showJokeActivity(String joke) {
        Intent intent = new Intent(getActivity(), TellMeAJokeActivity.class);
        intent.putExtra(TellMeAJokeActivity.EXTRA_JOKE, joke);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        getActivity().startActivity(intent);
    }
}
