package com.example.ppqppl_inpatient_ward_server.controller;

import com.example.ppqppl_inpatient_ward_server.entity.Admin;
import com.example.ppqppl_inpatient_ward_server.entity.User;
import com.example.ppqppl_inpatient_ward_server.repository.AdminRepository;
import com.example.ppqppl_inpatient_ward_server.repository.RegisterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
public class RegisterController {

    String regmsg = "获取你的免费账号！";

    @Autowired
    RegisterRepository registerRepository;

    @Autowired
    AdminRepository adminRepository;


    List<User> users = null;

    List<Admin> admins = null;

    @RequestMapping("/register")
    public String showregister(ModelMap map){
        map.addAttribute("regmsg",regmsg);
        map.addAttribute("newuser",new User());
        return "register";
    }

    @RequestMapping("/doregister")
    public String doregister(User user, ModelMap map){
        System.out.println(user);
        List<User> myusers =  registerRepository.findAllById(user.getId());
        List<Admin> myadmins = adminRepository.findAllById(user.getId());
        if((myusers!=null&&myusers.size()!=0)||(myadmins!=null&&myadmins.size()!=0)){
            regmsg = "身份证号已存在！";
            map.addAttribute("regmsg",regmsg);
            return "redirect:/register";
        }
        else{
            myusers = null;
            myadmins = null;
            myusers = registerRepository.findByPhone(user.getPhone());
            myadmins = adminRepository.findByPhone(user.getPhone());
            if((myusers!=null&&myusers.size()!=0)||(myadmins!=null&&myadmins.size()!=0)){
                regmsg = "电话号码已经被注册！";
                map.addAttribute("regmsg",regmsg);
                return "redirect:/register";
            }
            else{
                myusers = null;
                myusers = registerRepository.findByAccount(user.getAccount());
                myadmins = adminRepository.findByAccount(user.getAccount());
                if((myusers!=null&&myusers.size()!=0)||(myadmins!=null&&myadmins.size()!=0)) {
                    regmsg = "账号已经被注册！";
                    map.addAttribute("regmsg", regmsg);
                    return "redirect:/register";
                }
                else{
                    registerRepository.save(user);
                    regmsg = "获取你的免费账号！";
                    map.addAttribute("regmsg", regmsg);
                    return "redirect:/login";
                }
            }
        }
    }
}
