package example.com.albumdisplayer;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private   PhotoQueryManager photoQueryManager;
    private RecyclerView recyclerview;
    private PhotoAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
       initView();
    }

    private void initView(){
        recyclerview= (RecyclerView) findViewById(R.id.photo_rcv);
        photoQueryManager=new PhotoQueryManager(this);
        adapter=new PhotoAdapter();
        recyclerview.setLayoutManager(new GridLayoutManager(this,2));
        recyclerview.addItemDecoration(new DividerItemDecoration(this,DividerItemDecoration.HORIZONTAL));
        recyclerview.addItemDecoration(new DividerItemDecoration(this,DividerItemDecoration.VERTICAL));
         recyclerview.setAdapter(adapter);
        new AlbumLoader().execute();
    }
    class AlbumLoader extends AsyncTask<Void,Void,List<String>>{

        @Override
        protected List<String> doInBackground(Void... params) {
            List<String> photoList=new ArrayList<>();
           List<String>album= photoQueryManager.getAlbum();
            if (album != null&&album.size()>0) {
                for (String id:album) {
                    photoList.addAll(photoQueryManager.getPhoto(id));
                }
            }
            return photoList;
        }

        @Override
        protected void onPostExecute(List<String> strings) {
            super.onPostExecute(strings);
            adapter.refreshData(strings);

        }
    }
}
