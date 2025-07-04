# 接口文档（全量优化版）

## 1. 用户注册

### 【公开接口】
- 企业用户注册
- 接口地址：`/api/register`
- 请求方式：POST
- 请求头：Content-Type: application/json

#### 请求参数
| 名称           | 类型   | 必填 | 说明         |
| -------------- | ------ | ---- | ------------ |
| account        | string | 是   | 账号         |
| password      | string | 是   | 密码         |
| companyName    | string | 是   | 企业名称     |
| companyContact | string | 是   | 企业联系方式 |
| verifyCode     | string | 是   | 验证码       |

#### 示例请求
```json
{
  "account": "user001",
  "password": "123456",
  "companyName": "XX科技公司",
  "companyContact": "13800000000",
  "verifyCode": "1234"
}
```

#### 返回参数
| 名称 | 类型   | 说明         |
| ---- | ------ | ------------ |
| code | number | 状态码，0成功 |
| msg  | string | 消息         |

#### 示例响应
```json
{
  "code": 0,
  "msg": "注册成功"
}
```

---

## 2. 用户登录

### 【公开接口】
- 用户登录，返回token和用户信息
- 接口地址：`/api/login`
- 请求方式：POST
- 请求头：Content-Type: application/json

#### 请求参数
| 名称    | 类型   | 必填 | 说明   |
| ------- | ------ | ---- | ------ |
| account | string | 是   | 账号   |
| password| string | 是   | 密码   |

#### 示例请求
```json
{
  "account": "admin",
  "password": "123456"
}
```

#### 返回参数
| 名称     | 类型           | 说明                       |
| -------- | -------------- | -------------------------- |
| code     | number         | 状态码，0成功              |
| msg      | string         | 消息                       |
| token    | string         | JWT令牌                    |
| userInfo | LoginUserInfo  | 用户信息，见下表           |

##### userInfo 字段说明（LoginUserInfo 类型）
| 字段         | 类型    | 说明           | 来源         |
| ------------ | ------- | -------------- | ------------ |
| id           | number  | 用户ID         | User         |
| account      | string  | 账号           | User         |
| nickname     | string  | 昵称           | UserProfile  |
| userType     | string  | 用户类型       | User         |
| companyName  | string  | 企业名称       | User         |
| companyContact| string | 企业联系方式   | User         |
| status       | string  | 状态           | User         |
| phone        | string  | 手机号         | UserProfile  |
| email        | string  | 邮箱           | UserProfile  |
| gender       | string  | 性别           | UserProfile  |
| avatar       | string  | 头像           | UserProfile  |
| createdAt    | string  | 注册时间       | User         |
| lastLogin    | string  | 最后登录时间   | 扩展         |
| remark       | string  | 备注           | 扩展         |
| role         | string  | 角色名称       | 角色         |
| permissions  | array   | 权限标识数组   | 权限         |

#### 示例响应
```json
{
  "code": 0,
  "msg": "登录成功",
  "token": "xxx.yyy.zzz",
  "userInfo": {
    "id": 1,
    "account": "admin",
    "nickname": "管理员",
    "userType": "admin",
    "companyName": "XX科技公司",
    "companyContact": "13800000000",
    "status": "active",
    "role": "管理员",
    "permissions": ["user:manage", "role:manage"],
    "createdAt": "2024-01-01 00:00:00",
    "lastLogin": "2024-05-01 12:00:00"
  }
}
```

---

## 3. 获取个人信息

### 【需登录】
- 获取当前登录用户的个人信息
- 接口地址：`/api/user/profile`
- 请求方式：GET
- 请求头：Authorization: Bearer {token}

#### 返回参数
| 名称     | 类型   | 说明                       |
| -------- | ------ | -------------------------- |
| code     | number | 状态码，0成功              |
| userInfo | object | 用户信息（见实体类文档）   |

#### 示例响应
```json
{
  "code": 0,
  "userInfo": {
    "id": 2,
    "account": "user001",
    "nickname": "小明",
    "phone": "13900000000",
    "email": "a@b.com",
    "gender": "male",
    "companyName": "XX科技公司",
    "companyContact": "13800000000",
    "status": "active"
  }
}
```

---

## 4. 更新个人信息

### 【需登录】
- 更新当前登录用户的个人信息
- 接口地址：`/api/user/profile`
- 请求方式：PUT
- 请求头：Authorization: Bearer {token}

#### 请求参数
| 名称     | 类型   | 必填 | 说明   |
| -------- | ------ | ---- | ------ |
| nickname | string | 否   | 昵称   |
| phone    | string | 否   | 手机号 |
| email    | string | 否   | 邮箱   |
| gender   | string | 否   | 性别   |

#### 示例请求
```json
{
  "nickname": "小明",
  "phone": "13900000000",
  "email": "a@b.com",
  "gender": "male"
}
```

#### 返回参数
| 名称 | 类型   | 说明         |
| ---- | ------ | ------------ |
| code | number | 状态码，0成功 |
| msg  | string | 消息         |

#### 示例响应
```json
{
  "code": 0,
  "msg": "更新成功"
}
```

---

## 5. 修改密码

### 【需登录】
- 修改当前登录用户的密码
- 接口地址：`/api/user/password`
- 请求方式：PUT
- 请求头：Authorization: Bearer {token}

#### 请求参数
| 名称        | 类型   | 必填 | 说明   |
| ----------- | ------ | ---- | ------ |
| oldPassword | string | 是   | 旧密码 |
| newPassword | string | 是   | 新密码 |

#### 示例请求
```json
{
  "oldPassword": "123456",
  "newPassword": "654321"
}
```

#### 返回参数
| 名称 | 类型   | 说明         |
| ---- | ------ | ------------ |
| code | number | 状态码，0成功 |
| msg  | string | 消息         |

#### 示例响应
```json
{
  "code": 0,
  "msg": "密码修改成功"
}
```

---

## 6. 用户管理（管理员专用）

### 6.1 获取用户列表
- 【需登录】【管理员专用】
- 接口地址：`/api/users`
- 请求方式：GET
- 请求头：Authorization: Bearer {token}

#### 查询参数
| 名称         | 类型   | 必填 | 说明         |
| ------------ | ------ | ---- | ------------ |
| account      | string | 否   | 账号，模糊查询 |
| companyName  | string | 否   | 企业名称，模糊查询 |
| nickname     | string | 否   | 昵称，模糊查询 |
| phone        | string | 否   | 手机号       |
| status       | string | 否   | 状态         |
| page         | number | 否   | 页码，默认1  |
| pageSize     | number | 否   | 每页数量，默认10 |

#### 返回参数
| 名称     | 类型   | 说明         |
| -------- | ------ | ------------ |
| code     | number | 状态码，0成功 |
| userList | array  | 用户列表     |
| total    | number | 总数量       |

#### 示例响应
```json
{
  "code": 0,
  "userList": [
    {
      "id": 1,
      "account": "admin",
      "nickname": "管理员",
      "companyName": "XX科技公司",
      "companyContact": "13800000000",
      "userType": "admin",
      "phone": "13800138000",
      "email": "admin@example.com",
      "gender": "male",
      "status": "active",
      "createdAt": "2024-01-01 00:00:00",
      "lastLogin": "2024-05-01 12:00:00",
      "remark": "系统管理员"
    }
  ],
  "total": 1
}
```

### 6.2 创建用户
- 【需登录】【管理员专用】
- 接口地址：`/api/users`
- 请求方式：POST
- 请求头：Authorization: Bearer {token}

#### 请求参数
| 名称           | 类型   | 必填 | 说明         |
| -------------- | ------ | ---- | ------------ |
| account        | string | 是   | 账号         |
| password       | string | 是   | 密码         |
| companyName    | string | 是   | 企业名称     |
| companyContact | string | 是   | 企业联系方式 |
| nickname       | string | 否   | 昵称         |
| phone          | string | 否   | 手机号       |
| email          | string | 否   | 邮箱         |
| gender         | string | 否   | 性别         |
| status         | string | 否   | 状态         |
| remark         | string | 否   | 备注         |

#### 示例请求
```json
{
  "account": "user002",
  "password": "123456",
  "companyName": "YY科技公司",
  "companyContact": "13900000000"
}
```

#### 返回参数
| 名称 | 类型   | 说明         |
| ---- | ------ | ------------ |
| code | number | 状态码，0成功 |
| msg  | string | 消息         |

#### 示例响应
```json
{
  "code": 0,
  "msg": "用户创建成功"
}
```

### 6.3 更新用户信息
- 【需登录】【管理员专用】
- 接口地址：`/api/users/{id}`
- 请求方式：PUT
- 请求头：Authorization: Bearer {token}

#### 请求参数
| 名称           | 类型   | 必填 | 说明         |
| -------------- | ------ | ---- | ------------ |
| id             | string | 是   | 用户ID，路径参数 |
| nickname       | string | 否   | 昵称         |
| phone          | string | 否   | 手机号       |
| email          | string | 否   | 邮箱         |
| gender         | string | 否   | 性别         |
| companyName    | string | 否   | 企业名称     |
| companyContact | string | 否   | 企业联系方式 |
| status         | string | 否   | 状态         |
| remark         | string | 否   | 备注         |

#### 示例请求
```json
{
  "nickname": "新昵称",
  "phone": "13900000001"
}
```

#### 返回参数
| 名称 | 类型   | 说明         |
| ---- | ------ | ------------ |
| code | number | 状态码，0成功 |
| msg  | string | 消息         |

#### 示例响应
```json
{
  "code": 0,
  "msg": "用户信息更新成功"
}
```

### 6.4 删除用户
- 【需登录】【管理员专用】
- 接口地址：`/api/users/{id}`
- 请求方式：DELETE
- 请求头：Authorization: Bearer {token}

#### 请求参数
| 名称 | 类型   | 必填 | 说明         |
| ---- | ------ | ---- | ------------ |
| id   | string | 是   | 用户ID，路径参数 |

#### 返回参数
| 名称 | 类型   | 说明         |
| ---- | ------ | ------------ |
| code | number | 状态码，0成功 |
| msg  | string | 消息         |

#### 示例响应
```json
{
  "code": 0,
  "msg": "用户删除成功"
}
```

### 6.5 分配用户角色
- 【需登录】【管理员专用】
- 接口地址：`/api/users/{id}/roles`
- 请求方式：PUT
- 请求头：Authorization: Bearer {token}

#### 请求参数
| 名称   | 类型  | 必填 | 说明         |
| ------ | ----- | ---- | ------------ |
| id     | string| 是   | 用户ID，路径参数 |
| roleId | string| 是   | 角色ID       |

#### 示例请求
```json
{
  "roleId": "2"
}
```

#### 返回参数
| 名称 | 类型   | 说明         |
| ---- | ------ | ------------ |
| code | number | 状态码，0成功 |
| msg  | string | 消息         |

#### 示例响应
```json
{
  "code": 0,
  "msg": "角色分配成功"
}
```

---

## 7. 角色与权限管理（管理员专用）

### 7.1 获取角色列表
- 【需登录】【管理员专用】
- 接口地址：`/api/roles`
- 请求方式：GET
- 请求头：Authorization: Bearer {token}

#### 返回参数
| 名称     | 类型   | 说明         |
| -------- | ------ | ------------ |
| code     | number | 状态码，0成功 |
| roleList | array  | 角色列表     |

#### 示例响应
```json
{
  "code": 0,
  "roleList": [
    { "id": 1, "name": "管理员", "description": "系统最高权限" },
    { "id": 2, "name": "普通用户", "description": "仅基础功能" }
  ]
}
```

### 7.2 创建/更新/删除角色
- 参考用户管理接口，参数类似

### 7.3 获取权限列表
- 【需登录】【管理员专用】
- 接口地址：`/api/permissions`
- 请求方式：GET
- 请求头：Authorization: Bearer {token}

#### 返回参数
| 名称           | 类型   | 说明         |
| -------------- | ------ | ------------ |
| code           | number | 状态码，0成功 |
| permissionList | array  | 权限列表     |

#### 示例响应
```json
{
  "code": 0,
  "permissionList": [
    { "id": 1, "name": "用户管理", "code": "user:manage", "description": "可以管理用户" },
    { "id": 2, "name": "角色管理", "code": "role:manage", "description": "可以管理角色" }
  ]
}
```

### 7.4 分配角色权限
- 【需登录】【管理员专用】
- 接口地址：`/api/roles/{id}/permissions`
- 请求方式：PUT
- 请求头：Authorization: Bearer {token}

#### 请求参数
| 名称         | 类型  | 必填 | 说明         |
| ------------ | ----- | ---- | ------------ |
| id           | string| 是   | 角色ID，路径参数 |
| permissionIds| array | 是   | 权限ID数组   |

#### 示例请求
```json
{
  "permissionIds": [1, 2]
}
```

#### 返回参数
| 名称 | 类型   | 说明         |
| ---- | ------ | ------------ |
| code | number | 状态码，0成功 |
| msg  | string | 消息         |

#### 示例响应
```json
{
  "code": 0,
  "msg": "权限分配成功"
}
```

---

## 8. 通用说明

### 状态码说明
| 状态码 | 说明         |
| ------ | ------------ |
| 0      | 成功         |
| 1001   | 参数错误     |
| 1002   | 未授权       |
| 1003   | 权限不足     |
| 1004   | 资源不存在   |
| 1005   | 服务器内部错误 |
| 1006   | 业务逻辑错误 |

### 常见错误示例
```json
{
  "code": 1003,
  "msg": "权限不足"
}
```

---
如有疑问请联系开发者。 