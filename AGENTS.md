## 项目定位
本仓库是基于 Yudao / ruoyi-vue-pro 风格的 Java 后端项目。
后端采用模块化架构，业务代码应优先放入对应的 yudao-module-xxx 模块中，并遵循模块内既有的 controller / service / dal / api 分层方式。

## 最高优先级规则
- 先搜索同模块相似实现，再编码。
- 优先复用现有模块、现有包结构、现有基类、现有工具类、现有错误码、现有枚举。
- 不要擅自发明新的分层方式。
- 不要直接把 DO 暴露给 Controller 作为通用入参或出参，优先使用 ReqVO / RespVO。
- 不要在 Service 里堆砌 SQL、HTTP 细节、Redis Key 拼接细节；这些应分别放在 Mapper、Client、RedisDAO 或适当封装中。
- 不要因为当前任务顺手大改无关模块。
- 以最小改动完成需求。

## 开始编码前必须执行
1. 搜索当前业务模块下最相似的 2~3 组代码：
   - Controller
   - Service / ServiceImpl
   - Mapper / DO
   - ReqVO / RespVO / Convert
2. 确认本功能属于哪个模块：
   - system / infra / member / bpm / pay / mall / crm / erp / ai / mp / iot / 自定义模块
3. 确认该功能是否已经有：
   - 错误码定义
   - 枚举
   - 数据权限/租户逻辑
   - Excel 导出
   - 操作日志
   - 单元测试
4. 若仓库已有相似 CRUD，优先按现有结构最小改造，不重新设计。

## 后端分层纪律
### 1. Controller 层
- Controller 只负责接口编排、参数接收、权限注解、结果返回，不承载核心业务逻辑。
- 管理端接口优先放 controller.admin，App 端接口优先放 controller.app。
- Controller 入参优先使用 ReqVO，出参优先使用 RespVO / PageResult / CommonResult。
- 不要在 Controller 直接操作 Mapper。
- 不要在 Controller 里写复杂转换逻辑，优先放到 Convert 中。

### 2. Service 层
- Service 接口定义业务能力，ServiceImpl 实现业务逻辑。
- 业务校验、状态流转、事务边界优先放 Service。
- 多表写入、状态更新、审核流转等逻辑优先放 Service。
- 不要把简单查询也拆出过度抽象；保持与现有模块一致即可。
- 需要事务时，优先在 Service 方法上处理，而不是散落在 Controller。

### 3. DAL 层
- DO 只映射数据库表，不承担接口展示语义。
- Mapper 负责数据库访问，SQL 相关逻辑放在 Mapper/XML/联表查询封装中。
- Redis 缓存访问优先通过 RedisDAO 风格封装，不要在业务 Service 中到处手写 RedisTemplate 细节。
- 查询条件、分页、排序方式优先参考同模块既有写法。

### 4. API 层
- 跨模块调用优先复用已有 api / dto。
- 若当前模块要向其它模块提供能力，优先按现有 api 包风格新增 Api、ApiImpl、ReqDTO、RespDTO。
- 如果没有循环依赖风险，也可复用已有 Service；但不要随意跨模块直接访问内部实现类。

## Yudao 风格硬约束
- 包命名、类命名、方法命名必须对齐同模块旧代码。
- 新增功能时，优先沿用代码生成器产出的目录和类命名风格。
- 错误处理优先复用现有错误码体系，不随意抛裸 RuntimeException。
- 参数校验优先放在 ReqVO 注解和 Service 业务校验中，职责分清。
- 分页、导入导出、数据翻译、数据权限、租户隔离、操作日志等能力，优先复用框架现有机制。
- 敏感字段返回时，优先检查是否已有脱敏或字段权限处理方式。
- 不要绕开权限、租户、数据权限等现有框架能力“直接查库返回”。

## 代码风格纪律
- 先复用 Convert，再补零散 set。
- 先复用现有枚举、常量、工具类，再考虑新增。
- 方法保持单一职责，避免一个 Service 方法同时承担“查、转、存、发消息、导出、审计”全部职责。
- 避免新建与现有命名冲突的 DTO/VO/DO。
- 避免在一个 PR 中混入格式化全仓库、批量 rename、无关 import 整理等噪声改动。

## 完成后必须输出
1. 参考了哪些现有文件
2. 修改了哪些文件
3. 新增功能落在哪个模块、哪个分层
4. 为什么这样分层
5. 是否涉及事务、权限、租户、数据权限、日志、导出等框架能力
6. 运行了哪些校验命令
7. 还有哪些风险点未确认

## 校验要求
完成修改后，至少尝试执行与当前模块相关的一项或多项：
- mvn -pl 对应模块 -am test
- mvn -pl 对应模块 -am package -DskipTests
- mvn test
- mvn package -DskipTests

如果失败，先报告失败原因，不要为了通过编译而大面积修改无关代码。

## 当我纠正你时
如果我指出“这不符合本项目 / Yudao 后端既有写法”，你需要：
1. 先修正当前实现
2. 再把这条经验补充到最合适位置的 AGENTS.md
让后续任务继承
