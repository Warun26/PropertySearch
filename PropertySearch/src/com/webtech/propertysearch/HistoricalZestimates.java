package com.webtech.propertysearch;

import org.json.JSONObject;

import android.app.ActionBar.LayoutParams;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageSwitcher;
import android.widget.ImageView;
import android.widget.TextSwitcher;
import android.widget.TextView;
import android.widget.ViewSwitcher.ViewFactory;

public class HistoricalZestimates extends Fragment {
    // Store instance variables
    private String page;
    private static TextSwitcher textSwitcher;
    private static ImageSwitcher imageSwitcher;
    private static final String[] TEXTS = { "Historical Zestimates for the past 1 year", 
    										"Historical Zestimates for the past 5 years", 
    										"Historical Zestimates for the past 10 years" };
    private static final String[] FILENAMES = {"1year.jpg", "5year.jpg", "10year.jpg"};
    private static int count = 0;

    private static Animation in;
    private static Animation out;
    
    // newInstance constructor for creating fragment with arguments
    public static HistoricalZestimates newInstance(String page) {
        HistoricalZestimates fragmentFirst = new HistoricalZestimates();
        Bundle args = new Bundle();
        args.putString("someTitle", page);
        fragmentFirst.setArguments(args);
        return fragmentFirst;
    }

    // Store instance variables based on arguments passed
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        page = getArguments().getString("someTitle");
    }

    // Inflate the view for the fragment based on layout XML
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.historical_zestimates_tab, container, false);
        TextView addressLabel = (TextView) view.findViewById(R.id.address);
        try {
        	JSONObject result = new JSONObject(page).getJSONObject("result");
        	String address = "<a href=\"" + result.getString("homedetails") +"\">";
        	address = result.getString("street") +", ";
        	address += result.getString("city") + ", ";
        	address += result.getString("state") + "-" + result.getString("zipcode");
        	address += "</a>";
        	addressLabel.setText(Html.fromHtml(address));
        } catch(Exception e) {}
        
        imageSwitcher = (ImageSwitcher)view.findViewById(R.id.ZestimateCharts);
        textSwitcher = (TextSwitcher)view.findViewById(R.id.chartYear);
        in = AnimationUtils.loadAnimation(view.getContext(), android.R.anim.slide_out_right);
        out = AnimationUtils.loadAnimation(view.getContext(),android.R.anim.slide_out_right);

        imageSwitcher.setFactory(new ViewFactory() {

        	   @Override
        	   public View makeView() {
        	      ImageView myView = new ImageView(getActivity().getApplicationContext());
        	      myView.setScaleType(ImageView.ScaleType.FIT_CENTER);
        	      myView.setLayoutParams(new ImageSwitcher.LayoutParams(LayoutParams.
        	      FILL_PARENT,LayoutParams.FILL_PARENT));
        	      return myView;
        	       }
        	   });
        textSwitcher.setFactory(new ViewFactory() {
            @Override
            public View makeView() {
            	TextView myView = new TextView(getActivity().getApplicationContext());
            	myView.setTextSize(16);
            	myView.setGravity(Gravity.CENTER);
            	myView.setTypeface(Typeface.DEFAULT_BOLD);
            	myView.setTextColor(Color.BLACK);
                return myView;
            	}
        	});
        Uri uri = Uri.parse(Environment.getExternalStorageDirectory().toString() + "/" + FILENAMES[0]);
        imageSwitcher.setImageURI(uri);
        textSwitcher.setText(TEXTS[0]);
        Button nextButton = (Button) view.findViewById(R.id.nextImageButton);
        nextButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				count = (count + 1) % 3;
		        Uri uri = Uri.parse(Environment.getExternalStorageDirectory().toString() + "/" + FILENAMES[count]);
		        imageSwitcher.setImageURI(uri);
		        textSwitcher.setText(TEXTS[count]);
			}
		});
        Button previousButton = (Button) view.findViewById(R.id.previousImageButton);
        previousButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				count = (count + 2) % 3;
		        Uri uri = Uri.parse(Environment.getExternalStorageDirectory().toString() + "/" + FILENAMES[count]);
		        imageSwitcher.setImageURI(uri);
		        textSwitcher.setText(TEXTS[count]);
			}
		});
        String branding = "<p>&copy; Zillow, Inc., 2006-2014<br />";
        branding += "Use is subject to <a href=\"http://www.zillow.com/corp/Terms.htm\">Terms of Use</a><br />";
        branding += "<a href=\"http://www.zillow.com/wikipages/What-is-a-Zestimate/\">What's a Zestimate?</a></p>";
        TextView brand = (TextView) view.findViewById(R.id.branding2);
        brand.setText(Html.fromHtml(branding));
        brand.setMovementMethod(LinkMovementMethod.getInstance());
        return view;
    }
    
    public void nextImage(View view){
        imageSwitcher.setInAnimation(in);
        imageSwitcher.setOutAnimation(out);
        textSwitcher.setInAnimation(in);
        textSwitcher.setOutAnimation(out);
        Uri uri = Uri.parse(Environment.getExternalStorageDirectory().toString() + "/" + FILENAMES[count]);
        imageSwitcher.setImageURI(uri);
        textSwitcher.setText(TEXTS[count]);
        count = (count + 1) % 3;
     }
     public void previousImage(View view){
        imageSwitcher.setInAnimation(out);
        imageSwitcher.setOutAnimation(in);
        textSwitcher.setInAnimation(in);
        textSwitcher.setOutAnimation(out);
        Uri uri = Uri.parse(Environment.getExternalStorageDirectory().toString() + "/" + FILENAMES[count]);
        imageSwitcher.setImageURI(uri);
        textSwitcher.setText(TEXTS[count]);
        count = (count - 1) % 3;
     }
}