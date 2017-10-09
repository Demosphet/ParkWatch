package com.sachith.parkwatch;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by sachs on 9/10/2017.
 */

public class ThreeColumn_ListAdapter extends ArrayAdapter<Vehicle> {

    private LayoutInflater mInflater;
    private ArrayList<Vehicle> vehicles;
    private int mViewResourceId;

    public ThreeColumn_ListAdapter(Context context, int textViewResourceId, ArrayList<Vehicle> vehicles){
        super(context, textViewResourceId, vehicles);
        this.vehicles = vehicles;
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mViewResourceId = textViewResourceId;
    }

    public View getView(int position, View convertView, ViewGroup parents){
        convertView = mInflater.inflate(mViewResourceId,null);

        Vehicle vehicle = vehicles.get(position);

        if (vehicle != null){
            TextView timeStamptTextView = (TextView) convertView.findViewById(R.id.timeStamptTextView);
            TextView registrationValueTextView = (TextView) convertView.findViewById(R.id.registrationValueTextView);
            TextView makeValueTextView = (TextView) convertView.findViewById(R.id.makeValueTextView);
            TextView modelValueTextView = (TextView) convertView.findViewById(R.id.modelValueTextView);
            TextView colourValueTextView = (TextView) convertView.findViewById(R.id.colourValueTextView);
            TextView typeValueTextView = (TextView) convertView.findViewById(R.id.typeValueTextView);
            TextView carSpaceValueTextView = (TextView) convertView.findViewById(R.id.carSpaceValueTextView);

            if(timeStamptTextView != null){
                timeStamptTextView.setText(vehicle.getTimestamp());

            } if(registrationValueTextView != null){
                registrationValueTextView.setText(vehicle.getRegistration());

            } if(makeValueTextView != null){
                makeValueTextView.setText(vehicle.getMake());

            } if(modelValueTextView != null){
                modelValueTextView.setText(vehicle.getModel());

            } if(colourValueTextView != null){
                colourValueTextView.setText(vehicle.getColour());

            } if(typeValueTextView != null){
                typeValueTextView.setText(vehicle.getType());

            } if(carSpaceValueTextView != null){
                carSpaceValueTextView.setText(vehicle.getCarSpaces());

            }
        }
        return convertView;
    }
}
