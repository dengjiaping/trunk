package com.histudent.jwsoft.histudent.body.mine.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.histudent.jwsoft.histudent.R;
import com.histudent.jwsoft.histudent.body.mine.activity.PersonalDataPresenter;
import com.histudent.jwsoft.histudent.body.mine.model.CurrentUserDetailInfoModel;
import com.histudent.jwsoft.histudent.commen.activity.BaseActivity;
import com.histudent.jwsoft.histudent.commen.activity.ImageBrowserActivity;
import com.histudent.jwsoft.histudent.commen.bean.ActionListBean;
import com.histudent.jwsoft.histudent.commen.enums.ShowImageType;
import com.histudent.jwsoft.histudent.commen.helper.ReminderHelper;
import com.histudent.jwsoft.histudent.commen.listener.DialogButtonListener;
import com.histudent.jwsoft.histudent.commen.listener.HttpRequestCallBack;
import com.histudent.jwsoft.histudent.commen.url.HistudentUrl;
import com.histudent.jwsoft.histudent.commen.utils.HiStudentHttpUtils;
import com.histudent.jwsoft.histudent.commen.utils.imageloader.CommonGlideImageLoader;
import com.histudent.jwsoft.histudent.commen.view.IconView;
import com.histudent.jwsoft.histudent.model.manage.PhotoManager;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * 个人资料奖项适配器
 */
public class PersionDataAdapter extends BaseAdapter {
    private List<CurrentUserDetailInfoModel.ProfileBean.HonorsListBean> list;
    private BaseActivity context;
    private PersonalDataPresenter presenter;

    public PersionDataAdapter(List<CurrentUserDetailInfoModel.ProfileBean.HonorsListBean> list, BaseActivity context, PersonalDataPresenter presenter) {
        this.context = context;
        this.list = list;
        this.presenter = presenter;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, final ViewGroup parent) {

        ViewHolder viewHolder;

        if (convertView == null) {
            convertView = View.inflate(context, R.layout.persiondata_list_item, null);
            viewHolder = new ViewHolder();
            viewHolder.persiondata_delet = (IconView) convertView.findViewById(R.id.persiondata_delet);
            viewHolder.persiondata_imageView = (ImageView) convertView.findViewById(R.id.persiondata_imageView);
            viewHolder.persiondata_diplomanum = (TextView) convertView.findViewById(R.id.persiondata_diplomanum);
            viewHolder.persiondata_diploma_time = (TextView) convertView.findViewById(R.id.persiondata_diploma_time);
            viewHolder.persiondata_diploma_name = (TextView) convertView.findViewById(R.id.persiondata_diploma_name);
            convertView.setTag(viewHolder);

        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        if (presenter == null) {
            viewHolder.persiondata_delet.setVisibility(View.GONE);
        }

        final CurrentUserDetailInfoModel.ProfileBean.HonorsListBean model = list.get(position);
        viewHolder.persiondata_diplomanum.setText((position + 1) + "");

        if (model.getName().length() > 10) {
            viewHolder.persiondata_diploma_name.setTextSize(12);
            viewHolder.persiondata_diploma_name.setMaxEms(8);
        }
        viewHolder.persiondata_diploma_name.setText(model.getName());


        viewHolder.persiondata_diploma_time.setText(model.getGetTime().split(" ")[0]);
        CommonGlideImageLoader.getInstance().displayNetImageWithAnimation(context, model.getPicture(),
                viewHolder.persiondata_imageView, PhotoManager.getInstance().getDefaultPlaceholderResource());
        viewHolder.persiondata_delet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ReminderHelper.getIntentce().showDialog(context, null, "是否删除该奖项", "确定", new DialogButtonListener() {
                    @Override
                    public void setOnDialogButtonListener() {
                        Map<String, Object> map = new TreeMap<>();
                        map.put("honerId", model.getHonorId());

                        HiStudentHttpUtils.postDataByOKHttp(context, map, HistudentUrl.deletHoner, new HttpRequestCallBack() {
                            @Override
                            public void onSuccess(String result) {

                                if (presenter != null)
                                    presenter.UpDataUI();
                            }

                            @Override
                            public void onFailure(String msg) {

                            }
                        });
                    }
                }, "取消", new DialogButtonListener() {
                    @Override
                    public void setOnDialogButtonListener() {

                    }
                });
            }
        });

        viewHolder.persiondata_imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ArrayList<ActionListBean.ImagesBean> urls2 = new ArrayList<>();
                ActionListBean.ImagesBean imagesBean = new ActionListBean.ImagesBean();
                imagesBean.setThumbnailUrl(model.getPicture());
                imagesBean.setBigSizeUrl(model.getPicture());
                urls2.add(imagesBean);
                ImageBrowserActivity.start(context, 0, 100, urls2, ShowImageType.EXCHANGE, 0, "");
            }
        });


        return convertView;
    }

    class ViewHolder {
        private ImageView persiondata_imageView;
        private IconView persiondata_delet;
        private TextView persiondata_diploma_name, persiondata_diploma_time, persiondata_diplomanum;
    }
}
