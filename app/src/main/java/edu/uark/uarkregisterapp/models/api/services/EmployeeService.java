package edu.uark.uarkregisterapp.models.api.services;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import edu.uark.uarkregisterapp.models.api.ActiveEmployeeCounts;
import edu.uark.uarkregisterapp.models.api.ApiResponse;
import edu.uark.uarkregisterapp.models.api.Employee;
import edu.uark.uarkregisterapp.models.api.EmployeeLogin;
import edu.uark.uarkregisterapp.models.api.Product;
import edu.uark.uarkregisterapp.models.api.enums.ApiObject;
import edu.uark.uarkregisterapp.models.api.enums.EmployeeApiMethod;
import edu.uark.uarkregisterapp.models.api.enums.EmployeeClassification;
import edu.uark.uarkregisterapp.models.api.interfaces.LoadFromJsonInterface;
import edu.uark.uarkregisterapp.models.api.interfaces.PathElementInterface;

public class EmployeeService extends BaseRemoteService {
    public ApiResponse<Employee> getEmployee(UUID employeeId) {
        return this.readEmployeeDetailsFromResponse(
            this.<Employee>performGetRequest(
                this.buildPath(employeeId)
            )
        );
    }

    public ApiResponse<ActiveEmployeeCounts> getActiveEmployeeCounts() {
        return this.readActiveCountsDetailsFromResponse(
            this.<ActiveEmployeeCounts>performGetRequest(
                this.buildPath(
                    (new PathElementInterface[] { EmployeeApiMethod.ACTIVE_COUNTS })
                    , Integer.toString(EmployeeClassification.NOT_DEFINED.getValue())
                )
            )
        );
    }

    public ApiResponse<List<Employee>> getEmployees() {
        ApiResponse<List<Employee>> apiResponse = this.performGetRequest(
                this.buildPath()
        );

        JSONArray rawJsonArray = this.rawResponseToJSONArray(apiResponse.getRawResponse());
        if (rawJsonArray != null) {
            ArrayList<Employee> employees = new ArrayList<>(rawJsonArray.length());
            for (int i = 0; i < rawJsonArray.length(); i++) {
                try {
                    employees.add((new Employee()).loadFromJson(rawJsonArray.getJSONObject(i)));
                } catch (JSONException e) {
                    Log.d("GET EMPLOYEE", e.getMessage());
                }
            }

            apiResponse.setData(employees);
        } else {
            apiResponse.setData(new ArrayList<Employee>(0));
        }

        return apiResponse;
    }

    public ApiResponse<Employee> updateEmployee(Employee employee) {
        return this.readEmployeeDetailsFromResponse(
            this.<Employee>performPutRequest(
                this.buildPath(employee.getId())
                , employee.convertToJson()
            )
        );
    }

    public ApiResponse<Employee> createEmployee(Employee employee) {
        return this.readEmployeeDetailsFromResponse(
            this.<Employee>performPostRequest(
                this.buildPath()
                , employee.convertToJson()
            )
        );
    }

    public ApiResponse<String> deleteEmployee(UUID employeeId) {
        return this.<String>performDeleteRequest(
            this.buildPath(employeeId)
        );
    }

    public ApiResponse<Employee> logIn(EmployeeLogin employeeLogin) {
        return this.readEmployeeDetailsFromResponse(
            this.<Employee>performPostRequest(
                this.buildPath(
                    (new PathElementInterface[] { EmployeeApiMethod.LOGIN })
                )
                , employeeLogin.convertToJson()
            )
        );
    }

    private ApiResponse<Employee> readEmployeeDetailsFromResponse(ApiResponse<Employee> apiResponse) {
        return this.readDetailsFromResponse(
            apiResponse, (new Employee())
        );
    }

    private ApiResponse<ActiveEmployeeCounts> readActiveCountsDetailsFromResponse(ApiResponse<ActiveEmployeeCounts> apiResponse) {
        return this.readDetailsFromResponse(
            apiResponse, (new ActiveEmployeeCounts())
        );
    }

    private <T extends LoadFromJsonInterface<T>> ApiResponse<T> readDetailsFromResponse(ApiResponse<T> apiResponse, T apiObject) {
        JSONObject rawJsonObject = this.rawResponseToJSONObject(
            apiResponse.getRawResponse()
        );

        if (rawJsonObject != null) {
            apiResponse.setData(
                apiObject.loadFromJson(rawJsonObject)
            );
        }

        return apiResponse;
    }

    public EmployeeService() { super(ApiObject.EMPLOYEE); }
}
