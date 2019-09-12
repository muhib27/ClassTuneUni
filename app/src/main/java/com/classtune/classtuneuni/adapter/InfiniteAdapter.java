package com.classtune.classtuneuni.adapter;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.classtune.classtuneuni.R;
import com.classtune.classtuneuni.activity.MainActivity;
import com.classtune.classtuneuni.fragment.AssignmentPagerFragment;
import com.classtune.classtuneuni.fragment.AssignmentViewFragment;

/**
 * Created by IamDeveloper on 11/20/2016.
 */

public class InfiniteAdapter extends PagerAdapter {
    private Context activity;
    private int[] image;
    private int pos = 0;

    public InfiniteAdapter(Context activity,int[] image){
        this.activity = activity;
        this.image = image;
    }

    @Override
    public int getCount() {
        return 5;
    }


    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        ImageView img = new ImageView(activity);

//        Fragment fragment =new AssignmentPagerFragment();
//        gotoFragment(fragment, "assignmentPagerFragment");
        ((ViewPager)container).addView(img);
        img.setImageResource(image[pos]);

        LayoutInflater inflater = LayoutInflater.from(activity);
       // String imageUrl = Media.getImageUrl(myObject.getObjectId(), images.get(position).getImageId());
//        ViewGroup layout = (ViewGroup) inflater.inflate(R.layout.object_details_image, container, false);
        ViewGroup layout = (ViewGroup) inflater.inflate(R.layout.fragment_teacher_assi_details_view, container, false);
        TextView pagerImage = layout.findViewById(R.id.text);
        //Media.setImageFromUrl(pagerImage, imageUrl);//call to GlideApp or Picasso to load the image into the ImageView
        container.addView(layout);
        return layout;


//        if(pos >= image.length - 1)
//            pos =0;
//        else
//            ++pos;
//
//        Log.i("Posittion",pos+"");
//        return img;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == ((View)object);
    }


    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        ((ViewPager)container).removeView((View)object);
    }


    private void gotoFragment(Fragment fragment, String tag) {
        // load fragment
//        Bundle bundle = new Bundle();
//        bundle.putString("noticeId",noticeId);
//        fragment.setArguments(bundle);
        FragmentTransaction transaction = ((MainActivity)activity).getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.mainContainer, fragment, tag);
        //transaction.addToBackStack(null);
        transaction.commit();
    }

}
