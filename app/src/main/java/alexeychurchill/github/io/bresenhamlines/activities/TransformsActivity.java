package alexeychurchill.github.io.bresenhamlines.activities;

import android.content.Intent;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;

import java.util.Locale;

import alexeychurchill.github.io.bresenhamlines.R;

public class TransformsActivity extends AppCompatActivity implements SeekBar.OnSeekBarChangeListener, View.OnClickListener {
    public static final String EXTRAS_PREFIX = ""; //Stub
    public static final String EXTRA_TRANSLATE_X = EXTRAS_PREFIX.concat("EXTRA_TRANSLATE_X");
    public static final String EXTRA_TRANSLATE_Y = EXTRAS_PREFIX.concat("EXTRA_TRANSLATE_Y");
    public static final String EXTRA_SCALE_X = EXTRAS_PREFIX.concat("EXTRA_SCALE_X");
    public static final String EXTRA_SCALE_Y = EXTRAS_PREFIX.concat("EXTRA_SCALE_Y");
    public static final String EXTRA_ROTATION = EXTRAS_PREFIX.concat("EXTRA_ROTATION");
    public static final String EXTRA_ROTATION_C_X = EXTRAS_PREFIX.concat("EXTRA_ROTATION_C_X");
    public static final String EXTRA_ROTATION_C_Y = EXTRAS_PREFIX.concat("EXTRA_ROTATION_C_Y");

    private int mTranslateX = 0;
    private int mTranslateY = 0;
    private double mScaleX = 1.0;
    private double mScaleY = 1.0;
    private double mRotation = 0.0;
    private int mRotationCX = 0;
    private int mRotationCY = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transforms);
        loadState(getIntent());
        updateViews();
        SeekBar sbRotation = ((SeekBar) findViewById(R.id.sbRotation));
        if (sbRotation != null) {
            sbRotation.setOnSeekBarChangeListener(this);
        }
        Button btnOk = (Button) findViewById(R.id.btnOk);
        if (btnOk != null) {
            btnOk.setOnClickListener(this);
        }
    }

    private void loadState(Intent intent) {
        if (intent == null) {
            return;
        }
        mTranslateX = intent.getIntExtra(EXTRA_TRANSLATE_X, 0);
        mTranslateY = intent.getIntExtra(EXTRA_TRANSLATE_Y, 0);
        mScaleX = intent.getDoubleExtra(EXTRA_SCALE_X, 1.0);
        mScaleY = intent.getDoubleExtra(EXTRA_SCALE_Y, 1.0);
        mRotation = intent.getDoubleExtra(EXTRA_ROTATION, 0.0);
        mRotationCX = intent.getIntExtra(EXTRA_ROTATION_C_X, 0);
        mRotationCY = intent.getIntExtra(EXTRA_ROTATION_C_Y, 0);
    }

    private Intent saveState() {
        Intent intent = new Intent();
        intent.putExtra(EXTRA_TRANSLATE_X, mTranslateX);
        intent.putExtra(EXTRA_TRANSLATE_Y, mTranslateY);
        intent.putExtra(EXTRA_SCALE_X, mScaleX);
        intent.putExtra(EXTRA_SCALE_Y, mScaleY);
        intent.putExtra(EXTRA_ROTATION, mRotation);
        intent.putExtra(EXTRA_ROTATION_C_X, mRotationCX);
        intent.putExtra(EXTRA_ROTATION_C_Y, mRotationCY);
        return intent;
    }

    private void updateViews() {
        EditText etTransX = ((EditText) findViewById(R.id.etTransX));
        EditText etTransY = ((EditText) findViewById(R.id.etTransY));
        EditText etScaleX = ((EditText) findViewById(R.id.etScaleX));
        EditText etScaleY = ((EditText) findViewById(R.id.etScaleY));
        EditText etRCX = ((EditText) findViewById(R.id.etRCX));
        EditText etRCY = ((EditText) findViewById(R.id.etRCY));
        SeekBar sbRotation = ((SeekBar) findViewById(R.id.sbRotation));
        TextView tvRotation = ((TextView) findViewById(R.id.tvRotation));
        if (etTransX != null) {
            etTransX.setText(String.valueOf(mTranslateX));
        }
        if (etTransY != null) {
            etTransY.setText(String.valueOf(mTranslateY));
        }
        if (etScaleX != null) {
            etScaleX.setText(String.format(Locale.getDefault(), "%.1f", mScaleX));
        }
        if (etScaleY != null) {
            etScaleY.setText(String.format(Locale.getDefault(), "%.1f", mScaleY));
        }
        if (etRCX != null) {
            etRCX.setText(String.valueOf(mRotationCX));
        }
        if (etRCY != null) {
            etRCY.setText(String.valueOf(mRotationCY));
        }
        if (sbRotation != null) {
            sbRotation.setProgress((int) (mRotation * 10));
        }
        if (tvRotation != null) {
            tvRotation.setText(String.format(Locale.getDefault(), "%.1f", mRotation));
        }
    }

    private void updateState() {
        EditText etTransX = ((EditText) findViewById(R.id.etTransX));
        EditText etTransY = ((EditText) findViewById(R.id.etTransY));
        EditText etScaleX = ((EditText) findViewById(R.id.etScaleX));
        EditText etScaleY = ((EditText) findViewById(R.id.etScaleY));
        EditText etRCX = ((EditText) findViewById(R.id.etRCX));
        EditText etRCY = ((EditText) findViewById(R.id.etRCY));
        SeekBar sbRotation = ((SeekBar) findViewById(R.id.sbRotation));
        if (etTransX != null) {
            mTranslateX = Integer.parseInt(etTransX.getText().toString());
        }
        if (etTransY != null) {
            mTranslateY = Integer.parseInt(etTransY.getText().toString());
        }
        if (etScaleX != null) {
            mScaleX = Double.parseDouble(etScaleX.getText().toString().replace(',', '.'));
        }
        if (etScaleY != null) {
            mScaleY = Double.parseDouble(etScaleY.getText().toString().replace(',', '.'));
        }
        if (etRCX != null) {
            mRotationCX = Integer.parseInt(etRCX.getText().toString());
        }
        if (etRCY != null) {
            mRotationCY = Integer.parseInt(etRCY.getText().toString());
        }
        if (sbRotation != null) {
            mRotation = sbRotation.getProgress() / 10.0;
        }
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
        mRotation = seekBar.getProgress() / 10;
        TextView tvRotation = (TextView) findViewById(R.id.tvRotation);
        if (tvRotation != null) {
            tvRotation.setText(String.format(Locale.getDefault(), "%.1f", mRotation));
        }
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {
    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {
    }

    @Override
    public void onClick(View view) {
        updateState();
        Intent intent = saveState();
        setResult(RESULT_OK, intent);
        finish();
    }
}
