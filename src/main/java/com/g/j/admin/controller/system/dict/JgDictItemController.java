package com.g.j.admin.controller.system.dict;

import cn.dev33.satoken.util.SaResult;
import com.g.j.admin.controller.common.BaseController;
import com.g.j.admin.entity.common.BasePage;
import com.g.j.admin.entity.system.dto.DictItemAddModifyDto;
import com.g.j.admin.entity.system.dto.DictItemQueryDto;
import com.g.j.admin.entity.system.other.ArrParam;
import com.g.j.admin.entity.system.vo.DictItemVo;
import com.g.j.admin.service.system.dict.IDictItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @Author: jg-admin
 * @Description: TODO
 * @Version:1.0.0
 */
@RestController
@RequestMapping("/system/dict/item")
public class JgDictItemController extends BaseController {

    @Autowired
    private IDictItemService iDictItemService;

    @RequestMapping(method = RequestMethod.GET, produces = "application/json")
    public SaResult queryDictItemList(DictItemQueryDto dictItemQueryDto) {
        BasePage<DictItemVo> dictItemVoList = iDictItemService.queryDictItemList(dictItemQueryDto);
        return SaResult.data(dictItemVoList);
    }

    @RequestMapping(method = RequestMethod.POST, produces = "application/json")
    public SaResult addModifyDictItem(@RequestBody DictItemAddModifyDto dictItemAddModifyDto) {
        Boolean result = iDictItemService.addModifyDictItem(dictItemAddModifyDto);
        return SaResult.data(result);
    }

    @RequestMapping(value = "{id}", method = RequestMethod.DELETE, produces = "application/json")
    public SaResult deleteDictItem(@PathVariable(value = "id") String id) {
        Boolean result = iDictItemService.deleteDictItem(id);
        return SaResult.data(result);
    }

    @RequestMapping(method = RequestMethod.DELETE, produces = "application/json")
    public SaResult deleteDictItemByIds(@RequestBody ArrParam arrParam) {
        Boolean result = iDictItemService.deleteDictItemByIds(arrParam);
        return SaResult.data(result);
    }

}
