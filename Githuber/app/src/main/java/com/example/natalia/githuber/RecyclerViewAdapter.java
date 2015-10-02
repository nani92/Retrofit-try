package com.example.natalia.githuber;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by natalia on 10/2/15.
 */
public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>  {

    private Contributor[] mDataset;
    public static class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public TextView mLogin;
        public ImageView mAvatar;
        public TextView mContribUrl;
        public TextView mContributions;
        public TextView mId;
        public ViewHolder(View v) {
            super(v);
            mLogin = (TextView) v.findViewById(R.id.contributorLogin);
            mAvatar = (ImageView) v.findViewById(R.id.avatarView);
            mContribUrl = (TextView) v.findViewById(R.id.contributorUrl);
            mContributions = (TextView) v.findViewById(R.id.contributions);
            mId = (TextView) v.findViewById(R.id.contributorID);
        }
    }

    public RecyclerViewAdapter(Contributor[] myDataset) {
        mDataset = myDataset;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public RecyclerViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                   int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.contributor_layout, parent, false);
        // set the view's size, margins, paddings and layout parameters
        ViewHolder vh = new ViewHolder((View) v);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        holder.mLogin.setText(mDataset[position].getLogin());
        holder.mContribUrl.setText(mDataset[position].getHtmlUrl());
        holder.mContributions.setText("Made " + mDataset[position].getContributions() + " contributions.");
        holder.mId.setText("Id: "+mDataset[position].getId());
        //ImageLoader imgLoader = new ImageLoader(getApplicationContext());
        /*try {
            URL  url = new URL(mDataset[position].getAvatarUrl());
            holder.mAvatar.setImageBitmap(BitmapFactory.decodeStream(url.openConnection().getInputStream()));
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }*/

    }



    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mDataset.length;
    }
}
