package utp.openglapp.texturedsquare.square;

import android.app.Activity;
import android.os.Bundle;

public class TexturedSquareActivity extends Activity {

    TexturedSquareActivityView texturedSquareActivityView;

    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);

        texturedSquareActivityView = new TexturedSquareActivityView(this);

        setContentView(texturedSquareActivityView);
    }
}
