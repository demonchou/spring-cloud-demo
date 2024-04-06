package cn.com.stori.account.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.com.stori.account.web.dto.ResultDTO;
import cn.com.stori.account.web.facade.AccountManagerFacade;

/**
 * 账户
 * @author hzzhouhongfei
 * @version $$ AccountController, 2024/3/26 6:39 PM hzzhouhongfei $$
 */
@RequestMapping("/account")
@Controller
public class AccountController
{
	@Autowired
	private AccountManagerFacade accountManagerFacade;

	// http://127.0.0.1:8080/account/addAccount?name=testName
	@RequestMapping("/addAccount")
	@ResponseBody
	public String addAccount(@RequestParam(name = "name", defaultValue = "unknown user") String name)
	{
		ResultDTO<String> resultDTO = accountManagerFacade.addAccount(name);
		String result = resultDTO.getResult();
		return resultDTO.isSuccess() ?
				String.format("账户创建成功，name = %s，accountId = %s", name, result) :
				String.format("账户创建失败，name = %s，失败原因 = %s", name, resultDTO.getMsg());
	}

}
