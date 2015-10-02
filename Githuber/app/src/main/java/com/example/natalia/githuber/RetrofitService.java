package com.example.natalia.githuber;

import android.app.Fragment;
import android.util.Log;

import java.io.IOException;
import java.util.List;

import retrofit.Call;
import retrofit.Callback;
import retrofit.GsonConverterFactory;
import retrofit.Response;
import retrofit.Retrofit;

/**
 * Created by natalia on 10/2/15.
 */
public class RetrofitService  {
  /*  public static final String API_URL = "https://api.github.com";
    private static String[] data;
    public static void main(Fragment fr) throws IOException {
        // Create a very simple REST adapter which points the GitHub API.
        final Fragment  f = fr;
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(API_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        MainActivity.GitHub github = retrofit.create(MainActivity.GitHub.class);

        Call<List<MainActivity.Contributor>> call = github.contributors("square", "retrofit");
        Log.d("Natalia", "przed enq");
        call.enqueue(new Callback<List<MainActivity.Contributor>>() {
            @Override
            public void onResponse(Response<List<MainActivity.Contributor>> response, Retrofit retrofit) {
                Log.d("Natalia", "onResp");
                List<MainActivity.Contributor> contributors = response.body();
                Log.d("Natalia", String.valueOf(response.body()));
                data = new String[contributors.size()];
                int i = 0;
                for (MainActivity.Contributor contributor : contributors) {
                    data[i] = (contributor + "( " + contributor.contributions + ")");
                    i++;
                    //System.out.println(contributor.login + " (" + contributor.contributions + ")");
                }
                MainActivity.SendDataToView(f, data);
            }

            @Override
            public void onFailure(Throwable t) {
                // ...
                Log.d("Natalia", "Failure");
            }
        });
    }

    public String[] getData () {
        return  data;
    }
*/
}
