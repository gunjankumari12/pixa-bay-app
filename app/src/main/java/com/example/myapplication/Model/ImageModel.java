package com.example.myapplication.Model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Suleiman19 on 10/22/15.
 */
public class ImageModel implements Parcelable {

    String name, sub_img;


    protected ImageModel(Parcel in) {
      //  name = in.readString();
        sub_img = in.readString();

    }


    public static final Creator<ImageModel> CREATOR = new Creator<ImageModel>() {
        @Override
        public ImageModel createFromParcel(Parcel in) {
            return new ImageModel(in);
        }

        @Override
        public ImageModel[] newArray(int size) {
            return new ImageModel[size];
        }
    };

    public ImageModel( String sub_img) {
      //  this.name=name;
        this.sub_img=sub_img;
    }

    public String getSub_img() {
        return sub_img;
    }

    public void setSub_img(String sub_img) {
        this.sub_img = sub_img;
    }



    public String getUrl() {
        return sub_img;
    }

    public void setUrl(String url) {
        this.sub_img = url;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
       // dest.writeString(name);
        dest.writeString(sub_img);

    }
}
