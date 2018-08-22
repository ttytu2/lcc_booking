# lcc_booking(LCC订单服务)

## V1.7.0- 2018-08-13 by 李帅
* [Features]
  1. 新增LC5J_F
* [Bugfixed]
  1. 无
* [Configuration]
  1. 修改ipccOrderSeatsLimit:
       ```
       ipccOrderSeatsLimit: /{"TGAU_F":4,"9C_F":1,"LCJC_F":10,"LCPAM_F":10,"LC5J_F":10}/
       ```
## V1.6.0- 2018-08-07 by 李帅
* [Features]
  1. 新增PAM
* [Bugfixed]
  1. 无
* [Configuration]
  1. 修改ipccOrderSeatsLimit:
  ```
  ipccOrderSeatsLimit: /{"TGAU_F":4,"9C_F":1,"LCJC_F":10,"LCPAM_F":10/}
  ```

## V1.5.0- 2018-07-27 by 李帅
* [Features]
  1. 生单加入剩余座位数限制
* [Bugfixed]
  1. 无
* [Configuration]
  1. 移出orderPassengerLimit配置
  2. 新增ipccOrderSeatsLimit配置
  ```
  ipccOrderSeatsLimit: /{"TGAU_F":5,"9C_F":0,"LCJC_F":10/}
  ```

## V1.4.0- 2018-07-26 by 李帅
* [Features]
  1. 为basefare为0自动+1
* [Bugfixed]
  1. 无
* [Configuration]
  1. 无

## V1.3.2- 2018-07-25 by 李帅
* [Features]
  1. 无
* [Bugfixed]
  1. 9C不能正确返回爬虫查询码
* [Configuration]
  1. 无

## V1.3.1- 2018-07-24 by 李帅
* [Features]
  1. 无 
* [Bugfixed]
  1. 修复LCJC_F匹配行李额问题,按官网价格返回价格
* [Configuration]
  1. 无
  
## V1.3.0- 2018-07-23 by 李帅

* [Features]
  1. 新增ipcc:LCJC_F
    
* [Bugfixed]
  1. 修复9C机场码爬不到数据的问题
    
* [Configuration]
  1. 修改booking:application.yml
```
orderPassengerLimit: /{"TGAU_F":4,"9C_F":2,"LCJC_F":10/}

```

## V1.2.0- 2018-07-10 by 李帅

* [Features]
  1. 优化代码和日志输出
    
* [Bugfixed]
  1. 无
    
* [Configuration]
  1. 无

## V1.1.1- 2018-07-06 by 李帅

* [Features]
  1. 无
    
* [Bugfixed]
  1. 修复处理同程返回航班号错误
    
* [Configuration]
  1. 无

## V1.1.0- 2018-07-05 by 李帅

* [Features]
  1. 各渠道资源生单人数限制分离
    
* [Bugfixed]
  1. 无
    
* [Configuration]
  1. 修改orderPassengerLimit为:
   ```
   /{"TGAU_F":4,"9C_F":2/}
   ```

## V1.0.1- 2018-07-03 by 李帅

* [Features]
  1. 无
    
* [Bugfixed]
  1. 优化代码,移出切换代理逻辑
    
* [Configuration]
  1. 无

## V1.0.0- 2018-07-03 by 李帅

* [Features]
  1. lcc服务拆分(用于升单和验价)
    
* [Bugfixed]
  1. 无
    
* [Configuration]
  1. 无