package hu.ppke.itk.android.onlab.cellinfo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by User on 4.10.2018.
 */

public class ActualListAdapter extends ArrayAdapter<Measurement> {

    public ActualListAdapter(Context context, ArrayList<Measurement> measurements){super(context, 0, measurements);}

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        Measurement measurement = getItem(position);
        if(convertView == null){convertView = LayoutInflater.from(getContext()).inflate(R.layout.actual_list, parent, false);}

        TextView idText = (TextView) convertView.findViewById(R.id.idLabel);
        TextView lacText = (TextView) convertView.findViewById(R.id.lacData);
        TextView cidText = (TextView) convertView.findViewById(R.id.cellIdData);
        TextView mccmncText = (TextView) convertView.findViewById(R.id.mccmncData);
        TextView sgnText = (TextView) convertView.findViewById(R.id.signalData);

        switch(measurement.getBand()){
            case 900:
                idText.setText("GSM cell " + position);
                break;
            case 2000:
                idText.setText("WCDMA cell " + position);
                break;
            case 2600:
                idText.setText("LTE cell " + String.valueOf(position));
                break;
        }

        lacText.setText(String.valueOf(measurement.getLac()));
        cidText.setText(String.valueOf(measurement.getCid()));
        mccmncText.setText(String.valueOf(measurement.getMccmnc()));
        sgnText.setText(String.valueOf(measurement.getSgn()) + " dBm");

        return convertView;
    }
}
