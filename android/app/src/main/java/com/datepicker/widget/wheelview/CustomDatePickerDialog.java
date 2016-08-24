package com.datepicker.widget.wheelview;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.datepicker.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by plus on 16/5/25.
 * 自定义日期控件
 */
public class CustomDatePickerDialog extends Dialog {
    private Context mActivity;
    private WheelView year;
    private WheelView month;
    private WheelView day;

    private TextView tvTitle;

    private int mYear = 1995;
    private int mMonth = 12;
    private int mDay = 05;

    private String initDay;//初始化日期

    private int MAXYEAR;//最大年限
    private int MINYEAR = 1950;//最小年限

    onCustomDatePickerDialogListener onCustomDatePickerDialogListener;

    public CustomDatePickerDialog(Context mActivity, String initDay) {
        super(mActivity, R.style.customdialog_style);
        this.mActivity = mActivity;
        this.initDay = initDay;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.wheel_date_picker);
        tvTitle = (TextView) findViewById(R.id.title);

        MAXYEAR = Calendar.getInstance().get(Calendar.YEAR);

        initDataPickView();
    }

    protected void setDialogTitle(String title){

        tvTitle.setText(title);

    }

    /***
     * 实现接口
     *
     * @param onCustomDatePickerDialogListener
     */
    public void setOnCustomDatePickerDialogListener(CustomDatePickerDialog.onCustomDatePickerDialogListener onCustomDatePickerDialogListener) {
        this.onCustomDatePickerDialogListener = onCustomDatePickerDialogListener;
    }

    /***
     * 获取日期Date
     */
    public static Date fromatDate(String time, String pattern) {
        SimpleDateFormat dateFormater = new SimpleDateFormat(pattern);
        Date date = null;
        try {
            return dateFormater.parse(time);
        } catch (ParseException e) {
            e.printStackTrace();
            return date;
        }
    }

    private void initDataPickView() {
        if (!TextUtils.isEmpty(initDay)) {
            try {
                Calendar calendar = Calendar.getInstance();
                Date date = fromatDate(initDay, "yyyy-MM-dd");
                calendar.setTime(date);
                mYear = calendar.get(Calendar.YEAR);
                mMonth = calendar.get(Calendar.MONTH) + 1;
                mDay = calendar.get(Calendar.DAY_OF_MONTH);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        year = (WheelView) findViewById(R.id.year);
        year.setAdapter(new NumericWheelAdapter(MINYEAR, MAXYEAR));
        year.setLabel("年");
        year.setCyclic(true);
        year.addScrollingListener(scrollListener);

        month = (WheelView) findViewById(R.id.month);
        month.setAdapter(new NumericWheelAdapter(1, 12, "%02d"));
        month.setLabel("月");
        month.setCyclic(true);
        month.addScrollingListener(scrollListener);

        day = (WheelView) findViewById(R.id.day);
        initDay(mYear, mMonth);
        day.setLabel("日");
        day.setCyclic(true);

        year.setCurrentItem(mYear - 1950);
        month.setCurrentItem(mMonth - 1);
        day.setCurrentItem(mDay - 1);

        TextView bt = (TextView) findViewById(R.id.tv_confrim);
        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int y = year.getCurrentItem() + 1950;
                int m = month.getCurrentItem() + 1;
                int d = day.getCurrentItem() + 1;

                String birthday = new StringBuilder().append((year.getCurrentItem() + 1950)).append("-").append((month.getCurrentItem() + 1) < 10 ? "0" + (month.getCurrentItem() + 1) : (month.getCurrentItem() + 1)).append("-").append(((day.getCurrentItem() + 1) < 10) ? "0" + (day.getCurrentItem() + 1) : (day.getCurrentItem() + 1)).toString();
                Date date = fromatDate(birthday, "yyyy-MM-dd");
                if (date.compareTo(new Date()) > 1) {
                    Toast.makeText(mActivity, "日期选择有误哦", 1);
                    return;
                }

                if (onCustomDatePickerDialogListener != null) {
                    onCustomDatePickerDialogListener.getDate(birthday);
                    onCustomDatePickerDialogListener.getYMD(y, m, d);
                }
                dismiss();
            }
        });
    }

    OnWheelScrollListener scrollListener = new OnWheelScrollListener() {
        @Override
        public void onScrollingStarted(WheelView wheel) {

        }

        @Override
        public void onScrollingFinished(WheelView wheel) {
            int n_year = year.getCurrentItem() + 1950;//
            int n_month = month.getCurrentItem() + 1;//
            initDay(n_year, n_month);
            String birthday = new StringBuilder().append((year.getCurrentItem() + 1950)).append("-").append((month.getCurrentItem() + 1) < 10 ? "0" + (month.getCurrentItem() + 1) : (month.getCurrentItem() + 1)).append("-").append(((day.getCurrentItem() + 1) < 10) ? "0" + (day.getCurrentItem() + 1) : (day.getCurrentItem() + 1)).toString();
        }
    };

    /**
     */
    private void initDay(int arg1, int arg2) {
        day.setAdapter(new NumericWheelAdapter(1, getDay(arg1, arg2), "%02d"));
    }

    /**
     * @param year
     * @param month
     * @return
     */
    private int getDay(int year, int month) {
        int day = 30;
        boolean flag = false;
        switch (year % 4) {
            case 0:
                flag = true;
                break;
            default:
                flag = false;
                break;
        }
        switch (month) {
            case 1:
            case 3:
            case 5:
            case 7:
            case 8:
            case 10:
            case 12:
                day = 31;
                break;
            case 2:
                day = flag ? 29 : 28;
                break;
            default:
                day = 30;
                break;
        }
        return day;
    }

    /***
     * 获取日期
     */
    public interface onCustomDatePickerDialogListener {

        public void getDate(String date);

        public void getYMD(int year, int mouth, int day);

    }
}

