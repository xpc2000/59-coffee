#### 前后端连接API

---
> 使用axios

>管理端

|接口 名称| 功能| 前端给出|所需后端数据 |完成情况|
|-|-|-|-|-|
|AdminLogin|管理员登录 |{用户名id，密码password}loginForm对象 |"ok"登录成功/其他登录失败 |√|
|SearchStore|通过门店ID查找门店信息 |筛选条件:门店账号ID  (为空字符串则无筛选条件)  |筛选到的门店数 , 符合条件的门店列表 |√|
|SearchFood|通过餐品名称查找餐品信息 |筛选条件:餐品名称 foodName  (为空字符串则无筛选条件)  |筛选到的餐品数 , 符合条件的餐品列表 |√|
|SelectStore(暂时不用)|更新门店名称选择器 | 无| 筛选到的option列表数 , 全部{value，label}的option对象列表|
|SearchOrder|通过门店ID，起始时间,结束时间 <br>查找订单流水 |筛选条件:{门店账号storeID 起始时间date1,结束时间date2}Filter对象 (为空字符串则无筛选条件) |筛选到的订单数 , 符合条件的订单列表 |
|storeNew| 新建门店功能|{id, password, name, address, phone}editForm对象 | 返回"ok"则成功新建|√|
|storeEdit| 修改门店功能| {password, name, address, phone}|返回"ok"则成功修改 |√|
|storeDelete| 删除门店功能|id |返回"ok"则成功删除 |√|
|foodNew| 新建餐品功能| {foodID，foodName，foodType，foodMoney}editData|返回"ok"则成功新建 |√|
|foodEdit| 修改餐品功能| {foodName，foodType，foodMoney}|返回"ok"则成功修改 |√|
|foodDelete| 删除餐品功能|foodID | 返回"ok"则成功删除|√|

>客户端(包含门店和客户)

|接口 名称| 功能| 前端给出|所需后端数据 |完成情况|
|-|-|-|-|-|
|GuestLogin|客户登录|{username, password} |返回"ok"则成功登录|√|
|ShopAdminLogin|门店登录|{username, password} |返回"ok"则成功登录|√|
|GuestRegister|客户注册|{id, password, passwordConfirm, name, phone} |返回"ok"则成功注册|√|
|getMessage|客户个人信息修改初始化|id |返回{password, passwordConfirm, name, phone, address ,delivery_address}|√|
|GuestRegister|客户个人信息修改|{id, password, passwordConfirm, name, phone, address ,delivery_address}|返回"ok"则成功修改|√|
|SearchStock| 获取所有未下架商品及其库存信息 |筛选条件:餐品名称 name  (为空字符串则无筛选条件)  | 返回筛选到的(商品+库存)的列表,即每个商品均带有相应的库存信息
|SearchDownShelfStockw|无| 获取所有已下架商品及其库存信息 | 返回筛选到的(商品+库存)的列表,即每个商品均带有相应的库存信息
|StockEdit| 修改库存信息 |{idFood,num} |返回"ok"则成功修改库存
|StockDownShelf| 下架分店商品 |{idFood} |返回"ok"表示成功下架 (设置sellOut=1)
|StockUpShelf| 上架分店商品 |{idFood} |返回"ok"表示成功上架 (设置sellOut=0)
|SearchShopOrder| 获取门店订单信息| 无传入参数 |返回门店的订单列表
|Deliver| 进行发货 |{id} |返回"ok"表示发货成功
|OrderDetail| 获取订单详情 |{id}|获取订单id=id的订单详情