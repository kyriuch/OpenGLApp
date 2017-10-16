package utp.openglapp.triangle;

import android.opengl.GLES20;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;


public class Triangle {
    private static float vertices[] = {
            0f, 0.5f, 0f, 1f,
            -0.5f, 0f, 0f, 1f,
            0.5f, 0f, 0f, 1f
    };

    private static float verticesColors[] = {
            1f, 0f, 0f, 1f,
            0f, 1f, 0f, 1f,
            0f, 0f, 1f, 1f
    };

    private FloatBuffer verticesBuffer;
    private FloatBuffer verticesColorBuffer;

    private final String vertexShader =
            "attribute vec4 position;" +
                    "attribute vec4 color;" +
                    "varying vec4 Color;" +
                    "void main() {" +
                    "   gl_Position = position;" +
                    "   Color = color;" +
                    "}";


    private final String fragmentShader =
                    "precision mediump float;" +
                            "varying vec4 Color;" +
                            "void main() {" +
                            "   gl_FragColor = Color;" +
                            "}";


    private int program;

    public Triangle() {
        verticesBuffer = ByteBuffer.allocateDirect(vertices.length * 4).order(ByteOrder.nativeOrder()).asFloatBuffer();
        verticesBuffer.put(vertices).position(0);

        verticesColorBuffer = ByteBuffer.allocateDirect(verticesColors.length * 4).order(ByteOrder.nativeOrder()).asFloatBuffer();
        verticesColorBuffer.put(verticesColors).position(0);

        int vertexShader = TriangleActivityRenderer.loadShader(GLES20.GL_VERTEX_SHADER, this.vertexShader);
        int fragmentShader = TriangleActivityRenderer.loadShader(GLES20.GL_FRAGMENT_SHADER, this.fragmentShader);

        program = GLES20.glCreateProgram();

        GLES20.glAttachShader(program, vertexShader);
        GLES20.glAttachShader(program, fragmentShader);

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

        GLES20.glDrawArrays(GLES20.GL_TRIANGLES, 0, 3);

        GLES20.glDisableVertexAttribArray(position);
        GLES20.glDisableVertexAttribArray(color);
    }
}
