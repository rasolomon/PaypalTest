package com.paypal.bfs.test.employeeserv.api;

import com.paypal.bfs.test.employeeserv.api.model.Employee;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Interface for employee resource operations.
 */
public interface EmployeeResource {

    /**
     * Retrieves the {@link EmployeeBackup} resource by id.
     *
     * @param id employee id.
     * @return {@link EmployeeBackup} resource.
     */
    @RequestMapping("/v1/bfs/employees/get/{id}")
    ResponseEntity<Employee> employeeGetById(@PathVariable("id") String id);

    // ----------------------------------------------------------
    // TODO - add a new operation for creating employee resource.
    // ----------------------------------------------------------
    @RequestMapping("/v1/bfs/employees/create")
    ResponseEntity<Employee> createEmployee(@RequestBody Employee employee);

}
