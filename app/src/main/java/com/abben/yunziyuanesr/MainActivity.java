package com.abben.yunziyuanesr;

import android.os.AsyncTask;
import android.os.Environment;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.abben.yunziyuanesr.fragment.FirstFragment;
import com.abben.yunziyuanesr.fragment.FourthFragment;
import com.abben.yunziyuanesr.fragment.SecondFragment;
import com.abben.yunziyuanesr.fragment.ThirdFragment;
import com.abben.yunziyuanesr.modle.Movie;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private ViewPager main_viewpager;
    private TabLayout sliding_tabs;
    private ArrayList<Movie> allMovies;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        String filePath = Environment.getExternalStorageDirectory() + "/Movies.json";
        new Gsonget().execute(filePath);


    }

    private void initView(){
        main_viewpager = (ViewPager) findViewById(R.id.main_viewpager);
        ArrayList<Fragment> fragments = new ArrayList<>();
        fragments.add(new FirstFragment());
        fragments.add(new SecondFragment());
        fragments.add(new ThirdFragment());
        fragments.add(new FourthFragment());

        CustomFragmentPagerAdapter customFragmentPagerAdapter = new CustomFragmentPagerAdapter(getSupportFragmentManager(),fragments);
        main_viewpager.setAdapter(customFragmentPagerAdapter);

        sliding_tabs = (TabLayout) findViewById(R.id.sliding_tabs);
        sliding_tabs.setupWithViewPager(main_viewpager);
        sliding_tabs.setTabMode(TabLayout.MODE_FIXED);
    }

    public ArrayList<Movie> getAllMovies(){
        return allMovies;
    }

    class Gsonget extends AsyncTask<String,Void,ArrayList<Movie>> {

        @Override
        protected ArrayList<Movie> doInBackground(String... strings) {
            Gson gson = new Gson();
            Reader reader = null;
            ArrayList<Movie> moverArrayList = null;
            try {

                reader = new FileReader(strings[0]);
                moverArrayList = gson.fromJson(reader,new TypeToken<List<Movie>>(){}.getType());
            } catch (FileNotFoundException e) {
            }
            return moverArrayList;
        }

        @Override
        protected void onPostExecute(ArrayList<Movie> movers) {
            if(movers!=null){
                allMovies = movers;
                initView();
            }
        }
    }
}
