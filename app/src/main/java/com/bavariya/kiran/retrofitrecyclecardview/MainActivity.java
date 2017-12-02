package com.bavariya.kiran.retrofitrecyclecardview;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Rect;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.View;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.widget.DrawerLayout;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.Toast;


import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends Activity {


    public Handler handler;
    private SwipeRefreshLayout swipeContainer;
    protected FrameLayout frameLayout;
    protected ListView mDrawerList;
 //   protected String[] listArray = {"Item 1", "Item 2", "Item 3", "Item 4", "Item 5"};
    protected static int position;
    private static boolean isLaunch = true;
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle actionBarDrawerToggle;
    RecyclerView recyclerView;

    List<Product> listing;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = findViewById(R.id.recycle);
        swipeContainer = findViewById(R.id.swipeRefreshLayout);
        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {

            public final Runnable refreshing = new Runnable() {
                public void run() {
                    try {
                        if (isRefreshing()) {
                            Handler handler = null;
                            handler.postDelayed(this, 2000);
                        } else {
                            swipeContainer.setRefreshing(false);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            };
            @Override
            public void onRefresh() {
                ///////  getting error after setting handler
             //   handler = new Handler();
                handler.post(refreshing);
                handler.postAtTime(refreshing,3000);
            }
        });
        colorcan();
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(this).build();
        ImageLoader.getInstance().init(config);
        listing = new ArrayList<>();
        final Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://api2.mytrendin.com")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        APiService service = retrofit.create(APiService.class);
        Call<List<Product>> call = service.getbook();

        call.enqueue(new Callback<List<Product>>() {
            @Override
            public void onResponse(Call<List<Product>> call, Response<List<Product>> response) {

                List<Product> list = response.body();
                Product product = null;
                for (int i = 0; i < list.size(); i++) {
                    product = new Product();
                    String name = list.get(i).getProductname();
                    String color = list.get(i).getColor();
                    String image = list.get(i).getImageurl();
                    String price = list.get(i).getPrice();
                    product.setPrice(price);
                    product.setColor(color);
                    product.setProductname(name);
                    product.setImageurl(image);
                    listing.add(product);
                }

                Recycleradapter recyclerAdapter = new Recycleradapter(listing, ImageLoader.getInstance());
                RecyclerView.LayoutManager recyce = new GridLayoutManager(MainActivity.this, 2);
                // RecyclerView.LayoutManager recyce = new LinearLayoutManager(MainActivity.this);
                recyclerView.addItemDecoration(new GridSpacingdecoration(2, dpToPx(10), true));
                recyclerView.setLayoutManager(recyce);
                recyclerView.getItemAnimator();
                recyclerView.setItemAnimator(new DefaultItemAnimator());
                recyclerView.setAdapter(recyclerAdapter);
                recyclerView.addOnItemTouchListener(new RecyclerItemClickListener(MainActivity.this, new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        Intent myIntent = null;
                        if (position == 0) {
                            myIntent = new Intent(MainActivity.this, Main_Pager.class);
                        }
                        if (position == 1) {
                            myIntent = new Intent(MainActivity.this, TrafficSign.class);
                        }
                        if (position == 2) {
                            myIntent = new Intent(MainActivity.this, TrafficSign.class);
                        }
                        if (position == 3) {
                            myIntent = new Intent(MainActivity.this, TrafficSign.class);
                        }
                        if (position == 4) {
                            myIntent = new Intent(MainActivity.this, TrafficSign.class);
                        }
                        if (position == 5) {
                            myIntent = new Intent(MainActivity.this, TrafficSign.class);
                        }
                        if (position == 6) {
                            myIntent = new Intent(MainActivity.this, TrafficSign.class);
                        }
                        if (position == 7) {
                            myIntent = new Intent(MainActivity.this, TrafficSign.class);
                        }
                        if (position == 8) {
                            myIntent = new Intent(MainActivity.this, TrafficSign.class);
                        }
                        startActivity(myIntent);
                    }
                }));
            }

            @Override
            public void onFailure(Call<List<Product>> call, Throwable t) {

            }
        });

    }

    public boolean isRefreshing() {
        return swipeContainer.isRefreshing();
    }

    public class GridSpacingdecoration extends RecyclerView.ItemDecoration {

        private int span;
        private int space;
        private boolean include;

        public GridSpacingdecoration(int span, int space, boolean include) {
            this.span = span;
            this.space = space;
            this.include = include;
        }

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            int position = parent.getChildAdapterPosition(view);
            int column = position % span;

            if (include) {
                outRect.left = space - column * space / span;
                outRect.right = (column + 1) * space / span;

                if (position < span) {
                    outRect.top = space;
                }
                outRect.bottom = space;
            } else {
                outRect.left = column * space / span;
                outRect.right = space - (column + 1) * space / span;
                if (position >= span) {
                    outRect.top = space;
                }
            }
        }
    }
    /**
     * Converting dp to pixel
     */
    private int dpToPx(int dp) {
        Resources r = getResources();
        return Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, r.getDisplayMetrics()));
    }




    public void colorcan() {
        swipeContainer.setColorSchemeResources(
                android.R.color.holo_orange_light,
                android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_blue_dark,
                android.R.color.holo_red_light);
    }

}


//
