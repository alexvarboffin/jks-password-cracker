package com.walhalla.whatismyipaddress.features.checkhost;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Handler;

import androidx.preference.PreferenceManager;

import com.walhalla.ui.DLog;
import com.walhalla.whatismyipaddress.R;
import com.walhalla.whatismyipaddress.adapter.items.ViewModel;
import com.walhalla.whatismyipaddress.features.base.BasePresenter;

import java.util.ArrayList;
import java.util.List;

import java.util.List;

public class ${featureName?cap_first} extends BasePresenter {

   private static final String KEY_IP = "key_${featureName}_ip";
   private final String NO_DATA_FOUND;
   private final SharedPreferences preferences;
   ArrayList<ViewModel> models = new ArrayList<>();

    private ${featureName?cap_first}View view;
    //private ${featureName?cap_first}Model model;
    String ip;

    private List<String> name, values;
    private final String ERROR_MESSAGE = "ErrorMessage";

    public ${featureName?cap_first}(Context context, ${featureName?cap_first}View view, Handler handler) {
        super(handler);
        this.view = view;
        NO_DATA_FOUND = context.getString(R.string.no_data_found);
        this.preferences = PreferenceManager.getDefaultSharedPreferences(context);
    }

    public void ${featureName}(String pingIpText) {
        try {
            view.showProgress();
            models = new ArrayList<>();

            executor.execute(() -> {
                aaaa();
                handler.post(() -> {
                    view.hideProgress();
                    //@@
                });
            });
        } catch (Exception e) {
            DLog.handleException(e);
        }
    }

    private void aaaa() {
    }

    public void init() {
        String ip = preferences.getString(KEY_IP, "8.8.8.8");
        view.init(ip);
    }

    // Presenter implementation


    public interface ${featureName}View {

        void showProgress();

        void hideProgress();

        void successResult(List<ViewModel> dataModels);

        void init(String ip);
    }
}