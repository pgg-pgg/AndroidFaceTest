package pgg.androidfacetest.ActivityBase.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import pgg.androidfacetest.ActivityBase.base.BaseFragment;
import pgg.androidfacetest.ActivityBase.view.HorizontalEx;
import pgg.androidfacetest.ActivityBase.view.HorizontalEx2;

/**
 * Created by PDD on 2018/3/18.
 */

public class ScrollConflictFragment extends BaseFragment {

    private static final String TAG = "ScrollConflictFragment";

    public static final String SHOW_KEY = "show_key";

    public static final int OUT_HV = 1;
    public static final int OUT_VV = 2;
    public static final int INNER_HV = 3;
    public static final int INNER_VV = 4;
    private int code;
    private HorizontalEx mHorizontalEx;
    private HorizontalEx2 mHorizontalEx2;

    public static ScrollConflictFragment newInstance(int code){
        Bundle bundle=new Bundle();
        bundle.putInt(SHOW_KEY,code);
        ScrollConflictFragment fragment=new ScrollConflictFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        code = getArguments().getInt(SHOW_KEY);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = null;
        List<String> data1 = new ArrayList<>();
        List<String> data2 = new ArrayList<>();
        List<String> data3 = new ArrayList<>();
        for (int i = 0; i < 40; i++) {
            String str1 = "列表1——" + i;
            String str2 = "列表2__" + i;
            String str3 = "列表3__" + i;
            data1.add(str1);
            data2.add(str2);
            data3.add(str3);
        }

        switch (code){
            case OUT_HV:
                mHorizontalEx = new HorizontalEx(getContext());
                root = mHorizontalEx;
                showOutHVData(data1,data2,data3);
                break;
            case OUT_VV:

                break;
            case INNER_HV:
                mHorizontalEx2=new HorizontalEx2(getContext());
                root=mHorizontalEx2;
                showOutHV2Data(data1,data2,data3);
                break;
            case INNER_VV:

                break;
        }

        return root;
    }

    private void showOutHV2Data(List<String> data1, List<String> data2, List<String> data3) {
        if (code != INNER_HV) return;
        ListView listView1 = new ListView(getContext());
        ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(getContext(),
                android.R.layout.simple_list_item_1, data1);
        listView1.setAdapter(adapter1);

        ListView listView2 = new ListView(getContext());
        ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(getContext(),
                android.R.layout.simple_list_item_1, data2);
        listView2.setAdapter(adapter2);

        ListView listView3 = new ListView(getContext());
        ArrayAdapter<String> adapter3 = new ArrayAdapter<String>(getContext(),
                android.R.layout.simple_list_item_1, data3);
        listView3.setAdapter(adapter3);

        ViewGroup.LayoutParams params
                = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT);

        mHorizontalEx2.addView(listView1, params);
        mHorizontalEx2.addView(listView2, params);
        mHorizontalEx2.addView(listView3, params);
    }

    private void showOutHVData(List<String> data1, List<String> data2, List<String> data3) {
        if (code != OUT_HV) return;
        ListView listView1 = new ListView(getContext());
        ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(getContext(),
                android.R.layout.simple_list_item_1, data1);
        listView1.setAdapter(adapter1);

        ListView listView2 = new ListView(getContext());
        ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(getContext(),
                android.R.layout.simple_list_item_1, data2);
        listView2.setAdapter(adapter2);

        ListView listView3 = new ListView(getContext());
        ArrayAdapter<String> adapter3 = new ArrayAdapter<String>(getContext(),
                android.R.layout.simple_list_item_1, data3);
        listView3.setAdapter(adapter3);

        ViewGroup.LayoutParams params
                = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT);

        mHorizontalEx.addView(listView1, params);
        mHorizontalEx.addView(listView2, params);
        mHorizontalEx.addView(listView3, params);
    }
}
