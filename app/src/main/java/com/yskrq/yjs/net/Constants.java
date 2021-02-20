package com.yskrq.yjs.net;


public class Constants {
  //"http://19a744739c.iok.la:29524"; "http://www.hnhaozhen.com" :";
  //    public static final String url = isDebug() ? "http://19a744739c.iok.la:29524" : "http://qddyzyym.jbtzl.com";";

  /*************网络请求************/
  public static class TransCode {
    //是否启用云技师
    public static final String GetHotelCode = "RM_Others/GetHotelCode";

    ////    关于设置
    //    RM_Others/GetHotelCodePos（wifi）
    //    RM_Others/GetHotelCodePos（纬度）
    //    RM_Others/GetHotelCodePos（经度）

    //更新商品信息
    public static final String UpdateSoundStatue = "BrandOutCon/UpdateSoundStatue";
    //    获取采耳类技师列表
    public static final String SelectServerlistView = "RP_WaterBar/SelectServerlistView";
    //    添加项目
    public static final String TechAddPro = "TPC_AddItem/TechAddPro";
    //    获取该技师的所有项目(用于加项目和修改项目时)
    public static final String getIscalctime = "TPC_AddItem/getIscalctime";
    //    修改项目
    public static final String RelaxTechChangeItem = "RM_Others/RelaxTechChangeItem";
    //    加钟
    public static final String RelaxAddClock = "CA_SetV2_RelaxTech/RelaxAddClock";
    //    上班
    public static final String Techonwork = "RE_technician/Techonwork";
    //    下班
    public static final String Techoffwork = "RE_technician/Techoffwork";
    //    设置信息
    public static final String GetHotelCodePos = "RM_Others/GetHotelCodePos";
    //    获取待打卡和已打卡列表
    public static final String GetRelaxServerList = "CA_SetV2_RelaxTech/GetRelaxServerList";
    //    获取可选房间
    public static final String SelectDataByStatus = "CA_Restaurant/SelectDataByStatus";
    //    确定换房
    public static final String ChangeRoom = "RM_Others/ChangeRoom";
    //    我的钟数
    public static final String GetData = "TPC_AddItem/GetData";
    //    我的售卡
    public static final String CardSaled = "Foot_RelaxTechTc/CardSaled";
    //    我的售卡 - 区间查询
    public static final String TimeCardSaled = "Foot_RelaxTechTc/TimeCardSaled";
    //    开单业绩
    public static final String BillingSaled = "Foot_RelaxTechTc/BillingSaled";
    //    预约列表
    public static final String GetRelaxReservation = "CA_SetV2_RelaxTech/GetRelaxReservation";
    //    已经开过单的房间
    public static final String GetFixingOnUse = "CA_Restaurant/GetFixingOnUse";
    //    获取呼叫服务项目名称
    public static final String GetServiceItem = "BrandOutCon/GetServiceItem";
    //    确定呼叫
    public static final String InsertService = "BrandOutCon/InsertService";
    //    获取左侧类型列表
    public static final String SelectRestaurantType = "CA_Restaurant/SelectRestaurantType";
    //    获取各房间状态数量
    public static final String SelectCountNum = "CA_Restaurant/SelectCountNum";
    //    获取房间
    public static final String SelectRestaurantView = "CA_Restaurant/SelectRestaurantView";
    //    开房权限
    public static final String checkrights = "User/checkrights";
    //    左侧类别列表
    public static final String SelectRelaxInvtType = "CA_Restaurant/SelectRelaxInvtType";
    //    右侧商品、项目明细
    public static final String selectgoods = "CA_SetV2_Web_InvtItem/selectgoods";
    //    技师类型表查询成功
    public static final String SelectType = "RE_technician/SelectType";
    //    获取技师状态成功
    public static final String getWorkingTech = "TPC_AddItem/getWorkingTech";
    //    检测该技师是否有预约
    public static final String CheckTechDestineStatus = "RE_technician/CheckTechDestineStatus";
    //    没有预约就掉
    public static final String GetRelaxCdTechFac = "TPC_AddItem/GetRelaxCdTechFac";
    //    下单（也有权限）
    public static final String AddBillitem = "CA_FoodShop/AddBillitem";
    //    采耳下单（也有权限）
    public static final String AddBillitemBigRelax = "CA_FoodShop/AddBillitemBigRelax";
    //    订单详情
    //    获取已点的商品项目列表
    public static final String selectddan = "CA_PosBillNew_Proc/selectddan";
    //    已付金额
    public static final String getPaidMoney = "CA_Supplement/getPaidMoney";
    //    获取技师名称
    public static final String checkTechNo = "TPC_AddItem/checkTechNo";
    //    退项目（也有权限）
    public static final String DelItem = "RM_Others/DelItem";//  （9001||9002）
    //    订单总价
    public static final String selectzje = "CA_PosBillNew_Proc/selectzje";
    //    加载技师的项目名称
    public static final String getIsCalcTimeItems = "TPC_AddItem/getIsCalcTimeItems";
    //    退商品（也有权限）
    public static final String DelCommodity = "RM_Others/DelCommodity";
    //    采耳技师确定到场
    public static final String UpdateStatueYes = "RP_WaterBar/UpdateStatueYes";
    public static final String LogExeVersion = "RM_Others/LogExeVersion";
    public static final String readlog = "https://hotel16.yskvip.com:9092/RM_Others/readlog";

    public static final String RelaxTechRegistrationID = "RM_Others/RelaxTechRegistrationID";

    //    查看详情（判断此房间是否已开房）
    public static final String selectzhtddan = "CA_PosBillNew_Proc/selectzhtddan";
    //    开房检查
    public static final String SelectDataTai = "CA_Restaurant/SelectDataTai";
    //    开单
    public static final String OpenTheStage = "CA_Restaurant/OpenTheStage";
    //    取消技师
    //    退技师
    public static final String CancelTec = "RM_Others/CancelTec";
    //    上钟
    public static final String brandnoIn = "CA_SetV2_RelaxTech/brandnoIn";
    //    下钟
    public static final String BrandnoOut = "CA_SetV2_RelaxTech/BrandnoOut";
    //    获取封面图
    public static final String getTecCoverPhotos = "TPC_AddItem/getTecCoverPhotos";
    //    获取轮播图
    public static final String getTecPhotos = "TPC_AddItem/getTecPhotos";
    //    上传图片
    public static final String UploadPic = "TPC_Upload/UploadPic";
    //    删除图片
    public static final String deletePic = "TPC_Upload/deletePic";
    //    修改密码
    public static final String UpdatePwd = "https://hotel1.yskvip.com:9092/User/UpdatePwd";
    public static final String UpdatePwd2 = "User/UpdatePwd2";
    //     判断技师是否在上班中
    public static final String GetRelaxTechJobStatus = "CA_SetV2_RelaxTech/GetRelaxTechJobStatus";
    //     获取采耳商品
    public static final String selectgoodsbigrelax = "CA_SetV2_Web_InvtItem/selectgoodsbigrelax";
    //    更新播报状态
    public static final String UpdateRelaxStatus = "CA_SetV2_RelaxTech/UpdateRelaxStatus";
    //    更新播报状态2
    public static final String UpdateRelaxCountDown = "CA_SetV2_RelaxTech/UpdateRelaxCountDown";

  }
}
