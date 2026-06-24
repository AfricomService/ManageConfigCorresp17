package com.africom.configmanage.client;

import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@AuthorizedFeignClient(name = "ORGACARE")
public interface OrgaCareRestClient {
    // /api/departements-hierarchy/{organigrammeCode}
    @GetMapping("/api/departementshierarchy/{organigrammeCode}")
    List<DepartementTreeFeignDTO> getDepartementHierarchy(@PathVariable("organigrammeCode") String organigrammeCode);

    @GetMapping("/api/departements/by-code/{code}")
    DepartementFeignDTO getDepartementByCode(@PathVariable("code") String code);

    @GetMapping("/api/organigrammes/{id}/list")
    List<OrganigrammeFeignDTO> getOranigrammeById(@PathVariable(name = "id") Long id);

    @GetMapping("/api/departements/ogra-code-by-dept-code")
    String getOrgaCodeByDeptCode(@RequestParam(name = "deptCode") String deptCode);
    //TODO : API GET getDepartementByCode depuis Orgacare
    //    @GetMapping("/api/departements-hierarchy/{organigrammeCode}")
    //    List<DepartementTreeFeignDTO> getDepartementHierarchy(@PathVariable(name = "organigrammeCode") String organigrammeCode);

    @GetMapping("/api/societes/list")
    List<Map<String, Object>> getAllSocietesIdAndRaisonSociale();
}
