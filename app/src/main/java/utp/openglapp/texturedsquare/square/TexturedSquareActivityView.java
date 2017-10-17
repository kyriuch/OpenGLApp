package utp.openglapp.texturedsquare.square;

import android.content.Context;
import android.opengl.GLSurfaceView;
import android.util.AttributeSet;

/**
 * Created by kyriuch on 11.10.2017.
 */

public class TexturedSquareActivityView extends GLSurfaceView {
    public TexturedSquareActivityView(Context context) {
        super(context);

        initialize(context);
    }

    public TexturedSquareActivityView(Context context, AttributeSet attrs) {
        super(context, attrs);

        initialize(context);
    }

    private void initialize(Context context) {
        setEGLContextClientVersion(2);

        TexturedSquareActivityRenderer texturedSquareActivityRenderer = new TexturedSquareActivityRenderer(context);

        setRenderer(texturedSquareActivityRenderer);
    }
}
