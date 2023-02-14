package com.sp.saveurfood1.Settings;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.sp.saveurfood1.Firebase.ProfileActivity;
import com.sp.saveurfood1.R;
import com.sp.saveurfood1.Storage.introguide;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link fourfragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class fourfragment extends Fragment implements View.OnClickListener {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    ImageView moreinfo;
    ImageView myprofile;

    ImageView inst;
    ImageView twitter;
    ImageView facebook;
    ImageView interestingnews;
    Button buttoni;


    public fourfragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment fourfragment.
     */
    // TODO: Rename and change types and number of parameters
    public static fourfragment newInstance(String param1, String param2) {
        fourfragment fragment = new fourfragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }





    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
        setHasOptionsMenu(true);

    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        // First clear current all the menu items
        menu.clear();

        // Add the new menu items
        inflater.inflate(R.menu.menu_guide, menu);

        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.guide:
                Intent intent=new Intent(getView().getContext(), introguide.class);
                startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_fourfragment, container, false);
        View twitter = view.findViewById(R.id.twitter);
        View instagram = view.findViewById(R.id.instagram);
        View facebook = view.findViewById(R.id.facebook);
        View moreinfo = view.findViewById(R.id.moreinfo);
        View interestingnews = view.findViewById(R.id.news);
        View myprofile=view.findViewById(R.id.myprofile);
        instagram.setOnClickListener(this);
        moreinfo.setOnClickListener(this);
        myprofile.setOnClickListener(this);
        twitter.setOnClickListener(this);
        facebook.setOnClickListener(this);
        interestingnews.setOnClickListener(this);
        myprofile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(v.getContext(), ProfileActivity.class);
                startActivity(intent);
            }
        });
        moreinfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(v.getContext(), com.sp.saveurfood1.Settings.moreinfo.class);
                startActivity(intent);


            }
        });
        interestingnews.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), InterestingNews.class);
                startActivity(intent);

            }


        });


        return view;

    }

    @Override
    public void onClick(View v) {

        switch(v.getId()){
            case R.id.twitter:
                gotoUrl("https://www.twitter.com/saveurfood1/");
                break;
            case R.id.instagram:
                gotoUrl("https://www.instagram.com/saveurfoodinsta/");
                break;
            case R.id.facebook:
                gotoUrl("https://www.facebook.com/profile.php?id=100070153763059");
                break;





        }


        // implements your things
    }







    private void gotoUrl(String s) {
        Uri uri= Uri.parse(s);
        startActivity(new Intent(Intent.ACTION_VIEW,uri));
    }
}