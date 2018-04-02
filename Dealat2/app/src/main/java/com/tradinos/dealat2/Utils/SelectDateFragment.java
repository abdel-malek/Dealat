package com.tradinos.dealat2.Utils;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.widget.DatePicker;

import com.tradinos.dealat2.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * Created by developer on 01.10.17.
 */

public class SelectDateFragment extends DialogFragment implements
        DatePickerDialog.OnDateSetListener, DatePickerDialog.OnCancelListener, DatePickerDialog.OnDismissListener {
    int year;
    int month;
    int day;

    boolean minDate = true;

    public void setMinDate(boolean minDate) {
        this.minDate = minDate;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        //datePicker language
        Locale.setDefault(Locale.ENGLISH);

        // Use the current date as the default date in the picker
        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);

        // Create a new instance of DatePickerDialog and return it
        DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(), R.style.datepicker, this, year, month, day);

        Calendar calendar = Calendar.getInstance();
        if(minDate) // all input dates should be new
            datePickerDialog.getDatePicker().setMinDate(calendar.getTimeInMillis());
        else // but lastPayment date should be old
            datePickerDialog.getDatePicker().setMaxDate(calendar.getTimeInMillis());

        return datePickerDialog;
    }

    public void onDateSet(DatePicker view, int yy, int mm, int dd) {
        this.year = yy;
        this.month = mm;
        this.day = dd;
    }

    public interface OnDialogClosed {
        void OnDialogClosed(int year, int month, int day);
    }

    @Override
    public void onCancel(DialogInterface dialog) {
        this.day = 0;
        this.year = 0;
        this.month = 0;
        super.onCancel(dialog);
    }

    @Override
    public void onDismiss(DialogInterface dialog) {
        if (isDateValid() || (year == 0 && month == 0 && day == 0))
            ((OnDialogClosed) getActivity()).OnDialogClosed(year, month, day);

        else {
            ((OnDialogClosed) getActivity()).OnDialogClosed(-1, -1, -1);
         //   Toast.makeText(getActivity(), getResources().getString(R.string.invalid_date), Toast.LENGTH_SHORT).show();
        }
        super.onDismiss(dialog);
    }

    public boolean isDateValid() {
        Calendar c = Calendar.getInstance();

        String selectedDateString = year + "-" + ((month + 1) < 10 ? "0" + (month + 1) : (month + 1)) + "-" + (day < 10 ? "0" + day : day);
        Date currentDate = c.getTime();
        Date selectedDate = c.getTime();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
        try {
            currentDate = formatter.parse(formatter.format(c.getTime()));
            selectedDate = formatter.parse(selectedDateString);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        if (selectedDate.before(currentDate)) {
            return true; //it was false
        } else
            return true;
    }
}

