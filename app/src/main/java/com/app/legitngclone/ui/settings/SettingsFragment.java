package com.app.legitngclone.ui.settings;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.app.legitngclone.ui.main.MainActivity;
import com.app.legitngclone.R;
import com.app.legitngclone.ui.main.dialoge_notifiications;
import com.app.legitngclone.ui.main.dialoge_text_size;

import java.io.File;
import java.util.ArrayList;

public class SettingsFragment extends Fragment {

    private SettingsViewModel settingsViewModel;
    ListView listView1;
    ListView listView2;
    long size;
    Switch aSwitch;
    String cache_value;
    public static boolean var = true;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        settingsViewModel =
                ViewModelProviders.of(this).get(SettingsViewModel.class);
        View root = inflater.inflate(R.layout.fragment_settings, container, false);
        ActionBar actionBar = ((MainActivity) getActivity()).getSupportActionBar();
        actionBar.setDisplayUseLogoEnabled(false);
        actionBar.setTitle("Settings");
        listView1 = root.findViewById(R.id.list1);
        listView2 = root.findViewById(R.id.list2);
        aSwitch = root.findViewById(R.id.noImages);
        size = getDirSize(getContext().getCacheDir());
        final SharedPreferences sharedPreferences = getContext().getSharedPreferences("PREFS",0);
        final SharedPreferences.Editor editor = sharedPreferences.edit();
        aSwitch.setChecked(sharedPreferences.getBoolean("images_mode",false));
        aSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                editor.putBoolean("images_mode",b);
                editor.commit();
            }
        });

        cache_value = android.text.format.Formatter.formatShortFileSize(getContext(),size);
        final ArrayList<String> items = new ArrayList<String>();
        items.add("Manage Notifications");
        items.add("Text Size");
        items.add("Clear cache"+"                         "+cache_value);

        ArrayList<String> items2 = new ArrayList<String>();
        items2.add("Invite friends");
        items2.add("Privacy Policy");

        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1, items){
            @NonNull
            @Override
            public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                View view = super.getView(position, convertView, parent);
                TextView tv = view.findViewById(android.R.id.text1);
                tv.setTextColor(Color.BLACK);
                return view;
            }
        };
        listView1.setAdapter(adapter);
        listView1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                final Intent intent;
                switch(i){
                    case 0:
                        intent = new Intent(getContext(), dialoge_notifiications.class);
                        startActivity(intent);
                        break;
                    case 1:
                        intent = new Intent(getContext(), dialoge_text_size.class);
                        startActivity(intent);
                        break;
                    case 2:
                        try{
                            File file = getContext().getCacheDir();
                            deleteDir(file);
                            items.set(2,"Clear cache"+"                         "+"0 B");
                            adapter.notifyDataSetChanged();

                        }catch (Exception e){
                            e.printStackTrace();
                        }
                        break;
                    case 3:

                        break;
                }
            }
        });
        ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1, items2){
            @NonNull
            @Override
            public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                View view = super.getView(position, convertView, parent);
                TextView tv = view.findViewById(android.R.id.text1);
                tv.setTextColor(Color.BLACK);
                return view;
            }
        };
        listView2.setAdapter(adapter2);
        listView2.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                final Intent intent;
                switch(i){
                    case 0:

                        break;
                    case 1:

                        break;

                }
            }
        });
        return root;
    }

    @Override
    public void onStart() {
        super.onStart();

    }

    public long getDirSize(File dir){
        long size = 0;
        for(File file : dir.listFiles()){
            if(file != null && file.isDirectory()){
                size+=getDirSize(file);
            }else if(file != null && file.isFile()){
                size += file.length();
            }
        }
        return size;
    }
    public static boolean deleteDir(File dir){
        if(dir != null && dir.isDirectory()){
            String[] children = dir.list();
            for(int j = 0; j < children.length; j++){
                boolean success = deleteDir(new File(dir, children[j]));
                if(!success){
                    return false;
                }
            }
            return dir.delete();
        }else if(dir!= null && dir.isFile()){
            return dir.delete();
        }else{
            return false;
        }
    }
}