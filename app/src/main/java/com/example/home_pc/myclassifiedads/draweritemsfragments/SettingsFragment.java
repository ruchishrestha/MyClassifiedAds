package com.example.home_pc.myclassifiedads.draweritemsfragments;

import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.home_pc.myclassifiedads.R;
import com.example.home_pc.myclassifiedads.classified_api.ImageLoaderAPI;

/**
 * Created by Home-PC on 7/23/2015.
 */
public class SettingsFragment extends Fragment {

    ImageView imag;

    public SettingsFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.item_layouts, container,
                false);

        imag = (ImageView) view.findViewById(R.id.adImage);


        new AsyncImageLoader(view).execute("https://classifiedimagestorage.blob.core.windows.net/gallery/RealEstate7_0");
        return view;
    }

    protected class AsyncImageLoader extends AsyncTask<String,Void, Bitmap>{

        Bitmap image;
        View view;

        public AsyncImageLoader(View view) {
            this.view = view;
        }

        @Override
        protected Bitmap doInBackground(String... params) {
            try{

                image = ImageLoaderAPI.AzureImageDownloader(params[0]);
            }
            catch (Exception e){

            }
            return image;
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            imag.setImageBitmap(bitmap);
        }
    }


}
