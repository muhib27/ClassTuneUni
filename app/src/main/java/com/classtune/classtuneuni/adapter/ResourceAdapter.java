package com.classtune.classtuneuni.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import com.classtune.classtuneuni.R;
import com.classtune.classtuneuni.model.ComResult;
import com.classtune.classtuneuni.resource.Resource;
import com.classtune.classtuneuni.utils.AppUtility;

import java.io.File;
import java.util.ArrayList;
import java.util.List;


public class ResourceAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private ArrayList<Resource> mValues;
    private Context mContext;
    protected ItemListener mListener;
    private static final int HERO = 2;
    private static final int ITEM = 0;

    public ResourceAdapter(Context context) {
        mValues = new ArrayList<>();
        mContext = context;
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//        View view = LayoutInflater.from(mContext).inflate(R.layout.row_item, parent, false);
//        return new ViewHolder(view);
        RecyclerView.ViewHolder viewHolder = null;
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        switch (viewType) {
            case ITEM:
                View viewItem = inflater.inflate(R.layout.resource_item, parent, false);
                viewHolder = new MovieVH(viewItem);
                break;

        }
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, final int position) {
        final Resource result = mValues.get(position);
        switch (getItemViewType(position)) {
            case ITEM:
                final MovieVH itemHolder = (MovieVH) viewHolder;
                if(result.getChapterTitle()!=null)
                itemHolder.title.setText(result.getChapterTitle());
                if(result.getCourseName()!=null)
                itemHolder.subject.setText(result.getCourseName());
                if(result.getCreatedAt()!=null) {
                    String str = result.getCreatedAt();
                    String parts[] = str.split(" ");
                    if(parts[0].contains("-"))
                        itemHolder.date.setText(AppUtility.getDateString(parts[0], AppUtility.DATE_FORMAT_APP, AppUtility.DATE_FORMAT_SERVER));
                }
//                itemHolder.cell.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View view) {
//                        if (mListener != null) {
//                            mListener.onItemClick(result, position);
//                        }
//                        }
//                    });



//                Glide.with(mContext)
//                        .load(result.getThumbnail())
//                        .centerCrop()
//                        .placeholder(R.drawable.avatar)
//                        .into(itemHolder.imageView);
//
//                Glide.with(mContext)
//                        .load(result.getThumbnail())
//                        .apply(new RequestOptions()
//                                .placeholder(R.mipmap.ic_launcher)
//                                .fitCenter())
//                        .into(itemHolder.imageView);

                Glide.with(mContext)
                        .load(result.getThumbnail())
                        //.load("http://via.placeholder.com/300.png")
                        .listener(new RequestListener<Drawable>() {
                            @Override
                            public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                                // log exception
                                Log.e("TAG", "Error loading image", e);
                                return false; // important to return false so the error placeholder can be placed
                            }

                            @Override
                            public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                                return false;
                            }
                        })
                        .into(itemHolder.imageView);

                break;
        }
    }


    @Override
    public int getItemCount() {
        return mValues.size();
    }

    @Override
    public int getItemViewType(int position) {
//        if (position == 0) {
//            return HERO;
//        } else
        return ITEM;
    }

    public interface ItemListener {
        void onItemClick(ComResult item, int pos);
    }


    protected class MovieVH extends RecyclerView.ViewHolder {
        private TextView title;
        private TextView subject;
        private TextView date; // displays "year | language"
        private ImageView imageView;
        private ProgressBar mProgress;
        private TextView menuOption;
        private LinearLayout cell;
        CardView cardView;

        public MovieVH(View itemView) {
            super(itemView);

            title = itemView.findViewById(R.id.title);
            subject = itemView.findViewById(R.id.subject);
            date = itemView.findViewById(R.id.date);
            imageView = itemView.findViewById(R.id.imageView);
            cell = itemView.findViewById(R.id.cell);

        }
    }

    public void add(Resource r) {
        mValues.add(r);
        notifyItemInserted(mValues.size() - 1);
    }


    public void clearList(){
        mValues.clear();
    }

    public void addAllData(List<Resource> moveResults) {
        for (Resource result : moveResults) {
            add(result);
        }
    }

//    private DrawableRequestBuilder<String> loadThumbImage(@NonNull String posterPath) {
//        return Glide
//                .with(context)
//                .load(posterPath)
//                .diskCacheStrategy(DiskCacheStrategy.ALL)   // cache both original & resized image
//                .centerCrop()
//                .crossFade();
//    }
}