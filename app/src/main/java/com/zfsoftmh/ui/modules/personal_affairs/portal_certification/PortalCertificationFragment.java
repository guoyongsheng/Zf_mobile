package com.zfsoftmh.ui.modules.personal_affairs.portal_certification;

import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.Button;

import com.zfsoftmh.R;
import com.zfsoftmh.ui.base.BaseFragment;
import com.zfsoftmh.ui.modules.web.MD5Util_OA;

/**
 * @author wesley
 * @date: 2017-7-18
 * @Description: ui
 */

public class PortalCertificationFragment extends BaseFragment<PortalCertificationPresenter>
        implements PortalCertificationContract.View, View.OnClickListener {

    private String id;
    private String url;

    private Button btn_portal_certification;

    @Override
    protected void initVariables() {

    }

    @Override
    protected void handleBundle(Bundle bundle) {
        id = bundle.getString("id");
        url = bundle.getString("url");
    }

    @Override
    protected int getLayoutResID() {
        return R.layout.fragment_portal_certification;
    }

    @Override
    protected void initViews(View view) {
        btn_portal_certification = (Button) view.findViewById(R.id.portal_certification);
    }

    @Override
    protected void initListener() {
        btn_portal_certification.setOnClickListener(this);
    }

    public static PortalCertificationFragment newInstance(String id, String url) {
        Bundle bundle = new Bundle();
        bundle.putString("id", id);
        bundle.putString("url", url);
        PortalCertificationFragment fragment = new PortalCertificationFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onClick(View view) {
        String scriptid = id;
        String user = getUserId();
        long time = System.currentTimeMillis();
        String verify = scriptid + "@_@" + user + "@_@" + time + "zfca";
        String upper_case_verify = MD5Util_OA.Encrypt(verify).toUpperCase();
        upper_case_verify = scriptid + "@_@" + user + "@_@" + time + "@_@" + upper_case_verify;
        String token = new String(Base64.encode(upper_case_verify.getBytes(), 0));
        String url_portal = url + "?token=" + token;
        presenter.portal_certification(url_portal);
        ((PortalCertificationActivity) context).finish();
    }
}
