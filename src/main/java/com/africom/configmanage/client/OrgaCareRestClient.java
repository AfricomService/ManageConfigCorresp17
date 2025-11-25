package com.africom.configmanage.client;

import java.util.List;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.cloud.openfeign.FeignClientProperties;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "ORGACARE")
public interface OrgaCareRestClient {
    @GetMapping("/api/departements-hierarchy/{organigrammeCode}")
    List<DepartementTreeFeignDTO> getDepartementHierarchy(@PathVariable(name = "organigrammeCode") String organigrammeCode);

    @GetMapping("/api/departements/by-code/{code}")
    DepartementFeignDTO getDepartementByCode(@PathVariable("code") String code);

    @GetMapping("/api/organigrammes/{id}")
    List<OrganigrammeFeignDTO> getOranigrammeById(@PathVariable(name = "id") Long id);

    @GetMapping("/api/departements/ogra-code-by-dept-code")
    String getOrgaCodeByDeptCode(@RequestParam(name = "deptCode") String deptCode);
    //TODO : API GET getDepartementByCode depuis Orgacare
    //    @GetMapping("/api/departements-hierarchy/{organigrammeCode}")
    //    List<DepartementTreeFeignDTO> getDepartementHierarchy(@PathVariable(name = "organigrammeCode") String organigrammeCode);
}
