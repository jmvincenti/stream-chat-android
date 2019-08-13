package com.getstream.sdk.chat.view;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.lifecycle.LifecycleOwner;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.getstream.sdk.chat.adapter.ChannelListItemAdapter;
import com.getstream.sdk.chat.model.Channel;
import com.getstream.sdk.chat.rest.User;
import com.getstream.sdk.chat.viewmodel.ChannelListViewModel;


public class ChannelListView extends RecyclerView {
    final String TAG = ChannelListView.class.getSimpleName();

    private ChannelListViewStyle style;

    // our connection to the channel scope
    private ChannelListViewModel viewModel;
    private UserClickListener userClickListener;
    private ChannelClickListener channelClickListener;
    private ChannelClickListener channelLongClickListener;
    private ChannelListItemAdapter adapter;

    public ChannelListView(Context context) {
        super(context);
        this.setHasFixedSize(true);
        this.setLayoutManager(new LinearLayoutManager(context));
    }

    public ChannelListView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.setHasFixedSize(true);
        this.setLayoutManager(new LinearLayoutManager(context));
        this.parseAttr(context, attrs);
    }

    public ChannelListView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.setHasFixedSize(true);
        this.setLayoutManager(new LinearLayoutManager(context));
        this.parseAttr(context, attrs);
    }

    private void parseAttr(Context context, @Nullable AttributeSet attrs) {
        // parse the attributes
        style = new ChannelListViewStyle(context, attrs);
    }

    public void setViewModel(ChannelListViewModel viewModel, LifecycleOwner lifecycleOwner, ChannelListItemAdapter adapter) {
        this.viewModel = viewModel;

        // connect the adapter
        this.adapter = adapter;
        this.setAdapterWithStyle(adapter);

        // connect the viewHolder on click listener...
        adapter.setChannelClickListener(this.channelClickListener);
        adapter.setChannelLongClickListener(this.channelLongClickListener);
        adapter.setUserClickListener(this.userClickListener);

        viewModel.getChannels().observe(lifecycleOwner, channels -> {
            Log.i(TAG, "Oberseve found this many channels: " + channels.size());
            adapter.replaceChannels(channels);
        });
    }

    public void setViewModel(ChannelListViewModel viewModel, LifecycleOwner lifecycleOwner) {
        // default adapter...
        adapter = new ChannelListItemAdapter(getContext());

        this.setViewModel(viewModel, lifecycleOwner, adapter);
    }

    public void setOnUserClickListener(UserClickListener l) {
        this.userClickListener = l;
        if (adapter != null) {
            adapter.setUserClickListener(l);
        }
    }

    public void setOnChannelClickListener(ChannelClickListener l) {
        this.channelClickListener = l;
        if (adapter != null) {
            adapter.setChannelClickListener(l);
        }
    }

    public void setOnLongClickListener(ChannelClickListener l) {
        this.channelLongClickListener = l;
        if (adapter != null) {
            adapter.setChannelLongClickListener(l);
        }
    }

    @Override
    public void setAdapter(Adapter adapter) {
        throw new IllegalArgumentException("Use setAdapterWithStyle instead please");
    }

    // set the adapter and apply the style.
    public void setAdapterWithStyle(ChannelListItemAdapter adapter) {
        super.setAdapter(adapter);
        adapter.setStyle(style);

        this.addOnScrollListener(new RecyclerView.OnScrollListener() {

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                LinearLayoutManager linearLayoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();

                if (linearLayoutManager != null) {

                    int lastVisible = linearLayoutManager.findLastCompletelyVisibleItemPosition();
                    Boolean reachedTheEnd = lastVisible == adapter.getItemCount() - 1;
                    // Log.i(TAG, String.format("Last visible is %d out of %d", lastVisible, adapter.getItemCount()));
                    // the viewmodel ensures that we only load once..
                    if (reachedTheEnd) {
                        viewModel.loadMore();
                    }


                }

            }
        });

    }


    public interface UserClickListener {
        void onClick(User user);
    }

    public interface ChannelClickListener {
        void onClick(Channel channel);
    }
}
