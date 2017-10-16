package utp.openglapp.triangle;

import android.content.Context;
import android.opengl.GLSurfaceView;
import android.util.AttributeSet;

/**
 * Created by kyriuch on 11.10.2017.
 */

public class TriangleActivityView extends GLSurfaceView {
    public TriangleActivityView(Context context) {
        super(context);

        initialize();
    }

    public TriangleActivityView(Context context, AttributeSet attrs) {
        super(context, attrs);

        initialize();
    }

    private void initialize() {
        setEGLContextClientVersion(2);

        TriangleActivityRenderer triangleActivityRenderer = new TriangleActivityRenderer();

        setRenderer(triangleActivityRenderer);
    }
}
