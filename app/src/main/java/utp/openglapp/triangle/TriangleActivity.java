package utp.openglapp.triangle;

import android.app.Activity;
import android.os.Bundle;

public class TriangleActivity extends Activity {

    TriangleActivityView triangleActivityView;

    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);

        triangleActivityView = new TriangleActivityView(this);

        setContentView(triangleActivityView);
    }
}
