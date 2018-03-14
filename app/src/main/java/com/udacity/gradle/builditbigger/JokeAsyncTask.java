package com.udacity.gradle.builditbigger;

import android.os.AsyncTask;
import android.util.Log;

import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;
import com.google.api.client.googleapis.services.AbstractGoogleClientRequest;
import com.google.api.client.googleapis.services.GoogleClientRequestInitializer;
import com.udacity.gradle.builditbigger.backend.jokeApi.JokeApi;

import java.io.IOException;

class JokeAsyncTask extends AsyncTask<String, Void, String> {
    private static JokeApi mJokeApi;
    private JokeAsyncTaskCallback jokeAsyncTaskCallback;

    public JokeAsyncTask(JokeAsyncTaskCallback callback) {
        this.jokeAsyncTaskCallback = callback;
    }

    @Override
    protected String doInBackground(String... param) {
        if (mJokeApi == null) {  // Only do this once
            JokeApi.Builder builder = new JokeApi.Builder(AndroidHttp.newCompatibleTransport(),
                    new AndroidJsonFactory(), null)
                    // options for running against local devappserver
                    // - 10.0.2.2 is localhost's IP address in Android emulator
                    // - turn off compression when running against local devappserver
                    .setRootUrl("http://10.0.2.2:8080/_ah/api/")
                    .setGoogleClientRequestInitializer(new GoogleClientRequestInitializer() {
                        @Override
                        public void initialize(AbstractGoogleClientRequest<?> abstractGoogleClientRequest) throws IOException {
                            abstractGoogleClientRequest.setDisableGZipContent(true);
                        }
                    });
            // end options for devappserver

            mJokeApi = builder.build();
        }

        try {
            return mJokeApi.getJoke().execute().getJoke();
        } catch (IOException e) {
            Log.e("JokeAsyncTask", e.getMessage());
            return null;
        }
    }

    @Override
    protected void onPostExecute(String result) {
        if (jokeAsyncTaskCallback != null) {
            jokeAsyncTaskCallback.onResult(result);
        }
    }

    interface JokeAsyncTaskCallback {
        void onResult(String result);
    }
}