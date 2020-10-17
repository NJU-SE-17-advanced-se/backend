# backend

南京大学软件学院 2017 级软件工程高级技术方向实践。

## 团队成员

组长：孙逸伦，学号：171250662

组员：勇中坚，学号：171250631

组员：张雨奇，学号：171250658

组员：殷承鉴，学号：171250661

## 项目地址 (WIP)

https://wensun.top/

## 接口说明 (WIP)

文档地址：https://wensun.top/api/swagger-ui/index.html

## 构建状态

### 基础设施

| 服务名称 | 构建状态                                                     | 负责人 |
| -------- | ------------------------------------------------------------ | ------ |
| 注册中心 | [![Service Registry](https://github.com/NJU-SE-17-advanced-se/backend/workflows/Release%20Service%20Registry/badge.svg)](https://github.com/NJU-SE-17-advanced-se/backend/actions) | 孙逸伦 |
| 配置服务 | [![Config Server](https://github.com/NJU-SE-17-advanced-se/backend/workflows/Release%20Config%20Server/badge.svg)](https://github.com/NJU-SE-17-advanced-se/backend/actions) | 孙逸伦 |
| 服务网关 | [![API Gateway](https://github.com/NJU-SE-17-advanced-se/backend/workflows/Release%20API%20Gateway/badge.svg)](https://github.com/NJU-SE-17-advanced-se/backend/actions) | 孙逸伦 |

### 任务服务

| 服务名称     | 构建状态                                                     | 负责人 |
| ------------ | ------------------------------------------------------------ | ------ |
| 引用分析     | [![Task Citation Analysis](https://github.com/NJU-SE-17-advanced-se/backend/workflows/Release%20Citation%20Analysis%20Task%20Service/badge.svg)](https://github.com/NJU-SE-17-advanced-se/backend/actions) | 勇中坚 |
| 研究方向预测 | WIP                                                          | 勇中坚 |
| 影响力分析   | [![Task Impact Analysis](https://github.com/NJU-SE-17-advanced-se/backend/workflows/Release%20Impact%20Analysis%20Task%20Service/badge.svg)](https://github.com/NJU-SE-17-advanced-se/backend/actions) | 勇中坚 |
| 合作关系分析 | [![Task Partnership Analysis](https://github.com/NJU-SE-17-advanced-se/backend/workflows/Release%20Partnership%20Analysis%20Task%20Service/badge.svg)](https://github.com/NJU-SE-17-advanced-se/backend/actions) | 殷承鉴 |
| 审稿人推荐   | [![Task Reviewer Recommendation](https://github.com/NJU-SE-17-advanced-se/backend/workflows/Release%20Reviewer%20Recommendation%20Task%20Service/badge.svg)](https://github.com/NJU-SE-17-advanced-se/backend/actions) | 殷承鉴 |

### 实体服务

| 服务名称 | 构建状态 |
| -------- | -------- |
| 机构     | WIP      |
| 领域     | WIP      |
| 论文     | WIP      |
| 出版物    | WIP      |
| 学者     | WIP      |

## 环境配置

建议环境配置：

- Maven 3.6.3
- Java 8
- Node.js 12.18.3
- Yarn (包管理工具) 1.22.5

然后在项目根目录运行命令，安装项目规范相关工具（依赖于 node.js 和 yarn）：

```shell script
yarn install
```

**一定**要运行此条命令，否则项目规范相关工具无法工作。
