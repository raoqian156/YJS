package com.yskrq.common.widget;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;

import com.yskrq.common.R;

import java.util.Calendar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.StyleRes;

public class DatePickerDialog_less24 extends AlertDialog implements DialogInterface.OnClickListener,
                                                                    DatePicker.OnDateChangedListener {
  private static final String YEAR = "year";
  private static final String MONTH = "month";
  private static final String DAY = "day";

  private final DatePicker mDatePicker;

  private android.app.DatePickerDialog.OnDateSetListener mDateSetListener;

  /**
   * Creates a new date picker dialog for the current date using the parent
   * context's default date picker dialog theme.
   *
   * @param context the parent context
   */
  public DatePickerDialog_less24(@NonNull Context context) {
    this(context, 0, null, Calendar.getInstance(), -1, -1, -1);
  }

  /**
   * Creates a new date picker dialog for the current date.
   *
   * @param context the parent context
   * @param themeResId the resource ID of the theme against which to inflate
   *                   this dialog, or {@code 0} to use the parent
   *                   {@code context}'s default alert dialog theme
   */
  public DatePickerDialog_less24(@NonNull Context context, @StyleRes int themeResId) {
    this(context, themeResId, null, Calendar.getInstance(), -1, -1, -1);
  }

  /**
   * Creates a new date picker dialog for the specified date using the parent
   * context's default date picker dialog theme.
   *
   * @param context the parent context
   * @param listener the listener to call when the user sets the date
   * @param year the initially selected year
   * @param month the initially selected month (0-11 for compatibility with
   *              {@link Calendar#MONTH})
   * @param dayOfMonth the initially selected day of month (1-31, depending
   *                   on month)
   */
  public DatePickerDialog_less24(@NonNull Context context, @Nullable android.app.DatePickerDialog.OnDateSetListener listener,
                                 int year, int month, int dayOfMonth) {
    this(context, 0, listener, null, year, month, dayOfMonth);
  }

  /**
   * Creates a new date picker dialog for the specified date.
   *
   * @param context the parent context
   * @param themeResId the resource ID of the theme against which to inflate
   *                   this dialog, or {@code 0} to use the parent
   *                   {@code context}'s default alert dialog theme
   * @param listener the listener to call when the user sets the date
   * @param year the initially selected year
   * @param monthOfYear the initially selected month of the year (0-11 for
   *                    compatibility with {@link Calendar#MONTH})
   * @param dayOfMonth the initially selected day of month (1-31, depending
   *                   on month)
   */
  public DatePickerDialog_less24(@NonNull Context context, @StyleRes int themeResId,
                                 @Nullable android.app.DatePickerDialog.OnDateSetListener listener, int year, int monthOfYear, int dayOfMonth) {
    this(context, themeResId, listener, null, year, monthOfYear, dayOfMonth);
  }

  private DatePickerDialog_less24(@NonNull Context context, @StyleRes int themeResId,
                                  @Nullable android.app.DatePickerDialog.OnDateSetListener listener, @Nullable Calendar calendar, int year,
                                  int monthOfYear, int dayOfMonth) {
    super(context, resolveDialogTheme(context, themeResId));

    final Context themeContext = getContext();
    final LayoutInflater inflater = LayoutInflater.from(themeContext);
    final View view = inflater.inflate(R.layout.date_picker_dialog_less_24, null);
    setView(view);

    setButton(BUTTON_POSITIVE, "确认", this);
    setButton(BUTTON_NEGATIVE, "取消", this);
    if (calendar != null) {
      year = calendar.get(Calendar.YEAR);
      monthOfYear = calendar.get(Calendar.MONTH);
      dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
    }

    mDatePicker = (DatePicker) view.findViewById(R.id.datePicker);
    mDatePicker.init(year, monthOfYear, dayOfMonth, this);
    mDateSetListener = listener;
  }

  static @StyleRes int resolveDialogTheme(@NonNull Context context, @StyleRes int themeResId) {
//    if (themeResId == 0) {
    ////      final TypedValue outValue = new TypedValue();
    ////      context.getTheme().resolveAttribute(R.attr.datePickerDialogTheme, outValue, true);
    ////      return outValue.resourceId;
    ////    } else {
    ////      return themeResId;
    ////    }
    return 0;
  }

  @Override
  public void onDateChanged(@NonNull DatePicker view, int year, int month, int dayOfMonth) {
    mDatePicker.init(year, month, dayOfMonth, this);
  }

  /**
   * Sets the listener to call when the user sets the date.
   *
   * @param listener the listener to call when the user sets the date
   */
  public void setOnDateSetListener(@Nullable android.app.DatePickerDialog.OnDateSetListener listener) {
    mDateSetListener = listener;
  }

  @Override
  public void onClick(@NonNull DialogInterface dialog, int which) {
    switch (which) {
      case BUTTON_POSITIVE:
        if (mDateSetListener != null) {
          // Clearing focus forces the dialog to commit any pending
          // changes, e.g. typed text in a NumberPicker.
          mDatePicker.clearFocus();
          mDateSetListener.onDateSet(mDatePicker, mDatePicker.getYear(),
              mDatePicker.getMonth(), mDatePicker.getDayOfMonth());
        }
        break;
      case BUTTON_NEGATIVE:
        cancel();
        break;
    }
  }

  /**
   * Returns the {@link DatePicker} contained in this dialog.
   *
   * @return the date picker
   */
  @NonNull
  public DatePicker getDatePicker() {
    return mDatePicker;
  }

  /**
   * Sets the current date.
   *
   * @param year the year
   * @param month the month (0-11 for compatibility with
   *              {@link Calendar#MONTH})
   * @param dayOfMonth the day of month (1-31, depending on month)
   */
  public void updateDate(int year, int month, int dayOfMonth) {
    mDatePicker.updateDate(year, month, dayOfMonth);
  }

  @Override
  public Bundle onSaveInstanceState() {
    final Bundle state = super.onSaveInstanceState();
    state.putInt(YEAR, mDatePicker.getYear());
    state.putInt(MONTH, mDatePicker.getMonth());
    state.putInt(DAY, mDatePicker.getDayOfMonth());
    return state;
  }

  @Override
  public void onRestoreInstanceState(Bundle savedInstanceState) {
    super.onRestoreInstanceState(savedInstanceState);
    final int year = savedInstanceState.getInt(YEAR);
    final int month = savedInstanceState.getInt(MONTH);
    final int day = savedInstanceState.getInt(DAY);
    mDatePicker.init(year, month, day, this);
  }


  /**
   * The listener used to indicate the user has finished selecting a date.
   */
  public interface OnDateSetListener {
    /**
     * @param view the picker associated with the dialog
     * @param year the selected year
     * @param month the selected month (0-11 for compatibility with
     *              {@link Calendar#MONTH})
     * @param dayOfMonth the selected day of the month (1-31, depending on
     *                   month)
     */
    void onDateSet(DatePicker view, int year, int month, int dayOfMonth);
  }
}
