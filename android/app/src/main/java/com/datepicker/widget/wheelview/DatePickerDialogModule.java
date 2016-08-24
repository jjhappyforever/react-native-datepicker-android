package com.datepicker.widget.wheelview;

import android.text.TextUtils;

import com.facebook.react.bridge.Callback;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;

/**
 * Created by plus on 16/8/24.
 */
public class DatePickerDialogModule extends ReactContextBaseJavaModule {


    public static final String REACT_CLASS = "RCTDatePickerDialog";

    public DatePickerDialogModule(ReactApplicationContext reactContext) {
        super(reactContext);
    }


    @Override
    public String getName() {
        return REACT_CLASS;
    }

    @ReactMethod
    public void showDatePicker(String title, final Callback callback) {

        CustomDatePickerDialog customDatePickerDialog = new CustomDatePickerDialog(getCurrentActivity(), "1990-01-11");
        customDatePickerDialog.show();

        if (!TextUtils.isEmpty(title)) {
            customDatePickerDialog.setDialogTitle(title);
        }

        customDatePickerDialog.setOnCustomDatePickerDialogListener(new CustomDatePickerDialog.onCustomDatePickerDialogListener() {
            @Override
            public void getDate(String date) {
                callback.invoke(date);
            }

            @Override
            public void getYMD(int year, int mouth, int day) {

            }
        });

    }
}
