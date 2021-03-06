package com.ecloga.sortbook;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.melnykov.fab.FloatingActionButton;

public class Favourites extends android.support.v4.app.Fragment {

    public Favourites() {

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.favourites, container, false);

        Parameters.intentFrom = "favourites";

        LinearLayout linearLayout = (LinearLayout) view.findViewById(R.id.linearLayout);

        Typeface typeFace = Typeface.createFromAsset(getContext().getAssets(), "fonts/josefin_slab_regular.ttf");

        TextView tvNoTasks = (TextView) view.findViewById(R.id.tvNoFavs);
        tvNoTasks.setTypeface(typeFace);

        FloatingActionButton fab = (FloatingActionButton) view.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), Edit.class);
                intent.putExtra("activator", "favourites");
                startActivity(intent);
            }
        });

        final RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(false);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        final RecyclerViewRecyclerAdapter adapter = new RecyclerViewRecyclerAdapter(getActivity().getApplicationContext());

        for(int i = 0; i < Parameters.taskFavourites; i++) {
            String taskDetails = String.valueOf(Parameters.favDetails.get(i));
            Parameters.favDetails.set(i, taskDetails);
        }

        recyclerView.setAdapter(adapter);

        ItemTouchHelper swipeToDismissTouchHelper = new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(
                ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                int position = viewHolder.getAdapterPosition();

                if(direction == 8) {
                    UpdateTasks.removeTask(position);
                    UpdateTasks.removeFavTask(position);

                    adapter.notifyItemRemoved(position);
                    Snackbars.showDoneSnackbar(getActivity());
                }else if(direction == 4) {
                    String taskDetails = Parameters.favDetails.get(position);

                    if(taskDetails.contains("false")) {
                        UpdateTasks.addFavTask(taskDetails);
                        taskDetails = taskDetails.replace("false", "true");
                        Snackbars.showAddToFavouritesSnackbar(getActivity());
                    }else if(taskDetails.contains("true")) {
                        UpdateTasks.removeFavTask(position);
                        taskDetails = taskDetails.replace("true", "false");
                        Snackbars.showRemoveFromFavouritesSnackbar(getActivity());
                    }

                    Parameters.favDetails.set(position, taskDetails);
                    Parameters.taskDetails.set(position, taskDetails);
                    adapter.notifyDataSetChanged();
                }
            }
        });

        swipeToDismissTouchHelper.attachToRecyclerView(recyclerView);

        if(Parameters.taskFavourites == 0) {
            recyclerView.setVisibility(View.INVISIBLE);
            linearLayout.setVisibility(View.VISIBLE);
        }else {
            linearLayout.setVisibility(View.INVISIBLE);
            recyclerView.setVisibility(View.VISIBLE);
        }

        return view;
    }
}

