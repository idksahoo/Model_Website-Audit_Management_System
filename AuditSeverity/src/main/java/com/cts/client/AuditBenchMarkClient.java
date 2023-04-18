package com.cts.client;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;

import com.cts.pojo.AuditBenchmark;

@FeignClient(name = "AuditBenchmark", url = "${Benchmark.url}")
public interface AuditBenchMarkClient {
	@GetMapping("/AuditBenchmark")
	public ResponseEntity<List<AuditBenchmark>> getBenchmaks();

}
