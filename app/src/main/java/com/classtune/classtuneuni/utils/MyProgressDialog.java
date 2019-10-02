package com.classtune.classtuneuni.utils;

import android.app.Dialog;
import android.content.Context;
import android.widget.FrameLayout;
import android.widget.ProgressBar;

import com.classtune.classtuneuni.R;

public class MyProgressDialog extends Dialog {

    public static MyProgressDialog show(Context context, CharSequence title,
                                        CharSequence message) {
        return show(context, title, message, false);
    }

    public static MyProgressDialog show(Context context, CharSequence title,
                                        CharSequence message, boolean indeterminate) {
        return show(context, title, message, indeterminate, false, null);
    }

    public static MyProgressDialog show(Context context, CharSequence title,
                                        CharSequence message, boolean indeterminate, boolean cancelable) {
        return show(context, title, message, indeterminate, cancelable, null);
    }

    public static MyProgressDialog show(Context context, CharSequence title,
                                        CharSequence message, boolean indeterminate,
                                        boolean cancelable, OnCancelListener cancelListener) {
        MyProgressDialog dialog = new MyProgressDialog(context);
        dialog.setTitle(title);
        dialog.setCancelable(cancelable);
        dialog.setOnCancelListener(cancelListener);
        /* The next line will add the ProgressBar to the dialog. */
        dialog.addContentView(new ProgressBar(context), new FrameLayout.LayoutParams(FrameLayout.LayoutParams.WRAP_CONTENT, FrameLayout.LayoutParams.WRAP_CONTENT));
        dialog.show();

        return dialog;
    }

    @Override
    public void dismiss() {
        super.dismiss();
    }

    public static void dismis(Context context){
        MyProgressDialog dialog = new MyProgressDialog(context);
        dialog.dismiss();
    }

    public MyProgressDialog(Context context) {
        super(context, R.style.NewDialog);
    }
}