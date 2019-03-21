package com.shan.netlibrary.net;

import android.content.Context;

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
import com.shan.netlibrary.bean.UserloginBean;
import com.shan.netlibrary.bean.UserlogoutBean;
import com.shan.netlibrary.bean.YqxLoginBean;
import com.shan.netlibrary.contract.BaseView;

import java.util.Map;

import okhttp3.RequestBody;
import retrofit2.http.Body;


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

    public static final long CATEGORYS = 1530772612024L;

    /**
     * 获取产品分类列表 categoryId 不传时，获取所有的一级分类
     *
     * @param map
     */
    public void categorys(Map<String, String> map) {
        HttpCallback callback = new HttpCallback<CategorysBean>(mContext, this, false) {
            @Override
            protected void onSuccess(CategorysBean baseBean) {
                mView.onSuccess(baseBean, CATEGORYS);
            }

            @Override
            protected void onFailure(Throwable e) {
                mView.onFailure(e, CATEGORYS);
            }
        };
        startRequest(HttpBuilder.httpService.categorys(AppParams.getParams(map)), callback);
    }

    public static final long HASPRODUCTBEAN = 1530772612022L;

    /**
     * 获取产品分类列表 categoryId 不传时，获取所有的一级分类
     *
     * @param map
     */
    public void hasProduct(Map<String, String> map) {
        HttpCallback callback = new HttpCallback<HasProductBean>(mContext, this, false) {
            @Override
            protected void onSuccess(HasProductBean baseBean) {
                mView.onSuccess(baseBean, HASPRODUCTBEAN);
            }

            @Override
            protected void onFailure(Throwable e) {
                mView.onFailure(e, HASPRODUCTBEAN);
            }
        };
        startRequest(HttpBuilder.httpService.hasProduct(AppParams.getParams(map)), callback);
    }

    public static final long CENTERADDCUSTOMER = 1530772612025L;

    /**
     * 添加客户,同时建立与业务员的会话
     *
     * @param map
     */
    public void centeraddCustomer(Map<String, String> map) {
        HttpCallback callback = new HttpCallback<CenteraddCustomerBean>(mContext, this, false) {
            @Override
            protected void onSuccess(CenteraddCustomerBean baseBean) {
                mView.onSuccess(baseBean, CENTERADDCUSTOMER);
            }

            @Override
            protected void onFailure(Throwable e) {
                mView.onFailure(e, CENTERADDCUSTOMER);
            }
        };
        startRequest(HttpBuilder.httpService.centeraddCustomer(AppParams.getParams(map)), callback);
    }

    public static final long CENTERCUSTOMERS = 1530772612026L;

    /**
     * 获取用户列表
     *
     * @param map
     */
    public void centercustomers(Map<String, String> map) {
        HttpCallback callback = new HttpCallback<CentercustomersBean>(mContext, this, true) {
            @Override
            protected void onSuccess(CentercustomersBean baseBean) {
                mView.onSuccess(baseBean, CENTERCUSTOMERS);
            }

            @Override
            protected void onFailure(Throwable e) {
                mView.onFailure(e, CENTERCUSTOMERS);
            }
        };
        startRequest(HttpBuilder.httpService.centercustomers(AppParams.getParams(map)), callback);
    }

    public static final long USERLOGIN = 1530772612027L;

    /**
     * 业务员登录
     *
     * @param map
     */
    public void userlogin(Map<String, String> map) {
        HttpCallback callback = new HttpCallback<UserloginBean>(mContext, this, false) {
            @Override
            protected void onSuccess(UserloginBean baseBean) {
                mView.onSuccess(baseBean, USERLOGIN);
            }

            @Override
            protected void onFailure(Throwable e) {
                mView.onFailure(e, USERLOGIN);
            }
        };
        startRequest(HttpBuilder.httpService.userlogin(AppParams.getParams(map)), callback);
    }

    public static final long USERLOGOUT = 1530772612028L;

    /**
     * 业务员退出当前登录
     *
     * @param map
     */
    public void userlogout(Map<String, String> map) {
        HttpCallback callback = new HttpCallback<UserlogoutBean>(mContext, this, false) {
            @Override
            protected void onSuccess(UserlogoutBean baseBean) {
                mView.onSuccess(baseBean, USERLOGOUT);
            }

            @Override
            protected void onFailure(Throwable e) {
                mView.onFailure(e, USERLOGOUT);
            }
        };
        startRequest(HttpBuilder.httpService.userlogout(AppParams.getParams(map)), callback);
    }

    public static final long CUSTOMERLOGIN = 1530772612029L;

    /**
     * 业务员与客户建立会话
     *
     * @param map
     */
    public void customerlogin(Map<String, String> map) {
        HttpCallback callback = new HttpCallback<CustomerloginBean>(mContext, this, false) {
            @Override
            protected void onSuccess(CustomerloginBean baseBean) {
                mView.onSuccess(baseBean, CUSTOMERLOGIN);
            }

            @Override
            protected void onFailure(Throwable e) {
                mView.onFailure(e, CUSTOMERLOGIN);
            }
        };
        startRequest(HttpBuilder.httpService.customerlogin(AppParams.getParams(map)), callback);
    }

    public static final long CUSTOMERLOGOUT = 1530772612030L;

    /**
     * 退出当前客户
     *
     * @param map
     */
    public void customerlogout(Map<String, String> map) {
        HttpCallback callback = new HttpCallback<CustomerlogoutBean>(mContext, this, false) {
            @Override
            protected void onSuccess(CustomerlogoutBean baseBean) {
                mView.onSuccess(baseBean, CUSTOMERLOGOUT);
            }

            @Override
            protected void onFailure(Throwable e) {
                mView.onFailure(e, CUSTOMERLOGOUT);
            }
        };
        startRequest(HttpBuilder.httpService.customerlogout(AppParams.getParams(map)), callback);
    }

    public static final long CENTERUSERINFO = 1530772612031L;

    /**
     * 获取当前登录的用户数据, 和建立会话的客户信息
     *
     * @param map
     */
    public void centeruserInfo(Map<String, String> map) {
        HttpCallback callback = new HttpCallback<CenteruserInfoBean>(mContext, this, false) {
            @Override
            protected void onSuccess(CenteruserInfoBean baseBean) {
                mView.onSuccess(baseBean, CENTERUSERINFO);
            }

            @Override
            protected void onFailure(Throwable e) {
                mView.onFailure(e, CENTERUSERINFO);
            }
        };
        startRequest(HttpBuilder.httpService.centeruserInfo(AppParams.getParams(map)), callback);
    }

    public static final long PRODUCTSOFTYPE = 1530772612032L;

    /**
     * 根据类型id 获取产品数据
     *
     * @param map
     */
    public void productsoftype(Map<String, String> map) {
        HttpCallback callback = new HttpCallback<ProductsoftypeBean>(mContext, this, false) {
            @Override
            protected void onSuccess(ProductsoftypeBean baseBean) {
                mView.onSuccess(baseBean, PRODUCTSOFTYPE);
            }

            @Override
            protected void onFailure(Throwable e) {
                mView.onFailure(e, PRODUCTSOFTYPE);
            }
        };
        startRequest(HttpBuilder.httpService.productsoftype(AppParams.getParams(map)), callback);
    }

    public static final long STYLES = 1530772612033L;

    /**
     * 获取所有的风格数据列表
     *
     * @param map
     */
    public void styles(Map<String, String> map) {
        HttpCallback callback = new HttpCallback<StylesBean>(mContext, this, false) {
            @Override
            protected void onSuccess(StylesBean baseBean) {
                mView.onSuccess(baseBean, STYLES);
            }

            @Override
            protected void onFailure(Throwable e) {
                mView.onFailure(e, STYLES);
            }
        };
        startRequest(HttpBuilder.httpService.styles(AppParams.getParams(map)), callback);
    }

    public static final long CATEGORYSOFNAME = 1530772612034L;

    /**
     * 根据分类名称搜索分类信息
     *
     * @param map
     */
    public void categorysofName(Map<String, String> map) {
        HttpCallback callback = new HttpCallback<CategorysofNameBean>(mContext, this, false) {
            @Override
            protected void onSuccess(CategorysofNameBean baseBean) {
                mView.onSuccess(baseBean, CATEGORYSOFNAME);
            }

            @Override
            protected void onFailure(Throwable e) {
                mView.onFailure(e, CATEGORYSOFNAME);
            }
        };
        startRequest(HttpBuilder.httpService.categorysofName(AppParams.getParams(map)), callback);
    }

    public static final long BRANDLIST = 1527576007011L;

    /**
     * 根据风格id返回品牌列表 当风格id为空时查询所有的品牌信息
     *
     * @param map
     */
    public void brandlist(Map<String, String> map, boolean isShow) {
        HttpCallback callback = new HttpCallback<BrandlistBean>(mContext, this, isShow) {
            @Override
            protected void onSuccess(BrandlistBean baseBean) {
                mView.onSuccess(baseBean, BRANDLIST);
            }

            @Override
            protected void onFailure(Throwable e) {
                mView.onFailure(e, BRANDLIST);
            }
        };
        startRequest(HttpBuilder.httpService.brandlist(AppParams.getParams(map)), callback);
    }

    public static final long BRANDDETAILS = 1527576007012L;

    /**
     * 根据品牌id查询品牌详情
     *
     * @param map
     */
    public void branddetails(Map<String, String> map) {
        HttpCallback callback = new HttpCallback<BranddetailsBean>(mContext, this, false) {
            @Override
            protected void onSuccess(BranddetailsBean baseBean) {
                mView.onSuccess(baseBean, BRANDDETAILS);
            }

            @Override
            protected void onFailure(Throwable e) {
                mView.onFailure(e, BRANDDETAILS);
            }
        };
        startRequest(HttpBuilder.httpService.branddetails(AppParams.getParams(map)), callback);
    }

    public static final long PRODUCTSCATEGORYS = 1527576007017L;

    /**
     * 所有产品二级类目
     *
     * @param map
     */
    public void productscategorys(Map<String, String> map) {
        HttpCallback callback = new HttpCallback<ProductscategorysBean>(mContext, this, false) {
            @Override
            protected void onSuccess(ProductscategorysBean baseBean) {
                mView.onSuccess(baseBean, PRODUCTSCATEGORYS);
            }

            @Override
            protected void onFailure(Throwable e) {
                mView.onFailure(e, PRODUCTSCATEGORYS);
            }
        };
        startRequest(HttpBuilder.httpService.productscategorys(AppParams.getParams(map)), callback);
    }

    public static final long DESIGNLIST = 1527576007009L;

    /**
     * 根据风格Id所有审核通过的方案
     *
     * @param map
     */
    public void designlist(Map<String, String> map, boolean isShow) {
        HttpCallback callback = new HttpCallback<DesignlistBean>(mContext, this, isShow) {
            @Override
            protected void onSuccess(DesignlistBean baseBean) {
                mView.onSuccess(baseBean, DESIGNLIST);
            }

            @Override
            protected void onFailure(Throwable e) {
                mView.onFailure(e, DESIGNLIST);
            }
        };
        startRequest(HttpBuilder.httpService.designlist(AppParams.getParams(map)), callback);
    }

    public static final long DESIGNDETAILS = 1527576007010L;

    /**
     * 获取方案的详细信息
     *
     * @param map
     */
    public void designdetails(Map<String, String> map) {
        HttpCallback callback = new HttpCallback<DesigndetailsBean>(mContext, this, false) {
            @Override
            protected void onSuccess(DesigndetailsBean baseBean) {
                mView.onSuccess(baseBean, DESIGNDETAILS);
            }

            @Override
            protected void onFailure(Throwable e) {
                mView.onFailure(e, DESIGNDETAILS);
            }
        };
        startRequest(HttpBuilder.httpService.designdetails(AppParams.getParams(map)), callback);
    }


    public static final long PRODUCTOFCATEGOTY = 1530845342800L;

    /**
     * 根据类型id 获取产品数据
     *
     * @param map
     */
    public void productofCategoty(Map<String, String> map, boolean isShow) {
        HttpCallback callback = new HttpCallback<ProductofCategotyBean>(mContext, this, isShow) {
            @Override
            protected void onSuccess(ProductofCategotyBean baseBean) {
                mView.onSuccess(baseBean, PRODUCTOFCATEGOTY);
            }

            @Override
            protected void onFailure(Throwable e) {
                mView.onFailure(e, PRODUCTOFCATEGOTY);
            }
        };
        startRequest(HttpBuilder.httpService.productofCategoty(AppParams.getParams(map)), callback);
    }


    public static final long DESIGNEROFUSERID = 1534747251366L;

    /**
     * 根据用户id 获取获取设计师用户数据！
     *
     * @param map
     */
    public void designerofUserId(Map<String, String> map) {
        HttpCallback callback = new HttpCallback<DesignerofUserIdBean>(mContext, this) {
            @Override
            protected void onSuccess(DesignerofUserIdBean baseBean) {
                mView.onSuccess(baseBean, DESIGNEROFUSERID);
            }

            @Override
            protected void onFailure(Throwable e) {
                mView.onFailure(e, DESIGNEROFUSERID);
            }
        };
        startRequest(HttpBuilder.httpService.designerofUserId(AppParams.getParams(map)), callback);
    }

    public static final long MYDESIGNDETAILS = 1534747251367L;

    /**
     * 上设计师查看自己方案的详细数据
     *
     * @param map
     */
    public void mydesigndetails(Map<String, String> map) {
        HttpCallback callback = new HttpCallback<MyDesigndetailsBean>(mContext, this) {
            @Override
            protected void onSuccess(MyDesigndetailsBean baseBean) {
                mView.onSuccess(baseBean, MYDESIGNDETAILS);
            }

            @Override
            protected void onFailure(Throwable e) {
                mView.onFailure(e, MYDESIGNDETAILS);
            }
        };
        startRequest(HttpBuilder.httpService.designdetails(AppParams.getParams(map)), callback);
    }

    public static final long DESIGNSTYLESOFDESIGNER = 1534747251368L;

    /**
     * 获取设计师方案风格和方案列表
     *
     * @param map
     */
    public void designstylesOfdesigner(Map<String, String> map) {
        HttpCallback callback = new HttpCallback<DesignstylesOfdesignerBean>(mContext, this) {
            @Override
            protected void onSuccess(DesignstylesOfdesignerBean baseBean) {
                mView.onSuccess(baseBean, DESIGNSTYLESOFDESIGNER);
            }

            @Override
            protected void onFailure(Throwable e) {
                mView.onFailure(e, DESIGNSTYLESOFDESIGNER);
            }
        };
        startRequest(HttpBuilder.httpService.designstylesOfdesigner(AppParams.getParams(map)), callback);
    }


    public static final long BAGCATEGORYS = 1534936387700L;

    /**
     * 拎包入住模块获取产品分类数据【品牌家具，软装生活】
     *
     * @param map
     */
    public void bagcategorys(Map<String, String> map) {
        HttpCallback callback = new HttpCallback<BagcategorysBean>(mContext, this, false) {
            @Override
            protected void onSuccess(BagcategorysBean baseBean) {
                mView.onSuccess(baseBean, BAGCATEGORYS);
            }

            @Override
            protected void onFailure(Throwable e) {
                mView.onFailure(e, BAGCATEGORYS);
            }
        };
        startRequest(HttpBuilder.httpService.bagcategorys(AppParams.getParams(map)), callback);
    }

    public static final long BAGSHARE = 1534936387701L;

    /**
     * 在线设计方案一键分享
     *
     * @param map
     */
    public void bagshare(Map<String, String> map) {
        HttpCallback callback = new HttpCallback<BagshareBean>(mContext, this) {
            @Override
            protected void onSuccess(BagshareBean baseBean) {
                mView.onSuccess(baseBean, BAGSHARE);
            }

            @Override
            protected void onFailure(Throwable e) {
                mView.onFailure(e, BAGSHARE);
            }
        };
        startRequest(HttpBuilder.httpService.bagshare(AppParams.getParams(map)), callback);
    }

    public static final long CARTSAVE = 1534936387702L;

    /**
     * 保存购物车数据
     *
     * @param info
     */
    public void cartsave(@Body RequestBody info) {
        HttpCallback callback = new HttpCallback<CartsaveBean>(mContext, this) {
            @Override
            protected void onSuccess(CartsaveBean baseBean) {
                mView.onSuccess(baseBean, CARTSAVE);
            }

            @Override
            protected void onFailure(Throwable e) {
                mView.onFailure(e, CARTSAVE);
            }
        };
        startRequest(HttpBuilder.httpService.cartsave(info), callback);
    }

    public static final long ROOMTYPELIST = 1534936387703L;

    /**
     * 获取所有的户型数据
     *
     * @param map
     */
    public void roomTypelist(Map<String, String> map) {
        HttpCallback callback = new HttpCallback<RoomTypelistBean>(mContext, this, false) {
            @Override
            protected void onSuccess(RoomTypelistBean baseBean) {
                mView.onSuccess(baseBean, ROOMTYPELIST);
            }

            @Override
            protected void onFailure(Throwable e) {
                mView.onFailure(e, ROOMTYPELIST);
            }
        };
        startRequest(HttpBuilder.httpService.roomTypelist(AppParams.getParams(map)), callback);
    }

    public static final long ROOMTYPESPECS = 1534936387704L;

    /**
     * 根据户型id 获取布局数据
     *
     * @param map
     */
    public void roomTypespecs(Map<String, String> map) {
        HttpCallback callback = new HttpCallback<RoomTypespecsBean>(mContext, this) {
            @Override
            protected void onSuccess(RoomTypespecsBean baseBean) {
                mView.onSuccess(baseBean, ROOMTYPESPECS);
            }

            @Override
            protected void onFailure(Throwable e) {
                mView.onFailure(e, ROOMTYPESPECS);
            }
        };
        startRequest(HttpBuilder.httpService.roomTypespecs(AppParams.getParams(map)), callback);
    }

    public static final long PRODUCTSEARCH = 1534936387705L;

    /**
     * 产品搜索
     *
     * @param map
     */
    public void productsearch(Map<String, String> map, boolean isShow) {
        HttpCallback callback = new HttpCallback<ProductsearchBean>(mContext, this, isShow) {
            @Override
            protected void onSuccess(ProductsearchBean baseBean) {
                mView.onSuccess(baseBean, PRODUCTSEARCH);
            }

            @Override
            protected void onFailure(Throwable e) {
                mView.onFailure(e, PRODUCTSEARCH);
            }
        };
        startRequest(HttpBuilder.httpService.productsearch(AppParams.getParams(map)), callback);
    }


    public static final long CENTERDELETECOLLECTS = 1538029478197L;

    /**
     * 商品批量删除
     */
    public void centerdeletecollects(@Body RequestBody info) {
        HttpCallback callback = new HttpCallback<CenterdeletecollectsBean>(mContext, this, false) {
            @Override
            protected void onSuccess(CenterdeletecollectsBean baseBean) {
                mView.onSuccess(baseBean, CENTERDELETECOLLECTS);
            }

            @Override
            protected void onFailure(Throwable e) {
                mView.onFailure(e, CENTERDELETECOLLECTS);
            }
        };
        startRequest(HttpBuilder.httpService.centerdeletecollects(info), callback);
    }

    public static final long CENTERGETCOLLECTS = 1538029478198L;

    /**
     * 获取收藏的商品
     *
     * @param map
     */
    public void centergetcollects(Map<String, String> map, boolean isShow) {
        HttpCallback callback = new HttpCallback<CentergetcollectsBean>(mContext, this, isShow) {
            @Override
            protected void onSuccess(CentergetcollectsBean baseBean) {
                mView.onSuccess(baseBean, CENTERGETCOLLECTS);
            }

            @Override
            protected void onFailure(Throwable e) {
                mView.onFailure(e, CENTERGETCOLLECTS);
            }
        };
        startRequest(HttpBuilder.httpService.centergetcollects(AppParams.getParams(map)), callback);
    }

    public static final long CENTERSAVECOLLECTS = 1538029478199L;

    /**
     * 批量收藏
     */
    public void centersavecollects(Map<String, String> map) {
        HttpCallback callback = new HttpCallback<CentersavecollectsBean>(mContext, this, false) {
            @Override
            protected void onSuccess(CentersavecollectsBean baseBean) {
                mView.onSuccess(baseBean, CENTERSAVECOLLECTS);
            }

            @Override
            protected void onFailure(Throwable e) {
                mView.onFailure(e, CENTERSAVECOLLECTS);
            }

            @Override
            public void onService500(CentersavecollectsBean centersavecollectsBean) {
                mView.onSuccess(centersavecollectsBean, CENTERSAVECOLLECTS);
            }
        };
        startRequest(HttpBuilder.httpService.centersavecollects(AppParams.getParams(map)), callback);
    }

    public static final long COLLECTSSTYLES = 1538029478200L;

    /**
     * 获取风格列表
     *
     * @param map
     */
    public void collectsstyles(Map<String, String> map) {
        HttpCallback callback = new HttpCallback<CollectsstylesBean>(mContext, this, false) {
            @Override
            protected void onSuccess(CollectsstylesBean baseBean) {
                mView.onSuccess(baseBean, COLLECTSSTYLES);
            }

            @Override
            protected void onFailure(Throwable e) {
                mView.onFailure(e, COLLECTSSTYLES);
            }
        };
        startRequest(HttpBuilder.httpService.collectsstyles(AppParams.getParams(map)), callback);
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


    public static final long PRODUCTSDETAILS = 1539228874259L;
    /**
     * 商品详情
     * @param map
     */
    public void productsdetails(Map<String, String> map) {
        HttpCallback callback = new HttpCallback<ProductsdetailsBean>(mContext, this) {
            @Override
            protected void onSuccess(ProductsdetailsBean baseBean) {
                mView.onSuccess(baseBean, PRODUCTSDETAILS);
            }

            @Override
            protected void onFailure(Throwable e) {
                mView.onFailure(e, PRODUCTSDETAILS);
            }
        };
        startRequest(HttpBuilder.httpService.productsdetails(AppParams.getParams(map)), callback);
    }

    public static final long YQXLOGIN = 1539228874266L;
    /**
     * 商品详情
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


}
