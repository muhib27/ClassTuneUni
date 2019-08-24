package com.classtune.classtuneuni.fragment;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.classtune.classtuneuni.R;
import com.classtune.classtuneuni.adapter.InfiniteAdapter;
import com.classtune.classtuneuni.adapter.PagerAdapter;

/**
 * A simple {@link Fragment} subclass.
 */
public class AssignmentViewFragment extends Fragment {

    private ViewPager pager;
    private int[] image = {R.drawable.img,R.drawable.img2};

    public AssignmentViewFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_assignment_view, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        pager = (ViewPager) view.findViewById(R.id.viewpager);
        pager.setOffscreenPageLimit(1);
        pager.setAdapter(new InfiniteAdapter(getActivity(),image));
//        PagerAdapter adapter = new FragmentPagerAdapter(getSupportFragmentManager()) {
////            int[] colours = new int[]{Color.CYAN, Color.GRAY, Color.MAGENTA, Color.LTGRAY, Color.GREEN, Color.WHITE,
////                    Color.YELLOW};
//
//            @Override
//            public int getCount() {
//                return 10;
//            }
//
//            @Override
//            public Fragment getItem(int position) {
//                Fragment fragment = new ColourFragment();
//                Bundle args = new Bundle();
//                //args.putInt("colour", colours[position]);
//                args.putInt("identifier", position);
//                fragment.setArguments(args);
//                return fragment;
//            }
//        };

//        // wrap pager to provide infinite paging with wrap-around
//        PagerAdapter wrappedAdapter = new InfinitePagerAdapter(adapter);
//
//        // actually an InfiniteViewPager
//        ViewPager viewPager = (ViewPager) findViewById(R.id.pager);
//        viewPager.setAdapter(wrappedAdapter);

    }
}
