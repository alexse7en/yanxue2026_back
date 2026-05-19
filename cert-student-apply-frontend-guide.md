# 学生证书申请前端联调指南

## 1. 当前业务流程

当前学生证书功能已经调整为“三表模型”流程，前端联调时请按下面顺序处理：

1. 会员用户上传 Excel
2. 前端调用 `parse` 接口
3. 后端解析 Excel，生成一条“申请批次”+ 多条“申请明细”
4. 前端拿到解析结果后，展示给用户预览/修改
5. 用户确认无误后，前端调用 `submit`
6. 申请进入“待审核”
7. 管理员在后台审核
8. 仅当审核通过后，后端才会：
   - 扣减会员单位 `token_balance`
   - 生成正式证书编号
   - 生成证书图片
   - 写入正式证书表
   - 生成批量下载 ZIP

注意：

- `submit` 只是“提交审核”，不是“立即生成证书”
- 正式证书不是在 `parse` 阶段生成
- 正式证书也不是在用户点击 `submit` 时生成

## 2. 当前后端接口

### 2.1 会员侧接口

#### 1. 分页查询我的申请批次

`GET /admin-api/yw/yw-cert-student-apply/page-my`

用途：

- 查询当前登录会员用户自己的证书申请批次

请求参数：

- `pageNo`
- `pageSize`
- `keyword`：当前后端仅用于模糊匹配 `applyNo`

返回示例：

```json
{
  "code": 0,
  "data": {
    "list": [
      {
        "id": 1,
        "applyNo": "CERTAPPLY1744456789000",
        "applyStatus": 1,
        "uploadFilePath": "https://oss.example.com/cert/student/demo.xlsx",
        "fileType": "xlsx",
        "parseStatus": 1,
        "parseError": null,
        "parseCount": 3,
        "downloadUrl": null,
        "auditRemark": null,
        "auditTime": null,
        "auditorId": null,
        "createTime": "2026-04-12 10:00:00",
        "updateTime": "2026-04-12 10:05:00",
        "details": null
      }
    ],
    "total": 1
  },
  "msg": ""
}
```

说明：

- 列表页默认只返回批次信息
- `details` 在分页场景通常可忽略
- `downloadUrl` 只有审核通过且证书生成成功后才会有值

#### 2. 查询单条申请详情

`GET /admin-api/yw/yw-cert-student-apply/get?id={id}`

用途：

- 查询当前登录会员用户自己的某一条申请批次详情
- 返回批次信息 + 明细列表

返回示例：

```json
{
  "code": 0,
  "data": {
    "id": 1,
    "applyNo": "CERTAPPLY1744456789000",
    "applyStatus": 0,
    "uploadFilePath": "https://oss.example.com/cert/student/demo.xlsx",
    "fileType": "xlsx",
    "parseStatus": 1,
    "parseError": null,
    "parseCount": 2,
    "downloadUrl": null,
    "auditRemark": null,
    "auditTime": null,
    "auditorId": null,
    "createTime": "2026-04-12 10:00:00",
    "updateTime": "2026-04-12 10:02:00",
    "details": [
      {
        "id": 11,
        "studentName": "张三",
        "idCard": "440101201201010011",
        "schoolName": "广州市第一小学",
        "className": "三年级1班",
        "courseName": "自然生态研学课程",
        "courseHours": "8",
        "courseProvider": "广东自然生态研学基地",
        "certDate": "2026-04-10",
        "stampUnit": "广东自然生态研学基地"
      },
      {
        "id": 12,
        "studentName": "李四",
        "idCard": "440101201201010022",
        "schoolName": "广州市第一小学",
        "className": "三年级1班",
        "courseName": "自然生态研学课程",
        "courseHours": "8",
        "courseProvider": "广东自然生态研学基地",
        "certDate": "2026-04-10",
        "stampUnit": "广东自然生态研学基地"
      }
    ]
  },
  "msg": ""
}
```

#### 3. 解析 Excel

`POST /admin-api/yw/yw-cert-student-apply/parse`

用途：

- 上传文件到 OSS 后，调用该接口解析 Excel
- 后端会生成一条草稿批次，并把解析出的学生明细一起返回

请求体：

```json
{
  "filePath": "https://oss.example.com/cert/student/demo.xlsx",
  "fileType": "xlsx"
}
```

返回说明：

- 成功时返回一条草稿申请批次
- `parseStatus=1`
- `details` 中带回解析结果，前端可直接回填表格

失败时：

- 仍会生成一条草稿批次
- `parseStatus=2`
- `parseError` 会返回失败原因

#### 4. 提交审核申请

`POST /admin-api/yw/yw-cert-student-apply/submit`

用途：

- 用户在前端确认/修改解析结果后提交审核

请求体：

```json
{
  "id": 1,
  "details": [
    {
      "id": 11,
      "studentName": "张三",
      "idCard": "440101201201010011",
      "schoolName": "广州市第一小学",
      "className": "三年级1班",
      "courseName": "自然生态研学课程",
      "courseHours": "8",
      "courseProvider": "广东自然生态研学基地",
      "certDate": "2026-04-10",
      "stampUnit": "广东自然生态研学基地"
    },
    {
      "id": 12,
      "studentName": "李四",
      "idCard": "440101201201010022",
      "schoolName": "广州市第一小学",
      "className": "三年级1班",
      "courseName": "自然生态研学课程",
      "courseHours": "8",
      "courseProvider": "广东自然生态研学基地",
      "certDate": "2026-04-10",
      "stampUnit": "广东自然生态研学基地"
    }
  ]
}
```

返回：

```json
{
  "code": 0,
  "data": 1,
  "msg": ""
}
```

说明：

- `id` 是申请批次 ID
- `details` 是前端最终确认后的学生明细
- 后端会先删掉旧明细，再按这次提交内容重写明细
- 成功后批次状态会变成 `applyStatus=1`

### 2.2 管理员审核接口

#### 1. 分页查询申请

`GET /admin-api/yw/yw-cert-student-apply/page`

请求参数：

- `pageNo`
- `pageSize`
- `applyNo`
- `applyStatus`
- `audited`
  - `0` 未审核
  - `1` 已审核
- `beginCreateTime`
- `endCreateTime`

返回结构与 `page-my` 一致，都是批次列表。

#### 2. 审核申请

`POST /admin-api/yw/yw-cert-student-apply/audit`

请求体：

```json
{
  "id": 1,
  "applyStatus": 2,
  "auditRemark": "审核通过"
}
```

审核状态说明：

- `2` 审核通过
- `3` 审核拒绝

审核通过后，后端会立刻执行：

1. 检查会员单位 `token_balance`
2. 按学生条数扣减次数
3. 生成正式证书编号
4. 生成证书图片
5. 上传 OSS
6. 写入正式证书表
7. 生成 ZIP 下载地址并回写到申请批次

## 3. 当前前端应该使用的字段

### 3.1 申请批次字段

`YwCertStudentApplyRespVO`

- `id`
- `applyNo`
- `applyStatus`
- `uploadFilePath`
- `fileType`
- `parseStatus`
- `parseError`
- `parseCount`
- `downloadUrl`
- `auditRemark`
- `auditTime`
- `auditorId`
- `createTime`
- `updateTime`
- `details`

### 3.2 明细字段

`YwStudentApplyDetailRespVO`

- `id`
- `studentName`
- `idCard`
- `schoolName`
- `className`
- `courseName`
- `courseHours`
- `courseProvider`
- `certDate`
- `stampUnit`

## 4. 状态值说明

### 4.1 申请状态 `applyStatus`

- `0` 草稿
- `1` 待审核
- `2` 审核通过
- `3` 审核拒绝

建议前端显示文案：

- `0` 草稿
- `1` 待审核
- `2` 已通过
- `3` 已拒绝

### 4.2 解析状态 `parseStatus`

- `0` 未解析
- `1` 解析成功
- `2` 解析失败

建议前端显示文案：

- `0` 未解析
- `1` 解析成功
- `2` 解析失败

## 5. 与旧版接口说明的差异

旧版文档里提到的这些字段，当前后端已经不再按那个语义返回：

- `filePath`
- `certStatus`
- `certNo`
- `certName`
- `certUrl`
- `finishTime`

当前真实实现中：

- 上传文件字段名是 `uploadFilePath`
- 申请批次不直接返回正式证书编号
- 申请批次也不直接返回单张证书图片地址
- 批次完成后只会在申请批次上回写 `downloadUrl`

如果前端还需要：

- 单个学生的正式证书编号
- 单个学生的正式证书图片地址
- 生成完成时间

则需要下一步再补一个“正式证书查询接口”。

## 6. 前端联调建议

### 6.1 会员端页面建议流程

1. 上传 Excel 到 OSS
2. 调用 `/parse`
3. 用返回的 `details` 回填表格
4. 用户可修改表格内容
5. 调用 `/submit`
6. 提交后回到列表页
7. 在列表页根据 `applyStatus` 展示审核进度
8. 当 `applyStatus=2` 且 `downloadUrl` 不为空时，显示“批量下载证书”

### 6.2 管理端审核页建议流程

1. 调用 `/page`
2. 筛选 `applyStatus=1` 的待审核批次
3. 点击详情时调用 `/get?id=...`
4. 查看学生明细后调用 `/audit`

注意：

- 当前 `/get` 是按当前登录用户过滤的逻辑，适合会员端
- 如果管理员审核详情页需要查看任意批次详情，建议后续补一个管理员专用详情接口

## 7. 常见错误提示

可能遇到的典型提示：

- `请先完成 Excel 解析后再提交生成`
- `仅支持 xls/xlsx 文件解析`
- `会员单位证书生成次数不足`
- `申请批次不存在`
- `仅待审核的申请批次可审核`

## 8. 后续建议

如果前端页面还需要展示“每个学生生成后的证书编号/证书图片”，建议后端下一步补：

1. 正式证书分页接口
2. 按申请批次查询正式证书明细接口
3. 单个学生证书预览接口
