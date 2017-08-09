package com.zfsoftmh.ui.modules.chatting.helper.view.file_explorer;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.ListPopupWindow;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.zfsoftmh.R;
import com.zfsoftmh.entity.FileInfo;
import com.zfsoftmh.ui.base.BaseFragment;
import com.zfsoftmh.ui.widget.OnceClickListener;
import com.zfsoftmh.ui.widget.photopicker.PhotoPreview;
import com.zfsoftmh.ui.widget.photopicker.adapter.PhotoGridAdapter;
import com.zfsoftmh.ui.widget.photopicker.adapter.PopupDirectoryListAdapter;
import com.zfsoftmh.ui.widget.photopicker.entity.Photo;
import com.zfsoftmh.ui.widget.photopicker.entity.PhotoDirectory;
import com.zfsoftmh.ui.widget.photopicker.event.OnItemCheckListener;
import com.zfsoftmh.ui.widget.photopicker.event.OnPhotoClickListener;
import com.zfsoftmh.ui.widget.photopicker.utils.AndroidLifecycleUtils;
import com.zfsoftmh.ui.widget.photopicker.utils.ImageCaptureManager;
import com.zfsoftmh.ui.widget.photopicker.utils.MediaStoreHelper;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by sy
 * on 2017/7/7.
 * <p>图片文件</p>
 */

public class PicFileFragment extends BaseFragment {

    public static PicFileFragment newInstance() {
        return new PicFileFragment();
    }

    private ListPopupWindow listPopupWindow;
    private RequestManager mGlideRequestManager;

    private PhotoGridAdapter photoGridAdapter;
    private List<PhotoDirectory> directories;
    private PopupDirectoryListAdapter listAdapter;
    private ImageCaptureManager captureManager;

    @Override
    protected void initVariables() {
        setRetainInstance(true);
        mGlideRequestManager = Glide.with(this);

        directories = new ArrayList<>();
        photoGridAdapter = new PhotoGridAdapter(context, mGlideRequestManager, directories, null, 3);
        photoGridAdapter.setShowCamera(false);
        photoGridAdapter.setPreviewEnable(true);

        listAdapter = new PopupDirectoryListAdapter(mGlideRequestManager, directories);
        Bundle mediaStoreArgs = new Bundle();
        mediaStoreArgs.putBoolean("SHOW_GIF", false);
        MediaStoreHelper.getPhotoDirs(getActivity(), mediaStoreArgs,
                new MediaStoreHelper.PhotosResultCallback() {
                    @Override
                    public void onResultCallback(List<PhotoDirectory> dirs) {
                        directories.clear();
                        directories.addAll(dirs);
                        photoGridAdapter.notifyDataSetChanged();
                        listAdapter.notifyDataSetChanged();
                        adjustHeight();
                    }
                });

        captureManager = new ImageCaptureManager(context);
    }

    public void adjustHeight() {
        if (listAdapter == null) return;
        int count = listAdapter.getCount();
        count = count < 4 ? count : 4;
        if (listPopupWindow != null)
            listPopupWindow.setHeight(count * getResources().getDimensionPixelOffset(R.dimen.__picker_item_directory_height));
    }

    @Override
    protected void handleBundle(Bundle bundle) { }

    @Override
    protected int getLayoutResID() {
        return R.layout.__picker_fragment_photo_picker;
    }

    private Button btSwitchDirectory;
    private RecyclerView recyclerView;
    private TextView tvDone;

    @Override
    protected void initViews(View rootView) {
        recyclerView = (RecyclerView) rootView.findViewById(R.id.rv_photos);
        StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(3, OrientationHelper.VERTICAL);
        layoutManager.setGapStrategy(StaggeredGridLayoutManager.GAP_HANDLING_MOVE_ITEMS_BETWEEN_SPANS);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(photoGridAdapter);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        tvDone = (TextView) rootView.findViewById(R.id.str_done);
        tvDone.setVisibility(View.VISIBLE);
        tvDone.setText(getString(R.string.__picker_done_with_count,0,1));
        btSwitchDirectory = (Button) rootView.findViewById(R.id.button);
        listPopupWindow = new ListPopupWindow(context);
        listPopupWindow.setWidth(ListPopupWindow.MATCH_PARENT);
        listPopupWindow.setAnchorView(btSwitchDirectory);
        listPopupWindow.setAdapter(listAdapter);
        listPopupWindow.setModal(true);
        listPopupWindow.setDropDownGravity(Gravity.BOTTOM);
        tvDone.setOnClickListener(new OnceClickListener() {
            @Override
            public void onOnceClick(View v) {
                List<String> photos = photoGridAdapter.getSelectedPhotos();
                if (photos.size() == 0) return;
                Log.e("123",photos.get(0));
                fileSender.sendFile(4,new FileInfo("",photos.get(0),0));
            }
        });
    }

    @Override
    protected void initListener() {
        listPopupWindow.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                listPopupWindow.dismiss();
                PhotoDirectory directory = directories.get(position);
                btSwitchDirectory.setText(directory.getName());
                photoGridAdapter.setCurrentDirectoryIndex(position);
                photoGridAdapter.notifyDataSetChanged();
            }
        });

        photoGridAdapter.setOnPhotoClickListener(new OnPhotoClickListener() {
            @Override
            public void onClick(View v, int position, boolean showCamera) {
                final int index = showCamera ? position - 1 : position;
                ArrayList<String> photos = photoGridAdapter.getCurrentPhotoPaths();
                int[] screenLocation = new int[2];
                v.getLocationOnScreen(screenLocation);
                new PhotoPreview.PhotoPreviewBuilder()
                        .setShowDeleteButton(false)
                        .setCurrentItem(index)
                        .setPhotos(photos)
                        .start(getActivity());
            }
        });

        photoGridAdapter.setOnItemCheckListener(new OnItemCheckListener() {
            @Override
            public boolean onItemCheck(int position, Photo photo, int selectedItemCount) {
                List<String> photos = photoGridAdapter.getSelectedPhotos();
                if (!photos.contains(photo.getPath())) {
                    photos.clear();
                    photoGridAdapter.notifyDataSetChanged();
                }
                tvDone.setText(getString(R.string.__picker_done_with_count,
                        selectedItemCount > 1 ? 1 : selectedItemCount,
                        1));
                return true;
            }
        });

        btSwitchDirectory.setOnClickListener(new OnceClickListener() {
            @Override
            public void onOnceClick(View v) {
                if (listPopupWindow.isShowing()) {
                    listPopupWindow.dismiss();
                } else if (!getActivity().isFinishing()) {
                    adjustHeight();
                    listPopupWindow.show();
                }
            }
        });

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (Math.abs(dy) > 30) mGlideRequestManager.pauseRequests();
                else resumeRequestsIfNotDestroyed();

            }

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                if (newState == RecyclerView.SCROLL_STATE_IDLE)
                    resumeRequestsIfNotDestroyed();
            }
        });
    }

    private void resumeRequestsIfNotDestroyed() {
        if (!AndroidLifecycleUtils.canLoadImage(this))
            return;
        mGlideRequestManager.resumeRequests();
    }

    FileSender fileSender;
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        fileSender = (FileSender) context;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (directories == null)
            return;
        for (PhotoDirectory directory : directories) {
            directory.getPhotoPaths().clear();
            directory.getPhotos().clear();
            directory.setPhotos(null);
        }
        directories.clear();
        directories = null;
    }

    @Override public void onSaveInstanceState(Bundle outState) {
        captureManager.onSaveInstanceState(outState);
        super.onSaveInstanceState(outState);
    }

    @Override public void onViewStateRestored(Bundle savedInstanceState) {
        captureManager.onRestoreInstanceState(savedInstanceState);
        super.onViewStateRestored(savedInstanceState);
    }
}
