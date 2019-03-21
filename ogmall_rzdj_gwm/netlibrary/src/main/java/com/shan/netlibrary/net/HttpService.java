package com.shan.netlibrary.net;

import com.shan.netlibrary.bean.AddressBean;
import com.shan.netlibrary.bean.AppaccountInfoBean;
import com.shan.netlibrary.bean.AppeditPersonalDataBean;
import com.shan.netlibrary.bean.ApplogoutBean;
import com.shan.netlibrary.bean.AppresetPasswordBean;
import com.shan.netlibrary.bean.AppselectHomeDataBean;
import com.shan.netlibrary.bean.AppupdatePasswordBean;
import com.shan.netlibrary.bean.AppuploadImgBean;
import com.shan.netlibrary.bean.BatchCollectProductBean;
import com.shan.netlibrary.bean.BrandselectBrandByCategory1Bean;
import com.shan.netlibrary.bean.BrandselectBrandByCategoryAndOldHouseStyleBean;
import com.shan.netlibrary.bean.BrandselectBrandByIdBean;
import com.shan.netlibrary.bean.BrandselectBrandCategory1Bean;
import com.shan.netlibrary.bean.BrandselectCategory2ByBrandIdBean;
import com.shan.netlibrary.bean.BrandselectOldHouseStyleByCategoryBean;
import com.shan.netlibrary.bean.GoodsaddGoodsBean;
import com.shan.netlibrary.bean.GoodsaddGoodsSpecificationBean;
import com.shan.netlibrary.bean.GoodsdelGoodsBean;
import com.shan.netlibrary.bean.GoodsdelGoodsSpecificationBean;
import com.shan.netlibrary.bean.GoodseditGoodsBean;
import com.shan.netlibrary.bean.GoodseditGoodsSpecificationBean;
import com.shan.netlibrary.bean.GoodsputawayGoodsBean;
import com.shan.netlibrary.bean.GoodsselectCategoryByCategory3IdBean;
import com.shan.netlibrary.bean.GoodsselectGoodsCategoryBean;
import com.shan.netlibrary.bean.GoodsselectGoodsSpecificationBean;
import com.shan.netlibrary.bean.GoodsselectPermGoodsListBean;
import com.shan.netlibrary.bean.GoodssubmitGoodsAuditBean;
import com.shan.netlibrary.bean.HandbagselectAllHouseTypeBean;
import com.shan.netlibrary.bean.HandbagselectHandbagCategoryBean;
import com.shan.netlibrary.bean.HandbagselectLayoutBean;
import com.shan.netlibrary.bean.LoginBean;
import com.shan.netlibrary.bean.PlanaddPlanBean;
import com.shan.netlibrary.bean.PlanselectAllHouseStyleAndHousestyleBean;
import com.shan.netlibrary.bean.PlanselectAllHouseStyleBean;
import com.shan.netlibrary.bean.PlanselectAllHouseTypeBean;
import com.shan.netlibrary.bean.PlanselectHouseStyleByOneselfPlanBean;
import com.shan.netlibrary.bean.PlanselectPlanByIdBean;
import com.shan.netlibrary.bean.PlanselectPlanOfFactoryBean;
import com.shan.netlibrary.bean.PlanselectPlanOfOneselfBean;
import com.shan.netlibrary.bean.PlanselectPlanOfOneselfDesignerBean;
import com.shan.netlibrary.bean.PlansetAuditStateBean;
import com.shan.netlibrary.bean.ProductcancelCollectBean;
import com.shan.netlibrary.bean.ProductcollectProductBean;
import com.shan.netlibrary.bean.ProductsdetailsBean;
import com.shan.netlibrary.bean.ProductselectCategory2ByCategory1Bean;
import com.shan.netlibrary.bean.ProductselectCategory3ByCategory2Bean;
import com.shan.netlibrary.bean.ProductselectProductByCategoryBean;
import com.shan.netlibrary.bean.ProductselectProductCategory1Bean;
import com.shan.netlibrary.bean.ProductselectProductOfCollectBean;
import com.shan.netlibrary.bean.ProductsshareBean;
import com.shan.netlibrary.bean.SelectComboBean;
import com.shan.netlibrary.bean.SelectComboListBean;
import com.shan.netlibrary.bean.ShareShoppingCartBean;
import com.shan.netlibrary.bean.UpgradeBean;
import com.shan.netlibrary.bean.UseraddAccountOfDesignerBean;
import com.shan.netlibrary.bean.UseraddAccountOfStoreBean;
import com.shan.netlibrary.bean.UseraddCustomerBean;
import com.shan.netlibrary.bean.UsereditAccountOfDesignerBean;
import com.shan.netlibrary.bean.UsereditCustomerBean;
import com.shan.netlibrary.bean.UserselectCustomerBean;
import com.shan.netlibrary.bean.UserselectCustomerOfDesignerBean;
import com.shan.netlibrary.bean.UserselectDesignerBean;
import com.shan.netlibrary.bean.UserselectStoreBean;
import com.shan.netlibrary.bean.YqxLoginBean;

import java.util.Map;

import okhttp3.RequestBody;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.PartMap;
import retrofit2.http.QueryMap;
import retrofit2.http.Streaming;
import rx.Observable;

/**
 * Created by chenjunshan on 2016/8/18.
 */

public interface HttpService {
    //获取平板登录信息
    @Streaming
    @GET("ogtablet/app/accountInfo")
    Observable<AppaccountInfoBean> appaccountInfo(@QueryMap Map<String, String> params);

    //获取所有省份
    @Streaming
    @GET("ogtablet/app/getAllProvince")
    Observable<AddressBean> appgetAllProvince(@QueryMap Map<String, String> params);

    //获取某省下所有的市
    @Streaming
    @GET("ogtablet/app/getCityByProvince")
    Observable<AddressBean> appgetCityByProvince(@QueryMap Map<String, String> params);

    //获取某市下所有的县/区
    @Streaming
    @GET("ogtablet/app/getDistrictByCity")
    Observable<AddressBean> appgetDistrictByCity(@QueryMap Map<String, String> params);

    //平板登录
    @FormUrlEncoded
    @POST("ogtablet/app/login")
    Observable<LoginBean> applogin(@FieldMap Map<String, String> params);

    //平板注销
    @Streaming
    @GET("ogtablet/app/logout")
    Observable<ApplogoutBean> applogout(@QueryMap Map<String, String> params);

    //重置密码
    @FormUrlEncoded
    @POST("ogtablet/app/resetPassword")
    Observable<AppresetPasswordBean> appresetPassword(@FieldMap Map<String, String> params);

    //修改密码
    @FormUrlEncoded
    @POST("ogtablet/app/updatePassword")
    Observable<AppupdatePasswordBean> appupdatePassword(@FieldMap Map<String, String> params);

    //上传图片
    @Multipart
    @POST("ogtablet/app/uploadImg")
    Observable<AppuploadImgBean> appuploadImg(@Part("file" + "\";filename=\"" + "appUpload.png") RequestBody file);

    //上传图片
    @Multipart
    @POST("ogtablet/app/uploadImg")
    Observable<AppuploadImgBean> appuploadImg2(@PartMap Map<String, RequestBody> params);

    //编辑个人资料
    @FormUrlEncoded
    @POST("ogtablet/app/editPersonalData")
    Observable<AppeditPersonalDataBean> appeditPersonalData(@FieldMap Map<String, String> params);

    //增加客户
    @FormUrlEncoded
    @POST("ogtablet/app/user/addCustomer")
    Observable<UseraddCustomerBean> useraddCustomer(@FieldMap Map<String, String> params);

    //查询客户
    @FormUrlEncoded
    @POST("ogtablet/app/user/selectCustomer")
    Observable<UserselectCustomerBean> userselectCustomer(@FieldMap Map<String, String> params);

    //增加设计师
    @FormUrlEncoded
    @POST("ogtablet/app/user/addAccountOfDesigner")
    Observable<UseraddAccountOfDesignerBean> useraddAccountOfDesigner(@FieldMap Map<String, String> params);

    //增加店铺
    @FormUrlEncoded
    @POST("ogtablet/app/user/addAccountOfStore")
    Observable<UseraddAccountOfStoreBean> useraddAccountOfStore(@FieldMap Map<String, String> params);

    //编辑设计师
    @FormUrlEncoded
    @POST("ogtablet/app/user/editAccountOfDesigner")
    Observable<UsereditAccountOfDesignerBean> usereditAccountOfDesigner(@FieldMap Map<String, String> params);

    //店铺商查询自己的指定设计师其下的客户
    @FormUrlEncoded
    @POST("ogtablet/app/user/selectCustomerOfDesigner")
    Observable<UserselectCustomerOfDesignerBean> userselectCustomerOfDesigner(@FieldMap Map<String, String> params);

    //店铺商查询自己的设计师
    @FormUrlEncoded
    @POST("ogtablet/app/user/selectDesigner")
    Observable<UserselectDesignerBean> userselectDesigner(@FieldMap Map<String, String> params);

    //市代理查询自己的店铺
    @FormUrlEncoded
    @POST("ogtablet/app/user/selectStore")
    Observable<UserselectStoreBean> userselectStore(@FieldMap Map<String, String> params);

    //编辑客户
    @FormUrlEncoded
    @POST("ogtablet/app/user/editCustomer")
    Observable<UsereditCustomerBean> usereditCustomer(@FieldMap Map<String, String> params);

    //增加方案
    @FormUrlEncoded
    @POST("ogtablet/app/plan/addPlan")
    Observable<PlanaddPlanBean> planaddPlan(@FieldMap Map<String, String> params);

    //平板底部的方案里的风格列表查询
    @FormUrlEncoded
    @POST("ogtablet/app/plan/selectAllHouseStyle")
    Observable<PlanselectAllHouseStyleBean> planselectAllHouseStyle(@FieldMap Map<String, String> params);

    //查询所有户型
    @FormUrlEncoded
    @POST("ogtablet/app/plan/selectAllHouseType")
    Observable<PlanselectAllHouseTypeBean> planselectAllHouseType(@FieldMap Map<String, String> params);

    //根据自己所创建的方案查询出风格列表
    @FormUrlEncoded
    @POST("ogtablet/app/plan/selectHouseStyleByOneselfPlan")
    Observable<PlanselectHouseStyleByOneselfPlanBean> planselectHouseStyleByOneselfPlan(@FieldMap Map<String, String> params);

    //平板底部的方案里查询对应工厂下级的方案
    @FormUrlEncoded
    @POST("ogtablet/app/plan/selectPlanOfFactory")
    Observable<PlanselectPlanOfFactoryBean> planselectPlanOfFactory(@FieldMap Map<String, String> params);

    //查询自己的方案
    @FormUrlEncoded
    @POST("ogtablet/app/plan/selectPlanOfOneself")
    Observable<PlanselectPlanOfOneselfBean> planselectPlanOfOneself(@FieldMap Map<String, String> params);

    //查询自己的所有设计师下的所有方案
    @FormUrlEncoded
    @POST("ogtablet/app/plan/selectPlanOfOneselfDesigner")
    Observable<PlanselectPlanOfOneselfDesignerBean> planselectPlanOfOneselfDesigner(@FieldMap Map<String, String> params);

    //店铺设置审核状
    @FormUrlEncoded
    @POST("ogtablet/app/plan/setAuditState")
    Observable<PlansetAuditStateBean> plansetAuditState(@FieldMap Map<String, String> params);

    //查询方案详情
    @FormUrlEncoded
    @POST("ogtablet/app/plan/selectPlanById")
    Observable<PlanselectPlanByIdBean> planselectPlanById(@FieldMap Map<String, String> params);

    //根据收藏ID批量取消我的收藏或者客户的收藏
    @FormUrlEncoded
    @POST("ogtablet/app/product/cancelCollect")
    Observable<ProductcancelCollectBean> productcancelCollect(@FieldMap Map<String, String> params);

    //帐号本身或者客户收藏或取消收藏商品商品
    @FormUrlEncoded
    @POST("ogtablet/app/product/collectProduct")
    Observable<ProductcollectProductBean> productcollectProduct(@FieldMap Map<String, String> params);

    //根据一级分类查询二级分类（白名单接口）
    @FormUrlEncoded
    @POST("ogtablet/app/product/selectCategory2ByCategory1")
    Observable<ProductselectCategory2ByCategory1Bean> productselectCategory2ByCategory1(@FieldMap Map<String, String> params);

    //根据二级分类查询三级分类（白名单接口）
    @FormUrlEncoded
    @POST("ogtablet/app/product/selectCategory3ByCategory2")
    Observable<ProductselectCategory3ByCategory2Bean> productselectCategory3ByCategory2(@FieldMap Map<String, String> params);

    //根据分类查询商品或联合关键字查询（白名单接口）
    @FormUrlEncoded
    @POST("ogtablet/app/product/selectProductByCategory")
    Observable<ProductselectProductByCategoryBean> productselectProductByCategory(@FieldMap Map<String, String> params);

    //根据商品ID查询商品详情（白名单接口）
    @FormUrlEncoded
    @POST("ogtablet/app/product/selectProductById")
    Observable<ProductsdetailsBean> productselectProductById(@FieldMap Map<String, String> params);

    //查询我的收藏商品或者客户的收藏商品
    @FormUrlEncoded
    @POST("ogtablet/app/product/selectProductOfCollect")
    Observable<ProductselectProductOfCollectBean> productselectProductOfCollect(@FieldMap Map<String, String> params);

    //根据一级分类查询品牌列表（白名单接口）
    @FormUrlEncoded
    @POST("ogtablet/app/brand/selectBrandByCategory1")
    Observable<BrandselectBrandByCategory1Bean> brandselectBrandByCategory1(@FieldMap Map<String, String> params);

    //查询品牌详情（白名单接口）
    @FormUrlEncoded
    @POST("ogtablet/app/brand/selectBrandById")
    Observable<BrandselectBrandByIdBean> brandselectBrandById(@FieldMap Map<String, String> params);

    //查询品牌一级分类（白名单接口）
    @FormUrlEncoded
    @POST("ogtablet/app/brand/selectBrandCategory1")
    Observable<BrandselectBrandCategory1Bean> brandselectBrandCategory1(@FieldMap Map<String, String> params);

    //根据品牌ID查询二级分类（白名单接口）
    @FormUrlEncoded
    @POST("ogtablet/app/brand/selectCategory2ByBrandId")
    Observable<BrandselectCategory2ByBrandIdBean> brandselectCategory2ByBrandId(@FieldMap Map<String, String> params);

    //查询商品一级分类（白名单接口）
    @FormUrlEncoded
    @POST("ogtablet/app/product/selectProductCategory1")
    Observable<ProductselectProductCategory1Bean> productselectProductCategory1(@FieldMap Map<String, String> params);

    //一起秀登录
    @FormUrlEncoded
    @Headers({HttpConfig.HEADER_KEY + ":" + HttpConfig.YQX_URL_HEADER})
    //@POST("444f79a0cc938395/a5b451c078d68e45db36d5ec84647e13")//测试环境
    @POST("444f79a0cc938395/a5b451c078d68e45db36d5ec84647e13")
    //正式环境
    Observable<YqxLoginBean> yqxLogin(@FieldMap Map<String, String> params);

    //获取平板APP版本信息（白名单接口）
    @FormUrlEncoded
    @POST("ogtablet/app/getVersionInfo")
    Observable<UpgradeBean> getVersion(@FieldMap Map<String, String> params);

    //获取首页数据（白名单接口）
    @Streaming
    @GET("ogtablet/app/selectHomeData")
    Observable<AppselectHomeDataBean> appselectHomeData(@QueryMap Map<String, String> params);

    //查询所有户型
    @FormUrlEncoded
    @POST("ogtablet/app/handbag/selectAllHouseType")
    Observable<HandbagselectAllHouseTypeBean> handbagselectAllHouseType(@FieldMap Map<String, String> params);

    //查询拎包入住分类
    @FormUrlEncoded
    @POST("ogtablet/app/handbag/selectHandbagCategory")
    Observable<HandbagselectHandbagCategoryBean> handbagselectHandbagCategory(@FieldMap Map<String, String> params);

    //查询布局
    @FormUrlEncoded
    @POST("ogtablet/app/handbag/selectLayout")
    Observable<HandbagselectLayoutBean> handbagselectLayout(@FieldMap Map<String, String> params);

    //拎包入住分享
    @FormUrlEncoded
    @POST("ogtablet/app/product/shareShoppingCart")
    Observable<ShareShoppingCartBean> shareShoppingCart(@FieldMap Map<String, String> params);

    //批量收藏商品
    @FormUrlEncoded
    @POST("ogtablet/app/product/batchCollectProduct")
    Observable<BatchCollectProductBean> batchCollectProduct(@FieldMap Map<String, String> params);

    //产品分享
    @Streaming
    @Headers({HttpConfig.HEADER_KEY + ":" + HttpConfig.OLD_OGMALL_URL_HEADER})
    @GET("pad/products/share")
    Observable<ProductsshareBean> productsshare(@QueryMap Map<String, String> params);

    //根据(一/二/三级)分类和老系统的风格查询品牌列表
    @FormUrlEncoded
    @POST("ogtablet/app/brand/selectBrandByCategoryAndOldHouseStyle")
    Observable<BrandselectBrandByCategoryAndOldHouseStyleBean> brandselectBrandByCategoryAndOldHouseStyle(@FieldMap Map<String, String> params);

    //根据(一/二/三级)分类查询老系统风格列表，其中category=-1时表示主推品
    @FormUrlEncoded
    @POST("ogtablet/app/brand/selectOldHouseStyleByCategory")
    Observable<BrandselectOldHouseStyleByCategoryBean> brandselectOldHouseStyleByCategory(@FieldMap Map<String, String> params);

    //查询方案列表（包含平台方案列表和工厂的方案列表）
    @FormUrlEncoded
    @POST("ogtablet/app/plan/selectAllHouseStyleAndHousestyle")
    Observable<PlanselectAllHouseStyleAndHousestyleBean> planselectAllHouseStyleAndHousestyle(@FieldMap Map<String, String> params);

    //查询平台方案详情
    @FormUrlEncoded
    @POST("ogtablet/app/plan/selectDesignById")
    Observable<PlanselectPlanByIdBean> planselectDesignById(@FieldMap Map<String, String> params);

    //查询平台方案列表
    @FormUrlEncoded
    @POST("ogtablet/app/plan/selectDesignList")
    Observable<PlanselectPlanOfFactoryBean> planselectDesignList(@FieldMap Map<String, String> params);

    //添加商品（仅工厂和店铺拥有该权限）
    @FormUrlEncoded
    @POST("ogtablet/app/goods/addGoods")
    Observable<GoodsaddGoodsBean> goodsaddGoods(@FieldMap Map<String, String> params);

    //批量删除商品（仅工厂和店铺拥有该权限）
    @FormUrlEncoded
    @POST("ogtablet/app/goods/delGoods")
    Observable<GoodsdelGoodsBean> goodsdelGoods(@FieldMap Map<String, String> params);

    //修改商品（仅工厂和店铺拥有该权限）
    @FormUrlEncoded
    @POST("ogtablet/app/goods/editGoods")
    Observable<GoodseditGoodsBean> goodseditGoods(@FieldMap Map<String, String> params);

    //批量上下架商品（仅工厂和店铺拥有该权限）
    @FormUrlEncoded
    @POST("ogtablet/app/goods/putawayGoods")
    Observable<GoodsputawayGoodsBean> goodsputawayGoods(@FieldMap Map<String, String> params);

    //查询商品分类
    @FormUrlEncoded
    @POST("ogtablet/app/goods/selectGoodsCategory")
    Observable<GoodsselectGoodsCategoryBean> goodsselectGoodsCategory(@FieldMap Map<String, String> params);

    //查询商品规格
    @FormUrlEncoded
    @POST("ogtablet/app/goods/selectPermGoodsList")
    Observable<GoodsselectPermGoodsListBean> goodsselectPermGoodsList(@FieldMap Map<String, String> params);

    //批量提交商品审核（仅工厂和店铺拥有该权限）
    @FormUrlEncoded
    @POST("ogtablet/app/goods/submitGoodsAudit")
    Observable<GoodssubmitGoodsAuditBean> goodssubmitGoodsAudit(@FieldMap Map<String, String> params);

    //查询商品规格（仅管理员/工厂/店铺拥有该权限）
    @FormUrlEncoded
    @POST("ogtablet/app/goods/selectGoodsSpecification")
    Observable<GoodsselectGoodsSpecificationBean> goodsselectGoodsSpecification(@FieldMap Map<String, String> params);

    //添加商品规格（仅工厂和店铺拥有该权限）
    /*@FormUrlEncoded
    @POST("ogtablet/app/goods/addGoodsSpecification")
    Observable<GoodsaddGoodsSpecificationBean> goodsaddGoodsSpecification(@FieldMap Map<String, String> params);*/

    //上传图片
    @Multipart
    @POST("ogtablet/app/goods/addGoodsSpecification")
    Observable<GoodsaddGoodsSpecificationBean> goodsaddGoodsSpecification(@QueryMap Map<String, String> params, @PartMap Map<String, RequestBody> files);

    //批量删除商品规格（仅工厂和店铺拥有该权限）
    @FormUrlEncoded
    @POST("ogtablet/app/goods/delGoodsSpecification")
    Observable<GoodsdelGoodsSpecificationBean> goodsdelGoodsSpecification(@FieldMap Map<String, String> params);

    //修改商品规格（仅工厂和店铺拥有该权限）
    @Multipart
    @POST("ogtablet/app/goods/editGoodsSpecification")
    Observable<GoodseditGoodsSpecificationBean> goodseditGoodsSpecification(@QueryMap Map<String, String> params, @PartMap Map<String, RequestBody> files);

    //修改商品规格（仅工厂和店铺拥有该权限）
    @FormUrlEncoded
    @POST("ogtablet/app/goods/editGoodsSpecification")
    Observable<GoodseditGoodsSpecificationBean> goodseditGoodsSpecificationNoFile(@FieldMap Map<String, String> params);

    //根据三级分类查出对应的一级，二级，三级分类
    @FormUrlEncoded
    @POST("ogtablet/app/goods/selectCategoryByCategory3Id")
    Observable<GoodsselectCategoryByCategory3IdBean> goodsselectCategoryByCategory3Id(@FieldMap Map<String, String> params);
    //查询套餐详情  id
    @FormUrlEncoded
    @POST("ogtablet/app/combo/selectCombo")
    Observable<SelectComboBean> selectCombo(@FieldMap Map<String,String> params);
    //查询套餐列表  page(第几页) *  limit(每页显示多少条) *   keywords(搜索关键字)
    @FormUrlEncoded
    @POST("ogtablet/app/combo/selectComboList")
    Observable<SelectComboListBean> selectComboList(@FieldMap Map<String,String> params);
}
