package utp.openglapp.square;

import android.app.Activity;
import android.os.Bundle;

public class SquareActivity extends Activity {

    SquareActivityView squareActivityView;

    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);

        squareActivityView = new SquareActivityView(this);

        setContentView(squareActivityView);
    }
}
