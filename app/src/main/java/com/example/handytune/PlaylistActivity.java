package com.example.handytune;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.handytune.database.Album;

import java.util.ArrayList;
import java.util.List;

public class PlaylistActivity extends AppCompatActivity {

    Thread insertThread;
    Thread deleteThread;

    DbRepository dbRepository;
    List<Album> listOfAlbums;

    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_playlist);


        recyclerView = findViewById(R.id.playlistRecyclerView);
        recyclerView.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        adapter = new PlaylistAdapter(generatePlaylistForTesting(50));
        recyclerView.setAdapter(adapter);

        dbRepository = new DbRepository(getApplicationContext());
        listOfAlbums = new ArrayList<>();

        //Start thread on onCreate
        startThreadForInsertData();


    }

    int i = 1;

    public void startThreadForInsertData() {

        //Create a thread
        insertThread = new Thread(new Runnable() {
            @Override
            public void run() {

                System.out.println("Thread run*******");

                int userId =1;
                System.out.println("Before insert album(s) to database *********");
                /*For testing*/
                dbRepository.insertAlbum(userId, 100, "Barbiegirl", "testUrl 1", "imageUrl 1");
                listOfAlbums =dbRepository.getAlbumNamesFromUser(userId);
                for (int j = 0; j < listOfAlbums.size() ; j++) {
                    System.out.println( "Id "+ listOfAlbums.get(j).getId()+" *********");
                    System.out.println( "User id "+ listOfAlbums.get(j).getUserId()+" *********");
                    System.out.println( "Album id "+ listOfAlbums.get(j).getAlbumId()+" *********");
                    System.out.println( "Album name "+ listOfAlbums.get(j).getAlbumName()+" *********");
                    System.out.println( "Url "+ listOfAlbums.get(j).getUrl()+" *********");
                    System.out.println( "ImageUrl "+ listOfAlbums.get(j).getImageUrl()+" *********");
                }
                System.out.println("After insert album to database *********");
            }
        });
        insertThread.start();
    }


    public void startThreadForDeleteData() {

        //Create a thread
        deleteThread = new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("Nuke album");
                dbRepository.nukeAlbum();
            }
        });
        deleteThread.start();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        startThreadForDeleteData();

        //Stop threads when activity is destroyed
        insertThread.interrupt();
        deleteThread.interrupt();


    }

    private ArrayList<String> generatePlaylistForTesting(int amount) {
        ArrayList<String> arrayList = new ArrayList<>();
        String s = "My playlist ";
        for (int i = 1; i < amount; i++) {
            arrayList.add(s + i);
        }
        return arrayList;
    }
}
