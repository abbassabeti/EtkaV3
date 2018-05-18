package ir.etkastores.app.data;

import java.util.ArrayList;
import java.util.List;

import ir.etkastores.app.models.tickets.DepartmentModel;

public class TicketsDepartmentsManager {

    public static TicketsDepartmentsManager instance;

    private List<DepartmentModel> departments;
    private OnDepartmentCallback callback;

    public static void setInstance(TicketsDepartmentsManager instance) {
        TicketsDepartmentsManager.instance = instance;
    }

    private TicketsDepartmentsManager(){
        departments = new ArrayList<>();
    }

    public void getDepartments(OnDepartmentCallback callback){
        this.callback = callback;
    }

    private void loadData(){

    }

    public interface OnDepartmentCallback{
        void onDepartmentsFetched();
        void onDepartmentsFailure();
    }

}
