import request from './request'

/**
 * 获取角色列表
 * @returns {Promise<Object>} 角色列表
 */
export function getRoleList() {
  return request.get('/api/roles')
}

/**
 * 创建角色
 * @param {Object} data - 角色数据
 * @param {string} data.name - 角色名称
 * @param {string} [data.description] - 角色描述
 */
export function createRole(data) {
  return request.post('/api/roles', data)
}

/**
 * 更新角色
 * @param {Object} data - 角色数据
 * @param {number} data.id - 角色ID
 * @param {string} data.name - 角色名称
 * @param {string} [data.description] - 角色描述
 */
export function updateRole(data) {
  return request.put(`/api/roles/${data.id}`, data)
}

/**
 * 删除角色
 * @param {number|string} id - 角色ID
 */
export function deleteRole(id) {
  return request.delete(`/api/roles/${id}`)
}

/**
 * 分配角色权限
 * @param {number|string} roleId - 角色ID
 * @param {number[]|string[]} permissionIds - 权限ID数组
 */
export function assignRolePermissions(roleId, permissionIds) {
  return request.put(`/api/roles/${roleId}/permissions`, { permissionIds })
} 