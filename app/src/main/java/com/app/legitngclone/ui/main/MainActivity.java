package com.app.legitngclone.ui.main;

import android.app.Notification;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.RingtoneManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.app.legitngclone.R;
import com.facebook.AccessTokenTracker;
import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;
import com.app.legitngclone.ui.home.onef.OneFragmentViewModel;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;

import java.io.File;
import java.util.Arrays;
import java.util.zip.Inflater;

public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    CallbackManager callbackManager;
    LoginButton loginButton;
    private FirebaseAuth mAuth;
    TextView name;
    ImageView userImageView;
    View nav_view;
    AccessTokenTracker accessTokenTracker;
    FirebaseUser currentUser;
    public static boolean check = false;
    public static File file;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        mAuth = FirebaseAuth.getInstance();
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        nav_view = navigationView.getHeaderView(0);
        userImageView = nav_view.findViewById(R.id.userMainImageView);
        name = nav_view.findViewById(R.id.nameTextView);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow)
                .setDrawerLayout(drawer)
                .build();
        file = getCacheDir();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);
        callbackManager = CallbackManager.Factory.create();
        loginButton = findViewById(R.id.login_button);
        loginButton.setReadPermissions(Arrays.asList("email", "public_profile"));
        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                handleFacebookAccessToken(loginResult.getAccessToken());
            }

            @Override
            public void onCancel() {
                name.setText("Nigerian News");
                userImageView.setImageResource(R.drawable.mainlogo);
            }

            @Override
            public void onError(FacebookException error) {
                Log.i("errorfacebook",error.getMessage());
            }
        });
         accessTokenTracker = new AccessTokenTracker() {
            @Override
            protected void onCurrentAccessTokenChanged(AccessToken oldAccessToken, AccessToken currentAccessToken) {
                if(currentAccessToken == null){
                    name.setText("Nigerian News");
                    userImageView.setImageResource(R.drawable.mainlogo);
                    mAuth.signOut();
                }
            }
        };
        accessTokenTracker.startTracking();
        final SharedPreferences sharedPreferences = getSharedPreferences("PREFS",0);
        final SharedPreferences.Editor editor = sharedPreferences.edit();

        NotificationCompat.Builder notification;
        notification = new NotificationCompat.Builder(getApplicationContext());
        if(!sharedPreferences.getBoolean("sound",false)){
            Uri uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
            notification.setSound(uri);
        }else{
            notification.setDefaults(Notification.DEFAULT_ALL);
        }
        if(!sharedPreferences.getBoolean("vibration",false)){
            notification.setVibrate(new long[]{1000,1000,1000,1000,1000});
        }else{
            notification.setVibrate(null);
        }
        check = isNetworkAvailable();
        /*
        OneSignal.startInit(this)
                //.inFocusDisplaying(OneSignal.OSInFocusDisplayOption.Notification)
                .setNotificationOpenedHandler(new NotificationOpenedHandler())
                .autoPromptLocation(true)
                .init();
        if(currentUser != null){
            OneSignal.sendTag("User_ID",UsersInfo.getUsername());
        }
        OneSignal.idsAvailable(new OneSignal.IdsAvailableHandler() {
            @Override
            public void idsAvailable(String userId, String registrationId) {

            }
        });
*/
    }
    public boolean isNetworkAvailable(){
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onStart() {
        super.onStart();
        currentUser = mAuth.getCurrentUser();
        if (currentUser != null) {
            name.setText(currentUser.getDisplayName());
            if (currentUser.getPhotoUrl() != null) {
                Glide.with(this)
                        .load(currentUser.getPhotoUrl())
                        .fitCenter()
                        .placeholder(R.color.lightGrayColor)
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .dontTransform()
                        .into(userImageView);
            }
        }else{
            name.setText("Nigerian News");
        }

    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    private void handleFacebookAccessToken(AccessToken token) {
        AuthCredential credential = FacebookAuthProvider.getCredential(token.getToken());
        mAuth.signInWithCredential(credential).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    FirebaseUser currentUser = mAuth.getCurrentUser();
                    if (currentUser != null) {
                        name.setText(currentUser.getDisplayName());
                        if (currentUser.getPhotoUrl() != null) {
                            Glide.with(MainActivity.this)
                                    .load(currentUser.getPhotoUrl())
                                    .fitCenter()
                                    .placeholder(R.color.lightGrayColor)
                                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                                    .dontTransform()
                                    .into(userImageView);
                        }
                    }
                } else {
                    Toast.makeText(MainActivity.this, "Authentication failed.", Toast.LENGTH_SHORT).show();
                    Log.i("anotherError",task.toString());
                }
            }
        });
    }
}