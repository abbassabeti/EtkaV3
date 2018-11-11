package ir.etkastores.app.data;

import java.util.ArrayList;
import java.util.List;

import ir.etkastores.app.models.OauthResponse;
import ir.etkastores.app.models.tickets.DepartmentModel;
import ir.etkastores.app.webServices.ApiProvider;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TicketsDepartmentsManager {

    public static TicketsDepartmentsManager instance;

    private final static int MAX_RETRY_COUNT = 4;

    private List<DepartmentModel> departments;
    private OnDepartmentCallback callback;
    Call<OauthResponse<List<DepartmentModel>>> req;

    public static TicketsDepartmentsManager getInstance() {
        if (instance == null) instance = new TicketsDepartmentsManager();
        return instance;
    }

    private TicketsDepartmentsManager() {
        departments = new ArrayList<>();
    }

    public void fetchDepartments(OnDepartmentCallback callback) {
        this.callback = callback;
        if (departments != null && departments.size() > 0) {
            if (callback != null) callback.onDepartmentsFetched(departments);
        } else {
            loadData();
        }
    }

    private int retryCounter = MAX_RETRY_COUNT;

    private void loadData() {
        req = ApiProvider.getInstance().getAuthorizedApi().getDepartments();
        req.enqueue(new Callback<OauthResponse<List<DepartmentModel>>>() {
            @Override
            public void onResponse(Call<OauthResponse<List<DepartmentModel>>> call, Response<OauthResponse<List<DepartmentModel>>> response) {
                if (response.isSuccessful()) {
                    if (response.body().isSuccessful()) {
                        departments = response.body().getData();
                        retryCounter = MAX_RETRY_COUNT;
                        if (callback != null) callback.onDepartmentsFetched(departments);
                    } else {
                        if (callback != null)
                            callback.onDepartmentsFailure(response.body().getMeta().getMessage());
                    }
                } else {
                    onFailure(call, null);
                }
            }

            @Override
            public void onFailure(Call<OauthResponse<List<DepartmentModel>>> call, Throwable t) {
                if (retryCounter > 0) {
                    retryCounter--;
                    loadData();
                } else {
                    retryCounter = MAX_RETRY_COUNT;
                    if (callback != null) callback.onDepartmentsFailure(null);
                }
            }
        });
    }

    public interface OnDepartmentCallback {
        void onDepartmentsFetched(List<DepartmentModel> departments);

        void onDepartmentsFailure(String message);
    }

}
