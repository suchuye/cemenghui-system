// 用户核心信息
export interface User {
  id: number;
  account: string;
  password?: string;
  companyName: string;
  companyContact: string;
  roleId?: number;
  status?: 'active' | 'inactive';
  userType?: 'user' | 'admin';
  createdAt?: string;
}

// 用户补充信息
export interface UserProfile {
  id: number;
  userId: number;
  nickname?: string;
  phone?: string;
  email?: string;
  gender?: 'male' | 'female' | 'secret';
  birthday?: string;
  address?: string;
  avatar?: string;
}

// 角色
export interface Role {
  id: number;
  name: string;
  description?: string;
}

// 权限
export interface Permission {
  id: number;
  name: string;
  code: string;
  description?: string;
}



// 登录接口返回的用户信息类型（User + 部分UserProfile + 扩展字段）
export interface LoginUserInfo {
  id: number; // 用户ID，User
  account: string; // 账号，User
  nickname?: string; // 昵称，UserProfile
  userType?: 'user' | 'admin'; // 用户类型，User
  companyName?: string; // 企业名称，User
  companyContact?: string; // 企业联系方式，User
  status?: 'active' | 'inactive'; // 状态，User
  phone?: string; // 手机号，UserProfile
  email?: string; // 邮箱，UserProfile
  gender?: 'male' | 'female' | 'secret'; // 性别，UserProfile
  avatar?: string; // 头像，UserProfile
  createdAt?: string; // 注册时间，User
  lastLogin?: string; // 最后登录时间，扩展
  remark?: string; // 备注，扩展
  role?: string; // 角色名称，如'管理员'
  permissions?: string[]; // 权限标识数组，如['user:manage']
} 