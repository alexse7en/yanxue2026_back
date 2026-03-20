---
name: yudao-backend-module
description: 在 Yudao 后端项目中新增或改造 controller、service、mapper、DO、VO、DTO、Convert、错误码、权限与事务逻辑时使用。必须先搜索同模块相似实现，再按现有分层最小改造，不要自创新分层或直接绕开框架机制。
---

# Yudao Backend Module Skill

## 适用场景
- 新增一个业务功能的后端接口
- 补全某模块的 CRUD / 审核 / 状态流转 / 导入导出
- 根据前端页面补后端 controller + service + mapper + vo
- 在现有模块中增加跨表查询、分页、详情、更新逻辑

## 不适用场景
- 仅改一个字段文案
- 仅改一个 SQL 常量
- 基础设施级大重构
- 中间件、部署、网关、微服务拆分改造

## 固定工作流
### 第一步：识别模块与分层
先判断功能属于哪个模块：
- system / infra / member / bpm / pay / mall / crm / erp / ai / mp / iot / 自定义模块

再判断改动涉及哪些层：
- controller.admin / controller.app
- service / service.impl
- dal.dataobject / dal.mysql / dal.redis
- api / dto
- enums / convert / errorcode / framework

### 第二步：搜索参考实现
开始编码前，至少搜索：
1. 同模块最相似的 Controller 2 个
2. 同模块最相似的 Service / ServiceImpl 2 个
3. 同模块最相似的 Mapper / DO 2 个
4. 同模块最相似的 ReqVO / RespVO / Convert 1~2 组
5. 若涉及跨模块调用，再搜索已有 Api / DTO

### 第三步：提炼现有模式
编码前先总结：
- 包路径和命名方式
- Controller 注解、权限注解、日志注解写法
- ReqVO / RespVO 命名和字段风格
- Service 方法命名和事务边界
- Mapper 查询和分页风格
- 错误码、枚举、状态字段风格
- 是否已有导出、字典翻译、数据权限、租户处理模式

### 第四步：实施规则
编码时必须遵守：
- Controller 不直接操作 Mapper
- DO 不直接作为通用接口出入参
- 转换逻辑优先放 Convert
- 业务主逻辑优先放 Service
- SQL 访问优先放 Mapper
- Redis 访问优先放 RedisDAO 封装
- 跨模块能力优先复用 api/dto 或现有 service
- 不擅自改公共返回结构、权限模型、租户模型

### 第五步：自检
完成后检查：
- 是否遵循现有包结构
- 是否复用了现有错误码、枚举、常量
- 是否遗漏权限、租户、数据权限、日志、事务
- 是否把展示层逻辑误放进 DO / Mapper
- 是否产生不必要的新抽象
- 是否误改无关模块

### 第六步：结果汇报
最终必须输出：
1. 参考文件
2. 修改文件
3. 分层设计说明
4. 涉及的框架能力
5. 校验命令与结果
6. 风险点与待确认点

## 质量标准
好的实现应该：
- 看起来像仓库原生代码
- 类名、包名、方法名与同模块高度一致
- 没有把责任层次打乱
- 没有绕开 Yudao 既有能力硬写一套
- 后续维护者能一眼看出这是按现有模块扩展出来的
