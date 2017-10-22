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
import utp.openglapp.square.SquareActivityRenderer;
import utp.openglapp.triangle.TriangleActivityRenderer;


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

    private final String vertexShader =
            "attribute vec4 position;" +
                    "attribute vec4 color;" +
                    "attribute vec2 texCoordinate;" +
                    "varying vec4 Color;" +
                    "varying vec2 TexCoordinate;" +
                    "void main() {" +
                    "   gl_Position = position;" +
                    "   TexCoordinate = texCoordinate;" +
                    "   Color = color;" +
                    "}";


    private final String fragmentShader =
            "precision mediump float;" +
                    "varying vec4 Color;" +
                    "varying vec2 TexCoordinate;" +
                    "uniform sampler2D u_Texture;" +
                    "void main() {" +
                    "   gl_FragColor = (Color * texture2D(u_Texture, TexCoordinate));" +
                    "}";


    private int program;

    public TexturedSquare() {
        verticesBuffer = ByteBuffer.allocateDirect(vertices.length * 4).order(ByteOrder.nativeOrder()).asFloatBuffer();
        verticesBuffer.put(vertices).position(0);

        verticesColorBuffer = ByteBuffer.allocateDirect(verticesColors.length * 4).order(ByteOrder.nativeOrder()).asFloatBuffer();
        verticesColorBuffer.put(verticesColors).position(0);

        drawOrderBuffer = ByteBuffer.allocateDirect(drawOrder.length * 2).order(ByteOrder.nativeOrder());

        drawOrderBuffer.put(drawOrder).position(0);

        int vertexShader = SquareActivityRenderer.loadShader(GLES20.GL_VERTEX_SHADER, this.vertexShader);
        int fragmentShader = SquareActivityRenderer.loadShader(GLES20.GL_FRAGMENT_SHADER, this.fragmentShader);

        program = GLES20.glCreateProgram();

        GLES20.glAttachShader(program, vertexShader);
        GLES20.glAttachShader(program, fragmentShader);

        GLES20.glLinkProgram(program);

        TexturedSquareActivityRenderer.loadTexture();
    }

    public void Draw() {
        GLES20.glUseProgram(program);

        int position = GLES20.glGetAttribLocation(program, "position");

        GLES20.glEnableVertexAttribArray(position);

        GLES20.glVertexAttribPointer(position, 3, GLES20.GL_FLOAT, false, 4 * 4, verticesBuffer);

        int color = GLES20.glGetAttribLocation(program, "color");

        GLES20.glEnableVertexAttribArray(color);

        GLES20.glVertexAttribPointer(color, 3, GLES20.GL_FLOAT, false, 4 * 4, verticesColorBuffer);

        int texture = GLES20.glGetUniformLocation(program, "u_Texture");
        int texturecoord = GLES20.glGetAttribLocation(program, "texCoordinate");
        GLES20.glEnableVertexAttribArray(texturecoord);

        GLES20.glVertexAttribPointer(texturecoord, 2, GLES20.GL_FLOAT, false, 4 * 4, verticesBuffer);


        GLES20.glActiveTexture(GLES20.GL_TEXTURE0);

        GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, TexturedSquareActivityRenderer.textureHandle);

        GLES20.glUniform1i(texture, 0);

        GLES20.glDrawElements(GLES20.GL_TRIANGLES, drawOrder.length, GLES20.GL_UNSIGNED_BYTE, drawOrderBuffer);

        GLES20.glDisableVertexAttribArray(position);
        GLES20.glDisableVertexAttribArray(color);
    }
}
