package hu.ppke.itk.android.onlab.cellinfo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by User on 4.9.2018.
 */

public class MeasurementListAdapter extends ArrayAdapter<Measurement> {

    public MeasurementListAdapter(Context context, ArrayList<Measurement> measurements){super(context, 0, measurements);}

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        Measurement measurement = getItem(position);
        if(convertView == null){convertView = LayoutInflater.from(getContext()).inflate(R.layout.archive_list, parent, false);}

        TextView idText = (TextView) convertView.findViewById(R.id.archive_id);
        TextView dateText = (TextView) convertView.findViewById(R.id.archive_date);
        TextView nameText = (TextView) convertView.findViewById(R.id.archive_name);
        TextView lacText = (TextView) convertView.findViewById(R.id.archive_lac);
        TextView cidText = (TextView) convertView.findViewById(R.id.archive_cid);
        TextView mccmncText = (TextView) convertView.findViewById(R.id.archive_mccmnc);
        TextView sgnText = (TextView) convertView.findViewById(R.id.archive_sgn);
        TextView bandText = (TextView) convertView.findViewById(R.id.archive_band);

        idText.setText(String.valueOf(measurement.getId()));

        String date = measurement.getYear()+"."+measurement.getMonth()+"."+measurement.getDay()+". "+measurement.getHour()+":"+measurement.getMinute()+":"+measurement.getSecond();
        dateText.setText(date);
        nameText.setText(measurement.getName());

        lacText.setText(String.valueOf(measurement.getLac()));
        cidText.setText(String.valueOf(measurement.getCid()));
        mccmncText.setText(String.valueOf(measurement.getMccmnc()));

        String signal = String.valueOf(measurement.getSgn()) + " dBm";
        sgnText.setText(signal);

        switch(measurement.getBand()){
            case 900:
                bandText.setText(String.valueOf(measurement.getBand()) + " MHz " + "(GSM)");
                break;
            case 2000:
                bandText.setText(String.valueOf(measurement.getBand()) + " MHz " + "(WCDMA)");
                break;
            case 2600:
                bandText.setText(String.valueOf(measurement.getBand()) + " MHz " + "(LTE)");
                break;
            default:
                bandText.setText(String.valueOf(measurement.getBand()));
                break;
        }

        return convertView;
    }
}
