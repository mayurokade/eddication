package com.ssb.apps.bookapp.utils;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

import com.ssb.apps.bookapp.R;


public class DialogUniversalWarningUtils {

    private Context mcontext;
    private Dialog mDialog;
    private TextView mDialogText;
    private TextView mDialogOKButton;
    private TextView mDialogCancelButton;
    private OnWarningOkBtnClick onWarningOkBtnClick;

    public DialogUniversalWarningUtils(Context context) {
        this.mcontext = context;
    }

    private void initDialogButtons() {

        mDialogOKButton.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View view) {
                mDialog.dismiss();
                if (onWarningOkBtnClick != null)
                    onWarningOkBtnClick.OnWarnOkClick();
            }
        });

        mDialogCancelButton.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View view) {
                mDialog.dismiss();
            }
        });

        mDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                if (onWarningOkBtnClick != null)
                    onWarningOkBtnClick.onDialogDismiss();
            }
        });

    }

    public void showDialog(String msg, boolean isCancelBtn) {
        if (mDialog == null) {
            mDialog = new Dialog(mcontext,
                    R.style.CustomDialogTheme);
        }
        mDialog.setContentView(R.layout.dialog_universal_warning);
        mDialog.setCancelable(true);
        if (!((Activity) mcontext).isFinishing()) {
            mDialog.show();
        }
        mDialogText = mDialog
                .findViewById(R.id.dialog_universal_warning_text);
        mDialogOKButton = mDialog
                .findViewById(R.id.dialog_universal_warning_ok);
        mDialogCancelButton = mDialog
                .findViewById(R.id.dialog_universal_warning_cancel);
        mDialogText.setText(msg);

        if (!isCancelBtn) {
            mDialogCancelButton.setVisibility(View.GONE);
        }

        initDialogButtons();
    }

    public void setCallBack(OnWarningOkBtnClick callBack) {
        this.onWarningOkBtnClick = callBack;
    }

    public void dismissDialog() {
        mDialog.dismiss();
    }

    public interface OnWarningOkBtnClick {
        void OnWarnOkClick();

        void onDialogDismiss();
    }
}
