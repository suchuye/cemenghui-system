# 实体类设计（TypeScript优化版）

---

## 1. User（用户核心信息，注册/管理用）
```typescript
export interface User {
  id: number; // 用户ID，系统生成
  account: string; // 账号，必填
  password?: string; // 密码，必填（注册/添加时），编辑时可选
  companyName: string; // 企业名称，必填
  companyContact: string; // 企业联系方式，必填
  roleId?: number; // 角色ID，管理员分配
  status?: 'active' | 'inactive'; // 用户状态，系统默认active
  userType?: 'user' | 'admin'; // 用户类型，系统分配
  createdAt?: string; // 创建时间，系统生成
}
```
- 只包含账号、企业、权限等核心字段，不含补充信息

---

## 2. UserProfile（用户补充信息，个人信息页面用）
```typescript
export interface UserProfile {
  id: number; // 唯一编号，系统生成
  userId: number; // 用户ID，关联User
  nickname?: string; // 昵称，选填
  phone?: string; // 手机号，选填
  email?: string; // 邮箱，选填
  gender?: 'male' | 'female' | 'secret'; // 性别，选填
  birthday?: string; // 生日，选填
  address?: string; // 地址，选填
  avatar?: string; // 头像，选填
}
```
- 只包含补充信息字段，与User一对一关联

---

## 3. Role（角色）
```typescript
export interface Role {
  id: number; // 角色ID
  name: string; // 角色名称（如：管理员、普通用户）
  description?: string; // 角色描述
}
```

---

## 4. Permission（权限）
```typescript
export interface Permission {
  id: number; // 权限ID
  name: string; // 权限名称（如：用户管理）
  code: string; // 权限标识（如：user:manage）
  description?: string; // 权限描述
}
```

---

## 5. 用户-角色-权限关系说明
- User 只存账号、企业、权限等核心信息
- UserProfile 存所有补充信息，通过 userId 关联 User（一对一）
- 一个用户（User）只能有一个角色（Role），由管理员分配
- 一个角色（Role）可以有多个权限（Permission）
- 一个权限（Permission）可以被多个角色拥有


---

## 6. 字段与页面/接口对应说明
- 注册/添加用户页面：只填写User的必填字段
- 个人信息页面：填写User的选填字段
- 用户管理页面：可分配角色（roleId），不可分配管理员账号
- 角色管理页面：增删改查角色，分配权限
- 权限管理页面：增删改查权限

---

> 前端页面只展示/填写用户需要输入的字段，所有id、userId等系统字段均不在页面填写。