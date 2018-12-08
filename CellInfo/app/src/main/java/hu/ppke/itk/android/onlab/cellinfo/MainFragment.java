package hu.ppke.itk.android.onlab.cellinfo;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.telephony.CellIdentityGsm;
import android.telephony.CellIdentityLte;
import android.telephony.CellIdentityWcdma;
import android.telephony.CellInfo;
import android.telephony.CellInfoCdma;
import android.telephony.CellInfoGsm;
import android.telephony.CellInfoLte;
import android.telephony.CellInfoWcdma;
import android.telephony.CellSignalStrengthGsm;
import android.telephony.CellSignalStrengthLte;
import android.telephony.CellSignalStrengthWcdma;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created by User on 4.9.2018.
 */

public class MainFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public MainFragment() {
    }

    public static MainFragment newInstance(String param1, String param2) {
        MainFragment fragment = new MainFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.main_fragment, container, false);

        Button measureButton = (Button) view.findViewById(R.id.measure_button);

        final EditText nameField = (EditText) view.findViewById(R.id.measure_name);

        measureButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Calendar c = Calendar.getInstance();
//                int year = c.get(Calendar.YEAR);
//                int month = c.get(Calendar.MONTH);
//                int day = c.get(Calendar.DAY_OF_MONTH);
//                int hour = c.get(Calendar.HOUR_OF_DAY);
//                int minute = c.get(Calendar.MINUTE);
//                int second = c.get(Calendar.SECOND);
//                month++;
//                Toast.makeText(getActivity(), year + "." + month + "." + day + ". " + hour + ":" + minute + ":" + second, Toast.LENGTH_LONG).show();
                ArrayList<Measurement> measurements = getNetworkData();
                final ListView measurementListView = (ListView) getView().findViewById(R.id.actual_list);
                ActualListAdapter adapter = new ActualListAdapter(getActivity(), measurements);
                measurementListView.setAdapter(adapter);

                Measurement measurement;
                measurement = measurements.get(0);
                measurement.setName(nameField.getText().toString());

                SQLiteHandler db = new SQLiteHandler(getActivity());
                db.addMeasurement(measurement);
            }
        });

        return view;
    }

    public ArrayList<Measurement> getNetworkData() {

        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            //return;
        }

        TelephonyManager tm = (TelephonyManager) getActivity().getSystemService(Context.TELEPHONY_SERVICE);
        List<CellInfo> cellInfos = tm.getAllCellInfo();
        Calendar c = Calendar.getInstance();
        ArrayList<Measurement> measurements = new ArrayList<Measurement>();

        for(CellInfo cellInfo : cellInfos){
            //GSM data
            if (cellInfo instanceof CellInfoGsm) {
                CellIdentityGsm gsm_cell = ((CellInfoGsm) cellInfo).getCellIdentity();

                String cid = String.valueOf(gsm_cell.getCid());
                String lac = String.valueOf(gsm_cell.getLac());
                String mccmnc = String.valueOf(gsm_cell.getMcc())+String.valueOf(gsm_cell.getMnc());
                final CellSignalStrengthGsm gsm = ((CellInfoGsm) cellInfo).getCellSignalStrength();
                String signal = String.valueOf(gsm.getDbm());//+"#"+gsm.getLevel();

                //Toast.makeText(getActivity(),"CID: "+cid+" LAC: "+lac+" MCCMNC: "+mccmnc+" SGN: "+signal, Toast.LENGTH_LONG).show();

                int year = c.get(Calendar.YEAR);
                int month = c.get(Calendar.MONTH);
                int day = c.get(Calendar.DAY_OF_MONTH);
                int hour = c.get(Calendar.HOUR_OF_DAY);
                int minute = c.get(Calendar.MINUTE);
                int second = c.get(Calendar.SECOND);

                Measurement measurement = new Measurement(year, month, day, hour, minute, second, Integer.parseInt(lac), Integer.parseInt(cid), Integer.parseInt(mccmnc), Integer.parseInt(signal), 900);
                measurements.add(measurement);
            }

            //WCDMA
            else if (cellInfo instanceof CellInfoWcdma)
            {
                CellIdentityWcdma wcdma_cell = ((CellInfoWcdma) cellInfo).getCellIdentity();

                String cid = String.valueOf(wcdma_cell.getCid());
                String lac = String.valueOf(wcdma_cell.getLac());
                String mccmnc = String.valueOf(wcdma_cell.getMcc())+String.valueOf(wcdma_cell.getMnc());
                final CellSignalStrengthWcdma wcdma = ((CellInfoWcdma) cellInfo).getCellSignalStrength();
                String signal = String.valueOf(wcdma.getDbm());//+"#"+wcdma.getLevel();

                //Toast.makeText(getActivity(),"CID: "+cid+" LAC: "+lac+" MCCMNC: "+mccmnc+" SGN: "+signal, Toast.LENGTH_LONG).show();

                int year = c.get(Calendar.YEAR);
                int month = c.get(Calendar.MONTH);
                int day = c.get(Calendar.DAY_OF_MONTH);
                int hour = c.get(Calendar.HOUR_OF_DAY);
                int minute = c.get(Calendar.MINUTE);
                int second = c.get(Calendar.SECOND);

                Measurement measurement = new Measurement(year, month, day, hour, minute, second, Integer.parseInt(lac), Integer.parseInt(cid), Integer.parseInt(mccmnc), Integer.parseInt(signal), 2000);
                measurements.add(measurement);
            }

            //LTE
            else if (cellInfo instanceof CellInfoLte)
            {
                CellIdentityLte lte_cell = ((CellInfoLte) cellInfo).getCellIdentity();

                String cid = String.valueOf(lte_cell.getCi());
                String lac = String.valueOf(lte_cell.getPci());
                String mccmnc = String.valueOf(lte_cell.getMcc())+String.valueOf(lte_cell.getMnc());
                final CellSignalStrengthLte lte = ((CellInfoLte) cellInfo).getCellSignalStrength();
                String signal = String.valueOf(lte.getDbm());//+"#"+lte.getLevel();

                //Toast.makeText(getActivity(),"CID: "+cid+" LAC: "+lac+" MCCMNC: "+mccmnc+" SGN: "+signal, Toast.LENGTH_LONG).show();

                int year = c.get(Calendar.YEAR);
                int month = c.get(Calendar.MONTH);
                int day = c.get(Calendar.DAY_OF_MONTH);
                int hour = c.get(Calendar.HOUR_OF_DAY);
                int minute = c.get(Calendar.MINUTE);
                int second = c.get(Calendar.SECOND);

                Measurement measurement = new Measurement(year, month, day, hour, minute, second, Integer.parseInt(lac), Integer.parseInt(cid), Integer.parseInt(mccmnc), Integer.parseInt(signal), 2600);
                measurements.add(measurement);
            }
        }
        return measurements;
    }
}
