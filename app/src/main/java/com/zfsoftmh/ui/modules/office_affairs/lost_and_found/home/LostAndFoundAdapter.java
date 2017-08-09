package com.zfsoftmh.ui.modules.office_affairs.lost_and_found.home;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.v4.content.ContextCompat;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;
import com.zfsoftmh.R;
import com.zfsoftmh.entity.LostAndFoundItemInfo;

/**
 * @author wesley
 * @date: 2017-5-27
 * @Description: 适配器
 */

public class LostAndFoundAdapter extends RecyclerArrayAdapter<LostAndFoundItemInfo> {

    private String userId; //用户登录的id
    private Context context;

    public LostAndFoundAdapter(Context context, String userId) {
        super(context);
        this.userId = userId;
        this.context = context;
    }

    @Override
    public BaseViewHolder OnCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(parent, R.layout.item_lost_and_found);
    }

    //静态内部类
    private class ViewHolder extends BaseViewHolder<LostAndFoundItemInfo> {

        private RelativeLayout rl_item; //布局
        private TextView tv_name; //名称
        private TextView tv_description; //描述
        private TextView tv_time; //时间
        private TextView tv_type; //类型

        public ViewHolder(ViewGroup parent, @LayoutRes int res) {
            super(parent, res);

            rl_item = $(R.id.item_lost_and_found);
            tv_name = $(R.id.lost_and_found_name);
            tv_description = $(R.id.lost_and_found_description);
            tv_time = $(R.id.lost_and_found_time);
            tv_type = $(R.id.lost_and_found_type);
        }

        @Override
        public void setData(LostAndFoundItemInfo data) {
            super.setData(data);

            if (data == null || context == null) {
                return;
            }

            String id = data.getUsername(); //用户id
            String name = data.getTitle(); //标题
            String description = data.getContent(); //内容
            String time = data.getTimecreatestr(); //时间
            int flag = data.getFlag(); // 1--失物招领 我捡东西了  0--寻物启事 我丟东西了
            int isPass = data.getIspass(); ////0代表为审核中，1代表未审核通过，2代表已审核通过
            String userName = data.getName(); //姓名

            tv_name.setText(name);
            tv_description.setText(description);
            tv_time.setText(time);

            if (id != null && id.equals(userId)) {

                switch (isPass) {
                /*
                 * 审核中
                 */
                case 0:
                    tv_type.setText(context.getResources().getString(R.string.under_review));
                    tv_type.setTextColor(ContextCompat.getColor(context, R.color.colorAccent));
                    break;

                /*
                 * 未审核通过
                 */
                case 1:
                    tv_type.setText(context.getResources().getString(R.string.the_audit_did_not_pass));
                    tv_type.setTextColor(ContextCompat.getColor(context, R.color.colorPrimary));
                    break;

                /*
                 * 已审核通过
                 */
                case 2:
                    tv_type.setText(context.getResources().getString(R.string.the_audit_pass));
                    tv_type.setTextColor(ContextCompat.getColor(context, R.color.colorPrimaryDark));
                    break;

                default:
                    break;
                }
            } else {
                switch (flag) {
                /*
                 * 我捡东西了
                 */
                case 0:
                    tv_type.setText(userName + context.getResources().getString(R.string.lost_and_founds));
                    tv_type.setTextColor(ContextCompat.getColor(context, R.color.colorAccent));
                    break;

                /*
                 * 我丟东西了
                 */
                case 1:
                    tv_type.setText(userName + context.getResources().getString(R.string.looking_for_notices));
                    tv_type.setTextColor(ContextCompat.getColor(context, R.color.colorPrimary));
                    break;

                default:
                    break;
                }
            }
        }
    }
}
