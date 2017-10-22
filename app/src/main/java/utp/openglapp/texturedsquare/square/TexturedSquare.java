package utp.openglapp.texturedsquare.square;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.opengl.GLES20;
import android.opengl.GLUtils;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import utp.openglapp.R;


public class TexturedSquare {
    private static float vertices[] = {
            -1.0f, 0.0f, 0f, 1f, // top left
            -1.0f, -1.0f, 0f, 1f, // bottom left
            1.0f, 0.0f, 0f, 1f, // top right
            1.0f, -1.0f, 0f, 1f // bottom right
    };

    private static float verticesColors[] = {
            1f, 0f, 0f, 1f,
            0f, 1f, 0f, 1f,
            0f, 0f, 1f, 1f,
            0f, 0f, 0f, 1f
    };

    private byte[] drawOrder = {0, 1, 2, 1, 2, 3};

    private FloatBuffer verticesBuffer;
    private FloatBuffer verticesColorBuffer;
    private ByteBuffer drawOrderBuffer;

    private int textureDataHandle;

    private final String vertexShader =
            "attribute vec4 position;" +
                    "attribute vec4 color;" +
                    "varying vex4 TextCoordinate;" +
                    "varying vec4 Color;" +
                    "void main() {" +
                    "   gl_Position = position;" +
                    "   Color = color;" +
                    "   TextCoordinate = position;" +
                    "}";


    private final String fragmentShader =
                    "precision mediump float;" +
                            "varying vec4 Color;" +
                            "varying vec4 TextCoordinate;" +
                            "uniform sampler2D texture;" +
                            "void main() {" +
                            "   diffuse = diffuse * (1.0 / (1.0 + (0.10 * distance)));" +
                            "   diffuse = diffuse + 0.3;" +
                            "   gl_FragColor = Color * diffuse * texture2D(texture, TextCoordinate);" +
                            "}";


    private int program;

    public TexturedSquare(Context context) {
        verticesBuffer = ByteBuffer.allocateDirect(vertices.length * 4).order(ByteOrder.nativeOrder()).asFloatBuffer();
        verticesBuffer.put(vertices).position(0);

        verticesColorBuffer = ByteBuffer.allocateDirect(verticesColors.length * 4).order(ByteOrder.nativeOrder()).asFloatBuffer();
        verticesColorBuffer.put(verticesColors).position(0);

        drawOrderBuffer = ByteBuffer.allocateDirect(drawOrder.length * 2).order(ByteOrder.nativeOrder());

        drawOrderBuffer.put(drawOrder).position(0);

        int vertexShader = TexturedSquareActivityRenderer.loadShader(GLES20.GL_VERTEX_SHADER, this.vertexShader);
        int fragmentShader = TexturedSquareActivityRenderer.loadShader(GLES20.GL_FRAGMENT_SHADER, this.fragmentShader);

        program = GLES20.glCreateProgram();

        GLES20.glAttachShader(program, vertexShader);
        GLES20.glAttachShader(program, fragmentShader);

        textureDataHandle = loadTexture(context, R.drawable.kitty);

        GLES20.glLinkProgram(program);

    }

    public void Draw() {
        GLES20.glUseProgram(program);

        int position = GLES20.glGetAttribLocation(program, "position");

        GLES20.glEnableVertexAttribArray(position);

        GLES20.glVertexAttribPointer(position, 3, GLES20.GL_FLOAT, false, 4 * 4, verticesBuffer);

        int color = GLES20.glGetAttribLocation(program, "color");

        GLES20.glEnableVertexAttribArray(color);

        GLES20.glVertexAttribPointer(color, 3, GLES20.GL_FLOAT, false, 4 * 4, verticesColorBuffer);

        int texture = GLES20.glGetUniformLocation(program, "texture");

        GLES20.glActiveTexture(GLES20.GL_TEXTURE0);
        GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, textureDataHandle);

        GLES20.glUniform1i(texture, 0);

        GLES20.glDrawElements(GLES20.GL_TRIANGLES, drawOrder.length, GLES20.GL_UNSIGNED_BYTE, drawOrderBuffer);

        GLES20.glDisableVertexAttribArray(position);
        GLES20.glDisableVertexAttribArray(color);
    }

    public static int loadTexture(final Context context, final int resourceId)
    {
        final int[] textureHandle = new int[1];

        GLES20.glGenTextures(1, textureHandle, 0);

        if (textureHandle[0] != 0)
        {
            final BitmapFactory.Options options = new BitmapFactory.Options();
            options.inScaled = false;   // No pre-scaling

            // Read in the resource
            final Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), resourceId, options);

            // Bind to the texture in OpenGL
            GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, textureHandle[0]);

            // Set filtering
            GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MIN_FILTER, GLES20.GL_NEAREST);
            GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MAG_FILTER, GLES20.GL_NEAREST);

            // Load the bitmap into the bound texture.
            GLUtils.texImage2D(GLES20.GL_TEXTURE_2D, 0, bitmap, 0);

            // Recycle the bitmap, since its data has been loaded into OpenGL.
            bitmap.recycle();
        }

        if (textureHandle[0] == 0)
        {
            throw new RuntimeException("Error loading texture.");
        }

        return textureHandle[0];
    }
}
