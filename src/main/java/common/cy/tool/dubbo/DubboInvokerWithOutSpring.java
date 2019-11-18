package common.cy.tool.dubbo;

import com.alibaba.dubbo.config.ApplicationConfig;
import com.alibaba.dubbo.config.ReferenceConfig;
import com.netease.epay.common.util.GsonUtil;
import com.netease.epay.uc.api.dto.request.OpenAccountRequest;
import com.netease.epay.uc.api.enums.RegisterFromEnum;
import com.netease.epay.uc.api.facade.AccountFacade;

/**
 * @author hzchenya
 * @version TODO
 * @Title: DubboInvokerWithOutSpring.java
 * @Package epay.core.dubbo
 * @Description: TODO
 * @date 2017年6月12日 下午1:54:39
 */
public class DubboInvokerWithOutSpring
{
    public static void main(String[] args) {
        // 当前应用配置
        ApplicationConfig application = new ApplicationConfig();
        application.setName("local-test");
        // 连接注册中心配置
//		RegistryConfig registry = new RegistryConfig();
//		registry.setAddress("zookeeper://223.252.220.35:2181/");
//		registry.setGroup("cy-local-win");

        // 引用远程服务
        ReferenceConfig<AccountFacade> reference = new ReferenceConfig<AccountFacade>();
        reference.setApplication(application);
//		reference.setRegistry(registry); // 多个注册中心可以用setRegistries()
        reference.setInterface(AccountFacade.class);
        reference.setVersion("1.0.0");
        reference.setTimeout(20000000);
        reference.setUrl("dubbo://127.0.0.1:20885");

        // 和本地bean一样使用xxxService
        final AccountFacade proxy = reference.get();

        //绑定操作员测试
//		BindOperatorRequest request = new BindOperatorRequest();
//		request.setCoreAccountId("1630020100000100");
//		request.setOperatorId("yd.88888888888@163.com");
//		proxy.bindOperator(request);

        //注销测试
//		CancelAccountRequest request = new CancelAccountRequest();
//		request.setCoreAccountId("1630020100000100");
//		request.setOperatorId("yd.88888888888@163.com");
//		System.out.println(proxy.cancel(request).getBizResult());

        //开户测试
        final OpenAccountRequest req = new OpenAccountRequest();
//		req.setOperatorId("yd.88888888888@163.com");
        req.setOperatorId("pesport@163.com");
        req.setUserIp("127.0.0.1");
        req.setOperation("reopenAccount");
        req.setRegisterFrom(RegisterFromEnum.EWALLET);
        for (int i = 1; i <= 1; i++) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    System.out.println("Thread started ");
//                    System.out.println(GsonUtil.toJson(proxy.openAccount(req)));
                    System.out.println(GsonUtil.toJson(proxy.queryUser(req.getOperatorId())));
                }
            }).start();
        }


//		TreeSet<String> sortedRules = new TreeSet<String>(new Comparator<String>()
//		{
//			@Override
//			public int compare(String o1, String o2)
//			{
//				int order1 = Integer.parseInt(o1.split("#")[0]);
//				int order2 = Integer.parseInt(o2.split("#")[0]);
//
//				if (order1 > order2)
//				{
//					return 1;
//				}
//				else if (order1 <= order2)
//				{
//					return -1;
//				}
//				else
//				{
//					return 0;
//				}
//			}
//		});

//		sortedRules.add("23#R1");
//		sortedRules.add("23#R2");
//		sortedRules.add("23#R3");
//		sortedRules.add("2#R4");
//		sortedRules.add("15#R5");
//		sortedRules.add("8#R6");
//		sortedRules.add("28#R7");
//		sortedRules.add("24#R8");
//		sortedRules.add("33#R9");
//
//		List<String> sortedRuleList = new ArrayList<String>();
//		for (String ruleName : sortedRules)
//		{
//			sortedRuleList.add(ruleName.split("#")[1]);
//		}
//		for (String v:
//				sortedRuleList) {
//			System.out.println(v);
//		}
    }
}
