package com.shan.netlibrary.net;

import android.content.Context;

import com.google.gson.Gson;
import com.shan.netlibrary.bean.AddressBean;
import com.shan.netlibrary.bean.AppaccountInfoBean;
import com.shan.netlibrary.bean.AppeditPersonalDataBean;
import com.shan.netlibrary.bean.ApplogoutBean;
import com.shan.netlibrary.bean.AppresetPasswordBean;
import com.shan.netlibrary.bean.AppselectHomeDataBean;
import com.shan.netlibrary.bean.AppupdatePasswordBean;
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
import com.shan.netlibrary.contract.BaseView;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import okhttp3.RequestBody;


/**
 * HTTP请求方法
 * Created by chenjunshan on 2017-10-19.
 */

public class HttpPresenter extends HttpRequest {
    private Context mContext;
    private BaseView mView;


    public HttpPresenter(Context mContext, BaseView mView) {
        this.mContext = mContext;
        this.mView = mView;
    }


    public static final long APPACCOUNTINFO = 1542111539576L;

    /**
     * 获取平板登录信息
     *
     * @param map
     */
    public void appaccountInfo(Map<String, String> map, boolean isShow) {
        HttpCallback callback = new HttpCallback<AppaccountInfoBean>(mContext, this, isShow) {
            @Override
            protected void onSuccess(AppaccountInfoBean baseBean) {
                mView.onSuccess(baseBean, APPACCOUNTINFO);
            }

            @Override
            protected void onFailure(Throwable e) {
                mView.onFailure(e, APPACCOUNTINFO);
            }
        };
        startRequest(HttpBuilder.httpService.appaccountInfo(AppParams.getParams(map)), callback);
    }

    public static final long APPGETALLPROVINCE = 1542111539577L;

    /**
     * 获取所有省份
     *
     * @param map
     */
    public void appgetAllProvince(Map<String, String> map) {
        HttpCallback callback = new HttpCallback<AddressBean>(mContext, this) {
            @Override
            protected void onSuccess(AddressBean baseBean) {
                mView.onSuccess(baseBean, APPGETALLPROVINCE);
            }

            @Override
            protected void onFailure(Throwable e) {
                mView.onFailure(e, APPGETALLPROVINCE);
            }
        };
        startRequest(HttpBuilder.httpService.appgetAllProvince(AppParams.getParams(map)), callback);
    }

    public static final long APPGETCITYBYPROVINCE = 1542111539578L;

    /**
     * 获取某省下所有的市
     *
     * @param map
     */
    public void appgetCityByProvince(Map<String, String> map) {
        HttpCallback callback = new HttpCallback<AddressBean>(mContext, this) {
            @Override
            protected void onSuccess(AddressBean baseBean) {
                mView.onSuccess(baseBean, APPGETCITYBYPROVINCE);
            }

            @Override
            protected void onFailure(Throwable e) {
                mView.onFailure(e, APPGETCITYBYPROVINCE);
            }
        };
        startRequest(HttpBuilder.httpService.appgetCityByProvince(AppParams.getParams(map)), callback);
    }

    public static final long APPGETDISTRICTBYCITY = 1542111539579L;

    /**
     * 获取某市下所有的县/区
     *
     * @param map
     */
    public void appgetDistrictByCity(Map<String, String> map) {
        HttpCallback callback = new HttpCallback<AddressBean>(mContext, this) {
            @Override
            protected void onSuccess(AddressBean baseBean) {
                mView.onSuccess(baseBean, APPGETDISTRICTBYCITY);
            }

            @Override
            protected void onFailure(Throwable e) {
                mView.onFailure(e, APPGETDISTRICTBYCITY);
            }
        };
        startRequest(HttpBuilder.httpService.appgetDistrictByCity(AppParams.getParams(map)), callback);
    }

    public static final long APPLOGIN = 1542111539581L;

    /**
     * 平板登录
     *
     * @param map
     */
    public void applogin(Map<String, String> map) {
        HttpCallback callback = new HttpCallback<LoginBean>(mContext, this, false) {
            @Override
            protected void onSuccess(LoginBean baseBean) {
                mView.onSuccess(baseBean, APPLOGIN);
            }

            @Override
            protected void onFailure(Throwable e) {
                mView.onFailure(e, APPLOGIN);
            }
        };
        startRequest(HttpBuilder.httpService.applogin(AppParams.getParams(map)), callback);
    }

    public static final long APPLOGOUT = 1542111539582L;

    /**
     * 平板注销
     *
     * @param map
     */
    public void applogout(Map<String, String> map) {
        HttpCallback callback = new HttpCallback<ApplogoutBean>(mContext, this) {
            @Override
            protected void onSuccess(ApplogoutBean baseBean) {
                mView.onSuccess(baseBean, APPLOGOUT);
            }

            @Override
            protected void onFailure(Throwable e) {
                mView.onFailure(e, APPLOGOUT);
            }
        };
        startRequest(HttpBuilder.httpService.applogout(AppParams.getParams(map)), callback);
    }

    public static final long APPRESETPASSWORD = 1542111539583L;

    /**
     * 重置密码
     *
     * @param map
     */
    public void appresetPassword(Map<String, String> map) {
        HttpCallback callback = new HttpCallback<AppresetPasswordBean>(mContext, this) {
            @Override
            protected void onSuccess(AppresetPasswordBean baseBean) {
                mView.onSuccess(baseBean, APPRESETPASSWORD);
            }

            @Override
            protected void onFailure(Throwable e) {
                mView.onFailure(e, APPRESETPASSWORD);
            }
        };
        startRequest(HttpBuilder.httpService.appresetPassword(AppParams.getParams(map)), callback);
    }

    public static final long APPUPDATEPASSWORD = 1542111539584L;

    /**
     * 修改密码
     *
     * @param map
     */
    public void appupdatePassword(Map<String, String> map) {
        HttpCallback callback = new HttpCallback<AppupdatePasswordBean>(mContext, this) {
            @Override
            protected void onSuccess(AppupdatePasswordBean baseBean) {
                mView.onSuccess(baseBean, APPUPDATEPASSWORD);
            }

            @Override
            protected void onFailure(Throwable e) {
                mView.onFailure(e, APPUPDATEPASSWORD);
            }
        };
        startRequest(HttpBuilder.httpService.appupdatePassword(AppParams.getParams(map)), callback);
    }


    public static final long APPEDITPERSONALDATA = 1542339180433L;

    /**
     * 编辑个人资料
     *
     * @param map
     */
    public void appeditPersonalData(Map<String, String> map) {
        HttpCallback callback = new HttpCallback<AppeditPersonalDataBean>(mContext, this) {
            @Override
            protected void onSuccess(AppeditPersonalDataBean baseBean) {
                mView.onSuccess(baseBean, APPEDITPERSONALDATA);
            }

            @Override
            protected void onFailure(Throwable e) {
                mView.onFailure(e, APPEDITPERSONALDATA);
            }
        };
        startRequest(HttpBuilder.httpService.appeditPersonalData(AppParams.getParams(map)), callback);
    }


    public static final long USERADDCUSTOMER = 1542355953137L;

    /**
     * 增加客户
     *
     * @param map
     */
    public void useraddCustomer(Map<String, String> map) {
        HttpCallback callback = new HttpCallback<UseraddCustomerBean>(mContext, this) {
            @Override
            protected void onSuccess(UseraddCustomerBean baseBean) {
                mView.onSuccess(baseBean, USERADDCUSTOMER);
            }

            @Override
            protected void onFailure(Throwable e) {
                mView.onFailure(e, USERADDCUSTOMER);
            }
        };
        startRequest(HttpBuilder.httpService.useraddCustomer(AppParams.getParams(map)), callback);
    }


    public static final long USERSELECTCUSTOMER = 1542356826686L;

    /**
     * 查询客户
     *
     * @param map
     */
    public void userselectCustomer(Map<String, String> map, boolean isHide) {
        HttpCallback callback = new HttpCallback<UserselectCustomerBean>(mContext, this, isHide) {
            @Override
            protected void onSuccess(UserselectCustomerBean baseBean) {
                mView.onSuccess(baseBean, USERSELECTCUSTOMER);
            }

            @Override
            protected void onFailure(Throwable e) {
                mView.onFailure(e, USERSELECTCUSTOMER);
            }
        };
        startRequest(HttpBuilder.httpService.userselectCustomer(AppParams.getParams(map)), callback);
    }


    public static final long USERADDACCOUNTOFDESIGNER = 1542366462626L;

    /**
     * 增加设计师
     *
     * @param map
     */
    public void useraddAccountOfDesigner(Map<String, String> map) {
        HttpCallback callback = new HttpCallback<UseraddAccountOfDesignerBean>(mContext, this) {
            @Override
            protected void onSuccess(UseraddAccountOfDesignerBean baseBean) {
                mView.onSuccess(baseBean, USERADDACCOUNTOFDESIGNER);
            }

            @Override
            protected void onFailure(Throwable e) {
                mView.onFailure(e, USERADDACCOUNTOFDESIGNER);
            }
        };
        startRequest(HttpBuilder.httpService.useraddAccountOfDesigner(AppParams.getParams(map)), callback);
    }

    public static final long USERADDACCOUNTOFSTORE = 1542366462627L;

    /**
     * 增加店铺
     *
     * @param map
     */
    public void useraddAccountOfStore(Map<String, String> map) {
        HttpCallback callback = new HttpCallback<UseraddAccountOfStoreBean>(mContext, this) {
            @Override
            protected void onSuccess(UseraddAccountOfStoreBean baseBean) {
                mView.onSuccess(baseBean, USERADDACCOUNTOFSTORE);
            }

            @Override
            protected void onFailure(Throwable e) {
                mView.onFailure(e, USERADDACCOUNTOFSTORE);
            }
        };
        startRequest(HttpBuilder.httpService.useraddAccountOfStore(AppParams.getParams(map)), callback);
    }

    public static final long USEREDITACCOUNTOFDESIGNER = 1542366462628L;

    /**
     * 编辑设计师
     *
     * @param map
     */
    public void usereditAccountOfDesigner(Map<String, String> map) {
        HttpCallback callback = new HttpCallback<UsereditAccountOfDesignerBean>(mContext, this) {
            @Override
            protected void onSuccess(UsereditAccountOfDesignerBean baseBean) {
                mView.onSuccess(baseBean, USEREDITACCOUNTOFDESIGNER);
            }

            @Override
            protected void onFailure(Throwable e) {
                mView.onFailure(e, USEREDITACCOUNTOFDESIGNER);
            }
        };
        startRequest(HttpBuilder.httpService.usereditAccountOfDesigner(AppParams.getParams(map)), callback);
    }

    public static final long USERSELECTCUSTOMEROFDESIGNER = 1542366462629L;

    /**
     * 店铺商查询自己的指定设计师其下的客户
     *
     * @param map
     */
    public void userselectCustomerOfDesigner(Map<String, String> map) {
        HttpCallback callback = new HttpCallback<UserselectCustomerOfDesignerBean>(mContext, this) {
            @Override
            protected void onSuccess(UserselectCustomerOfDesignerBean baseBean) {
                mView.onSuccess(baseBean, USERSELECTCUSTOMEROFDESIGNER);
            }

            @Override
            protected void onFailure(Throwable e) {
                mView.onFailure(e, USERSELECTCUSTOMEROFDESIGNER);
            }
        };
        startRequest(HttpBuilder.httpService.userselectCustomerOfDesigner(AppParams.getParams(map)), callback);
    }

    public static final long USERSELECTDESIGNER = 1542366462630L;

    /**
     * 店铺商查询自己的设计师
     *
     * @param map
     */
    public void userselectDesigner(Map<String, String> map, boolean isShow) {
        HttpCallback callback = new HttpCallback<UserselectDesignerBean>(mContext, this, isShow) {
            @Override
            protected void onSuccess(UserselectDesignerBean baseBean) {
                mView.onSuccess(baseBean, USERSELECTDESIGNER);
            }

            @Override
            protected void onFailure(Throwable e) {
                mView.onFailure(e, USERSELECTDESIGNER);
            }
        };
        startRequest(HttpBuilder.httpService.userselectDesigner(AppParams.getParams(map)), callback);
    }

    public static final long USERSELECTSTORE = 1542366462631L;

    /**
     * 市代理查询自己的店铺
     *
     * @param map
     */
    public void userselectStore(Map<String, String> map) {
        HttpCallback callback = new HttpCallback<UserselectStoreBean>(mContext, this) {
            @Override
            protected void onSuccess(UserselectStoreBean baseBean) {
                mView.onSuccess(baseBean, USERSELECTSTORE);
            }

            @Override
            protected void onFailure(Throwable e) {
                mView.onFailure(e, USERSELECTSTORE);
            }
        };
        startRequest(HttpBuilder.httpService.userselectStore(AppParams.getParams(map)), callback);
    }


    public static final long USEREDITCUSTOMER = 1542378639416L;

    /**
     * 编辑客户
     *
     * @param map
     */
    public void usereditCustomer(Map<String, String> map) {
        HttpCallback callback = new HttpCallback<UsereditCustomerBean>(mContext, this) {
            @Override
            protected void onSuccess(UsereditCustomerBean baseBean) {
                mView.onSuccess(baseBean, USEREDITCUSTOMER);
            }

            @Override
            protected void onFailure(Throwable e) {
                mView.onFailure(e, USEREDITCUSTOMER);
            }
        };
        startRequest(HttpBuilder.httpService.usereditCustomer(AppParams.getParams(map)), callback);
    }


    public static final long PLANADDPLAN = 1542418619362L;

    /**
     * 增加方案
     *
     * @param map
     */
    public void planaddPlan(Map<String, String> map) {
        HttpCallback callback = new HttpCallback<PlanaddPlanBean>(mContext, this) {
            @Override
            protected void onSuccess(PlanaddPlanBean baseBean) {
                mView.onSuccess(baseBean, PLANADDPLAN);
            }

            @Override
            protected void onFailure(Throwable e) {
                mView.onFailure(e, PLANADDPLAN);
            }
        };
        startRequest(HttpBuilder.httpService.planaddPlan(AppParams.getParams(map)), callback);
    }

    public static final long PLANSELECTALLHOUSESTYLE = 1542418619363L;

    /**
     * 平板底部的方案里的风格列表查询
     *
     * @param map
     */
    public void planselectAllHouseStyle(Map<String, String> map) {
        HttpCallback callback = new HttpCallback<PlanselectAllHouseStyleBean>(mContext, this) {
            @Override
            protected void onSuccess(PlanselectAllHouseStyleBean baseBean) {
                mView.onSuccess(baseBean, PLANSELECTALLHOUSESTYLE);
            }

            @Override
            protected void onFailure(Throwable e) {
                mView.onFailure(e, PLANSELECTALLHOUSESTYLE);
            }
        };
        startRequest(HttpBuilder.httpService.planselectAllHouseStyle(AppParams.getParams(map)), callback);
    }

    public static final long PLANSELECTALLHOUSETYPE = 1542418619364L;

    /**
     * 查询所有户型
     *
     * @param map
     */
    public void planselectAllHouseType(Map<String, String> map) {
        HttpCallback callback = new HttpCallback<PlanselectAllHouseTypeBean>(mContext, this) {
            @Override
            protected void onSuccess(PlanselectAllHouseTypeBean baseBean) {
                mView.onSuccess(baseBean, PLANSELECTALLHOUSETYPE);
            }

            @Override
            protected void onFailure(Throwable e) {
                mView.onFailure(e, PLANSELECTALLHOUSETYPE);
            }
        };
        startRequest(HttpBuilder.httpService.planselectAllHouseType(AppParams.getParams(map)), callback);
    }

    public static final long PLANSELECTHOUSESTYLEBYONESELFPLAN = 1542418619365L;

    /**
     * 根据自己所创建的方案查询出风格列表
     *
     * @param map
     */
    public void planselectHouseStyleByOneselfPlan(Map<String, String> map) {
        HttpCallback callback = new HttpCallback<PlanselectHouseStyleByOneselfPlanBean>(mContext, this) {
            @Override
            protected void onSuccess(PlanselectHouseStyleByOneselfPlanBean baseBean) {
                mView.onSuccess(baseBean, PLANSELECTHOUSESTYLEBYONESELFPLAN);
            }

            @Override
            protected void onFailure(Throwable e) {
                mView.onFailure(e, PLANSELECTHOUSESTYLEBYONESELFPLAN);
            }
        };
        startRequest(HttpBuilder.httpService.planselectHouseStyleByOneselfPlan(AppParams.getParams(map)), callback);
    }

    public static final long PLANSELECTPLANOFFACTORY = 1542418619366L;

    /**
     * 平板底部的方案里查询对应工厂下级的方案
     *
     * @param map
     */
    public void planselectPlanOfFactory(Map<String, String> map) {
        HttpCallback callback = new HttpCallback<PlanselectPlanOfFactoryBean>(mContext, this, true) {
            @Override
            protected void onSuccess(PlanselectPlanOfFactoryBean baseBean) {
                mView.onSuccess(baseBean, PLANSELECTPLANOFFACTORY);
            }

            @Override
            protected void onFailure(Throwable e) {
                mView.onFailure(e, PLANSELECTPLANOFFACTORY);
            }
        };
        startRequest(HttpBuilder.httpService.planselectPlanOfFactory(AppParams.getParams(map)), callback);
    }

    public static final long PLANSELECTPLANOFONESELF = 1542418619367L;

    /**
     * 查询自己的方案
     *
     * @param map
     */
    public void planselectPlanOfOneself(Map<String, String> map) {
        HttpCallback callback = new HttpCallback<PlanselectPlanOfOneselfBean>(mContext, this, true) {
            @Override
            protected void onSuccess(PlanselectPlanOfOneselfBean baseBean) {
                mView.onSuccess(baseBean, PLANSELECTPLANOFONESELF);
            }

            @Override
            protected void onFailure(Throwable e) {
                mView.onFailure(e, PLANSELECTPLANOFONESELF);
            }
        };
        startRequest(HttpBuilder.httpService.planselectPlanOfOneself(AppParams.getParams(map)), callback);
    }

    public static final long PLANSELECTPLANOFONESELFDESIGNER = 1542418619368L;

    /**
     * 查询自己的所有设计师下的所有方案
     *
     * @param map
     */
    public void planselectPlanOfOneselfDesigner(Map<String, String> map) {
        HttpCallback callback = new HttpCallback<PlanselectPlanOfOneselfDesignerBean>(mContext, this) {
            @Override
            protected void onSuccess(PlanselectPlanOfOneselfDesignerBean baseBean) {
                mView.onSuccess(baseBean, PLANSELECTPLANOFONESELFDESIGNER);
            }

            @Override
            protected void onFailure(Throwable e) {
                mView.onFailure(e, PLANSELECTPLANOFONESELFDESIGNER);
            }
        };
        startRequest(HttpBuilder.httpService.planselectPlanOfOneselfDesigner(AppParams.getParams(map)), callback);
    }

    public static final long PLANSETAUDITSTATE = 1542418619369L;

    /**
     * 店铺设置审核状
     *
     * @param map
     */
    public void plansetAuditState(Map<String, String> map) {
        HttpCallback callback = new HttpCallback<PlansetAuditStateBean>(mContext, this) {
            @Override
            protected void onSuccess(PlansetAuditStateBean baseBean) {
                mView.onSuccess(baseBean, PLANSETAUDITSTATE);
            }

            @Override
            protected void onFailure(Throwable e) {
                mView.onFailure(e, PLANSETAUDITSTATE);
            }
        };
        startRequest(HttpBuilder.httpService.plansetAuditState(AppParams.getParams(map)), callback);
    }


    public static final long PLANSELECTPLANBYID = 1542439923222L;

    /**
     * 查询方案详情
     *
     * @param map
     */
    public void planselectPlanById(Map<String, String> map) {
        HttpCallback callback = new HttpCallback<PlanselectPlanByIdBean>(mContext, this) {
            @Override
            protected void onSuccess(PlanselectPlanByIdBean baseBean) {
                mView.onSuccess(baseBean, PLANSELECTPLANBYID);
            }

            @Override
            protected void onFailure(Throwable e) {
                mView.onFailure(e, PLANSELECTPLANBYID);
            }
        };
        startRequest(HttpBuilder.httpService.planselectPlanById(AppParams.getParams(map)), callback);
    }


    public static final long PRODUCTCANCELCOLLECT = 1542505092500L;

    /**
     * 根据收藏ID批量取消我的收藏或者客户的收藏
     *
     * @param map
     */
    public void productcancelCollect(Map<String, String> map) {
        HttpCallback callback = new HttpCallback<ProductcancelCollectBean>(mContext, this) {
            @Override
            protected void onSuccess(ProductcancelCollectBean baseBean) {
                mView.onSuccess(baseBean, PRODUCTCANCELCOLLECT);
            }

            @Override
            protected void onFailure(Throwable e) {
                mView.onFailure(e, PRODUCTCANCELCOLLECT);
            }
        };
        startRequest(HttpBuilder.httpService.productcancelCollect(AppParams.getParams(map)), callback);
    }

    public static final long PRODUCTCOLLECTPRODUCT = 1542505092501L;

    /**
     * 帐号本身或者客户收藏或取消收藏商品商品
     *
     * @param map
     */
    public void productcollectProduct(Map<String, String> map) {
        HttpCallback callback = new HttpCallback<ProductcollectProductBean>(mContext, this, false) {
            @Override
            protected void onSuccess(ProductcollectProductBean baseBean) {
                mView.onSuccess(baseBean, PRODUCTCOLLECTPRODUCT);
            }

            @Override
            protected void onFailure(Throwable e) {
                mView.onFailure(e, PRODUCTCOLLECTPRODUCT);
            }
        };
        startRequest(HttpBuilder.httpService.productcollectProduct(AppParams.getParams(map)), callback);
    }

    public static final long PRODUCTSELECTCATEGORY2BYCATEGORY1 = 1542505092502L;

    /**
     * 根据一级分类查询二级分类（白名单接口）
     *
     * @param map
     */
    public void productselectCategory2ByCategory1(Map<String, String> map) {
        HttpCallback callback = new HttpCallback<ProductselectCategory2ByCategory1Bean>(mContext, this) {
            @Override
            protected void onSuccess(ProductselectCategory2ByCategory1Bean baseBean) {
                mView.onSuccess(baseBean, PRODUCTSELECTCATEGORY2BYCATEGORY1);
            }

            @Override
            protected void onFailure(Throwable e) {
                mView.onFailure(e, PRODUCTSELECTCATEGORY2BYCATEGORY1);
            }
        };
        startRequest(HttpBuilder.httpService.productselectCategory2ByCategory1(AppParams.getParams(map)), callback);
    }

    public static final long PRODUCTSELECTCATEGORY3BYCATEGORY2 = 1542505092503L;

    /**
     * 根据二级分类查询三级分类（白名单接口）
     *
     * @param map
     */
    public void productselectCategory3ByCategory2(Map<String, String> map) {
        HttpCallback callback = new HttpCallback<ProductselectCategory3ByCategory2Bean>(mContext, this) {
            @Override
            protected void onSuccess(ProductselectCategory3ByCategory2Bean baseBean) {
                mView.onSuccess(baseBean, PRODUCTSELECTCATEGORY3BYCATEGORY2);
            }

            @Override
            protected void onFailure(Throwable e) {
                mView.onFailure(e, PRODUCTSELECTCATEGORY3BYCATEGORY2);
            }
        };
        startRequest(HttpBuilder.httpService.productselectCategory3ByCategory2(AppParams.getParams(map)), callback);
    }

    public static final long PRODUCTSELECTPRODUCTBYCATEGORY = 1542505092504L;

    /**
     * 根据分类查询商品或联合关键字查询（白名单接口）
     *
     * @param map
     */
    public void productselectProductByCategory(Map<String, String> map, boolean isShow) {
        HttpCallback callback = new HttpCallback<ProductselectProductByCategoryBean>(mContext, this, isShow) {
            @Override
            protected void onSuccess(ProductselectProductByCategoryBean baseBean) {
                mView.onSuccess(baseBean, PRODUCTSELECTPRODUCTBYCATEGORY);
            }

            @Override
            protected void onFailure(Throwable e) {
                mView.onFailure(e, PRODUCTSELECTPRODUCTBYCATEGORY);
            }
        };
        startRequest(HttpBuilder.httpService.productselectProductByCategory(AppParams.getParams(map)), callback);
    }

    public static final long PRODUCTSELECTPRODUCTBYID = 1542505092505L;

    /**
     * 根据商品ID查询商品详情（白名单接口）
     *
     * @param map
     */
    public void productselectProductById(Map<String, String> map) {
        HttpCallback callback = new HttpCallback<ProductsdetailsBean>(mContext, this) {
            @Override
            protected void onSuccess(ProductsdetailsBean baseBean) {
                mView.onSuccess(baseBean, PRODUCTSELECTPRODUCTBYID);
            }

            @Override
            protected void onFailure(Throwable e) {
                mView.onFailure(e, PRODUCTSELECTPRODUCTBYID);
            }
        };
        startRequest(HttpBuilder.httpService.productselectProductById(AppParams.getParams(map)), callback);
    }

    public static final long PRODUCTSELECTPRODUCTOFCOLLECT = 1542505092506L;

    /**
     * 查询我的收藏商品或者客户的收藏商品
     *
     * @param map
     */
    public void productselectProductOfCollect(Map<String, String> map) {
        HttpCallback callback = new HttpCallback<ProductselectProductOfCollectBean>(mContext, this) {
            @Override
            protected void onSuccess(ProductselectProductOfCollectBean baseBean) {
                mView.onSuccess(baseBean, PRODUCTSELECTPRODUCTOFCOLLECT);
            }

            @Override
            protected void onFailure(Throwable e) {
                mView.onFailure(e, PRODUCTSELECTPRODUCTOFCOLLECT);
            }
        };
        startRequest(HttpBuilder.httpService.productselectProductOfCollect(AppParams.getParams(map)), callback);
    }

    public static final long BRANDSELECTBRANDBYCATEGORY1 = 1542505092507L;

    /**
     * 根据一级分类查询品牌列表（白名单接口）
     *
     * @param map
     */
    public void brandselectBrandByCategory1(Map<String, String> map) {
        HttpCallback callback = new HttpCallback<BrandselectBrandByCategory1Bean>(mContext, this) {
            @Override
            protected void onSuccess(BrandselectBrandByCategory1Bean baseBean) {
                mView.onSuccess(baseBean, BRANDSELECTBRANDBYCATEGORY1);
            }

            @Override
            protected void onFailure(Throwable e) {
                mView.onFailure(e, BRANDSELECTBRANDBYCATEGORY1);
            }
        };
        startRequest(HttpBuilder.httpService.brandselectBrandByCategory1(AppParams.getParams(map)), callback);
    }

    public static final long BRANDSELECTBRANDBYID = 1542505092508L;

    /**
     * 查询品牌详情（白名单接口）
     *
     * @param map
     */
    public void brandselectBrandById(Map<String, String> map) {
        HttpCallback callback = new HttpCallback<BrandselectBrandByIdBean>(mContext, this) {
            @Override
            protected void onSuccess(BrandselectBrandByIdBean baseBean) {
                mView.onSuccess(baseBean, BRANDSELECTBRANDBYID);
            }

            @Override
            protected void onFailure(Throwable e) {
                mView.onFailure(e, BRANDSELECTBRANDBYID);
            }
        };
        startRequest(HttpBuilder.httpService.brandselectBrandById(AppParams.getParams(map)), callback);
    }


    public static final long BRANDSELECTBRANDCATEGORY1 = 1542524368769L;

    /**
     * 查询品牌一级分类（白名单接口）
     *
     * @param map
     */
    public void brandselectBrandCategory1(Map<String, String> map) {
        HttpCallback callback = new HttpCallback<BrandselectBrandCategory1Bean>(mContext, this) {
            @Override
            protected void onSuccess(BrandselectBrandCategory1Bean baseBean) {
                mView.onSuccess(baseBean, BRANDSELECTBRANDCATEGORY1);
            }

            @Override
            protected void onFailure(Throwable e) {
                mView.onFailure(e, BRANDSELECTBRANDCATEGORY1);
            }
        };
        startRequest(HttpBuilder.httpService.brandselectBrandCategory1(AppParams.getParams(map)), callback);
    }

    public static final long BRANDSELECTCATEGORY2BYBRANDID = 1542529578315L;

    /**
     * 根据品牌ID查询二级分类（白名单接口）
     *
     * @param map
     */
    public void brandselectCategory2ByBrandId(Map<String, String> map) {
        HttpCallback callback = new HttpCallback<BrandselectCategory2ByBrandIdBean>(mContext, this) {
            @Override
            protected void onSuccess(BrandselectCategory2ByBrandIdBean baseBean) {
                mView.onSuccess(baseBean, BRANDSELECTCATEGORY2BYBRANDID);
            }

            @Override
            protected void onFailure(Throwable e) {
                mView.onFailure(e, BRANDSELECTCATEGORY2BYBRANDID);
            }
        };
        startRequest(HttpBuilder.httpService.brandselectCategory2ByBrandId(AppParams.getParams(map)), callback);
    }


    public static final long PRODUCTSELECTPRODUCTCATEGORY1 = 1542532319492L;

    /**
     * 查询商品一级分类（白名单接口）
     *
     * @param map
     */
    public void productselectProductCategory1(Map<String, String> map) {
        HttpCallback callback = new HttpCallback<ProductselectProductCategory1Bean>(mContext, this, false) {
            @Override
            protected void onSuccess(ProductselectProductCategory1Bean baseBean) {
                mView.onSuccess(baseBean, PRODUCTSELECTPRODUCTCATEGORY1);
            }

            @Override
            protected void onFailure(Throwable e) {
                mView.onFailure(e, PRODUCTSELECTPRODUCTCATEGORY1);
            }
        };
        startRequest(HttpBuilder.httpService.productselectProductCategory1(AppParams.getParams(map)), callback);
    }

    public static final long YQXLOGIN = 1539228874266L;

    /**
     * 商品详情
     *
     * @param map
     */
    public void yqxLogin(Map<String, String> map) {
        HttpCallback callback = new HttpCallback<YqxLoginBean>(mContext, this) {
            @Override
            protected void onSuccess(YqxLoginBean baseBean) {
                mView.onSuccess(baseBean, YQXLOGIN);
            }

            @Override
            protected void onFailure(Throwable e) {
                mView.onFailure(e, YQXLOGIN);
            }
        };
        startRequest(HttpBuilder.httpService.yqxLogin(AppParams.getParams(map)), callback);
    }


    public static final long APPGETVERSIONINFO = 1542603633325L;

    /**
     * 获取平板APP版本信息（白名单接口）
     *
     * @param map
     */
    public void appgetVersionInfo(Map<String, String> map) {
        HttpCallback callback = new HttpCallback<UpgradeBean>(mContext, this) {
            @Override
            protected void onSuccess(UpgradeBean baseBean) {
                mView.onSuccess(baseBean, APPGETVERSIONINFO);
            }

            @Override
            protected void onFailure(Throwable e) {
                mView.onFailure(e, APPGETVERSIONINFO);
            }
        };
        startRequest(HttpBuilder.httpService.getVersion(AppParams.getParams(map)), callback);
    }

    public static final long APPSELECTHOMEDATA = 1542614686749L;

    /**
     * 获取首页数据（白名单接口）
     *
     * @param map
     */
    public void appselectHomeData(Map<String, String> map) {
        HttpCallback callback = new HttpCallback<AppselectHomeDataBean>(mContext, this) {
            @Override
            protected void onSuccess(AppselectHomeDataBean baseBean) {
                mView.onSuccess(baseBean, APPSELECTHOMEDATA);
            }

            @Override
            protected void onFailure(Throwable e) {
                mView.onFailure(e, APPSELECTHOMEDATA);
            }
        };
        startRequest(HttpBuilder.httpService.appselectHomeData(AppParams.getParams(map)), callback);
    }


    public static final long HANDBAGSELECTALLHOUSETYPE = 1542851886574L;

    /**
     * 查询所有户型
     *
     * @param map
     */
    public void handbagselectAllHouseType(Map<String, String> map) {
        HttpCallback callback = new HttpCallback<HandbagselectAllHouseTypeBean>(mContext, this) {
            @Override
            protected void onSuccess(HandbagselectAllHouseTypeBean baseBean) {
                mView.onSuccess(baseBean, HANDBAGSELECTALLHOUSETYPE);
            }

            @Override
            protected void onFailure(Throwable e) {
                mView.onFailure(e, HANDBAGSELECTALLHOUSETYPE);
            }
        };
        startRequest(HttpBuilder.httpService.handbagselectAllHouseType(AppParams.getParams(map)), callback);
    }

    public static final long HANDBAGSELECTHANDBAGCATEGORY = 1542851886575L;

    /**
     * 查询拎包入住分类
     *
     * @param map
     */
    public void handbagselectHandbagCategory(Map<String, String> map) {
        HttpCallback callback = new HttpCallback<HandbagselectHandbagCategoryBean>(mContext, this) {
            @Override
            protected void onSuccess(HandbagselectHandbagCategoryBean baseBean) {
                mView.onSuccess(baseBean, HANDBAGSELECTHANDBAGCATEGORY);
            }

            @Override
            protected void onFailure(Throwable e) {
                mView.onFailure(e, HANDBAGSELECTHANDBAGCATEGORY);
            }
        };
        startRequest(HttpBuilder.httpService.handbagselectHandbagCategory(AppParams.getParams(map)), callback);
    }

    public static final long HANDBAGSELECTLAYOUT = 1542851886576L;

    /**
     * 查询布局
     *
     * @param map
     */
    public void handbagselectLayout(Map<String, String> map) {
        HttpCallback callback = new HttpCallback<HandbagselectLayoutBean>(mContext, this) {
            @Override
            protected void onSuccess(HandbagselectLayoutBean baseBean) {
                mView.onSuccess(baseBean, HANDBAGSELECTLAYOUT);
            }

            @Override
            protected void onFailure(Throwable e) {
                mView.onFailure(e, HANDBAGSELECTLAYOUT);
            }
        };
        startRequest(HttpBuilder.httpService.handbagselectLayout(AppParams.getParams(map)), callback);
    }

    public static final long SHARESHOPPINGCART = 1542851886577L;

    /**
     * 拎包入住分享
     *
     * @param map
     */
    public void shareShoppingCart(Map<String, String> map) {
        HttpCallback callback = new HttpCallback<ShareShoppingCartBean>(mContext, this) {
            @Override
            protected void onSuccess(ShareShoppingCartBean baseBean) {
                mView.onSuccess(baseBean, SHARESHOPPINGCART);
            }

            @Override
            protected void onFailure(Throwable e) {
                mView.onFailure(e, SHARESHOPPINGCART);
            }
        };
        startRequest(HttpBuilder.httpService.shareShoppingCart(AppParams.getParams(map)), callback);
    }

    public static final long BATCHCOLLECTPRODUCT = 1542851886578L;

    /**
     * 批量收藏商品
     *
     * @param map
     */
    public void batchCollectProduct(Map<String, String> map) {
        HttpCallback callback = new HttpCallback<BatchCollectProductBean>(mContext, this) {
            @Override
            protected void onSuccess(BatchCollectProductBean baseBean) {
                mView.onSuccess(baseBean, BATCHCOLLECTPRODUCT);
            }

            @Override
            protected void onFailure(Throwable e) {
                mView.onFailure(e, BATCHCOLLECTPRODUCT);
            }
        };
        startRequest(HttpBuilder.httpService.batchCollectProduct(AppParams.getParams(map)), callback);
    }

    public static final long PRODUCTSSHARE = 1538100874354L;

    /**
     * 产品分享
     *
     * @param map
     */
    public void productsshare(Map<String, String> map) {
        HttpCallback callback = new HttpCallback<ProductsshareBean>(mContext, this, false) {
            @Override
            protected void onSuccess(ProductsshareBean baseBean) {
                mView.onSuccess(baseBean, PRODUCTSSHARE);
            }

            @Override
            protected void onFailure(Throwable e) {
                mView.onFailure(e, PRODUCTSSHARE);
            }
        };
        startRequest(HttpBuilder.httpService.productsshare(AppParams.getParams(map)), callback);
    }


    public static final long BRANDSELECTBRANDBYCATEGORYANDOLDHOUSESTYLE = 1542947231876L;

    /**
     * 根据(一/二/三级)分类和老系统的风格查询品牌列表
     *
     * @param map
     */
    public void brandselectBrandByCategoryAndOldHouseStyle(Map<String, String> map) {
        HttpCallback callback = new HttpCallback<BrandselectBrandByCategoryAndOldHouseStyleBean>(mContext, this) {
            @Override
            protected void onSuccess(BrandselectBrandByCategoryAndOldHouseStyleBean baseBean) {
                mView.onSuccess(baseBean, BRANDSELECTBRANDBYCATEGORYANDOLDHOUSESTYLE);
            }

            @Override
            protected void onFailure(Throwable e) {
                mView.onFailure(e, BRANDSELECTBRANDBYCATEGORYANDOLDHOUSESTYLE);
            }
        };
        startRequest(HttpBuilder.httpService.brandselectBrandByCategoryAndOldHouseStyle(AppParams.getParams(map)), callback);
    }

    public static final long BRANDSELECTOLDHOUSESTYLEBYCATEGORY = 1542947231877L;

    /**
     * 根据(一/二/三级)分类查询老系统风格列表，其中category=-1时表示主推品
     *
     * @param map
     */
    public void brandselectOldHouseStyleByCategory(Map<String, String> map) {
        HttpCallback callback = new HttpCallback<BrandselectOldHouseStyleByCategoryBean>(mContext, this) {
            @Override
            protected void onSuccess(BrandselectOldHouseStyleByCategoryBean baseBean) {
                mView.onSuccess(baseBean, BRANDSELECTOLDHOUSESTYLEBYCATEGORY);
            }

            @Override
            protected void onFailure(Throwable e) {
                mView.onFailure(e, BRANDSELECTOLDHOUSESTYLEBYCATEGORY);
            }
        };
        startRequest(HttpBuilder.httpService.brandselectOldHouseStyleByCategory(AppParams.getParams(map)), callback);
    }


    public static final long PLANSELECTALLHOUSESTYLEANDHOUSESTYLE = 1543541060333L;

    /**
     * 查询方案列表（包含平台方案列表和工厂的方案列表）
     *
     * @param map
     */
    public void planselectAllHouseStyleAndHousestyle(Map<String, String> map) {
        HttpCallback callback = new HttpCallback<PlanselectAllHouseStyleAndHousestyleBean>(mContext, this) {
            @Override
            protected void onSuccess(PlanselectAllHouseStyleAndHousestyleBean baseBean) {
                mView.onSuccess(baseBean, PLANSELECTALLHOUSESTYLEANDHOUSESTYLE);
            }

            @Override
            protected void onFailure(Throwable e) {
                mView.onFailure(e, PLANSELECTALLHOUSESTYLEANDHOUSESTYLE);
            }
        };
        startRequest(HttpBuilder.httpService.planselectAllHouseStyleAndHousestyle(AppParams.getParams(map)), callback);
    }

    public static final long PLANSELECTDESIGNBYID = 1543541060334L;

    /**
     * 查询平台方案详情
     *
     * @param map
     */
    public void planselectDesignById(Map<String, String> map) {
        HttpCallback callback = new HttpCallback<PlanselectPlanByIdBean>(mContext, this) {
            @Override
            protected void onSuccess(PlanselectPlanByIdBean baseBean) {
                mView.onSuccess(baseBean, PLANSELECTDESIGNBYID);
            }

            @Override
            protected void onFailure(Throwable e) {
                mView.onFailure(e, PLANSELECTDESIGNBYID);
            }
        };
        startRequest(HttpBuilder.httpService.planselectDesignById(AppParams.getParams(map)), callback);
    }

    public static final long PLANSELECTDESIGNLIST = 1543541060335L;

    /**
     * 查询平台方案列表
     *
     * @param map
     */
    public void planselectDesignList(Map<String, String> map) {
        HttpCallback callback = new HttpCallback<PlanselectPlanOfFactoryBean>(mContext, this) {
            @Override
            protected void onSuccess(PlanselectPlanOfFactoryBean baseBean) {
                mView.onSuccess(baseBean, PLANSELECTDESIGNLIST);
            }

            @Override
            protected void onFailure(Throwable e) {
                mView.onFailure(e, PLANSELECTDESIGNLIST);
            }
        };
        startRequest(HttpBuilder.httpService.planselectDesignList(AppParams.getParams(map)), callback);
    }


    public static final long GOODSADDGOODS = 1544497272289L;

    /**
     * 添加商品（仅工厂和店铺拥有该权限）
     *
     * @param map
     */
    public void goodsaddGoods(Map<String, String> map) {
        HttpCallback callback = new HttpCallback<GoodsaddGoodsBean>(mContext, this) {
            @Override
            protected void onSuccess(GoodsaddGoodsBean baseBean) {
                mView.onSuccess(baseBean, GOODSADDGOODS);
            }

            @Override
            protected void onFailure(Throwable e) {
                mView.onFailure(e, GOODSADDGOODS);
            }
        };
        startRequest(HttpBuilder.httpService.goodsaddGoods(AppParams.getParams(map)), callback);
    }

    public static final long GOODSADDGOODSSPECIFICATION = 1544497272290L;

    /**
     * 添加商品规格（仅工厂和店铺拥有该权限）
     *
     * @param map
     */
    public void goodsaddGoodsSpecification(Map<String, RequestBody> files, Map<String, String> map) {
        HttpCallback callback = new HttpCallback<GoodsaddGoodsSpecificationBean>(mContext, this) {
            @Override
            protected void onSuccess(GoodsaddGoodsSpecificationBean baseBean) {
                mView.onSuccess(baseBean, GOODSADDGOODSSPECIFICATION);
            }

            @Override
            protected void onFailure(Throwable e) {
                mView.onFailure(e, GOODSADDGOODSSPECIFICATION);
            }
        };
        startRequest(HttpBuilder.httpService.goodsaddGoodsSpecification(AppParams.getParams(map), files), callback);
    }

    public static final long GOODSDELGOODS = 1544497272291L;

    /**
     * 批量删除商品（仅工厂和店铺拥有该权限）
     *
     * @param map
     */
    public void goodsdelGoods(Map<String, String> map) {
        HttpCallback callback = new HttpCallback<GoodsdelGoodsBean>(mContext, this) {
            @Override
            protected void onSuccess(GoodsdelGoodsBean baseBean) {
                mView.onSuccess(baseBean, GOODSDELGOODS);
            }

            @Override
            protected void onFailure(Throwable e) {
                mView.onFailure(e, GOODSDELGOODS);
            }
        };
        startRequest(HttpBuilder.httpService.goodsdelGoods(AppParams.getParams(map)), callback);
    }

    public static final long GOODSEDITGOODS = 1544497272292L;

    /**
     * 修改商品（仅工厂和店铺拥有该权限）
     *
     * @param map
     */
    public void goodseditGoods(Map<String, String> map) {
        HttpCallback callback = new HttpCallback<GoodseditGoodsBean>(mContext, this) {
            @Override
            protected void onSuccess(GoodseditGoodsBean baseBean) {
                mView.onSuccess(baseBean, GOODSEDITGOODS);
            }

            @Override
            protected void onFailure(Throwable e) {
                mView.onFailure(e, GOODSEDITGOODS);
            }
        };
        startRequest(HttpBuilder.httpService.goodseditGoods(AppParams.getParams(map)), callback);
    }

    public static final long GOODSPUTAWAYGOODS = 1544497272293L;

    /**
     * 批量上下架商品（仅工厂和店铺拥有该权限）
     *
     * @param map
     */
    public void goodsputawayGoods(Map<String, String> map) {
        HttpCallback callback = new HttpCallback<GoodsputawayGoodsBean>(mContext, this) {
            @Override
            protected void onSuccess(GoodsputawayGoodsBean baseBean) {
                mView.onSuccess(baseBean, GOODSPUTAWAYGOODS);
            }

            @Override
            protected void onFailure(Throwable e) {
                mView.onFailure(e, GOODSPUTAWAYGOODS);
            }
        };
        startRequest(HttpBuilder.httpService.goodsputawayGoods(AppParams.getParams(map)), callback);
    }

    public static final long GOODSSELECTGOODSCATEGORY = 1544497272294L;

    /**
     * 查询商品分类
     *
     * @param map
     */
    public void goodsselectGoodsCategory(Map<String, String> map) {
        HttpCallback callback = new HttpCallback<GoodsselectGoodsCategoryBean>(mContext, this) {
            @Override
            protected void onSuccess(GoodsselectGoodsCategoryBean baseBean) {
                mView.onSuccess(baseBean, GOODSSELECTGOODSCATEGORY);
            }

            @Override
            protected void onFailure(Throwable e) {
                mView.onFailure(e, GOODSSELECTGOODSCATEGORY);
            }
        };
        startRequest(HttpBuilder.httpService.goodsselectGoodsCategory(AppParams.getParams(map)), callback);
    }

    public static final long GOODSSELECTPERMGOODSLIST = 1544497272295L;

    /**
     * 查询商品规格
     *
     * @param map
     */
    public void goodsselectPermGoodsList(Map<String, String> map, boolean isShow) {
        HttpCallback callback = new HttpCallback<GoodsselectPermGoodsListBean>(mContext, this, isShow) {
            @Override
            protected void onSuccess(GoodsselectPermGoodsListBean baseBean) {
                mView.onSuccess(baseBean, GOODSSELECTPERMGOODSLIST);
            }

            @Override
            protected void onFailure(Throwable e) {
                mView.onFailure(e, GOODSSELECTPERMGOODSLIST);
            }
        };
        startRequest(HttpBuilder.httpService.goodsselectPermGoodsList(AppParams.getParams(map)), callback);
    }

    public static final long GOODSSUBMITGOODSAUDIT = 1544497272296L;

    /**
     * 批量提交商品审核（仅工厂和店铺拥有该权限）
     *
     * @param map
     */
    public void goodssubmitGoodsAudit(Map<String, String> map) {
        HttpCallback callback = new HttpCallback<GoodssubmitGoodsAuditBean>(mContext, this) {
            @Override
            protected void onSuccess(GoodssubmitGoodsAuditBean baseBean) {
                mView.onSuccess(baseBean, GOODSSUBMITGOODSAUDIT);
            }

            @Override
            protected void onFailure(Throwable e) {
                mView.onFailure(e, GOODSSUBMITGOODSAUDIT);
            }
        };
        startRequest(HttpBuilder.httpService.goodssubmitGoodsAudit(AppParams.getParams(map)), callback);
    }


    public static final long GOODSSELECTGOODSSPECIFICATION = 1544688025834L;

    /**
     * 查询商品规格（仅管理员/工厂/店铺拥有该权限）
     *
     * @param map
     */
    public void goodsselectGoodsSpecification(Map<String, String> map) {
        HttpCallback callback = new HttpCallback<GoodsselectGoodsSpecificationBean>(mContext, this) {
            @Override
            protected void onSuccess(GoodsselectGoodsSpecificationBean baseBean) {
                mView.onSuccess(baseBean, GOODSSELECTGOODSSPECIFICATION);
            }

            @Override
            protected void onFailure(Throwable e) {
                mView.onFailure(e, GOODSSELECTGOODSSPECIFICATION);
            }
        };
        startRequest(HttpBuilder.httpService.goodsselectGoodsSpecification(AppParams.getParams(map)), callback);
    }


    public static final long GOODSDELGOODSSPECIFICATION = 1544754487336L;

    /**
     * 批量删除商品规格（仅工厂和店铺拥有该权限）
     *
     * @param map
     */
    public void goodsdelGoodsSpecification(Map<String, String> map) {
        HttpCallback callback = new HttpCallback<GoodsdelGoodsSpecificationBean>(mContext, this) {
            @Override
            protected void onSuccess(GoodsdelGoodsSpecificationBean baseBean) {
                mView.onSuccess(baseBean, GOODSDELGOODSSPECIFICATION);
            }

            @Override
            protected void onFailure(Throwable e) {
                mView.onFailure(e, GOODSDELGOODSSPECIFICATION);
            }
        };
        startRequest(HttpBuilder.httpService.goodsdelGoodsSpecification(AppParams.getParams(map)), callback);
    }

    public static final long GOODSEDITGOODSSPECIFICATION = 1544754487337L;

    /**
     * 修改商品规格（仅工厂和店铺拥有该权限）
     *
     * @param map
     */
    public void goodseditGoodsSpecification(Map<String, RequestBody> files, Map<String, String> map) {
        HttpCallback callback = new HttpCallback<GoodseditGoodsSpecificationBean>(mContext, this) {
            @Override
            protected void onSuccess(GoodseditGoodsSpecificationBean baseBean) {
                mView.onSuccess(baseBean, GOODSEDITGOODSSPECIFICATION);
            }

            @Override
            protected void onFailure(Throwable e) {
                mView.onFailure(e, GOODSEDITGOODSSPECIFICATION);
            }
        };
        startRequest(HttpBuilder.httpService.goodseditGoodsSpecification(AppParams.getParams(map), files), callback);
    }

    /**
     * 修改商品规格（仅工厂和店铺拥有该权限）
     *
     * @param map
     */
    public void goodseditGoodsSpecificationNoFile(Map<String, String> map) {
        HttpCallback callback = new HttpCallback<GoodseditGoodsSpecificationBean>(mContext, this) {
            @Override
            protected void onSuccess(GoodseditGoodsSpecificationBean baseBean) {
                mView.onSuccess(baseBean, GOODSEDITGOODSSPECIFICATION);
            }

            @Override
            protected void onFailure(Throwable e) {
                mView.onFailure(e, GOODSEDITGOODSSPECIFICATION);
            }
        };
        startRequest(HttpBuilder.httpService.goodseditGoodsSpecificationNoFile(AppParams.getParams(map)), callback);
    }


    public static final long GOODSSELECTCATEGORYBYCATEGORY3ID = 1544774984717L;

    /**
     * 根据三级分类查出对应的一级，二级，三级分类
     *
     * @param map
     */
    public void goodsselectCategoryByCategory3Id(Map<String, String> map) {
        HttpCallback callback = new HttpCallback<GoodsselectCategoryByCategory3IdBean>(mContext, this, false) {
            @Override
            protected void onSuccess(GoodsselectCategoryByCategory3IdBean baseBean) {
                mView.onSuccess(baseBean, GOODSSELECTCATEGORYBYCATEGORY3ID);
            }

            @Override
            protected void onFailure(Throwable e) {
                mView.onFailure(e, GOODSSELECTCATEGORYBYCATEGORY3ID);
            }
        };
        startRequest(HttpBuilder.httpService.goodsselectCategoryByCategory3Id(AppParams.getParams(map)), callback);
    }
    public static final long SELECTCOMBO = 1544774984718L;
    /**
     * 查询套餐详情
     * @param params
     */
    public void selectCombo(Map<String,String> params){
//        String json = "{\n" +
//                "    \"code\": 0,\n" +
//                "    \"data\": {\n" +
//                "        \"comboArea\": \"10\",\n" +
//                "        \"comboName\": \"test1\",\n" +
//                "        \"detailsImage\": [\n" +
//                "            \"http://images.ogmall.com/0fc09532-481b-4c50-939a-77a2f7bf6d90\",\n" +
//                "            \"http://images.ogmall.com/5a035121-e15d-44e0-ae3d-affacbd4383d\",\n" +
//                "            \"http://images.ogmall.com/0c20fe39-3cb0-47a8-a870-b07cdcc1a8bd\"\n" +
//                "        ],\n" +
//                "        \"headImage\": \"http://images.ogmall.com/1f2c67bc-9390-49a6-801a-c7acd0ca5bc6\",\n" +
//                "        \"introduce\": \"北欧风格是指欧洲北部国家挪威、丹麦、瑞典、芬兰及冰岛等国的艺术设计风格，北欧风格起源于斯堪的那维亚地区的设计风格，因此也被称为“斯堪的纳维亚风格”。具有简约、自然、人性化的特点。北欧风格在使大众利益得到关注的同时，北欧设计没有缺失对小众的关怀。例如消除残障人士在生活上的不便，为其设计便捷的人性设计，实现社会公平。它们都体现了北欧风格设计对人的周全关心\",\n" +
//                "        \"rich\": [\n" +
//                "            {\n" +
//                "                \"color\": \"黑檀色\",\n" +
//                "                \"headImage\": \"http://images.ogmall.com/fac84fa6-a3cd-4e1e-893e-08b5020521bd\",\n" +
//                "                \"id\": 55711,\n" +
//                "                \"number\": 2,\n" +
//                "                \"productCategory\": \"床\",\n" +
//                "                \"productName\": \"B07 芙森堡家具 法式新古典 2米/1.8米床 俄罗斯桦木/皮 香槟金/珍珠白/香槟银/黑檀色\",\n" +
//                "                \"productOgCode\": \"OABA353A0527\",\n" +
//                "                \"sellPrice\": 1.0,\n" +
//                "                \"spec\": \"2400*2550*1830建议床垫尺寸:2000*2200\"\n" +
//                "            }\n" +
//                "        ],\n" +
//                "        \"room\": [\n" +
//                "            {\n" +
//                "                \"mainCombo\": [\n" +
//                "                    {\n" +
//                "                        \"color\": \"香槟银\",\n" +
//                "                        \"headImage\": \"http://images.ogmall.com/6a084990-8702-41f5-b54a-a45bff64b136\",\n" +
//                "                        \"id\": 55662,\n" +
//                "                        \"number\": 3,\n" +
//                "                        \"productCategory\": \"方几\",\n" +
//                "                        \"productName\": \"B07 芙森堡家具 法式新古典 方几 俄罗斯桦木,大理石(冰花玉) 香槟金/珍珠白/香槟银/黑檀色\",\n" +
//                "                        \"productOgCode\": \"OABA353A0352\",\n" +
//                "                        \"sellPrice\": 2.0,\n" +
//                "                        \"spec\": \"800*800*505(不含大理石)\"\n" +
//                "                    }\n" +
//                "                ],\n" +
//                "                \"roomName\": \"space1\",\n" +
//                "                \"viceCombo\": [\n" +
//                "                    {\n" +
//                "                        \"color\": \"香槟金\",\n" +
//                "                        \"headImage\": \"http://images.ogmall.com/OABA353A042.jpg\",\n" +
//                "                        \"id\": 4773,\n" +
//                "                        \"number\": 4,\n" +
//                "                        \"productCategory\": \"床\",\n" +
//                "                        \"productName\": \"B07 芙森堡家具 法式新古典 1.8米/1.5米床 俄罗斯桦木/按图皮板为：829皮 香槟金/珍珠白/香槟银/黑檀色\",\n" +
//                "                        \"productOgCode\": \"OABA353A042\",\n" +
//                "                        \"sellPrice\": 3.0,\n" +
//                "                        \"spec\": \"1950*2200*1550建议床垫尺寸:1800*2000\"\n" +
//                "                    }\n" +
//                "                ]\n" +
//                "            },\n" +
//                "            {\n" +
//                "                \"mainCombo\": [\n" +
//                "                    {\n" +
//                "                        \"color\": \"黑檀色\",\n" +
//                "                        \"headImage\": \"http://images.ogmall.com/OABA353A048 _1.jpg\",\n" +
//                "                        \"id\": 55696,\n" +
//                "                        \"number\": 2,\n" +
//                "                        \"productCategory\": \"床\",\n" +
//                "                        \"productName\": \"B07 芙森堡家具 法式新古典 1.8米床 俄罗斯桦木/按图：皮板改皮加钱 香槟金/珍珠白/香槟银/黑檀色\",\n" +
//                "                        \"productOgCode\": \"OABA353A0487\",\n" +
//                "                        \"sellPrice\": 1.0,\n" +
//                "                        \"spec\": \"2080*2320*1445建议床垫尺寸:1800*2000\"\n" +
//                "                    }\n" +
//                "                ],\n" +
//                "                \"roomName\": \"space2\",\n" +
//                "                \"viceCombo\": [\n" +
//                "                    {\n" +
//                "                        \"color\": \"香槟金\",\n" +
//                "                        \"headImage\": \"http://images.ogmall.com/9e58cf8d-05b9-4022-9fc8-f789d98254c9\",\n" +
//                "                        \"id\": 4780,\n" +
//                "                        \"number\": 5,\n" +
//                "                        \"productCategory\": \"床\",\n" +
//                "                        \"productName\": \"B07 芙森堡家具 法式新古典 1.8米床 俄罗斯桦木/按图：皮板为902皮 香槟金/珍珠白/香槟银/黑檀色\",\n" +
//                "                        \"productOgCode\": \"OABA353A051\",\n" +
//                "                        \"sellPrice\": 4.0,\n" +
//                "                        \"spec\": \"1980*2400*1670建议床垫尺寸:1800*2000\"\n" +
//                "                    }\n" +
//                "                ]\n" +
//                "            }\n" +
//                "        ],\n" +
//                "        \"vrurl\": \"xx.rl.com\"\n" +
//                "    },\n" +
//                "    \"message\": \"成功\"\n" +
//                "}";
//        SelectComboBean bean = new Gson().fromJson(json,SelectComboBean.class);
//        mView.onSuccess(bean, SELECTCOMBO);

        HttpCallback callback = new HttpCallback<SelectComboBean>(mContext, this, false) {
            @Override
            protected void onSuccess(SelectComboBean baseBean) {
                mView.onSuccess(baseBean, SELECTCOMBO);
            }

            @Override
            protected void onFailure(Throwable e) {
                mView.onFailure(e, SELECTCOMBO);
            }
        };
        startRequest(HttpBuilder.httpService.selectCombo(params), callback);
    }
    public static final long SELECTCOMBOLIST = 1544774984718L;
    /**
     * 查询套餐列表
     * @param params
     */
    public void selectComboList(Map<String,String> params){
        HttpCallback callback = new HttpCallback<SelectComboListBean>(mContext, this, false) {
            @Override
            protected void onSuccess(SelectComboListBean baseBean) {
                mView.onSuccess(baseBean, SELECTCOMBOLIST);
            }

            @Override
            protected void onFailure(Throwable e) {
                mView.onFailure(e, SELECTCOMBOLIST);
            }
        };
        startRequest(HttpBuilder.httpService.selectComboList(params), callback);
    }

}
