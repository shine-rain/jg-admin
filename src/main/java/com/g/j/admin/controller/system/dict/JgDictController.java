package com.g.j.admin.controller.system.dict;

import cn.dev33.satoken.util.SaResult;
import com.g.j.admin.controller.common.BaseController;
import com.g.j.admin.entity.system.dto.DictAddModifyDto;
import com.g.j.admin.entity.system.vo.DictVo;
import com.g.j.admin.service.system.dict.IDictService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Author: jg-admin
 * @Description: TODO
 * @Version:1.0.0
 */
@RestController
@RequestMapping("/system/dict")
public class JgDictController extends BaseController {

    @Autowired
    private IDictService iDictService;

    @RequestMapping(value = "tree", method = RequestMethod.GET, produces = "application/json")
    public SaResult queryDictTree() {
        List<DictVo> dictVoList = iDictService.queryDictTree();
        return SaResult.data(dictVoList);
    }

    @RequestMapping(method = RequestMethod.POST, produces = "application/json")
    public SaResult addModifyDict(@RequestBody DictAddModifyDto dictAddModifyDto) {
        Boolean result = iDictService.addModifyDict(dictAddModifyDto);
        return SaResult.data(result);
    }

    @RequestMapping(value = "{id}", method = RequestMethod.DELETE, produces = "application/json")
    public SaResult deleteDictItem(@PathVariable(value = "id") String id) {
        Boolean result = iDictService.deleteDict(id);
        return SaResult.data(result);
    }
}
