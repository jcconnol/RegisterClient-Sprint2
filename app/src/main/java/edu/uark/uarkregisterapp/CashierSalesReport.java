package edu.uark.uarkregisterapp;

import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import edu.uark.uarkregisterapp.adapters.EmployeeListAdapter;
import edu.uark.uarkregisterapp.adapters.ProductListAdapter;
import edu.uark.uarkregisterapp.models.api.ApiResponse;
import edu.uark.uarkregisterapp.models.api.Employee;
import edu.uark.uarkregisterapp.models.api.Product;
import edu.uark.uarkregisterapp.models.api.services.EmployeeService;
import edu.uark.uarkregisterapp.models.api.services.ProductService;

public class CashierSalesReport extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cashier_sales_report);

        this.employees = new ArrayList<>();
        this.employeeListAdapter = new EmployeeListAdapter(this, this.employees);
        this.getEmployeeListView().setAdapter(this.employeeListAdapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        (new CashierSalesReport.RetrieveEmployeeTask()).execute();
    }

    private ListView getEmployeeListView() {
        return (ListView) this.findViewById(R.id.employee_report_list_view_products);
    }

    private class RetrieveEmployeeTask extends AsyncTask<Void, Void, ApiResponse<List<Employee>>> {
        @Override
        protected void onPreExecute() {
            this.loadingProductsAlert.show();
        }

        @Override
        protected ApiResponse<List<Employee>> doInBackground(Void... params) {
            ApiResponse<List<Employee>> apiResponse = (new EmployeeService()).getEmployees();

            if (apiResponse.isValidResponse()) {
                employees.clear();
                employees.addAll(apiResponse.getData());

                Collections.sort(
                        employees,
                        new Comparator<Employee>() {
                            @Override
                            public int compare(Employee employee1, Employee employee2) {
                                return employee2.compareTo(employee1);
                            }
                        }
                );


            }

            return apiResponse;
        }

        @Override
        protected void onPostExecute(ApiResponse<List<Employee>> apiResponse) {
            if (apiResponse.isValidResponse()) {
                employeeListAdapter.notifyDataSetChanged();
            }

            this.loadingProductsAlert.dismiss();

            if (!apiResponse.isValidResponse()) {
                new AlertDialog.Builder(CashierSalesReport.this).
                        setMessage(R.string.alert_dialog_products_load_failure).
                        setPositiveButton(
                                R.string.button_dismiss,
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        dialog.dismiss();
                                    }
                                }
                        ).
                        create().
                        show();
            }
        }

        private RetrieveEmployeeTask() {
            this.loadingProductsAlert = new AlertDialog.Builder(CashierSalesReport.this).
                    setMessage(R.string.alert_dialog_products_loading).
                    create();
        }

        private AlertDialog loadingProductsAlert;
    }

    private List<Employee> employees;
    private EmployeeListAdapter employeeListAdapter;
}
