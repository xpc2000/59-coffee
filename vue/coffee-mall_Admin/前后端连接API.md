#### 前后端连接API

---
> 使用axios

|接口 名称| 功能| 前端给出|所需后端数据 |
|-|-|-|-|
|AdminLogin|管理员登录 |{用户名username，密码password}loginForm对象 |"ok"登录成功/其他登录失败 |
|SearchStore|通过门店ID查找门店信息 |筛选条件:门店账号storeID  (为空字符串则无筛选条件)  |符合条件的门店列表 |
|SearchFood|通过餐品名称查找餐品信息 |筛选条件:餐品名称 foodName  (为空字符串则无筛选条件)  |符合条件的餐品列表 |
|SelectStore(暂时不用)|更新门店名称选择器 | 字符串getName(占位用，无实际用处)| 全部{value，label}的option对象列表|
|SearchOrder|通过门店ID，起始时间,结束时间 <br>查找订单流水 |筛选条件:{门店账号storeID 起始时间date1,结束时间date2}Filter对象 (为空字符串则无筛选条件) |符合条件的订单列表 |
|| 新建门店功能| | |
|| 删除门店功能| | |
|| 修改门店功能| | |
|| 新建餐品功能| | |
|| 删除餐品功能| | |
|| 修改餐品功能| | |