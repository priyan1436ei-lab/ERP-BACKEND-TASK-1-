package com.priyan.studentmanagement.dto;

import java.util.Map;

public class StatisticsResponseDTO {

    private long totalStudents;
    private Map<String, Long> departmentWise;
    private Map<String, Long> yearWise;

    public StatisticsResponseDTO() {
    }

    public StatisticsResponseDTO(long totalStudents, Map<String, Long> departmentWise, Map<String, Long> yearWise) {
        this.totalStudents = totalStudents;
        this.departmentWise = departmentWise;
        this.yearWise = yearWise;
    }

    public long getTotalStudents() {
        return totalStudents;
    }

    public void setTotalStudents(long totalStudents) {
        this.totalStudents = totalStudents;
    }

    public Map<String, Long> getDepartmentWise() {
        return departmentWise;
    }

    public void setDepartmentWise(Map<String, Long> departmentWise) {
        this.departmentWise = departmentWise;
    }

    public Map<String, Long> getYearWise() {
        return yearWise;
    }

    public void setYearWise(Map<String, Long> yearWise) {
        this.yearWise = yearWise;
    }
}
