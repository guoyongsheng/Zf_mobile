package com.zfsoftmh.common.utils;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.text.Editable;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextWatcher;
import android.text.style.ImageSpan;
import android.util.DisplayMetrics;
import android.widget.EditText;
import android.widget.Toast;

import com.zfsoftmh.common.config.Config;
import com.zfsoftmh.common.constant.Constant;
import com.zfsoftmh.entity.ImageCompressInfo;
import com.zfsoftmh.listener.CallBackListener;
import com.zfsoftmh.service.OfficeAffairsApi;
import com.zfsoftmh.ui.base.BaseApplication;
import com.zxy.tiny.core.FileKit;

import java.io.File;
import java.util.Stack;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;
import retrofit2.Response;

/**
 * 创建时间： 2017/5/9 0009
 * 编写人： 王世美
 * 功能描述：撤销与恢复撤销
 */

public class PerformEdit {

    private static final String TAG = "PerformEdit";

    // 操作序号(一次编辑可能对应多个操作，如替换文字，就是删除+插入)
    int index;
    // 撤销栈
    Stack<Action> history = new Stack<Action>();
    // 恢复栈
    Stack<Action> historyBack = new Stack<Action>();

    private Editable editable;
    private EditText editText;
    // 自动操作标志，防止重复回调,导致无限撤销
    private boolean flag = false;

    public static final String mBitmapTag = "☆"; // 用于分割图片
    public Context mContext;
    OfficeAffairsApi downLoadService;
    private ProgressDialog progressDialog;
    private String mFilePath;

    public PerformEdit(@NonNull EditText editText, Context mContext) {
        CheckNull(editText, "EditText不能为空");
        this.mContext = mContext;
        this.editable = editText.getText();
        this.editText = editText;

        this.downLoadService = BaseApplication.getAppComponent().getRetrofit().create(OfficeAffairsApi.class);
        progressDialog = new ProgressDialog(mContext);
        progressDialog.setTitle("提示信息");
        progressDialog.setMessage("正在加载图片，请稍候...");
        editText.addTextChangedListener(new Watcher());
    }

    protected void onEditableChanged(Editable s) {

    }

    protected void onTextChanged(Editable s) {

    }

    /**
     * 清理记录 Clear history.
     */
    public final void clearHistory() {
        history.clear();
        historyBack.clear();
    }

    /**
     * 撤销 Undo.
     */
    public final void undo() {
        if (history.empty()) {
            return;
        }
        // 锁定操作
        flag = true;
        Action action = history.pop();
        historyBack.push(action);
        if (action.isAdd) {
            // 撤销添加
            editable.delete(action.startCursor, action.startCursor
                    + action.actionTarget.length());
            editText.setSelection(action.startCursor, action.startCursor);
        } else {
            // 插销删除
            String str = String.valueOf(action.actionTarget);
            if (str.contains("<img") && str.contains("src=")) { // 如果是一张图片
                // 还原地址字符串
                String path = str.replace(mBitmapTag, "")
                        .replace("<img src=", "").replace("/>", "").replaceAll("\n","");
                DisplayMetrics dm = editText.getResources().getDisplayMetrics();
                int screen_width = dm.widthPixels;// 屏幕宽度（像素）
                int screen_height = dm.heightPixels;
                mFilePath = Config.CLOUD_NOTE_PATH + "repealCloudNotePhoto.png";
                displayPic(path, screen_width, screen_height, action.actionTarget, action.startCursor, action.endCursor); // 下载已删除图片
                editable.insert(action.startCursor, action.actionTarget);
//                editText.setText(editable);

            } else {
                editable.insert(action.startCursor, action.actionTarget);

            }
            if (action.endCursor == action.startCursor) {
                editText.setSelection(action.startCursor
                        + action.actionTarget.length());
            } else {
                editText.setSelection(action.startCursor, action.endCursor);
            }

        }
        // 释放操作
        flag = false;
        // 判断是否是下一个动作是否和本动作是同一个操作，直到不同为止
        if (!history.empty() && history.peek().index == action.index) {
            undo();
        }
    }

    /**
     * 删除本地撤销图片
     */
    public void deleteCameraPic(String mFilePath) {
        File myCaptureFile = new File(mFilePath);
        if (myCaptureFile.exists()) {
            myCaptureFile.delete();
        }
        // 删除压缩文件
        File file2 = FileKit.getDefaultFileCompressDirectory();
        FileUtils.deleteDirWithFile(file2);
    }

    /**
     * 下载已删除图片
     *
     * @param path1
     * @param screen_width
     * @param actionTarget
     */
    public void displayPic(final String path1, final int screen_width,
                           final int screen_height, final CharSequence actionTarget,
                           final int startCursor, final int endCursor) {

        progressDialog.show();
        downLoadService.downLoadFile(path1)
                .subscribeOn(Schedulers.io())
                .map(new Function<Response<ResponseBody>, Response<ResponseBody>>() {
                    @Override
                    public Response<ResponseBody> apply(@io.reactivex.annotations.NonNull Response<ResponseBody> response) throws Exception {

                        return response;
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Response<ResponseBody>>() {
                    @Override
                    public void accept(@io.reactivex.annotations.NonNull Response<ResponseBody> response) throws Exception {
                        if (response != null && response.isSuccessful()) {
                            boolean writtenToDisk = CloudNoteUtils.writeResponseBodyToDisk(response.body(), mFilePath);
                            if (writtenToDisk) {
                                File file = new File(mFilePath);
                                if (file != null) {
                                    ImageCompressUtils.compressToFile(mContext, file, new CallBackListener<ImageCompressInfo>() {
                                        @Override
                                        public void onSuccess(ImageCompressInfo data) {
                                            if (data != null) {
                                                if (data.bitmap != null) {
                                                    if (data.outfile != null && !data.outfile.equals("")) {
                                                        @SuppressWarnings("deprecation")
                                                        Drawable d = CloudNoteUtils.getDrawable(screen_width, screen_height, data.bitmap);
                                                        // 根据Bitmap对象创建ImageSpan对象
                                                        ImageSpan imSpan = new ImageSpan(d);
                                                        SpannableString spannableString = new SpannableString(
                                                                actionTarget);
                                                        spannableString.setSpan(imSpan, 0,
                                                                spannableString.length(),
                                                                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
//                                                        editable.insert(startCursor, spannableString);
//                                                        // 把et添加到Edittext中
//                                                        editText.setText(editable);
//                                                        if (endCursor == startCursor) {
//                                                            editText.setSelection(startCursor
//                                                                    + actionTarget.length());
//                                                        } else {
//                                                            editText.setSelection(startCursor, endCursor);
//                                                        }
                                                        deleteCameraPic(mFilePath);
                                                    }
                                                }
                                            }
                                        }

                                        @Override
                                        public void onError(String errorMsg) {
                                            Toast.makeText(mContext, Constant.image_load_failure_delete,
                                                    Toast.LENGTH_LONG).show();
                                        }
                                    });


                                } else {
                                    Toast.makeText(mContext, Constant.image_load_failure_delete,
                                            Toast.LENGTH_LONG).show();
                                }
                            }
                        } else {
                            Toast.makeText(mContext, Constant.image_load_failure_delete,
                                    Toast.LENGTH_LONG).show();
                        }
                        progressDialog.dismiss();


                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(@io.reactivex.annotations.NonNull Throwable throwable) throws Exception {
                        LoggerHelper.e(TAG, " displayPic " + throwable.getMessage());
                        progressDialog.dismiss();
                    }
                });
    }

    /**
     * 恢复 Redo.
     */
    public final void redo() {
        if (historyBack.empty()) {
            return;
        }
        flag = true;
        Action action = historyBack.pop();
        history.push(action);
        if (action.isAdd) {
            // 恢复添加
            editable.insert(action.startCursor, action.actionTarget);
            if (action.endCursor == action.startCursor) {
                editText.setSelection(action.startCursor
                        + action.actionTarget.length());
            } else {
                editText.setSelection(action.startCursor, action.endCursor);
            }
        } else {
            // 恢复删除
            editable.delete(action.startCursor, action.startCursor
                    + action.actionTarget.length());
            editText.setSelection(action.startCursor, action.startCursor);
        }
        flag = false;
        // 判断是否是下一个动作是否和本动作是同一个操作
        if (!historyBack.empty() && historyBack.peek().index == action.index) {
            redo();
        }
    }

    /**
     * 首次设置文本 Set default text.
     */
    public final void setDefaultText(CharSequence text) {
        clearHistory();
        flag = true;
        editable.replace(0, editable.length(), text);
        flag = false;
    }

    private class Watcher implements TextWatcher {

        /**
         * Before text changed.
         *
         * @param s     the s
         * @param start the start 起始光标
         * @param count the endCursor 选择数量
         * @param after the after 替换增加的文字数
         */
        @Override
        public final void beforeTextChanged(CharSequence s, int start,
                                            int count, int after) {
            if (flag) {
                return;
            }
            int end = start + count;
            if (end > start && end <= s.length()) {
                CharSequence charSequence = s.subSequence(start, end);
                // 删除了文字
                if (charSequence.length() > 0) {
                    Action action = new Action(charSequence, start, false);
                    if (count > 1) {
                        // 如果一次超过一个字符，说名用户选择了，然后替换或者删除操作
                        action.setSelectCount(count);
                    } else if (count == 1 && count == after) {
                        // 一个字符替换
                        action.setSelectCount(count);
                    }
                    // 还有一种情况:选择一个字符,然后删除(暂时没有考虑这种情况)

                    history.push(action);
                    historyBack.clear();
                    action.setIndex(++index);
                }
            }
        }

        /**
         * On text changed.
         *
         * @param s      the s
         * @param start  the start 起始光标
         * @param before the before 选择数量
         * @param count  the endCursor 添加的数量
         */
        @Override
        public final void onTextChanged(CharSequence s, int start, int before,
                                        int count) {
            if (flag) {
                return;
            }
            int end = start + count;
            if (end > start) {
                CharSequence charSequence = s.subSequence(start, end);
                // 添加文字
                if (charSequence.length() > 0) {
                    Action action = new Action(charSequence, start, true);

                    history.push(action);
                    historyBack.clear();
                    if (before > 0) {
                        // 文字替换（先删除再增加），删除和增加是同一个操作，所以不需要增加序号
                        action.setIndex(index);
                    } else {
                        action.setIndex(++index);
                    }
                }
            }
        }

        @Override
        public final void afterTextChanged(Editable s) {
            if (flag) {
                return;
            }
            if (s != editable) {
                editable = s;
                onEditableChanged(s);
            }
            PerformEdit.this.onTextChanged(s);
        }

    }

    private class Action {
        /**
         * 改变字符.
         */
        CharSequence actionTarget;
        /**
         * 光标位置.
         */
        int startCursor;
        int endCursor;
        /**
         * 标志增加操作.
         */
        boolean isAdd;
        /**
         * 操作序号.
         */
        int index;

        public Action(CharSequence actionTag, int startCursor, boolean add) {
            this.actionTarget = actionTag;
            this.startCursor = startCursor;
            this.endCursor = startCursor;
            this.isAdd = add;
        }

        public void setSelectCount(int count) {
            this.endCursor = endCursor + count;
        }

        public void setIndex(int index) {
            this.index = index;
        }
    }

    private static void CheckNull(Object o, String message) {
        if (o == null) {
            try {
                throw new IllegalStateException(message);
            } catch (Exception e) {
                LoggerHelper.e(TAG, " CheckNull " + e.getMessage());
            }
        }
    }
}
