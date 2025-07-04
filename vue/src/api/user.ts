import request from './request'
import type { User } from '../types/user'

/**
 * 用户注册
 * @param {Object} data - 注册数据
 * @param {string} data.account - 账号
 * @param {string} data.password - 密码
 * @param {string} data.companyName - 企业名称
 * @param {string} data.companyContact - 企业联系方式
 * @param {string} data.verifyCode - 验证码
 */
export function register(data: any) {
  return request.post('/api/register', data)
}

/**
 * 用户登录
 * @param {Object} data - 登录数据
 * @param {string} data.account - 账号
 * @param {string} data.password - 密码
 */
export function login(data: { account: string; password: string }) {
  return request.post('/api/login', data)
}

/**
 * 获取个人信息
 * @returns {Promise<Object>} 用户信息
 */
export function getProfile() {
  return request.get('/api/user/profile')
}

/**
 * 更新个人信息
 * @param {Object} data - 个人信息数据
 * @param {string} data.nickname - 昵称
 * @param {string} data.phone - 手机号
 * @param {string} data.email - 邮箱
 * @param {string} data.gender - 性别
 */
export function updateProfile(data: Partial<User>) {
  return request.put('/api/user/profile', data)
}

/**
 * 修改密码
 * @param {Object} data - 密码数据
 * @param {string} data.oldPassword - 旧密码
 * @param {string} data.newPassword - 新密码
 */
export function changePassword(data: { oldPassword: string; newPassword: string }) {
  return request.put('/api/user/password', data)
}

/**
 * 获取用户列表（管理员功能）
 * @param {Object} params - 查询参数
 * @param {string} [params.account] - 账号
 * @param {string} [params.companyName] - 企业名称
 * @param {string} [params.nickname] - 昵称
 * @param {string} [params.phone] - 手机号
 * @param {string} [params.status] - 状态
 * @param {number} [params.page] - 页码
 * @param {number} [params.pageSize] - 每页数量
 * @returns {Promise<Object>} 用户列表和总数
 */
export async function getUserList(params) {
  const res = await request.get('/api/users', { params })
  return res
}

/**
 * 创建用户（管理员功能）
 * @param {Object} data - 用户数据
 * @param {string} data.account - 账号
 * @param {string} data.password - 密码
 * @param {string} data.companyName - 企业名称
 * @param {string} data.companyContact - 企业联系方式
 * @param {string} [data.nickname] - 昵称
 * @param {string} [data.phone] - 手机号
 * @param {string} [data.email] - 邮箱
 * @param {string} [data.gender] - 性别
 * @param {string} [data.status] - 状态
 * @param {string} [data.remark] - 备注
 */
export function createUser(data) {
  return request.post('/api/users', data)
}

/**
 * 更新用户（管理员功能）
 * @param {Object} data - 用户数据
 * @param {string} data.id - 用户ID
 * @param {string} [data.nickname] - 昵称
 * @param {string} [data.phone] - 手机号
 * @param {string} [data.email] - 邮箱
 * @param {string} [data.gender] - 性别
 * @param {string} [data.companyName] - 企业名称
 * @param {string} [data.companyContact] - 企业联系方式
 * @param {string} [data.status] - 状态
 * @param {string} [data.remark] - 备注
 */
export function updateUser(data) {
  return request.put(`/api/users/${data.id}`, data)
}

/**
 * 删除用户（管理员功能）
 * @param {string|number} id - 用户ID
 */
export function deleteUser(id) {
  return request.delete(`/api/users/${id}`)
}

/**
 * 分配用户角色（管理员功能）
 * @param {string|number} id - 用户ID
 * @param {string|number} roleId - 角色ID
 */
export function assignUserRole(id, roleId) {
  return request.put(`/api/users/${id}/roles`, { roleId })
} 