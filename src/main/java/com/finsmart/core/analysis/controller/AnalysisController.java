package com.finsmart.core.analysis.controller;

import com.finsmart.core.analysis.dto.AnalysisDashboardResponse;
import com.finsmart.core.analysis.services.AnalysisService;
import com.finsmart.core.auth.entities.User;
import com.finsmart.core.common.dto.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/analysis")
@RequiredArgsConstructor
public class AnalysisController {

    private final AnalysisService analysisService;

    @GetMapping("/dashboard")
    public ResponseEntity<ApiResponse<AnalysisDashboardResponse>> getAnalysisDashboard(){

        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        AnalysisDashboardResponse dashBoardData = analysisService.getDashboard(user.getId());

        return ResponseEntity.ok(ApiResponse.success("Dashboard verileri başarıyla getirildi", dashBoardData));
    }
}
