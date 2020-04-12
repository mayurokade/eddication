package com.ssb.apps.bookapp.utils;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

import com.ssb.apps.bookapp.R;


public class DialogUniversalErrorUtils {

    private Context mContext;
    private Dialog mDialog;

    private TextView mDialogHeader;
    private TextView mDialogText;
    private TextView mDialogOKButton;
    //private ImageView mDialogImage;
    private OnErrorBtnClick onOkBtnClick;

    public DialogUniversalErrorUtils(Context context) {
        this.mContext = context;
    }

    public void showDialog(String msg) {
        if (mDialog == null) {
            mDialog = new Dialog(mContext, R.style.CustomDialogTheme);
        }
        mDialog.setContentView(R.layout.dialog_universal_error);
        mDialog.setCancelable(true);
        mDialog.show();

        mDialogHeader = mDialog.findViewById(R.id.dialog_universal_info_title);
        mDialogText = mDialog.findViewById(R.id.dialog_universal_info_text);
        mDialogOKButton = mDialog.findViewById(R.id.dialog_universal_info_ok);
        //mDialogImage = mDialog.findViewById(R.id.dialog_universal_info_image);

        /*if (!isCancelBtn) {
            mDialogOKButton.setVisibility(View.INVISIBLE);
        }*/

        mDialogText.setText(msg);
        initDialogButtons();
    }

    public interface OnErrorBtnClick {
        void OnErrorClick();
    }

    private void initDialogButtons() {

        mDialogOKButton.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View view) {
                mDialog.dismiss();
                if (onOkBtnClick != null)
                    onOkBtnClick.OnErrorClick();
            }
        });
    }

    public void setCallBack(OnErrorBtnClick callBack) {
        this.onOkBtnClick = callBack;
    }

    public void dismissDialog() {
        mDialog.dismiss();
    }
}
