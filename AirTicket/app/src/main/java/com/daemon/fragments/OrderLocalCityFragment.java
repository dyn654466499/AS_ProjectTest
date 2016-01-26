package com.daemon.fragments;

import android.app.Fragment;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.ScrollView;

import com.daemon.adapters.ConsumeLocalCityAdapter;
import com.daemon.airticket.R;
import com.daemon.beans.Resp_OrderLocalCityList;
import com.daemon.consts.Constants;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link OrderLocalCityFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link OrderLocalCityFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class OrderLocalCityFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public OrderLocalCityFragment() {
        // Required empty public constructor
    }
    Resp_OrderLocalCityList list;
    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment OrderLocalCityFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static OrderLocalCityFragment newInstance(Resp_OrderLocalCityList list) {
        OrderLocalCityFragment fragment = new OrderLocalCityFragment();
        Bundle args = new Bundle();
        args.putParcelable(Constants.KEY_PARCELABLE, list);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            list = getArguments().getParcelable(Constants.KEY_PARCELABLE);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View rootLayout = inflater.inflate(R.layout.fragment_order_local_city, container, false);
        ListView lv_my_consume_order_localCity = (ListView)rootLayout.findViewById(R.id.lv_my_consume_order_localCity);
        ConsumeLocalCityAdapter adapter = new ConsumeLocalCityAdapter(getActivity(),list);
        lv_my_consume_order_localCity.setAdapter(adapter);
        new Handler().post(new Runnable() {
            @Override
            public void run() {
                ScrollView sv = (ScrollView)rootLayout.findViewById(R.id.sv_my_consume_localCity);
                sv.smoothScrollTo(0,0);
            }
        });
        return rootLayout;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
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

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
