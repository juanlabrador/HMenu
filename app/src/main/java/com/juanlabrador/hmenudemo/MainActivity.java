package com.juanlabrador.hmenudemo;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.github.juanlabrador.hmenu.HMenu;

public class MainActivity extends Activity {

    private HMenu mHorizontalMenu;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        createHorizontalMenu();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void createHorizontalMenu() {
        mHorizontalMenu = (HMenu) findViewById(R.id.main_add_entry);
        final int[] mIcons = {
                R.drawable.icn_feed_feeding, R.drawable.icn_feed_sleeping,
                R.drawable.icn_feed_diapers, R.drawable.icn_feed_sickness,
                R.drawable.icn_feed_temperature, R.drawable.icn_feed_medicines,
                R.drawable.icn_feed_teeth, R.drawable.icn_feed_other
        };

        for (int i = 0; i < mIcons.length; i++) {
            ImageView mItemIcon = new ImageView(this);
            mItemIcon.setImageResource(mIcons[i]);

            final int position = i;
            mHorizontalMenu.addItem(mItemIcon, new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(getApplicationContext(), "Position: " + position, Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}
