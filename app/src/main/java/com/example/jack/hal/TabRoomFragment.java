package com.example.jack.hal;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.jack.hal.services.AsynDelegate;
import com.example.jack.hal.services.AsynTaskRooms;
import com.example.jack.hal.services.HttpAsynTask;

import java.util.ArrayList;
import java.util.Arrays;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link TabRoomFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link TabRoomFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TabRoomFragment extends Fragment implements AsynDelegate {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    private ListView listView;
    private ArrayAdapter<String> arrayAdapter;
    private String[] rooms;
    private int[] roomIds;
    private Handler handler;
    private Activity activity;


    @Override
    public void asyncComplete(boolean success) {
        rooms = new String[Global.rooms.size()];
        roomIds = new int[rooms.length];
        for (int i = 0; i < Global.rooms.size(); i++) {
            rooms[i] = Global.rooms.get(i).getName();
            roomIds[i] = Global.rooms.get(i).getId();
        }

        arrayAdapter.clear();
        arrayAdapter = new ArrayAdapter<>(getActivity().getBaseContext(), R.layout.layout_single_textview, rooms);

        this.arrayAdapter.notifyDataSetChanged();
        listView.setAdapter(this.arrayAdapter);
    }

    public TabRoomFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment TabRoomFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static TabRoomFragment newInstance(String param1, String param2) {
        TabRoomFragment fragment = new TabRoomFragment();
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

//        new HttpAsynTask(this).execute("get-states");

        new AsynTaskRooms(this).execute();

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_tab_room, container, false);


        rooms = new String[0];


        arrayAdapter = new ArrayAdapter<>(getActivity().getBaseContext(), R.layout.layout_single_textview, rooms);
        listView = (ListView) view.findViewById(R.id.tab_rooms_listview);
        listView.setAdapter(arrayAdapter);
        listView.setOnItemClickListener(new RoomsItemListener());

        return view;
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
        this.activity = getActivity();
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
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    private class RoomsItemListener implements AdapterView.OnItemClickListener {

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            Intent sampleRoom = new Intent(getActivity(), RoomActivity.class);

            String room_name = parent.getItemAtPosition(position).toString();



            sampleRoom.putExtra("room_name", room_name);
            sampleRoom.putExtra("roomId", roomIds[position]);
            getActivity().startActivity(sampleRoom);

        }
    }
}
