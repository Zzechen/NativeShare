package com.zzc.nativeshare;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private AppInfoAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.btn_all).setOnClickListener(this);
        ListView lv = findViewById(R.id.lv_apps);
        mAdapter = new AppInfoAdapter(this);
        lv.setAdapter(mAdapter);
    }

    @Override
    public void onClick(View v) {
        new Thread(() -> {
            List<AppInfoVo> shareApps = getShareApps(this);
            runOnUiThread(() -> {
                mAdapter.addAll(shareApps);
            });
        }).start();
    }

    public List<AppInfoVo> getShareApps(Context context) {
        PackageManager packageManager = context.getPackageManager();
        List<AppInfoVo> appInfoVos = new ArrayList<>();
        List<ResolveInfo> resolveInfos;
        Intent intent = new Intent(Intent.ACTION_SEND, null);
        intent.addCategory(Intent.CATEGORY_DEFAULT);
        intent.setType("*/*");
        PackageManager pManager = context.getPackageManager();
        resolveInfos = pManager.queryIntentActivities(intent, PackageManager
                .COMPONENT_ENABLED_STATE_DEFAULT);
        for (int i = 0; i < resolveInfos.size(); i++) {
            AppInfoVo appInfoVo = new AppInfoVo();
            ResolveInfo resolveInfo = resolveInfos.get(i);
            appInfoVo.setAppName(resolveInfo.loadLabel(packageManager).toString());
            appInfoVo.setIcon(resolveInfo.loadIcon(packageManager));
            appInfoVo.setPackageName(resolveInfo.activityInfo.packageName);
            appInfoVo.setLauncherName(resolveInfo.activityInfo.name);
            appInfoVos.add(appInfoVo);
        }
        return appInfoVos;
    }
}
