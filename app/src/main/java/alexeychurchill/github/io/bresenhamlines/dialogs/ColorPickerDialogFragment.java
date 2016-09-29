package alexeychurchill.github.io.bresenhamlines.dialogs;

import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.SeekBar;

import alexeychurchill.github.io.bresenhamlines.R;

public class ColorPickerDialogFragment extends DialogFragment implements SeekBar.OnSeekBarChangeListener {
    private OnColorChosenListener mListener;
    private SeekBar mSBRed;
    private SeekBar mSBGreen;
    private SeekBar mSBBlue;
    private LinearLayout llColorPreview;
    private int mRed = 0;
    private int mGreen = 0;
    private int mBlue = 0;

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        View dialogView = getActivity().getLayoutInflater().inflate(R.layout.dialog_choose_color, null);
        if (dialogView != null) {
            mSBRed = ((SeekBar) dialogView.findViewById(R.id.sbRed));
            mSBGreen = ((SeekBar) dialogView.findViewById(R.id.sbGreen));
            mSBBlue = ((SeekBar) dialogView.findViewById(R.id.sbBlue));
            llColorPreview = ((LinearLayout) dialogView.findViewById(R.id.llColorPreview));
            mSBRed.setOnSeekBarChangeListener(this);
            mSBGreen.setOnSeekBarChangeListener(this);
            mSBBlue.setOnSeekBarChangeListener(this);
            updateSeekBars();
            updatePreview();
        }
        builder.setTitle(R.string.dialog_choose_color_title)
                .setView(dialogView)
                .setPositiveButton(R.string.text_ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (mListener != null) {
                            mListener.onColorChosen(ColorPickerDialogFragment.this, Color.rgb(mRed, mGreen, mBlue));
                        }
                    }
                });
        return builder.create();
    }

    public void setColor(int color) {
        mRed = Color.red(color);
        mGreen = Color.green(color);
        mBlue = Color.blue(color);
        updateSeekBars();
        updatePreview();
    }

    public int getColor() {
        return Color.rgb(mRed, mGreen, mBlue);
    }

    public void setListener(OnColorChosenListener listener) {
        this.mListener = listener;
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        if (!fromUser) {
            return;
        }
        switch (seekBar.getId()) {
            case R.id.sbRed:
                mRed = seekBar.getProgress();
                break;
            case R.id.sbGreen:
                mGreen = seekBar.getProgress();
                break;
            case R.id.sbBlue:
                mBlue = seekBar.getProgress();
                break;
        }
        updatePreview();
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {
    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {
    }

    private void updatePreview() {
        if (llColorPreview == null) {
            return;
        }
        llColorPreview.setBackgroundColor(Color.rgb(mRed, mGreen, mBlue));
    }

    private void updateSeekBars() {
        if (mSBRed != null) {
            mSBRed.setProgress(mRed);
        }
        if (mSBGreen != null) {
            mSBGreen.setProgress(mGreen);
        }
        if (mSBBlue != null) {
            mSBBlue.setProgress(mBlue);
        }
    }

    public interface OnColorChosenListener {
        void onColorChosen(DialogFragment dialog, int color);
    }
}
