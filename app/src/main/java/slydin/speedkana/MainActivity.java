package slydin.speedkana;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.Locale;


/**
 * SpeedKana tests your kana recognition skills, be sure to sound off the characters.
 * Copyright (C) 2015  Jeric Derama
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

public class MainActivity extends ActionBarActivity implements ActionBar.TabListener {

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    SectionsPagerAdapter mSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    ViewPager mViewPager;
    static double spinnerValue = 0.1;
    static int levelValue = 1;
    private static final String TAG = "speedkana-MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Set up the action bar.
        final ActionBar actionBar = getSupportActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.pager);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        // When swiping between different sections, select the corresponding
        // tab. We can also use ActionBar.Tab#select() to do this if we have
        // a reference to the Tab.
        mViewPager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                actionBar.setSelectedNavigationItem(position);
            }
        });

        // For each of the sections in the app, add a tab to the action bar.
        for (int i = 0; i < mSectionsPagerAdapter.getCount(); i++) {
            // Create a tab with text corresponding to the page title defined by
            // the adapter. Also specify this Activity object, which implements
            // the TabListener interface, as the callback (listener) for when
            // this tab is selected.
            actionBar.addTab(
                    actionBar.newTab()
                            .setText(mSectionsPagerAdapter.getPageTitle(i))
                            .setTabListener(this));
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onTabSelected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
        // When the given tab is selected, switch to the corresponding page in
        // the ViewPager.
        mViewPager.setCurrentItem(tab.getPosition());
    }

    @Override
    public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
    }

    @Override
    public void onTabReselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
    }

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            return PlaceholderFragment.newInstance(position + 1);
        }

        @Override
        public int getCount() {
            // Show 2 total pages.
            return 2;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            Locale l = Locale.getDefault();
            switch (position) {
                case 0:
                    return getString(R.string.title_section1).toUpperCase(l);
                case 1:
                    return getString(R.string.title_section2).toUpperCase(l);
                //case 2:
                //    return getString(R.string.title_section3).toUpperCase(l);
            }
            return null;
        }
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment implements AdapterView.OnItemSelectedListener {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_main, container, false);
            // Add the textview to indicate section
            Bundle args = this.getArguments();
            final int section = args.getInt(ARG_SECTION_NUMBER);
            // Set Tab-specific values
            Log.i(TAG, "Section: " + section);
            int card_visual = R.string.hiragana_section;
            switch (section) {
                case 2:
                    card_visual = R.string.katakana_section;
                    break;
                //case 3:
                //    card_visual = R.string.kanji_section;
                //   break;
            }
            // An example of the kana that the user will be tested on
            final TextView exampleCard = (TextView) rootView.findViewById(R.id.section_label);
            exampleCard.setText(getString(card_visual));

            // Time Spinner
            final Spinner timeSpinner = (Spinner) rootView.findViewById(R.id.time_spinner);
            timeSpinner.setOnItemSelectedListener(this);
            ArrayAdapter<CharSequence> timeAdapter = ArrayAdapter.createFromResource(rootView.getContext(), R.array.time_array, R.layout.spinner_layout);
            timeAdapter.setDropDownViewResource(R.layout.spinner_layout);
            timeSpinner.setAdapter(timeAdapter);

            // Level Spinner
            final Spinner levelSpinner = (Spinner) rootView.findViewById(R.id.level_spinner);
            levelSpinner.setOnItemSelectedListener(this);
            ArrayAdapter<CharSequence> levelAdapter = ArrayAdapter.createFromResource(rootView.getContext(), R.array.level_array, R.layout.spinner_layout);
            levelAdapter.setDropDownViewResource(R.layout.spinner_layout);
            levelSpinner.setAdapter(levelAdapter);

            // Start!
            final Button startButton = (Button) rootView.findViewById(R.id.start_button);
            startButton.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    Intent speedRun = new Intent(v.getContext(), slydin.speedkana.SpeedActivity.class);
                    // Input the spinner value
                    Log.i(TAG, "" + spinnerValue);
                    Log.i(TAG, "" + levelValue);
                    speedRun.putExtra("spinnerValue", spinnerValue);
                    speedRun.putExtra("levelValue", levelValue);
                    speedRun.putExtra("section", section);
                    v.getContext().startActivity(speedRun);
                }
            });
            return rootView;
        }

        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            // Get the string from the chosen position and convert
            if (parent.getAdapter().getCount() > 3)
                spinnerValue = (double) Double.parseDouble(parent.getAdapter().getItem(position).toString());
            else if (parent.getAdapter().getCount() < 3)
                levelValue = (int) Integer.parseInt(parent.getAdapter().getItem(position).toString());
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {
        }
    }

}
