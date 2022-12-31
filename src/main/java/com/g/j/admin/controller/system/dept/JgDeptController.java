package com.g.j.admin.controller.system.dept;

import cn.dev33.satoken.util.SaResult;
import com.g.j.admin.controller.common.BaseController;
import com.g.j.admin.entity.system.dto.DeptAddModifyDto;
import com.g.j.admin.entity.system.dto.DeptQueryDto;
import com.g.j.admin.entity.system.vo.DeptVo;
import com.g.j.admin.service.system.dept.IJgDeptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/system/dept")
public class JgDeptController extends BaseController {

    @Autowired
    private IJgDeptService iJgDeptService;

    @RequestMapping(value = "tree", method = RequestMethod.GET, produces = "application/json")
    public SaResult queryAllTreeMenu(DeptQueryDto deptQueryDto) {
        List<DeptVo> deptVoList = iJgDeptService.queryTreeDeptList(deptQueryDto);
        return SaResult.data(deptVoList);
    }

    @RequestMapping(method = RequestMethod.POST, produces = "application/json")
    public SaResult addModifyDept(@RequestBody DeptAddModifyDto deptAddModifyDto) {
        Boolean result = iJgDeptService.addModifyDept(deptAddModifyDto);
        return SaResult.data(result);
    }

    @RequestMapping(value = "{id}",method = RequestMethod.DELETE, produces = "application/json")
    public SaResult deleteDept(@PathVariable(value = "id") String id) {
        Boolean result = iJgDeptService.deleteDept(id);
        return SaResult.data(result);
    }

}
