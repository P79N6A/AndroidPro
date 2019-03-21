package com.shan.netlibrary.net;

import com.shan.netlibrary.bean.BagcategorysBean;
import com.shan.netlibrary.bean.BagshareBean;
import com.shan.netlibrary.bean.BranddetailsBean;
import com.shan.netlibrary.bean.BrandlistBean;
import com.shan.netlibrary.bean.CartsaveBean;
import com.shan.netlibrary.bean.CategorysBean;
import com.shan.netlibrary.bean.CategorysofNameBean;
import com.shan.netlibrary.bean.CenteraddCustomerBean;
import com.shan.netlibrary.bean.CentercustomersBean;
import com.shan.netlibrary.bean.CenterdeletecollectsBean;
import com.shan.netlibrary.bean.CentergetcollectsBean;
import com.shan.netlibrary.bean.CentersavecollectsBean;
import com.shan.netlibrary.bean.CenteruserInfoBean;
import com.shan.netlibrary.bean.CollectsstylesBean;
import com.shan.netlibrary.bean.CustomerloginBean;
import com.shan.netlibrary.bean.CustomerlogoutBean;
import com.shan.netlibrary.bean.DesigndetailsBean;
import com.shan.netlibrary.bean.DesignerofUserIdBean;
import com.shan.netlibrary.bean.DesignlistBean;
import com.shan.netlibrary.bean.DesignstylesOfdesignerBean;
import com.shan.netlibrary.bean.HasProductBean;
import com.shan.netlibrary.bean.MyDesigndetailsBean;
import com.shan.netlibrary.bean.ProductofCategotyBean;
import com.shan.netlibrary.bean.ProductscategorysBean;
import com.shan.netlibrary.bean.ProductsdetailsBean;
import com.shan.netlibrary.bean.ProductsearchBean;
import com.shan.netlibrary.bean.ProductsoftypeBean;
import com.shan.netlibrary.bean.ProductsshareBean;
import com.shan.netlibrary.bean.RoomTypelistBean;
import com.shan.netlibrary.bean.RoomTypespecsBean;
import com.shan.netlibrary.bean.StylesBean;
import com.shan.netlibrary.bean.UpgradeBean;
import com.shan.netlibrary.bean.UserloginBean;
import com.shan.netlibrary.bean.UserlogoutBean;
import com.shan.netlibrary.bean.YqxLoginBean;

import java.util.Map;

import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.HTTP;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.QueryMap;
import retrofit2.http.Streaming;
import rx.Observable;

/**
 * Created by chenjunshan on 2016/8/18.
 */

public interface HttpService {
    //获取产品分类列表 categoryId 不传时，获取所有的一级分类
    @Streaming
    @GET("categorys")
    Observable<CategorysBean> categorys(@QueryMap Map<String, String> params);

    //获取产品分类列表 categoryId 不传时，获取所有的一级分类
    @Streaming
    @GET("categorys/hasProduct")
    Observable<HasProductBean> hasProduct(@QueryMap Map<String, String> params);

    //添加客户,同时建立与业务员的会话
    @FormUrlEncoded
    @POST("pad/center/addCustomer")
    Observable<CenteraddCustomerBean> centeraddCustomer(@FieldMap Map<String, String> params);

    //获取用户列表
    @Streaming
    @GET("pad/center/customers")
    Observable<CentercustomersBean> centercustomers(@QueryMap Map<String, String> params);

    //业务员登录
    @FormUrlEncoded
    @POST("pad/user/login")
    Observable<UserloginBean> userlogin(@FieldMap Map<String, String> params);

    //业务员退出当前登录
    @FormUrlEncoded
    @POST("pad/user/logout")
    Observable<UserlogoutBean> userlogout(@FieldMap Map<String, String> params);

    //业务员与客户建立会话
    @FormUrlEncoded
    @POST("pad/center/customer/login")
    Observable<CustomerloginBean> customerlogin(@FieldMap Map<String, String> params);

    //退出当前客户
    @FormUrlEncoded
    @POST("pad/center/customer/logout")
    Observable<CustomerlogoutBean> customerlogout(@FieldMap Map<String, String> params);

    //获取当前登录的用户数据, 和建立会话的客户信息
    @Streaming
    @GET("pad/center/userInfo")
    Observable<CenteruserInfoBean> centeruserInfo(@QueryMap Map<String, String> params);

    //根据类型id 获取产品数据
    @FormUrlEncoded
    @POST("products/oftype")
    Observable<ProductsoftypeBean> productsoftype(@FieldMap Map<String, String> params);

    //获取所有的风格数据列表
    @Streaming
    @GET("styles")
    Observable<StylesBean> styles(@QueryMap Map<String, String> params);

    //根据分类名称搜索分类信息
    @Streaming
    @GET("categorys/ofName")
    Observable<CategorysofNameBean> categorysofName(@QueryMap Map<String, String> params);

    //根据风格id返回品牌列表 当风格id为空时查询所有的品牌信息
    /*@FormUrlEncoded
    @POST("brand/list")
    Observable<BrandlistBean> brandlist(@FieldMap Map<String, String> params);*/
    @Streaming
    @GET("pad/brand/list")
    Observable<BrandlistBean> brandlist(@QueryMap Map<String, String> params);

    //根据品牌id查询品牌详情
    @FormUrlEncoded
    @POST("brand/details")
    Observable<BranddetailsBean> branddetails(@FieldMap Map<String, String> params);

    //所有产品二级类目
    @FormUrlEncoded
    @POST("products/categorys")
    Observable<ProductscategorysBean> productscategorys(@FieldMap Map<String, String> params);

    //根据风格Id所有审核通过的方案
    /*@Streaming
    @GET("design/list")
    Observable<DesignlistBean> designlist(@QueryMap Map<String, String> params);*/
    @Streaming
    @GET("pad/design/list")
    Observable<DesignlistBean> designlist(@QueryMap Map<String, String> params);

    /*//获取方案的详细信息
    @FormUrlEncoded
    @POST("design/details")
    Observable<DesigndetailsBean> designdetails(@FieldMap Map<String, String> params);*/
    //获取方案的详细信息
    @Streaming
    @GET("pad/design/details")
    Observable<DesigndetailsBean> designdetails(@QueryMap Map<String, String> params);

    //根据类型id 获取产品数据
    @Streaming
    @GET("product/ofCategoty")
    Observable<ProductofCategotyBean> productofCategoty(@QueryMap Map<String, String> params);

    //版本升级
    @Streaming
    @GET("apk/getVersion")
    Observable<UpgradeBean> getVersion(@QueryMap Map<String, String> params);

    //根据用户id 获取获取设计师用户数据！
    @Streaming
    @GET("pad/designer/ofUserId")
    Observable<DesignerofUserIdBean> designerofUserId(@QueryMap Map<String, String> params);

    //上设计师查看自己方案的详细数据
    @Streaming
    @GET("pad/design/details")
    Observable<MyDesigndetailsBean> mydesigndetails(@QueryMap Map<String, String> params);

    //获取设计师方案风格和方案列表
    @Streaming
    @GET("pad/design/stylesOfdesigner")
    Observable<DesignstylesOfdesignerBean> designstylesOfdesigner(@QueryMap Map<String, String> params);

    //拎包入住模块获取产品分类数据【品牌家具，软装生活】
    @Streaming
    @GET("pad/bag/categorys")
    Observable<BagcategorysBean> bagcategorys(@QueryMap Map<String, String> params);

    //在线设计方案一键分享
    @FormUrlEncoded
    @POST("pad/bag/share")
    Observable<BagshareBean> bagshare(@FieldMap Map<String, String> params);

    //保存购物车数据
    /*@FormUrlEncoded
    @POST("pad/cart/save")
    Observable<CartsaveBean> cartsave(@FieldMap Map<String, String> params);*/
    @Headers({"Content-Type: application/json", "Accept: application/json"})//需要添加头
    @POST("pad/cart/save")
    Observable<CartsaveBean> cartsave(@Body RequestBody info);

    //获取所有的户型数据
    @Streaming
    @GET("pad/bag/roomType/list")
    Observable<RoomTypelistBean> roomTypelist(@QueryMap Map<String, String> params);

    //根据户型id 获取布局数据
    @Streaming
    @GET("pad/bag/roomType/specs")
    Observable<RoomTypespecsBean> roomTypespecs(@QueryMap Map<String, String> params);

    //产品搜索
    @Streaming
    @GET("pad/bag/product/search")
    Observable<ProductsearchBean> productsearch(@QueryMap Map<String, String> params);

    //商品批量删除
    @HTTP(method = "DELETE", path = "pad/center/collects", hasBody = true)
    Observable<CenterdeletecollectsBean> centerdeletecollects(@Body RequestBody info);

    //获取收藏的商品
    @Streaming
    @GET("pad/center/collects")
    Observable<CentergetcollectsBean> centergetcollects(@QueryMap Map<String, String> params);

    //批量收藏商品
    /*@Headers({"Content-Type: application/json", "Accept: application/json"})//需要添加头
    @POST("pad/center/collects")
    Observable<CentersavecollectsBean> centersavecollects(@Body RequestBody info);*/
    //收藏商品
    @FormUrlEncoded
    @POST("pad/center/collects")
    Observable<CentersavecollectsBean> centersavecollects(@FieldMap Map<String, String> params);

    //获取收藏商品风格列表
    @Streaming
    @GET("pad/center/collects/styles")
    Observable<CollectsstylesBean> collectsstyles(@QueryMap Map<String, String> params);

    //产品分享
    @Streaming
    @GET("pad/products/share")
    Observable<ProductsshareBean> productsshare(@QueryMap Map<String, String> params);

    //商品详情
    @Streaming
    @GET("pad/products/details")
    Observable<ProductsdetailsBean> productsdetails(@QueryMap Map<String, String> params);

    //一起秀登录
    @FormUrlEncoded
    @Headers({HttpConfig.HEADER_KEY+":"+ HttpConfig.YQX_URL_HEADER})
    @POST("444f79a0cc938395/a5b451c078d68e45db36d5ec84647e13")
    Observable<YqxLoginBean> yqxLogin(@FieldMap Map<String, String> params);


}
