package com.example.natalia.githuber;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.IOException;
import java.util.List;

import retrofit.Call;
import retrofit.Callback;
import retrofit.GsonConverterFactory;
import retrofit.Response;
import retrofit.Retrofit;
import retrofit.http.GET;
import retrofit.http.Path;


public class MainActivity extends Activity {

    public static final String API_URL = "https://api.github.com";
    private static Contributor[] data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        RetrofitService retrofitService= new RetrofitService();
        try {
            main();
        } catch (IOException e) {
            e.printStackTrace();
        }

        final SwipeRefreshLayout mySwipe = (SwipeRefreshLayout) findViewById(R.id.swiperefresh);

        mySwipe.setOnRefreshListener(
                new SwipeRefreshLayout.OnRefreshListener() {
                    @Override
                    public void onRefresh() {
                        try {
                            main();
                            mySwipe.setRefreshing(false);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
        );

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }



    public interface GitHub {
        @GET("/repos/{owner}/{repo}/contributors")
        Call<List<Contributor>> contributors(
                @Path("owner") String owner,
                @Path("repo") String repo);
        /*public void contributors(@Path("owner") String owner, @Path("repo") String repo,
                                 Callback<List<Contributor>> contributors);*/
    }

    public void main() throws IOException {
        // Create a very simple REST adapter which points the GitHub API.
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(API_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        MainActivity.GitHub github = retrofit.create(MainActivity.GitHub.class);

        Call<List<Contributor>> call = github.contributors("square", "retrofit");
        call.enqueue(new Callback<List<Contributor>>() {
            @Override
            public void onResponse(Response<List<Contributor>> response, Retrofit retrofit) {
                List<Contributor> contributors = response.body();
                data = new Contributor[contributors.size()];
                int i = 0;
                for (Contributor contributor : contributors) {
                    data[i] = contributor;
                    i++;
                    //System.out.println(contributor.login + " (" + contributor.contributions + ")");
                }
                SendDataToView(data);
            }

            @Override
            public void onFailure(Throwable t) {
                // ...
                Log.d("Natalia", "Failure");
            }
        });
    }

    public void SendDataToView(Contributor[] data) {
        RecyclerView.Adapter mAdapter = new RecyclerViewAdapter(data);
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);
        recyclerView.setAdapter(mAdapter);
    }

}
