package com.getstream.sdk.chat.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.Typeface;
import android.util.AttributeSet;

import com.getstream.sdk.chat.R;
import com.getstream.sdk.chat.utils.BaseStyle;

public class ChannelHeaderViewStyle extends BaseStyle {
    private int channelTitleTextSize;
    // Color
    private int channelTitleTextColor;
    // Style
    private int channelTitleTextStyle;
    private boolean backButtonShow;

    public ChannelHeaderViewStyle(Context c, AttributeSet attrs) {
        // parse the attributes
        this.setContext(c);
        TypedArray a = this.getContext().obtainStyledAttributes(attrs,
                R.styleable.ChannelHeaderView, 0, 0);

        channelTitleTextSize = (int) a.getDimension(R.styleable.ChannelHeaderView_channelTitleTextSize, c.getResources().getDimension(R.dimen.stream_channel_initials));
        channelTitleTextColor = a.getColor(R.styleable.ChannelHeaderView_channelTitleTextColor, c.getResources().getColor(R.color.stream_channel_initials));
        channelTitleTextStyle = a.getInt(R.styleable.ChannelHeaderView_channelTitleTextStyle, Typeface.NORMAL);

        backButtonShow = a.getBoolean(R.styleable.ChannelHeaderView_backButtonShow, false);

        // Avatar
        avatarWidth = a.getDimension(R.styleable.ChannelHeaderView_channelHeaderAvatarWidth, c.getResources().getDimension(R.dimen.stream_channel_avatar_height));
        avatarHeight = a.getDimension(R.styleable.ChannelHeaderView_channelHeaderAvatarHeight, c.getResources().getDimension(R.dimen.stream_channel_avatar_width));

        avatarBorderWidth = a.getDimension(R.styleable.ChannelHeaderView_channelHeaderAvatarBorderWidth, c.getResources().getDimension(R.dimen.stream_channel_avatar_border_width));
        avatarBorderColor = a.getColor(R.styleable.ChannelHeaderView_channelHeaderAvatarBorderColor, Color.WHITE);
        avatarBackGroundColor = a.getColor(R.styleable.ChannelHeaderView_channelHeaderAvatarBackGroundColor, c.getResources().getColor(R.color.user_intials_background));

        avatarInitialTextSize = a.getDimension(R.styleable.ChannelHeaderView_channelHeaderAvatarTextSize, c.getResources().getDimension(R.dimen.stream_channel_initials));
        avatarInitialTextColor = a.getColor(R.styleable.ChannelHeaderView_channelHeaderAvatarTextColor, c.getResources().getColor(R.color.stream_channel_initials));
        avatarInitialTextStyle = a.getInt(R.styleable.ChannelHeaderView_channelHeaderAvatarTextStyle, Typeface.NORMAL);

        a.recycle();
    }

    public int getChannelTitleTextSize() {
        return channelTitleTextSize;
    }

    public int getChannelTitleTextColor() {
        return channelTitleTextColor;
    }

    public int getChannelTitleTextStyle() {
        return channelTitleTextStyle;
    }

    public boolean isBackButtonShow() {
        return backButtonShow;
    }
}
