package com.work.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.alibaba.fastjson.JSON;
import com.work.entity.Function;
import com.work.entity.Role;
import com.work.service.FunctionService;
import com.work.service.RoleService;
import com.work.util.Page;
import com.work.util.TreeGridUtil;

@Controller
@RequestMapping("/role")
public class RoleController {
	@Autowired
	private RoleService roleService ;
	
	@Autowired
	private FunctionService functionService ;
	
	@RequestMapping("/turnToRoleList")
	public String turnToRoleIndex(){
		return "/role/roleList" ;
	}
	
	@RequestMapping("/getRoleList")
	public void getRoleList(Role role,HttpServletResponse response,int page,int rows) throws IOException{
		response.setCharacterEncoding("utf-8") ;
		Page<Role> roles = roleService.findByParams(role, page, rows) ;
		for(Role r: roles.getRows()){
			String function_id = r.getFunction_id() ;
			if(function_id != null){
				String function_name = "" ;
				String ids[] = function_id.split(",") ;
				for(String id :ids){
					String name ="" ;
					if(id!=null && !"".equals(id)){
						name = functionService.get(Integer.parseInt(id)).getFunction_name() ;
					}
					function_name += name+"," ;
					r.setAll_function_name(function_name) ;
				}
			}
		}
		
		
		String json = JSON.toJSONString(roles) ;
		response.getWriter().print(json) ;
	}
	@RequestMapping("/create")
	public void create(Role role,HttpServletResponse response) throws IOException{
		try {
			roleService.save(role) ;
			response.getWriter().print("true") ;
		} catch (Exception e) {
			e.printStackTrace() ;
			response.getWriter().print("false") ;
		}
		
	}
	
	@RequestMapping("/delete")
	public void delete(String ids ,HttpServletResponse response) throws IOException{
		String[] str_ids = ids.split(",") ;
		for(String id : str_ids){
			roleService.delete(Integer.parseInt(id)) ;
			response.getWriter().print("ture");
		}
	}
	
	@RequestMapping("/edit")
	public void edit(Role role,HttpServletResponse response) throws IOException{
		try {
			roleService.update(role) ;
			response.getWriter().print("true") ;
		} catch (Exception e) {
			response.getWriter().print("false") ;
			e.printStackTrace();
		}
	}
	
	@RequestMapping("/editSq")
	public void editSq(Role role,HttpServletResponse response) throws IOException{
		response.setContentType("html/text") ;
		roleService.update(role) ;
		response.getWriter().print("true") ;
	}
	
	/**
	 * 
	 * @Description: 获取所有的功能点 授权界面
	 * @param @param role_id
	 * @param @param response
	 * @param @throws IOException   
	 * @return void  
	 * @throws
	 * @author chj
	 * @date 2016-1-15  下午5:20:50
	 */
	@RequestMapping("/getFunctionByRole")
	public void getFunctionByRole(int role_id,HttpServletResponse response) throws IOException{
		response.setCharacterEncoding("utf-8") ;
		response.setContentType("text/html;charset=utf-8");
		response.setHeader("Cache-Control", "no-cache");
		Role role = roleService.get(role_id) ;
		String function_id = role.getFunction_id() ;//获得当前角色所拥有的所有权限
		Function function = new Function();
		List<TreeGridUtil> trees = new ArrayList<TreeGridUtil>() ;
		//只显示一级菜单
		function.setParent_id(-1) ;
		List<Function> functions = functionService.findAll(function);
		for(Function f:functions){
			TreeGridUtil tree = new TreeGridUtil() ;
			tree.setId(f.getId()) ;
			tree.setFunction_url(f.getFunction_url());
			tree.setName(f.getFunction_name());
			tree.setRemark(f.getRemark()) ;
			tree.setParent_id(f.getParent_id()) ;
			String id = f.getId() +"" ;
			//如果当前角色所拥有的权限
			if(function_id != null){
				
//				String[] arrayId = function_id.split(",") ;
//				for(String id_ : arrayId){
//					if(id_.equals(id)){
//						tree.setChecked(true);
//					}
//				}
				
				if(function_id.indexOf(id) != -1){
					tree.setChecked(true) ;
				}
			}
			if(f.getParent_id() == -1){
				int parent_id = f.getId() ;
				Function parent = new Function() ;
				parent.setParent_id(parent_id) ;
				List<Function> lists = functionService.findAll(parent) ;
				List<TreeGridUtil> t2 = new ArrayList<TreeGridUtil>() ;
				for(Function f_son : lists){
					TreeGridUtil t_son = new TreeGridUtil() ;
					t_son.setId(f_son.getId());
					t_son.setFunction_url(f_son.getFunction_url()) ;
					t_son.setName(f_son.getFunction_name()) ;
					t_son.setRemark(f_son.getRemark());
					t_son.setParent_id(f_son.getParent_id()) ;
					String t_son_id = t_son.getId() +"" ;
					if(function_id != null){
						if(function_id.indexOf(t_son_id) != -1){
							t_son.setChecked(true) ;
							//如果子菜单选中了 那么父菜单就要选中
							tree.setChecked(true) ;
						}
					}
					t2.add(t_son) ;
				}
				tree.setChildren(t2) ;
			}else{
				
			}
			trees.add(tree) ;
		}
		
		String json = JSON.toJSONString(trees) ;
		System.out.println(json);
		response.getWriter().print(json) ;
	}
	
	
}
