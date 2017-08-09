package com.zfsoftmh.ui.modules.chatting.custom;

import android.app.Activity;
import android.app.Dialog;
import android.content.ClipData;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;
import android.net.Uri;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.mobileim.YWChannel;
import com.alibaba.mobileim.YWIMKit;
import com.alibaba.mobileim.aop.Pointcut;
import com.alibaba.mobileim.aop.custom.IMChattingPageOperateion;
import com.alibaba.mobileim.aop.model.ReplyBarItem;
import com.alibaba.mobileim.aop.model.YWChattingPlugin;
import com.alibaba.mobileim.channel.YWEnum;
import com.alibaba.mobileim.channel.helper.ImageMsgPacker;
import com.alibaba.mobileim.channel.upload.UploadManager;
import com.alibaba.mobileim.channel.util.FileUtils;
import com.alibaba.mobileim.channel.util.WXUtil;
import com.alibaba.mobileim.channel.util.WxLog;
import com.alibaba.mobileim.contact.IYWContact;
import com.alibaba.mobileim.conversation.YWConversation;
import com.alibaba.mobileim.conversation.YWGeoMessageBody;
import com.alibaba.mobileim.conversation.YWImageMessageBody;
import com.alibaba.mobileim.conversation.YWMessage;
import com.alibaba.mobileim.conversation.YWMessageChannel;
import com.alibaba.mobileim.fundamental.widget.WxAlertDialog;
import com.alibaba.mobileim.kit.contact.YWContactHeadLoadHelper;
import com.alibaba.mobileim.utility.IMFileTools;
import com.alibaba.mobileim.utility.IMImageCache;
import com.alibaba.mobileim.utility.IMImageUtils;
import com.alibaba.mobileim.utility.IMNotificationUtils;
import com.alibaba.mobileim.utility.IMThumbnailUtils;
import com.alibaba.wxlib.config.StorageConstant;
import com.alibaba.wxlib.util.WXFileTools;
import com.google.gson.Gson;
import com.tencent.smtt.sdk.QbSdk;
import com.tencent.smtt.sdk.ValueCallback;
import com.zfsoftmh.R;
import com.zfsoftmh.entity.FileInfo;
import com.zfsoftmh.listener.CallBackListener;
import com.zfsoftmh.ui.base.BaseApplication;
import com.zfsoftmh.ui.modules.chatting.helper.CollectionHelper;
import com.zfsoftmh.ui.modules.chatting.helper.IMKitHelper;
import com.zfsoftmh.ui.modules.chatting.helper.KVStoreHelper;
import com.zfsoftmh.ui.modules.chatting.helper.view.file_explorer.FileExplorerActivity;
import com.zfsoftmh.ui.modules.chatting.helper.view.forward.ActivityCollector;
import com.zfsoftmh.ui.modules.chatting.helper.view.forward.CardSendActivity;
import com.zfsoftmh.ui.modules.chatting.helper.view.forward.CustomMsg;
import com.zfsoftmh.ui.modules.chatting.helper.view.forward.ForwardConversationActivity;
import com.zfsoftmh.ui.modules.chatting.location.LocationActivity;
import com.zfsoftmh.ui.modules.chatting.location.OpenGeoMessageActivity;
import com.zfsoftmh.ui.modules.chatting.tribe.profile.TribeMemberProfileActivity;
import com.zfsoftmh.ui.modules.personal_affairs.favourites.FavouritesListActivity;
import com.zfsoftmh.ui.modules.web.WebActivity;
import com.zfsoftmh.ui.widget.OnceClickListener;
import com.zfsoftmh.ui.widget.photopicker.PhotoPicker;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static android.app.Activity.RESULT_OK;
import static android.content.Context.CLIPBOARD_SERVICE;

/**
 * Created by sy
 * on 2017/4/27.
 */
public class Chatting_Operation extends IMChattingPageOperateion {

    public Chatting_Operation(Pointcut pointcut) {
        super(pointcut);
    }



    /*%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%以下是自定义消息%%%%%%%%%%%%%%%%%%%%%%%%%%%%*/

    public class CustomMessageType {
        public static final String CARD = "CallingCard";
        public static final String READ_STATUS = "PrivateImageRecvRead";
        public static final String FILE = "CallingFile";
    }

    //地理位置消息
    private final int type_0 = 0;
    //名片消息
    private final int type_1 = 1;
    //file message
    private final int type_2 = 2;

    /**
     * 自定义消息view的种类数
     * @return  自定义消息view的种类数
     */
    @Override
    public int getCustomViewTypeCount() {
        return 3;
    }

    /**
     * 自定义消息view的类型
     * @param message 需要自定义显示的消息
     * @return  自定义消息view的类型
     */
    @Override
    public int getCustomViewType(YWMessage message) {
        if (message.getSubType() == YWMessage.SUB_MSG_TYPE.IM_GEO){
            return type_0;
        }else if (message.getSubType() == YWMessage.SUB_MSG_TYPE.IM_P2P_CUS || message.getSubType() == YWMessage.SUB_MSG_TYPE.IM_TRIBE_CUS) {
            String msgType;
            try {
                String content = message.getMessageBody().getContent();
                Gson gson = new Gson();
                CustomMsg cMsg = gson.fromJson(content, CustomMsg.class);
                msgType = cMsg.customizeMessageType;
                if (!TextUtils.isEmpty(msgType)){
                    if (TextUtils.equals(msgType,CustomMessageType.CARD)){
                        return type_1;
                    }else if(TextUtils.equals(msgType,CustomMessageType.FILE)){
                        return type_2;
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return super.getCustomViewType(message);
    }

    /**
     * 特殊item是否需要隐藏头像
     * @param viewType 自定义view类型
     * @return true: 隐藏头像  false：不隐藏头像
     */
    @Override
    public boolean needHideHead(int viewType) {
        return /*viewType == type_1*/false;
    }

    /**
     * 特殊item是否需要隐藏显示名
     * @param viewType 自定义view类型
     * @return true: 隐藏显示名  false：不隐藏显示名
     */
    @Override
    public boolean needHideName(int viewType) {
        return /*viewType == type_1*/false;
    }

    /**
     * 根据viewType获取自定义view
     * @param fragment      聊天窗口fragment
     * @param message       当前需要自定义view的消息
     * @param convertView   自定义view
     * @param viewType      自定义view的类型
     * @param headLoadHelper    头像加载管理器
     * @return  自定义view
     */
    @Override
    public View getCustomView(Fragment fragment, YWMessage message, View convertView, int viewType, YWContactHeadLoadHelper headLoadHelper) {
        if (viewType == type_0){//地理位置消息
            ViewHolderGeo holder;
            if (convertView == null){
                holder = new ViewHolderGeo();
                convertView = View.inflate(fragment.getActivity(), R.layout.geo_message_layout, null);
                holder.address = (TextView) convertView.findViewById(R.id.geo_content);
                holder.info= (TextView) convertView.findViewById(R.id.geo_content_info);
                convertView.setTag(holder);
            }else{
                holder = (ViewHolderGeo)convertView.getTag();
            }
            YWGeoMessageBody messageBody = (YWGeoMessageBody) message.getMessageBody();

            int pos=messageBody.getAddress().indexOf("n");
            if(pos>1){
                holder.address.setText(messageBody.getAddress().substring(0,pos));
                holder.info.setText(messageBody.getAddress().substring(pos+1));
            }else{
                holder.address.setText(messageBody.getAddress());
                holder.info.setText(messageBody.getAddress());
            }

            return convertView;
        }else if (viewType == type_1){//名片消息
            String userId ;
            try {
                String msgContent = message.getMessageBody().getContent();
                Gson gson = new Gson();
                CustomMsg cMsg = gson.fromJson(msgContent,CustomMsg.class);
                userId = cMsg.arg0;
                ViewHolderCard holder;
                if (convertView == null){
                    holder = new ViewHolderCard();
                    convertView = View.inflate(fragment.getActivity(), R.layout.custom_msg_layout_without_head, null);
                    holder.head = (ImageView) convertView.findViewById(R.id.head);
                    holder.name = (TextView) convertView.findViewById(R.id.name);
                    convertView.setTag(holder);
                }else {
                    holder = (ViewHolderCard) convertView.getTag();
                }
                holder.name.setText(userId);
                YWIMKit imKit = IMKitHelper.getInstance().getIMKit();
                IYWContact contact = imKit.getContactService().getContactProfileInfo(userId, IMKitHelper.APP_KEY);
                if (contact != null) {
                    String nick = contact.getShowName();
                    if (!TextUtils.isEmpty(nick)) {
                        holder.name.setText(nick);
                    }
                    String avatarPath = contact.getAvatarPath();
                    if (avatarPath != null) {
                        headLoadHelper.setCustomOrTribeHeadView(holder.head, R.drawable.aliwx_head_default, avatarPath);
                    }
                }
                return convertView;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }else if(viewType == type_2){//文件消息
            String msgContent = message.getMessageBody().getContent();
            Gson gson = new Gson();
            CustomMsg cMsg = gson.fromJson(msgContent,CustomMsg.class);
            ViewHolderFile holder;
            if(convertView == null){
                holder = new ViewHolderFile();
                convertView = View.inflate(fragment.getActivity(), R.layout.custom_msg_layout_file, null);
                holder.fileType = (ImageView) convertView.findViewById(R.id.iv_file);
                holder.fileTitle = (TextView) convertView.findViewById(R.id.file_name);
                holder.fileSize = (TextView) convertView.findViewById(R.id.file_size);
                convertView.setTag(holder);
            }else{
                holder = (ViewHolderFile) convertView.getTag();
            }
            holder.fileTitle.setText(cMsg.arg0);
            holder.fileSize.setText(cMsg.arg5);
            switch (cMsg.arg3){
                case "1":
                    if (!TextUtils.isEmpty(cMsg.arg4))
                    switch (cMsg.arg4){
                        case FileInfo.TYPE_WORD:
                            holder.fileType.setBackgroundResource(R.mipmap.word);
                            break;
                        case FileInfo.TYPE_PPT:
                            holder.fileType.setBackgroundResource(R.mipmap.ppt);
                            break;
                        case FileInfo.TYPE_PDF:
                            holder.fileType.setBackgroundResource(R.mipmap.pdf);
                            break;
                        case FileInfo.TYPE_XLS:
                            holder.fileType.setBackgroundResource(R.mipmap.excel);
                            break;
                    }
                    break;
                case "2":
                    holder.fileType.setBackgroundResource(R.mipmap.music);
                    break;
                case "3":
                    holder.fileType.setBackgroundResource(R.mipmap.music);
                    break;
            }
        }
        return convertView;
    }

    private class ViewHolderGeo {
        TextView address;
        TextView  info;
    }

    private class ViewHolderCard {
        ImageView head;
        TextView name;
    }

    private class ViewHolderFile {
        ImageView fileType;
        TextView fileTitle;
        TextView fileSize;
    }

    /*%%%%%%%%%%%%%%%%%%%%%%%%%%% 以上是自定义消息%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%*/






    /*%%%%%%%%%%%%%%%%%%%%%%%%%%% 以下是自定义回复栏的操作区 %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%*/



    /**
     *  注意不要和内部的ID重合
     * {@link YWChattingPlugin.ReplyBarItem#ID_CAMERA}
     * {@link YWChattingPlugin.ReplyBarItem#ID_ALBUM}
     * {@link YWChattingPlugin.ReplyBarItem#ID_SHORT_VIDEO}
     *
     * 用于增加聊天窗口 下方回复栏的操作区的item
     *
     * ReplyBarItem
     * itemId:唯一标识 从1开始
     * ItemImageRes：显示的图片
     * ItemLabel：文字
     * needHide:是否隐藏 默认: false ,  显示：false ， 隐藏：true
     * OnClickListener: 自定义点击事件, null则使用默认的点击事件
     * @param pointcut         聊天窗口fragment
     * @param replyBarItemList 默认的replyBarItemList
     */
    @Override
    public List<ReplyBarItem> getCustomReplyBarItemList(final Fragment pointcut, final YWConversation ywConversation, List<ReplyBarItem> replyBarItemList) {
        List<ReplyBarItem> replyBarItems = new ArrayList<>();
        for (ReplyBarItem replyBarItem : replyBarItemList){
            switch (replyBarItem.getItemId()){
                case YWChattingPlugin.ReplyBarItem.ID_CAMERA://拍照
                    replyBarItem.setNeedHide(false);
                    replyBarItem.setOnClicklistener(null);
                    break;
                case YWChattingPlugin.ReplyBarItem.ID_ALBUM:
                    replyBarItem.setNeedHide(false);
                    replyBarItem.setOnClicklistener(new OnceClickListener() {
                        @Override
                        public void onOnceClick(View v) {
                            PhotoPicker.builder()
                                    .setPhotoCount(1)
                                    .setShowCamera(false)
                                    .setPreviewEnabled(true)
                                    .start(pointcut.getActivity());
                        }
                    });
                    break;
                case YWChattingPlugin.ReplyBarItem.ID_SHORT_VIDEO:
                    replyBarItem.setNeedHide(true);
                    break;
            }
            replyBarItems.add(replyBarItem);
        }

        replyBarItems.add(makeReplyBarItem(0x1,R.mipmap.reply_bar_location,"位置",new OnceClickListener(){
            @Override
            public void onOnceClick(View v) {
                sendGeoMessage(pointcut.getActivity(),ywConversation);
            }
        }));

        replyBarItems.add(makeReplyBarItem(0x2,R.mipmap.reply_bar_profile_card,"名片",new OnceClickListener() {
            @Override
            public void onOnceClick(View v) {
                selectContact(pointcut.getActivity(),ywConversation);
            }
        }));

        replyBarItems.add(makeReplyBarItem(0x3,R.mipmap.reply_bar_profile_card,"收藏",new OnceClickListener() {
            @Override
            public void onOnceClick(View v) {
                Intent intent = new Intent(pointcut.getActivity(), FavouritesListActivity.class);
                pointcut.getActivity().startActivityForResult(intent,-1);
            }
        }));

        replyBarItems.add(makeReplyBarItem(0x4,R.mipmap.reply_bar_profile_card,"文件",new OnceClickListener() {
            @Override
            public void onOnceClick(View v) {
                Intent intent = new Intent(pointcut.getActivity(), FileExplorerActivity.class);
                intent.putExtra("conversation_id",ywConversation.getConversationId());
                pointcut.getActivity().startActivity(intent);
            }
        }));
        return replyBarItems;
    }

    private ReplyBarItem makeReplyBarItem(int itemId,int resID,String name,OnceClickListener callback){
        ReplyBarItem replyBarItem = new ReplyBarItem();
        replyBarItem.setItemId(itemId);
        replyBarItem.setItemImageRes(resID);
        replyBarItem.setItemLabel(name);
        replyBarItem.setOnClicklistener(callback);
        return replyBarItem;
    }

    /**
     * 发送名片消息
     * @param conversation 当前会话
     */
    private void selectContact(FragmentActivity activity,YWConversation conversation) {
        Intent intent = new Intent(activity, CardSendActivity.class);
        intent.putExtra("conversation_id",conversation.getConversationId());
        activity.startActivity(intent);
    }

    /**
     * 发送地理位置消息
     * @param ywConversation 当前会话
     */
    private void sendGeoMessage(FragmentActivity activity,YWConversation ywConversation) {
        Intent intent = new Intent(activity, LocationActivity.class);
        intent.putExtra("conversation_id",ywConversation.getConversationId());
        activity.startActivity(intent);
    }

    /*%%%%%%%%%%%%%%%%%%%%%%%%%%% 以上是自定义回复栏的操作区 %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%*/



    /**
     * 单聊ui界面，点击url的事件拦截 返回true;表示自定义处理，返回false，由默认处理
     *
     * @param fragment 可以通过 fragment.getActivity拿到Context
     * @param message  点击的url所属的message
     * @param url      点击的url
     */
    @Override
    public boolean onUrlClick(Fragment fragment, YWMessage message, String url,
                              YWConversation conversation) {
        if(!url.startsWith("http")) {
            url = "http://" + url;
        }
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse(url));
        fragment.startActivity(intent);
        return true;
    }

    /**
     * 是否显示默认的Item，照片，相册
     */
    @Override
    public boolean showDefaultBarItems(YWConversation conversation){
        return true;//显示
    }


    /**
     * 消息点击事件
     * @param fragment  聊天窗口fragment对象
     * @param message   被点击的消息
     * @return true:使用用户自定义的消息点击事件，false：使用默认的消息点击事件
     */
    @Override
    public boolean onMessageClick(Fragment fragment, YWMessage message) {
        switch (getCustomViewType(message)){
            case type_0:
                openGeoView(fragment.getActivity(),message);
                return true;
            case type_1:
                showContactProfile(fragment.getActivity(),message);
                return true;
            case type_2:
                openFile(fragment.getActivity(),message);
                return true;
            default:
                return super.onMessageClick(fragment, message);
        }
    }

    private void openFile(FragmentActivity activity, YWMessage message) {
        String msgContent = message.getMessageBody().getContent();
        Gson gson = new Gson();
        CustomMsg cMsg = gson.fromJson(msgContent,CustomMsg.class);
        File file = new File(cMsg.arg2);
        if (file.exists()){//说明是自己发送的文件
            if(message.getAuthorUserId().equals(
                    IMKitHelper.getInstance().getIMKit().getIMCore().getLoginUserId())){
                QbSdk.openFileReader(activity, cMsg.arg2, null, new ValueCallback<String>() {
                    @Override
                    public void onReceiveValue(String s) {

                    }
                });
            }
        }else{
            Intent intent = new Intent(activity, WebActivity.class);


        }

    }

    /**
     * 用户个人信息
     */
    private void showContactProfile(FragmentActivity mContext, YWMessage message) {
        Intent intent = new Intent(mContext,TribeMemberProfileActivity.class);
        String msgContent = message.getMessageBody().getContent();
        Gson gson = new Gson();
        CustomMsg cMsg = gson.fromJson(msgContent,CustomMsg.class);
        String userID = cMsg.arg0;
        String nick = cMsg.arg1;
        intent.putExtra("targetID",userID);
        intent.putExtra("targetNick",nick);
        mContext.startActivity(intent);
    }

    /**
     * 打开地图消息
     */
    private void openGeoView(FragmentActivity activity, YWMessage message) {
        YWGeoMessageBody body = (YWGeoMessageBody) message.getMessageBody();
        Intent intent=new Intent(activity, OpenGeoMessageActivity.class);
        intent.putExtra("latitude",  body.getLatitude());
        intent.putExtra("longitude", body.getLongitude());
        intent.putExtra("address",body.getAddress());
        activity.startActivity(intent);

    }

    /**
     * 长按消息事件
     * @param fragment  聊天窗口fragment对象
     * @param message   被点击的消息
     * @return true:自定义，false：使用
     */
    @Override
    public boolean onMessageLongClick(Fragment fragment, final YWMessage message) {
        final Activity context=fragment.getActivity();
        if (message != null) {
            List<String> linkedList = new ArrayList<>();
            if (message.getSubType() == YWMessage.SUB_MSG_TYPE.IM_TEXT
                    || message.getSubType() == YWMessage.SUB_MSG_TYPE.IM_GEO)
                linkedList.add(context.getString(R.string.str_forward));
            linkedList.add(context.getString(R.string.str_msg_del));
            if (message.getSubType() == YWMessage.SUB_MSG_TYPE.IM_TEXT)
                linkedList.add(context.getString(R.string.str_copy));
            if ((message.getSubType() == YWMessage.SUB_MSG_TYPE.IM_IMAGE ||
                    message.getSubType() == YWMessage.SUB_MSG_TYPE.IM_GIF)) {
                String content = message.getMessageBody().getContent();
                if (!TextUtils.isEmpty(content)&&content.startsWith("http"))
                    linkedList.add(0,context.getString(R.string.str_forward));
            }
            if(message.getSubType() == YWMessage.SUB_MSG_TYPE.IM_TEXT
                    || message.getSubType() == YWMessage.SUB_MSG_TYPE.IM_IMAGE
                    )
                linkedList.add(context.getString(R.string.str_collect));

            final String[] items = new String[linkedList.size()];
            linkedList.toArray(items);
            final YWConversation conversation = IMKitHelper.getInstance().getIMKit().getConversationService()
                    .getConversationByConversationId(message.getConversationId());
            new WxAlertDialog.Builder(context)
                    .setItems(items, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            if(TextUtils.equals(context.getString(R.string.str_forward),items[which])){
                                ActivityCollector.getInstance().clearCollector();
                                Intent intent = new Intent(context, ForwardConversationActivity.class);
                                intent.putExtra("forward_msg",message);
                                context.startActivity(intent);
                            }else if(TextUtils.equals(context.getString(R.string.str_msg_del),items[which])){
                                if (conversation != null)
                                    conversation.getMessageLoader().deleteMessage(message);
                                else
                                    IMNotificationUtils.getInstance().showToast(context, "删除失败，请稍后重试");
                            }else if(TextUtils.equals(context.getString(R.string.str_copy),items[which])){
                                android.content.ClipboardManager c = (android.content.ClipboardManager) context.getSystemService(CLIPBOARD_SERVICE);
                                String content = message.getMessageBody().getContent();
                                if (!TextUtils.isEmpty(content)){
                                    c.setPrimaryClip(ClipData.newPlainText("chat", content ));
                                    IMNotificationUtils.getInstance().showToast(context, "成功复制到剪贴板");
                                }
                            }else if(TextUtils.equals(context.getString(R.string.str_collect),items[which])){
                                addToCollection(message, context);
                            }
                        }
                    }).setNegativeButton(context.getResources().getString(R.string.cancel),
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                        }
                    }).create().show();
        }
        return true;
    }

    private CallBackListener<String> listener = new CallBackListener<String>() {
        @Override
        public void onSuccess(String data) {

        }

        @Override
        public void onError(String errorMsg) {

        }
    };
    /**
     * 添加收藏
     * @param message 消息
     * @param context 环境
     */
    private void addToCollection(YWMessage message, final Context context) {
        String id = message.getAuthorUserId();
        IYWContact contact = IMKitHelper.getInstance().getIMKit()
                .getContactService().getContactProfileInfo(id,IMKitHelper.APP_KEY);
        switch (message.getSubType()){
            case YWMessage.SUB_MSG_TYPE.IM_TEXT://文本
                new CollectionHelper.ParamsBuilder()
                        .setSort(CollectionHelper.TYPE_TEXT)
                        .setAuthor(message.getAuthorUserName())
                        .setAvatarPath(contact.getAvatarPath())
                        .setContent(message.getContent())
                        .build(listener)
                        .sendRequest(context);
                break;
            case YWMessage.SUB_MSG_TYPE.IM_IMAGE:
                YWImageMessageBody imageMessage = (YWImageMessageBody) message.getMessageBody();
                String picUrl = imageMessage.getContent(YWEnum.ShowImageResolutionType.BIG_IMAGE);
                new CollectionHelper.ParamsBuilder()
                        .setSort(CollectionHelper.TYPE_IMAGE)
                        .setAuthor(message.getAuthorUserName())
                        .setAvatarPath(contact.getAvatarPath())
                        .setContent(picUrl)
                        .build(listener)
                        .sendRequest(context);
                break;
        }

    }


    /**
     * 是否使用听筒模式播放语音消息
     *
     * @return true：使用听筒模式， false：使用扬声器模式
     */
    @Override
    public boolean useInCallMode(Fragment fragment, YWMessage message) {
        return KVStoreHelper.getUseInCallMode();
    }

    /**
     * 数字字符串点击事件
     * @param clickString 被点击的数字string
     * @param widget 被点击的TextView
     * @return false:不处理  true:自实现
     */
    @Override
    public boolean onNumberClick(final Activity activity, final String clickString, final View widget) {
        String phoneNumber = clickString.replaceAll(" ","").replaceAll("-","");
        ArrayList<String> menuList = new ArrayList<>();
        if (phoneNumber.length() == 11){
            menuList.add("呼叫");
            menuList.add("添加到手机通讯录");
        }
        menuList.add("复制到剪贴板");
        final String[] items = new String[menuList.size()];
        menuList.toArray(items);
        Dialog alertDialog = new WxAlertDialog.Builder(activity)
                .setItems(items, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (TextUtils.equals(items[which], "呼叫")){
                            Intent intent = new Intent(Intent.ACTION_DIAL);
                            intent.setData(Uri.parse("tel:" + clickString));
                            activity.startActivity(intent);
                        }else if (TextUtils.equals(items[which], "添加到手机通讯录")) {
                            Intent intent = new Intent(Intent.ACTION_INSERT_OR_EDIT);
                            intent.setType("vnd.android.cursor.item/person");
                            intent.setType("vnd.android.cursor.item/contact");
                            intent.setType("vnd.android.cursor.item/raw_contact");
                            intent.putExtra(android.provider.ContactsContract.Intents.Insert.PHONE, clickString);
                            activity.startActivity(intent);
                        }else if (TextUtils.equals(items[which], "复制到剪贴板")) {
                            android.content.ClipboardManager c = (android.content.ClipboardManager) activity.getSystemService(CLIPBOARD_SERVICE);
                            c.setPrimaryClip(ClipData.newPlainText("chat",clickString));
                            IMNotificationUtils.getInstance().showToast(activity, "成功复制到剪贴板");
                        }
                    }
                }).create();
        alertDialog.setCanceledOnTouchOutside(true);
        alertDialog.setCancelable(true);
        alertDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                widget.invalidate();
            }
        });
        if (!alertDialog.isShowing()) {
            alertDialog.show();
        }
        return true;
    }

    /**
     * 双击放大文字消息的开关
     * @return true:开启双击放大文字 false: 关闭双击放大文字
     */
    @Override
    public boolean enableDoubleClickEnlargeMessageText(Fragment fragment) {
        return true;
    }

    /**
     * 如果自己实现拍照或者选择照片的流程，则可以在该方法中实现照片(图片)的发送操作
     * @return 在自己实现拍照处理或者选择照片时，一定要return true
     */
    public boolean onActivityResult(int requestCode, int resultCode, Intent data, List<YWMessage> messageList) {
        if (resultCode == RESULT_OK && requestCode == PhotoPicker.REQUEST_CODE) {
            if (data != null) {
                Context mContext = BaseApplication.getContext();
                int mWidthPixels = mContext.getResources().getDisplayMetrics().widthPixels;
                int mHeightPixels = (int) (mContext.getResources().getDisplayMetrics().heightPixels
                        - 32.0F * mContext.getResources().getDisplayMetrics().density);

                File dir = new File(StorageConstant.getFilePath());
                dir.mkdirs();
                ArrayList<String> photos = data.getStringArrayListExtra(PhotoPicker.KEY_SELECTED_PHOTOS);
                for (String originPicPath: photos) {
                    File picOriginFile = new File(originPicPath);
                    int fileSize = 0;
                    if ((picOriginFile.exists()) && (picOriginFile.isFile()))
                        fileSize = (int)picOriginFile.length();
                    if (fileSize == 0) continue;
                    YWMessage message;
                    message= handleGifPic(originPicPath,fileSize);
                    if (message != null){
                        messageList.add(message);
                        continue;
                    }
                    String savePath = StorageConstant.getFilePath() + File.separator + WXUtil.getMD5FileName(UUID.randomUUID().toString());
                    int ori = IMImageUtils.getOrientation(originPicPath, BaseApplication.getContext(), null);
                    Bitmap origin = IMThumbnailUtils.compressFileAndRotateToBitmapThumb(originPicPath, mWidthPixels, mHeightPixels, ori, savePath, false, false);
                    if (origin == null) continue;
                    String compressedPath = savePath + "_comp";
                    ImageMsgPacker imageMsgPacker = new ImageMsgPacker(YWChannel.getApplication());
                    int mMaxHeight = imageMsgPacker.getMaxNeedServerToGiveThumnailHeight();
                    int mMinWidth = imageMsgPacker.getMinWidth();
                    int[] resizedDimension = IMThumbnailUtils.getResizedDimensionOfThumbnail(origin.getWidth(), origin.getHeight(), mMinWidth, mMaxHeight);
                    Bitmap scalebBitmap = IMThumbnailUtils.getCropAndScaledBitmap(origin, resizedDimension[0], resizedDimension[1], resizedDimension[2], resizedDimension[3], false);
                    if (scalebBitmap == null) continue;
                    String imageType = "jpg";
                    imageType = getImageType(originPicPath, picOriginFile, imageType);
                    FileOutputStream out = null;
                    try {
                        out = new FileOutputStream(compressedPath);
                        if (out != null)
                            if (imageType.equals("jpg"))
                                scalebBitmap.compress(Bitmap.CompressFormat.JPEG, 90, out);
                            else if (imageType.equals("png"))
                                scalebBitmap.compress(Bitmap.CompressFormat.PNG, 90, out);
                    } catch (FileNotFoundException e){
                        WxLog.w("PicSendThread", "run", e);
                    } finally {
                        if (out != null) {
                            try {
                                out.close();
                            }catch (IOException e){
                                e.printStackTrace();
                            }
                        }
                    }
                    IMImageCache wxImageCache = IMImageCache.findOrCreateCache(mContext, StorageConstant.getFilePath());
                    scalebBitmap = IMImageUtils.getAndCacheChattingBitmap(compressedPath, scalebBitmap, false, 0.0f, this, wxImageCache, true);
                    Rect oriRect = new Rect();
                    oriRect.set(0, 0, origin.getWidth(), origin.getHeight());

                    Rect compRect = new Rect();
                    compRect.set(0, 0, scalebBitmap.getWidth(), scalebBitmap.getHeight());
                    savePicToSavePath(originPicPath, savePath, origin);
                    message = YWMessageChannel.createImageMessage(originPicPath, compressedPath, oriRect.width(), oriRect.height(), fileSize, imageType, YWEnum.SendImageResolutionType.BIG_IMAGE);
                    messageList.add(message);
                }
                return true;
            }
        }
        return false;
    }

    private void savePicToSavePath(String originPicPath, String savePath, Bitmap origin){
        if (origin != null)
            try {
                saveToSavePath(originPicPath, savePath, true, origin);
            } catch (IOException e) {
                e.printStackTrace();
                WxLog.e("PicSendThread" + UploadManager.TAG, e.getMessage());
            }
    }

    private void saveToSavePath(String originPath, String savePath,
                                boolean writeCompressedBitmapBackToFile, Bitmap bm) throws IOException{
        IMFileTools.deleteFile(savePath);
        if (writeCompressedBitmapBackToFile)
            IMFileTools.writeBitmap(savePath, bm, 90);
        else
            WXFileTools.copyFileFast(new File(originPath), new File(savePath));
    }


    private String getImageType(String originPicPath, File picOriginFile, String imageType) {
        if ((picOriginFile.isFile()) && (picOriginFile.exists())){
            if ((picOriginFile.exists()) && (picOriginFile.isFile())) {
                BitmapFactory.Options decodeOptions = new BitmapFactory.Options();
                decodeOptions.inJustDecodeBounds = true;
                BitmapFactory.decodeFile(originPicPath, decodeOptions);
                if ((!TextUtils.isEmpty(decodeOptions.outMimeType))
                        && ((decodeOptions.outMimeType.contains("png"))
                        || (decodeOptions.outMimeType.contains("PNG")))) {
                    imageType = "png";
                }
            }
        }
        return imageType;
    }

    private YWMessage handleGifPic(String originPicPath, int fileSize){
        if (originPicPath.endsWith(".gif")) {
            String originPath = StorageConstant.getFilePath() + File.separator + WXUtil.getMD5FileName(UUID.randomUUID().toString());
            FileUtils.copyFile(new File(originPicPath), new File(originPath));
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inJustDecodeBounds = true;
            BitmapFactory.decodeFile(originPicPath, options);
            return YWMessageChannel.createGifImageMessage(originPath, originPath, options.outWidth, options.outHeight, fileSize);
        }
        return null;
    }


    /** 自定义时间文案
     * @param fragment     聊天窗口fragment
     * @param conversation 当前聊天窗口对应的会话
     * @param time         默认时间文案
     * @return  如果是NULL，则不显示，如果是空字符串，则使用默认的文案，如果返回非空串，则使用自定义的
     */
    @Override
    public String getCustomTimeString(Fragment fragment, YWConversation conversation, String time) {
        return "";
    }

    /**
     * 自定义系统消息文案
     * @param fragment     聊天窗口fragment
     * @param conversation 当前聊天窗口对应的会话
     * @param content      默认系统消息文案
     * @return 如果是NULL，则不显示，如果是空字符串，则使用默认的文案，如果返回非空串，则使用自定义的
     */
    @Override
    public String getSystemMessageContent(Fragment fragment, YWConversation conversation, String content) {
        return "";
    }

    /**
     * 获取消息的显示在左边还是右边 0：按照默认规则 1:左边 2:右边
     */
    @Override
    public int getMessageShowAtLeftOrRight(Fragment fragment, YWMessage ywMessage, YWConversation conversation,String myUserId) {
        return 0;
    }

    @Override
    public boolean onSendButtonClick(Fragment pointcut, YWConversation ywConversation, String text) {
        Chatting_UICustom imChattingUI = (Chatting_UICustom) getIMChattingUI();
        return imChattingUI.isMyComputerConv();
    }
}
