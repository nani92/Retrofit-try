package com.example.natalia.githuber;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

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
    private static String[] data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d("Natalia", "przed");
        //final SwipeRefreshLayout layoutManager = (SwipeRefreshLayout) findViewById(R.id.swiperefresh);
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        RetrofitService retrofitService= new RetrofitService();
        try {
            Log.d("Natalia", "RetroSe");
            main();
            //String[] data = retrofitService.getData();
            /*Log.d("Natalia", "jest tutaj");
            RecyclerView.Adapter mAdapter = new RecyclerViewAdapter(data);
            recyclerView.setAdapter(mAdapter);*/
        } catch (IOException e) {
            e.printStackTrace();
        }

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

    public static class Contributor {
        public final String login;
        public final int contributions;

        public Contributor(String login, int contributions) {
            this.login = login;
            this.contributions = contributions;
        }
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

        Call<List<MainActivity.Contributor>> call = github.contributors("square", "retrofit");
        Log.d("Natalia", "przed enq");
        call.enqueue(new Callback<List<MainActivity.Contributor>>() {
            @Override
            public void onResponse(Response<List<Contributor>> response, Retrofit retrofit) {
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
                SendDataToView(data);
            }

            @Override
            public void onFailure(Throwable t) {
                // ...
                Log.d("Natalia", "Failure");
            }
        });
    }

    public void SendDataToView(String[] data) {
        RecyclerView.Adapter mAdapter = new RecyclerViewAdapter(data);
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);
        recyclerView.setAdapter(mAdapter);
    }

}
