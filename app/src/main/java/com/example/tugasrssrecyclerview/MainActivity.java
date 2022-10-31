package com.example.tugasrssrecyclerview;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;

import org.xml.sax.SAXException;

import java.io.IOException;
import java.util.ArrayList;

import javax.xml.parsers.ParserConfigurationException;

public class MainActivity extends AppCompatActivity {

    final static String source = "https://medium.com/feed/tag/programming";
    RecyclerView rv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        rv = findViewById(R.id.rv);
        AsyncExample task=new AsyncExample(rv);
        rv.setLayoutManager(new LinearLayoutManager(MainActivity.this));
        task.execute();
    }

    class AsyncExample extends AsyncTask<Void,Void, ArrayList<Artikel>> {
        RecyclerView rv;
        ProgressDialog pd;

        public AsyncExample(RecyclerView rv) {
            this.rv = rv;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pd = new ProgressDialog(MainActivity.this);
            pd.setMessage("Masih loading...");
            pd.show();

        }

        @Override
        protected ArrayList<Artikel> doInBackground(Void... voids) {
            RssParser parser = new RssParser();
            try {
                String xml = parser.loadRssFromUrl(source);
                ArrayList<Artikel>arrayLists =parser.parseRssFromUrl(xml);
                return arrayLists;
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ParserConfigurationException e) {
                e.printStackTrace();
            } catch (SAXException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(ArrayList<Artikel> arrayLists) {
            super.onPostExecute(arrayLists);
            RssAdapter adapter = new RssAdapter(MainActivity.this,arrayLists);
            rv.setAdapter(adapter);
            pd.dismiss();
            adapter.notifyDataSetChanged();
        }
    }


}