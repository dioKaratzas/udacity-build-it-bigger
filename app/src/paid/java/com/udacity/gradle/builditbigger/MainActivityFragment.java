package com.udacity.gradle.builditbigger;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

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

        root.findViewById(R.id.buttonTellJoke).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mProgressBar.setVisibility(View.VISIBLE);

                new JokeAsyncTask(new JokeAsyncTask.JokeAsyncTaskCallback() {
                    @Override
                    public void onResult(String result) {
                        mProgressBar.setVisibility(View.GONE);
                        if (result != null) {
                            showJokeActivity(result);
                        } else {
                            Toast.makeText(getActivity(), R.string.failed_load_joke, Toast.LENGTH_SHORT).show();
                        }
                    }
                }).execute();
            }
        });
        return root;
    }

    private void showJokeActivity(String joke) {
        Intent intent = new Intent(getActivity(), TellMeAJokeActivity.class);
        intent.putExtra(TellMeAJokeActivity.EXTRA_JOKE, joke);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        getActivity().startActivity(intent);
    }
}
