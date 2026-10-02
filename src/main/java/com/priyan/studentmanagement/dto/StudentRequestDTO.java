package com.priyan.studentmanagement.dto;

import jakarta.validation.constraints.*;

public class StudentRequestDTO {

    @NotBlank(message = "Register number cannot be empty")
    private String registerNo;

    @NotBlank(message = "Name cannot be empty")
    private String name;

    @NotBlank(message = "Email cannot be empty")
    @Email(message = "Invalid email address")
    private String email;

    @NotBlank(message = "Phone cannot be empty")
    @Pattern(regexp = "^[6-9][0-9]{9}$", message = "Invalid phone number")
    private String phone;

    @NotBlank(message = "Department cannot be empty")
    private String department;

    @NotNull(message = "Year cannot be empty")
    @Min(value = 1, message = "Year must be between 1 and 4")
    @Max(value = 4, message = "Year must be between 1 and 4")
    private Integer year;

    @NotNull(message = "Semester cannot be empty")
    @Min(value = 1, message = "Semester must be between 1 and 8")
    @Max(value = 8, message = "Semester must be between 1 and 8")
    private Integer semester;

    public StudentRequestDTO() {
    }

    public StudentRequestDTO(String registerNo, String name, String email, String phone,
                             String department, Integer year, Integer semester) {
        this.registerNo = registerNo;
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.department = department;
        this.year = year;
        this.semester = semester;
    }

    public String getRegisterNo() {
        return registerNo;
    }

    public void setRegisterNo(String registerNo) {
        this.registerNo = registerNo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public Integer getSemester() {
        return semester;
    }

    public void setSemester(Integer semester) {
        this.semester = semester;
    }
}
