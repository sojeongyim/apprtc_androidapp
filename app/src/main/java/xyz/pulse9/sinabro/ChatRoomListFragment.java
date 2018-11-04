package xyz.pulse9.sinabro;

import android.content.Context;
import android.content.Intent;
import android.database.DataSetObserver;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.baoyz.swipemenulistview.SwipeMenu;
import com.baoyz.swipemenulistview.SwipeMenuCreator;
import com.baoyz.swipemenulistview.SwipeMenuItem;
import com.baoyz.swipemenulistview.SwipeMenuListView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import static android.app.Activity.RESULT_OK;

public class ChatRoomListFragment extends Fragment{
    private DatabaseReference userDatabase;

    final String TAG = "ChatRoomListFrag";

    FirebaseUser curuser;
    public SwipeMenuListView listView;
    private TextView noTxt;
    ChatRoomAdapter chatRoomAdapter;
    SwipeMenuCreator creator;

    public ChatRoomListFragment() {
        // Required empty public constructor
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        chatRoomAdapter = new ChatRoomAdapter(this.getActivity().getApplicationContext(),R.layout.chat_room);
        curuser = FirebaseAuth.getInstance().getCurrentUser();

        listView = getView().findViewById(R.id.chat_list);
        noTxt = getView().findViewById(R.id.noTxt);
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
                Intent intent = new Intent(getActivity(), ConnectActivity.class);
                intent.putExtra("chatroomname", roomname);
                intent.putExtra("receiverUID", chatRoomAdapter.getItem(i).getReceiver());
                startActivity(intent);
            }
        });

        setRoomDB();
        setSwipeMenu();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_chat_list, container, false);
    }
    public void setRoomDB() {
        userDatabase= FirebaseDatabase.getInstance().getReference("users").child(curuser.getUid()).child("rooms");
        userDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.getChildrenCount()==0)
                {
                    noTxt.setVisibility(View.VISIBLE);
                }
                else
                {
                    noTxt.setVisibility(View.GONE);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });

        userDatabase.addChildEventListener(new ChildEventListener() {
            String chatroomname;
            String lastcontents;
            String lastTime;
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                ChatRoom chatRoom;
                chatRoom = dataSnapshot.getValue(ChatRoom.class);
                chatRoom.setRoomName(dataSnapshot.getRef().getKey());
                chatRoomAdapter.add(chatRoom);
                chatRoomAdapter.sortList();
                chatRoomAdapter.notifyDataSetChanged();
                listView.setAdapter(chatRoomAdapter);
                if(dataSnapshot.getChildrenCount()==0)
                {
                    noTxt.setVisibility(View.VISIBLE);
                }
                else
                {
                    noTxt.setVisibility(View.GONE);
                }
            }
            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                chatroomname = dataSnapshot.getRef().getKey();
                lastcontents = dataSnapshot.child("lastcontents").getValue().toString();
                lastTime = dataSnapshot.child("time").getValue().toString();
                chatRoomAdapter.refresh(chatroomname, lastcontents, lastTime);
                chatRoomAdapter.sortList();
                chatRoomAdapter.notifyDataSetChanged();
                if(dataSnapshot.getChildrenCount()==0)
                {
                    noTxt.setVisibility(View.VISIBLE);
                }
                listView.setAdapter(chatRoomAdapter);
            }
            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.getChildrenCount()==0)
                {
                    noTxt.setVisibility(View.VISIBLE);
                }
                else
                {
                    noTxt.setVisibility(View.GONE);
                }
            }
            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
    public void setSwipeMenu()
    {
        creator = new SwipeMenuCreator() {
            @Override
            public void create(SwipeMenu menu) {
                // create "open" item
                SwipeMenuItem openItem = new SwipeMenuItem(
                        getActivity().getApplicationContext());
                openItem.setBackground(new ColorDrawable(Color.rgb(0xD6, 0xF4,
                        0x56)));
                openItem.setWidth(250);
                openItem.setTitle("Delete");
                openItem.setTitleSize(18);
                openItem.setTitleColor(Color.WHITE);
                menu.addMenuItem(openItem);
            }
        };
        listView.setMenuCreator(creator);
        listView.setOnMenuItemClickListener(new SwipeMenuListView.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(int position, SwipeMenu menu, int index) {
                userDatabase= FirebaseDatabase.getInstance().getReference("users").child(curuser.getUid()).child("rooms");
                userDatabase.child(chatRoomAdapter.getItem(position).getRoomName()).removeValue();
                chatRoomAdapter.delete(chatRoomAdapter.getItem(position));
                return false;
            }
        });
    }
}
