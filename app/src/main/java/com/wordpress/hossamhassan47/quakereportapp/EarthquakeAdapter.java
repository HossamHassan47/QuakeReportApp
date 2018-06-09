package com.wordpress.hossamhassan47.quakereportapp;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class EarthquakeAdapter extends ArrayAdapter<Earthquake> {

    public EarthquakeAdapter(Context context, List<Earthquake> objects) {
        super(context, 0, objects);
    }
    private static final String LOCATION_SEPARATOR = " of ";

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listItemView = convertView;

        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(R.layout.earthquake_list_item, parent, false);
        }

        Earthquake current = getItem(position);

        TextView txtMag = listItemView.findViewById(R.id.text_view_magnitude);
        txtMag.setText(formatMagnitude(current.getMagnitude()));

        String originalLocation = current.getLocation();
        String primaryLocation;
        String locationOffset;

        if (originalLocation.contains(LOCATION_SEPARATOR)) {
            String[] parts = originalLocation.split(LOCATION_SEPARATOR);
            locationOffset = parts[0] + LOCATION_SEPARATOR;
            primaryLocation = parts[1];
        } else {
            locationOffset = getContext().getString(R.string.near_the);
            primaryLocation = originalLocation;
        }

        TextView txtLocOff = listItemView.findViewById(R.id.text_view_location_offset);
        txtLocOff.setText(locationOffset);
        TextView txtLoc = listItemView.findViewById(R.id.text_view_location);
        txtLoc.setText(primaryLocation);

        Date date = new Date(current.getDate());

        TextView txtDate = listItemView.findViewById(R.id.text_view_date);
        txtDate.setText(formatDate(date));

        TextView txtTime = listItemView.findViewById(R.id.text_view_time);
        txtTime.setText(formatTime(date));

        return listItemView;
    }

    /**
     * Return the formatted date string (i.e. "Mar 3, 1984") from a Date object.
     */
    private String formatDate(Date dateObject) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("LLL dd, yyyy");
        return dateFormat.format(dateObject);
    }

    /**
     * Return the formatted date string (i.e. "4:30 PM") from a Date object.
     */
    private String formatTime(Date dateObject) {
        SimpleDateFormat timeFormat = new SimpleDateFormat("h:mm a");
        return timeFormat.format(dateObject);
    }

    private String formatMagnitude(double doubleObject) {
        DecimalFormat decimalFormat = new DecimalFormat("0.0");
        return decimalFormat.format(doubleObject);
    }
}
