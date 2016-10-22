package selinabing.cranberrymelon;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.astuetz.PagerSlidingTabStrip;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.viewpager)
    ViewPager viewPager;
    @BindView(R.id.tabs)
    PagerSlidingTabStrip tabs;

    public class AvailibilityPagerAdapter extends FragmentStatePagerAdapter {
        final int PAGE_COUNT = 2;
        private String tabTitles[] = {"Favorited", "All"};

        public AvailibilityPagerAdapter(FragmentManager fragmentManager) { super(fragmentManager); }

        @Override
        public Fragment getItem(int position) {
            Log.d("DEBUG","WHERES MY FRAGMENT");
            return new AvailabilityFragment();
        }

        @Override
        public int getCount() {
            return PAGE_COUNT;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return tabTitles[position];
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        viewPager.setAdapter(new AvailibilityPagerAdapter(getSupportFragmentManager()));
        tabs.setViewPager(viewPager);
        toolbar.setClickable(true);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("");

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        final MenuItem searchItem = menu.findItem(R.id.action_search);
        final SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchItem);
        searchItem.expandActionView();
        searchView.requestFocus();
        searchView.setIconifiedByDefault(false);
        searchView.setQueryHint("Enter friend's name");
        searchView.setOnSearchClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(), "!!!!!", Toast.LENGTH_LONG).show();

            }
        });
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                Toast.makeText(getApplication(), "query submit", Toast.LENGTH_SHORT).show();
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    public void onAddFriend (View view) {
        Intent i = new Intent(MainActivity.this, AddFriendActivity.class);
        startActivity(i);

    }

    public void onProfileView (MenuItem menuItem) {
        Intent i = new Intent(MainActivity.this, FriendProfileActivity.class);
        startActivity(i);

        Toast.makeText(this, "profile view clicked", Toast.LENGTH_SHORT).show();

    }

}
