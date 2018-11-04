package com.tl.customclothing.util.actionmapping;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import com.tl.customclothing.util.Constant;

public class ActionServlet extends HttpServlet
{
	private static final long serialVersionUID = 1L;

	private ActionMappingsConfig mappingConfig;

	@Override
	public void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		try
		{
			String path = request.getServletPath();
			path = path.substring(1, path.length() - 7);
			System.out.println("请求到达..." + path);
			ActionConfig actionConfig = this.mappingConfig.findActionConfig(path);

			if (actionConfig == null)
			{
				System.out.println("没有该请求对应的处理！");
				return;
			}

			String result = actionConfig.getResult();
			if (result != null)
			{
				request.getRequestDispatcher(result).forward(request, response);
			} else
			{
				String className = actionConfig.getClassname();

				Object obj = Class.forName(className).newInstance();
				String methodName = actionConfig.getMethod();

				Method method = obj.getClass().getMethod(methodName, new Class[]
				{ HttpServletRequest.class, HttpServletResponse.class });

				method.invoke(obj, new Object[]
				{ request, response });

				System.out.println("请求完成..." + path + "\n");
			}
		} catch (Exception e)
		{
			e.printStackTrace();
			System.out.println("ActionServlet的service方法异常");
		}
	}

	public void init() throws ServletException
	{
		try
		{
			SAXReader reader = new SAXReader();
			Document doc = reader.read(
					ActionServlet.class.getClassLoader().getResourceAsStream(Constant.MVC_ACTION_MAPPING_FILE_NAME));
			Element root = doc.getRootElement();

			this.mappingConfig = new ActionMappingsConfig();

			List actionEleList = root.selectNodes("/action-mappings/action");
			if ((actionEleList != null) && (!actionEleList.isEmpty()))
			{
				for (int i = 0; i < actionEleList.size(); i++)
				{
					Element actionEle = (Element) actionEleList.get(i);

					ActionConfig actionConfig = new ActionConfig();
					List actionAttrList = actionEle.attributes();

					String attrName = null;

					if ((actionAttrList != null) && (!actionAttrList.isEmpty()))
					{
						for (int j = 0; j < actionAttrList.size(); j++)
						{
							Attribute attr = (Attribute) actionAttrList.get(j);

							attrName = attr.getName();
							String attrValue = attr.getValue();
							Method method = actionConfig.getClass().getMethod(
									"set" + attrName.substring(0, 1).toUpperCase() + attrName.substring(1), new Class[]
							{ String.class });
							method.invoke(actionConfig, new Object[]
							{ attrValue });
						}
					}

					List resultEleList = actionEle.elements();
					if ((resultEleList != null) && (!resultEleList.isEmpty()))
					{
						for (int k = 0; k < resultEleList.size(); k++)
						{
							Element resultEle = (Element) resultEleList.get(k);

							ResultConfig resultConfig = new ResultConfig();

							List resultAttrList = resultEle.attributes();
							if ((resultAttrList != null) && (!resultAttrList.isEmpty()))
							{
								for (int l = 0; l < resultAttrList.size(); l++)
								{
									Attribute resultAttr = (Attribute) resultAttrList.get(l);

									String resultAttrName = resultAttr.getName();
									String resultAttrValue = resultAttr.getValue();
									Method method = resultConfig.getClass()
											.getMethod("set" + resultAttrName.substring(0, 1).toUpperCase()
													+ resultAttrName.substring(1), new Class[]
									{ "redirect".equals(resultAttrName) ? Boolean.TYPE : String.class });
									if ("redirect".equals(resultAttrName))
									{
										method.invoke(resultConfig, new Object[]
										{ Boolean.valueOf(Boolean.parseBoolean(resultAttrValue)) });
									} else
									{
										method.invoke(resultConfig, new Object[]
										{ resultAttrValue });
									}
								}
							}
							actionConfig.addResultConfig(resultConfig);
						}
					}
					this.mappingConfig.addActionConfig(actionConfig);
				}
			}

		} catch (Exception e)
		{
			e.printStackTrace();

			System.out.println("ActionServlet的init方法异常");
		}
	}
}
