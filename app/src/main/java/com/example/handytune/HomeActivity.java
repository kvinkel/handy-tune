package com.example.handytune;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.webkit.CookieManager;
import android.webkit.ValueCallback;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.handytune.spotify.RetrofitClient;
import com.spotify.sdk.android.authentication.AuthenticationClient;
import com.spotify.sdk.android.authentication.AuthenticationRequest;
import com.spotify.sdk.android.authentication.AuthenticationResponse;

import java.util.Random;

public class HomeActivity extends AppCompatActivity {


    Thread insertThread;
    Thread deleteThread;
    DbRepository dbRepository;

    private static final int REQUEST_CODE = 1337;
    private static final String REDIRECT_URI = "com.example.handytune://callback"; // com.yourdomain.yourapp://callback"
    private static final String CLIENT_ID = "94824f7bd8cc470887b4bf7b8cc8a103";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dbRepository = new DbRepository(getApplicationContext());

        setContentView(R.layout.activity_main);

        findViewById(R.id.loginButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                authenticate();
            }
        });

        findViewById(R.id.searchButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToSearch();
            }
        });

        findViewById(R.id.playlistButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToPlaylist();
            }
        });

        findViewById(R.id.userButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToSpotifyUser();
            }
        });

        findViewById(R.id.logoutButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case DialogInterface.BUTTON_POSITIVE:
                                logout();
                                break;

                            case DialogInterface.BUTTON_NEGATIVE:
                                //No button clicked
                                break;
                        }
                    }
                };
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(HomeActivity.this);
                alertDialog.setTitle("This will delete cookies");
                alertDialog.setMessage("Are you sure?").setPositiveButton("Yes", dialogClickListener)
                        .setNegativeButton("No", dialogClickListener).show();
            }
        });

    }

    @Override
    protected void onStart() {

        super.onStart();
    }

    public void goToSearch() {
        Intent intent = new Intent(this, SearchActivity.class);
        startActivity(intent);
    }

    public void goToPlaylist() {
        Intent intent = new Intent(this, PlaylistActivity.class);
        startActivity(intent);
    }

    public void goToSpotifyUser() {
        Intent intent = new Intent(this, SpotifyUserActivity.class);
        startActivity(intent);
    }

    public void authenticate() {
        AuthenticationRequest.Builder builder = new AuthenticationRequest.Builder(CLIENT_ID, AuthenticationResponse.Type.TOKEN, REDIRECT_URI);
        builder.setScopes(new String[]{"streaming"});
        AuthenticationRequest request = builder.build();
        AuthenticationClient.openLoginActivity(this, REQUEST_CODE, request);
    }

    public void logout() {
        CookieManager man = CookieManager.getInstance();
        ValueCallback<Boolean> callback = new ValueCallback<Boolean>() {
            @Override
            public void onReceiveValue(Boolean value) {
                if (value) {
                    Toast.makeText(HomeActivity.this, "Cookies cleared", Toast.LENGTH_LONG).show();
                    RetrofitClient.clearToken();
                } else {
                    Toast.makeText(HomeActivity.this, "Cookies not removed", Toast.LENGTH_LONG).show();
                }
            }
        };
        man.removeAllCookies(callback);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);

        // Check if result comes from the correct activity
        if (requestCode == REQUEST_CODE) {
            AuthenticationResponse response = AuthenticationClient.getResponse(resultCode, intent);

            switch (response.getType()) {
                // Response was successful and contains auth token
                case TOKEN:
                    // Handle successful response
                    System.out.println("YAY!");
                    System.out.println(response.getAccessToken());
                    RetrofitClient.setAuthToken(response.getAccessToken());
                    Toast.makeText(HomeActivity.this, "Success!", Toast.LENGTH_LONG).show();
                    break;
                // Auth flow returned an error
                case ERROR:
                    System.out.println("NAY!");
                    Toast.makeText(HomeActivity.this, response.getError().toString(), Toast.LENGTH_LONG).show();
                    break;
                // Most likely auth flow was cancelled
                default:
                    // Handle other cases
            }
        }
    }

}
