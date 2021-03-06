/*
 *  Copyright 2014 The WebRTC Project Authors. All rights reserved.
 *
 *  Use of this source code is governed by a BSD-style license
 *  that can be found in the LICENSE file in the root of the source
 *  tree. An additional intellectual property rights grant can be found
 *  in the file PATENTS.  All contributing project authors may
 *  be found in the AUTHORS file in the root of the source tree.
 */

package xyz.pulse9.sinabro;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.DataSetObserver;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.webkit.URLUtil;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

/**
 * Handles the initial setup where the user selects which room to join.
 */
public class ConnectActivity extends AppCompatActivity {

    public static final int VIDCALL = 100;
    private DatabaseReference myDatabase;
    private DatabaseReference userDatabase;

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        intent.putExtra("choice", 1);
        startActivity(intent);
    }
    private final static String TAG = "ConnectActivity";
    ChatAdapter chatAdapter;
    public static String chatroomname = "none";
    FirebaseDatabase database;
    private String uid;
    private String receiveruid;
    private ImageButton vidBtn;
    private ImageButton backBtn;
    private ImageButton sendBtn;
    private EditText sendMsg;
    private SharedPreferences sharedPref;
    private String keyprefResolution;
    private TextView friendsid;
    private String keyprefFps;
    private String keyprefVideoBitrateType;
    private String keyprefVideoBitrateValue;
    private String keyprefAudioBitrateType;
    private String keyprefAudioBitrateValue;
    private String keyprefRoomServerUrl;
    private String receivernick;
    private String receivertoken;
    private String sendernick;
    private String receiverphoto;
    private String senderphoto;
    private ListView listView;
    FirebaseUser curuser;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        registerReceiver(mMessageReceiver, new IntentFilter("fgsdgdfgg"));
        database = FirebaseDatabase.getInstance();
        setContentView(R.layout.activity_chatting);
        friendsid = findViewById(R.id.friendId);
        sendBtn = findViewById(R.id.sendButton);
        sendBtn.setEnabled(false);
        sendMsg =findViewById(R.id.sendMsg);
        sendMsg.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(charSequence.toString().trim().length()==0){
                    sendBtn.setEnabled(false);
                } else {
                    sendBtn.setEnabled(true);
                }
            }
            @Override
            public void afterTextChanged(Editable editable) {
            }
        });
        backBtn = findViewById(R.id.backBtn);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        curuser = FirebaseAuth.getInstance().getCurrentUser();

        Intent intent2 = getIntent();
        uid = curuser.getUid();
        sendernick = curuser.getDisplayName();
        senderphoto = curuser.getPhotoUrl().toString();
        chatroomname = intent2.getStringExtra("chatroomname");
        receiveruid = intent2.getStringExtra("receiverUID");
        userDatabase = database.getReference("users").child(receiveruid);

        onRead();


        userDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                receiverphoto = dataSnapshot.child("photo").getValue().toString();
                receivernick = dataSnapshot.child("nickname").getValue().toString();
                receivertoken = dataSnapshot.child("token").getValue().toString();
                friendsid.setText(receivernick.toUpperCase());
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        if(!chatroomname.equals("none")) {
            userDatabase = database.getReference("users").child(uid).child("rooms").child(chatroomname);
            userDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    ChatRoom mChatroom = dataSnapshot.getValue(ChatRoom.class);
                    setChatRoom(mChatroom);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
//        }
        }

        initDB(chatroomname);
        chatAdapter = new ChatAdapter(this.getApplicationContext(), R.layout.chat_message);
        listView = findViewById(R.id.chatListview);
        listView.setAdapter(chatAdapter);
        listView.setTranscriptMode(ListView.TRANSCRIPT_MODE_ALWAYS_SCROLL); // 이게 필수

// When message is added, it makes listview to scroll last message
        chatAdapter.registerDataSetObserver(new DataSetObserver() {
            @Override

            public void onChanged() {
                super.onChanged();
                listView.setSelection(chatAdapter.getCount() - 1);
            }
        });
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                if(chatAdapter.getItem(i).getType().equals("3"))
                {
                    connectToRoom(chatAdapter.getItem(i).getVidroomname(), chatAdapter.getItem(i).getMessagename());
                }
            }
        });

        vidBtn = (ImageButton) findViewById(R.id.vidBtn);
        vidBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final BottomSheetDialog bottomSheetDialog = BottomSheetDialog.getInstance();
                bottomSheetDialog.show(getSupportFragmentManager(), "bottomSheet");
                bottomSheetDialog.setListener(new BottomSheetDialog.PollButtonClickListener() {
                    @Override
                    public void onClick(View view) {
                        switch (view.getId()) {
                            case R.id.msgLo:
                                break;
                            case R.id.emailLo:
                                Intent intent = new Intent(ConnectActivity.this, mDatePicker.class);
                                startActivityForResult(intent, 1);
                                break;
                        }
                        bottomSheetDialog.dismiss();
                    }

                    @Override
                    public void onAnyButtonClick(Object data) {
                    }
                });
            }
        });
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.CAMERA, Manifest.permission.RECORD_AUDIO, Manifest.permission.INTERNET, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.ACCESS_NETWORK_STATE, Manifest.permission.BLUETOOTH},
                    202);
        }
        // Get setting keys.
        PreferenceManager.setDefaultValues(this, R.xml.preferences, false);
        sharedPref = PreferenceManager.getDefaultSharedPreferences(this);
        keyprefResolution = getString(R.string.pref_resolution_key);
        keyprefFps = getString(R.string.pref_fps_key);
        keyprefVideoBitrateType = getString(R.string.pref_maxvideobitrate_key);
        keyprefVideoBitrateValue = getString(R.string.pref_maxvideobitratevalue_key);
        keyprefAudioBitrateType = getString(R.string.pref_startaudiobitrate_key);
        keyprefAudioBitrateValue = getString(R.string.pref_startaudiobitratevalue_key);
        keyprefRoomServerUrl = getString(R.string.pref_room_server_url_key);
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode==RESULT_OK) {
            if (requestCode == 1) {
                long result = data.getLongExtra("result", 0);
                Log.d("JANGMIN", "OnActivity Result");
                Intent intent = new Intent(ConnectActivity.this, mTimePicker.class);
                intent.putExtra("result",result);
                startActivityForResult(intent, 2);
            }else if(requestCode == 2 ){//requestCode == 2
                DatabaseReference ref;
                if (chatroomname.equals("none")) {
                    // It Duplicate Chatrooms
                    ref = database.getReference("message").push();
                    chatroomname = ref.getKey();
                    initDB(chatroomname);
                }
                ref = database.getReference("message").child(chatroomname);
                long result2 = data.getLongExtra("result", 0);
                Message tmp = new Message("1", uid, receiveruid, receivertoken);   //test
                String tmp_name = ref.push().getKey();
                tmp.setMessagename(tmp_name);
                tmp.setContents("Planing Conference");
                tmp.setPhoto(senderphoto);
                tmp.setDate(String.valueOf(result2));
                tmp.setChk("0");
                tmp.setChatroomname(chatroomname);
                ref.child(tmp_name).setValue(tmp);

                ChatRoom updateChatRoom = new ChatRoom(chatroomname, receiveruid, receivernick, receiverphoto, tmp.getContents(), tmp.getSenddate());
                ChatRoom updateChatRoom2 = new ChatRoom(chatroomname, uid, sendernick, senderphoto, tmp.getContents(), tmp.getSenddate());
                userDatabase.child(uid).child("rooms").child(chatroomname).setValue(updateChatRoom);
                incRoomCnt(receiveruid, chatroomname, updateChatRoom2);
            }
            else if(requestCode==VIDCALL)
            {
                Long result = data.getLongExtra("millisec", 0);
                Log.d("JANGMIN", "How long ~~? : " + String.valueOf(result));
                Message tmpMessage = new Message("4", String.valueOf(result/1000));
                DatabaseReference ref;
                ref = database.getReference("message").child(chatroomname);
                ref.child(data.getStringExtra("MsgName")+"1").setValue(tmpMessage);
            }
        }
    }
    private String sharedPrefGetString(int attributeId, String intentName, int defaultId, boolean useFromIntent) {
        String defaultValue = getString(defaultId);
        if (useFromIntent) {
            String value = getIntent().getStringExtra(intentName);
            if (value != null) {
                return value;
            }
            return defaultValue;
        } else {
            String attributeName = getString(attributeId);
            return sharedPref.getString(attributeName, defaultValue);
        }
    }
    private boolean sharedPrefGetBoolean(int attributeId, String intentName, int defaultId, boolean useFromIntent) {
        boolean defaultValue = Boolean.valueOf(getString(defaultId));
        if (useFromIntent) {
            return getIntent().getBooleanExtra(intentName, defaultValue);
        } else {
            String attributeName = getString(attributeId);
            return sharedPref.getBoolean(attributeName, defaultValue);
        }
    }
    private int sharedPrefGetInteger(int attributeId, String intentName, int defaultId, boolean useFromIntent) {
        String defaultString = getString(defaultId);
        int defaultValue = Integer.parseInt(defaultString);
        if (useFromIntent) {
            return getIntent().getIntExtra(intentName, defaultValue);
        } else {
            String attributeName = getString(attributeId);
            String value = sharedPref.getString(attributeName, defaultString);
            try {
                return Integer.parseInt(value);
            } catch (NumberFormatException e) {
                Log.e(TAG, "Wrong setting for: " + attributeName + ":" + value);
                return defaultValue;
            }
        }
    }
    private void connectToRoom(String roomId, String messageName) {
        String roomUrl = sharedPref.getString(
                keyprefRoomServerUrl, getString(R.string.pref_room_server_url_default));
        // Video call enabled flag.
        boolean videoCallEnabled = sharedPrefGetBoolean(R.string.pref_videocall_key,
                CallActivity.EXTRA_VIDEO_CALL, R.string.pref_videocall_default, false);
        // Use screencapture option.
        boolean useScreencapture = sharedPrefGetBoolean(R.string.pref_screencapture_key,
                CallActivity.EXTRA_SCREENCAPTURE, R.string.pref_screencapture_default, false);
        // Use Camera2 option.
        boolean useCamera2 = sharedPrefGetBoolean(R.string.pref_camera2_key, CallActivity.EXTRA_CAMERA2,
                R.string.pref_camera2_default, false);
        // Get default codecs.
        String videoCodec = sharedPrefGetString(R.string.pref_videocodec_key,
                CallActivity.EXTRA_VIDEOCODEC, R.string.pref_videocodec_default, false);
        String audioCodec = sharedPrefGetString(R.string.pref_audiocodec_key,
                CallActivity.EXTRA_AUDIOCODEC, R.string.pref_audiocodec_default, false);
        // Check HW codec flag.
        boolean hwCodec = sharedPrefGetBoolean(R.string.pref_hwcodec_key,
                CallActivity.EXTRA_HWCODEC_ENABLED, R.string.pref_hwcodec_default, false);
        // Check Capture to texture.
        boolean captureToTexture = sharedPrefGetBoolean(R.string.pref_capturetotexture_key,
                CallActivity.EXTRA_CAPTURETOTEXTURE_ENABLED, R.string.pref_capturetotexture_default,
                false);
        // Check FlexFEC.
        boolean flexfecEnabled = sharedPrefGetBoolean(R.string.pref_flexfec_key,
                CallActivity.EXTRA_FLEXFEC_ENABLED, R.string.pref_flexfec_default, false);

        // Check Disable Audio Processing flag.
        boolean noAudioProcessing = sharedPrefGetBoolean(R.string.pref_noaudioprocessing_key,
                CallActivity.EXTRA_NOAUDIOPROCESSING_ENABLED, R.string.pref_noaudioprocessing_default,
                false);

        // Check Disable Audio Processing flag.
        boolean aecDump = sharedPrefGetBoolean(R.string.pref_aecdump_key,
                CallActivity.EXTRA_AECDUMP_ENABLED, R.string.pref_aecdump_default, false);

        // Check OpenSL ES enabled flag.
        boolean useOpenSLES = sharedPrefGetBoolean(R.string.pref_opensles_key,
                CallActivity.EXTRA_OPENSLES_ENABLED, R.string.pref_opensles_default, false);

        // Check Disable built-in AEC flag.
        boolean disableBuiltInAEC = sharedPrefGetBoolean(R.string.pref_disable_built_in_aec_key,
                CallActivity.EXTRA_DISABLE_BUILT_IN_AEC, R.string.pref_disable_built_in_aec_default,
                false);

        // Check Disable built-in AGC flag.
        boolean disableBuiltInAGC = sharedPrefGetBoolean(R.string.pref_disable_built_in_agc_key,
                CallActivity.EXTRA_DISABLE_BUILT_IN_AGC, R.string.pref_disable_built_in_agc_default,
                false);

        // Check Disable built-in NS flag.
        boolean disableBuiltInNS = sharedPrefGetBoolean(R.string.pref_disable_built_in_ns_key,
                CallActivity.EXTRA_DISABLE_BUILT_IN_NS, R.string.pref_disable_built_in_ns_default,
                false);

        // Check Enable level control.
        boolean enableLevelControl = sharedPrefGetBoolean(R.string.pref_enable_level_control_key,
                CallActivity.EXTRA_ENABLE_LEVEL_CONTROL, R.string.pref_enable_level_control_key,
                false);

        // Check Disable gain control
        boolean disableWebRtcAGCAndHPF = sharedPrefGetBoolean(
                R.string.pref_disable_webrtc_agc_and_hpf_key, CallActivity.EXTRA_DISABLE_WEBRTC_AGC_AND_HPF,
                R.string.pref_disable_webrtc_agc_and_hpf_key, false);

        // Get video resolution from settings.
        int videoWidth = 0;
        int videoHeight = 0;

        if (videoWidth == 0 && videoHeight == 0) {
            String resolution =
                    sharedPref.getString(keyprefResolution, getString(R.string.pref_resolution_default));
            String[] dimensions = resolution.split("[ x]+");
            if (dimensions.length == 2) {
                try {
                    videoWidth = Integer.parseInt(dimensions[0]);
                    videoHeight = Integer.parseInt(dimensions[1]);
                } catch (NumberFormatException e) {
                    videoWidth = 0;
                    videoHeight = 0;
                    Log.e(TAG, "Wrong video resolution setting: " + resolution);
                }
            }
        }

        // Get camera fps from settings.
        int cameraFps = 0;

        if (cameraFps == 0) {
            String fps = sharedPref.getString(keyprefFps, getString(R.string.pref_fps_default));
            String[] fpsValues = fps.split("[ x]+");
            if (fpsValues.length == 2) {
                try {
                    cameraFps = Integer.parseInt(fpsValues[0]);
                } catch (NumberFormatException e) {
                    cameraFps = 0;
                    Log.e(TAG, "Wrong camera fps setting: " + fps);
                }
            }
        }

        // Check capture quality slider flag.
        boolean captureQualitySlider = sharedPrefGetBoolean(R.string.pref_capturequalityslider_key,
                CallActivity.EXTRA_VIDEO_CAPTUREQUALITYSLIDER_ENABLED,
                R.string.pref_capturequalityslider_default, false);

        // Get video and audio start bitrate.
        int videoStartBitrate = 0;

        if (videoStartBitrate == 0) {
            String bitrateTypeDefault = getString(R.string.pref_maxvideobitrate_default);
            String bitrateType = sharedPref.getString(keyprefVideoBitrateType, bitrateTypeDefault);
            if (!bitrateType.equals(bitrateTypeDefault)) {
                String bitrateValue = sharedPref.getString(
                        keyprefVideoBitrateValue, getString(R.string.pref_maxvideobitratevalue_default));
                videoStartBitrate = Integer.parseInt(bitrateValue);
            }
        }

        int audioStartBitrate = 0;

        if (audioStartBitrate == 0) {
            String bitrateTypeDefault = getString(R.string.pref_startaudiobitrate_default);
            String bitrateType = sharedPref.getString(keyprefAudioBitrateType, bitrateTypeDefault);
            if (!bitrateType.equals(bitrateTypeDefault)) {
                String bitrateValue = sharedPref.getString(
                        keyprefAudioBitrateValue, getString(R.string.pref_startaudiobitratevalue_default));
                audioStartBitrate = Integer.parseInt(bitrateValue);
            }
        }

        // Check statistics display option.
        boolean displayHud = sharedPrefGetBoolean(R.string.pref_displayhud_key,
                CallActivity.EXTRA_DISPLAY_HUD, R.string.pref_displayhud_default, false);

        boolean tracing = sharedPrefGetBoolean(R.string.pref_tracing_key, CallActivity.EXTRA_TRACING,
                R.string.pref_tracing_default, false);

        // Get datachannel options
        boolean dataChannelEnabled = sharedPrefGetBoolean(R.string.pref_enable_datachannel_key,
                CallActivity.EXTRA_DATA_CHANNEL_ENABLED, R.string.pref_enable_datachannel_default,
                false);
        boolean ordered = sharedPrefGetBoolean(R.string.pref_ordered_key, CallActivity.EXTRA_ORDERED,
                R.string.pref_ordered_default, false);
        boolean negotiated = sharedPrefGetBoolean(R.string.pref_negotiated_key,
                CallActivity.EXTRA_NEGOTIATED, R.string.pref_negotiated_default, false);
        int maxRetrMs = sharedPrefGetInteger(R.string.pref_max_retransmit_time_ms_key,
                CallActivity.EXTRA_MAX_RETRANSMITS_MS, R.string.pref_max_retransmit_time_ms_default,
                false);
        int maxRetr =
                sharedPrefGetInteger(R.string.pref_max_retransmits_key, CallActivity.EXTRA_MAX_RETRANSMITS,
                        R.string.pref_max_retransmits_default, false);
        int id = sharedPrefGetInteger(R.string.pref_data_id_key, CallActivity.EXTRA_ID,
                R.string.pref_data_id_default, false);
        String protocol = sharedPrefGetString(R.string.pref_data_protocol_key,
                CallActivity.EXTRA_PROTOCOL, R.string.pref_data_protocol_default, false);

        // Start AppRTCMobile activity.
        Log.d(TAG, "Connecting to room " + roomId + " at URL " + roomUrl);
        if (validateUrl(roomUrl)) {
            Uri uri = Uri.parse(roomUrl);
            Intent intent = new Intent(this, CallActivity.class);
            intent.setData(uri);
            intent.putExtra(CallActivity.EXTRA_ROOMID, roomId);
            intent.putExtra(CallActivity.EXTRA_LOOPBACK, false);
            intent.putExtra(CallActivity.EXTRA_VIDEO_CALL, videoCallEnabled);
            intent.putExtra(CallActivity.EXTRA_SCREENCAPTURE, useScreencapture);
            intent.putExtra(CallActivity.EXTRA_CAMERA2, useCamera2);
            intent.putExtra(CallActivity.EXTRA_VIDEO_WIDTH, videoWidth);
            intent.putExtra(CallActivity.EXTRA_VIDEO_HEIGHT, videoHeight);
            intent.putExtra(CallActivity.EXTRA_VIDEO_FPS, cameraFps);
            intent.putExtra(CallActivity.EXTRA_VIDEO_CAPTUREQUALITYSLIDER_ENABLED, captureQualitySlider);
            intent.putExtra(CallActivity.EXTRA_VIDEO_BITRATE, videoStartBitrate);
            intent.putExtra(CallActivity.EXTRA_VIDEOCODEC, videoCodec);
            intent.putExtra(CallActivity.EXTRA_HWCODEC_ENABLED, hwCodec);
            intent.putExtra(CallActivity.EXTRA_CAPTURETOTEXTURE_ENABLED, captureToTexture);
            intent.putExtra(CallActivity.EXTRA_FLEXFEC_ENABLED, flexfecEnabled);
            intent.putExtra(CallActivity.EXTRA_NOAUDIOPROCESSING_ENABLED, noAudioProcessing);
            intent.putExtra(CallActivity.EXTRA_AECDUMP_ENABLED, aecDump);
            intent.putExtra(CallActivity.EXTRA_OPENSLES_ENABLED, useOpenSLES);
            intent.putExtra(CallActivity.EXTRA_DISABLE_BUILT_IN_AEC, disableBuiltInAEC);
            intent.putExtra(CallActivity.EXTRA_DISABLE_BUILT_IN_AGC, disableBuiltInAGC);
            intent.putExtra(CallActivity.EXTRA_DISABLE_BUILT_IN_NS, disableBuiltInNS);
            intent.putExtra(CallActivity.EXTRA_ENABLE_LEVEL_CONTROL, enableLevelControl);
            intent.putExtra(CallActivity.EXTRA_DISABLE_WEBRTC_AGC_AND_HPF, disableWebRtcAGCAndHPF);
            intent.putExtra(CallActivity.EXTRA_AUDIO_BITRATE, audioStartBitrate);
            intent.putExtra(CallActivity.EXTRA_AUDIOCODEC, audioCodec);
            intent.putExtra(CallActivity.EXTRA_DISPLAY_HUD, displayHud);
            intent.putExtra(CallActivity.EXTRA_TRACING, tracing);
            intent.putExtra(CallActivity.EXTRA_CMDLINE, false);
            intent.putExtra(CallActivity.EXTRA_RUNTIME, 0);
            intent.putExtra(CallActivity.EXTRA_DATA_CHANNEL_ENABLED, dataChannelEnabled);

            if (dataChannelEnabled) {
                intent.putExtra(CallActivity.EXTRA_ORDERED, ordered);
                intent.putExtra(CallActivity.EXTRA_MAX_RETRANSMITS_MS, maxRetrMs);
                intent.putExtra(CallActivity.EXTRA_MAX_RETRANSMITS, maxRetr);
                intent.putExtra(CallActivity.EXTRA_PROTOCOL, protocol);
                intent.putExtra(CallActivity.EXTRA_NEGOTIATED, negotiated);
                intent.putExtra(CallActivity.EXTRA_ID, id);
            }
            intent.putExtra("MsgName", messageName);
            startActivityForResult(intent, VIDCALL);
        }
    }
    private boolean validateUrl(String url) {
        if (URLUtil.isHttpsUrl(url) || URLUtil.isHttpUrl(url)) {
            return true;
        }

        new AlertDialog.Builder(this)
                .setTitle(getText(R.string.invalid_url_title))
                .setMessage(getString(R.string.invalid_url_text, url))
                .setCancelable(false)
                .setNeutralButton(R.string.ok,
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        })
                .create()
                .show();
        return false;
    }
    public void sendMessage(View view) {
        // Nick(Sender), Token(Receiver))
        Message tmpM = new Message("0", uid, receiveruid,receivertoken);
        tmpM.setPhoto(senderphoto);
        tmpM.setSendernick(sendernick);
        tmpM.setContents(sendMsg.getText().toString());
        DatabaseReference ref;
        if (chatroomname.equals("none")) {
            ref = database.getReference("message").push();
            chatroomname = ref.getKey();
            initDB(chatroomname);
        }

        ref = database.getReference("message").child(chatroomname);
        ChatRoom updateChatRoom = new ChatRoom(chatroomname, receiveruid, receivernick, receiverphoto, tmpM.getContents(), tmpM.getSenddate());
        ChatRoom updateChatRoom2 = new ChatRoom(chatroomname, uid, sendernick, senderphoto, tmpM.getContents(), tmpM.getSenddate());
        incRoomCnt(receiveruid, chatroomname, updateChatRoom2);
        userDatabase.child(uid).child("rooms").child(chatroomname).setValue(updateChatRoom);
        ref.push().setValue(tmpM);
        sendMsg.setText("");
    }
    private void incRoomCnt(final String receiveruid, String roomname, final ChatRoom chatRoom) {
        final DatabaseReference tmp = userDatabase.child(receiveruid).child("rooms").child(roomname);
        tmp.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.hasChild("cnt"))
                {
                    Log.d("JANGMIN", "haschild");
                    chatRoom.setCnt((long)dataSnapshot.child("cnt").getValue()+1);
                    userDatabase.child(receiveruid).child("rooms").child(chatroomname).setValue(chatRoom);
                }
                else
                {
                    chatRoom.setCnt((long)1);
                    userDatabase.child(receiveruid).child("rooms").child(chatroomname).setValue(chatRoom);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }

    public void initDB(final String rommname) {
        myDatabase = database.getReference("message").child(rommname);
        userDatabase = database.getReference("users");
        myDatabase.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                Message mMessage;
                mMessage = dataSnapshot.getValue(Message.class);
                chatAdapter.add(mMessage);
//                userDatabase.child(uid).child("rooms").child(chatroomname).child("lastcontents").setValue(mMessage.getContents());
//                userDatabase.child(uid).child("rooms").child(chatroomname).child("time").setValue(mMessage.getSendDate());
//                userDatabase.child(receiveruid).child("rooms").child(chatroomname).child("lastcontents").setValue(mMessage.getContents());
//                userDatabase.child(receiveruid).child("rooms").child(chatroomname).child("time").setValue(mMessage.getSendDate());
            }
            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s)
            {
                Message mMessage;
                mMessage = dataSnapshot.getValue(Message.class);
                if(mMessage.getType().equals("1")) {
                    chatAdapter.getItemByName(mMessage.getMessagename()).setChk(mMessage.getChk());
                    chatAdapter.notifyDataSetChanged();
                }
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
    }
    @Override
    protected void onStop() {
        super.onStop();
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
    @Override
    public void onPause() {
        super.onPause();
    }
    public void setChatRoom(ChatRoom temp) {
        receiveruid = temp.getReceiver();
        receivernick = temp.getNickname();
        receiverphoto = temp.getPhoto();
        friendsid.setText(receivernick.toUpperCase());
    }
    public void onRead()
    {
        final DatabaseReference tmp = database.getReference("users").child(curuser.getUid()).child("rooms").child(chatroomname);
        tmp.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.hasChild("cnt"))
                {
                    dataSnapshot.child("cnt").getRef().setValue(0);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}