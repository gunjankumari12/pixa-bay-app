package com.example.myapplication.Activity;


import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import android.app.ProgressDialog;
import android.content.Intent;
import android.opengl.Visibility;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.myapplication.Adapter.GalleryAdapter;
import com.example.myapplication.Model.ImageModel;
import com.example.myapplication.R;
import com.example.myapplication.RecyclerItemClickListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private GalleryAdapter mAdapter;
    private  RecyclerView mRecyclerView;
    private Integer perpage= 20;
    private Integer pageNo = 1;
    private String URLstring = "https://pixabay.com/api/?key=17359597-42d34ece966a526b1283411da";
    ArrayList<ImageModel> dataModelArrayList = new ArrayList<>();
    private TextView next_tv;
    private TextView per_tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        next_tv=(TextView)findViewById(R.id.next_tv);
        per_tv=(TextView)findViewById(R.id.per_tv);

        mRecyclerView = (RecyclerView) findViewById(R.id.list);
        mRecyclerView.setLayoutManager(new GridLayoutManager(this, 4));
        mRecyclerView.setHasFixedSize(true);

        fetchingJSON(1);


    }




    private void fetchingJSON(Integer page) {

        final ProgressDialog pd = new ProgressDialog(MainActivity.this);
        pd.setCancelable(false);
        pd.setMessage("Please Wait...!");
        pd.show();

        StringBuffer sb = new StringBuffer(URLstring);
        sb.append("&per_page=" + perpage);
        sb.append("&page="+ pageNo);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, sb.toString(),
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject obj = new JSONObject(response);
                                dataModelArrayList = new ArrayList<>();
                                JSONArray dataArray = obj.getJSONArray("hits");
                                for (int i = 0; i < dataArray.length(); i++) {
                                    JSONObject jsonObject = dataArray.getJSONObject(i);
                                    String sub_img = jsonObject.getString("webformatURL");

                                    ImageModel dm = new ImageModel( sub_img);
                                    dataModelArrayList.add(dm);


                                }
                                mAdapter = new GalleryAdapter(MainActivity.this, dataModelArrayList);
                                mRecyclerView.setAdapter(mAdapter);
                                next_tv.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        pageNo=pageNo+1;
                                        fetchingJSON(pageNo);

                                        if(pageNo==2){
                                            per_tv.setVisibility(View.VISIBLE);
                                        }

                                    }
                                });

                            per_tv.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    pageNo=pageNo-1;

                                    fetchingJSON(pageNo);

                                    if(pageNo==1){
                                        per_tv.setVisibility(View.GONE);
                                    }

                                }
                            });
                                mRecyclerView.addOnItemTouchListener(new RecyclerItemClickListener(getApplicationContext(),
                                        new RecyclerItemClickListener.OnItemClickListener() {
                                            @Override
                                            public void onItemClick(View view, int position) {

                                                Intent intent = new Intent(MainActivity.this, ShowFullImage.class);
                                                intent.putParcelableArrayListExtra("data", dataModelArrayList);
                                                intent.putExtra("pos", position);
                                                startActivity(intent);

                                            }
                                        }));


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        pd.dismiss();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });

        RequestQueue requestQueue = Volley.newRequestQueue(this);

        requestQueue.add(stringRequest);


    }




}

