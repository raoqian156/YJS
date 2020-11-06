package com.yskrq.common.ui;

import android.app.Activity;
import android.content.Intent;
import android.view.View;

import com.rq.rvlibrary.BaseAdapter;
import com.rq.rvlibrary.BaseViewHolder;
import com.rq.rvlibrary.OnItemClickListener;
import com.rq.rvlibrary.RecyclerUtil;
import com.yskrq.common.AppInfo;
import com.yskrq.common.BaseActivity;
import com.yskrq.common.R;
import com.yskrq.common.bean.ProfitCenterBean;

import java.util.ArrayList;
import java.util.List;

import androidx.recyclerview.widget.RecyclerView;


public class TableWindowActivity extends BaseActivity implements View.OnClickListener,
                                                                 OnItemClickListener {

    public static final int RESULT_COMPLETE_SELECT = 0x00101;
    BaseAdapter mAdapter;

    public static void start(Activity activity,
                             List<ProfitCenterBean.ProfitCenterValueBean> beanList) {
        Intent intent = new Intent(activity, TableWindowActivity.class);
        ArrayList<ProfitCenterBean.ProfitCenterValueBean> list = new ArrayList<>();
        list.addAll(beanList);
        intent.putExtra("list.data", list);
        activity.startActivityForResult(intent, 111);
    }

    @Override
    protected int layoutId() {
        return R.layout.login_pop_table;
    }

    @Override
    protected void initView() {
        findViewById(R.id.tv_title).requestFocus(1000);
        findViewById(R.id.btn_cancel).setOnClickListener(this);
        mAdapter = new BaseAdapter(this, R.layout.login_item_table_content, ViewHolder.class, this);
        List<ProfitCenterBean.ProfitCenterValueBean> beanList = (List<ProfitCenterBean.ProfitCenterValueBean>) getIntent()
                .getSerializableExtra("list.data");
        if (beanList == null) {
            toast("数据获取异常,请稍后重试.");
            finish();
            return;
        }
        mAdapter.setPassData(-1);
        mAdapter.setData(beanList);
        mAdapter.addOnItemClickListener(this);
        new RecyclerUtil(mAdapter).row(Math.min(beanList.size(), 4))
                                  .set2View((RecyclerView) findViewById(R.id.recycler));
    }


    ProfitCenterBean.ProfitCenterValueBean select;

    @Override
    public void onItemClick(BaseViewHolder holder, Object o, View view, int position) {
        select = (ProfitCenterBean.ProfitCenterValueBean) o;
        mAdapter.setPassData(position);
        mAdapter.notifyDataSetChanged();
        AppInfo.setProfitCenter(this, select.getCode());
        setResult(RESULT_COMPLETE_SELECT);
        finish();
    }

    public class ViewHolder extends BaseViewHolder<ProfitCenterBean.ProfitCenterValueBean> {
        public ViewHolder(View itemView) {
            super(itemView);
        }

        @Override
        public int inflateLayoutId() {
            return R.layout.login_item_table_content;
        }

        @Override
        public void fillData(int position, ProfitCenterBean.ProfitCenterValueBean o) {
            super.fillData(position, o);
            setItemText(R.id.tv_name, o.getDescription());
            itemView.setSelected(position == (int) getPassNullable(0));
        }
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btn_cancel) {
            finish();
        }
    }


}
