package xyz.pulse9.sinabro;

import android.content.Context;
import android.content.Intent;
import android.database.DataSetObserver;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import static android.app.Activity.RESULT_OK;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ChatRoomListFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ChatRoomListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */

public class ChatRoomListFragment extends Fragment{
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private DatabaseReference userDatabase;
    Button testBtn;

    final String TAG = "ChatRoomListFrag";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    FirebaseUser curuser;
    private OnFragmentInteractionListener mListener;
    public ListView listView;
    ChatRoomAdapter chatRoomAdapter;

    public ChatRoomListFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ChatRoomListFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ChatRoomListFragment newInstance(String param1, String param2) {
        ChatRoomListFragment fragment = new ChatRoomListFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode==RESULT_OK)
        {
            switch (requestCode)
            {
                case 3000:
//
//                    chatRoomAdapter.refresh(data.getStringExtra("roomname"), data.getStringExtra("title"), data.getStringExtra("time"));
//                    chatRoomAdapter.notifyDataSetChanged();
//                    listView.setAdapter(chatRoomAdapter);
                    break;
            }

        }
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        chatRoomAdapter = new ChatRoomAdapter(this.getActivity().getApplicationContext(),R.layout.chat_room);
        curuser = FirebaseAuth.getInstance().getCurrentUser();


        listView = (ListView)getView().findViewById(R.id.chat_list);
        listView.setAdapter(chatRoomAdapter);
        listView.setTranscriptMode(ListView.TRANSCRIPT_MODE_ALWAYS_SCROLL);
        chatRoomAdapter.registerDataSetObserver(new DataSetObserver() {
            @Override
            public void onChanged() {
                super.onChanged();
                listView.setSelection(chatRoomAdapter.getCount()-1);
            }
        });


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String roomname = chatRoomAdapter.getItem(i).getRoomName();

//                Intent intent = new Intent(getActivity(), ChattingActivity.class);
                Intent intent = new Intent(getActivity(), ConnectActivity.class);
                intent.putExtra("chatroomname", roomname);
//                intent.putExtra("receiveruid", chatRoomAdapter.getItem(i).getReceiver());
                intent.putExtra("receiveruid", "receiver");
                intent.putExtra("uid", curuser.getUid());
                startActivityForResult(intent, 3000);

//                startActivity(intent);
            }
        });

        userDatabase= FirebaseDatabase.getInstance().getReference("users").child(curuser.getUid()).child("rooms");

        userDatabase.addChildEventListener(new ChildEventListener() {
            String roomname;
            String title;
            String receiver;
            String lastTime;

            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                roomname = dataSnapshot.getRef().getKey();
                receiver = dataSnapshot.child("receiver").getValue().toString();
                title = dataSnapshot.child("lastContents").getValue().toString();
                lastTime = dataSnapshot.child("time").getValue().toString();

                ChatRoom chatRoom = new ChatRoom(roomname,receiver, title, lastTime);
                chatRoomAdapter.add(chatRoom);
            }
            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                roomname = dataSnapshot.getRef().getKey();
                title = dataSnapshot.child("lastContents").getValue().toString();
                lastTime = dataSnapshot.child("time").getValue().toString();
                chatRoomAdapter.refresh(roomname, title, lastTime);
                chatRoomAdapter.notifyDataSetChanged();
                listView.setAdapter(chatRoomAdapter);

                Log.d("jangmin",roomname);

            }
            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }
            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        super.onActivityCreated(savedInstanceState);
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
        // Inflate the layout for this fragment

        return inflater.inflate(R.layout.fragment_chat_list, container, false);
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
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}

