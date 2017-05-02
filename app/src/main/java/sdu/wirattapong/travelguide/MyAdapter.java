package sdu.wirattapong.travelguide;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

/**
 * Created by SuPerJoWTF on 2/5/2560.
 */

public class MyAdapter extends BaseAdapter{

    //Explicit
    private Context objContext;
    private  String[] travelStrings , sourceStrings , priceStrings;


    public MyAdapter(Context objContext ,String[] travelStrings,
                     String[] sourceStrings, String[] priceStrings) {
        this.objContext = objContext;
        this.travelStrings = travelStrings;
        this.sourceStrings = sourceStrings;
        this.priceStrings = priceStrings;
    }

    @Override
    public int getCount() {
        return travelStrings.length;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater objLayoutInflater = (LayoutInflater) objContext
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view1 = objLayoutInflater.inflate(R.layout.my_listview, parent, false);
        //Show Travel
        TextView travelTextView = (TextView) view1.findViewById(R.id.txtListTravel);
        travelTextView.setText(travelStrings[position]);
        //Show Price
        TextView priceTextView = (TextView) view1.findViewById(R.id.txtListPrice);
        priceTextView.setText(priceStrings[position]);
        //Show ImageTracel
        //ImageView travelImageView = (ImageView) view1.findViewById(R.id.imvListTravel);

        return view1;
    }
} //Main Class
