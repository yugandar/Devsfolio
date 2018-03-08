package com.madonasyombua.growwithgoogleteamproject.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.madonasyombua.growwithgoogleteamproject.R;
import com.madonasyombua.growwithgoogleteamproject.actvities.AddFeeds;
import com.madonasyombua.growwithgoogleteamproject.models.Feeds;
import com.madonasyombua.growwithgoogleteamproject.util.Constant;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * to handle interaction events.
 * Use the {@link FeedsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FeedsFragment extends Fragment{
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";


    private static final String TAG = "FeedsFragment";
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private View view;
    private List<Feeds> feedsList = new ArrayList<>();
    private RecyclerView recyclerView;

    private FirebaseRecyclerAdapter<Feeds, FeedsViewHolder> adapter;

    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference reference = database.getReference(Constant.FIREBASE_FEEDS);


    public FeedsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FeedsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static FeedsFragment newInstance(String param1, String param2) {
        FeedsFragment fragment = new FeedsFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_feeds, container, false);

        //set up adapter
//        setUpAdapter();

        recyclerView = view.findViewById(R.id.feeds_recyclerview);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);

        FloatingActionButton addFeeds = view.findViewById(R.id.add_feeds);
        if (addFeeds != null)
            addFeeds.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    openFeedsActivity(view);
                }
            });

        return view;
    }

    private void setUpAdapter() {
        adapter = new FirebaseRecyclerAdapter<Feeds, FeedsViewHolder>(
                Feeds.class,
                R.layout.feeds_list_item,
                FeedsViewHolder.class,
                reference
        ) {
            @Override
            protected void populateViewHolder(FeedsViewHolder viewHolder, Feeds model, int position) {
                viewHolder.feed_title.setText(feedsList.get(position).getFeed_name());
            }
        };
    }

    //my method to open the FeedsActivity
    private void openFeedsActivity(@SuppressWarnings("unused") View view) {
        Intent intent = new Intent(this.getActivity(), AddFeeds.class);
        startActivity(intent);
        Toast.makeText(getContext(), "Adding Feed", Toast.LENGTH_SHORT).show();

    }

    private static class FeedsViewHolder extends RecyclerView.ViewHolder{
        TextView feed_title;

        public FeedsViewHolder(View itemView) {
            super(itemView);
            feed_title = itemView.findViewById(R.id.tv_feed_title);
        }
    }
}
