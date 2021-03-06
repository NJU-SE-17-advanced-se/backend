# backend

南京大学软件学院 2017 级软件工程高级技术方向实践。

## 团队成员

组长：孙逸伦，学号：171250662

组员：勇中坚，学号：171250631

组员：张雨奇，学号：171250658

组员：殷承鉴，学号：171250661

## 项目地址

https://wensun.top/

## 接口说明

http://101.132.35.81:8080/swagger-ui/index.html

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
| 研究领域预测 | [![Task Domain Prediction](https://github.com/NJU-SE-17-advanced-se/backend/workflows/Release%20Domain%20Prediction%20Task%20Service/badge.svg)](https://github.com/NJU-SE-17-advanced-se/backend/actions) | 殷承鉴 |
| 影响力分析   | [![Task Impact Analysis](https://github.com/NJU-SE-17-advanced-se/backend/workflows/Release%20Impact%20Analysis%20Task%20Service/badge.svg)](https://github.com/NJU-SE-17-advanced-se/backend/actions) | 勇中坚 |
| 合作关系分析 | [![Task Partnership Analysis](https://github.com/NJU-SE-17-advanced-se/backend/workflows/Release%20Partnership%20Analysis%20Task%20Service/badge.svg)](https://github.com/NJU-SE-17-advanced-se/backend/actions) | 殷承鉴 |
| 审稿人推荐   | [![Task Reviewer Recommendation](https://github.com/NJU-SE-17-advanced-se/backend/workflows/Release%20Reviewer%20Recommendation%20Task%20Service/badge.svg)](https://github.com/NJU-SE-17-advanced-se/backend/actions) | 殷承鉴 |

### 实体服务

| 服务名称 | 构建状态 | 负责人 |
| -------- | -------- | -------- |
| 机构     | [![Entity Affiliation](https://github.com/NJU-SE-17-advanced-se/backend/workflows/Release%20Affiliation%20Entity%20Service/badge.svg)](https://github.com/NJU-SE-17-advanced-se/backend/actions) | 勇中坚，孙逸伦 |
| 领域     | [![Entity Domain](https://github.com/NJU-SE-17-advanced-se/backend/workflows/Release%20Domain%20Entity%20Service/badge.svg)](https://github.com/NJU-SE-17-advanced-se/backend/actions)      | 勇中坚，孙逸伦 |
| 论文     | [![Entity Paper](https://github.com/NJU-SE-17-advanced-se/backend/workflows/Release%20Paper%20Entity%20Service/badge.svg)](https://github.com/NJU-SE-17-advanced-se/backend/actions)      | 勇中坚，孙逸伦 |
| 出版物    | [![Entity Publication](https://github.com/NJU-SE-17-advanced-se/backend/workflows/Release%20Publication%20Entity%20Service/badge.svg)](https://github.com/NJU-SE-17-advanced-se/backend/actions)      | 殷承鉴，孙逸伦 |
| 学者     | [![Entity Researcher](https://github.com/NJU-SE-17-advanced-se/backend/workflows/Release%20Researcher%20Entity%20Service/badge.svg)](https://github.com/NJU-SE-17-advanced-se/backend/actions)      | 殷承鉴，孙逸伦 |

## 环境配置

- Maven 3.6.3
- Java 8
- Node.js 12.18.3
- Yarn (包管理工具) 1.22.5

## 项目启动

### 首次启动

在项目根目录运行命令，安装项目规范相关工具（依赖于 node.js 和 yarn）：

```shell script
yarn install
```

**一定**要运行此条命令，否则项目规范相关工具无法工作。

### 开发准备

#### 必要

由于使用了 spring cloud，需要首先启动 service-registry，然后启动 config-server，才能确保正确运行。

在命令行运行以下命令，以启动上述服务：

```shell script
yarn run predev
```

#### 可选

##### 服务启动

实体服务和任务服务均提供了命令行启动的方式，具体命令如下：

```shell
yarn run entity-affiliation
yarn run entity-domain
yarn run entity-paper
yarn run entity-publication
yarn run entity-researcher
yarn run task-citation-analysis
yarn run task-domain-prediction
yarn run task-impact-analysis
yarn run task-partnership-analysis
yarn run task-reviewer-recommendation
```

如果需要同时启动所有的实体服务或任务服务，也提供了相应的命令：

```shell
yarn run entity
yarn run task
```

##### 质量分析

执行下述操作需要 shell 环境。如果是 Windows，可以考虑使用 Git Bash。

**Make（推荐）**

如果安装了 GNU Make，可以使用以下命令对全部服务的代码质量进行分析：

```shell
make
make all
```

也可以对单个服务进行分析：

```shell
make entity-affiliation
make entity-domain
make entity-paper
make entity-publication
make entity-researcher
make task-citation-analysis
make task-domain-prediction
make task-impact-analysis
make task-partnership-analysis
make task-reviewer-recommendation
```

**Shell Script**

如果没有安装 GNU Make，可以执行`analysis.sh`来进行质量分析（可以将`.`替换成其他执行 shell 的方法）：

```shell
. analysis.sh entity-affiliation
. analysis.sh entity-domain
. analysis.sh entity-paper
. analysis.sh entity-publication
. analysis.sh entity-researcher
. analysis.sh task-citation-analysis
. analysis.sh task-domain-prediction
. analysis.sh task-impact-analysis
. analysis.sh task-partnership-analysis
. analysis.sh task-reviewer-recommendation
```

目前只支持每次对单个服务进行分析，不能进行批量分析。
