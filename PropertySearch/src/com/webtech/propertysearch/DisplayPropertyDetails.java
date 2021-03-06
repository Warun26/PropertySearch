package com.webtech.propertysearch;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.Signature;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Bundle;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBar.Tab;
import android.support.v7.app.ActionBarActivity;
import android.util.Base64;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;

public class DisplayPropertyDetails extends ActionBarActivity implements ActionBar.TabListener{

	private FragmentPagerAdapter adapterViewPager;
	private ViewPager vpPager;
	private ActionBar actionbar;
	public static String JSONString = "";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		supportRequestWindowFeature(Window.FEATURE_ACTION_BAR);
		setContentView(R.layout.activity_display_property_details);
		Intent intent = getIntent();
		JSONString = intent.getStringExtra(MainActivity.JSONSTRING);
		//setUpView();
		try {
	        PackageInfo info = getPackageManager().getPackageInfo(
	                "com.webtech.propertysearch", 
	                PackageManager.GET_SIGNATURES);
	        for (android.content.pm.Signature signature : info.signatures) {
	            MessageDigest md = MessageDigest.getInstance("SHA");
	            md.update(signature.toByteArray());
	            Log.d("KeyHash:", Base64.encodeToString(md.digest(), Base64.DEFAULT));
	            }
	    } catch (NameNotFoundException e) {

	    } catch (NoSuchAlgorithmException e) {

	    }
		vpPager = (ViewPager)findViewById(R.id.vpPager);
		adapterViewPager = new MyPagerAdapter(getSupportFragmentManager());
		vpPager.setAdapter(adapterViewPager);
		getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setLogo(R.drawable.ic_launcher);
        getSupportActionBar().setDisplayUseLogoEnabled(true);
		actionbar = getSupportActionBar();
		actionbar.setDisplayUseLogoEnabled(true);
		actionbar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
		actionbar.addTab(actionbar.newTab().setText("Basic Info").setTabListener(this));
		actionbar.addTab(actionbar.newTab().setText("Historical Zestimates").setTabListener(this));
		vpPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            /**
             * on swipe select the respective tab
             * */
            @Override
            public void onPageSelected(int position) {
                actionbar.setSelectedNavigationItem(position);
            }

            @Override
            public void onPageScrolled(int arg0, float arg1, int arg2) { }

            @Override
            public void onPageScrollStateChanged(int arg0) { }
        });
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.display_property_details, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	private void setUpView() {
		vpPager = (ViewPager)findViewById(R.id.vpPager);
		adapterViewPager = new MyPagerAdapter(getSupportFragmentManager());
		vpPager.setAdapter(adapterViewPager);
		vpPager.setCurrentItem(0);
	}
	
	
	public static class MyPagerAdapter extends FragmentPagerAdapter {
	    private static int NUM_ITEMS = 2;

	        public MyPagerAdapter(android.support.v4.app.FragmentManager fragmentManager) {
	            super(fragmentManager);
	        }

	        // Returns total number of pages
	        @Override
	        public int getCount() {
	            return NUM_ITEMS;
	        }

	        // Returns the fragment to display for that page
	        @Override
	        public android.support.v4.app.Fragment getItem(int position) {
	            switch (position) {
	            case 0: // Fragment # 0 - This will show FirstFragment
	                return BasicInformation.newInstance(JSONString);
	            case 1: // Fragment # 0 - This will show FirstFragment different title
	                return HistoricalZestimates.newInstance(JSONString);
	            default:
	                return null;
	            }
	        }

	        // Returns the page title for the top indicator
	        @Override
	        public CharSequence getPageTitle(int position) {
	            switch(position) {
	            case 0: return "Basic Info";
	            case 1: return "Historical Zestimates";
	            }
	            return "Page " + position;
	        }
	    }


	@Override
	public void onTabReselected(Tab arg0, FragmentTransaction arg1) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onTabSelected(Tab arg0, FragmentTransaction arg1) {
		// TODO Auto-generated method stub
		vpPager.setCurrentItem(arg0.getPosition());
		
	}

	@Override
	public void onTabUnselected(Tab arg0, FragmentTransaction arg1) {
		// TODO Auto-generated method stub
		
	}
	
	
}
