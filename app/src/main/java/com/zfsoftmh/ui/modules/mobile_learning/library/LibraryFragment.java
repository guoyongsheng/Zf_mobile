package com.zfsoftmh.ui.modules.mobile_learning.library;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.zfsoftmh.R;
import com.zfsoftmh.entity.LibraryInfo;
import com.zfsoftmh.ui.base.BaseFragment;
import com.zfsoftmh.ui.modules.mobile_learning.library.arrears_information.ArrearsInformationActivity;
import com.zfsoftmh.ui.modules.mobile_learning.library.booking_information.BookingInformationActivity;
import com.zfsoftmh.ui.modules.mobile_learning.library.borrow_information.BorrowInformationActivity;
import com.zfsoftmh.ui.modules.mobile_learning.library.reader_information.ReaderInformationActivity;

import java.util.ArrayList;
import java.util.List;


/**
 * @author wesley
 * @date: 2017-6-21
 * @Description: 图书馆ui
 */

public class LibraryFragment extends BaseFragment implements LibraryContract.View,
        LibraryAdapter.OnItemClickListener {

    @Override
    public void setPresenter(Object presenter) {

    }

    @Override
    protected void initVariables() {

    }

    @Override
    protected void handleBundle(Bundle bundle) {

    }

    @Override
    protected int getLayoutResID() {
        setHasOptionsMenu(true);
        return R.layout.common_recycler_view;
    }

    @Override
    protected void initViews(View view) {

        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.common_recycler_view);
        recyclerView.setBackgroundResource(R.mipmap.ic_icon_library_bg);

        //LayoutManager
        GridLayoutManager layoutManager = new GridLayoutManager(context, 2);
        recyclerView.setLayoutManager(layoutManager);

        //adapter
        LibraryAdapter adapter = new LibraryAdapter(context);
        adapter.setOnItemClickListener(this);
        recyclerView.setAdapter(adapter);

        adapter.setDataSource(getDataSource());
    }

    @Override
    protected void initListener() {

    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_library, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();
        switch (itemId) {
        /*
         * 读者信息
         */
        case R.id.menu_library_personal_info:
            Intent intent = new Intent(context, ReaderInformationActivity.class);
            openActivity(intent);
            return true;

        default:
            break;
        }
        return super.onOptionsItemSelected(item);
    }

    public static LibraryFragment newInstance() {
        return new LibraryFragment();
    }

    @Override
    public List<LibraryInfo> getDataSource() {

        List<LibraryInfo> list = new ArrayList<>();

        //借书信息
        LibraryInfo libraryInfo_information = new LibraryInfo();
        libraryInfo_information.setResId(R.mipmap.ic_icon_library_information);
        libraryInfo_information.setName(getResources().getString(R.string.library_information));
        list.add(libraryInfo_information);

        //馆藏查询
        LibraryInfo libraryInfo_collection_inquiries = new LibraryInfo();
        libraryInfo_collection_inquiries.setResId(R.mipmap.ic_icon_collection_inquiries);
        libraryInfo_collection_inquiries.setName(getResources().getString(R.string.collection_inquiries));
        list.add(libraryInfo_collection_inquiries);

        //欠费查询
        LibraryInfo libraryInfo_arrears = new LibraryInfo();
        libraryInfo_arrears.setResId(R.mipmap.ic_icon_arrears);
        libraryInfo_arrears.setName(getResources().getString(R.string.arrears));
        list.add(libraryInfo_arrears);

        //预约查询
        LibraryInfo libraryInfo_make_an_appointment = new LibraryInfo();
        libraryInfo_make_an_appointment.setResId(R.mipmap.ic_icon_make_an_appointment);
        libraryInfo_make_an_appointment.setName(getResources().getString(R.string.make_an_appointment));
        list.add(libraryInfo_make_an_appointment);

        return list;
    }

    @Override
    public void onItemClick(int position) {

        switch (position) {
        /*
         * 借书信息
         */
        case 0:
            Intent intent_borrow = new Intent(context, BorrowInformationActivity.class);
            openActivity(intent_borrow);
            break;

        /*
         *  馆藏查询
         */
        case 1:
            break;

        /*
         * 欠费查询
         */
        case 2:
            Intent intent_arrears = new Intent(context, ArrearsInformationActivity.class);
            openActivity(intent_arrears);
            break;

        /*
         * 预约查询
         */
        case 3:
            Intent intent_booking = new Intent(context, BookingInformationActivity.class);
            openActivity(intent_booking);
            break;

        default:
            break;
        }
    }
}
