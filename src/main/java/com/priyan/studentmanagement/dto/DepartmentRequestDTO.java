package com.priyan.studentmanagement.dto;

import jakarta.validation.constraints.NotBlank;

public class DepartmentRequestDTO {

    @NotBlank(message = "Department code cannot be empty")
    private String departmentCode;

    @NotBlank(message = "Department name cannot be empty")
    private String departmentName;

    public DepartmentRequestDTO() {
    }

    public DepartmentRequestDTO(String departmentCode, String departmentName) {
        this.departmentCode = departmentCode;
        this.departmentName = departmentName;
    }

    public String getDepartmentCode() {
        return departmentCode;
    }

    public void setDepartmentCode(String departmentCode) {
        this.departmentCode = departmentCode;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }
}
