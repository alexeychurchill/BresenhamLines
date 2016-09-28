package alexeychurchill.github.io.bresenhamlines.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import alexeychurchill.github.io.bresenhamlines.R;
import alexeychurchill.github.io.bresenhamlines.filebrowser.OpenFileActivity;
import alexeychurchill.github.io.bresenhamlines.graphics.parsers.FileParser;
import alexeychurchill.github.io.bresenhamlines.graphics.primitives.Drawing;
import alexeychurchill.github.io.bresenhamlines.graphics.transforms.Scale;
import alexeychurchill.github.io.bresenhamlines.graphics.transforms.Translate;
import alexeychurchill.github.io.bresenhamlines.views.RenderView;

public class MainActivity extends AppCompatActivity {
    private static final int RC_OPEN_FILE = 1;
    private RenderView mRVField;
    private Translate mTranslate = new Translate();
    private Scale mScale = new Scale();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mRVField = ((RenderView) findViewById(R.id.rvField));
        mTranslate.setTranslationX(300);
        mTranslate.setTranslationY(200);
        mScale.setScaleByX(0.5);
        mScale.setScaleByY(1.5);
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
        }
        return super.onOptionsItemSelected(item);
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
        parsedDrawing.getTransforms().add(mTranslate);
        parsedDrawing.getTransforms().add(mScale);
        mRVField.setRenderThreadPaused(true);
        mRVField.getDrawings().clear();
        mRVField.getDrawings().add(parsedDrawing);
        mRVField.setRenderThreadPaused(false);
    }
}
