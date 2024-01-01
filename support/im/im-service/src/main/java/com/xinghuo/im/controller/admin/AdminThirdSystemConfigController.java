package com.xinghuo.im.controller.admin;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.xinghuo.im.model.dto.AddThirdSystemConfigDTO;
import com.xinghuo.im.model.dto.DeleteThirdSystemConfigDTO;
import com.xinghuo.im.model.dto.ThirdSystemConfigDTO;
import com.xinghuo.im.model.vo.ThirdSystemConfigVO;
import com.xinghuo.im.service.ThirdSystemConfigService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @author zhou miao
 * @date 2022/05/31
 */
@RestController
@Api(tags = "(后管) 系统管理接口")
@RequestMapping("/admin/thirdSystem")
public class AdminThirdSystemConfigController {
    @Resource
    private ThirdSystemConfigService thirdSystemConfigService;

    @ApiOperation(value = "新增系统")
    @PostMapping
    public Boolean add(@Validated @RequestBody AddThirdSystemConfigDTO addThirdSystemConfigDTO) {
        thirdSystemConfigService.add(addThirdSystemConfigDTO);
        return true;
    }

    @ApiOperation(value = "删除系统")
    @DeleteMapping
    public Boolean delete(@Validated  @RequestBody DeleteThirdSystemConfigDTO deleteThirdSystemConfigDTO) {
        thirdSystemConfigService.delete(deleteThirdSystemConfigDTO);
        return true;
    }

    @ApiOperation(value = "查询系统")
    @GetMapping
    public IPage<ThirdSystemConfigVO> page(ThirdSystemConfigDTO thirdSystemConfigDTO) {
        return thirdSystemConfigService.page(thirdSystemConfigDTO, thirdSystemConfigDTO);
    }
}
