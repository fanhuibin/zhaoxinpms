/* Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.zhaoxinms.workflow.engine.modeler;

import com.zhaoxinms.common.core.controller.BaseController;
import com.zhaoxinms.common.core.domain.entity.SysRole;
import com.zhaoxinms.common.core.domain.entity.SysUser;
import com.zhaoxinms.common.core.page.TableDataInfo;
import com.zhaoxinms.system.mapper.SysUserMapper;
import com.zhaoxinms.system.service.ISysRoleService;
import com.zhaoxinms.system.service.ISysUserService;

import lombok.AllArgsConstructor;
import org.activiti.editor.constants.ModelDataJsonConstants;
import org.activiti.engine.ActivitiException;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.repository.Model;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.nio.charset.StandardCharsets;
import java.util.List;

@RestController
@AllArgsConstructor
public class ModelEditorJsonRestResource extends BaseController implements ModelDataJsonConstants {

  private static final Logger LOGGER = LoggerFactory.getLogger(ModelEditorJsonRestResource.class);

  private RepositoryService repositoryService;
  private ObjectMapper objectMapper;
  private ISysUserService userService;
  private ISysRoleService roleService;
  private SysUserMapper userMapper;

  @RequestMapping(value="/modeler/model/{modelId}/json", method = RequestMethod.GET, produces = "application/json")
  public ObjectNode getEditorJson(@PathVariable String modelId) {
    ObjectNode modelNode = null;

    Model model = repositoryService.getModel(modelId);

    if (model != null) {
      try {
        if (StringUtils.isNotEmpty(model.getMetaInfo())) {
          modelNode = (ObjectNode) objectMapper.readTree(model.getMetaInfo());
        } else {
          modelNode = objectMapper.createObjectNode();
          modelNode.put(MODEL_NAME, model.getName());
        }
        modelNode.put(MODEL_ID, model.getId());
        ObjectNode editorJsonNode = (ObjectNode) objectMapper.readTree(
            new String(repositoryService.getModelEditorSource(model.getId()), StandardCharsets.UTF_8));
        modelNode.put("model", editorJsonNode);

      } catch (Exception e) {
        LOGGER.error("Error creating model JSON", e);
        throw new ActivitiException("Error creating model JSON", e);
      }
    }
    return modelNode;
  }

  /**
   * 免登录访问用户列表
   * @param user
   * @return
   */
  @GetMapping("/list")
  public TableDataInfo list(SysUser user)
  {
    startPage();
    List<SysUser> list = userService.selectUserList(user);
    return getDataTable(list);
  }

  /**
   * 角色列表
   * @param role
   * @return
   */
  @GetMapping("/modeler/role/list")
  public TableDataInfo list(SysRole role)
  {
    startPage();
    List<SysRole> list = roleService.selectRoleList(role);
    return getDataTable(list);
  }

  /**
   * 某角色下的用户列表
   * @param roleKey
   * @return
   */
  @GetMapping("/modeler/user/listByRoleKey")
  public TableDataInfo list(String roleKey)
  {
    startPage();
    List<SysUser> list = userMapper.selectUserListByRoleKey(roleKey);
    return getDataTable(list);
  }

}
