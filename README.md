# Pre-Course Assignment
## Problem
```text

题目描述
商品列表展示
其中每一项商品包含了如下信息: SKU, 名称，价格，库存量，图片地址


业务规则
1.在控制台中展示所有接口中 products 返回的商品
2.一条商品对应多个商品库存信息，商品的库存等于各个区域库存的总和
3.商品的价格依据商品类型，商品类型有普通商品和稀缺商品
3.1 如果是普通商品 (type 为 NORMAL) 商品真实价格就等于原价格
3.2 稀缺商品  (type 为 HIGH_DEMAND) 的价格取决于库存
如果库存大于100，则价格等于原价，而当
库存小于等于100且大于30，价格为原价的120%
如果库存小于等于30，价格为原价的150%

数据源
下载库
https://github.com/TW-Android-Junior-Training/kotlin-json-server
根据 README 中的说明启动 Mock-server
说明
http://localhost:3000/products 返回商品列表
http://localhost:3000/inventories 返回商品库存列表
两组数据通过 SKU 而非 id 进行匹配
```
