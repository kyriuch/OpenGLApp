package utp.openglapp;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.Arrays;
import java.util.List;

import utp.openglapp.square.SquareActivity;
import utp.openglapp.texturedsquare.square.TexturedSquareActivity;
import utp.openglapp.triangle.TriangleActivity;


public class MainActivity extends AppCompatActivity {

    interface Clickable {
        void click();
    }

    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        context = this;

        ListView listView = (ListView) findViewById(R.id.listView);

        List<String> menus = Arrays.asList("Tęczowy trójkąt", "Kwadratowy trójkąt", "Trzecie menu", "Czwarte menu", "Piąte menu", "Szóste menu", "Siódme menu");

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.listview_item, menus);

        listView.setAdapter(adapter);

        setListViewListener(listView);
    }

    private void setListViewListener(ListView listView) {
        final Clickable[] clickables = new Clickable[]{
                new Clickable() {
                    @Override
                    public void click() {
                        Intent intent = new Intent(context, TriangleActivity.class);
                        startActivity(intent);
                    }
                },
                new Clickable() {
                    @Override
                    public void click() {
                        Intent intent = new Intent(context, SquareActivity.class);
                        startActivity(intent);
                    }
                },
                new Clickable() {
                    @Override
                    public void click() {
                        Intent intent = new Intent(context, TexturedSquareActivity.class);
                        startActivity(intent);
                    }
                },
                new Clickable() {
                    @Override
                    public void click() {

                    }
                },
                new Clickable() {
                    @Override
                    public void click() {

                    }
                },
                new Clickable() {
                    @Override
                    public void click() {

                    }
                },
                new Clickable() {
                    @Override
                    public void click() {

                    }
                }
        };

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                clickables[position].click();
            }
        });
    }
}
