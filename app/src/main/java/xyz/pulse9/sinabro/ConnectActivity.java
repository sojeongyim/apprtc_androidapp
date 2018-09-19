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
import android.app.Notification;
import android.app.NotificationManager;
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
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.webkit.URLUtil;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;

/**
 * Handles the initial setup where the user selects which room to join.
 */
public class ConnectActivity extends AppCompatActivity {

    private DatabaseReference myDatabase;
    private DatabaseReference userDatabase;

    private final static String TAG = "ConnectActivity";
    ChatAdapter chatAdapter;
    String chatroomname = "none";
    private String uid;
    private String receiveruid;
    ImageButton vidBtn;


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

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chatting);
        friendsid = findViewById(R.id.friendId);

        Intent intent2 = getIntent();
        chatroomname = intent2.getStringExtra("chatroomname");
        if (!chatroomname.equals("none")) {
            initDB(chatroomname);
        }
        uid = intent2.getStringExtra("uid");
        receiveruid = intent2.getStringExtra("receiveruid");
        receivernick = intent2.getStringExtra("receivernick");

        friendsid.setText(receivernick);

        chatAdapter = new ChatAdapter(this.getApplicationContext(), R.layout.chat_message);
        final ListView listView = (ListView) findViewById(R.id.chatListview);
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
                                connectToRoom(chatroomname);
                                break;
                            case R.id.emailLo:
//                                Message tmp = new Message(1, uid);
//                                FirebaseDatabase database = FirebaseDatabase.getInstance();
                                //ss
                                Intent intent = new Intent(ConnectActivity.this, DateTimePicker.class);
                                startActivityForResult(intent, 1);
                                //
//                                if (chatroomname.equals("none"))
//                                {
//                                    Log.d(TAG, "You should chat atleast one sentences");
//                                }
//                                else
//                                    {
//                                    DatabaseReference ref = database.getReference("message").child(chatroomname);
//                                    ref.push().setValue(tmp);
//                                }
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
        if (requestCode == 1) {
            if (resultCode == RESULT_OK) {
                String result = data.getStringExtra("result");
                Toast.makeText(ConnectActivity.this, result, Toast.LENGTH_SHORT).show();
                Message tmp = new Message(1, uid,result);
                FirebaseDatabase database = FirebaseDatabase.getInstance();
                if (chatroomname.equals("none"))
                {
                    Log.d(TAG, "You should chat atleast one sentences");
                }
                else
                {
                    DatabaseReference ref = database.getReference("message").child(chatroomname);
                    ref.push().setValue(tmp);
                }
            }
        }

    }


    @Override
    public void onPause() {
        super.onPause();
//    String room = roomEditText.getText().toString();
//    String roomListJson = new JSONArray(roomList).toString();
//    SharedPreferences.Editor editor = sharedPref.edit();
//    editor.putString(keyprefRoom, "roomEditText");
//    editor.putString(keyprefRoomList, roomListJson);
//    editor.commit();
    }

    @Override
    public void onResume() {
        super.onResume();
//    String room = sharedPref.getString(keyprefRoom, "");
////    roomEditText.setText(room);
//    roomList = new ArrayList<String>();
//    String roomListJson = sharedPref.getString(keyprefRoomList, null);
//    if (roomListJson != null) {
//      try {
//        JSONArray jsonArray = new JSONArray(roomListJson);
//        for (int i = 0; i < jsonArray.length(); i++) {
//          roomList.add(jsonArray.get(i).toString());
//        }
//      } catch (JSONException e) {
//        Log.e(TAG, "Failed to load room list: " + e.toString());
//      }
//    }
//    adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, roomList);
//    roomListView.setAdapter(adapter);
//    if (adapter.getCount() > 0) {
//      roomListView.requestFocus();
//      roomListView.setItemChecked(0, true);
//    }
    }


    /**
     * Get a value from the shared preference or from the intent, if it does not
     * exist the default is used.
     */
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

    /**
     * Get a value from the shared preference or from the intent, if it does not
     * exist the default is used.
     */
    private boolean sharedPrefGetBoolean(int attributeId, String intentName, int defaultId, boolean useFromIntent) {
        boolean defaultValue = Boolean.valueOf(getString(defaultId));
        if (useFromIntent) {
            return getIntent().getBooleanExtra(intentName, defaultValue);
        } else {
            String attributeName = getString(attributeId);
            return sharedPref.getBoolean(attributeName, defaultValue);
        }
    }

    /**
     * Get a value from the shared preference or from the intent, if it does not
     * exist the default is used.
     */
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

    private void connectToRoom(String roomId) {


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


            startActivity(intent);
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
        EditText sendMsg = (EditText) findViewById(R.id.sendMsg);
        if(!sendMsg.getText().equals("")) {

            final FirebaseDatabase database = FirebaseDatabase.getInstance();
            Message mMessage = new Message(uid, receiveruid, sendMsg.getText().toString());
            mMessage.setReceiver(receiveruid);
            DatabaseReference ref;
            if (chatroomname.equals("none")) {
                ref = database.getReference("message").push();
                chatroomname = ref.getKey();
                initDB(chatroomname);
            }
            ref = database.getReference("message").child(chatroomname);
            ref.push().setValue(mMessage);
            sendMsg.setText("");
        }
    }

    public void initDB(final String rommname) {
        myDatabase = FirebaseDatabase.getInstance().getReference("message").child(rommname);
        userDatabase = FirebaseDatabase.getInstance().getReference("users");

        myDatabase.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                String type = dataSnapshot.child("type").getValue().toString();
                Message mMessage;
                if (type.equals("0")) {
                    String contents = dataSnapshot.child("contents").getValue().toString();
                    String time = dataSnapshot.child("sendDate").getValue().toString();
                    String sender = dataSnapshot.child("sender").getValue().toString();
                    String receiver =dataSnapshot.child("receiver").getValue().toString();
                    mMessage = new Message(sender, receiver, contents);

                    ChatRoom temp = new ChatRoom(receiveruid, contents, time);
                    userDatabase.child(uid).child("rooms").child(rommname).setValue(temp);
                    temp.setReceiver(uid);
                    userDatabase.child(receiveruid).child("rooms").child(rommname).setValue(temp);

                } else {
                    String caller = dataSnapshot.child("caller").getValue().toString();
                    String date = dataSnapshot.child("date").getValue().toString();
                    mMessage = new Message(1, caller, Calendar.getInstance().getTime().toString());
                    mMessage.setReceiver(receiveruid);
                    mMessage.setDate(date);
                    dataSnapshot.getRef().removeValue();
                }
                chatAdapter.add(mMessage);
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
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
}