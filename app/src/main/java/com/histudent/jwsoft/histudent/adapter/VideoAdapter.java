package com.histudent.jwsoft.histudent.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Icon;
import android.media.ThumbnailUtils;
import android.provider.MediaStore;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.histudent.jwsoft.histudent.R;
import com.histudent.jwsoft.histudent.commen.utils.imageloader.CommonGlideImageLoader;
import com.histudent.jwsoft.histudent.commen.view.IconView;
import com.histudent.jwsoft.histudent.entity.VideoInfoEntity;
import com.histudent.jwsoft.histudent.entity.WorkVideoDeleteEvent;
import com.histudent.jwsoft.histudent.entity.WorkVideoPlayEvent;
import com.histudent.jwsoft.histudent.tool.CommonTool;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by huyg on 2017/10/25.
 */

public class VideoAdapter extends RecyclerView.Adapter<VideoAdapter.ViewHolder> {
    private Context mContext;
    private List<VideoInfoEntity> mList = new ArrayList<>();
    private boolean isDelete = true;//是否可以删除

    public VideoAdapter(Context context) {
        this.mContext = context;
    }

    public void setList(List<VideoInfoEntity> list) {
        this.mList = list;
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_work_video, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        VideoInfoEntity videoInfo = mList.get(position);
        if (isDelete) {
            holder.mVideoDelete.setVisibility(View.VISIBLE);
        } else {
            holder.mVideoDelete.setVisibility(View.GONE);
        }
        if (videoInfo != null) {
            Bitmap bitmap = ThumbnailUtils.createVideoThumbnail(videoInfo.getFileName(), MediaStore.Images.Thumbnails.MINI_KIND);
            holder.mVideoCover.setImageBitmap(bitmap);
            holder.mVideoTime.setText(CommonTool.getTimeFromMillisecond(videoInfo.getDuration()));
        }
    }

    @Override
    public int getItemCount() {
        return mList == null ? 0 : mList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.item_video_cover)
        ImageView mVideoCover;
        @BindView(R.id.item_video_delete)
        IconView mVideoDelete;
        @BindView(R.id.item_video_time)
        TextView mVideoTime;
        @BindView(R.id.publish_movie_play)
        IconView mMoviePlay;

        @OnClick({R.id.item_video_cover, R.id.item_video_delete})
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.item_video_delete:
                    int position = getLayoutPosition();
                    EventBus.getDefault().post(new WorkVideoDeleteEvent(mList.get(position), position));
                    mList.remove(position);
                    if (getItemCount() != 0) {
                        notifyItemRemoved(position);
                        notifyItemRangeChanged(position, getItemCount() - position);
                    } else {
                        notifyDataSetChanged();
                    }
                    break;
                case R.id.publish_movie_play:
                case R.id.item_video_cover:
                    final WorkVideoPlayEvent workVideoPlayEvent = new WorkVideoPlayEvent(mList.get(getLayoutPosition()));
                    workVideoPlayEvent.position = getLayoutPosition();
                    EventBus.getDefault().post(workVideoPlayEvent);
                    break;
            }
        }

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
