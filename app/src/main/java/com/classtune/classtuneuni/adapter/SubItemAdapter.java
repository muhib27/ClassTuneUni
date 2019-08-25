package com.classtune.classtuneuni.adapter;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.classtune.classtuneuni.R;
import com.classtune.classtuneuni.activity.MainActivity;
import com.classtune.classtuneuni.fragment.TeacherNoticeDetails;
import com.classtune.classtuneuni.model.SubItem;
import com.classtune.classtuneuni.response.CourseSection;
import com.classtune.classtuneuni.response.Notice;
import com.classtune.classtuneuni.response.NoticeInfo;

import java.util.List;

public class SubItemAdapter extends RecyclerView.Adapter<SubItemAdapter.SubItemViewHolder> {

    private List<Notice> subItemList;
    Context context;

    SubItemAdapter(List<Notice> subItemList) {
        this.subItemList = subItemList;
    }

    public SubItemAdapter(List<Notice> subItemList, Context context) {
        this.subItemList = subItemList;
        this.context = context;
    }

    @NonNull
    @Override
    public SubItemViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.layout_sub_item, viewGroup, false);
        return new SubItemViewHolder(view);
    }

    String courseName = "";
    @Override
    public void onBindViewHolder(@NonNull SubItemViewHolder subItemViewHolder, final int i) {
        final Notice subItem = subItemList.get(i);

        int si = subItem.getCourses().size();
        courseName = "";
        for(int l=0; l<subItem.getCourses().size();l++)
        {
            courseName = courseName + subItem.getCourses().get(l)  + " ";
        }
        subItemViewHolder.title.setText(courseName);
        subItemViewHolder.description.setText(subItem.getNotice().getTitle());
        subItemViewHolder.time.setText(getTime(subItem.getNotice().getCreatedAt().substring(11, 16)));
        subItemViewHolder.rl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Toast.makeText(context, "" + subItem.getId(), Toast.LENGTH_LONG).show();
                Fragment fragment = new TeacherNoticeDetails();
                gotoFragment(fragment, "teacherNoticeDetails", subItem.getNotice().getId());
            }
        });
    }

    @Override
    public int getItemCount() {
        return subItemList.size();
    }

    class SubItemViewHolder extends RecyclerView.ViewHolder {
        TextView title, time;
        TextView description;
        LinearLayout rl;

        SubItemViewHolder(View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.title);
            time = itemView.findViewById(R.id.time);
            description = itemView.findViewById(R.id.description);
            rl = itemView.findViewById(R.id.rl);

        }
    }

    private void gotoFragment(Fragment fragment, String tag, String noticeId) {
        // load fragment
        Bundle bundle = new Bundle();
        bundle.putString("noticeId",noticeId);
        fragment.setArguments(bundle);
        FragmentTransaction transaction = ((MainActivity)context).getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.mainContainer, fragment, tag);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    private String getTime(String st){
        String time = "";
        if(st.length()>2) {

            if (Integer.parseInt(st.substring(0, 2)) >= 12) {
                time = st + " PM";
            } else {
                time = st + " AM";
            }

        }
        return time;
    }
}
