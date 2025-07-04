import request from './request'

/**
 * 获取权限列表
 * @returns {Promise<Object>} 权限列表
 */
export function getPermissionList() {
  return request.get('/api/permissions')
} 