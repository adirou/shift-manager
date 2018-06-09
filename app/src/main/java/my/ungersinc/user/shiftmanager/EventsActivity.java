package my.ungersinc.user.shiftmanager;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.ungersinc.shiftmanager.R;

import java.util.Locale;


public class EventsActivity extends ActionBarActivity {
    MySQLiteHelper db = new MySQLiteHelper(this);
    Intent intent1, intent;

    private AdView mAdView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        SharedPreferences sharedPref = this.getPreferences(Context.MODE_PRIVATE);
        boolean isChanged = sharedPref.getBoolean("changed", false);


        Work work;
        Work WorkHelper;
        double hourHelp,addPerHourHelp;
        Log.e("s1",String.valueOf(isChanged));
        if(!isChanged) {
            Log.e("s1", String.valueOf(isChanged));

            for (int i = 0; i < db.getAllWorks().size(); i++) {
                work = db.getAllWorks().get(i);
                if (work.getTypeOfWork() == 3) {
                    hourHelp = work.getHourIII();
                    if (work.getHourI() != 0)
                        hourHelp = work.getHourI();
                    else if (work.getHourII() != 0)
                        hourHelp = work.getHourII();

                    WorkHelper = new Work(work.getWorkName(), 2, work.getGlobal(), work.getStart(), 0, work.getAddPerHour()
                            , 0, 0, 0, 0, 0, hourHelp, work.getPerKilometer(), work.getBonusDrive(), work.getColor(), work.getTypeOfDrive(),"","","","",0,0);
                    db.updateWork(WorkHelper, work.getId());

                } else if (work.getTypeOfWork() == 4) {
                    hourHelp = work.getHourI();
                    if (work.getHourIII() != 0)
                        hourHelp = work.getHourIII();
                    else if (work.getHourII() != 0)
                        hourHelp = work.getHourII();

                    addPerHourHelp= work.getAddPerHour();
                    if(work.getStepII()!=0)
                        addPerHourHelp=work.getStepII();

                    WorkHelper = new Work(work.getWorkName(), 5, 0, work.getStart(), hourHelp, 0
                            , work.getPerHour(), 0,addPerHourHelp , 0, 0, 0, work.getPerKilometer(), work.getBonusDrive(), work.getColor(), work.getTypeOfDrive(),"","","","",0,0);
                    db.updateWork(WorkHelper, work.getId());
                }
            }
            for (int i = 0; i < db.getAllWorks().size(); i++) {
                Log.e("s1","!");
                work = db.getAllWorks().get(i);
                if (work.getTypeOfWork() > 4) {
                    work.setTypeOfWork(work.getTypeOfWork() - 2);
                    db.updateWork(work, work.getId());
                }

            }
            SharedPreferences.Editor editor = sharedPref.edit();
            editor.putBoolean("changed",true);
            editor.commit();

        }




        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_events);

        mAdView = (AdView) findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        ViewPager pager = (ViewPager) findViewById(R.id.pager);

        pager.setAdapter(new MyPagerAdapter(getSupportFragmentManager()));
        pager.arrowScroll(View.FOCUS_LEFT);
        if (Locale.getDefault().getLanguage().equals("iw") || Locale.getDefault().getLanguage().equals("he")) {

        }

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        if (toolbar != null) {
            setSupportActionBar(toolbar);
            // getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        }

        TabLayout tabLayout = (TabLayout) findViewById(R.id.sliding_tabs);
        tabLayout.setupWithViewPager(pager);


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);


        intent1 = new Intent(this, MainActivity.class);
        fab.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {


                startActivity(intent1);
            }
        });


    }


    private class MyPagerAdapter extends FragmentPagerAdapter {

        public MyPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int pos) {
            switch (pos) {

                case 0:
                    return showEvents.newInstance("FirstFragment, Instance 1");
                case 1:
                    return ViewOnCalender.newInstance("SecondFragment, Instance 1");
                case 2:
                    return SumMonth.newInstance("ThirdFragment, Instance 1");
                case 3:
                    return showWorks.newInstance("ForthFragment, Instance 1");
                default:
                    return showEvents.newInstance("ThirdFragment, Default");
            }
        }

        @Override
        public int getCount() {
            return 4;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            // Generate title based on item position
            switch (position) {
                case 0:
                    return (getString(R.string.EventsList));
                case 1:
                    return (getString(R.string.EventsCalender));
                case 2:
                    return (getString(R.string.EventsSummary));
                case 3:
                    return (getString(R.string.EventsWorks));
                default:
                    return "Tab";
            }
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_events, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        switch (id) {

            case R.id.action_addEvent:
                intent = new Intent(this, MainActivity.class);
                startActivity(intent);
                return true;

            case R.id.action_addWork:
                intent = new Intent(this, SetWorksActivity.class);
                startActivity(intent);
                return true;

        }
        return super.onOptionsItemSelected(item);


    }



}
