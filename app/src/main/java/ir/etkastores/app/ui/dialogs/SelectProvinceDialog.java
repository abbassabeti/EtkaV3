package ir.etkastores.app.ui.dialogs;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.AppCompatSpinner;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import ir.etkastores.app.R;
import ir.etkastores.app.utils.DiskDataHelper;

public class SelectProvinceDialog extends BaseDialog {

    private final static String LAST_SELECTED_PROVINCE_IN_HEKMAT = "LAST_SELECTED_PROVINCE_IN_HEKMAT";

    @BindView(R.id.spinner)
    AppCompatSpinner spinner;

    private ArrayAdapter adapter;

    private OnButtonClickListener callback;
    private ArrayList<String> items;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_select_province, container, false);
        ButterKnife.bind(this, view);
        initViews();
        return view;
    }

    private void initViews() {
        items = new ArrayList<>();
        for (String s : getResources().getStringArray(R.array.hekmat_city_names)) {
            items.add(s);
        }
        adapter = new ArrayAdapter<String>(getContext(),
                R.layout.text_view_dark_text_for_spinner,
                items);
        spinner.setAdapter(adapter);
        int lastSelectedId = getLastSelectedProvince();
        int[] ids = getResources().getIntArray(R.array.hekmat_city_codes);
        for (int x = 0; x < ids.length; x++) {
            if (lastSelectedId == ids[x]) {
                spinner.setSelection(x);
                break;
            }
        }
    }

    @OnClick(R.id.selectProvinceRow)
    public void onSelectProvinceRowClick() {
        spinner.performClick();
    }

    public void show(FragmentManager fm, OnButtonClickListener callback) {
        this.callback = callback;
        show(fm, "SelectProvinceDialog");
    }

    public interface OnButtonClickListener {
        void onSelectButton(int id, String name);
    }

    @OnClick(R.id.selectButton)
    public void onSubmitButton() {
        if (callback != null) {
            int position = spinner.getSelectedItemPosition();
            int provinceCode = getResources().getIntArray(R.array.hekmat_city_codes)[position];
            String provinceName = getResources().getStringArray(R.array.hekmat_city_names)[position];
            saveLastSelectedProvince(provinceCode);
            callback.onSelectButton(provinceCode, provinceName);
        }
    }

    private void saveLastSelectedProvince(int id) {
        DiskDataHelper.putInt(LAST_SELECTED_PROVINCE_IN_HEKMAT, id);
    }

    private int getLastSelectedProvince() {
        return DiskDataHelper.getInt(LAST_SELECTED_PROVINCE_IN_HEKMAT);
    }

}
