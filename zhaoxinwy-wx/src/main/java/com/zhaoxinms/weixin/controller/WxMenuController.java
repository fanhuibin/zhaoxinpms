/*
MIT License

Copyright (c) 2020 www.joolun.com

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
*/
package com.zhaoxinms.weixin.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.zhaoxinms.weixin.service.WxMenuService;

import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.error.WxErrorException;

/**
 * 自定义菜单
 *
 * @author www.joolun.com
 * @date 2019-03-27 16:52:10
 */
@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("/wxmenu")
public class WxMenuController extends BaseController {

	private final WxMenuService wxMenuService;

	/**
	 * 通过appId查询自定义菜单
	 *
	 * @return R
	 */
	@GetMapping("/list")
	@PreAuthorize("@ss.hasPermi('wxmp:wxmenu:get')")
	public AjaxResult getWxMenuButton() {
		return AjaxResult.success(wxMenuService.getWxMenuButton());
	}

	/**
	 * 保存并发布菜单
	 *
	 * @param
	 * @return R
	 */
	@PostMapping("/release")
	@PreAuthorize("@ss.hasPermi('wxmp:wxmenu:add')")
	public AjaxResult saveAndRelease(@RequestBody String data) {
		JSONObject jSONObject = JSONUtil.parseObj(data);
		String strWxMenu = jSONObject.getStr("strWxMenu");
		String appId = jSONObject.getStr("appId");
		try {
			wxMenuService.saveAndRelease(strWxMenu);
			return AjaxResult.success();
		} catch (WxErrorException e) {
			e.printStackTrace();
			log.error("发布自定义菜单失败appID:" + appId + ":" + e.getMessage());
			return AjaxResult.error(e.getMessage());
		}
	}

}
