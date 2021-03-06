package xyz.pulse9.sinabro;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetDialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import static android.app.Activity.RESULT_OK;

public class BottomSheetDialog extends BottomSheetDialogFragment implements View.OnClickListener {

    public interface PollButtonClickListener extends View.OnClickListener {
        void onAnyButtonClick(Object data);
    }

    private PollButtonClickListener listener;

    public void setListener(PollButtonClickListener listner) {
        this.listener = listner;

    }


    public static BottomSheetDialog getInstance() {
        return new BottomSheetDialog();
    }

//    private LinearLayout msgLo;
    private LinearLayout emailLo;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.bottom_sheet_dialog, container, false);
//        msgLo = (LinearLayout) view.findViewById(R.id.msgLo);
        emailLo = (LinearLayout) view.findViewById(R.id.emailLo);

//        msgLo.setOnClickListener(listener);
        emailLo.setOnClickListener(listener);
        return view;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.msgLo:
//                Toast.makeText(getContext(), "@string/ChattingBottomSheetMenu2", Toast.LENGTH_SHORT).show();
                break;
            case R.id.emailLo:
//                Toast.makeText(getContext(), "@string/ChattingBottomSheetMenu2", Toast.LENGTH_SHORT).show();

                break;
        }
        dismiss();
    }


}

