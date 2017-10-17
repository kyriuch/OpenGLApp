package utp.openglapp.square;

import android.content.Context;
import android.opengl.GLSurfaceView;
import android.util.AttributeSet;

/**
 * Created by kyriuch on 11.10.2017.
 */

public class SquareActivityView extends GLSurfaceView {
    public SquareActivityView(Context context) {
        super(context);

        initialize();
    }

    public SquareActivityView(Context context, AttributeSet attrs) {
        super(context, attrs);

        initialize();
    }

    private void initialize() {
        setEGLContextClientVersion(2);

        SquareActivityRenderer squareActivityRenderer = new SquareActivityRenderer();

        setRenderer(squareActivityRenderer);
    }
}
