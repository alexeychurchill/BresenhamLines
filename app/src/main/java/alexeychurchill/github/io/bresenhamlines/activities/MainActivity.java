package alexeychurchill.github.io.bresenhamlines.activities;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import alexeychurchill.github.io.bresenhamlines.R;
import alexeychurchill.github.io.bresenhamlines.dialogs.ColorPickerDialogFragment;
import alexeychurchill.github.io.bresenhamlines.filebrowser.OpenFileActivity;
import alexeychurchill.github.io.bresenhamlines.graphics.parsers.FileParser;
import alexeychurchill.github.io.bresenhamlines.graphics.primitives.Drawing;
import alexeychurchill.github.io.bresenhamlines.graphics.primitives.Point;
import alexeychurchill.github.io.bresenhamlines.graphics.transforms.Rotate;
import alexeychurchill.github.io.bresenhamlines.graphics.transforms.Scale;
import alexeychurchill.github.io.bresenhamlines.graphics.transforms.Translate;
import alexeychurchill.github.io.bresenhamlines.views.RenderView;

public class MainActivity extends AppCompatActivity implements ColorPickerDialogFragment.OnColorChosenListener {
    private static final int RC_OPEN_FILE = 1;
    private static final int RC_TRANSFORMS_SETTING = 2;
    private RenderView mRVField;
    private Translate mTranslate = new Translate();
    private Scale mScale = new Scale();
    private Rotate mRotate = new Rotate();
    private int mColor = Color.BLACK;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mRVField = ((RenderView) findViewById(R.id.rvField));
        mRotate.setCenterPoint(new Point(0, 0));
    }

    public void onOpenFile() {
        Intent fileOpenIntent = new Intent(this, OpenFileActivity.class);
        startActivityForResult(fileOpenIntent, RC_OPEN_FILE);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.miMainOpen:
                onOpenFile();
                break;
            case R.id.miMainTransform:
                onTransformsSetting();
                break;
            case R.id.miMainColor:
                onColorChange();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void onColorChange() {
        ColorPickerDialogFragment colorPickerDialog = new ColorPickerDialogFragment();
        colorPickerDialog.setListener(this);
        colorPickerDialog.setColor(mColor);
        colorPickerDialog.show(getSupportFragmentManager(), "ColorPickerDialogFragment");
    }

    private void onTransformsSetting() {
        Intent intent = new Intent(this, TransformsActivity.class);
        intent.putExtra(TransformsActivity.EXTRA_TRANSLATE_X, mTranslate.getTranslationX());
        intent.putExtra(TransformsActivity.EXTRA_TRANSLATE_Y, mTranslate.getTranslationY());
        intent.putExtra(TransformsActivity.EXTRA_SCALE_X, mScale.getScaleByX());
        intent.putExtra(TransformsActivity.EXTRA_SCALE_Y, mScale.getScaleByY());
        intent.putExtra(TransformsActivity.EXTRA_ROTATION, mRotate.getRotationDegree());
        intent.putExtra(TransformsActivity.EXTRA_ROTATION_C_X, mRotate.getCenterPoint().getX());
        intent.putExtra(TransformsActivity.EXTRA_ROTATION_C_Y, mRotate.getCenterPoint().getY());
        startActivityForResult(intent, RC_TRANSFORMS_SETTING);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case RC_OPEN_FILE:
                    if (data != null) {
                        openFile(data.getStringExtra(OpenFileActivity.EXTRA_FILENAME));
                    }
                    break;
                case RC_TRANSFORMS_SETTING:
                    if (data != null) {
                        mRVField.setRenderThreadPaused(true);
                        //Translate
                        mTranslate.setTranslationX(data.getIntExtra(TransformsActivity.EXTRA_TRANSLATE_X, mTranslate.getTranslationX()));
                        mTranslate.setTranslationY(data.getIntExtra(TransformsActivity.EXTRA_TRANSLATE_Y, mTranslate.getTranslationY()));
                        //Scale
                        mScale.setScaleByX(data.getDoubleExtra(TransformsActivity.EXTRA_SCALE_X, mScale.getScaleByX()));
                        mScale.setScaleByY(data.getDoubleExtra(TransformsActivity.EXTRA_SCALE_Y, mScale.getScaleByY()));
                        //Rotate
                        mRotate.setRotationDegree(data.getDoubleExtra(TransformsActivity.EXTRA_ROTATION, mRotate.getRotationDegree()));
                        mRotate.getCenterPoint().setX(data.getIntExtra(TransformsActivity.EXTRA_ROTATION_C_X, mRotate.getCenterPoint().getX()));
                        mRotate.getCenterPoint().setY(data.getIntExtra(TransformsActivity.EXTRA_ROTATION_C_Y, mRotate.getCenterPoint().getX()));
                        mRVField.setRenderThreadPaused(false);
                    }
                    break;
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void openFile(String filename) {
        if (filename == null) {
            return;
        }
        if (!filename.endsWith(".shape.txt")) {
            Toast.makeText(this, R.string.text_wrong_file, Toast.LENGTH_SHORT)
                    .show();
            return;
        }
        FileParser parser = new FileParser(filename);
        Drawing parsedDrawing = parser.parse();
        if (parsedDrawing != null) {
            showDrawing(parsedDrawing);
        } else {
            Toast.makeText(this, R.string.text_file_error, Toast.LENGTH_SHORT)
                    .show();
        }
    }

    private void showDrawing(Drawing parsedDrawing) {
        if (mRVField == null) {
            return;
        }
        if (parsedDrawing == null) {
            return;
        }
        parsedDrawing.getTransforms().add(mScale);
        parsedDrawing.getTransforms().add(mTranslate);
        parsedDrawing.getTransforms().add(mRotate);
        parsedDrawing.setColor(mColor);
        mRVField.setRenderThreadPaused(true);
        mRVField.getDrawings().clear();
        mRVField.getDrawings().add(parsedDrawing);
        mRVField.setRenderThreadPaused(false);
    }

    @Override
    public void onColorChosen(DialogFragment dialog, int color) {
        mColor = color;
        for (Drawing drawing : mRVField.getDrawings()) {
            drawing.setColor(color);
        }
    }
}
