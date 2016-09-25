package alexeychurchill.github.io.bresenhamlines.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.Toast;

import alexeychurchill.github.io.bresenhamlines.R;
import alexeychurchill.github.io.bresenhamlines.filebrowser.OpenFileActivity;
import alexeychurchill.github.io.bresenhamlines.graphics.primitives.Drawing;
import alexeychurchill.github.io.bresenhamlines.graphics.parsers.FileParser;
import alexeychurchill.github.io.bresenhamlines.views.RenderView;

public class MainActivity extends AppCompatActivity implements View.OnLongClickListener {
    private static final int RC_OPEN_FILE = 1;
    private RenderView mRVField;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mRVField = ((RenderView) findViewById(R.id.rvField));
        mRVField.setOnLongClickListener(this);
    }

    public void onOpenFile() {
        Intent fileOpenIntent = new Intent(this, OpenFileActivity.class);
        startActivityForResult(fileOpenIntent, RC_OPEN_FILE);
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
        mRVField.setRenderThreadPaused(true);
        mRVField.getDrawings().clear();
        mRVField.getDrawings().add(parsedDrawing);
        mRVField.setRenderThreadPaused(false);
    }

    @Override
    public boolean onLongClick(View v) {
        switch (v.getId()) {
            case R.id.rvField:
                onOpenFile();
                return true;
        }
        return false;
    }
}
