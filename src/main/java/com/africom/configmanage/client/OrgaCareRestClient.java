package com.africom.configmanage.client;

import java.util.List;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.cloud.openfeign.FeignClientProperties;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "ORGACAREAPP")
public interface OrgaCareRestClient {
    @GetMapping("/api/departements-hierarchy/{organigrammeCode}")
    List<DepartementTreeFeignDTO> getDepartementHierarchy(@PathVariable(name = "organigrammeCode") String organigrammeCode);
}
